package dev.kohn.playground;

import dev.kohn.playground.parse.GraphGenerator;
import dev.kohn.playground.tracing.DistributedTracingGraph;

public class App {
    public static void main(String[] args) {

        final DistributedTracingGraph distributedTracingGraph = new DistributedTracingGraph(
                GraphGenerator.createDirectedWeightedGraphFromFile("/home/torsten/development/test.txt"));

        System.out.println(" 1. " + distributedTracingGraph.averageLatencyFor("A", "B", "C"));
        System.out.println(" 2. " + distributedTracingGraph.averageLatencyFor("A", "D"));
        System.out.println(" 3. " + distributedTracingGraph.averageLatencyFor("A", "D", "C"));
        System.out.println(" 4. " + distributedTracingGraph.averageLatencyFor("A", "E", "B", "C", "D"));
        System.out.println(" 5. " + distributedTracingGraph.averageLatencyFor("A", "E", "D"));
        System.out.println(" 6. " + distributedTracingGraph.numberOfTracesWithConfiguredHops("C", "C", 1, 3));
        System.out.println(" 7. " + distributedTracingGraph.numberOfTracesWithConfiguredHops("A", "C", 4, 4));
        System.out.println(" 8. " + distributedTracingGraph.latencyForShortestTraceBetween("A", "C"));
        System.out.println(" 9. " + distributedTracingGraph.latencyForShortestTraceBetween("B", "B"));
        System.out.println("10. skipped");
    }
}
