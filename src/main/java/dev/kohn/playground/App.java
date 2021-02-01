package dev.kohn.playground;

import dev.kohn.playground.parse.GraphGenerator;
import dev.kohn.playground.tracing.DistributedTracingGraph;

public class App {
    public static void main(String[] args) {

        if(args.length == 0){
            System.out.println("Please provide a path to a text file which contains the graph description.");
            return;
        }

        final DistributedTracingGraph distributedTracingGraph = new DistributedTracingGraph(
                GraphGenerator.createDirectedWeightedGraphFromFile(args[0]));

        System.out.println(" 1. " + distributedTracingGraph.averageLatencyFor("A", "B", "C"));
        System.out.println(" 2. " + distributedTracingGraph.averageLatencyFor("A", "D"));
        System.out.println(" 3. " + distributedTracingGraph.averageLatencyFor("A", "D", "C"));
        System.out.println(" 4. " + distributedTracingGraph.averageLatencyFor("A", "E", "B", "C", "D"));
        System.out.println(" 5. " + distributedTracingGraph.averageLatencyFor("A", "E", "D"));
        System.out.println(" 6. " + distributedTracingGraph.numberOfTracesWithConfiguredHops("C", "C", 1, 3));
        System.out.println(" 7. " + distributedTracingGraph.numberOfTracesWithConfiguredHops("A", "C", 4, 4));
        System.out.println(" 8. " + distributedTracingGraph.latencyForShortestTraceBetween("A", "C"));
        System.out.println(" 9. " + distributedTracingGraph.latencyForShortestTraceBetween("B", "B"));
        System.out.println("10. " + distributedTracingGraph.numberOfTracesWithConfiguredPathLengthAndWeight("C", "C", 1, 29,29));
    }
}
