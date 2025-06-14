package model;

/**
 * @param correctedWord Исправленное слово (например, "кофе")
 * @param document      Найденный документ
 **/
public record SearchResult(String correctedWord, Document document) {
    @Override
    public String toString() {
        return String.format("[Исправлено: '%s'] %s: %s", correctedWord, document.id(), document.text());
    }
}
