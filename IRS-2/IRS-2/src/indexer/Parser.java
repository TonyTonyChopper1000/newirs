package indexer;

import java.io.*;
import java.util.*;

public class Parser {
    private final String path;

    public Parser(String path) {
        this.path = path;
    }

    public Map<String, List<String>> parse() throws IOException {
        Map<String, List<String>> docs = new HashMap<>();
        File fileOrDir = new File(path);

        if (fileOrDir.isDirectory()) {
            for (File file : fileOrDir.listFiles()) {
                parseFile(file, docs);
            }
        } else {
            parseFile(fileOrDir, docs);
        }
        return docs;
    }

    private void parseFile(File file, Map<String, List<String>> docs) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        String docID = null;
        StringBuilder content = new StringBuilder();

        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.startsWith("<DOCNO>")) {
                docID = line.replace("<DOCNO>", "").replace("</DOCNO>", "").trim();
            } else if (line.startsWith("<TEXT>")) {
                content = new StringBuilder();
            } else if (line.startsWith("</TEXT>")) {
                if (docID != null) {
                    List<String> words = Arrays.asList(content.toString().split("\\s+"));
                    docs.put(docID, words);
                }
            } else if (!line.startsWith("<") && docID != null) {
                content.append(line).append(" ");
            }
        }
        br.close();
    }
}
