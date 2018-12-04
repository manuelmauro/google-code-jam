
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class Main {
	
	private static final int BAD_MAGICIAN = -1;
	private static final int VOLUNTEER_CHEATED = 0;
	
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
				out.write(output(i+1, solve(lines, 1 + i*10)));
				out.newLine();
			}
			out.close();
		} catch (IOException e) {};	
	}
	
	private static int solve(List<String> lines, int start) {
		
		int chosenCard = VOLUNTEER_CHEATED;
		
		int fstAns = Integer.parseInt(lines.get(start));
		int sndAns = Integer.parseInt(lines.get(start + 5));
		
		String[] fstPick = lines.get(start + fstAns).split(" ");
		String[] sndPick = lines.get(start + 5 + sndAns).split(" ");
		
		for (int i=0; i<fstPick.length; i++)
			for (int j=0; j<sndPick.length; j++) {
				if (fstPick[i].equals(sndPick[j]))
					if (chosenCard == VOLUNTEER_CHEATED)
						chosenCard = Integer.parseInt(fstPick[i]);
					else
						return BAD_MAGICIAN;
			}
		
		return chosenCard;
	}
	
	private static String output(int caseNum, int result) {
		
		switch (result) {
		case BAD_MAGICIAN:
			return "Case #" + caseNum + ": Bad magician!";
		
		case VOLUNTEER_CHEATED:
			return "Case #" + caseNum + ": Volunteer cheated!";
		
		default:
			return "Case #" + caseNum + ": " + result;
		}
	}

}
