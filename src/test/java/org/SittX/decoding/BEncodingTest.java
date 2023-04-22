package org.SittX.decoding;

import org.SittX.util.TypeConverter;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BEncodingTest {

    @Test
    void decode() {
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
        Iterator<Byte> iterator = Arrays.stream(byteObjectArray).iterator();

        long result = (long) BEncoding.decodeNumber(iterator);
        assertEquals(987L, result);
    }
}