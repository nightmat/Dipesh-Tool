package view;

import java.awt.EventQueue;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;

import controller.MainPageController;
import database.ContextQueries;
import database.CreateTestCaseQueries;
import database.DatabaseConnection;
import database.EffectQueries;
import database.PriorityQueries;
import database.ProbabilityQueries;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class MainPage {
	public MainPage mainPage;
	
	
	public JFrame frame;
	
	private JButton btnCheckTestCases;
	private JButton btnCreateNewTest;
	private JButton btnCancel, btnSelectTestCases, btnCheckTestResults;
	
	private MainPageController mainPageController;

	/**
	 * Launch the application.


	/**
	 * Create the application.
	 */
	public MainPage() {
		initComponents();
		initialize();
		setUpListeners();
	}
	
	
	public void initComponents(){
		btnCheckTestCases = new JButton("List Test Cases");
		btnCheckTestCases.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCreateNewTest = new JButton("Add Test Case");
		btnCreateNewTest.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCheckTestResults = new JButton("Check Test Results"); 
		btnCheckTestResults.setFont(new Font("Tahoma", Font.PLAIN, 14));
		mainPageController= new MainPageController();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1234, 768);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Case Selector");
		
		
		btnCheckTestCases.setBounds(417, 171, 305, 80);
		frame.getContentPane().add(btnCheckTestCases);
		
		btnCheckTestResults.setBounds(417, 303, 305, 80);
		frame.getContentPane().add(btnCheckTestResults);
		
		btnCreateNewTest.setBounds(417, 39, 305, 80);
		frame.getContentPane().add(btnCreateNewTest);
		
		btnSelectTestCases = new JButton("Test Case Selection");
		btnSelectTestCases.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		btnSelectTestCases.setBounds(417, 437, 305, 80);
		frame.getContentPane().add(btnSelectTestCases);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 14));
	
		btnCancel.setBounds(417, 580, 305, 80);
		frame.getContentPane().add(btnCancel);
	}

	public void setUpListeners(){
		btnCreateNewTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				mainPageController.runcreateTestCasePage();
			}
		});
		
		btnCheckTestCases.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();				
				mainPageController.runListCasesPage();
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		
		btnSelectTestCases.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				mainPageController.runSelectionPage();			
			}
		});
		
		btnCheckTestResults.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				mainPageController.runListResultPage();	
			}
		});
	}
}
