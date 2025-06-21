package ru.sfedu.search_engine.api;

import ru.sfedu.search_engine.index.Index;
import ru.sfedu.search_engine.models.*;
import ru.sfedu.search_engine.repository.ProductRepository;
import ru.sfedu.search_engine.search.Searcher;
import ru.sfedu.search_engine.search.BKTree;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import ru.sfedu.search_engine.utils.SplitUtil;

@RestController
@RequestMapping("/")
public class ProductController {
    private final Searcher searcher;
    private final ProductRepository repository;

    public ProductController(ProductRepository repository) {
        this.repository = repository;
        List<Product> products = repository.findAll();

        Index index = new Index();
        BKTree bkTree = new BKTree();

        // indexing && building bkTree
        for (Product product : products) {
            index.addProduct(product);
            for (String word : SplitUtil.getWords(product.name()))
                bkTree.add(word);
        }

        this.searcher = new Searcher(index, bkTree);
    }

    // add new product && update index + bkTree
    @PostMapping("/products")
    public void addProduct(@RequestBody Product product) {
        repository.save(product);
        searcher.index().addProduct(product);
        for (String word : SplitUtil.getWords(product.name()))
            searcher.bkTree().add(word);
    }

    // search product w/ type-error
    @GetMapping("/search")
    public List<ProductSearchResult> searchProducts(
            @RequestParam String query,
            @RequestParam(defaultValue = "1") int maxDistance) {

        return searcher.search(query, maxDistance);
    }
}
