package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import model.Consequence;
import model.Context;
import model.Effect;
import model.Model;
import model.ModelConstraint;
import model.Priority;
import model.Probability;
import model.RCUType;
import model.Risk;
import model.TestCase;
import database.DatabaseConnection;

public class ReadTestCasesArtificialData {

	public DatabaseConnection databaseConnection = new DatabaseConnection();
	public TestCase testCase[] = new TestCase[200];
	private ArrayList<TestCase> testCaseList = new ArrayList<>() ;
//	public void main(String[] args) throws Exception {
//		readFile();
//	}
	
	
	public void readFile(){		
		try {
		    BufferedReader in = new BufferedReader(new FileReader("C:\\Personal\\practice\\files\\case200.txt"));
		    String str;
		    int i=0;
		    while ((str = in.readLine()) != null){
//		    	if (i==1000)
//		    		break;
		    	String line = str;
		    	String[] details = line.split("\t");
		    	
		    	testCase[i] = new TestCase();
		    	Priority priority = new Priority();
				Probability probability = new Probability();
				Consequence consequence = new Consequence();
				Risk risk = new Risk();
				Context context = new Context();
				RCUType rcuType = new RCUType();
				ModelConstraint modelConstraint = new ModelConstraint();
				Effect effect = new Effect();
				Model model = new Model();
		    	
		    	
		    	testCase[i].setId(Integer.parseInt(details[0]));
		    	testCase[i].setCaseName(details[1]);
		    	
		    	priority.setName(details[2]);
		    	testCase[i].setPriority(priority);
		    	
		    	probability.setName(details[3]);
		    	testCase[i].setProbability(probability);
		    	
		    	consequence.setName(details[4]);
		    	testCase[i].setConsequence(consequence);
		    	
		    	testCase[i].setTimeExecution(Double.parseDouble(details[5]));
		    	
		    	context.setName(details[6]);
		    	testCase[i].setContext(context);
		    	
		    	rcuType.setName(details[7]);
		    	testCase[i].setRcuType(rcuType);
		    	
		    	modelConstraint.setName(details[8]);
		    	testCase[i].setModelConstraint(modelConstraint);
		    	
		    	effect.setName(details[9]);
		    	testCase[i].setEffect(effect);
		    	
		    	model.setName(details[10]);
		    	testCase[i].setModel(model);

		    	risk.setName(details[11]);
		    	testCase[i].setRisk(risk);
		    	
		    	
		    	i++;
		    }
		    in.close();
		} catch (IOException e) {
		}
		
	}
	
	public ArrayList<TestCase> getTestCaseContents(int caseNumber,String context, String component,String constraint, String effect){
		for (int i=0;i<caseNumber;i++){
			if (!context.isEmpty())
				if ((testCase[i].getContext().getName())!= context)
					break;
			
			if (!component.isEmpty())
				if ((testCase[i].getRcuType().getName())!= component)
					break;
			
			if (!constraint.isEmpty())
				if ((testCase[i].getModelConstraint().getName())!= effect)
					break;
			
			if (!effect.isEmpty())
				if ((testCase[i].getEffect().getName())!= effect)
					break;
			
			testCaseList.add(testCase[i]);
			
			}

		return testCaseList;
		
	}
	
}
