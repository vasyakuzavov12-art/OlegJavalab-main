package utils;

import java.util.*;

public class StringProcessor {

    public static Map<String, Integer> getWordFrequency(String text) {
        Map<String, Integer> frequency = new HashMap<>();

        // Убираем знаки препинания и приводим к нижнему регистру
        String cleanedText = text.toLowerCase().replaceAll("[^а-яa-z0-9\\s]", "");
        String[] words = cleanedText.split("\\s+");

        for (String word : words) {
            if (!word.isEmpty()) {
                frequency.put(word, frequency.getOrDefault(word, 0) + 1);
            }
        }

        return frequency;
    }

    public static void printWordFrequency(Map<String, Integer> frequency) {
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(frequency.entrySet());

        // Сортировка по убыванию частоты
        entries.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        for (Map.Entry<String, Integer> entry : entries) {
            System.out.printf("%-15s : %d\n", entry.getKey(), entry.getValue());
        }
    }
}