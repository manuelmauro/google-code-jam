import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
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
		double C, F, X;
		
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("output.txt"));

			for (int i=0; i<cases; i++) {
				String[] line = lines.get(i+1).split(" ");
				C = Double.parseDouble(line[0]);
				F = Double.parseDouble(line[1]);
				X = Double.parseDouble(line[2]);
				
				out.write(output(i+1, solve(2, 0, X, C, F)));
				out.newLine();
			}
			out.close();
		} catch (IOException e) {};

	}
	
	private static double solve(double curRate, double curTime, double X, double C, double F) {
		
		double wNewFarm, woNewFarm;
		
		while (true) {
			wNewFarm = X /(curRate + F) + C / curRate;
			woNewFarm = X / curRate;
			
			if (woNewFarm < wNewFarm) {
				return curTime + X / curRate;
			} else {
				curRate += F;
				curTime += C/curRate;
			}
		}
		
	}
	
	private static String output(int caseNum, double result) {
		
		return "Case #" + caseNum + ": " + result;
	}

}
