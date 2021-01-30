package dev.kohn.playground.graph;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DistributedTracingGraphTest {

    private DistributedTracingGraph underTest;

    @BeforeEach
    void setUp() {
        Graph<String, DefaultWeightedEdge> g = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");

        g.setEdgeWeight(g.addEdge("A", "B"), 1);
        g.setEdgeWeight(g.addEdge("B", "C"), 2);

        underTest = new DistributedTracingGraph(g);
    }

    @Test
    void should_throw_exception_if_path_does_not_exists_for_given_path_A_to_X() {
        var givenPath = new String[]{"A", "X"};
        NoSuchTraceException exception = assertThrows(NoSuchTraceException.class, () -> underTest.averageLatencyForTrace(givenPath));

        assertEquals(NoSuchTraceException.DEFAULT_MESSAGE, exception.getMessage());
        assertEquals("A", exception.getSourceVertex());
        assertEquals("X", exception.getDestinationVertex());
    }

    @Test
    void should_return_1_for_given_path_A_to_B_with_weight_1() {
        var expectedWeight = 1;
        var givenPath = new String[]{"A", "B"};
        assertEquals(expectedWeight, underTest.averageLatencyForTrace(givenPath));
    }

    @Test
    void should_return_3_for_given_path_A_to_B_with_weight_1_and_B_to_C_with_weight_2() {
        var expectedWeight = 3;
        var givenPath = new String[]{"A", "B", "C"};
        assertEquals(expectedWeight, underTest.averageLatencyForTrace(givenPath));
    }
}