package org.example;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public record Datum(String bookTitle, String authorName, int pageCount, LocalDate dateOfPublication,
                    BigDecimal MSRP_USD) {
    private static final String DELIMITER_REGEX = "\\s\\s";

    public static Datum parseLine(String line) {
        String[] points = Arrays.stream(line.split(DELIMITER_REGEX)).map(string -> string.trim().toLowerCase())
                .toArray(String[]::new);
        System.out.println(Arrays.toString(points));
        return new Datum(points[0].toLowerCase(),
                points[1].toLowerCase(),
                Integer.parseInt(points[2], 10),
                //YYYY MM DD
                LocalDate.parse(Arrays.stream(points[3].split("\\.")).map(n -> n.length() == 1 ? "0" + n : n)
                        .reduce((a, b) -> a + "." + b).get(), DateTimeFormatter.ofPattern("yyyy.MM.dd")),
                new BigDecimal(points[4]));
    }
}
