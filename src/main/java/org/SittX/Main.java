package org.SittX;

import org.SittX.decoding.BEncoding;
import org.SittX.util.FileIO;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
            byte[] fileContents= FileIO.readFileIntoByteArray("rust.torrent");
            Object result = BEncoding.decode(fileContents);
        System.out.println(result);
    }
}