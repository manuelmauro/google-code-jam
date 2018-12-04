package com.company;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(
                    Paths.get("./input/input.txt"),
                    StandardCharsets.UTF_8);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        int cases = Integer.parseInt(lines.get(0));

        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("output.txt"));

            for (int i=0; i<cases; i++) {
                out.write(output(i+1, solve(readCase(i+1, lines))));
                out.newLine();
            }
            out.close();
        } catch (IOException e) {};
    }

    private static int solve(Case c) {

        int standing = 0;
        int invited = 0;

        for (int i=0; i<c.sMax+1; i++) {
            if (standing >= i) {
                standing += c.S[i];
            } else {
                invited += i - standing;
                standing = i + c.S[i];
            }
        }
        return invited;
    }

    private static Case readCase(int n, List<String> lines) {
        String line[] = lines.get(n).split(" ");
        int sMax = Integer.parseInt(line[0]);
        int S[] = new int[sMax+1];

        for (int i=0; i<sMax+1; i++)
            S[i] = Integer.parseInt(line[1].substring(i, i+1));

        return new Case(sMax, S);
    }

    private static class Case {

        int sMax;
        int S[];

        public Case(int sMax, int[] s) {
            this.sMax = sMax;
            S = s;
        }
    }

    private static String output(int caseNum, int result) {
        return "Case #" + caseNum + ": " + result;
    }
}
