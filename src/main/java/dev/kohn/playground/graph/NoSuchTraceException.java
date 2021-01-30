package dev.kohn.playground.graph;

public class NoSuchTraceException extends RuntimeException {
    public final static String DEFAULT_MESSAGE = "NO SUCH TRACE";
    private final String sourceVertex;
    private final String destinationVertex;

    public NoSuchTraceException(String sourceVertex, String destinationVertex) {
        super(DEFAULT_MESSAGE);
        this.sourceVertex = sourceVertex;
        this.destinationVertex = destinationVertex;
    }

    public String getSourceVertex() {
        return sourceVertex;
    }

    public String getDestinationVertex() {
        return destinationVertex;
    }
}
