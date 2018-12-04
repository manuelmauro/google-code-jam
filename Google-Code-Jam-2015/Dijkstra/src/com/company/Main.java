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
                Case c = readCase(2 * i + 1, lines);
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
        Map<Integer, SignedQuaternion> cachedResults = new HashMap<>();

        int length = c.L * c.X;

        SignedQuaternion fst = SignedQuaternion.valueOf(1, Quaternion.ONE);

        for (int i=0; i<length-2; i++) {
            fst = Quaternions.eval(fst, c.misspell[i]);
            if (fst.sign == 1 && fst.mod == Quaternion.I) {
                SignedQuaternion snd = SignedQuaternion.valueOf(1, Quaternion.ONE);
                for (int j=i+1; j<length-1-i; j++) {
                    snd = Quaternions.eval(snd, c.misspell[j]);
                    if (snd.sign == 1 && snd.mod == Quaternion.J) {
                        SignedQuaternion trd;
                        if (cachedResults.get(j+1) != null) {
                            trd = cachedResults.get(j + 1);
                        } else {
                            trd = Quaternions.eval(c.misspell, j+1, length);
                            cachedResults.put(j+1, trd);
                        }
                        if (trd.sign == 1 && trd.mod == Quaternion.K)
                            return 1;
                    }
                }
            }
        }

        return 0;
    }

    private static Case readCase(int n, List<String> lines) {
        String fstLine[] = lines.get(n).split(" ");
        int L = Integer.parseInt(fstLine[0]);
        int X = Integer.parseInt(fstLine[1]);

        String sndLine = lines.get(n+1);

        SignedQuaternion misspell[] = new SignedQuaternion[L*X];

        for (int i=0; i<L*X; i++) {
            misspell[i] = SignedQuaternion.valueOf(sndLine.charAt(i % L));
        }

        return new Case(L, X, misspell);
    }

    private static class Case {

        int L;
        int X;
        SignedQuaternion misspell[];

        public Case(int l, int x, SignedQuaternion misspell[]) {
            L = l;
            X = x;
            this.misspell = misspell;
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
        return "Case #" + caseNum + ": " + ((result==0) ? "NO" : "YES");
    }


    private enum Quaternion {
        ONE(0), I(1), J(2), K(3);
        int code;

        Quaternion(int code) {
            this.code = code;
        }
    }

    private static class SignedQuaternion {
        int sign;
        Quaternion mod;

        private static SignedQuaternion cache[][] = {
                {
                        new SignedQuaternion(1, Quaternion.ONE),
                        new SignedQuaternion(1, Quaternion.I),
                        new SignedQuaternion(1, Quaternion.J),
                        new SignedQuaternion(1, Quaternion.K),},
                {
                        new SignedQuaternion(-1, Quaternion.ONE),
                        new SignedQuaternion(-1, Quaternion.I),
                        new SignedQuaternion(-1, Quaternion.J),
                        new SignedQuaternion(-1, Quaternion.K)
                }
        };

        private SignedQuaternion(int sign, Quaternion mod) {
            this.sign = sign;
            this.mod = mod;
        }

        public static SignedQuaternion valueOf(int sign, Quaternion mod) {
            if (sign == 1)
                return cache[0][mod.code];
            else
                return cache[1][mod.code];
        }

        public static SignedQuaternion valueOf(char c) {
            switch (c) {
                case 'i': return cache[0][1];
                case 'j': return cache[0][2];
                case 'k': return cache[0][3];
            }

            return null;
        }
    }

    private static class Quaternions {

        private static Quaternion mulTable[][] = {
                {Quaternion.ONE, Quaternion.I, Quaternion.J, Quaternion.K},
                {Quaternion.I, Quaternion.ONE, Quaternion.K, Quaternion.J},
                {Quaternion.J, Quaternion.K, Quaternion.ONE, Quaternion.I},
                {Quaternion.K, Quaternion.J, Quaternion.I, Quaternion.ONE}
        };

        private static int signTable[][] = {
                {1, 1, 1, 1},
                {1, -1, 1, -1},
                {1, -1, -1, 1},
                {1, 1, -1, -1}
        };

        public static SignedQuaternion eval(SignedQuaternion[] expr, int start, int end) {
            SignedQuaternion acc = expr[start];

            for (int i=start+1; i<end; i++)
                acc = eval(acc, expr[i]);
            return acc;
        }

        public static SignedQuaternion eval(SignedQuaternion a, SignedQuaternion b) {
            return new SignedQuaternion(
                    signTable[a.mod.code][b.mod.code] * a.sign * b.sign,
                    mulTable[a.mod.code][b.mod.code]
            );
        }
    }
}
