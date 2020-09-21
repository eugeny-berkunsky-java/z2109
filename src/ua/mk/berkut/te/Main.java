package ua.mk.berkut.te;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.DoubleUnaryOperator;

public class Main {

    public static void main(String[] args) {
	    new Main().run();
    }

    private void run() {
        long startTime = System.currentTimeMillis();
        double a = 0;
        double b = Math.PI;
        int n = 1000_000_000;
        DoubleUnaryOperator f = Math::sin;
        int nThreads = 1000  ;
        double delta = (b - a) / nThreads;
        ExecutorService executorService = Executors.newWorkStealingPool();
//        List<Future<Double>> futures = new ArrayList<>();
        List<Callable<Double>> callables = new ArrayList<>();
        for (int i = 0; i < nThreads; i++) {
            callables.add(new CallableCalculator(a + i * delta, a + (i + 1) * delta, n / nThreads, f));
        }
        double sum = 0;
        try {
            sum = executorService.invokeAll(callables)
                    .stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (Exception e) {
                            throw new IllegalStateException(e);
                        }
                    }).mapToDouble(x -> x).sum();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        double sum = 0;
//        try {
//            for (Future<Double> future : futures) {
//                sum += future.get();
//            }
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }
        executorService.shutdown();
        long finishTime = System.currentTimeMillis();
        System.out.println(sum);
        System.out.println(finishTime-startTime);
    }
}
