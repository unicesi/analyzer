# Analyzer
PdG Analizador

This software is partial implementation of the MAPE-K loop, which contains the Monitor, Analyzer and Knowledge base.

This software is build with FraSCAti as a middleware, PostgreSQL as his database and RabittMQ as the message handler and the communitarion of events between mape-k and the target system, to make a loop over a target system and measure his quality.

### Compile
This project requires the jdk 1.6, to compile you need to import in this project to your workspace as a java project, and export as a normal jar. To run the command lines are the following: 'java -jar path/mapek.jar' and the test project must be deployed with in glassfish and the rabbitmq.

### PostgreSQL
The talbes and all the information are saved in the Schema file, just have to import to the database server

### Compile instructions
This are the compile instructions for deploy this project

Have the java_home set up to a 1.6 jdk

frascati compile src MAPEK /{project-path}/MAPEK/library-0.0.1-SNAPSHOT-jar-with-dependencies.jar:/{project-path}/MAPEK/commons-math3-3.5.jar
