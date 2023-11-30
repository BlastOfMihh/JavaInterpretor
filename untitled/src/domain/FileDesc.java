package domain;

import java.io.BufferedReader;
import java.io.Reader;

public class FileDesc extends BufferedReader {
    public FileDesc(Reader fr){
        super(fr);
    }

    @Override
    public String toString() {
        return "";
    }
}
