package org.SittX.util;

import java.util.ListIterator;

/***
 * This util class has some useful methods that are not present in the Iterator interface
 * such as being able to peek the next character or compare the next character to a specific value.
 */
public class IteratorUtil {
    /**
     * This method checks the next value with the "value" parameter.
     * @param iterator
     * @param value
     * @return
     */
    public static boolean checkNextValueEquals(ListIterator iterator, Object value){
       Object current = iterator.next();
       iterator.previous();
       return current == value;
    }

    /***
     * This method allows to peek the next character in the collection without moving the cursor pointer of the Iterator.
     * It's useful when we want to know which decoding method to call based on the next character.
     * @param iterator
     * @return
     */
    public static Object peekNextCharacter(ListIterator iterator){
       Object nextValue = iterator.next();
       iterator.previous();
       return nextValue;
    }
}
