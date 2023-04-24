package org.SittX.MyBEncoding;

import java.io.File;
import java.io.FileNotFoundException;

//todo Delete after tests compleated.
@Deprecated(forRemoval = true) //tmp class for running small tests
public class MyMain {
    public static void main(String[] args) {
        /*
        d
            13:announce-list
                l
                    l
                        32:http://192.168.1.8:6969/announce
                    e
                e
            7:comment        15:This is comment
            10:created by    31:Transmission/4.0.0 (280ace12f8)
            13:creation date i1682158703e
            8:encoding       5:UTF-8
            4:info
            d
                6:length            i63e
                4:name 11:          testing.txt
                12:piece length     i32768e
                6:pieces 20:        ‘ø^rçÑØ#¬±ÉÊê;•Éeã
             e
        e
        */




        MyBEncoding torrentFile;

        try {
            torrentFile = new MyBEncoding(new File("testing.txt.torrent")); //just path to a given file.
        } catch (FileNotFoundException e) {
            //file not found error handleing
            throw new RuntimeException(e);
        }


        for (String s : torrentFile.getStringKeySet()) {
            System.out.println(s);
        }

    }
}
