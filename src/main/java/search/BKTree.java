package search;

import java.util.*;

public class BKTree {
    private Node root;

    public void add(String word) {
        if (root == null) {
            root = new Node(word);
        } else {
            root.add(word);
        }
    }

    public List<String> search(String word, int maxDistance) {
        List<String> results = new ArrayList<>();
        if (root != null) {
            root.search(word, maxDistance, results);
        }
        return results;
    }

    private static class Node {
        final String word;
        final Map<Integer, Node> children = new HashMap<>();

        Node(String word) {
            this.word = word;
        }

        void add(String word) {
            int distance = Levenshtein.distance(this.word, word);
            Node child = children.get(distance);
            if (child == null) {
                children.put(distance, new Node(word));
            } else {
                child.add(word);
            }
        }

        void search(String word, int maxDistance, List<String> results) {
            int distance = Levenshtein.distance(this.word, word);
            if (distance <= maxDistance) {
                results.add(this.word);
            }
            for (int d = Math.max(1, distance - maxDistance); d <= distance + maxDistance; d++) {
                Node child = children.get(d);
                if (child != null) {
                    child.search(word, maxDistance, results);
                }
            }
        }
    }
}