package ru.eastwind.menu.data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import ru.eastwind.menu.model.Menu;

public abstract class MenuFactory {

    protected abstract Menu createMenu(InputStream is) throws IOException;

    public Menu createMenuFromResource(String resourceName) throws IOException {
        try (InputStream is = getClass().getResourceAsStream(resourceName)) {
            if (is == null) {
                throw new FileNotFoundException(resourceName);
            }
            return createMenu(is);
        }
    }

}
