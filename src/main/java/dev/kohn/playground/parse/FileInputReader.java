package dev.kohn.playground.parse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class FileInputReader {
    public static List<String> allLinesFromFile(String path) {
        try {
            return Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return Collections.emptyList();
    }
}
