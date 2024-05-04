package org.example;

import com.google.common.collect.ImmutableSet;

public class Main {
    public static void main(String[] args) {
        System.out.println(new LinearRegression(DataParser.getData().stream().map(datum -> new Point(
                datum.dateOfPublication().toEpochDay(),
                datum.MSRP_USD().doubleValue() / datum.pageCount())).collect(ImmutableSet.toImmutableSet()))
                .calcPValue(0));


    }
}
