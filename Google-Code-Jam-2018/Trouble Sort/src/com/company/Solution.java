package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = in.nextInt();
        for (int i = 1; i <= t; ++i) {
            int n = in.nextInt();
            int[] v = new int[n];
            for (int j = 0; j < n; j++) {
                v[j] = in.nextInt();
            }
            Case c = new Case(n, v);
            System.out.println(output(i, solve(c)));
        }
    }

    private static int solve(Case c) {
        List<Integer> odd = new ArrayList<>();
        List<Integer> even = new ArrayList<>();
        for (int i = 0; i < c.N; i++) {
            if (i % 2 == 0)
               odd.add(c.V[i]);
            else
                even.add(c.V[i]);
        }

        Collections.sort(odd);
        Collections.sort(even);

        List<Integer> all = new ArrayList<>();
        int i;
        for (i = 0; i < c.N / 2; i++) {
            all.add(odd.get(i));
            all.add(even.get(i));
        }

        if (c.N % 2 == 1)
            all.add(odd.get(i));

        int e = 0;
        while (e < c.N-1 && all.get(e) <= all.get(e+1))
            e++;

        if (e == c.N-1)
            return -1;
        else
            return e;
    }

    private static class Case {
        int N;
        int[] V;

        public Case(int n, int[] v) {
            N = n;
            V = v;
        }
    }

    private static String output(int caseNum, int result) {
        return "Case #" + caseNum + ": " + ((result == -1) ? "OK": result);
    }
}
