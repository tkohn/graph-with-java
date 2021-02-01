package dev.kohn.playground.tracing;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DistributedTracingGraphTest {

    private static final String EXPECTED_ERROR_MESSAGE = "NO SUCH TRACE";
    private DistributedTracingGraph underTest;

    @BeforeEach
    void setUp() {
        Graph<String, DefaultWeightedEdge> g = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");
        g.addVertex("D");
        g.addVertex("X");

        g.setEdgeWeight(g.addEdge("A", "B"), 1);
        g.setEdgeWeight(g.addEdge("B", "C"), 2);
        g.setEdgeWeight(g.addEdge("C", "D"), 1);
        g.setEdgeWeight(g.addEdge("D", "C"), 2);

        underTest = new DistributedTracingGraph(g);
    }

    @Test
    void should_output_NO_SUCH_TRACE_if_path_does_not_exists_for_given_path_A_to_X() {
        var givenPath = new String[]{"A", "X"};
        assertEquals(EXPECTED_ERROR_MESSAGE, underTest.averageLatencyFor(givenPath));
    }

    @Test
    void should_return_1_for_given_path_A_to_B_with_weight_1() {
        var expectedWeight = "1";
        var givenPath = new String[]{"A", "B"};
        assertEquals(expectedWeight, underTest.averageLatencyFor(givenPath));
    }

    @Test
    void should_return_3_for_given_path_A_to_B_with_weight_1_and_B_to_C_with_weight_2() {
        var expectedWeight = "3";
        var givenPath = new String[]{"A", "B", "C"};
        assertEquals(expectedWeight, underTest.averageLatencyFor(givenPath));
    }

    @Test
    void should_return_1_as_number_of_traces_for_start_C_and_end_C() {
        assertEquals("1", underTest.numberOfTracesWithConfiguredHops("C", "C", 1, 2));
    }

    @Test
    void should_return_0_as_number_of_traces_for_start_A_and_end_X() {
        assertEquals("0", underTest.numberOfTracesWithConfiguredHops("A", "X", 1, 2));
    }

    @Test
    void should_return_latency_of_3_for_shortest_trace_between_C_and_C() {
        assertEquals("3", underTest.latencyForShortestTraceBetween("C", "C"));
    }

    @Test
    void should_return_NO_SUCH_TRACE_for_shortest_trace_between_C_and_X() {
        assertEquals(EXPECTED_ERROR_MESSAGE, underTest.latencyForShortestTraceBetween("C", "X"));
    }
}