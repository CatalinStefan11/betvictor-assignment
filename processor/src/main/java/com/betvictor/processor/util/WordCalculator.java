package com.betvictor.processor.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordCalculator {

  public static String calculateMostFrequentWord(List<String[]> paragraphs) {
    Map<String, Integer> occurrences = new HashMap<>();

    paragraphs.stream()
        .flatMap(Arrays::stream)
        .forEach((word) -> occurrences.compute(word, (key, value) -> value != null ? value + 1 : 1));

    return occurrences.entrySet().stream()
        .max(Map.Entry.comparingByValue())
        .map(Map.Entry::getKey)
        .orElse("");
  }

  public static int calculateAverageParagraphSize(List<String[]> paragraphWords) {
    int totalWords = paragraphWords.stream()
        .mapToInt(words -> words.length)
        .sum();

    return paragraphWords.isEmpty() ? 0 : totalWords / paragraphWords.size();
  }
}
