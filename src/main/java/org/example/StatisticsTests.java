package org.example;

import com.google.common.collect.ImmutableSet;
import org.apache.commons.math3.dfp.DfpField;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

import java.math.BigDecimal;
import java.util.function.Function;
import java.util.stream.DoubleStream;

public class StatisticsTests {
    static final Function<ImmutableSet<Datum>, double[]> defaultFlattener = (data -> data.stream()
            .flatMapToDouble(datum -> DoubleStream.of(datum.MSRP_USD().divide(BigDecimal.valueOf(datum.pageCount()),
                            DfpField.RoundingMode.ROUND_DOWN.ordinal())
                    .doubleValue(), (double) datum.dateOfPublication().toEpochDay())).toArray());
    private static final BigDecimal ALPHA = new BigDecimal(".05");

    public static BigDecimal calculateRSquared(ImmutableSet<Datum> data, Function<ImmutableSet<Datum>, double[]> dataFlattener) {
        OLSMultipleLinearRegression linearRegression = new OLSMultipleLinearRegression();
        System.out.println(data);
        System.out.println(data.size());
        linearRegression.newSampleData(dataFlattener.apply(data), 2, data.size() - 1);
        return BigDecimal.valueOf(linearRegression.calculateRSquared());
    }
}
