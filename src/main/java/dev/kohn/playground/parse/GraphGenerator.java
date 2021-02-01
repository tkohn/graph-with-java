package dev.kohn.playground.parse;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.util.List;

public final class GraphGenerator {

    public static Graph<String, DefaultWeightedEdge> createDirectedWeightedGraphFromFile(final String inputPathToFile){
        Graph<String, DefaultWeightedEdge> resultedGraph = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);

        List<EdgeDescription> allEdgesFromFile =
                EdgeDescriptionParser.allEdgeDescriptionFor(FileInputReader.allLinesFromFile(inputPathToFile));

        for(EdgeDescription edgeDescription : allEdgesFromFile){
            resultedGraph.addVertex(edgeDescription.source());
            resultedGraph.addVertex(edgeDescription.destination());
            resultedGraph.setEdgeWeight(
                    resultedGraph.addEdge(edgeDescription.source(), edgeDescription.destination()),
                    edgeDescription.weight());
        }
        return resultedGraph;
    }
}
