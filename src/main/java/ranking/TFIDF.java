package ranking;

import model.Document;
import java.util.*;

public class TFIDF {
    private final Map<String, Double> idfCache = new HashMap<>();

    public double calculate(String word, Document doc, List<Document> allDocs) {
        double tf = calculateTF(word, doc);
        double idf = calculateIDF(word, allDocs);
        return tf * idf;
    }

    private double calculateTF(String word, Document doc) {
        long wordCount = doc.getWords().stream().filter(w -> w.equals(word)).count();
        return (double) wordCount / doc.getWords().size();
    }

    private double calculateIDF(String word, List<Document> allDocs) {
        if (idfCache.containsKey(word))
            return idfCache.get(word);

        long docsWithWord = allDocs.stream().filter(doc -> doc.getWords().contains(word)).count();
        double idf = Math.log((double) allDocs.size() / (docsWithWord + 1));
        idfCache.put(word, idf);
        return idf;
    }
}