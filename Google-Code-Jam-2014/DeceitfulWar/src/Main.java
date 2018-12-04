

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
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

				Case c = readInput(i, lines);
				out.write(output(i+1, deceivedWar(c.kenBlocks, c.naomiBlocks), war(c.kenBlocks, c.naomiBlocks)));
				out.newLine();
			}
			out.close();
		} catch (IOException e) {};
	}

	private static int war(final LinkedList<Double> kb, final LinkedList<Double> naomiBlocks) {

		int naomiScore = 0;

		LinkedList<Double> kenBlocks = (LinkedList<Double>) kb.clone();
		Collections.sort(kenBlocks);

		for (int i=0; i<naomiBlocks.size(); i++) {

			if (naomiBlocks.get(i) > kenBlocks.getLast().doubleValue()) {
				naomiScore++;
				kenBlocks.removeFirst();
			} else {
				kenBlocks.remove(minGt(kenBlocks, naomiBlocks.get(i)));
			}
			
		}		

		return naomiScore;		
	}
	
	private static int deceivedWar(final LinkedList<Double> kb, final LinkedList<Double> nb) {

		int naomiScore = 0;

		LinkedList<Double> kenBlocks = (LinkedList<Double>) kb.clone();
		LinkedList<Double> naomiBlocks = (LinkedList<Double>) nb.clone();
		
		Collections.sort(kenBlocks);
		Collections.sort(naomiBlocks);
		
		while (!naomiBlocks.isEmpty()) {
			int mgt = minGt(naomiBlocks, kenBlocks.getFirst());
			if (mgt == naomiBlocks.size()) {
				naomiBlocks.removeFirst();
				kenBlocks.removeLast();
			} else {
				naomiScore++;
				naomiBlocks.remove(mgt);
				kenBlocks.removeFirst();
			}			
		}
		
		return naomiScore;
	}

	private static int minGt(LinkedList<Double> blocks, double b) {

		int i=0;
		while (i < blocks.size() && blocks.get(i).doubleValue() < b)
			i++;
		
		return i;
	}

	private static String output(int caseNum, int resultD, int result) {

		return "Case #" + caseNum + ": " + resultD + " " + result;
	}

	private static Case readInput(int i, List<String> lines) {


		String[] tmpBlocks;
		LinkedList<Double> kenBlocks = new LinkedList<Double>();

		tmpBlocks = lines.get(1 + i*3 + 1).split(" ");
		LinkedList<Double> naomiBlocks = new LinkedList<Double>();

		for (int j=0; j<tmpBlocks.length; j++)
			naomiBlocks.add(Double.parseDouble(tmpBlocks[j]));
		
		tmpBlocks = lines.get(1 + i*3 + 2).split(" ");
		for (int j=0; j<tmpBlocks.length; j++)
			kenBlocks.add(Double.parseDouble(tmpBlocks[j]));
		
		return new Case(
				kenBlocks, 
				naomiBlocks);
	}

	private static class Case {

		LinkedList<Double> kenBlocks;
		LinkedList<Double> naomiBlocks;

		public Case(LinkedList<Double> kenBlocks, LinkedList<Double> naomiBlocks) {
			super();
			this.kenBlocks = kenBlocks;
			this.naomiBlocks = naomiBlocks;
		}		
	}

}
