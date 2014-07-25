package ru.eastwind.menu.model;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import ru.eastwind.menu.model.IRelevanceStrategy;
import ru.eastwind.menu.model.RelevanceStrategy;

@RunWith(Parameterized.class)
public class RelevanceStrategyTest {

    private String text;
    private String query;
    private float expectedRelevance;

    public RelevanceStrategyTest(String text, String query, float expectedRelevance) {
        this.text = text;
        this.query = query;
        this.expectedRelevance = expectedRelevance;
    }

    private IRelevanceStrategy relevanceStrategy = new RelevanceStrategy();

    @Test
    public void test() {
        float relevance = relevanceStrategy.getRelevance(text, query);
        Assert.assertEquals("Relevance", expectedRelevance, relevance, 0.01);
    }

    @Parameters(name = "Relevance for \"{0}\" in \"{1}\" should be {2}")
    public static Collection<Object[]> generateData() {
        return Arrays.asList(new Object[][] {
                {"", "", 0},
                {"", "be", 0},
                {"some", "", 0},
                {"word", "word", 1},
                {"word", "WORD", 1},
                {"word", "war", 0},
                {"some word", "word", 1},
                {"two words", "two words", 2},
                {"bees", "be", 0.5F},
                {"bees", "bees be", 1},
                {"bees be", "bees be", 2},
        });
    }
}
