import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ScrabbleDictParser {
	
	private HashMap<String, Integer> wordPoints = new HashMap<String, Integer>();
	private BufferedReader br = null;
	private String filename;
	
	public ScrabbleDictParser(String filename) {
		this.filename = filename;
		initReader();
	}
	
	protected List<String> populateDictionary() throws IOException {
		if (filename == null) {
			throw new IllegalArgumentException("File was null.");
		}
		List<String> words = new ArrayList<String>();
		while (br.ready()) {
			String word = br.readLine(); // all words should be uppercase already
			calcPoints(word);
			words.add(word);
		}
		closeReader();
		return words;
	}
	
	private void calcPoints(String word) {
		int score = 0;
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			int pointsForLetter = Global.letterScores.get(c);
			score += pointsForLetter;
		}
		wordPoints.put(word, score);
	}
	
	private void initReader() {
		try {
			br = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			System.out.println("File " + filename + " was not found");
			System.exit(1);
		}
	}
	
	private void closeReader() {
		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				System.out.println("Error in closeing the file reader.");
				System.exit(1);
			}
		}
	}
	
	protected HashMap<String, Integer> getWordScores() {
		return wordPoints;
	}
}
