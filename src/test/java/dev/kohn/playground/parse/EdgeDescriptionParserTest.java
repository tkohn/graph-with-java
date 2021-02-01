package dev.kohn.playground.parse;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class EdgeDescriptionParserTest {

    @Test
    void should_parse_string_AB5_to_a_valid_EdgeDescription() {
        var expectedResult = new EdgeDescription("A", "B", 5);
        var givenInput = Collections.singletonList("AB5");
        assertEquals(expectedResult, EdgeDescriptionParser.allEdgeDescriptionFor(givenInput).get(0));
    }
}