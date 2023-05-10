package main;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class JustPlayground {

	public static void main(String[] args) {
		int[] ligma = {1,2,3};
		printArray(ligma);
		changeShit(ligma);
		printArray(ligma);
		// WHY DO I LEARN, THAT THIS WORKS ONLY NOW?!

		getHHMM();
		getHHMM2();
	}
	
	public static void printArray(int[] array) {
		for (int i : array) {
			System.out.println(i);
		}
		System.out.println();
	}
	
	public static void changeShit(int[] array) {
		array[0] = 2;
	}
	
	private static int getHHMM() {
		String currentTime = LocalTime.now().toString();
		int result = Integer.parseInt(currentTime.replace(":", "").substring(0, 4));
		System.out.println(result);
		return 0;
		
		
	}
	
	private static void getHHMM2() {
		LocalTime currentTime = LocalTime.now(ZoneId.of("Europe/Riga"));
		int minute = currentTime.getMinute();
		if (minute >= 30) {
		    currentTime = currentTime.plusHours(1);
		}
		currentTime = currentTime.truncatedTo(ChronoUnit.HOURS);
		int result = Integer.parseInt(currentTime.toString().replace(":", ""));
		System.out.println(result);
	}

}
