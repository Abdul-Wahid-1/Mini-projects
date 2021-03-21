package mini_project;

import java.util.Scanner;

public class Mini_project {
	public static void main(String [] args) {
		
	}
	
	//Method to make System.out.println shorter
	public static void print(String message) {
		System.out.println(message);
	}
	
	//Method for inputting string
	public static String inputstring(String message) {
		String answer;
		Scanner scanner = new Scanner(System.in);
		
		System.out.print(message);
		answer=scanner.nextLine();
		
		return answer;
	}
	
	//Method for inputting integer
	public static int inputInt(String message) {
		String textinput = inputstring(message);
		int answer = Integer.parseInt(textinput);
		
		return answer;
	}
}
