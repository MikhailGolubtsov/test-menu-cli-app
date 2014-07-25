package ru.eastwind.menu;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;

public class IOSupport implements Closeable {

    public IOSupport(Reader reader, PrintStream writer) {
        this.writer = writer;
        this.reader = new BufferedReader(reader);
    }

    private BufferedReader reader;
    private PrintStream writer;

    public String readInputString() throws IOException {
        return reader.readLine();
    }

    public void output(String str) {
        writer.println(str);
    }

    public static IOSupport systemIOSupport() {
        return new IOSupport(new InputStreamReader(System.in), System.out);
    }

    @Override
    public void close() throws IOException {
        try {
            writer.close();
        } finally {
            reader.close();
        }
    }

    public static IOSupport fileIOSupport(String inputFileName, String outputFileName) throws IOException {
        InputStreamReader reader = new InputStreamReader(App.class.getResourceAsStream(inputFileName));
        File outputFile = new File(outputFileName);
        if (!outputFile.exists()) {
            outputFile.createNewFile();
        }
        PrintStream writer = new PrintStream(outputFile);
        return new IOSupport(reader, writer);
    }
}
