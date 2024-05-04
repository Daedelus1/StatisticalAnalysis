package org.example;

import com.google.common.collect.ImmutableSet;
import org.apache.commons.math3.distribution.TDistribution;

public class LinearRegression {
    private final ImmutableSet<Point> data;
    private final ImmutableSet<Point> residualPlot;
    private final double xBar;
    private final double yBar;
    private final double alpha;
    private final double beta;
    private final double betaSE;

    public LinearRegression(ImmutableSet<Point> data) {
        assert data.size() > 0;
        this.data = data;
        this.xBar = data.stream().mapToDouble(Point::x).average().getAsDouble();
        this.yBar = data.stream().mapToDouble(Point::y).average().getAsDouble();
        this.beta = data.stream().mapToDouble(point -> (point.x() - this.xBar) * (point.y()) - this.yBar).sum() /
                    data.stream().mapToDouble(point -> (point.x() - this.xBar)).map(operand -> operand * operand).sum();
        this.alpha = this.yBar - this.beta * this.xBar;
        this.residualPlot = data.stream().map(point -> new Point(point.x(), calcResidual(point)))
                .collect(ImmutableSet.toImmutableSet());
        this.betaSE = Math.sqrt(this.residualPlot.stream().mapToDouble(n -> n.y() * n.y()).sum() / (data.size() * (data.size()) - 2));
    }

    public double calcTValue(int h0Beta) {
        return (this.beta - h0Beta) / this.betaSE;
    }

    public double calcPValue(int h0Beta) {
        return new TDistribution(data.size() - 2).cumulativeProbability(calcTValue(h0Beta));
    }

    public double calcRSquaredValue() {
        return 1 - (Math.pow((residualPlot.stream().mapToDouble(Point::y).sum() / data.stream().mapToDouble(point -> point.y() - this.yBar).sum()), 2));
    }

    public boolean isLargeEnough() {
        return data.size() >= 30;
    }


    public double calcResidual(Point p) {
        return p.y() - (p.x() * beta + alpha);
    }
}
