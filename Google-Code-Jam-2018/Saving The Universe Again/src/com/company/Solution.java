package com.company;

import java.io.*;
import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = in.nextInt();
        for (int i = 1; i <= t; ++i) {
            int d = in.nextInt();
            String p = in.next();
            Case c = new Case(d, p);
            System.out.println(output(i, solve(c)));
        }
    }

    private static int solve(Case c) {
        int[] damages = new int[30];

        int pow = 0;
        for (int i = 0; i < c.P.length(); i++) {
            if (c.P.charAt(i) == 'S')
                damages[pow]++;
            else
                pow++;
        }

        int hacks = 0;
        while (dealt(damages) > c.D) {
            int j;
            for (j = damages.length-1; j > 0 && damages[j] == 0; j--)
                ;
            if (j == 0) {
                hacks = -1;
                break;
            } else {
                damages[j]--;
                damages[j - 1]++;
                hacks++;
            }
        }
        return hacks;
    }

    private static int dealt(int[] d) {
        int tot = 0;
        for (int i = 0; i < d.length; i++) {
            tot += d[i] * Math.pow(2, i);
        }
        return tot;
    }

    private static class Case {
        int D;
        String P;

        public Case(int d, String p) {
            D = d;
            P = p;
        }
    }

    private static String output(int caseNum, int result) {
        return "Case #" + caseNum + ": " + ((result == -1) ? "IMPOSSIBLE": result);
    }
}
