package org.SittX.decoding;

import java.nio.charset.StandardCharsets;
import java.util.*;

// TODO write Unit tests for every methods in this class
public class BEncoding {
    private static final Byte listStartChar = "l".getBytes(StandardCharsets.UTF_8)[0];
    private static final Byte listEndChar = "e".getBytes(StandardCharsets.UTF_8)[0];
    private static final Byte numberStartChar = "i".getBytes(StandardCharsets.UTF_8)[0];
    private static final Byte numberEndChar = "e".getBytes(StandardCharsets.UTF_8)[0];
    private static final Byte dictionaryStartChar = "d".getBytes(StandardCharsets.UTF_8)[0];
    private static final Byte dictionaryEndChar = "e".getBytes(StandardCharsets.UTF_8)[0];
    private static final Byte byteArrayDivider = ":".getBytes(StandardCharsets.UTF_8)[0];

    public static Object decode(Byte[] bytes) {
        Iterator<Byte> iterator = Arrays.stream(bytes).iterator();
        // Skip the Beginning characters
        iterator.next();
        return decodeNextObject(iterator);
    }

    protected static Object decodeNextObject(Iterator<Byte> iterator) {
        if (Objects.equals(iterator.next(), listStartChar)) return decodeList(iterator);
        if (Objects.equals(iterator.next(), numberStartChar)) return decodeNumber(iterator);
        if (Objects.equals(iterator.next(), dictionaryStartChar)) return decodeDictionary(iterator);
        return decodeByteArray(iterator);
    }

    protected static Object decodeByteArray(Iterator<Byte> iterator) {
        List<Byte> lengthBytesList = new ArrayList<>();

        do {
            Byte current = iterator.next();
            if (current == byteArrayDivider) break;

            lengthBytesList.add(current);
        } while (iterator.hasNext());

        byte[] lengthArray = fromByteListToBytePrimitiveArray(lengthBytesList);
        String lengthAsString = new String(lengthArray);
        int arrayLength = Integer.parseInt(lengthAsString);

        byte[] byteArray = new byte[arrayLength];

        for (int i = 0; i < arrayLength; i++) {
            byte current = iterator.next();
            byteArray[i] = current;
        }

        return byteArray;
    }

    protected static Dictionary<String,Object> decodeDictionary(Iterator<Byte> iterator) {
        Dictionary<String,Object> dictionary = new Hashtable<>();
        List<String> keys = new ArrayList<>();

        while(iterator.hasNext()){
            byte current = iterator.next();
            if(current == dictionaryEndChar) break;

            String key = (String) decodeByteArray(iterator);
            iterator.next();
            Object value = decodeNextObject(iterator);

            keys.add(key);
            dictionary.put(key,value);
        }
        return  dictionary;
    }

    protected static Object decodeNumber(Iterator<Byte> iterator) {
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

    private static Object decodeList(Iterator<Byte> iterator) {
        List<Object> list = new ArrayList<>();

        // Skip the first ListStartChar -> "l"
        iterator.next();

        while (iterator.next() != listEndChar) {
            list.add(decodeNextObject(iterator));
        }

        return list;
//            while (iterator.hasNext()) {
//                byte nextByte = iterator.next();
//
//                if (nextByte == listEndChar) {
//                    return list;
//                } else {
//                    Object nextObject = decodeNextObject(iterator);
//                    list.add(nextObject);
//                }
//            }
    }

    private static byte[] fromByteListToBytePrimitiveArray(List<Byte> byteList) {
        byte[] byteArray = new byte[byteList.size()];
        for (int i = 0; i < byteList.size(); i++) {
            byteArray[i] = byteList.get(i);
        }
        return byteArray;
    }

}
