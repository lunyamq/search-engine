package search;

import index.Index;
import model.Document;
import model.SearchResult;

import java.util.*;

public class SpellAwareSearcher {
    private final Index index;
    private final BKTree bkTree;

    public SpellAwareSearcher(Index index, BKTree bkTree) {
        this.index = index;
        this.bkTree = bkTree;
    }

    public List<Document> search1(String query, int maxDistance) {
        Set<Document> results = new HashSet<>();

        for (String word : query.toLowerCase().split("\\s+")) {
            results.addAll(index.search(word));

            if (index.search(word).isEmpty())
                bkTree.search(word, maxDistance).forEach(corrected -> results.addAll(index.search(corrected)));
        }
        return new ArrayList<>(results);
    }

    public List<SearchResult> search(String query, int maxDistance) {
        List<SearchResult> results = new ArrayList<>();

        for (String word : query.toLowerCase().split("\\s+")) {
            List<Document> exactMatches = index.search(word);
            if (!exactMatches.isEmpty()) {
                exactMatches.forEach(doc -> results.add(new SearchResult(word, doc)));
                continue;
            }

            List<String> corrections = bkTree.search(word, maxDistance);
            for (String corrected : corrections)
                index.search(corrected).forEach(doc -> results.add(new SearchResult(corrected, doc)));
        }

        return results;
    }
}