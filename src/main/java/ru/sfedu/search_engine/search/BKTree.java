package ru.sfedu.search_engine.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BKTree {
    private static class Node {
        final String word;
        final Map<Integer, Node> children = new HashMap<>();

        Node(String word) {
            this.word = word;
        }

        void add(String word) {
            int distance = Levenshtein.distance(this.word, word);
            Node child = children.get(distance);
            if (child == null)
                children.put(distance, new Node(word));
            else
                child.add(word);
        }

        void find(String word, int maxDistance, List<String> results) {
            int distance = Levenshtein.distance(this.word, word);
            if (distance <= maxDistance)
                results.add(this.word);

            for (int d = Math.max(1, distance - maxDistance); d <= distance + maxDistance; d++) {
                Node child = children.get(d);
                if (child != null)
                    child.find(word, maxDistance, results);
            }
        }
    }
    

    private Node root;

    public void add(String word) {
        if (root == null)
            root = new Node(word);
        else
            root.add(word);
    }

    public List<String> find(String word, int maxDistance) {
        List<String> results = new ArrayList<>();
        if (root != null)
            root.find(word, maxDistance, results);

        return results;
    }
}
