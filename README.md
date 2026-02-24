# antlr4
this project relys on antlr4 to create the parsing. To actually run this program you will need to install antlr4 under a linux system (fox servers or Ubuntu)  
go to this webpage and follow install instructions (if successful you will have like two jar files and two sym links) https://www.antlr.org/download.html  

# how to run
Make sure antlr4 is okay by running antlr4 JSON.g4  
Then find your classpath by using the cat $(which antlr4) command  
Then create a variable with what is shown as CLASSPATH= by running CP=/usr/share/java/stringtemplate4.jar:/usr/share/java/antlr4.jar:/usr/share/java/antlr4-runtime.jar:/usr/share/java/antlr3-runtime.jar/:/usr/share/java/treelayout.jar  
now, compile all of the java files using javac -cp $CP:. *.java  
finally run the program by saying java -cp $CP:. PlaylistExtractor.java < data/inputfilename > outputfilename  
all formatted contents will appear in outputfilename when you are done.  

# files
the main program is in PlaylistExtractor.java, everything else is a helper.
