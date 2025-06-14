import index.Index;
import model.Document;
import model.SearchResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import search.BKTree;
import search.SpellAwareSearcher;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class SearchEngineTest {
    private SpellAwareSearcher searcher;

    @BeforeEach
    void setUp() {
        List<Document> documents = List.of(
                new Document("1", "кофе вкусный"),
                new Document("2", "чай горячий"),
                new Document("3", "кофе с молоком")
        );

        Index index = new Index();
        BKTree bkTree = new BKTree();
        for (Document doc : documents) {
            index.addDocument(doc);
            for (String word : doc.getWords())
                bkTree.add(word);
        }

        searcher = new SpellAwareSearcher(index, bkTree);
    }

    @Test
    void testExactSearch() {
        // Проверка точного совпадения
        List<SearchResult> results = searcher.search("кофе", 0);

        assertEquals(2, results.size());
        assertTrue(results.stream().anyMatch(r -> r.document().id().equals("1")));
        assertTrue(results.stream().anyMatch(r -> r.document().id().equals("3")));
    }

    @Test
    void testFuzzySearch() {
        // Проверка поиска с опечаткой
        List<SearchResult> results = searcher.search("коф", 1);

        assertEquals(2, results.size());
        assertEquals("кофе", results.get(0).correctedWord());
        assertEquals("кофе", results.get(1).correctedWord());
    }

    @Test
    void testNoResults() {
        // Проверка случая, когда ничего не найдено
        List<SearchResult> results = searcher.search("вода", 1);
        assertTrue(results.isEmpty());
    }

    @Test
    void testMultiWordSearch() {
        // Проверка поиска по нескольким словам
        List<SearchResult> results = searcher.search("кофэ ча", 1);

        assertEquals(3, results.size());
        assertTrue(results.stream().anyMatch(r -> r.document().id().equals("2") && r.correctedWord().equals("чай")));
    }

    @Test
    void testDocumentContents() {
        // Проверка содержимого документов
        List<SearchResult> results = searcher.search("молоком", 0);

        assertEquals(1, results.size());
        assertEquals("кофе с молоком", results.getFirst().document().text());
    }
}