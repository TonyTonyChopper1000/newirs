package indexer;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        String stopwordsPath = "input/stopwordlist.txt";
        String mode = (args.length > 0) ? args[0] : "test"; // default to test mode
        String inputPath = mode.equals("corpus") ? "input/ft911" : "input/testdata.txt";

        Stopwords stopwords = new Stopwords(stopwordsPath);
        Parser parser = new Parser(inputPath);
        IndexBuilder indexBuilder = new IndexBuilder(stopwords);

        Map<String, List<String>> documents = parser.parse();
        indexBuilder.buildIndexes(documents);

        indexBuilder.writeForwardIndex("output/forward_index.txt");
        indexBuilder.writeInvertedIndex("output/inverted_index.txt");

        // Allow querying
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a word to search: ");
        String query = sc.nextLine().toLowerCase();
        String stemmed = indexBuilder.stem(query);
        indexBuilder.queryInvertedIndex(stemmed);
        sc.close();
    }
}
