import java.util.ArrayList;
import java.util.Scanner;

public class PrimeReductor {
	// Problem Finder only has static Methods
	public static Scanner sc = new Scanner(System.in);
	private static ArrayList<Integer> primes = new ArrayList<Integer>();
	static {
		primes.add(2);
	}

	// returns an int array of size 2:
	// the first element is the reducted value and the second is the number of
	// routine iterations
	private static int[] getPrimeReduction(int number) {
		int nrOfIterations = 1;
		ArrayList<Integer> factors;
		// step 1
		while (!isPrime(number)) {
			nrOfIterations++;
			// step 2
			factors = toPrimeFactors(number);
			// step 3
			number = 0;
			for (int factor : factors)
				number += factor;
			// step 4
		}
		int[] returnValues = { number, nrOfIterations };
		return returnValues;
	}

	public static ArrayList<Integer> toPrimeFactors(int number) {

		int rest = number;
		// Only need to test primes up to sqrt of number
		double sqrt = Math.sqrt(number);
		ArrayList<Integer> possibleFactors = getPrimes(sqrt);
		ArrayList<Integer> foundFactors = new ArrayList<Integer>();

		int prime = 0;
		for (int i = 0; i < possibleFactors.size() && prime < sqrt && rest != 1; i++) {
			prime = possibleFactors.get(i);
			while (rest % prime == 0) {
				foundFactors.add(prime);
				rest /= prime;
			}
		}
		// if the last prime is greater than sqrt
		if (rest != 1)
			foundFactors.add(rest);

		return foundFactors;
	}

	// returns the list of primes, request generation of more primes if needed.
	private static ArrayList<Integer> getPrimes(double sqrt) {
		if (primes.get(primes.size() - 1) < sqrt)
			generateMorePrimes(sqrt);

		return primes;
	}

	// generate primes up to given value
	private static void generateMorePrimes(double sqrt) {
		int testForPrime = primes.get(primes.size() - 1) + 1;
		while (testForPrime < sqrt) {
			if (isPrime(testForPrime)) {
				primes.add(testForPrime);
			}
			testForPrime++;
		}

	}

	// check if arguments is a prime, all primes before it must be found!!
	private static boolean isPrime(int testForPrime) {
		for (int prime : primes) {
			if (testForPrime == prime)
				return true;
			if (testForPrime % prime == 0)
				return false;
		}
		return true;
	}

	

	// Main
	public static void main(String[] args) {
		String currentLine;
		int number;
		int[] response;
		boolean exit = false;
		generateMorePrimes(31623);
		while (!exit) {
			currentLine = sc.nextLine();
			number = Integer.parseInt(currentLine);
			if (number != 4) {
				response = getPrimeReduction(number);
				System.out.println(response[0] + " " + response[1]);
			} else {
				exit = true;
			}

		}
	}

}
