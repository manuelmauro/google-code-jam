import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Main {

	/**
	 * @param args
	 */
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
				Case c = readInput(i, lines);
				
				out.write(output(i+1, solve(c.N, c.L, c.cur_flow, c.target_flow)));
				out.newLine();
			}
			out.close();
		} catch (IOException e) {};
		

		System.out.println("Finished: ");

	}
	
	private static int solve(int N, int L, ArrayList<String> cur_flow, ArrayList<String> target_flow) {
		int result = 0;
		boolean conflict = false;
		boolean switched = false;
		for (int col=0; col<L; col++) {			
			Collections.sort(cur_flow);
			Collections.sort(target_flow);
			for (int j=0; j<N; j++) {
				if (!cur_flow.get(j).substring(0, col+1).equals(target_flow.get(j).substring(0, col+1))) {
					conflict = true;
					if (switched)
						return -1;
				}
			}
			switched = false;
			
			if (conflict) {
				for (int j=0; j<N; j++) {
					String tmp = cur_flow.get(j);
					cur_flow.remove(j);
					cur_flow.add(change(tmp, col));
				}
				conflict = false;
				switched = true;
				result++;
				col--;
			}
		}
		
		return result;
	}
	
	private static String change(String s, int i) {
		String mod = "0";
		if (s.charAt(i) == '0')
			mod = "1";
		
		return s.substring(0, i) + mod + s.substring(i+1);
	}
	
	private static String output(int caseNum, int result) {
		if (result == -1)
			return "Case #" + caseNum + ": NOT POSSIBLE";
		else
			return "Case #" + caseNum + ": " + result;
	}
	
	
	private static Case readInput(int i, List<String> lines) {
		String[] line = lines.get(3*i + 1).split(" ");
		int N = Integer.parseInt(line[0]);
		int L = Integer.parseInt(line[1]);
		
		line = lines.get(3*i + 2).split(" ");
		ArrayList<String> cur_flow = new ArrayList<String>(line.length);
		for (int j=0; j<line.length; j++)
			cur_flow.add(line[j]);
		
		line = lines.get(3*i + 3).split(" ");
		ArrayList<String> target_flow = new ArrayList<String>(line.length);
		for (int j=0; j<line.length; j++)
			target_flow.add(line[j]);
		
		return new Case(N, L, cur_flow, target_flow);
	}
	
	private static class Case {
		
		int N;
		int L;
		ArrayList<String> cur_flow;
		ArrayList<String> target_flow;
		
		public Case(int n, int l, ArrayList<String> cur_flow,
				ArrayList<String> target_flow) {
			super();
			N = n;
			L = l;
			this.cur_flow = cur_flow;
			this.target_flow = target_flow;
		}
	}
}
