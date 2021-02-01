package dev.kohn.playground.tracing;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents a distributed tracing graph.
 * It can calculate latency for a given trace,
 * number of traces for start and end service or latency for the shortest path.
 */
public final class DistributedTracingGraph {
    private final static String ERROR_MESSAGE = "NO SUCH TRACE";
    private final Graph<String, DefaultWeightedEdge> graph;
    private final AllDirectedPaths<String, DefaultWeightedEdge> allDirectedPaths;

    public DistributedTracingGraph(Graph<String, DefaultWeightedEdge> graph) {
        this.graph = graph;
        this.allDirectedPaths = new AllDirectedPaths<>(this.graph);
    }

    /**
     * Calculates the average latency for a trace.
     * If the given trace is not connected, the text No SUCH TRACE is output instead of the a latency value.
     *
     * @param trace to calculate the average latency
     * @return value average latency or "NO SUCH TRACE"
     */
    public String averageLatencyFor(String... trace) {
        try {
            return String
                    .valueOf(Double.valueOf(calculateAverageLatencyFor(trace)).intValue());
        } catch (NoSuchTraceException exception) {
            return ERROR_MESSAGE;
        }
    }

    private double calculateAverageLatencyFor(String[] trace) throws NoSuchTraceException {
        if (trace.length == 1) {
            return 0;
        }

        final var edge = this.graph.getEdge(trace[0], trace[1]);

        if (Objects.nonNull(edge)) {
            final var edgeWeight = this.graph.getEdgeWeight(edge);
            return edgeWeight + calculateAverageLatencyFor(Arrays.copyOfRange(trace, 1, trace.length));
        } else {
            throw new NoSuchTraceException(trace[0], trace[1]);
        }
    }

    /**
     * Calculates all traces between a start and end point.
     *
     * @param startService start point
     * @param targetService end point
     * @param minimumPathLength the minimum number of hops in th trace
     * @param maximumPathLength the maximum number of allowed hops in the trace
     * @return the number of traces between two services
     */
    public String numberOfTracesWithConfiguredHops(
            String startService, String targetService, int minimumPathLength, int maximumPathLength) {

        List<GraphPath<String, DefaultWeightedEdge>> allTracesForMaximumPathLength =
                getAllPaths(startService, targetService, maximumPathLength);

        return String.valueOf(allTracesForMaximumPathLength
                .stream()
                .filter(path -> path.getLength() >= minimumPathLength && path.getLength() <= maximumPathLength)
                .count());
    }

    /**
     * Calculates all traces between a start and end point.
     *
     * @param startService start point
     * @param targetService end point
     * @param minimumPathLength the minimum number of hops in th trace
     * @param maximumPathLength the maximum number of allowed hops in the trace
     * @param maximumLatency the maximum latency for the trace
     * @return the number of traces between two services for a maximum latency
     */
    public String numberOfTracesWithConfiguredPathLengthAndWeight(
            String startService, String targetService, int minimumPathLength, int maximumPathLength, int maximumLatency) {

        List<GraphPath<String, DefaultWeightedEdge>> allTracesForMaximumPathLength =
                getAllPaths(startService, targetService, maximumPathLength);

        return String.valueOf(allTracesForMaximumPathLength
                .stream()
                .filter(path -> path.getLength() >= minimumPathLength && path.getWeight() <= maximumLatency)
                .count());
    }

    private List<GraphPath<String, DefaultWeightedEdge>> getAllPaths(
            String startService, String targetService, int maximumPathLength){
        return allDirectedPaths.getAllPaths(startService, targetService, false, maximumPathLength);
    }

    /**
     * Calculates the lowest latency for a start and end service.
     * If there is no path between start and end, the text NO SUCH TRACE is returned.
     *
     * @param startService start point
     * @param targetService end point
     * @return the latency or "NO SUCH TRACE"
     */
    public String latencyForShortestTraceBetween(String startService, String targetService) {
        if (startService.equals(targetService)) {
            return latencyForShortestCycleFor(startService);
        }

        return Optional.ofNullable(shortestPathBetween(startService, targetService))
                .map(GraphPath::getWeight)
                .map(weight -> String.valueOf(weight.intValue()))
                .orElse(ERROR_MESSAGE);
    }

    private String latencyForShortestCycleFor(String service) {
        Optional<Double> lowestAverage = this.graph.outgoingEdgesOf(service)
                .stream()
                .map(edge -> new SimpleEntry<>(this.graph.getEdgeWeight(edge), this.graph.getEdgeTarget(edge)))
                .map(tuple -> new SimpleEntry<>(tuple.getKey(), shortestPathBetween(tuple.getValue(), service)))
                .filter(tuple -> Objects.nonNull(tuple.getValue()))
                .map(tuple -> tuple.getKey() + tuple.getValue().getWeight())
                .min(Double::compareTo);

        return lowestAverage
                .map(value -> String.valueOf(value.intValue()))
                .orElse(ERROR_MESSAGE);
    }

    private GraphPath<String, DefaultWeightedEdge> shortestPathBetween(String startService, String targetService) {
        return DijkstraShortestPath.findPathBetween(graph, startService, targetService);
    }

}
