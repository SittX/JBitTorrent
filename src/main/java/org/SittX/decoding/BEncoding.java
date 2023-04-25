package org.SittX.decoding;

import org.SittX.util.IteratorUtil;
import org.SittX.util.TypeConverter;

import java.nio.charset.StandardCharsets;
import java.util.*;

// TODO write Unit tests for every methods in this class
public class BEncoding {
    private static final Byte LIST_START_CHAR = "l".getBytes(StandardCharsets.UTF_8)[0]; // 108
    private static final Byte LIST_END_CHAR = "e".getBytes(StandardCharsets.UTF_8)[0]; // 101
    private static final Byte NUMBER_START_CHAR = "i".getBytes(StandardCharsets.UTF_8)[0]; // 105
    private static final Byte NUMBER_END_CHAR = "e".getBytes(StandardCharsets.UTF_8)[0]; // 101
    private static final Byte DICTIONARY_START_CHAR = "d".getBytes(StandardCharsets.UTF_8)[0]; // 100
    private static final Byte DICTIONARY_END_CHAR = "e".getBytes(StandardCharsets.UTF_8)[0]; // 101
    private static final Byte BYTE_ARRAY_DIVIDER = ":".getBytes(StandardCharsets.UTF_8)[0]; // 58

    /***
     * This method decodes the byte[] which got returned from reading a Torrent file.
     * @param bytes is the file contents of the torrent file.
     * @return Object which decodes byte[] using BEncoding encoding format.
     */
    public static Object decode(byte[] bytes) {
        Byte[] byteObjectArray = TypeConverter.toByteObjectArray(bytes);
        ListIterator<Byte> iterator = Arrays.stream(byteObjectArray).toList().listIterator();
        return decodeNextObject(iterator);
    }


    /***
     * Excuse this monstrosity :) I'll refactor it later.
     * This method will check which decoding method that it should call based on the next character in the iterator.
     * @param iterator
     * @return an Object which is decoded based on their data type
     */
    protected static Object decodeNextObject(ListIterator<Byte> iterator) {
        if(IteratorUtil.peekNextCharacter(iterator) == LIST_START_CHAR || IteratorUtil.peekNextCharacter(iterator) == NUMBER_START_CHAR ||IteratorUtil.peekNextCharacter(iterator) == DICTIONARY_START_CHAR ){
            Byte current = iterator.next();
            if (Objects.equals(current, LIST_START_CHAR)) return decodeList(iterator);
            if (Objects.equals(current, NUMBER_START_CHAR)) return decodeNumber(iterator);
            if (Objects.equals(current, DICTIONARY_START_CHAR)) return decodeDictionary(iterator);
        }
       return decodeByteArray(iterator);
    }

    /***
     *  This method decodes the byte array which is
     * @param iterator
     * @return
     */
    protected static String decodeByteArray(ListIterator<Byte> iterator) {
        List<Byte> arrayLengthBytes = new ArrayList<>();

        do {
            Byte current = iterator.next();
            if (current == BYTE_ARRAY_DIVIDER) break;
            arrayLengthBytes.add(current);
        } while (iterator.hasNext());

        // Convert [2,4] -> "24" -> 24
        byte[] lengthArray = fromByteListToBytePrimitiveArray(arrayLengthBytes);
        String lengthAsString = new String(lengthArray);
        int arrayLength = Integer.parseInt(lengthAsString);

        byte[] byteArray = new byte[arrayLength];
        for (int i = 0; i < arrayLength; i++) {
            Byte current = iterator.next();
            byteArray[i] = current;
        }

        return new String(byteArray);
    }

    protected static Dictionary<String,Object> decodeDictionary(ListIterator<Byte> iterator) {
        Dictionary<String,Object> dictionary = new Hashtable<>();
        List<String> keys = new ArrayList<>();

        while(iterator.hasNext()){
            if(IteratorUtil.checkNextValueEquals(iterator, DICTIONARY_END_CHAR)) break;

            String key = decodeByteArray(iterator);
            Object value = decodeNextObject(iterator);

            keys.add(key);
            dictionary.put(key,value);
        }
        return  dictionary;
    }

    protected static Object decodeNumber(ListIterator<Byte> iterator) {
        List<Byte> byteObjectList = new ArrayList<>();

        while (iterator.hasNext()) {
            Byte current = iterator.next();
            if (current == NUMBER_END_CHAR) {
                break;
            }
            byteObjectList.add(current);
        }

        byte[] byteArray = fromByteListToBytePrimitiveArray(byteObjectList);

        String numAsString = new String(byteArray);
        long result = Long.parseLong(numAsString);
        return result;
    }

    private static Object decodeList(ListIterator<Byte> iterator) {
        List<Object> list = new ArrayList<>();

        while(iterator.hasNext()) {
//            iterator.next(); // Skip the ListStartCharacter "l"
            Byte nextChar = (Byte) IteratorUtil.peekNextCharacter(iterator);
            if (Objects.equals(nextChar, LIST_END_CHAR)) {
                break;
            }

            list.add(decodeNextObject(iterator));
        }

        return list;
    }

    private static byte[] fromByteListToBytePrimitiveArray(List<Byte> byteList) {
        byte[] byteArray = new byte[byteList.size()];
        for (int i = 0; i < byteList.size(); i++) {
            byteArray[i] = byteList.get(i);
        }
        return byteArray;
    }

}
