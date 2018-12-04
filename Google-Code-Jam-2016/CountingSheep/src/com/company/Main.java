package com.company;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(
                    Paths.get("./input.txt"),
                    StandardCharsets.UTF_8);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        int cases = Integer.parseInt(lines.get(0));

        ExecutorService executorService = Executors.newFixedThreadPool(8);
        final CountDownLatch done = new CountDownLatch(cases);

        Map<Integer, String> output = new ConcurrentHashMap<>(cases);
        try {
            for (int i = 0; i < cases; i++) {
                Case c = readCase(i+1, lines);
                executorService.execute(new Worker(i+1, c, output, done));
            }
            executorService.shutdown();

            done.await();

            BufferedWriter out = new BufferedWriter(new FileWriter("output.txt"));
            for (int i=1; i <= cases; i++) {
                out.write(output.get(i));
                out.newLine();
            }
            out.close();

        } catch (IOException e) {
        } catch (InterruptedException e) {
        }
    }

    private static int solve(Case c) {
        if (c.N == 0) {
            return 0;
        }

        boolean[] digits = new boolean[10];

        int i = 0;
        while (!allFound(digits)) {
            updateDigits(digits, c.N * i++);
        }
        return c.N * (i-1);
    }

    private static boolean allFound(boolean[] a) {
        boolean allFound = true;
        for (int i = 0; i <= 9; i++) {
            allFound = allFound && a[i];
        }
        return  allFound;
    }

    private static void updateDigits(boolean[] a, int n) {
        while (n > 0) {
            a[n % 10] = true;
            n /= 10;
        }
    }

    private static Case readCase(int n, List<String> lines) {
        int N = Integer.parseInt(lines.get(n));
        return new Case(N);
    }

    private static class Case {
        int N;

        public Case(int N) {
            this.N = N;
        }
    }

    private static class Worker implements Runnable {

        int index;
        Case c;
        Map<Integer, String> output;
        CountDownLatch done;

        public Worker(int index, Case c, Map<Integer, String> output, CountDownLatch done) {
            this.index = index;
            this.c = c;
            this.output = output;
            this.done = done;
        }

        public void run() {
            output.put(index, output(index, solve(c)));
            done.countDown();
        }
    }

    private static String output(int caseNum, int result) {
        return "Case #" + caseNum + ": " + ((result <= 0) ? "INSOMNIA" : result);
    }
}