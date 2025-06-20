package ru.sfedu.search_engine.utils;

import java.util.ArrayList;
import java.util.List;

public class SplitUtil {
    // just split words
    public static List<String> getWords(String src) {
        List<String> words = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        String lower = src.toLowerCase();

        for (int i = 0; i < lower.length(); i++) {
            char c = lower.charAt(i);
            if (Character.isLetter(c))
                sb.append(c);
            else if (Character.isWhitespace(c) && !sb.isEmpty()) {
                words.add(sb.toString());
                sb.setLength(0);
            }
        }

        if (!sb.isEmpty())
            words.add(sb.toString());

        return words;
    }
}
