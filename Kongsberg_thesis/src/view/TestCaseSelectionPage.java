package view;

import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import controller.MainPageController;
import model.Context;
import model.Effect;
import model.ExperimentScheduling;
import model.ExperimentSchedulingArtificialData;
import model.ModelConstraint;
import model.Priority;
import model.Probability;
import model.RCUType;
import model.TestCase;
import database.ContextQueries;
import database.CreateTestCaseQueries;
import database.DatabaseConnection;
import database.EffectQueries;
import database.ModelConstraintQueries;
import database.ModelQueries;
import database.OptimizeQueries;
import database.PriorityQueries;
import database.ProbabilityQueries;
import database.RCUTypeQueries;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

import javax.swing.JSlider;
import javax.swing.SwingConstants;

public class TestCaseSelectionPage {

	public DatabaseConnection databaseConnection;
	
	public JFrame frame;
	private JTextField textTime;
	
	
	public PriorityQueries priorityQueries;
	public ProbabilityQueries probabilityQueries;
	public EffectQueries effectQueries;
	public ContextQueries contextQueries;
	private ModelQueries modelQueries;	
	private ModelConstraintQueries modelConstraintQueries;
	private RCUTypeQueries rcuTypeQueries;
	private OptimizeQueries optimizeQueries;
	
	private JButton btnCancel, btnDefaultConfiguration, btnOptimizeCases, btnOptimize, btnOptimizeArtificial, btnCalculateRisk;
	private JComboBox comboBoxEffect, comboBoxContext, comboBoxModel, comboBoxComponent, comboBoxModelConstraint;
	private JSlider sliderPriority, sliderProbability, sliderConsequence, sliderEPriority, sliderEProbability, sliderEConsequence, sliderRisk ;
	
	final int MIN = 0;
	final int MAX = 100;
	final int FPS_INIT = 15; 
	
	private MainPageController mainPageController;
	private SelectionTestCasesPage selectionTestCasesPage;
	private ListTestCasesPage listTestCasesPage;
	private ExperimentScheduling experimentScheduling;
	private ExperimentSchedulingArtificialData experimentSchedulingArtificialData;
	private JLabel lblConsequence;
	private JLabel lblEfficiencyPriority;
	private JLabel lblEfficiencyProbability;
	private JLabel lblEfficiencyConsequence;

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestCaseSelectionPage window = new TestCaseSelectionPage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TestCaseSelectionPage() {
		initComponents();
		
		databaseConnection.connect();
		initialize();
		
		this.fillComboBoxes();
		setUpListeners();
	}
	

	public void initComponents(){
		databaseConnection = new DatabaseConnection();
		priorityQueries = new PriorityQueries();
		probabilityQueries = new ProbabilityQueries();
		effectQueries = new EffectQueries();
		contextQueries = new ContextQueries();
		modelQueries = new ModelQueries();
		rcuTypeQueries = new RCUTypeQueries();
		modelConstraintQueries = new ModelConstraintQueries();
		optimizeQueries = new OptimizeQueries();
		
		mainPageController = new MainPageController();

		listTestCasesPage = new ListTestCasesPage();
		experimentScheduling = new ExperimentScheduling();
		experimentSchedulingArtificialData = new ExperimentSchedulingArtificialData();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 906, 687);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Test Case Selection");
		
		JLabel lblExecutionTime = new JLabel("Execution Time");
		lblExecutionTime.setBounds(59, 70, 101, 14);
		frame.getContentPane().add(lblExecutionTime);
		
		textTime = new JTextField();
		textTime.setBounds(210, 67, 180, 20);
		frame.getContentPane().add(textTime);
		textTime.setColumns(10);
		
		JLabel lblHours = new JLabel("hours");
		lblHours.setBounds(400, 70, 46, 14);
		frame.getContentPane().add(lblHours);
		
		JLabel lblSelectTestCases = new JLabel("Select test cases based on the following choices:");
		lblSelectTestCases.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSelectTestCases.setBounds(59, 20, 375, 14);
		frame.getContentPane().add(lblSelectTestCases);
		
		JLabel label = new JLabel("Context");
		label.setBounds(58, 465, 46, 14);
		frame.getContentPane().add(label);
		
		comboBoxContext = new JComboBox();
		
		comboBoxContext.setBounds(216, 462, 172, 20);
		frame.getContentPane().add(comboBoxContext);
		
		JLabel label_1 = new JLabel("Priority");
		label_1.setBounds(58, 118, 46, 14);
		frame.getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("Probability");
		label_2.setBounds(59, 183, 75, 14);
		frame.getContentPane().add(label_2);
		
