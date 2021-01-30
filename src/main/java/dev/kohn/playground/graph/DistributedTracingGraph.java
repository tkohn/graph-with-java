package dev.kohn.playground.graph;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.Arrays;
import java.util.Objects;

public final class DistributedTracingGraph {

    private final Graph<String, DefaultWeightedEdge> serviceGraphWithLatency;

    public DistributedTracingGraph(Graph<String, DefaultWeightedEdge> serviceGraphWithLatency) {
        this.serviceGraphWithLatency = serviceGraphWithLatency;
    }

    /**
     * Calculates the average latency for a given trace in the whole graph.
     *
     * @param vertexes represents a trace
     * @return latency
     * @throws NoSuchTraceException will be thrown if a a part of the trace does not exists in the whole graph
     */
    public double averageLatencyForTrace(String... vertexes) throws NoSuchTraceException {
        if (vertexes.length == 1) {
            return 0;
        }

        final var edge = this.serviceGraphWithLatency.getEdge(vertexes[0], vertexes[1]);

        if (Objects.nonNull(edge)) {
            final var edgeWeight = this.serviceGraphWithLatency.getEdgeWeight(edge);
            return edgeWeight + averageLatencyForTrace(Arrays.copyOfRange(vertexes, 1, vertexes.length));
        } else {
            throw new NoSuchTraceException(vertexes[0], vertexes[1]);
        }
    }
}
