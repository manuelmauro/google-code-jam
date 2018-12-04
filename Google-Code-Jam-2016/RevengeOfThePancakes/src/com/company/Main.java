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
        int inversions = 0;
        for (int i = 1; i < c.S.length(); i++) {
            if (c.S.charAt(i) != c.S.charAt(i - 1)) {
                inversions++;
            }
        }
        return inversions;
    }

    private static Case readCase(int n, List<String> lines) {
        return new Case(lines.get(n));
    }

    private static class Case {
        String S;

        public Case(String s) {
            this.S = s + "+";
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
        return "Case #" + caseNum + ": " + result;
    }
}