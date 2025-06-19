package ru.sfedu.search_engine.index;

import ru.sfedu.search_engine.model.Product;
import ru.sfedu.search_engine.utils.SplitUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Index {
    private final Map<String, List<Product>> index = new HashMap<>();

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

    public List<Product> search(String word) {
        List<Product> docs = index.get(word);
        return docs != null ? docs : List.of();
    }
}
