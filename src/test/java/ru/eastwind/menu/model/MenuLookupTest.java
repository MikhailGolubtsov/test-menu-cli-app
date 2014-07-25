package ru.eastwind.menu.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mockito;

import ru.eastwind.menu.model.IRelevanceStrategy;
import ru.eastwind.menu.model.Menu;
import ru.eastwind.menu.model.MenuItem;
import ru.eastwind.menu.model.MenuLookup;

@RunWith(Parameterized.class)
public class MenuLookupTest {

    private static final int MAX_MATCHING_ITEMS = 5;

    private MenuLookup menuLookup;
    private IRelevanceStrategy relevanceStrategy = new IRelevanceStrategy() {
        @Override
        public float getRelevance(String text, String query) {
            if (text.equals(query)) {
                return 1;
            }
            if (text.contains(query)) {
                return 0.5F;
            }
            return 0;
        }
    };
    private Menu menu;
    private String testName;
    private String query;
    private List<MenuItem> itemsFromDao;
    private List<String> expectedItems;

    public MenuLookupTest(String testName, String query, List<MenuItem> itemsFromDao, List<String> expectedItems) {
        this.testName = testName;
        this.query = query;
        this.itemsFromDao = itemsFromDao;
        this.expectedItems = expectedItems;
    }

    @Before
    public void setup() {
        menu = Mockito.mock(Menu.class);
        menuLookup = new MenuLookup(menu, relevanceStrategy);
        Mockito.when(menu.getItems()).thenReturn(itemsFromDao);
    }

    @Test
    public void test() {
        List<String> foundItems = menuLookup.lookupMenuItemsBy(query, MAX_MATCHING_ITEMS);
        Assert.assertEquals(testName, expectedItems, foundItems);
    }

    @Parameters(name = "{0}")
    public static Collection<Object[]> generateData() {
        return Arrays.asList(new Object[][] {
                {"Should not find with zero relevance", "be", MenuItem.menuItems("no", "nono"), Collections.emptyList()},
                {"Should find with positive relevance", "be", MenuItem.menuItems("be1", "be2"), Arrays.asList("be1", "be2")},
                {"Should sort items by relevance then alphabetically", "be", MenuItem.menuItems("bec", "beb", "bea", "be"),
                        Arrays.asList("be", "bea", "beb", "bec")},
                {"Should find only top " + MAX_MATCHING_ITEMS + " items", "be",
                        MenuItem.menuItems("be1", "be2", "be3", "be4", "be5", "be6", "be7"),
                        Arrays.asList("be1", "be2", "be3", "be4", "be5")},
                });
    }
}
