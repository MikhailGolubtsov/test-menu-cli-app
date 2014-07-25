package ru.eastwind.menu.model;

import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MenuLookup {

    private IRelevanceStrategy relevanceStrategy;
    private Menu menu;

    public MenuLookup(Menu menu, IRelevanceStrategy relevanceStrategy) {
        this.menu = menu;
        this.relevanceStrategy = relevanceStrategy;
    }

    public List<String> lookupMenuItemsBy(String searchStr, int maxItems) {
        Stream<String> items = menu.getItems().stream().map(item -> item.name).distinct();
        Stream<ItemWithRelevance> itemsWithRelevance = items.map(item -> new ItemWithRelevance(item, relevanceStrategy
                .getRelevance(item, searchStr)));
        Stream<ItemWithRelevance> relevantItems = itemsWithRelevance.filter((item) -> item.relevance > 0);
        Map<Float, List<ItemWithRelevance>> groupedByRelevance = relevantItems.collect(Collectors
                .groupingBy(item -> item.relevance));
        NavigableMap<Float, List<ItemWithRelevance>> map = new TreeMap<>(groupedByRelevance).descendingMap();
        Stream<String> sortedItemsWithName = map.values().stream()
                .flatMap(c -> c.stream().map(item -> item.name).sorted());
        Stream<String> topMatchingItems = sortedItemsWithName.limit(maxItems);
        return topMatchingItems.collect(Collectors.toList());
    }

    public static class ItemWithRelevance {

        public final String name;
        public final float relevance;

        public ItemWithRelevance(String name, float relevance) {
            this.name = name;
            this.relevance = relevance;
        }

    }

}
