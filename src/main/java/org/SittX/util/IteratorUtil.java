package org.SittX.util;

import java.util.ListIterator;

public class IteratorUtil {
    public static boolean checkNextValueEquals(ListIterator iterator, Object value){
       Object current = iterator.next();
       iterator.previous();
       return current == value;
    }

    public static Object peekNextCharacter(ListIterator iterator){
       Object nextValue = iterator.next();
       iterator.previous();
       return nextValue;
    }
}
