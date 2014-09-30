package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Context;
import model.Effect;
import model.Model;
import model.ModelConstraint;
import model.Priority;
import model.Probability;
import model.RCUType;
import model.TestCase;
import model.TestRunResult;

public class OptimizeQueries {
	public ResultSet resultSet;

	List<Integer> caseNum = new ArrayList<>();
	private TestRunResult testRunResult = new TestRunResult();
	ArrayList<TestRunResult> testRunList = new ArrayList<TestRunResult>();
	
	public void getCase(DatabaseConnection databaseConnection){
		String selectCase= "SELECT id from testcase ORDER BY id";
		resultSet = databaseConnection.queryTable(selectCase);
		
		try{
			while (resultSet.next()) {
				caseNum.add(Integer.parseInt(resultSet.getString("id")));
			
			}
			resultSet.close();
		}catch(SQLException e) {			 
			System.out.println(e.getMessage());
 
		}
	}
	
	
	public void calculate(DatabaseConnection databaseConnection){
		getCase(databaseConnection);
	
		int j= 0,i=0;
		
		while (caseNum.size()>j){
			getRun(databaseConnection,caseNum.get(j));
			j++;
		}
		while (caseNum.size()>i){
			String prob=null;
			double probability=0;
			int pass=0, fail=0, undecided=0, total=0;
			//getRun(databaseConnection,caseNum.get(j));

			pass= testRunList.get(i).getPass();
			fail= testRunList.get(i).getFail();
			undecided= testRunList.get(i).getUndecided();
			total = pass+fail+undecided;
			
			probability = (double)(fail)/(total);
			
			if (probability<=0.33)
				prob= "low";
			else if (probability<=0.66)
				prob= "medium";
			else if (probability<=1)
				prob= "high";
			
			updateCase(databaseConnection,testRunList.get(i).getCaseId(),prob );
			i++;
		}
		//}
		
	
		
		
	}		
	
	
	public void updateCase(DatabaseConnection databaseConnection, int i, String probability){
		String updateQuery= "UPDATE testcase SET probability='" + probability + "' WHERE id=" + i;
		
		databaseConnection.insertTable(updateQuery);
	}
	
	
	public void getRun(DatabaseConnection databaseConnection, int i){
		testRunResult = new TestRunResult();
		String selectRunPass= "select count(result) from testrun where caseid=" + i+ "and result='pass'";
		String selectRunFail= "select count(result) from testrun where caseid=" + i+ "and result='fail'";
		String selectRunUndecided= "select count(result) from testrun where caseid=" + i+ "and result='undecided'";
		resultSet = databaseConnection.queryTable(selectRunPass);
		
		try{
			while (resultSet.next()) {
				testRunResult.setCaseId(i);
				testRunResult.setPass(Integer.parseInt(resultSet.getString("count")));		
			}
			resultSet.close();
		}catch(SQLException e) {			 
			System.out.println(e.getMessage());
 
		}
		resultSet = databaseConnection.queryTable(selectRunFail);
		try{
			while (resultSet.next()) {
				testRunResult.setCaseId(i);
				testRunResult.setFail(Integer.parseInt(resultSet.getString("count")));			
			}
			resultSet.close();
		}catch(SQLException e) {			 
			System.out.println(e.getMessage());
 
		}
		resultSet = databaseConnection.queryTable(selectRunUndecided);
		try{
			while (resultSet.next()) {
				testRunResult.setCaseId(i);
				testRunResult.setUndecided(Integer.parseInt(resultSet.getString("count")));	
			}
			resultSet.close();
		}catch(SQLException e) {			 
			System.out.println(e.getMessage());
 
		}
		testRunList.add(testRunResult);
		
	}
	
	
	
	
}
