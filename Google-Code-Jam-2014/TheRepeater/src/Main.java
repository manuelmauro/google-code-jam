

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

			for (int i=0, j=1; i<cases; i++) {

				Case c = readInput(j, lines);
				j += c.N+1;
				out.write(output(i+1, solve(c)));
				out.newLine();
			}
			out.close();
		} catch (IOException e) {};
		
	}
	
	private static int solve(Case c) {
		String target = target(c.repetitions[0]);
		int[][] charachts = new int[c.N][];
		int result = 0;
		
		
		for (String s : c.repetitions) {
			if (!target.equals(target(s)))
				return -1;
		}
			
		for (int i=0; i<c.N; i++) {
			charachts[i] = charachteristic(c.repetitions[i]);
		}
		
		int[][] tcharachts = new int[target.length()][c.N];
		for (int i=0; i<target.length(); i++) {
			for (int j=0; j<c.N; j++) {
				tcharachts[i][j] = charachts[j][i];
			}
		}
		
		int totCost = 0;
		for (int i=0; i<target.length(); i++) {
			totCost += minCost(tcharachts[i]);
		}
		
		return totCost;
	}
	
	private static int minCost(int[] m) {
		int min = m[0];
		int max = m[0];
		for (int i=1; i<m.length; i++) {
			if (m[i] < min)
				min = m[i];
			if (m[i] > max)
				max = m[i];
		}
		
		int cost = Integer.MAX_VALUE;;
		int tmpCost = 0;
		for (int i=min; i<=max; i++) {
			for (int j=0; j<m.length; j++) {
				tmpCost += Math.abs(m[j] - i);
			}
			if (tmpCost < cost)
				cost = tmpCost;
			tmpCost = 0;
		}
		return cost;
	}
	
	private static int[] charachteristic(String s) {
		String target = target(s);
		int[] charac = new int[target.length()];
		int curS = 0;
		
		for (int i=0; i<target.length() && curS < s.length();) {
			if (target.charAt(i) == s.charAt(curS)) {
				charac[i]++;
				curS++;
			} else {
				i++;
			}
		}
		return charac;
	}
	
	private static String target(String s) {
		String target = "" + s.charAt(0);
		
		for (int i=1; i<s.length(); i++) {
			if (s.charAt(i) != target.charAt(target.length()-1))
				target += s.charAt(i);			
		}
		
		return target;
	}

	private static String output(int caseNum, int result) {
		return "Case #" + caseNum + ": "
				+ ((result == -1) ? "Fegla Won": result);
	}

	private static Case readInput(int i, List<String> lines) {
		int N = Integer.parseInt(lines.get(i));
		
		String[] tmpReps = new String[N];
		for (int j=0; j<N; j++)
			tmpReps[j] = lines.get(i+j+1);
		
		return new Case(N, tmpReps);
	}

	private static class Case {
		int N;
		String[] repetitions;

		public Case(int n, String[] reps) {
			super();
			this.N = n;
			this.repetitions = reps;
		}		
	}
}
