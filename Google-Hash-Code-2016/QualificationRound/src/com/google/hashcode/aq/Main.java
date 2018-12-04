package com.google.hashcode.aq;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        String i0 = "./input/busy_day.in";
        String i1 = "./input/mother_of_all_warehouses.in";
        String i2 = "./input/redundancy.in";
        List<String> lines = null;
        try {
            lines = Files.readAllLines(
                    Paths.get(i0),
                    StandardCharsets.UTF_8);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        ProblemInstance pi = Parser.readInstance(lines);
        RandomSimulation s = new RandomSimulation(pi);
        List<Command> solution = s.run();

        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("output.txt"));

            out.write("" + solution.size());
            out.newLine();

            for (int i = 0; i < solution.size(); i++) {
                out.write(solution.get(i).toString());
                out.newLine();
            }
            out.close();
        } catch (IOException e) {};
    }
}
