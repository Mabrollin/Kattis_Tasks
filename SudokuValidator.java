
import java.util.ArrayList;
import java.util.Scanner;

public class SudokuValidator {

	public static Scanner sc = new Scanner(System.in);

	private NumberBox[] board = new NumberBox[81];
	private NumberBoxField[] horizontalRows = new NumberBoxField[9];
	private NumberBoxField[] verticalRows = new NumberBoxField[9];
	private NumberBoxField[] boxSquares = new NumberBoxField[9];

	private boolean valid = true;

	private static boolean uniqueSolution = true;

	private static String[] foundSolution;

	public SudokuValidator() {

		for (int i = 0; i < 9; i++) {
			horizontalRows[i] = new NumberBoxField();
			verticalRows[i] = new NumberBoxField();
			boxSquares[i] = new NumberBoxField();
		}
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				board[i * 9 + j] = new NumberBox(horizontalRows[i], verticalRows[j]);
			}
		}
		int offset;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				offset = 27 * i + 3 * j;
				board[offset].setBoxSquare(boxSquares[i * 3 + j]);
				board[offset + 1].setBoxSquare(boxSquares[i * 3 + j]);
				board[offset + 2].setBoxSquare(boxSquares[i * 3 + j]);
				board[offset + 9].setBoxSquare(boxSquares[i * 3 + j]);
				board[offset + 9 + 1].setBoxSquare(boxSquares[i * 3 + j]);
				board[offset + 9 + 2].setBoxSquare(boxSquares[i * 3 + j]);
				board[offset + 9 * 2].setBoxSquare(boxSquares[i * 3 + j]);
				board[offset + 9 * 2 + 1].setBoxSquare(boxSquares[i * 3 + j]);
				board[offset + 9 * 2 + 2].setBoxSquare(boxSquares[i * 3 + j]);
			}
		}
		for (NumberBox box : board) {
			box.reset();
		}

	}

	class NumberBox {
		private NumberBoxField horizontalRow;
		private NumberBoxField verticalRow;
		private NumberBoxField boxSquare;
		private int value = 0;
		private ArrayList<Integer> candidates = new ArrayList<Integer>();

		public NumberBox(NumberBoxField horizontalRow, NumberBoxField verticalRow) {
			this.setHorizontalRow(horizontalRow);
			this.setVerticalRow(verticalRow);
			reset();

		}

		public NumberBoxField getHorizontalRow() {
			return horizontalRow;
		}

		public void setHorizontalRow(NumberBoxField horizontalRow) {
			this.horizontalRow = horizontalRow;
			horizontalRow.addBox(this);
		}

		public NumberBoxField getVerticalRow() {
			return verticalRow;

		}

		public void setVerticalRow(NumberBoxField verticalRow) {
			this.verticalRow = verticalRow;
			verticalRow.addBox(this);
		}

		public NumberBoxField getBoxSquare() {
			return boxSquare;
		}

		public void setBoxSquare(NumberBoxField boxSquare) {
			this.boxSquare = boxSquare;
			boxSquare.addBox(this);
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
			if (value == 0)
				return;
			this.getBoxSquare().removeCandidate(value);
			this.getHorizontalRow().removeCandidate(value);
			this.getVerticalRow().removeCandidate(value);
			candidates = new ArrayList<Integer>(0);

		}

		public ArrayList<Integer> getCandidates() {
			return candidates;
		}

		public void setCandidates(ArrayList<Integer> candidates) {
			this.candidates = candidates;
		}

		public void removeCandidate(int value) {
			this.getCandidates().remove(new Integer(value));

		}

		public void reset() {
			candidates = new ArrayList<Integer>(9);
			for (int i = 1; i < 10; i++) {
				this.candidates.add(i);
			}

		}
	}

	class NumberBoxField {
		private NumberBox[] boxes = new NumberBox[9];
		private int index = 0;

		public void addBox(NumberBox box) {
			boxes[index] = box;
			index++;
		}

		public void removeCandidate(int value) {
			for (NumberBox box : boxes) {
				box.removeCandidate(value);
			}

		}

		public void setNumbers(int[] values) {
			for (int i = 0; i < 9; i++) {
				boxes[i].setValue(values[i]);
			}

		}

		public boolean solveSingleCandidates() {
			NumberBox singleCandidate;
			boolean change = false;
			for (int i = 1; i < 10; i++) {
				singleCandidate = null;
				for (NumberBox box : boxes) {
					if (box.getCandidates().contains(i)) {
						if (singleCandidate == null)
							singleCandidate = box;
						else {
							singleCandidate = null;
							break;
						}
					}
				}
				if (singleCandidate != null) {
					singleCandidate.setValue(i);
					change = true;
				}

			}
			return change;
		}

		// checks for duplicate numbers in this field
		public boolean validate() {
			boolean found;
			for (int i = 1; i < 10; i++) {
				found = false;
				for (NumberBox box : boxes) {
					if (box.getValue() == i) {
						if (found)
							return false;
						found = true;
					}
				}
			}
			return validateCandidates();
		}

		// checks that this field at least have one candidate for every number
		private boolean validateCandidates() {
			boolean found;
			for (int i = 1; i < 10; i++) {
				found = false;
				for (NumberBox box : boxes) {
					if (box.getValue() == i) {
						found = true;
					}
					if (box.getCandidates().contains(new Integer(i))) {
						found = true;
					}

				}
				if (!found)
					return false;
			}
			return true;
		}

		// Removes candidates in Square based on if a rowsegment
		public void rowElimination() {
			for (int i = 1; i < 10; i++) {
				for (int j = 0; j < 9; j += 3) {
					if (boxes[(j * 3) % 9].getCandidates().contains(new Integer(i))
							&& boxes[(j * 3 + 1) % 9].getCandidates().contains(new Integer(i))
							&& boxes[(j * 3 + 2) % 9].getCandidates().contains(new Integer(i))
							&& !boxes[(j * 3 + 3) % 9].getCandidates().contains(new Integer(i))
							&& !boxes[(j * 3 + 4) % 9].getCandidates().contains(new Integer(i))
							&& !boxes[(j * 3 + 5) % 9].getCandidates().contains(new Integer(i))
							&& !boxes[(j * 3 + 6) % 9].getCandidates().contains(new Integer(i))
							&& !boxes[(j * 3 + 7) % 9].getCandidates().contains(new Integer(i))
							&& !boxes[(j * 3 + 8) % 9].getCandidates().contains(new Integer(i))) {

						boxes[(j * 3) % 9].getBoxSquare().removeCandidate(i);
						boxes[(j * 3) % 9].getCandidates().add(new Integer(i));
						boxes[(j * 3 + 1) % 9].getCandidates().add(new Integer(i));
						boxes[(j * 3 + 2) % 9].getCandidates().add(new Integer(i));
					}
				}
			}
		}

		// removes candidates in row based on rowsegment
		public void fieldElimination() {
			for (int i = 1; i < 10; i++) {
				for (int j = 0; j < 9; j += 3) {
					if (boxes[(j * 3) % 9].getCandidates().contains(new Integer(i))
							&& boxes[(j * 3 + 1) % 9].getCandidates().contains(new Integer(i))
							&& boxes[(j * 3 + 2) % 9].getCandidates().contains(new Integer(i))
							&& !boxes[(j * 3 + 3) % 9].getCandidates().contains(new Integer(i))
							&& !boxes[(j * 3 + 4) % 9].getCandidates().contains(new Integer(i))
							&& !boxes[(j * 3 + 5) % 9].getCandidates().contains(new Integer(i))
							&& !boxes[(j * 3 + 6) % 9].getCandidates().contains(new Integer(i))
							&& !boxes[(j * 3 + 7) % 9].getCandidates().contains(new Integer(i))
							&& !boxes[(j * 3 + 8) % 9].getCandidates().contains(new Integer(i))) {

						boxes[(j * 3) % 9].getHorizontalRow().removeCandidate(i);
						boxes[(j * 3) % 9].getCandidates().add(new Integer(i));
						boxes[(j * 3 + 1) % 9].getCandidates().add(new Integer(i));
						boxes[(j * 3 + 2) % 9].getCandidates().add(new Integer(i));
					}
				}
			}
			for (int i = 1; i < 10; i++) {
				for (int j = 0; j < 9; j += 3) {
					if (boxes[(j * 3) % 9].getCandidates().contains(new Integer(i))
							&& boxes[(j * 3 + 3) % 9].getCandidates().contains(new Integer(i))
							&& boxes[(j * 3 + 6) % 9].getCandidates().contains(new Integer(i))
							&& !boxes[(j * 3 + 1) % 9].getCandidates().contains(new Integer(i))
							&& !boxes[(j * 3 + 4) % 9].getCandidates().contains(new Integer(i))
							&& !boxes[(j * 3 + 7) % 9].getCandidates().contains(new Integer(i))
							&& !boxes[(j * 3 + 2) % 9].getCandidates().contains(new Integer(i))
							&& !boxes[(j * 3 + 5) % 9].getCandidates().contains(new Integer(i))
							&& !boxes[(j * 3 + 8) % 9].getCandidates().contains(new Integer(i))) {

						boxes[(j * 3) % 9].getVerticalRow().removeCandidate(i);
						boxes[(j * 3) % 9].getCandidates().add(new Integer(i));
						boxes[(j * 3 + 3) % 9].getCandidates().add(new Integer(i));
						boxes[(j * 3 + 6) % 9].getCandidates().add(new Integer(i));
					}
				}
			}

		}

		// Removes candidates based on hidden or naked groups.
		public void eliminateCandidatesByGroups(int size) {

			int[] uniqueCandidates = getUniqueCandidates();
			if (uniqueCandidates == null)
				return;
			// routine to hidden groups.
			for (int i = 0; i < uniqueCandidates.length; i++) {
				for (int j = 0; i < uniqueCandidates.length && j != i; i++) {
					if (size == 2) {
						elimanateCandidatesByNakedGroup(i, j);
						elimanateCandidatesByHiddenGroup(i, j);
					} else
						for (int k = 0; i < uniqueCandidates.length && k != i && k != j; i++) {
							if (size == 3) {
								elimanateCandidatesByNakedGroup(i, j, k);
								elimanateCandidatesByHiddenGroup(i, j, k);
							} else
								// size == 4
								for (int l = 0; i < uniqueCandidates.length && l != i && l != j && l != k; i++) {
								elimanateCandidatesByNakedGroup(i, j, k, l);
								elimanateCandidatesByHiddenGroup(i, j, k, l);
								}
						}
				}
			}

		}

		private void elimanateCandidatesByNakedGroup(int... candidates) {
			boolean[] isInGroup = { true, true, true, true, true, true, true, true, true };
			int count = 0;
			for (int i = 0; i < 9; i++) {
				isInGroup[i] &= (boxes[i].getCandidates().size() == candidates.length);
				if (isInGroup[i]) {
					for (int candidate : candidates) {
						isInGroup[i] &= boxes[i].getCandidates().contains(new Integer(candidate));
					}
					if (isInGroup[i]) {
						count++;
					}
				}
			}

			if (count > candidates.length)
				// valid = false;
				if (count == candidates.length) {
				for (int i = 0; i < 9; i++) {
				if (isInGroup[i]) {
				for (int candidate : candidates) {
				boxes[i].getCandidates().remove(new Integer(candidate));
				}
				}
				}
				}

		}

		private void elimanateCandidatesByHiddenGroup(int... candidates) {
			int[] group = new int[candidates.length];
			int count = 0;
			for (int i = 0; i < 9; i++) {
				for (int candidate : candidates) {
					if (boxes[i].getCandidates().contains(candidate)) {
						if (count < candidates.length)
							group[count] = i;
						i++;
						break;
					}
				}
			}

			if (count < candidates.length)
				// valid = false;
				if (count == candidates.length) {
				for (int i = 0; i < count; i++) {
				boxes[group[i]].getCandidates().clear();
				for (int candidate : candidates) {
				boxes[group[i]].getCandidates().add(candidate);
				}

				}
				}

		}

		private int[] getUniqueCandidates() {
			int[] candidates = new int[9 - nrOfSolved()];
			int index = 0;
			if (candidates.length == 0)
				return null;
			for (int i = 0; i < 9; i++) {
				if (boxes[i].getValue() != 0)
					candidates[index] = boxes[i].getValue();
			}
			return candidates;
		}

		private int nrOfSolved() {
			int count = 0;
			for (int i = 0; i < 9; i++) {
				if (boxes[i].getValue() != 0)
					count++;
			}
			return count;
		}

	}

	// Main
	public static void main(String[] args) {
		SudokuValidator sudokuValidator = new SudokuValidator();
		String currentLine;
		String[] row;
		String[] response;

		// starting with user inputs

		while (sc.hasNextLine()) {
			sudokuValidator.reset();
			for (int i = 0; i < 9; i++) {
				currentLine = sc.nextLine();
				row = currentLine.split(" ");
				sudokuValidator.inputRow(i, row);
			}
			sudokuValidator.solve();
			response = sudokuValidator.getResult();
			for (String resp : response)
				System.out.println(resp);
			if (sc.hasNextLine()) {
				System.out.println("");
				sc.nextLine();
			}

		}
	}

	private void reset() {
		valid = true;
		uniqueSolution = true;
		foundSolution = null;
		for (NumberBox box : board) {
			box.reset();
		}

	}

	private String[] getResult() {

		String[] returnStrings;

		if (!valid) {
			returnStrings = new String[1];
			returnStrings[0] = "Find another job";
			return returnStrings;
		}
		if (!uniqueSolution) {
			returnStrings = new String[1];
			returnStrings[0] = "Non-unique";
			return returnStrings;
		}
		if (isSolved())
			return this.getBoardString();
		if (foundSolution != null)
			return foundSolution;
		
		else {
			returnStrings = new String[1];
			returnStrings[0] = "Non-unique";
			return returnStrings;
		}
	}

	// try solve the sudoku, or determine if it's non-valid or non-unqiue
	public void solve() {

		if(!uniqueSolution)
			return;
		boolean change = true;
		valid = isLegal();
		if (!valid)
			return;
		while (change) {
			while (change) {
				change = solveSingleCandidates();
			}
			eliminateCandidates();
			change = solveSingleCandidates();

		}
		valid = isLegal();
		if (!valid)
			return;
		if (!isSolved())
			guess();

	}

	private void eliminateCandidates() {
		eliminateCandidatesByRows();
		eliminateCandidatesByGroups(2);
		eliminateCandidatesByGroups(3);
		eliminateCandidatesByGroups(4);

	}

	private void eliminateCandidatesByGroups(int size) {
		for (int i = 0; i < 9; i++) {
			horizontalRows[i].eliminateCandidatesByGroups(size);
			verticalRows[i].eliminateCandidatesByGroups(size);
			boxSquares[i].eliminateCandidatesByGroups(size);
		}

	}

	private void eliminateCandidatesByRows() {
		for (int i = 0; i < 9; i++) {
			horizontalRows[i].rowElimination();
			verticalRows[i].rowElimination();
			boxSquares[i].fieldElimination();
		}

	}

	// try solving by guessing the first unsolved box, used recursive
	private void guess() {
		String[][] possibleGuesses = this.getPossibleGuesses();
		if (possibleGuesses.length == 0) {
			valid = false;
			return;
		}
		SudokuValidator childValidator = new SudokuValidator();
		String[] response;
		for (int i = 0; i < possibleGuesses.length; i++) {
			childValidator.inputAllRows(possibleGuesses[i]);
			childValidator.solve();
			response = childValidator.getResult();

			if (response.length == 1) {

				if (response[0].equals("Non-unique")) {
					return;
				}
				if (response[0].equals("Find another job")) {
					// failed
				}
			} else {
				if (foundSolution == null) {
					foundSolution = response;

				} else if (!compare(foundSolution, response)) {
					uniqueSolution = false;

				}
			}

		}
		// no children found anything
		if (foundSolution == null)
			valid = false;

	}

	private boolean compare(String[] array1, String[] array2) {
		if (array1.length != array2.length)
			return false;
		for (int i = 0; i < array1.length; i++) {
			if (!array1[i].equals(array2[i]))
				return false;
		}
		return true;
	}

	// Generates the legal possibilities for first unsolved box
	private String[][] getPossibleGuesses() {
		String[] currentState = this.getBoardString();

		int index = -1;
		int row = -1;
		// find where the 0 is
		for (int i = 0; i < 9; i++) {
			if (currentState[i].contains("0")) {
				index = currentState[i].replace(" ", "").indexOf("0");
				row = i;
				i = 9;
			}

		}
		// count possibilities
		int possibilities = board[row * 9 + index].getCandidates().size();
		String[][] returnString = new String[possibilities][9];

		// create new boardString
		for (int i = 0; i < possibilities; i++) {
			for (int j = 0; j < 9; j++) {
				if (j == row) {
					returnString[i][j] = currentState[j].replaceFirst("0",
							Integer.toString(board[row * 9 + index].getCandidates().get(i)));
				} else {
					returnString[i][j] = currentState[j];
				}

			}
		}
		return returnString;
	}

	// checks if board is legal
	private boolean isLegal() {
		boolean valid = true;
		for (NumberBoxField boxSquare : boxSquares)
			valid = boxSquare.validate();
		for (NumberBoxField row : verticalRows)
			valid = valid && row.validate();
		for (NumberBoxField row : horizontalRows)
			valid = valid && row.validate();
		return valid;

	}

	// Looks for Boxes with one candidate och fields with one possible candidate
	// for a number
	private boolean solveSingleCandidates() {
		boolean change = false;
		for (NumberBox box : board) {
			if (box.getValue() == 0) {
				if (box.getCandidates().size() == 1) {
					box.setValue(box.getCandidates().get(0));
					change = true;
				}
			}
		}
		for (NumberBoxField boxSquare : boxSquares)
			change = change || boxSquare.solveSingleCandidates();
		for (NumberBoxField row : verticalRows)
			change = change || row.solveSingleCandidates();
		for (NumberBoxField row : horizontalRows)
			change = change || row.solveSingleCandidates();
		return change;
	}

	// returns solution or status of board (non.unique, invalid)
	private String[] getBoardString() {
		String[] returnStrings = new String[9];
		String line;

		for (int i = 0; i < 9; i++) {
			line = "";
			for (int j = 0; j < 9; j++) {
				line += board[i * 9 + j].getValue();
				if (j != 8) {
					line += " ";
				}
				returnStrings[i] = line;
			}
		}
		return returnStrings;
	}

	private boolean isSolved() {
		for (NumberBox number : board)
			if (number.getValue() == 0)
				return false;
		return true;
	}

	// inputs all numbers
	// Format: Array of String of Row representation
	private void inputAllRows(String[] possibleGuesses) {
		// reset for candidates
		for (NumberBox box : board) {
			box.reset();
		}
		for (int i = 0; i < 9; i++) {
			inputRow(i, possibleGuesses[i].split(" "));
		}

	}

	// inputs a row
	// Format index of row, Array of String representing letters in row
	private void inputRow(int rowIndex, String[] row) {
		int[] values = new int[9];
		for (int i = 0; i < 9; i++) {
			values[i] = Integer.parseInt(row[i]);
		}
		inputRow(rowIndex, values);

	}

	// inputs a row
	// Format index of row, Array of ints representing letters in row
	private void inputRow(int rowIndex, int[] values) {
		horizontalRows[rowIndex].setNumbers(values);

	}

}
