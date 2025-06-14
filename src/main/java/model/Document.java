package model;

import java.util.ArrayList;
import java.util.List;

public record Document(String id, String text) {
    public List<String> getWords() {
        List<String> words = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        String lower = text.toLowerCase();

        for (int i = 0; i < lower.length(); i++) {
            char c = lower.charAt(i);
            if (Character.isLetterOrDigit(c))
                sb.append(c);
            else if (Character.isWhitespace(c)) {
                if (!sb.isEmpty()) {
                    words.add(sb.toString());
                    sb.setLength(0);
                }
            }
        }

        if (!sb.isEmpty())
            words.add(sb.toString());

        return words;
    }
}