		JLabel label_3 = new JLabel("Component Under Test");
		label_3.setBounds(474, 465, 140, 14);
		frame.getContentPane().add(label_3);
		
		comboBoxComponent = new JComboBox();
		comboBoxComponent.setBounds(676, 462, 175, 20);
		frame.getContentPane().add(comboBoxComponent);
		
		JLabel label_4 = new JLabel("Feature");
		label_4.setBounds(475, 527, 97, 14);
		frame.getContentPane().add(label_4);
		
		comboBoxModelConstraint = new JComboBox();
		comboBoxModelConstraint.setBounds(676, 524, 175, 20);
		frame.getContentPane().add(comboBoxModelConstraint);
		
		btnCancel = new JButton("Cancel");
		
		btnCancel.setBounds(227, 610, 120, 23);
		frame.getContentPane().add(btnCancel);
        Font font = new Font("Serif", Font.ITALIC, 15);
		
		sliderPriority = new JSlider(JSlider.HORIZONTAL,
                MIN, MAX, FPS_INIT);
		sliderPriority.setBounds(198, 98, 200, 77);
		frame.getContentPane().add(sliderPriority);
		
		sliderPriority.setMajorTickSpacing(25);
		sliderPriority.setMinorTickSpacing(5);
		sliderPriority.setPaintTicks(true);
        sliderPriority.setPaintLabels(true);
        sliderPriority.setBorder(
                BorderFactory.createEmptyBorder(0,0,10,0));
        sliderPriority.setFont(font);
		
		sliderProbability = new JSlider(JSlider.HORIZONTAL,
                MIN, MAX, FPS_INIT);
		sliderProbability.setBounds(196, 165, 200, 85);
		frame.getContentPane().add(sliderProbability);
				
		sliderProbability.setMajorTickSpacing(25);
		sliderProbability.setMinorTickSpacing(5);
		sliderProbability.setPaintTicks(true);
        sliderProbability.setPaintLabels(true);
        sliderProbability.setBorder(
                BorderFactory.createEmptyBorder(0,0,10,0));
        sliderProbability.setFont(font);
        
		btnDefaultConfiguration = new JButton("Default configuration");
		btnDefaultConfiguration.setBounds(397, 610, 175, 23);
		frame.getContentPane().add(btnDefaultConfiguration);
		
		JLabel lblEffect = new JLabel("Type of Tests");
		lblEffect.setBounds(59, 527, 127, 14);
		frame.getContentPane().add(lblEffect);
		
		comboBoxEffect = new JComboBox();
		comboBoxEffect.setBounds(215, 524, 175, 20);
		frame.getContentPane().add(comboBoxEffect);
		
		lblConsequence = new JLabel("Consequence");
		lblConsequence.setBounds(59, 260, 95, 14);
		frame.getContentPane().add(lblConsequence);
		
		sliderConsequence = new JSlider(JSlider.HORIZONTAL,
                MIN, MAX, FPS_INIT);
		sliderConsequence.setBounds(200, 235, 200, 96);
		frame.getContentPane().add(sliderConsequence);
		
		sliderConsequence.setMajorTickSpacing(25);
		sliderConsequence.setMinorTickSpacing(5);
		sliderConsequence.setPaintTicks(true);
		sliderConsequence.setPaintLabels(true);
		sliderConsequence.setBorder(
                BorderFactory.createEmptyBorder(0,0,10,0));
		sliderConsequence.setFont(font);
		
		btnOptimizeCases = new JButton("Optimize cases");
		btnOptimizeCases.setBounds(631, 426, 140, 23);
		//frame.getContentPane().add(btnOptimizeCases);
		
		btnOptimize = new JButton("Optimize");
		btnOptimize.setBounds(28, 610, 120, 23);
		frame.getContentPane().add(btnOptimize);
		
		JLabel lblRisk = new JLabel("Risk");
		lblRisk.setBounds(59, 340, 46, 14);
		frame.getContentPane().add(lblRisk);
		
		sliderEPriority = new JSlider(SwingConstants.HORIZONTAL, 0, 100, 15);
		sliderEPriority.setPaintTicks(true);
		sliderEPriority.setPaintLabels(true);
		sliderEPriority.setMinorTickSpacing(5);
		sliderEPriority.setMajorTickSpacing(25);
		sliderEPriority.setFont(new Font("Serif", Font.ITALIC, 15));
		sliderEPriority.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
		sliderEPriority.setBounds(651, 98, 200, 77);
		frame.getContentPane().add(sliderEPriority);
		
