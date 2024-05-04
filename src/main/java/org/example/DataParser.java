package org.example;

import com.google.common.collect.ImmutableSet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class DataParser {
    private static final File DATA_TEXT_FILE = new File("src/main/java/org/example/RawData.txt");

    static ImmutableSet<Datum> getData() {
        try {
            return new BufferedReader(new FileReader(DATA_TEXT_FILE)).lines().map(Datum::parseLine)
                    .collect(ImmutableSet.toImmutableSet());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
