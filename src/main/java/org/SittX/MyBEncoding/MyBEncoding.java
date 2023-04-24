package org.SittX.MyBEncoding;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


public class MyBEncoding {
    private final MyDictionary self;
    public MyBEncoding(File file) throws FileNotFoundException {
        this.self = this.mapToDictionary(this.mySplit(
                new MyFileIO(file).getRawFileData()));
    }

    /**
     * @return Set of all keys associated with a Value instanceof String
     */
    public Set<String> getStringKeySet(){
        return self.getStringMap().keySet();
    }
    /**
     * @return Set of all keys associated with a Value instanceof Integer
     */
    public Set<String> getIntKeySet(){
        return self.getIntegerMap().keySet();
    }
    /**
     * @return Set of all keys associated with a Value instanceof MyDictionary
     */
    public Set<String> getDictionaryKeySet(){
        return self.getDictionaryMap().keySet();
    }

    /**
     * @param key name of parameter from .torrent file
     * @return String associated with given key. If not found a null value will be given
     */
    public String getStringByKey(String key){
        return self.getStringMap().get(key);
    }
    /**
     * @param key name of parameter from .torrent file
     * @return Integer associated with given key. If not found a null value will be given
     */
    public Integer getIntegerByKey(String key){
        return self.getIntegerMap().get(key);
    }
    /**
     * @param key name of parameter from .torrent file
     * @return Integer associated with given key. If not found a null value will be given
     */
    public MyBEncoding getDictionaryByKey(String key){
        return new MyBEncoding(this.self.getDictionaryMap().get(key));
    }
















    private MyBEncoding(MyDictionary myDictionary){
        this.self = myDictionary;
    }

    private String mySplit(String input){
        StringBuilder command = new StringBuilder();
        StringBuilder output = new StringBuilder();
        AtomicBoolean readingInteger = new AtomicBoolean(false);
        AtomicBoolean readingString = new AtomicBoolean(false);
        AtomicInteger nameLength = new AtomicInteger();
        input.chars()
        .forEach(c -> {
            if (readingInteger.get()){
                if (c == 'e'){
                    command.setLength(0);
                    readingInteger.set(false);
                    output.append('\n');
                    return;
                }
                output.append((char)c);
            }
            if (readingString.get()){
                if(nameLength.decrementAndGet() >= 0) {
                    output.append((char)c);
                    return;
                }
                command.setLength(0);
                readingString.set(false);
                output.append('\n');
            }

            switch (c) {
                case 'd' -> output.append('{').append('\n');
                case 'e' -> output.append('}').append('\n');
                case 'i' -> readingInteger.set(true);
                case ':' -> {
                    readingString.set(true);
                    nameLength.set(Integer.parseInt(command.toString()));
                }
                default -> {
                    if ( c >= '0' && c<= '9')
                        command.append(Character.getNumericValue(c));
                }
            }
        });
        return output.toString();
    }
    private MyDictionary mapToDictionary(String input){
        MyDictionary output = new MyDictionary(new HashMap<>(), new HashMap<>(), new HashMap<>());
        Queue<String> stringsQueue = new ArrayDeque<>();
        MyDictionary workingOn = output;

        for (String word : input.split("\n")) {

            if (word.equals("{")) {
                if(stringsQueue.size()!=0) {
                    String key = stringsQueue.remove();
                    workingOn.getDictionaryMap().putIfAbsent(key, new MyDictionary(
                            new HashMap<>(),
                            new HashMap<>(),
                            new HashMap<>()
                    ));
                    workingOn = workingOn.getDictionaryMap().get(key);
                }
                continue;
            }
            if (word.equals("}")){
                workingOn = output;
                continue;
            }
            stringsQueue.add(word);
            if (stringsQueue.size() == 2) {
                String key = stringsQueue.remove();
                String val = stringsQueue.remove();
                try {
                    int intVal = Integer.parseInt(val);
                    workingOn.getIntegerMap().put(key, intVal);
                } catch (NumberFormatException e) {
                    workingOn.getStringMap().put(key, val);
                }
            }
        }
        return output;
    }
}
