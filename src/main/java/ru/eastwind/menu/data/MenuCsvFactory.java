package ru.eastwind.menu.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import au.com.bytecode.opencsv.CSVReader;

import org.apache.commons.io.Charsets;

import ru.eastwind.menu.model.Menu;
import ru.eastwind.menu.model.MenuItem;

public class MenuCsvFactory extends MenuFactory {

    @Override
    public Menu createMenu(InputStream is) throws IOException {
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(is, Charsets.UTF_8), '\t')) {
            List<String[]> lines = csvReader.readAll();
            List<MenuItem> items = lines
                    .stream()
                    .map(line -> {
                        if (line.length != 2) {
                            throw new IllegalArgumentException(String.format(
                                    "Line %d is invalid - two values required", lines.indexOf(line)));
                        }
                        return new MenuItem(line[1]);
                    }).collect(Collectors.toList());
            return new Menu(items);
        }
    }

}
