package uit.ent.synchronizer.table;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import uit.ent.synchronizer.Statics;

public class Examen {

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
	private static int n;
	private static String query2;
	private static ResultSet rs2;
	private static String id_exam;
	private static String DB_DRIVER;
	private static String DB_CONNECTION;
	private static String DB_USER;
	private static String DB_PASSWORD;
	private static String JDBC_DRIVER;
	private static String DB_URL;
	private static String USER;
	private static String PASS;
	private static String filename = "examen";
	private static String heure;
	private static FileWriter writer;
	private static String txtDate;
	private static String datefin;
	private static String datedebi;
	private static String COD_SES;
	private static String COD_ETP;

	public static void Tableexam(String dateanne, String datsychr)
			throws SQLException {

		EntConnexion entcon = new EntConnexion();

		DB_DRIVER = entcon.getDB_DRIVER();
		DB_CONNECTION = entcon.getDB_CONNECTION();
		DB_USER = entcon.getDB_USER();
		DB_PASSWORD = entcon.getDB_PASSWORD();

		JDBC_DRIVER = entcon.getJDBC_DRIVER();
		DB_URL = entcon.getDB_URL();
		USER = entcon.getUSER();
		PASS = entcon.getPASS();

		Date uneDate = new Date();
		Date uneDate1 = new Date();

		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(uneDate);
		gc.add(GregorianCalendar.DATE, +30);
		uneDate = gc.getTime();

		datefin = new SimpleDateFormat("YYYY-MM-DD").format(uneDate);

		GregorianCalendar gc1 = new GregorianCalendar();
		gc1.setTime(uneDate1);
		gc1.add(GregorianCalendar.DATE, -3);
		uneDate1 = gc1.getTime();

		datedebi = new SimpleDateFormat("yyyy-MM-dd").format(uneDate1);

		System.out.println("---------- "+datedebi);
		
		txtDate = new SimpleDateFormat("yyyy-MM-dd hh:m:ss", Locale.FRANCE)
				.format(new Date());

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		Connection conn = null;
		Statement stmt = null;
		Statement stmt1 = null;

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
				+ " AND PRD_EPR_SAL_ANU.DAT_DEB_PES > to_date('" + datedebi + "', 'yyyy-mm-dd')"
				+ " and PRD_EPR_SAL_ANU.DAT_DEB_PES IS NOT NULL";

		System.out.println(selectSQL);
		
		//AND PRD_EPR_SAL_ANU.DAT_DEB_PES < '" + datefin + "'
		
		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(selectSQL);

			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(DB_URL, USER, PASS);
				stmt1 = conn.createStatement();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			ResultSet rs = preparedStatement.executeQuery();

			int i = 0;
			n = 0;
			try {
				writer = new FileWriter(Statics.workingDir.replace("\\", "/")
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

				i++;
				n++;

			}

			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			System.out.println("Insertion examen");
			PreparedStatement Pindividu = conn
					.prepareStatement("LOAD DATA LOCAL INFILE '"
							+ Statics.workingDir.replace("\\", "/")
							+ "/ficher/examen.txt' "
							+ "INTO TABLE examen "
							+ "FIELDS "
							+ "TERMINATED BY ';' "
							+ "ESCAPED BY '\\\\' LINES STARTING BY '' "
							+ "TERMINATED BY '\\n' "
							+ " (cod_ind, cod_etudiant, cod_annee, date_deb, dhh_deb, dur_exa_epr, lib_epr, cod_nel, salle, cod_eprv, cod_ses, cod_etp, datesync) ");
			Pindividu.executeUpdate();
			System.out.println("Fin Insertion examen");

			conn.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
	}

	private static Connection getDBConnection() {

		Connection dbConnection = null;

		try {

			Class.forName(DB_DRIVER);

		} catch (ClassNotFoundException e) {

			System.out.println(e.getMessage());

		}

		try {

			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER,
					DB_PASSWORD);
			return dbConnection;

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}

		return dbConnection;

	}

}
