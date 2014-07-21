package ru.eastwind.menu;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import au.com.bytecode.opencsv.CSVReader;

public class MenuDao {

    private List<MenuItem> items;

    public MenuDao(CSVReader csvReader) throws IOException {
        List<String[]> lines = csvReader.readAll();
        items = lines
                .stream()
                .map(line -> {
                    if (line.length != 2) {
                        throw new IllegalArgumentException(String.format("Line %d is invalid - two values required",
                                lines.indexOf(line)));
                    }
                    return new MenuItem(line[1]);
                }).collect(Collectors.toList());
    }

    public List<MenuItem> getAllItems() {
        return items;
    }

    public static MenuDao fromResource(String name) throws IOException {
        try (CSVReader csvReader = CSVUtils.getCsvReaderFromResource(name)) {
            return new MenuDao(csvReader);
        }
    }

}
