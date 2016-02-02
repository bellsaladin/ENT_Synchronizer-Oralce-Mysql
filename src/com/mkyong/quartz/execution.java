package com.mkyong.quartz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import org.eclipse.wb.swing.ViderTablesSynchro;
import org.eclipse.wb.swing.Vidertables;
import org.eclipse.wb.swing.entconnexion;
import org.eclipse.wb.swing.individu;
import org.eclipse.wb.swing.synchronisation;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class execution implements Job
{
	public static String dateanne;
	private static String txtDate;
	private static String JDBC_DRIVER;
	private static String DB_URL;
	private static String USER;
	private static String PASS;
	private String DateSynch;
	
	public void dateannee (String date){
		
		dateanne = date;
		
	}
	
	@SuppressWarnings("null")
	public void execute(JobExecutionContext context)
	throws JobExecutionException {
		
		entconnexion entcon = new entconnexion();
		entcon.entconnexion();
		
			
		//------------ vider les tables Mysql ----------
		JDBC_DRIVER = entcon.getJDBC_DRIVER();
		DB_URL = entcon.getDB_URL();
		USER = entcon.getUSER();
		PASS = entcon.getPASS();
		
		Connection dbConnection = null;
		PreparedStatement ps = null;
		Connection conn = null;
		Statement stmt1 = null;
		
		
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			try {
				conn = DriverManager.getConnection(DB_URL, USER, PASS);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				stmt1 = conn.createStatement();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println("L'op�ration en cours de traitement !!!!");
		synchronisation synch = new synchronisation();
		try {
			synch.OperationsynchronisationMysql(dateanne);
			//JOptionPane.showMessageDialog(null, "Synchronsation est effectu�e avec succ�s");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println("Op�ration Update DateSynch");
		
		try {
			
			ps = conn.prepareStatement("UPDATE datesynch SET etat = 1");
					ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		//JOptionPane.showMessageDialog(null, "Update table DateSycnhronsation est effectu�e avec succ�s ");
		
		// vider table si l date et > a la date de l'atat 0
		
		System.out.println("Fin Op�ration Update DateSynch");
		PreparedStatement selle = null;
		
		try {	
			selle = conn .prepareStatement("SELECT date FROM datesynch where id = (Select max(id) From datesynch)");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		ResultSet rs = null;
		try {
			rs = selle.executeQuery();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}	
		try {
			while (rs.next()) {
				try {
					DateSynch = rs.getString("date");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("Op�ration vidier les tables");
		
		
		ViderTablesSynchro vidisycnh = new ViderTablesSynchro();
		//vidisycnh.vidiertablesSycnh(DateSynch);
		
		
		//JOptionPane.showMessageDialog(null, "Delete les lignes inf�rieure a la date est effectu�e avec succ�s ");
		//----------------------------------------------------
		System.out.println("Fin Op�ration vidier les tables");
		JOptionPane.showMessageDialog(null, "La t�che est effectu�e avec succ�s !!!!!!!!!!!!!!!!!");

	}
	
}
