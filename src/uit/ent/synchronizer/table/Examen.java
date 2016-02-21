package uit.ent.synchronizer.table;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import uit.ent.synchronizer.Config;
import uit.ent.synchronizer.table.generic.Synchronizable;

public class Examen extends Synchronizable{

	private static String COD_IND;
	private static String COD_ETU;
	private static String COD_ANU;
	private static String DAT_DEB_PES;
	private static String DHH_DEB_PES;
	private static String DMM_DEB_PES;
	private static String DUR_EXA_EPR_PES;
	private static String LIB_EPR;
	private static String COD_NEL;
	private static String LIB_SAL;
	private static String COD_EPR;
	private static String heure;
	private static String dateDebutStr;
	private static String dateFinStr;
	private static String COD_SES;
	private static String COD_ETP;
	private static FileWriter writer;


	public void synchronize(String dateanne, String datsychr)
			throws SQLException {


		Date dateFin = new Date();
		Date dateDebut = new Date();

		GregorianCalendar calendarDateDebut = new GregorianCalendar();
		calendarDateDebut.setTime(dateFin);
		calendarDateDebut.add(GregorianCalendar.DATE, +30);
		dateFin = calendarDateDebut.getTime();
		
		GregorianCalendar calendarDateFin = new GregorianCalendar();
		calendarDateFin.setTime(dateDebut);
		calendarDateFin.add(GregorianCalendar.DATE, -3);
		dateDebut = calendarDateFin.getTime();

		dateDebutStr = new SimpleDateFormat("yyyy-MM-dd").format(dateDebut);
		dateFinStr   = new SimpleDateFormat("yyyy-MM-dd").format(dateFin);

		System.out.println("Examen - Filtre [ Date Debut / Date Fin ] :  [ " + dateDebutStr + " / " + dateFinStr);

		PreparedStatement preparedStatement = null;

		String selectSQL = "SELECT DISTINCT " + "PRD_EPR_SAL_ANU.COD_ANU,"
				+ "PRD_EPR_SAL_ANU.DAT_DEB_PES,"
				+ "PRD_EPR_SAL_ANU.DHH_DEB_PES,"
				+ "PRD_EPR_SAL_ANU.DMM_DEB_PES,"
				+ "PRD_EPR_SAL_ANU.DUR_EXA_EPR_PES," + "EPREUVE.LIB_EPR,"
				+ "PES_IND.COD_PES," + "PES_IND.COD_IND," + "PES_IND.COD_ETU,"
				+ "PES_IND.LIB_NOM_PAT_IND," + "ELEMENT_PEDAGOGI.COD_NEL,"
				+ "PRD_EPR_SAL_ANU.COD_SAL," + "PRD_EPR_SAL_ANU.COD_EPR,"
				+ "PES_IND.LIB_PR1_IND , APOGEE.EPR_SANCTIONNE_ELP.COD_SES, APOGEE.IND_CONTRAT_ELP.COD_ETP " + "FROM " + "PRD_EPR_SAL_ANU "
				+ "INNER JOIN PES_IND " + "ON "
				+ "PRD_EPR_SAL_ANU.COD_PES = PES_IND.COD_PES "
				+ "INNER JOIN APOGEE.EPREUVE " + "ON "
				+ "PRD_EPR_SAL_ANU.COD_EPR = EPREUVE.COD_EPR "
				+ "INNER JOIN APOGEE.EPR_SANCTIONNE_ELP " + "ON "
				+ "EPREUVE.COD_EPR = EPR_SANCTIONNE_ELP.COD_EPR "
				+ "INNER JOIN ELEMENT_PEDAGOGI " + "ON "
				+ "ELEMENT_PEDAGOGI.COD_ELP = EPR_SANCTIONNE_ELP.COD_ELP "
				+ "INNER JOIN APOGEE.SALLE " + "ON "
				+ "SALLE.COD_SAL = PRD_EPR_SAL_ANU.COD_SAL "
				+ "INNER JOIN APOGEE.IND_CONTRAT_ELP "
				+ "ON APOGEE.IND_CONTRAT_ELP.COD_ELP = ELEMENT_PEDAGOGI.COD_ELP "
				+ "AND APOGEE.IND_CONTRAT_ELP.COD_IND = PES_IND.COD_IND " + "WHERE "
				+ "PRD_EPR_SAL_ANU.COD_ANU = " + dateanne
				+ " AND APOGEE.EPR_SANCTIONNE_ELP.COD_SES = 2 "
				+ " AND PRD_EPR_SAL_ANU.DAT_DEB_PES > to_date('" + dateDebutStr + "', 'yyyy-mm-dd')"
				+ " and PRD_EPR_SAL_ANU.DAT_DEB_PES IS NOT NULL";

		//System.out.println(selectSQL);
				
		try {
			preparedStatement = getConnection("oracle").prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();
			try {
				writer = new FileWriter(Config.workingDir.replace("\\", "/")
						+ "/ficher/examen.txt", false);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			while (rs.next()) {
				COD_IND = rs.getString("COD_IND");
				COD_ETU = rs.getString("COD_ETU");
				COD_ANU = rs.getString("COD_ANU");
				DAT_DEB_PES = rs.getString("DAT_DEB_PES");
				DHH_DEB_PES = rs.getString("DHH_DEB_PES");
				DMM_DEB_PES = rs.getString("DMM_DEB_PES");
				DUR_EXA_EPR_PES = rs.getString("DUR_EXA_EPR_PES");
				LIB_EPR = (rs.getString("LIB_EPR") != null)?rs.getString("LIB_EPR").replaceAll("(\\r|\\n|\\r\\n)+", " "):"";
				COD_NEL = rs.getString("COD_NEL");
				LIB_SAL = rs.getString("COD_SAL");
				COD_EPR = rs.getString("COD_EPR");
				COD_SES = rs.getString("COD_SES");
				heure = String.format("%02d", Integer.valueOf(DHH_DEB_PES))
						+ ":"
						+ String.format("%02d", Integer.valueOf(DMM_DEB_PES));

				COD_ETP =  rs.getString("COD_ETP");
				
				String texte = COD_IND + ";" + COD_ETU + ";" + COD_ANU + ";"
						+ DAT_DEB_PES + ";" + heure + ";" + DUR_EXA_EPR_PES
						+ ";" + LIB_EPR + ";" + COD_NEL + ";" + LIB_SAL + ";"
						+ COD_EPR + ";" + COD_SES + ";" + COD_ETP + ";" + datsychr + " \n";

				texte = texte.replace("null", "---");
				String stre2 = new String(texte.getBytes(Charset
						.forName("ISO-8859-9")));

				try {
					writer.write(stre2, 0, stre2.length());
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}

			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			System.out.println("Insertion examen");
			PreparedStatement loadInfileStatement = getConnection("mysql")
					.prepareStatement("LOAD DATA LOCAL INFILE '"
							+ Config.workingDir.replace("\\", "/")
							+ "/ficher/examen.txt' "
							+ "INTO TABLE examen "
							+ "FIELDS "
							+ "TERMINATED BY ';' "
							+ "ESCAPED BY '\\\\' LINES STARTING BY '' "
							+ "TERMINATED BY '\\n' "
							+ " (cod_ind, cod_etudiant, cod_annee, date_deb, dhh_deb, dur_exa_epr, lib_epr, cod_nel, salle, cod_eprv, cod_ses, cod_etp, datesync) ");
			loadInfileStatement.executeUpdate();
			System.out.println("Fin Insertion examen");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			closeConnection("oracle");
			closeConnection("mysql");
		}
	}

}
