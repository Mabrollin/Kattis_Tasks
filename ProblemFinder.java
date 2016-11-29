import java.util.Scanner;

public final class ProblemFinder {
	// Problem Finder only has static Methods
	public static Scanner sc = new Scanner(System.in);

	public static String containsNonCaseSensitive(String line, String word) {
		char[] lineCharArray = line.toCharArray();
		char[] wordCharArray = word.toCharArray();
		int pivot = 0;
		for (int i = 0; i < lineCharArray.length; i++) {
			if (toLower(lineCharArray[i]) == toLower(wordCharArray[pivot])) {
				pivot++;
			} else {
				pivot = 0;
				if (toLower(lineCharArray[i]) == toLower(wordCharArray[pivot])) {
					pivot++;
				}
			}
			if (pivot >= wordCharArray.length)
				return "yes";
		}
		return "no";
	}

	// converts char to lowercase if uppercase
		static char toLower(char c) {
			if (c >= 'A' && c <= 'Z')
				return (char) (c + ('a' - 'A'));
			return c;
		}

	public static void main(String[] args) {
		String currentLine;
		String response;
		while (sc.hasNextLine()) {
			currentLine = sc.nextLine();
			response = containsNonCaseSensitive(currentLine, "problem");
			System.out.println(response);

		}
	}

}
