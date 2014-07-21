package ru.eastwind.menu;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

public class AppFunctionalTest {

    @Test
    public void test() throws Exception {
        String outputFileName = "programOutput.txt";

        App app = App.newApp(IOSupport.fileIOSupport("userInput.txt", outputFileName));
        app.run();

        try (InputStream ouputIS = new FileInputStream(outputFileName);
                InputStream expectedIS = getClass().getResourceAsStream("expectedProgramOutput.txt")) {
            String output = getReasonbleContent(ouputIS);
            String expectedOutput = getReasonbleContent(expectedIS);

            Assert.assertEquals(expectedOutput, output);
        }
    }

    private String getReasonbleContent(InputStream is) throws IOException {
        String content = IOUtils.toString(is, Charsets.UTF_8);
        Stream<String> lines = Arrays.asList(content.split("\n")).stream();
        List<String> reasonableLines = lines.map(line -> line.trim()).filter(line -> line.length() > 0).collect(Collectors.toList());
        return String.join("\n", reasonableLines);
    }
}
