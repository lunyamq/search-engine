package ru.sfedu.search_engine.api;

import ru.sfedu.search_engine.index.Index;
import ru.sfedu.search_engine.model.*;
import ru.sfedu.search_engine.repository.ProductRepository;
import ru.sfedu.search_engine.search.Searcher;
import ru.sfedu.search_engine.search.BKTree;

import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class ProductController {
    private final Searcher searcher;

    public ProductController(ProductRepository repository) {
        List<Product> products = repository.findAll();

        Index index = new Index();
        BKTree bkTree = new BKTree();

        for (Product product : products) {
            index.addProduct(product);
            for (String word : product.getWords())
                bkTree.add(word);
        }

        this.searcher = new Searcher(index, bkTree);
    }

    @GetMapping("/search")
    public List<ProductSearchResult> searchProducts(
            @RequestParam String query,
            @RequestParam(defaultValue = "1") int maxDistance) {

        return searcher.search(query.toLowerCase(), maxDistance);
    }
}
