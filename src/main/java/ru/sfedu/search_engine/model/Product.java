package ru.sfedu.search_engine.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document("Products")
public record Product(@Id Long id, String name, double price) {
    public List<String> getWords() {
        List<String> words = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        String lower = name.toLowerCase();

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
