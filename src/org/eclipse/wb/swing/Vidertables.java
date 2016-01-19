package org.eclipse.wb.swing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Calendar;

public class Vidertables {

	public void vidiertables()
	  {
	    try
	    {
	    	
	    entconnexion entcon = new entconnexion();
	    entcon.entconnexion();
	    
	      System.out.println("début opération pour vider  toutes les tables ");
	      // create a mysql database connection
	      String myDriver = "org.gjt.mm.mysql.Driver";
	      String myUrl = entcon.getDB_URL();
	      Class.forName(myDriver);
	      Connection conn = DriverManager.getConnection(myUrl, entcon.getUSER(), entcon.getPASS());
 
	      String query = "TRUNCATE TABLE annee_uni";
	      String query1 = "TRUNCATE TABLE bac";
	      String query2 = "TRUNCATE TABLE cursusresultat";
	      String query3 = "TRUNCATE TABLE diplome";
	      String query4 = "TRUNCATE TABLE etape";
	      String query5 = "TRUNCATE TABLE examen";
	      String query6 = "TRUNCATE TABLE individu";
	      
	      
	      PreparedStatement annee_uni = conn.prepareStatement(query);
	      annee_uni.execute();
	      
	      PreparedStatement bac = conn.prepareStatement(query1);
	      bac.execute();
	      
	      PreparedStatement cursusresultat = conn.prepareStatement(query2);
	      cursusresultat.execute();
	      
	      PreparedStatement diplome = conn.prepareStatement(query3);
	      diplome.execute();
	      
	      PreparedStatement etape = conn.prepareStatement(query4);
	      etape.execute();
	      
	      PreparedStatement examen = conn.prepareStatement(query5);
	      examen.execute();
	      
	      PreparedStatement individu = conn.prepareStatement(query6);
	      individu.execute();
	    	       
	      conn.close();
	      
	      System.out.println("Fin d'opération vider tables ");
	    }
	    catch (Exception e)
	    {
	      System.err.println("Got an exception!");
	      System.err.println(e.getMessage());
	    }
	  }
	}