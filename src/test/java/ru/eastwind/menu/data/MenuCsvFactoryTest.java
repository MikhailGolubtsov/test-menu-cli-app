package ru.eastwind.menu.data;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.Charsets;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ru.eastwind.menu.data.MenuCsvFactory;
import ru.eastwind.menu.model.Menu;
import ru.eastwind.menu.model.MenuItem;

@RunWith(MockitoJUnitRunner.class)
public class MenuCsvFactoryTest {

    @Test(expected = IllegalArgumentException.class)
    public void badData() throws IOException {
        createMenuFromData("1");
    }

    @Test
    public void ok() throws IOException {
        Menu menu = createMenuFromData("1\tПицца", "1\tПиво");
        Assert.assertEquals(MenuItem.menuItems("Пицца", "Пиво"), menu.getItems());
    }

    @Test
    public void empty() throws IOException {
        Menu menu = createMenuFromData("");
        Assert.assertEquals(Collections.emptyList(), menu.getItems());
    }

    private Menu createMenuFromData(String... lines) throws IOException {
        InputStream is = inputData(Arrays.asList(lines));
        return new MenuCsvFactory().createMenu(is);
    }

    private InputStream inputData(List<String> lines) {
        String data = String.join("\n", lines);
        return new ByteArrayInputStream(data.getBytes(Charsets.UTF_8));
    }

}
