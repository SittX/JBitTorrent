package org.SittX.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileIO {
    public static byte[] readFileIntoByteArray(String filePath) throws FileNotFoundException {
        byte[] fileBuffer;
        try(FileInputStream input = new FileInputStream(filePath)){
            fileBuffer =  input.readAllBytes();
            return fileBuffer;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
