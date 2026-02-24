# Million Playlist Dataset JSON → SQL Converter  
## ANTLR4-Based JSON Parser Project

---

## Overview

This project uses **ANTLR4** with a JSON grammar to parse the **Million Playlist Dataset (MPD)** and convert selected playlist data into SQL `INSERT INTO` statements.

The program extracts:

- Playlist information (`pid`, `name`)
- Song information (`title`, `duration`)
- Album information
- Artist information
- Relationship entities:
  - `Existence` (Playlist ↔ Song)
  - `Accredation` (Song ↔ Artist)

The formatted SQL output is written to: formattedData.txt  


---

## Requirements

You must have:

- Java (JDK 8 or newer)
- ANTLR4 installed
- Linux-based environment (Ubuntu or university servers recommended)

ANTLR4 is required to generate the lexer, parser, and visitor classes from the JSON grammar.

---

## Installing ANTLR4

1. Go to the official ANTLR download page:

   https://www.antlr.org/download.html

2. Follow the Linux installation instructions.

After installation, you should have:

- `antlr4` command available
- Required `.jar` files
- Symbolic links properly configured

Verify installation:

```bash
which antlr4
```
If installed correctly you will be able to see the location of antlr4

## Project Structure
Model classes:  
Accredation.java  
Album.java  
Song.java  
Artist.java  
Existence.java  
Playlist.java 

Other:  
PlaylistExtractor.java- main program containing Main() method  
JSON.g4- antlr grammar g4 file from https://github.com/antlr/grammars-v4/blob/master/json/JSON.g4  
(ANTLR-generated files from JSON.g4)  
formattedData.txt- output file  

## How to build and run
1. Generate the parser files
```bash
antlr4 JSON.g4
antlr4 JSON.g4 -visitor
```
2. Set classpath (example below)
```bash
cat $(which antlr4)
CP=/usr/share/java/stringtemplate4.jar:/usr/share/java/antlr4.jar:/usr/share/java/antlr4-runtime.jar:/usr/share/java/antlr3-runtime.jar/:/usr/share/java/treelayout.jar
```  
3. Compile and run (replace inputFilename with an input file from data directory)
```bash
javac -cp $CP:. *.java
java -cp $CP:. PlaylistExtractor < data/inputfilename > formattedData.txt
```

## What the program does
1. Uses ANTLR4 to lex and parse JSON input.

2. Traverses the parse tree using a Visitor.

3. Extracts relevant playlist and track information.

4. Constructs in-memory entity objects.

5. Outputs SQL INSERT INTO statements for database population.

## Dataset Liscense and Citation
I got this dataset from Kaggle  
Full reference:  
Ching-Wei Chen, Paul Lamere, Markus Schedl, and Hamed Zamani.  
Recsys Challenge 2018: Automatic Music Playlist Continuation.  
In Proceedings of the 12th ACM Conference on Recommender Systems (RecSys ’18), 2018.  

**IEE**  
C.-W. Chen, P. Lamere, M. Schedl, and H. Zamani, “RecSys Challenge 2018: Automatic Music Playlist Continuation,” in Proceedings of the 12th ACM Conference on Recommender Systems (RecSys ’18), 2018, doi: 10.1145/3240323.3240342.

**APA**   
Chen, C.-W., Lamere, P., Schedl, M., & Zamani, H. (2018). RecSys Challenge 2018: Automatic Music Playlist Continuation. In Proceedings of the 12th ACM Conference on Recommender Systems (RecSys ’18). ACM. https://doi.org/10.1145/3240323.3240342  

## Notes
This project is intended for educational use in compiler construction and database systems.  
The parser uses ANTLR4’s JSON grammar without modification.  
All SQL formatting is handled within PlaylistExtractor.java.  
Ensure ANTLR-generated files are regenerated if JSON.g4 is modified.  
