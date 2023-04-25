package org.SittX.MyBEncoding;

import java.io.*;

public class MyFileIO {

    private final File fileToReadFrom;


    /**
     * MyFileIO accesses raw (unproccessed, not DeEncoded) data from source file. and provides it in a form of a String object.
     * It does not hold any inputed file data. Every getRawFileData overrides previus read.
     * @param file Object class File. Source file that is being red from.
     *
     * @see File#File(String path)
     * @see #getRawFileData()
     */
    public MyFileIO(File file) {
        this.fileToReadFrom = file;
    }

    /**
     * Reloads StringBuilder with new input from the source location
     * @throws FileNotFoundException unable to read from File. Check constructor File object path
     * @return Whole raw data file. Method will never return null. If file is empty expect empty string as a resault
     */
    public String getRawFileData() throws FileNotFoundException {
        StringBuilder stringBuilder = new StringBuilder();
        FileReader fileInputStream = new FileReader(this.fileToReadFrom.getPath());

        int charCodeInput;
        try {
            charCodeInput = fileInputStream.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        //Reads until finds -1 charCode (end of file value for the FileReader class)
        while (charCodeInput!=-1){
            stringBuilder.append((char) charCodeInput);
            try {
                charCodeInput = fileInputStream.read();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        //exiting
        try {
            fileInputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stringBuilder.toString();
    }



}
