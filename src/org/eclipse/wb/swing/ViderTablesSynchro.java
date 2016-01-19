package org.eclipse.wb.swing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ViderTablesSynchro {

	public void vidiertablesSycnh(String Date) {
		try {

			entconnexion entcon = new entconnexion();
			entcon.entconnexion();

			System.out
					.println("début opération pour vider toutes les tables aprés synchronisation");
			String myDriver = "org.gjt.mm.mysql.Driver";
			String myUrl = entcon.getDB_URL();
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl,
					entcon.getUSER(), entcon.getPASS());

			String query = "DELETE FROM annee_uni WHERE datesync <'" + Date
					+ "'";
			String query1 = "DELETE FROM bac WHERE datesync <'" + Date + "'";
			String query2 = "DELETE FROM cursusresultat WHERE datesync <'"
					+ Date + "'";
			String query3 = "DELETE FROM diplome WHERE datesync <'" + Date
					+ "'";
			String query4 = "DELETE FROM etape WHERE datesync <'" + Date + "'";
			String query5 = "DELETE FROM examen WHERE datesync <'" + Date + "'";
			String query6 = "DELETE FROM individu WHERE datesync <'" + Date
					+ "'";
			String query7 = "DELETE FROM fos_user WHERE datesync <'" + Date
					+ "'";
			String query8 = "DELETE FROM cursusannee WHERE datesync <'" + Date
					+ "'";

			PreparedStatement annee_uni = conn.prepareStatement(query);
			annee_uni.execute();

			PreparedStatement individu = conn.prepareStatement(query6);
			individu.execute();

			PreparedStatement etape = conn.prepareStatement(query4);
			etape.execute();

			PreparedStatement bac = conn.prepareStatement(query1);
			bac.execute();

			PreparedStatement diplome = conn.prepareStatement(query3);
			diplome.execute();

			PreparedStatement fos_user = conn.prepareStatement(query7);
			fos_user.execute();

			PreparedStatement examen = conn.prepareStatement(query5);
			examen.execute();

			PreparedStatement cursusresultat = conn.prepareStatement(query2);
			cursusresultat.execute();

			PreparedStatement cursusannee = conn.prepareStatement(query8);
			cursusannee.execute();

			conn.close();

			System.out
					.println("Fin d'opération vider tables aprés synchronisation ");
		} catch (Exception e) {
			System.err.println("Got an exception!");
			System.err.println(e.getMessage());
		}
	}
}
