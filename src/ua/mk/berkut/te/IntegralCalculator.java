package ua.mk.berkut.te;

import java.util.function.DoubleUnaryOperator;
import java.util.stream.IntStream;

public class IntegralCalculator {

    private final double start;
    private final double finish;
    private final int n;
    private final DoubleUnaryOperator f;

    public IntegralCalculator(double start, double finish, int n, DoubleUnaryOperator f) {
        this.start = start;
        this.finish = finish;
        this.n = n;
        this.f = f;
    }

    public double calculate() {
        double h = (finish - start) / n;
        return IntStream.range(0, n)
                .mapToDouble(i -> start + i * h)
                .map(f)
                .map(y -> y * h)
                .sum();
    }
}
