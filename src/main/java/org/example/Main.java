package org.example;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");

        System.out.println(DataParser.getData());
        DecimalFormat df = new DecimalFormat("$##########.00");
        DataPusher dataPusher = new DataPusher();
        DataParser.getData().stream().sorted(Comparator.comparing(Datum::dateOfPublication)).forEach(datum -> dataPusher.addRow(new String[]{
                datum.bookTitle(), datum.authorName(), String.valueOf(datum.pageCount()),
                datum.dateOfPublication().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")),
                df.format(datum.MSRP_USD())
        }));
        dataPusher.setRow(0, new String[]{"Name", "Author", "Page Count", "Publication Date", "Cost"});
        dataPusher.writeToFile(new File("foo.xls"));
        System.out.println("Successfully Saved");
    }
}