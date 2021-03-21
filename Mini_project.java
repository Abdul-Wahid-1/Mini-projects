package mini_project;
//NAME Abdul Wahid
//DATE 16/10/2020
//VERSION 1
//BRIEF OVERVIEW OF PURPOSE
/*
 Program simulates dinosaurs that must be cared for
 - A dinosaur is picked (& then given a hunger & calmness)
 - A round type game played where dinosaur soothed or fed until user dies
 - The stats can be stored and viewed via main menu
 - Game can keep being played until user exits main menu
 */

import java.util.Scanner;
import javax.swing.*;
import java.io.*;
import java.util.Random;


public class Mini_project_lvl_7 {
	public static void main(String [] args)throws IOException{
		boolean NewUser = yesnoinput("Are you new here?","Quick question");
		if(NewUser){
			mainmenu(NewUser);
		}else {
			mainmenu(NewUser);
		}
		System.exit(0);
	}
	
	/*___________________________________ 
	  |                                 |
	  |   Methods that shorten (input)  |
	  |_________________________________|
	*/
	
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
		boolean checknum = false;
		
		//while loop used to keep repeating until valid numbers are input
		while(!checknum){
			boolean isdigit = true;
			//for loop used to check whether the input are all numbers
			
			for(int i = 0;i<textinput.length();i++){
				if(!(Character.isDigit(textinput.charAt(i)))){
					isdigit = false;
					i = textinput.length() + 1;
				}
			}
		
			if(!(isdigit)){
				textinput = inputstring("That wasn't a number, please re-enter a valid integer (i.e. no letters/symbols): ");
			}else if(textinput.isEmpty()) {
				textinput = inputstring("There was no entry, please re-enter a valid integer: ");
			}else{
				checknum = true;
			}
		}
		