		sliderEProbability = new JSlider(SwingConstants.HORIZONTAL, 0, 100, 15);
		sliderEProbability.setPaintTicks(true);
		sliderEProbability.setPaintLabels(true);
		sliderEProbability.setMinorTickSpacing(5);
		sliderEProbability.setMajorTickSpacing(25);
		sliderEProbability.setFont(new Font("Serif", Font.ITALIC, 15));
		sliderEProbability.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
		sliderEProbability.setBounds(651, 169, 200, 77);
		frame.getContentPane().add(sliderEProbability);
		
		sliderEConsequence = new JSlider(SwingConstants.HORIZONTAL, 0, 100, 15);
		sliderEConsequence.setPaintTicks(true);
		sliderEConsequence.setPaintLabels(true);
		sliderEConsequence.setMinorTickSpacing(5);
		sliderEConsequence.setMajorTickSpacing(25);
		sliderEConsequence.setFont(new Font("Serif", Font.ITALIC, 15));
		sliderEConsequence.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
		sliderEConsequence.setBounds(651, 235, 200, 96);
		frame.getContentPane().add(sliderEConsequence);
		
		lblEfficiencyPriority = new JLabel("Efficiency priority");
		lblEfficiencyPriority.setBounds(475, 118, 115, 14);
		frame.getContentPane().add(lblEfficiencyPriority);
		
		lblEfficiencyProbability = new JLabel("Efficiency probability");
		lblEfficiencyProbability.setBounds(475, 183, 127, 14);
		frame.getContentPane().add(lblEfficiencyProbability);
		
		lblEfficiencyConsequence = new JLabel("Efficiency consequence");
		lblEfficiencyConsequence.setBounds(474, 261, 140, 14);
		frame.getContentPane().add(lblEfficiencyConsequence);
		
		sliderRisk = new JSlider(SwingConstants.HORIZONTAL, 0, 100, 15);
		sliderRisk.setPaintTicks(true);
		sliderRisk.setPaintLabels(true);
		sliderRisk.setMinorTickSpacing(5);
		sliderRisk.setMajorTickSpacing(25);
		sliderRisk.setFont(new Font("Serif", Font.ITALIC, 15));
		sliderRisk.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
		sliderRisk.setBounds(199, 317, 200, 96);
		frame.getContentPane().add(sliderRisk);
		
		btnOptimizeArtificial = new JButton("Optimize from artificial data");
		btnOptimizeArtificial.setBounds(633, 610, 218, 23);
		frame.getContentPane().add(btnOptimizeArtificial);
		
