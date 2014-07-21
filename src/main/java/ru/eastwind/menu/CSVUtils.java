package ru.eastwind.menu;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import au.com.bytecode.opencsv.CSVReader;

public class CSVUtils {

    public static CSVReader getCsvReaderFromResource(String name) throws IOException {
        InputStream is = MenuDao.class.getResourceAsStream(name);
        if (is == null) {
            throw new FileNotFoundException(name);
        }
        return new CSVReader(new InputStreamReader(is), '\t');
    }
}
