package main;

public class JustPlayground {

	public static void main(String[] args) {
		int[] ligma = {1,2,3};
		printArray(ligma);
		changeShit(ligma);
		printArray(ligma);
		// WHY DO I LEARN, THAT THIS WORKS ONLY NOW?!
		
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

}
