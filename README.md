# Graph with Java

The programme reads in a text file and creates a graph with it.
The graph is then used to answer questions (see the [App.class](src/main/java/dev/kohn/playground/App.java)).

To describe a directed graph, list the individual edges in the following format:
VertexVertexWeight e.g. `AB1` means that there is a connection from vertex A to vertex B with a weight of 1.

The sample below shows the possible contents of a text file:

```
AB5, BC4,
CD8, DC8, DE6,
```

## Development

### Build and Run the Program

Java version 15 is specified as the compiler target.

To build an executable JAR, use one of the following commands in the terminal.

```
# with installed maven
mvn package

# with maven wrapper (Unix)
./mvnw package

# with maven wrapper (Windows)
./mvnw.cmd package
```

After the JAR has been successfully built, the programme can be started with the following command. 
It is important to specify a path for reading in the graph structure.

```
java --enable-preview -cp target/graph-with-java-1.0.0-jar-with-dependencies.jar <PATH_TO_FILE>

# for example  
java \
  --enable-preview \
  -cp target/graph-with-java-1.0.0-jar-with-dependencies.jar dev.kohn.playground.App /home/torsten/development/test.txt
```


### Run Tests

To run the tests, one of these commands must be used.

```
# with installed maven
mvn test

# with maven wrapper (Unix)
./mvnw test

# with maven wrapper (Windows)
./mvnw.cmd test
```
