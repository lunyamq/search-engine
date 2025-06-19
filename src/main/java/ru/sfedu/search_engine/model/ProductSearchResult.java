package ru.sfedu.search_engine.model;

public record ProductSearchResult(String correctedWord, Long productId, String productName, double price) {
    @Override
    public String toString() {
        return String.format("[Исправлено: '%s'] %s: %s", correctedWord, productId, productName);
    }
}
