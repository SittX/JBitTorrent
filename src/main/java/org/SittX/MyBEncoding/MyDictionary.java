package org.SittX.MyBEncoding;

import java.util.ArrayList;
import java.util.HashMap;

public class MyDictionary {
    private final HashMap<String, Integer> integerMap;
    private final HashMap<String, String> stringMap;
    private final HashMap<String, MyDictionary> dictionaryMap;
//    private final ArrayList<ArrayList<String>> //todo

    public MyDictionary(HashMap<String, Integer> integerMap, HashMap<String, String> stringMap, HashMap<String, MyDictionary> dictionaryMap) {
        this.integerMap = integerMap;
        this.stringMap = stringMap;
        this.dictionaryMap = dictionaryMap;
    }

    public HashMap<String, Integer> getIntegerMap() {
        return integerMap;
    }

    public HashMap<String, String> getStringMap() {
        return stringMap;
    }

    public HashMap<String, MyDictionary> getDictionaryMap() {
        return dictionaryMap;
    }
}
