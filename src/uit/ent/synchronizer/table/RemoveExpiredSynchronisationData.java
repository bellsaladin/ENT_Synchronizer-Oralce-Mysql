package uit.ent.synchronizer.table;

import java.sql.PreparedStatement;

import uit.ent.synchronizer.table.generic.Synchronizable;

public class RemoveExpiredSynchronisationData extends Synchronizable {

	public void run(String Date) {
		try {

			System.out.println("Suppression des données des anciennes synchronisations...");

			String query0 = "DELETE FROM annee_uni WHERE datesync <'" + Date + "'";
			String query1 = "DELETE FROM bac WHERE datesync <'" + Date + "'";
			String query2 = "DELETE FROM cursusresultat WHERE datesync <'" + Date + "'";
			String query3 = "DELETE FROM diplome WHERE datesync <'" + Date + "'";
			String query4 = "DELETE FROM etape WHERE datesync <'" + Date + "'";
			String query5 = "DELETE FROM examen WHERE datesync <'" + Date + "'";
			String query6 = "DELETE FROM individu WHERE datesync <'" + Date + "'";
			String query7 = "DELETE FROM fos_user WHERE datesync <'" + Date + "'";
			String query8 = "DELETE FROM cursusannee WHERE datesync <'" + Date + "'";

			PreparedStatement annee_uni = getConnection("mysql").prepareStatement(query0);
			annee_uni.execute();

			PreparedStatement individu = getConnection("mysql").prepareStatement(query6);
			individu.execute();

			PreparedStatement etape = getConnection("mysql").prepareStatement(query4);
			etape.execute();

			PreparedStatement bac = getConnection("mysql").prepareStatement(query1);
			bac.execute();

			PreparedStatement diplome = getConnection("mysql").prepareStatement(query3);
			diplome.execute();

			PreparedStatement fos_user = getConnection("mysql").prepareStatement(query7);
			fos_user.execute();

			PreparedStatement examen = getConnection("mysql").prepareStatement(query5);
			examen.execute();

			PreparedStatement cursusresultat = getConnection("mysql").prepareStatement(query2);
			cursusresultat.execute();

			PreparedStatement cursusannee = getConnection("mysql").prepareStatement(query8);
			cursusannee.execute();

			closeConnection("mysql");
			
			System.out.println("Données d'anciennes synchronisations supprimées avec succès !");
			
		} catch (Exception e) {
			getLogger().error("Erreur lors de la suppression des données de synchronisation",e);
		}
	}
}
