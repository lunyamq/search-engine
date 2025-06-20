package ru.sfedu.search_engine.models;

import java.util.Objects;

public record ProductSearchResult(String correctedWord, Long productId, String productName, double price) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductSearchResult that = (ProductSearchResult) o;
        return productId.equals(that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
}
