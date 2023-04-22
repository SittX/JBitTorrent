package org.SittX;

import org.SittX.decoding.BEncoding;
import org.SittX.util.TypeConverter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try (FileInputStream fileInputStream = new FileInputStream("testing.txt.torrent")) {
            byte[] fileContents = fileInputStream.readAllBytes();
            Byte[] byteObjectArray = TypeConverter.toByteObjectArray(fileContents);

            BEncoding.decode(byteObjectArray);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}