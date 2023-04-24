package org.SittX.decoding;

import org.SittX.util.IteratorUtil;
import org.SittX.util.TypeConverter;

import java.nio.charset.StandardCharsets;
import java.util.*;

// TODO write Unit tests for every methods in this class
public class BEncoding {
    private static final Byte listStartChar = "l".getBytes(StandardCharsets.UTF_8)[0]; // 108
    private static final Byte listEndChar = "e".getBytes(StandardCharsets.UTF_8)[0]; // 101
    private static final Byte numberStartChar = "i".getBytes(StandardCharsets.UTF_8)[0]; // 105
    private static final Byte numberEndChar = "e".getBytes(StandardCharsets.UTF_8)[0]; // 101
    private static final Byte dictionaryStartChar = "d".getBytes(StandardCharsets.UTF_8)[0]; // 100
    private static final Byte dictionaryEndChar = "e".getBytes(StandardCharsets.UTF_8)[0]; // 101
    private static final Byte byteArrayDivider = ":".getBytes(StandardCharsets.UTF_8)[0]; // 58

    public static Object decode(byte[] bytes) {
        Byte[] byteObjectArray = TypeConverter.toByteObjectArray(bytes);
        ListIterator<Byte> iterator = Arrays.stream(byteObjectArray).toList().listIterator();
        return decodeNextObject(iterator);
    }

    protected static Object decodeNextObject(ListIterator<Byte> iterator) {
        if(IteratorUtil.peekNextCharacter(iterator) == listStartChar || IteratorUtil.peekNextCharacter(iterator) == numberStartChar ||IteratorUtil.peekNextCharacter(iterator) == dictionaryStartChar ){
            Byte current = iterator.next();
            if (Objects.equals(current, listStartChar)) return decodeList(iterator);
            if (Objects.equals(current, numberStartChar)) return decodeNumber(iterator);
            if (Objects.equals(current, dictionaryStartChar)) return decodeDictionary(iterator);
        }
       return decodeByteArray(iterator);
    }

    protected static Object decodeByteArray(ListIterator<Byte> iterator) {
        List<Byte> arrayLengthBytes = new ArrayList<>();

        do {
            Byte current = iterator.next();
            if (current == byteArrayDivider) break;
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

        return byteArray;
    }

    protected static Dictionary<String,Object> decodeDictionary(ListIterator<Byte> iterator) {
        Dictionary<String,Object> dictionary = new Hashtable<>();
        List<String> keys = new ArrayList<>();

        while(iterator.hasNext()){
            if(IteratorUtil.checkNextValueEquals(iterator,dictionaryEndChar)) break;

            String key = new String((byte[]) decodeByteArray(iterator));
            // We need to something right here to check the value of the index value without moving the cursor
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
            if (current == numberEndChar) {
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

//        Byte current = iterator.next();
//        System.out.println(current);
//        if(Objects.equals(current,listStartChar)){
//            decodeList(iterator);
//        }else{
//            iterator.previous();
//        }


        while(iterator.hasNext()) {
            iterator.next(); // Skip the element "ListIntroChar"
            Byte current = iterator.next();
            System.out.println(current);
            if (Objects.equals(current, listEndChar)) {
                break;
            }
            iterator.previous(); // Move the cursor backward to fully include the data
//            list.add(decodeNextObject(iterator));
            list.add(new String((byte[]) decodeByteArray(iterator)));
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
