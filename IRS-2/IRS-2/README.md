# IR Phase 2: Indexer

## Project Description
This project implements the **indexing engine** (Phase 2) of an Information Retrieval System. It constructs both a forward index and an inverted index using a provided document corpus (`ft911`) and supports user queries for individual terms. It reuses Phase 1 components such as stopword filtering and Porter stemming.

---

## Folder Structure
```
IR_Phase2/
├── input/
│   ├── stopwordlist.txt
│   ├── testdata.txt
│   └── ft911/
├── output/
│   ├── forward_index.txt
│   └── inverted_index.txt
├── src/
│   └── indexer/
│       ├── Main.java
│       ├── Parser.java
│       ├── IndexBuilder.java
│       ├── Stopwords.java
│       └── PorterStemmer.java
```

---

## How to Compile
Run the following from the project root directory:
```bash
javac -d bin src/indexer/*.java
```

## How to Run

### Test Mode (using testdata.txt):
```bash
java -cp bin indexer.Main test
```

### Corpus Mode (using full ft911/ dataset):
```bash
java -Xmx1G -cp bin indexer.Main corpus
```

---

## Output
- `output/forward_index.txt` — maps each document to word frequencies.
- `output/inverted_index.txt` — maps each word to document occurrences and frequencies.

---

## Query Example
```
Enter a word to search: work
Results for term 'work':
Doc 1 => freq: 1
Doc 2 => freq: 1
Doc 3 => freq: 1
```

