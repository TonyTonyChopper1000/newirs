
package indexer;

import java.io.*;
import java.util.*;

public class Stopwords {
    private final Set<String> stopwords = new HashSet<>();

    public Stopwords(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        while ((line = br.readLine()) != null) {
            stopwords.add(line.trim().toLowerCase());
        }
        br.close();
    }

    public boolean isStopword(String word) {
        return stopwords.contains(word);
    }
}
