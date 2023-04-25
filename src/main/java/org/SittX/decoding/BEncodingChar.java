package org.SittX.decoding;

import org.SittX.util.IteratorUtil;
import org.SittX.util.TypeConverter;

import java.util.*;

public class BEncodingChar {
    private static final char LIST_START_CHAR = 'l';
    private static final char LIST_END_CHAR = 'e';
    private static final char NUMBER_START_CHAR = 'i';
    private static final char NUMBER_END_CHAR = 'e';
    private static final char DICTIONARY_START_CHAR = 'd';
    private static final char DICTIONARY_END_CHAR = 'e';
    private static final char BYTE_ARRAY_DIVIDER = ':';

    /***
     * This method decodes the byte[] which got returned from reading a Torrent file.
     * @param buffer is the file contents of the torrent file.
     * @return Object which decodes byte[] using BEncoding encoding format.
     */
    public static Object decode(char[] buffer) {
        Character[] byteObjectArray = TypeConverter.toCharacterObjectArray(buffer);
        ListIterator<Character> iterator = Arrays.stream(byteObjectArray).toList().listIterator();
        return decodeNextObject(iterator);
    }


    /***
     * Excuse this monstrosity :) I'll refactor it later.
     * This method checks which decoding method it should call based on the next character in the iterator.
     * @param iterator
     * @return an Object which is decoded based on their data type
     */
    protected static Object decodeNextObject(ListIterator<Character> iterator) {
        if((Character) IteratorUtil.peekNextCharacter(iterator) == LIST_START_CHAR || (Character)IteratorUtil.peekNextCharacter(iterator) == NUMBER_START_CHAR || (Character)IteratorUtil.peekNextCharacter(iterator) == DICTIONARY_START_CHAR ){
            Character current = iterator.next();
            if (Objects.equals(current, LIST_START_CHAR)) return decodeList(iterator);
            if (Objects.equals(current, NUMBER_START_CHAR)) return decodeNumber(iterator);
            if (Objects.equals(current, DICTIONARY_START_CHAR)) return decodeDictionary(iterator);
        }
        return decodeByteArray(iterator);
    }

    /***
     *  This method decodes the file contents into String.
     * @param iterator
     * @return a String
     */
    protected static String decodeByteArray(ListIterator<Character> iterator) {
        List<Character> arrayLengthBytes = new ArrayList<>();

        do {
            Character current = iterator.next();
            if (current == BYTE_ARRAY_DIVIDER) break;
            arrayLengthBytes.add(current);
        } while (iterator.hasNext());

        // Convert [2,4] -> "24" -> 24
        char[] lengthArray = fromCharListToCharArray(arrayLengthBytes);
        String lengthAsString = new String(lengthArray);
        int arrayLength = Integer.parseInt(lengthAsString);

        char[] charArray = new char[arrayLength];
        for (int i = 0; i < arrayLength; i++) {
            Character current = iterator.next();
            charArray[i] = current;
        }

        return new String(charArray);
    }

    /***
     * This method is entry to the decoder method because the Bencoding starts with a dictionary. It will store all the attributes and  their values in key-value pairs
     * @param iterator
     * @return a dictionary of the Torrent file properties.
     */
    protected static Dictionary<String,Object> decodeDictionary(ListIterator<Character> iterator) {
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

    /***
     * This method decode the number in Torrent file.
     * @param iterator
     * @return
     */
    protected static Object decodeNumber(ListIterator<Character> iterator) {
        List<Character> byteObjectList = new ArrayList<>();

        while (iterator.hasNext()) {
            Character current = iterator.next();
            if (current == NUMBER_END_CHAR) {
                break;
            }
            byteObjectList.add(current);
        }

        char[] byteArray = fromCharListToCharArray(byteObjectList);

        String numAsString = new String(byteArray);
        long result = Long.parseLong(numAsString);
        return result;
    }

    /***
     * This is the most difficult one to get it right because sometimes the List contains two opening and ending characters which makes it somehow difficult to decode
     * There is still some bugs in this method.
     * @param iterator
     * @return
     */
    private static Object decodeList(ListIterator<Character> iterator) {
        List<Object> list = new ArrayList<>();

        while(iterator.hasNext()) {
            Character nextChar = (Character) IteratorUtil.peekNextCharacter(iterator);
            if (Objects.equals(nextChar, LIST_END_CHAR)) {
                // When we peeked the next character and if it's the ListEndingChar, we skip that character in the iterator
                // Otherwise, this won't work :(
                iterator.next();
                break;
            }

            list.add(decodeNextObject(iterator));
        }
        return list;
    }

    /***
     * A helper method that convert from a List to an Array. There might be better ways to do this.
     * @param charList is the List that we want to turn into an Array.
     * @return
     */
    private static char[] fromCharListToCharArray(List<Character> charList) {
        char[] charArray = new char[charList.size()];
        for (int i = 0; i < charList.size(); i++) {
            charArray[i] = charList.get(i);
        }
        return charArray;
    }
}
