import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class CheckPositionOfScore {

	LinkedHashMap<String, Integer> tableOfScores;
	boolean shouldBeTableUpdated = false;
	private StringBuilder sb;
	private final int maxNumberOfRowsInTheFile = 10;
	int userPositionInTable;
	ArrayList<String> names;

	public static void main(String[] args) {

//		 CheckPositionOfScore obj = new CheckPositionOfScore(10);
//		 System.out.println("Your position is: " + obj.userPositionInTable);
//		 for(String name: obj.names) {
//			 System.out.println(name);
//		 }

		CheckPositionOfScore obj2 = new CheckPositionOfScore("septimoxe", 90);
//		 obj2.read("Robertowesd", 10);
	}
	
	public CheckPositionOfScore () {
		// make a ArrayList with the names in score
		names = new ArrayList<String>();
		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader("Files/Score"));
			String line = br.readLine();
			while (line != null) {
				String sentence = line;
				line = br.readLine();
				String name = sentence.substring(ordinalIndexOf(sentence, ";", 1) + 1,
						ordinalIndexOf(sentence, ";", 2));
				names.add(name);
			}
			} catch (Exception e) {
				System.err.println("It was an error");
			}
	}

	public CheckPositionOfScore(int playerScore) {
		// TODO Auto-generated constructor stub
		names = new ArrayList<String>();
		tableOfScores = new LinkedHashMap<String, Integer>();

		BufferedReader br = null;
		int position;
		int counter = 0;

		try {
			br = new BufferedReader(new FileReader("Files/Score"));

			String line = br.readLine();
			
			while (line != null) {
				System.out.println(line);

				String sentence = line;
				line = br.readLine();
				if (counter < maxNumberOfRowsInTheFile) {

				position = Integer.parseInt(sentence.substring(0, ordinalIndexOf(sentence, ";", 1)));
				System.out.println("Number: " + position);
			
				int score = Integer
						.parseInt(sentence.substring(ordinalIndexOf(sentence, ";", 2) + 1, sentence.length()));
				System.out.println("Score: " + score);

				if (playerScore >= score) {
					userPositionInTable = position;
					System.out.println("Congratulations! You were in the " + position + "th position of the ranking");
					shouldBeTableUpdated = true;
					break;
				}
				
				counter++;
				}

			}
			
			if (counter < maxNumberOfRowsInTheFile && playerScore > 0) {
				userPositionInTable = counter+1;
			}

		} catch (Exception e) {

		}

	}

	public CheckPositionOfScore(String playerName, int playerScore) {
		System.out.println("Inside constructor 2");
		read(playerName, playerScore);
		displayTableOfScores();
		System.out.println("Should be updated:" + shouldBeTableUpdated);
		if (shouldBeTableUpdated = true)
			writeScoreInTxt();
	}

	public void read(String playerName, int playerScore) {
		tableOfScores = new LinkedHashMap<String, Integer>();

		BufferedReader br = null;
		int counter = 0;

		try {
			br = new BufferedReader(new FileReader("Files/Score"));

			String line = br.readLine();

			while (line != null) {
				System.out.println(line);
				// sb.append(line);
				// sb.append(System.lineSeparator());
				String sentence = line;
				line = br.readLine();

				int position = Integer.parseInt(sentence.substring(0, ordinalIndexOf(sentence, ";", 1)));
				System.out.println("Number: " + position);

				String name = sentence.substring(ordinalIndexOf(sentence, ";", 1) + 1,
						ordinalIndexOf(sentence, ";", 2));
				System.out.println("Name: " + name);

				int score = Integer
						.parseInt(sentence.substring(ordinalIndexOf(sentence, ";", 2) + 1, sentence.length()));
				System.out.println("Score: " + score);

				if (counter < maxNumberOfRowsInTheFile) {

					if (playerScore >= score && shouldBeTableUpdated == false) {
						userPositionInTable = position;
						System.out
								.println("Congratulations! You were in the " + position + "th position of the ranking");
						tableOfScores.put(playerName, playerScore);
						counter++;
						shouldBeTableUpdated = true;
					}

					if (playerScore == score && counter < maxNumberOfRowsInTheFile) {
						System.out.println("Inside player = score");
						tableOfScores.put(name, score);
						counter++;
					}

					if (counter < maxNumberOfRowsInTheFile) {
						tableOfScores.put(name, score);
						counter++;
					}

				}

			}
			if (counter < maxNumberOfRowsInTheFile && playerScore > 0) {
				System.out.println("Congratulations! You were in the " + counter + "th position of the ranking");
				tableOfScores.put(playerName, playerScore);
				counter++;
				shouldBeTableUpdated = true;
			}
		} catch (Exception e) {

		}

	}

	private static int ordinalIndexOf(String str, String substr, int n) {
		int pos = str.indexOf(substr);
		while (--n > 0 && pos != -1)
			pos = str.indexOf(substr, pos + 1);
		return pos;
	}

	private void writeScoreInTxt() {
		// TODO: write in Score file
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("Files/Score");
			writer.print(sb.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
				System.out.println("write.close executed");
			}
		}
	}

	private void displayTableOfScores() {
		sb = new StringBuilder();
		int row = 1;
		boolean last = true;
		final int tableSize = tableOfScores.size();

		System.out.println("The table size is: " + tableOfScores.size());
		Iterator it = tableOfScores.entrySet().iterator();
		while (it.hasNext()) {
			HashMap.Entry pair = (HashMap.Entry) it.next();
			System.out.println(pair.getKey() + " = " + pair.getValue());
			if (row == tableSize) {
				sb.append(row++ + ";" + pair.getKey() + ";" + pair.getValue());
			} else {
				sb.append(row++ + ";" + pair.getKey() + ";" + pair.getValue() + System.lineSeparator());
			}
			it.remove(); // avoids a ConcurrentModificationException
		}
	}

}
