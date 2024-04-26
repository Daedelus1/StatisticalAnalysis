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
        return new Datum(points[0],
                points[1],
                Integer.parseInt(points[2], 10),
                //YYYY MM DD
                LocalDate.parse(points[3].replaceAll("\\.", ""), DateTimeFormatter.BASIC_ISO_DATE),
                new BigDecimal(points[4]));
    }
}
