import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class ECoinValidator {

	public static Scanner sc = new Scanner(System.in);

	boolean[][] checkedCoors;
	Set<List<Integer>> coins;
	int modulus = 0;

	private int checked;

	public int validateECoins(int modulus, Integer[]... coins) {
		checked = 0;
		checkedCoors = new boolean[modulus + 1][modulus + 1];
		for (boolean[] row : checkedCoors)
			Arrays.fill(row, false);

		Set<List<Integer>> reachedPoints = new HashSet<List<Integer>>();

		this.modulus = modulus;

		this.coins = trimVectorArray(coins);
		int step = 0;

		List<Integer> origo = new ArrayList<Integer>();
		origo.add(0);
		origo.add(0);
		reachedPoints.add(origo);
		while (!reachedPoints.isEmpty() && !hasSolution(reachedPoints, getIntegerSolutions(modulus)) && checked < (modulus + 1) * (modulus + 1)) {

			step++;
			reachedPoints = generateNextGuessStep(reachedPoints);
		}
		if (reachedPoints.isEmpty())
			return -1;
		else
			return step;

	}

	private boolean hasSolution(Set<List<Integer>> reachedPoints, Set<List<Integer>> solutions) {
		for (List<Integer> point : reachedPoints) {
			if (solutions.contains(point))
				return true;
		}
		return false;
	}

	private Set<List<Integer>> generateNextGuessStep(Set<List<Integer>> integerSolutions) {
		List<List<Integer>> coins = new ArrayList<List<Integer>>(this.coins);
		Set<List<Integer>> newSolutions = new HashSet<List<Integer>>();
		// if (checked >= (modulus + 1) * (modulus + 1))
		// return newSolutions;
		List<Integer> newSolution = new ArrayList<Integer>();
		for (List<Integer> coinSum : integerSolutions) {
			for (List<Integer> coin : coins) {
				newSolution = addCoin(coinSum, coin);
				if (pointInRange(newSolution) && !checkedCoors[newSolution.get(0)][newSolution.get(1)]) {
					checkedCoors[newSolution.get(0)][newSolution.get(1)] = true;
					newSolutions.add(newSolution);
					

				}

			}
		}
		checked += newSolutions.size();
		return newSolutions;
	}

	private boolean pointInRange(List<Integer> newSolution) {
		return (calcModulus(newSolution) <= modulus * modulus);
	}

	private double calcModulus(List<Integer> newSolution) {
		return calcModulus(newSolution.get(0), newSolution.get(1));
	}

	private double calcModulus(int x, int y) {
		return x * x + y * y;
	}

	private List<Integer> addCoin(List<Integer> solution, List<Integer> coin) {
		List<Integer> newSolution = new ArrayList<Integer>();
		newSolution.add(solution.get(0) + coin.get(0));
		newSolution.add(solution.get(1) + coin.get(1));
		return newSolution;
	}

	private Set<List<Integer>> getIntegerSolutions(int modulus) {
		Set<List<Integer>> foundSolutions = new HashSet<List<Integer>>();
		int maxTestValue = modulus;
		List<Integer> solution = new ArrayList<Integer>();

		for (int x = 0; x <= maxTestValue; x++)
			for (int y = 0; y <= maxTestValue; y++) {
				if (modulus * modulus == x * x + y * y) {
					solution = new ArrayList<Integer>();
					solution.add(x);
					solution.add(y);
					foundSolutions.add(solution);
				}
			}
		return foundSolutions;
	}

	private Set<List<Integer>> trimVectorArray(Integer[][] coins) {
		Set<List<Integer>> returnCoinsSet = new HashSet<List<Integer>>();
		List<Integer> coinValueList;
		for (Integer[] coin : coins) {
			if (coin[0] != 0 || coin[1] != 0) {
				coinValueList = new ArrayList<Integer>();
				coinValueList.add(coin[0]);
				coinValueList.add(coin[1]);
				returnCoinsSet.add(coinValueList);
			}
		}

		return returnCoinsSet;
	}

	// private int guessForModByVectors(int x, int y, int index, int count) {
	// if (x < 0 || y < 0)
	// return -1;
	//
	// if (checkedCoors[x][y] <= count)
	// checkedCoors[x][y] = count;
	// else
	// return -1;
	//
	// if (x == 0 && y == 0)
	//
	// return count;
	// else {
	// int bestResult = -1;
	// int newResult;
	// for (int i = index; i < coins.length; i++) {
	// newResult = guessForModByVectors(x - coins[i][0], y - coins[i][1], i,
	// count + 1);
	// if (newResult != -1)
	// if (bestResult != -1)
	// bestResult = Math.min(bestResult, newResult);
	// else
	// bestResult = newResult;
	// }
	//
	// return bestResult;
	//
	// }
	// }

	public static void main(String[] args) {
		ECoinValidator validator = new ECoinValidator();
		int nrOfRuns = sc.nextInt();
		Integer[][] coins;
		int modulus;
		int nrOfCoins;

		for (int i = 0; i < nrOfRuns; i++) {
			nrOfCoins = sc.nextInt();
			modulus = sc.nextInt();
			coins = new Integer[nrOfCoins][2];
			for (int j = 0; j < nrOfCoins; j++) {
				coins[j][0] = sc.nextInt();
				coins[j][1] = sc.nextInt();
			}
			int result = validator.validateECoins(modulus, coins);
			if (result == -1)
				System.out.println("not possible");
			else
				System.out.println(result);
		}
	}
}
