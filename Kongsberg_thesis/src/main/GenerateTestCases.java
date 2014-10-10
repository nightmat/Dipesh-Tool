package main;

import java.io.File;
import java.sql.SQLException;
import java.util.Random;

import model.Effect;
import model.Priority;
import model.TestCase;
import database.DatabaseConnection;

public class GenerateTestCases {

	public static DatabaseConnection databaseConnection = new DatabaseConnection();
	public static TestCase testCase[] = new TestCase[2000];
	private static File fileName;
	
	public static void main(String[] args) {
		databaseConnection.connect();
		//generateRuns();
	
	}
	
	
	public static void generateRuns(){
		
		
	}
	
	public static void addCase(){
		int min=1, max1= 3, max2 = 5;
		for (int i=0;i<2000;i++){
			Priority priority = new Priority();
			testCase[i] = new TestCase();
			testCase[i].setCaseName("Case "+i);
			int num2 = randInt(min,max2);
			priority.setName(returnName2(num2));
			testCase[i].setPriority(priority);
			
		}
	}
	
	
	public static void sqlCode(int i, int caseId, int min, int max){
		int num = randInt(min,max);
		String value= null;
		if (num ==1)
			value = "pass";
		else if (num ==2)
			value = "fail";
		else if (num==3)
			value = "undecided";
		
		
		
	}
	
	public static String returnName1(int value){
		String name1 = null;
		if (value ==1)
			name1 = "high";
		else if (value ==2)
			name1 = "medium";
		else if (value==3)
			name1 = "low";
		
		return name1;
	}
	
	public static String returnName2(int value){
		String name1 = null;
		if (value ==1)
			name1 = "higher";
		else if (value ==2)
			name1 = "high";
		else if (value==3)
			name1 = "medium";
		else if (value ==2)
			name1 = "low";
		else if (value==3)
			name1 = "lower";
		return name1;
	}
	
	public void createFile() throws Exception{
		fileName = new File("C:\\Personal\\practice\\files\\test.txt");
		

		// if file does not exists, then create it
		if (!fileName.exists()) {
			fileName.createNewFile();
		}
	}
	public static int randInt(int min, int max) {

	    // NOTE: Usually this should be a field rather than a method
	    // variable so that it is not re-seeded every call.
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	

	
	
}
