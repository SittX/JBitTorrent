package org.SittX.util;

import java.io.*;

public class FileIO {
    public static byte[] readFileIntoByteArray(String filePath) {
        byte[] fileBuffer;
        try(FileInputStream input = new FileInputStream(filePath)){
            fileBuffer =  input.readAllBytes();
            return fileBuffer;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static char[] readFileIntoCharArray(String filePath){
        File file = new File(filePath);
        char[] fileBuffer = new char[(int) file.length()];
        try(FileReader reader = new FileReader(filePath)){
           reader.read(fileBuffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileBuffer;
    }
}
