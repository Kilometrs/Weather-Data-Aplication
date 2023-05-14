package main;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JustPlayground {

	public static void main(String[] args) {
//		int[] ligma = {1,2,3};
//		printArray(ligma);
//		changeShit(ligma);
//		printArray(ligma);
//		// WHY DO I LEARN, THAT THIS WORKS ONLY NOW?!
//
//		getHHMM();
//		getHHMM2();
		
		System.out.println(getInt("+12°C"));
	}
	
	public static int getInt(String input) {
	    Pattern doublePattern = Pattern.compile("([-+]?\\d+[.]\\d+)");
	    Matcher matcher = doublePattern.matcher(input);
	    if (matcher.find()) {
	        String doubleMatch = matcher.group(1);
	        try {
	            double result = Double.parseDouble(doubleMatch);
	            return (int) Math.round(result);
	        } catch (NumberFormatException e) {
	            System.out.println("Could not parse double value: " + doubleMatch);
	        }
	    }
	    Pattern intPattern = Pattern.compile("([-+]?\\d+)");
	    matcher = intPattern.matcher(input);
	    if (matcher.find()) {
	        String intMatch = matcher.group(1);
	        try {
	            return Integer.parseInt(intMatch);
	        } catch (NumberFormatException e) {
	            System.out.println("Could not parse int value: " + intMatch);
	        }
	    }
	    System.out.println("No match found in input: " + input);
	    return 0;
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
