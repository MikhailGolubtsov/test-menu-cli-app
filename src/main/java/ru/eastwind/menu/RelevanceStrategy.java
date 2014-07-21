package ru.eastwind.menu;

import java.util.Arrays;
import java.util.List;

/**
 * И текст и запрос разбиваются на слова (по пробельным символам). Для каждого слова из текста определяется наиболее релевантное слово из
 * слов запроса, общая релевантность определяется как сумма релевантностей всех слов текста. Слово считается релевантным, если оно совпадает
 * с искомым словом из запроса (значение рел-ти - 1), либо начинается с этого слова (тогда релевантность считается как доля совпавших букв).
 * Для примеров см. тесты {@link RelevanceStrategyTest#generateData()}
 */
public class RelevanceStrategy implements IRelevanceStrategy {

    public float getRelevance(String text, String query) {
        text = text.trim().toLowerCase();
        String q = query.trim().toLowerCase();
        if (text.length() == 0 || q.length() == 0) {
            return 0F;
        }
        List<String> textWords = Arrays.asList(text.split("\\s+"));
        List<String> queryWords = Arrays.asList(q.split("\\s+"));
        return textWords.stream().map((t) -> getMaxRelevanceFor(t, queryWords)).reduce(0F, Float::sum);
    }

    private float getMaxRelevanceFor(String word, List<String> queryWords) {
        return queryWords.stream().map((queryWord) -> {
            return (singleWordRelevance(word, queryWord));
        }).max(Float::compare).orElse(0F);
    }

    private float singleWordRelevance(String word, String query) {
        if (!word.startsWith(query)) {
            return 0F;
        }
        return (float) query.length() / word.length();
    }
}
