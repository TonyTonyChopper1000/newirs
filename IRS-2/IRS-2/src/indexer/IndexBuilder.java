
package indexer;

import java.io.*;
import java.util.*;

public class IndexBuilder {
    private final Stopwords stopwords;
    private final PorterStemmer stemmer = new PorterStemmer();
    private final Map<String, Map<String, Integer>> forwardIndex = new HashMap<>();
    private final Map<String, Map<String, Integer>> invertedIndex = new HashMap<>();

    public IndexBuilder(Stopwords stopwords) {
        this.stopwords = stopwords;
    }

    public void buildIndexes(Map<String, List<String>> docs) {
        for (String docID : docs.keySet()) {
            Map<String, Integer> wordFreq = new HashMap<>();
            for (String rawWord : docs.get(docID)) {
                String word = rawWord.toLowerCase().replaceAll("[^a-zA-Z]", "");
                if (word.isEmpty() || stopwords.isStopword(word)) continue;
                String stemmed = stem(word);
                wordFreq.put(stemmed, wordFreq.getOrDefault(stemmed, 0) + 1);

                invertedIndex.putIfAbsent(stemmed, new HashMap<>());
                Map<String, Integer> postings = invertedIndex.get(stemmed);
                postings.put(docID, postings.getOrDefault(docID, 0) + 1);
            }
            forwardIndex.put(docID, wordFreq);
        }
    }

    public void writeForwardIndex(String path) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        for (String docID : forwardIndex.keySet()) {
            writer.write(docID + ": ");
            Map<String, Integer> wordFreq = forwardIndex.get(docID);
            for (Map.Entry<String, Integer> entry : wordFreq.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue() + "; ");
            }
            writer.newLine();
        }
        writer.close();
    }

    public void writeInvertedIndex(String path) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        for (String word : invertedIndex.keySet()) {
            writer.write(word + ": ");
            Map<String, Integer> postings = invertedIndex.get(word);
            for (Map.Entry<String, Integer> entry : postings.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue() + "; ");
            }
            writer.newLine();
        }
        writer.close();
    }

    public String stem(String word) {
        stemmer.add(word.toCharArray(), word.length());
        stemmer.stem();
        return stemmer.toString();
    }

    public void queryInvertedIndex(String term) {
        if (invertedIndex.containsKey(term)) {
            System.out.println("Results for term '" + term + "':");
            invertedIndex.get(term).forEach((doc, freq) ->
                System.out.println("Doc " + doc + " => freq: " + freq));
        } else {
            System.out.println("Term not found in index.");
        }
    }
}
