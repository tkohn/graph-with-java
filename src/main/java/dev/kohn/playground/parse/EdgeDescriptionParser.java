package dev.kohn.playground.parse;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class EdgeDescriptionParser {

    public static List<EdgeDescription> allEdgeDescriptionFor(List<String> input) {
        return input
                .stream()
                .flatMap(line -> Arrays.stream(line.split(",").clone()))
                .map(String::trim)
                .filter(edgeDescription -> edgeDescription.length() >= 3)
                .map(edgeDescription -> {
                    char[] edge = edgeDescription.toCharArray();
                    return new EdgeDescription(
                            String.valueOf(edge[0]),
                            String.valueOf(edge[1]),
                            Double.parseDouble(new String(Arrays.copyOfRange(edge, 2, edge.length))));
                })
                .collect(Collectors.toList());
    }

}
