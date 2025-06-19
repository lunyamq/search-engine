package ru.sfedu.search_engine.search;

import ru.sfedu.search_engine.index.Index;
import ru.sfedu.search_engine.model.Product;
import ru.sfedu.search_engine.model.ProductSearchResult;

import java.util.*;

public class Searcher {
    private final Index index;
    private final BKTree bkTree;

    public Searcher(Index index, BKTree bkTree) {
        this.index = index;
        this.bkTree = bkTree;
    }

    public List<ProductSearchResult> search(String query, int maxDistance) {
        List<ProductSearchResult> results = new ArrayList<>();

        for (String word : query.toLowerCase().split("\\s+")) {
            List<Product> exactMatches = index.search(word);
            if (!exactMatches.isEmpty()) {
                exactMatches.forEach(product -> results.add(new ProductSearchResult(word, product.id(), product.name(), product.price())));
                continue;
            }

            List<String> corrections = bkTree.search(word, maxDistance);
            for (String corrected : corrections)
                index.search(corrected).forEach(product -> results.add(new ProductSearchResult(corrected, product.id(), product.name(), product.price())));
        }

        return results;
    }
}
