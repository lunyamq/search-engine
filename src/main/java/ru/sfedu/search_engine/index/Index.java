package ru.sfedu.search_engine.index;

import ru.sfedu.search_engine.models.Product;
import ru.sfedu.search_engine.utils.SplitUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Index {
    private final Map<String, List<Product>> index = new HashMap<>();

    // inverted index: maps each word to a list of products containing it
    public void addProduct(Product product) {
        for (String word : SplitUtil.getWords(product.name())) {
            List<Product> list = index.get(word);
            if (list == null) {
                list = new ArrayList<>();
                index.put(word, list);
            }

            list.add(product);
        }
    }

    // searches for all products containing the exact word
    public List<Product> getList(String word) {
        List<Product> docs = index.get(word);
        return docs != null ? docs : List.of();
    }
}