		btnCalculateRisk = new JButton("Calculate risk");
		btnCalculateRisk.setBounds(274, 570, 172, 23);
	//	frame.getContentPane().add(btnCalculateRisk);
		
		
	}
	
	public void fillComboBoxes(){
		
		try{
			this.contextQueries.addContext(databaseConnection);
			Iterator<Context> contextIterator = this.contextQueries.getContext().iterator();
			this.comboBoxContext.addItem("");
			while (contextIterator.hasNext()){
				this.comboBoxContext.addItem(contextIterator.next().getName());
				contextIterator.remove();
			}			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			this.rcuTypeQueries.addRCUType(databaseConnection);
			Iterator<RCUType> rcuTypeIterator = this.rcuTypeQueries.getRCUType().iterator();
			this.comboBoxComponent.addItem("");
			while (rcuTypeIterator.hasNext()){
				this.comboBoxComponent.addItem(rcuTypeIterator.next().getName());
				rcuTypeIterator.remove();
			}			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			this.modelConstraintQueries.addModelConstraint(databaseConnection);
			Iterator<ModelConstraint> modelConstraintIterator = this.modelConstraintQueries.getModelConstraint().iterator();
			this.comboBoxModelConstraint.addItem("");
			while (modelConstraintIterator.hasNext()){
				this.comboBoxModelConstraint.addItem(modelConstraintIterator.next().getName());
				modelConstraintIterator.remove();
			}			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			this.effectQueries.addEffect(databaseConnection);
			Iterator<Effect> effectIterator = this.effectQueries.getEffect().iterator();
			this.comboBoxEffect.addItem("");
			while (effectIterator.hasNext()){
				this.comboBoxEffect.addItem(effectIterator.next().getName());
				effectIterator.remove();
			}			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void setUpListeners(){
	
		
		btnOptimize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				double time, priority, probability, consequence, risk, epriority, eprobability, econsequence;
				String context, component, constraint, effect;
				
				context =comboBoxContext.getSelectedItem().toString();
				component =comboBoxComponent.getSelectedItem().toString();
				constraint =comboBoxModelConstraint.getSelectedItem().toString();
				effect =comboBoxEffect.getSelectedItem().toString();
				
				
				if (textTime.getText().equals(""))
						time = 0;
				else
					time = Integer.parseInt(textTime.getText());
			//	context = sliderContext.getValue();
				priority = sliderPriority.getValue();
				probability = sliderProbability.getValue();
				consequence = sliderConsequence.getValue();
				epriority = sliderEPriority.getValue();
				eprobability = sliderEConsequence.getValue();
				econsequence = sliderEConsequence.getValue();
				risk = sliderRisk.getValue();
				
				double total = priority + probability + consequence + risk + epriority + eprobability + econsequence;
				priority = priority/total;
				probability = probability/total;
				consequence = consequence/total;
				risk = risk/total;
				epriority = epriority/total;
				eprobability = eprobability/total;
				econsequence = econsequence/total;

				
				ArrayList<TestCase> tempCaseList = new ArrayList<TestCase>();
				frame.dispose();
				experimentScheduling.setDatabaseConnection(databaseConnection);
				experimentScheduling.setMaxTime(time);
				//experimentScheduling.setContext(context);
				experimentScheduling.setPriority(priority);
				experimentScheduling.setProbability(probability);
				experimentScheduling.setConsequence(consequence);
				experimentScheduling.setContext(context);
				experimentScheduling.setComponent(component);
				experimentScheduling.setConstraint(constraint);
				experimentScheduling.setEffect(effect);
				experimentScheduling.setTotal(total);
				
				experimentScheduling.setRisk(risk);
				experimentScheduling.setEpriority(epriority);
				experimentScheduling.setEprobability(eprobability);
				experimentScheduling.setEconsequence(econsequence);
	
				tempCaseList=experimentScheduling.run();
				
				listTestCasesPage.listCostCases(tempCaseList);
				listTestCasesPage.frame.setVisible(true);
			}
		});
		
		btnOptimizeArtificial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				double time, priority, probability, consequence, risk, epriority, eprobability, econsequence;
				String context, component, constraint, effect;
				
				context =comboBoxContext.getSelectedItem().toString();
				component =comboBoxComponent.getSelectedItem().toString();
				constraint =comboBoxModelConstraint.getSelectedItem().toString();
				effect =comboBoxEffect.getSelectedItem().toString();
				
				
				if (textTime.getText().equals(""))
						time = 0;
				else
					time = Integer.parseInt(textTime.getText());
			//	context = sliderContext.getValue();
				priority = sliderPriority.getValue();
				probability = sliderProbability.getValue();
				consequence = sliderConsequence.getValue();
				epriority = sliderEPriority.getValue();
				eprobability = sliderEConsequence.getValue();
				econsequence = sliderEConsequence.getValue();
				risk = sliderRisk.getValue();
				
				double total = priority + probability + consequence + risk + epriority + eprobability + econsequence;
				priority = priority/total;
				probability = probability/total;
				consequence = consequence/total;
				risk = risk/total;
				epriority = epriority/total;
				eprobability = eprobability/total;
				econsequence = econsequence/total;

				
				ArrayList<TestCase> tempCaseList = new ArrayList<TestCase>();
				frame.dispose();
				experimentSchedulingArtificialData.setMaxTime(time);
				//experimentScheduling.setContext(context);
				experimentSchedulingArtificialData.setPriority(priority);
				experimentSchedulingArtificialData.setProbability(probability);
				experimentSchedulingArtificialData.setConsequence(consequence);
				experimentSchedulingArtificialData.setContext(context);
				experimentSchedulingArtificialData.setComponent(component);
				experimentSchedulingArtificialData.setConstraint(constraint);
				experimentSchedulingArtificialData.setEffect(effect);
				experimentSchedulingArtificialData.setTotal(total);
				
				experimentSchedulingArtificialData.setRisk(risk);
				experimentSchedulingArtificialData.setEpriority(epriority);
				experimentSchedulingArtificialData.setEprobability(eprobability);
				experimentSchedulingArtificialData.setEconsequence(econsequence);
	
				
				tempCaseList=experimentSchedulingArtificialData.run();
				
				listTestCasesPage.listCostCases(tempCaseList);
				listTestCasesPage.frame.setVisible(true);
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				mainPageController.runMainPage();
				
			}
		});
		
		btnDefaultConfiguration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				sliderPriority.setValue(0);
				sliderProbability.setValue(0);
				sliderConsequence.setValue(0);
				sliderRisk.setValue(0);
				sliderEPriority.setValue(0);
				sliderEProbability.setValue(0);
				sliderEConsequence.setValue(0);
				
				textTime.setText("40");
			}
		});
		
		btnOptimizeCases.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				optimizeQueries.calculate(databaseConnection);
				
			}
		});
		
		btnCalculateRisk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				optimizeQueries.calculateRisk(databaseConnection);
				
			}
		});
	}
}
