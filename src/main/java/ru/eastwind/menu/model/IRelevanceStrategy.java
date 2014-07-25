package ru.eastwind.menu.model;

public interface IRelevanceStrategy {

    float getRelevance(String text, String query);

}
