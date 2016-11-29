import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class ECoinValidator2 {

	public static Scanner sc = new Scanner(System.in);

	int[][] nrToReachPoint;
	Set<List<Integer>> checkedPoints;
	Set<List<Integer>> coins;
	int modulus = 0;

	public int validateECoins(int modulus, Integer[]... coins) {

		nrToReachPoint = new int[modulus + 1][modulus + 1];
		for (int[] row : nrToReachPoint)
			Arrays.fill(row, modulus + 1);

		this.modulus = modulus;

		this.coins = trimVectorArray(coins);

		checkedPoints = new HashSet<List<Integer>>();
		List<Integer> origo = new ArrayList<Integer>();
		origo.add(0);
		origo.add(0);
		checkedPoints.add(origo);
		nrToReachPoint[0][0] = 0;
		for (List<Integer> coin : this.coins) {
			addCoinToGrid(coin);
		}
		Set<List<Integer>> solutions = getSolutions(checkedPoints);
		if (solutions.isEmpty())
			return -1;
		else {
			return bestResult(solutions);
		}

	}

	private int bestResult(Set<List<Integer>> solutions) {
		int best = modulus + 1;
		for (List<Integer> solution : solutions) {
			best = Math.min(best, nrToReachPoint[solution.get(0)][solution.get(1)]);
		}
		return best;
	}

	private Set<List<Integer>> getSolutions(Set<List<Integer>> checkedPoints) {
		Set<List<Integer>> possibleSolution = getPossibleSolutions();
		Set<List<Integer>> foundSolutions = new HashSet<List<Integer>>();
		for (List<Integer> point : checkedPoints) {
			if (possibleSolution.contains(point))
				foundSolutions.add(point);
		}
		return foundSolutions;
	}

	private void addCoinToGrid(List<Integer> coin) {
		Set<List<Integer>> oldCheckedPoints = new HashSet<List<Integer>>(checkedPoints);
		for (List<Integer> point : oldCheckedPoints) {
			addCoinToGrid(point, coin);
		}

	}

	private void addCoinToGrid(List<Integer> point, List<Integer> coin) {
		List<Integer> newPoint = new ArrayList<Integer>(point);
		int newX = newPoint.get(0) + coin.get(0);
		int newY = newPoint.get(1) + coin.get(1);
		newPoint.set(0, newX);
		newPoint.set(1, newY);
		while (isInGrid(newPoint)) {
			checkedPoints.add(newPoint);
			nrToReachPoint[newX][newY] = Math.min(nrToReachPoint[newX][newY],
					nrToReachPoint[newX - coin.get(0)][newY - coin.get(1)] + 1);
			newX += coin.get(0);
			newY += coin.get(1);
			newPoint = new ArrayList<Integer>();
			newPoint.add(newX);
			newPoint.add(newY);
		}

	}

	private boolean isInGrid(List<Integer> newPoint) {
		return (newPoint.get(0) < modulus + 1 && newPoint.get(1) < modulus + 1);
	}

	private Set<List<Integer>> getPossibleSolutions() {
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

		ECoinValidator2 validator = new ECoinValidator2();
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
