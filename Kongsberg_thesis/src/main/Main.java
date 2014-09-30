package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import database.DatabaseConnection;
import view.CreateTestCasePage;
import view.MainPage;
import view.ListTestCasesPage;

public class Main {

	public static CreateTestCasePage window;
	public static ListTestCasesPage selection;
	public static MainPage mainPage;
	
	
	public static void main(String[] args) {
		runMainPage();
	}
	
	public static void runMainPage(){
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainPage = new MainPage();
					mainPage.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void run(){
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new CreateTestCasePage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void run2(){
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					selection = new ListTestCasesPage();
					selection.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
