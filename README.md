# Analyzer
PdG Analizador

This software is partial implementation of the MAPE-K loop, which contains the Monitor, Analyzer and Knowledge base.

This software is build with FraSCAti as a middleware, PostgreSQL as his database and RabittMQ as the message handler and the communitarion of events between mape-k and the target system, to make a loop over a target system and measure his quality.

### FraSCAti
To correctly compile with this middleware, it is important to update the sources with the files named here descriptor https://github.com/jachinte/frascati-binaries

### PostgreSQL
The talbes and all the information are saved in the Schema file, just have to import to the database server

### Compile instructions
This are the compile instructions for deploy this project

Have the java_home set up to a 1.6 jdk

frascati compile src MAPEK /{project-path}/MAPEK/library-0.0.1-SNAPSHOT-jar-with-dependencies.jar:/{project-path}/MAPEK/commons-math3-3.5.jar
