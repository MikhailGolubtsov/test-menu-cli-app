package ru.eastwind.menu;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MenuDaoTest {

    @Mock
    private CSVReader csvReader;

    @Test(expected = IllegalArgumentException.class)
    public void badData() throws IOException {
        mockData(Arrays.asList(new String[][] { {"1", "item"}, {"1"}}));
        new MenuDao(csvReader);
    }

    @Test
    public void ok() throws IOException {
        mockData(Arrays.asList(new String[][] { {"1", "Пицца"}, {"1", "Пиво"}}));
        List<MenuItem> allItems = new MenuDao(csvReader).getAllItems();
        Assert.assertEquals(MenuItem.menuItems("Пицца", "Пиво"), allItems);
    }

    private void mockData(List<String[]> badData) throws IOException {
        Mockito.when(csvReader.readAll()).thenReturn(badData);
    }

}
