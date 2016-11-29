import java.util.Scanner;

public class ToiletSeat {
	public abstract class SeatFlipPolicy {

		public int countSeatFlips(char initState, char[] preferencesQueue) {
			int flips = 0;
			char currentState = initState;
			char pref;
			for (int i = 0; i < preferencesQueue.length; i++) {
				pref = preferencesQueue[i];
				if (pref != currentState) {
					currentState = pref;
					flips++;
				}

				if(flipAfterRule(currentState)){
					flips ++;
					currentState = flip(currentState);
				}

			}
			return flips;
		}

		private char flip(Character currentState) {
			if(currentState == 'U')
				return 'D';
			else if(currentState == 'D')
				return 'U';
			else return 'X';
			
			
		}

		protected boolean flipAfterRule(Character currentState) {
			return false;
		}
	}

	public class AlwaysUpPolicy extends SeatFlipPolicy {
		protected boolean flipAfterRule(Character currentState) {
			return (currentState != 'U');
		}
	}

	public class AlwaysDownPolicy extends SeatFlipPolicy {
		protected boolean flipAfterRule(Character currentState) {
			return (currentState != 'D');
		}
	}

	public class AsPrefeeredPolicy extends SeatFlipPolicy {
		protected boolean flipAfterRule(Character currentState) {
			// do nothing, extecting the seat to be in prefeered state
			return false;
		}
	}

	public static Scanner sc = new Scanner(System.in);

	public int[] countSeatFlips(String input, SeatFlipPolicy... flipPolicies) {
		int[] result = new int[flipPolicies.length];
		char initState = input.charAt(0);
		char[] preferencesQueue = input.substring(1).toCharArray();

		for (int i = 0; i < flipPolicies.length; i++) {
			result[i] = flipPolicies[i].countSeatFlips(initState, preferencesQueue);
		}
		return result;
	}

	public static void main(String[] args) throws Exception {

		ToiletSeat toiletSeat = new ToiletSeat();
		SeatFlipPolicy alwaysUpPolicy = toiletSeat.new AlwaysUpPolicy();
		SeatFlipPolicy alwaysDownPolicy = toiletSeat.new AlwaysDownPolicy();
		SeatFlipPolicy asPrefeeredPolicy = toiletSeat.new AsPrefeeredPolicy();

		String currentLine = sc.nextLine();
		int[] response;

		response = toiletSeat.countSeatFlips(currentLine, alwaysUpPolicy, alwaysDownPolicy, asPrefeeredPolicy);
		String output = "";
		for (int i = 0; i < response.length; i++) {
			if (i != 0)
				output += "\n";
			output += response[i];
		}
		System.out.println(output);

	}

}
