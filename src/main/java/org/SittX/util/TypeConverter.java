package org.SittX.util;

public class TypeConverter {

    public static Byte[] toByteObjectArray(byte[] array) {
        Byte[] objectArray = new Byte[array.length];
        for (int i = 0; i < array.length; i++) {
            objectArray[i] = array[i];
        }
        return objectArray;
    }

    public static byte[] toBytePrimitiveArray(Byte[] array) {
        byte[] objectArray = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            objectArray[i] = array[i];
        }
        return objectArray;
    }
    public static Character[] toCharacterObjectArray(char[] array) {
        Character[] objectArray = new Character[array.length];
        for (int i = 0; i < array.length; i++) {
            objectArray[i] = array[i];
        }
        return objectArray;
    }

    public static char[] toCharArrayFromObjectArray(Character[] array) {
        char[] objectArray = new char[array.length];
        for (int i = 0; i < array.length; i++) {
            objectArray[i] = array[i];
        }
        return objectArray;
    }
}
