package ru.sfedu.search_engine.search;

import ru.sfedu.search_engine.index.Index;
import ru.sfedu.search_engine.model.Product;
import ru.sfedu.search_engine.model.ProductSearchResult;
import ru.sfedu.search_engine.utils.SplitUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public record Searcher(Index index, BKTree bkTree) {
    public List<ProductSearchResult> search(String query, int maxDistance) {
        Set<ProductSearchResult> results = new HashSet<>();

        for (String word : SplitUtil.getWords(query.toLowerCase())) {
            List<Product> exactMatches = index.search(word);
            if (!exactMatches.isEmpty()) {
                for (Product product : exactMatches)
                    results.add((new ProductSearchResult(word, product.id(), product.name(), product.price())));

                continue;
            }

            List<String> corrections = bkTree.find(word, maxDistance);
            for (String corrected : corrections)
                for (Product product : index.search(corrected))
                    results.add(new ProductSearchResult(corrected, product.id(), product.name(), product.price()));
        }

        return new ArrayList<>(results);
    }
}
