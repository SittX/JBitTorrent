package org.SittX;

import org.SittX.decoding.BEncoding;
import org.SittX.util.FileIO;
import org.SittX.util.TypeConverter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
            byte[] fileContents= FileIO.readFileIntoByteArray("testing.txt.torrent");
            BEncoding.decode(fileContents);
    }
}