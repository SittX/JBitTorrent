package org.SittX.decoding;

import org.SittX.util.BEncodingIterator;
import org.SittX.util.FileIO;
import org.SittX.util.TypeConverter;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BEncodingTest {

    @Test
    void decode() {
        char[] chars = FileIO.readFileIntoCharArray("Rust.torrent");
        Object result = BEncodingChar.decode(chars);
        System.out.println(result);
    }

    @Test
    void decodeNextObject() {
    }

    @Test
    void decodeByteArray() {
    }

    @Test
    void decodeDictionary() {
    }

    @Test
    void decodeNumber() throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("testing.txt.torrent");
        byte[] byteBuffer = "987e".getBytes(StandardCharsets.UTF_8);
        Byte[] byteObjectArray = TypeConverter.toByteObjectArray(byteBuffer);

        // Get Iterator for the byteObjectArray
//        BEncodingIterator<Byte> iterator = (BEncodingIterator<Byte>) Arrays.stream(byteObjectArray).toList().listIterator();

//        long result = (long) BEncoding.decodeNumber(iterator);
//        assertEquals(987L, result);
    }
}