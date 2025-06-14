package index;

import model.Document;
import java.util.*;

public class Index {
    private final Map<String, List<Document>> index = new HashMap<>();

    public void addDocument(Document doc) {
        for (String word : doc.getWords()) {
            List<Document> list = index.get(word);
            if (list == null) {
                list = new ArrayList<>();
                index.put(word, list);
            }

            list.add(doc);
        }
    }

    public List<Document> search(String word) {
        List<Document> docs = index.get(word);
        return docs != null ? docs : List.of();
    }
}