		return Integer.parseInt(textinput);
	}
	
	//Method just to shorten joptionpane and having to re-enter null for printing
	public static void Jprint(String message){
		JOptionPane.showMessageDialog(null, message);
	}
	
	//Method just to shorten joptionpane and having to re-enter null for input
	public static String inputstringJ(String message) {
		String input = JOptionPane.showInputDialog(null, message);
		while(input == null) {
			Jprint("Invalid, no option chosen");
			input = JOptionPane.showInputDialog(null, message);
		}
		return input;
	}
	
	//Method that prints a closed ended question to the screen allowing user to press yes or no
	public static boolean yesnoinput(String question, String boxdialogue) {
        int input = JOptionPane.showConfirmDialog(
            null,
            question,
            boxdialogue,
            JOptionPane.YES_NO_OPTION);
        
        //while loop used to keep iterating until user presses either yes or no
        while(!(input == JOptionPane.YES_OPTION|input == JOptionPane.NO_OPTION)) {
        	input = JOptionPane.showConfirmDialog(
                    null,
                    "Sorry, either press yes or no\n"+question,
                    boxdialogue,
                    JOptionPane.YES_NO_OPTION);
        }
        
        if (input == JOptionPane.YES_OPTION) {
        	return true;
        }else {
        	return false;
        }
	}
	
	/*___________________________________ 
	  |                                 |
	  |        Compound methods         |
	  |_________________________________|
	*/
	
	//Method used to print the main menu where user will be refered back to each time
	//Brings together all other methods to actually do the task
	public static void mainmenu(boolean NewUser)throws IOException {
		boolean end = false;
		if(NewUser) {
			info();
			Jprint("The next pop up will be the main menu window "
					+ "\nwhere you will be refered back to each time you finish your chosen task");
		}
		
		//while loop used to always come back to main menu
		while(!end) {
			String choice = inputstringJ("Main menu\n1. Play Game\n2. View leaderboards\n3. Quit");
			if(choice.equals("1")){
				dinoinfo dino = new dinoinfo();
				if(NewUser) {
					setdifficulty(dino);
					choosedinonew(dino);
					printroundinfo(NewUser);
					rounds(dino);
				}else {
					setdifficulty(dino);
					choosedinoexisting(dino);
					printroundinfo(NewUser);
					rounds(dino);
				}
			}else if(choice.equals("2")) {
				leaderboard();
			}else if(choice.equals("3")){
				Jprint("Hope to see you again!");
				end = true;
			}else{
				Jprint("Invalid input \nPlease pick from the options that were stated (1,2,3)");
			}
		}
	}	
	
	//Method that brings together other methods allowing user to pick dinosaur
	//When new user goes through this method
	public static void choosedinonew(dinoinfo dino) {
		welcome();
		dinolist(dinos());
		
		String decision = inputstring("Please enter the number associated with the dinosaur here: ");
		String statement = whatdino(dino, decision, dinos());
		
		print(statement + "\n");
		dinocalmhunger(dino);
		dinostate(dino,false);
	}
	
	//Method that brings together other methods allowing user to pick dinosaur
	//When existing user goes through this method
	public static void choosedinoexisting(dinoinfo dino1) {
		welcome();
		dinolist(dinos());
		String decision = inputstring("Please enter the number associated with the dinosaur here: ");
		whatdino(dino1, decision, dinos());
	}
	
	//Method used to allowed rounds to be played and prints a statement once done
	public static void rounds(dinoinfo d)throws IOException{
		int i = 0;
		boolean end = false;
		printline();
		
		while(!end) {
			i++;
			print("                                        ROUND " + i + "                                        ");
			print("");
			randomisestats(d);
			changedinostate(d);
			boolean check = dinostate(d, true);
			if(check) {
				end = true;
			}
			printline();
		}
		
		String dead = ("Your dead (x_x)\n");
		if(i>=20) {
			Jprint(dead + "Woah, you must be really lucky, you survived " + i + " rounds");
		}else if(i>=10){
			Jprint(dead + "Nicely done, you survived " + i + " rounds");
		}else if(i>=5){
			Jprint(dead + "Not bad, you survived " + i + " rounds");
		}else if(i==1){
			Jprint(dead + "Pathetic, you survived 1 round");
		}else {
			Jprint(dead + "You could've done better than that, you survived " + i + " rounds");
		}
		
		recordscore(i, d);
	}
	
	//Method that asks user whether the want to feed or soothe the dinosaur to change its state
	//By pressing either the hunger/calmness level well be altered
	public static dinoinfo changedinostate(dinoinfo d) {
		String feedsoothe = inputstring("Would you like to feed or soothe the dinosaur? ");
		feedsoothe = feedsoothe.toLowerCase();
		
		while(!(feedsoothe.equals("feed")|feedsoothe.equals("soothe"))) {
			feedsoothe = inputstring("Ooops, please enter either feed or soothe? ");
			feedsoothe = feedsoothe.toLowerCase();
		}
		
		if(feedsoothe.equals("soothe")) {
				addcalmness(d);
				print("The " + getspecies(d) + " has been soothed");
		}else {
				minushunger(d);
				print("The " + getspecies(d) + " has been fed");
		}
		
		return d;
	}
	
	//Checks what dinosaur the user has chosen and returns the statement associated with it
	public static String whatdino(dinoinfo d, String decision, String [] dino) {
		String statement = "Fact: ";
		
		if(!(decision.equals("1") | decision.equals("2") | decision.equals("3") | decision.equals("4"))){
			printdash();
		}
		while(!(decision.equals("1") | decision.equals("2") | decision.equals("3") | decision.equals("4"))) {
			print("Invalid input");
			decision = inputstring("Please re-enter the number associated with the dinosaur here: ");
			printdash();
		}
		
		switch(decision){
		case "1":
			setspecies(d, dino[0]);
			statement = statement + dino[0] + "s are small (compared to other dinosaurs)";
			break;
		case "2":
			setspecies(d, dino[1]);
			statement = statement + dino[1] +" have long mouths";
			break;
		case "3":
			setspecies(d, dino[2]);
			statement = statement + dino[2]+" have horns";
			break;
		case "4":
			setspecies(d, dino[3]);
			statement = statement + dino[3] +" are herbivores";
			break;
		}
		
		return statement;
	}
	
	//Asks user to input a calmness and hunger score for the dinosaur
	//Also checks whether this number is within the given range
	public static void dinocalmhunger(dinoinfo d) {
		int calmness = inputInt("Please enter the calmness score for the dinosaur(1-8 where 8 is calm): ");
		final int max = 8;
		final int min = 1;
		
		if(min>calmness | calmness>max) {
			printdash();
		}
		while(min>calmness | calmness>max) {
			calmness = inputInt("Invalid input\nPlease re-enter the calmness score for the dinosaur (IN BETWEEN 1 & 8): ");
			printdash();
		}
		
		int hunger = inputInt("\nPlease enter the hunger score for the dinosaur(1-8 where 8 is hungry): ");
		if(min>hunger | hunger>max) {
			printdash();
		}
		while(min>hunger | hunger>max) {
			hunger = inputInt("\nInvalid input\nPlease re-enter the hunger score for the dinosaur (IN BETWEEN 1 & 8): ");
			printdash();
		}
		
		sethunger(d, hunger);
		setcalmness(d, calmness);
	}
	
	//Checks the dinosaurs state and prints out message accordingly
	public static boolean dinostate(dinoinfo dino, boolean secondtime) {
		int dangerlevel = gethunger(dino)-getcalmness(dino);
		
		
		if(secondtime) {
			if(dangerlevel <1) {
				dangerlevel = 1;
			}else if(dangerlevel>8) {
			dangerlevel = 8;
			}
		}else {
			if(dangerlevel <1) {
				dangerlevel = 1;
			}
		}
		
		boolean isdead = false;
		if(dangerlevel<=2){
			print("The "+ getspecies(dino) + " is looking very calm");
		}else if(dangerlevel<=4){
			print("The " + getspecies(dino) + " seems to be aroused");
		}else if(dangerlevel<=6){
			print("The " + getspecies(dino) + " seems to be angry");
		}else if(dangerlevel<=8){
			print("The " + getspecies(dino) + " is looking to kill");
			isdead = true;
		}
		print("");
		
		return isdead;
	}
	
	/*___________________________________ 
	  |                                 |
	  |   File input/output & sorting   |
	  |_________________________________|
	*/
	
	//Method used to ask user if they want to record  score or not
	//i.e. whether there should be file input/output or not
	public static void recordscore(int i, dinoinfo d)throws IOException{
		boolean record = yesnoinput("Would you like to record your score?"
				+ "\n(if its recorded it can be seen on the leaderboard)","Save");
		
		if(record) {
			boolean lessthan = false;
			String username = "";
			
			while(!lessthan) {
				username = inputstringJ("Please enter you username");
				
				if(username.length()<=10) {
					lessthan = true;
				}else {
					Jprint("Sorry your username has to be 10 characters or less");
				}
			}
			appendfile(username, i, getspecies(d), getdifficulty(d));
		}else {
			Jprint("Make sure to record your score next time \nif you think you have a high score");
		}
	}

	//Method used to count the lines of a file	
	public static int readlines()throws IOException{
		int linecount = 0;
		BufferedReader readlines = new BufferedReader(new FileReader("dinosurvivalleaderboard.txt"));
		String returnline;
		
		
		while((returnline = readlines.readLine())!= null) {
			linecount++;
		}
		readlines.close();
		
		return linecount;
	}
	
	//Method that reads a file and then orders it printing the leaderboard
	public static void leaderboard()throws IOException{
		try {
			BufferedReader readfile = new BufferedReader(new FileReader("dinosurvivalleaderboard.txt"));
			String line_in_file;
			final int numberoflines = (readlines());
			final int max = numberoflines/4;
			int i =0;
			String [] holdline = new String [numberoflines];
			String names [] = new String [max];
			int rounds [] = new int [max];
			String species [] = new String[max];
			String difficulty [] = new String[max];
			
			
			while((line_in_file = readfile.readLine())!= null) {
				holdline[i] = line_in_file;
				i++;
			}
	
			readfile.close();
			
			splitarray(holdline, max, rounds, names, species, difficulty);
			SortArraysElements(rounds, names, species, difficulty);
			
			String [] STRrounds = new String[max];
			for (int j = 0; j < max; j++) {
	            STRrounds[j] = String.valueOf(rounds[j]);
			}
			
			printleaderboard(max, STRrounds, names, species, difficulty);
			inputstring("\nPress enter to go back to the main menu ");
		}catch(FileNotFoundException e) {
			Jprint("There's no entries :(\n"
					+ "Looks like no ones played the game yet...\n"
					+ "Why don't you play and record your score?\n"
					+ "You'll surely be first on the leaderboard!!");
		}
	}
	
	//Prints leaderboard after alligning the names 
	public static void printleaderboard(int max, String [] rounds1, String [] names1, String [] species1, String [] difficulty1) {
		final String round = "Rounds";
		final String username = "Username   ";
		final String Species = "Species         ";
		final String Difficulty = "Difficulty";
		final String position = "Position ";
		
		String [] pos = new String [max];
		String [] rounds = allign(round, rounds1, max);
		String [] names = allign(username, names1, max);
		String [] species = allign(Species, species1, max);
		String [] difficulty = allign(Difficulty, difficulty1, max);
	
		for(int k = 0; k<max;k++) {
			int place = k+1;
			pos[k] = String.valueOf(place);
			}
		
		String [] placed = allign(position, pos, max);
		
		print("| " + position + "  | "+ username + "  | "+round + "   | "+ Species +"  | "+Difficulty+"  | ");	
		for(int i = 0; i<max;i++) {
		print("| "+placed[i]+" | "+names[i] + " |  " + rounds[i] + " | " + species[i]+ " | " + difficulty[i]+" |");
		}
	}
	
	//Alligns each element so that rows are spaced right
	public static String[] allign(String pass, String [] array, int max) {
		int length;
		String maxlength = "";
		String space = " ";
		String storespace = "";
		
		for(int i = 0; i<max;i++) {
			if(pass.length()<array[i].length()) {
				if(maxlength.length()<array[i].length()) {
					maxlength = array[i];
				}
			}else{
				if(maxlength.length()<pass.length()) {
					maxlength = pass;
				}
			}
		}
		
		length = maxlength.length();
		
		for(int j = 0; j<max;j++) {
			if(array[j].length()<length) {
				int templength = length-array[j].length();
				for(int k = 0; k<=templength; k++) {
					storespace = storespace + space;
				}
				array[j] = array[j]+storespace;
				storespace = "";
			}else{
				int templength = array[j].length()-length;
				for(int k = 0; k<=templength; k++) {
					storespace = storespace + space;
				}
				pass = pass + storespace;
				storespace = "";
			}
		}
		
		return array;
	}
	
	//Method that appends to a file and adds lines for round, name, species and difficulty
	public static void appendfile(String username, int round, String species, String difficulty) throws IOException{
		FileWriter writetofile = new FileWriter("dinosurvivalleaderboard.txt", true);
	    BufferedWriter append = new BufferedWriter(writetofile);
	    
	    append.write(round + "\n" + username+ "\n" +species + "\n" + difficulty);
	    append.newLine();
	    
	    append.close();
	}
	
	//Method that takes one array and splits it into four others
	public static void splitarray(String [] holdline, int max, int [] rounds, String [] names, String [] species, String [] difficulty) {
		int nameposition = 1;
		int roundposition = 0;
		int speciesposition = 2;
		int difficultyposition = 3;
		
		for(int j = 0; j< max;j++) {
			names[j] = holdline[nameposition]; 
			rounds[j] = Integer.parseInt(holdline[roundposition]);
			species[j] = holdline[speciesposition];
			difficulty[j] = holdline[difficultyposition];
			
			roundposition = roundposition+4;
			nameposition = nameposition+4;
			speciesposition = speciesposition+4;
			difficultyposition = difficultyposition+4;
		}
	}
	
	//Method used to sort the elements of four arrays
	public static void SortArraysElements(int [] rounds, String [] names, String [] species, String [] difficulty){
		boolean resume = true;
	    int SwapRound;
	    String SwapName;
	    String SwapSpecies;
	    String SwapDifficulty;
	    
	    while(resume){
	        resume = false;
			for(int i = 0;i < rounds.length-1; i++){              
	            if(rounds[i] < rounds[i+1]){
	            	SwapRound = rounds[i];
	            	rounds[i] = rounds[i+1];
	                rounds[i+1] = SwapRound;
	                
	                SwapName = names[i];
	                names[i] = names[i+1];
	                names[i+1] = SwapName;
	                
	                SwapSpecies = species[i];
	                species[i] = species[i+1];
	                species[i+1] = SwapSpecies;

	                SwapDifficulty = difficulty[i];
	                difficulty[i] = difficulty[i+1];
	                difficulty[i+1] = SwapDifficulty;
	                
	                resume = true;
	            }
	        }
	    }
	}
	
	/*___________________________________ 
	  |                                 |
	  |  Methods that just print/return |
	  |_________________________________|
	*/
	
	//Used to print message about what the program is about
	public static void info() {
		Jprint("                     This program simulates bio-engineered dinosaurs that must be cared for\n"
				+ "You must choose from the dinosaurs in the list to care for and give it a calmness and hunger score\n"
				+ "                                                               Press ok to continue");
	}
	
	//prints a welcome message along with a text art picture of a dinosaur
	public static void welcome() {
		print("-----------------------------------------WELCOME TO------------------------------------");
		print("           ________________________                     ___._\n" + 
				"           |                      |                 .'  <0>'-.._\n" + 
				"           |    JURASSIC WORLD    |               /  /.--.____\")\n" + 
				"           |______________________|                |   \\   __.-'~\n" + 
				"                                                 |  :  -'/\n" + 
				"                                                /:.  :.-'\n" + 
				"	                                         | : '. |\n" + 
				" 		--¬.____.______    _.----.-----./      :/\n" + 
				"        	'--      ' `'----/       '-.   -  __ :/\n" + 
				"               '-._         _ :   *  \\   .'  )/\n" + 
					"                    '----._  :    _    _.-'   ] /  _/\n" + 
				"                         '-._   _   _/ *   _/ / _/\n" + 
				"                             \\_ .-'*___.-'__< |  \\___\n" + 
				"                               <___:___.\\    \\_\\_---.7\n" + 
				"                              |   /'=r_.-'     _\\\\ =/\n" + 
				"                          .--'   /            ._/'>\n" + 
				"                        .'   _.-'\n" + 
				"                     / .--'n" + 
				"                      /,/\n" + 
				"                      |/`)\n" + 
				"                      'c=,");
	}
	
	//Prints out the list of dinosaurs
	public static void dinolist(String [] dino){
		print("\n\n Dinosaurs to care for\n"
				+ " ______________________\n"
				+ " |                    |\n"
				+ " | 1. "  + dino[0] +"    |\n"
				+ " | 2. " + dino[1] + "     |\n"
				+ " | 3. " + dino[2] + "     |\n"
				+ " | 4. " + dino[3] + " |\n"
				+ " |____________________|\n\n");
	}
	
	//prints a line of underscores
	public static void printline() {
		print("_______________________________________________________________________________________");
		print("");
	}

	//prints a line of dashes
	public static void printdash(){
		print("");
		print("--------------------------------------------------------------------------------------");
		print("");	
	}
	
	//method returns an array holding dinosaur names
	//used to shorten the dinosaurs names and implement throughout whole program
	public static String[] dinos() {
		final String [] dinobank = {"Velociraptor", "Spinosaurus", "Carnotaurus", "Parasaurolophus"};
		
		return dinobank;
	}
	
	//Prints instructions pertaining to the rounds
	public static void printroundinfo(boolean NewUser) {
		if(NewUser) {
		Jprint("           Well done, now the dinosaurs hunger and calmnesss level will vary by random amounts\n"
				+ "Survive the rounds by soothing or feeding the dinosaur to change its state without getting it too angry\n"
				+ "                                                                     Press ok to continue");
		}else {
			Jprint("Survive the dinosaurs predatorial instinct\n"
					+ "for as long as you can\n"
					+ "Good Luck!");
		}
	}

	/*___________________________________ 
	  |                                 |
	  |              ADTs               |
	  |_________________________________|
	*/

	//used to randomise hunger & calmness levels
	//range depends on what difficulty was chosen
	public static void randomisestats(dinoinfo d) {
		Random number = new Random();
		int i;
		int j;
		
		if(getdifficulty(d).equals("Easy")) {
			i = 5;
			j = 6;
		}else if(getdifficulty(d).equals("Medium")) { 
			i = 6;
			j = 7;
		}else {
			i = 12;
			j = 9;
		}
		
		d.hunger = number.nextInt(i)+j; 
		d.calmness = number.nextInt(i)+1;
	}
	
	//used to subtract hunger by a random amount
	public static void minushunger(dinoinfo d) {
		Random number = new Random();
		int num = number.nextInt(6);
		
		d.hunger = d.hunger - num;
	}
	
	//used to add calmness by a random amount
	public static void addcalmness(dinoinfo d) {
		Random number = new Random();
		int num = number.nextInt(6);
		
		d.calmness = d.calmness + num;
	}	
	
	/*___________________________________ 
	  |                                 |
	  |         Accessor methods        |
	  |_________________________________|
	*/
	
	//Accessor method for difficulty to set it
	public static void setdifficulty(dinoinfo dino) {
		String difficulty = inputstringJ("Before we start\nPlease enter a difficulty level from\nEasy\nMedium\nHard");
		
		while(!(difficulty.toLowerCase().equals("easy")|difficulty.toLowerCase().equals("medium")|difficulty.toLowerCase().equals("hard"))) {
			difficulty = inputstringJ("Invalid input\nPlease enter a difficulty level from\nEasy\nMedium\nHard");
		}
		
		difficulty = Character.toString(difficulty.charAt(0)).toUpperCase() + difficulty.substring(1).toLowerCase();
		dino.difficulty = difficulty;
	}
	
	//Accessor method for hunger to set it
	public static dinoinfo sethunger(dinoinfo d, int hunger) {
		d.hunger = hunger;
		
		return d;
	}
	
	//Accessor method for calmness to set it
	public static dinoinfo setcalmness(dinoinfo d, int calmness) {
		d.calmness = calmness;
		
		return d;
	}
	
	//Accessor method for species to set it
	public static dinoinfo setspecies(dinoinfo d, String species) {
		d.species = species;
		
		return d;
	}
	
	//Accessor method for username to set it
	public static dinoinfo setusername(dinoinfo d, String username) {
		d.username = username;
		
		return d;
	}
	
	//Accessor method for species to extract it
	public static String getspecies(dinoinfo d) {
		return d.species;
	}
	
	//Accessor method for hunger to extract it
	public static int gethunger(dinoinfo d) {
		return d.hunger;
	}
	
	//Accessor method for calmness to extract it
	public static int getcalmness(dinoinfo d) {
		return d.calmness;
	}
	
	//Accessor method for difficulty to extract it
	public static String getdifficulty(dinoinfo d) {
		return d.difficulty;
	}
	
}
//record to store information pertaining to the dinosaur
class dinoinfo{
	String species;
	int hunger;
	int calmness;
	String difficulty;
	String username;
}