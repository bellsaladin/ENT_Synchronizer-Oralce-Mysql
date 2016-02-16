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
import java.util.Locale;

import uit.ent.synchronizer.Statics;

public class Bac {

	private static String DB_DRIVER;
	private static String DB_CONNECTION;
	private static String DB_USER;
	private static String DB_PASSWORD;
	private static String JDBC_DRIVER;
	private static String DB_URL;
	private static String USER;
	private static String PASS;
	private static String query2;
	private static ResultSet rs2;
	private static int COD_IND;
	private static String DAA_OBT_BAC_IBA;
	private static String COD_BAC;
	private static String LIB_BAC;
	private static String LIB_MNB;
	private static String LIB_ETB;
	private static String LIB_DEP;
	private static int n;
	private static Connection conn;
	private static String filename = "Bac";
	private static FileWriter writer;
	private static String txtDate;

	public static void TableBac(String dateanne, String datsychr)
			throws SQLException {

		EntConnexion entcon = new EntConnexion();
		Statics cc = new Statics();

		DB_DRIVER = entcon.getDB_DRIVER();
		DB_CONNECTION = entcon.getDB_CONNECTION();
		DB_USER = entcon.getDB_USER();
		DB_PASSWORD = entcon.getDB_PASSWORD();

		JDBC_DRIVER = entcon.getJDBC_DRIVER();
		DB_URL = entcon.getDB_URL();
		USER = entcon.getUSER();
		PASS = entcon.getPASS();

		txtDate = new SimpleDateFormat("yyyy-MM-dd hh:m:ss", Locale.FRANCE)
				.format(new Date());

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		Connection conn = null;
		Statement stmt = null;
		Statement stmt1 = null;

		String selectSQL = "SELECT DISTINCT IND_BAC.COD_BAC,"
				+ "INDIVIDU.COD_IND," + "BAC_OUX_EQU.LIB_BAC,"
				+ "MENTION_NIV_BAC.LIB_MNB," + "ETABLISSEMENT.LIB_ETB,"
				+ "DEPARTEMENT.LIB_DEP," + "IND_BAC.DAA_OBT_BAC_IBA "
				+ "FROM INDIVIDU " + "FULL JOIN IND_BAC "
				+ "ON INDIVIDU.COD_IND = IND_BAC.COD_IND "
				+ "FULL JOIN DEPARTEMENT "
				+ "ON DEPARTEMENT.COD_DEP = IND_BAC.COD_DEP "
				+ "FULL JOIN BAC_OUX_EQU "
				+ "ON BAC_OUX_EQU.COD_BAC = IND_BAC.COD_BAC "
				+ "FULL JOIN ETABLISSEMENT "
				+ "ON ETABLISSEMENT.COD_ETB = IND_BAC.COD_ETB "
				+ "FULL JOIN MENTION_NIV_BAC "
				+ "ON MENTION_NIV_BAC.COD_MNB = IND_BAC.COD_MNB "
				+ "FULL JOIN INS_ADM_ETP "
				+ "ON INS_ADM_ETP.COD_IND = INDIVIDU.COD_IND "
				+ "WHERE INS_ADM_ETP.COD_ANU = " + dateanne
				+ " AND INS_ADM_ETP.ETA_IAE = 'E'";
		// AND INDIVIDU.COD_IND = '38032'
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
						+ "/ficher/Bac.txt", false);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			while (rs.next()) {

				COD_BAC = rs.getString("COD_BAC");
				COD_IND = rs.getInt("COD_IND");
				LIB_BAC = rs.getString("LIB_BAC");
				LIB_MNB = rs.getString("LIB_MNB");
				LIB_ETB = rs.getString("LIB_ETB");
				LIB_DEP = rs.getString("LIB_DEP");
				DAA_OBT_BAC_IBA = rs.getString("DAA_OBT_BAC_IBA");

				String texte = COD_IND + ";" + LIB_BAC + ";" + LIB_MNB + ";"
						+ LIB_ETB + ";" + LIB_DEP + ";" + DAA_OBT_BAC_IBA + ";"
						+ datsychr + " \n";

				texte = texte.replace("null", "---");
				String stre2 = new String(texte.getBytes(Charset
						.forName("ISO-8859-9")));
				// System.out.println(stre2);

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

			System.out.println("Insertion Bac");
			PreparedStatement Pindividu = conn
					.prepareStatement("LOAD DATA LOCAL INFILE '"
							+ Statics.workingDir.replace("\\", "/")
							+ "/ficher/Bac.txt' "
							+ "INTO TABLE bac "
							+ "FIELDS "
							+ "TERMINATED BY ';' "
							+ "ESCAPED BY '\\\\' LINES STARTING BY '' "
							+ "TERMINATED BY '\\n' "
							+ "(cod_ind, serie, mention, etablissmenet, province, date, datesync) ");
			Pindividu.executeUpdate();
			System.out.println("Fin Insertion Bac");

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
