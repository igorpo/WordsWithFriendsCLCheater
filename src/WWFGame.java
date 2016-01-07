import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

class ValueComparator implements Comparator<String> {
	 
	Map<String, Integer> map;
 
	public ValueComparator(Map<String, Integer> map) {
		this.map = map;
	}
 
	public int compare(String a, String b) {
        if (map.get(a) >= map.get(b)) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys 
    }
}

public class WWFGame {
	
	private static List<String> dict;
	private static HashMap<String, Integer> wordScores;
	
	private static void findPossibleWords(String rack) {
		HashMap<String, Integer> foundWords = new HashMap<String, Integer>(16);
		char[] rackChars = rack.toCharArray();
		ArrayList<Character> rackLetters = new ArrayList<Character>();
		for (char c : rackChars) {
			rackLetters.add(c);
		}
		for (String word : dict) {
			char[] wordLetters = word.toCharArray();
			if (wordCanBeMade(wordLetters, rackLetters)) {
				foundWords.put(word, wordScores.get(word));
			}
		}
		
		Map<String, Integer> sortedMap = sortByValue(foundWords);
		for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
	}
	
	private static Map<String, Integer> sortByValue(Map<String, Integer> unsortedMap) {
		Map<String, Integer> sortedMap = new TreeMap<String, Integer>(new ValueComparator(unsortedMap));
		sortedMap.putAll(unsortedMap);
		return sortedMap;
	}
	
	private static boolean wordCanBeMade(char[] wordLetters, ArrayList<Character> rackLetters) {
		ArrayList<Character> tempRemovedLetters = new ArrayList<Character>();
		for (char c : wordLetters) {
			if (rackLetters.contains((Character) c)) {
				rackLetters.remove((Character) c);
				tempRemovedLetters.add(c);
			} else {
				for (char x : tempRemovedLetters) {
					rackLetters.add((Character) x);
				}
				return false;
			}
		}
		for (char x : tempRemovedLetters) {
			rackLetters.add((Character) x);
		}
		return true;
	}
	
	public static void main(String[] args) throws IOException {
		Global.populateLetterScores();
		ScrabbleDictParser s = new ScrabbleDictParser("sowpods.txt");
		dict = s.populateDictionary();
		wordScores = s.getWordScores();
		if (args.length != 1) {
			System.out.println("Invalid number of arguments, try again.");
			System.exit(1);
		}
		String input = args[0].replaceAll("[^A-Za-z]", "").toUpperCase();
		findPossibleWords(input);
	}
}
