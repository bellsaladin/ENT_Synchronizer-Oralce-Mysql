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

public class Etape {

	private static String query2;
	private static ResultSet rs2;
	private static int COD_IND;
	private static String LIB_ETP;
	private static int COD_ANU;
	private static String COD_ETP;
	private static String LIB_DIP;
	private static String LIC_DIP;

	private static String DB_DRIVER;
	private static String DB_CONNECTION;
	private static String DB_USER;
	private static String DB_PASSWORD;
	private static String JDBC_DRIVER;
	private static String DB_URL;
	private static String USER;
	private static String PASS;

	private static int n;
	private static Connection conn;

	private static String filename = "etape";
	private static FileWriter writer;
	private static String txtDate;

	public static void TableEtape(String dateanne, String datsychr)
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

		txtDate = new SimpleDateFormat("yyyy-MM-dd hh:m:ss", Locale.FRANCE)
				.format(new Date());

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		Connection conn = null;
		Statement stmt = null;
		Statement stmt1 = null;

		String selectSQL = "SELECT DISTINCT INDIVIDU.COD_IND,"
				+ "ETAPE.LIB_ETP," + "INS_ADM_ETP.COD_ANU,"
				+ "INS_ADM_ETP.COD_ETP," + "DIPLOME.LIB_DIP,"
				+ "DIPLOME.LIC_DIP " + "FROM ETAPE " + "FULL JOIN INS_ADM_ETP "
				+ "ON INS_ADM_ETP.COD_ETP = ETAPE.COD_ETP "
				+ "FULL JOIN INDIVIDU "
				+ "ON INS_ADM_ETP.COD_IND = INDIVIDU.COD_IND "
				+ "FULL JOIN DIPLOME "
				+ "ON DIPLOME.COD_DIP = INS_ADM_ETP.COD_DIP "
				+ "WHERE INS_ADM_ETP.COD_ANU = " + dateanne
				+ " AND INS_ADM_ETP.ETA_IAE = 'E'";
		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(selectSQL);

			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(DB_URL, USER, PASS);
				stmt1 = conn.createStatement();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			ResultSet rs = preparedStatement.executeQuery();

			int i = 0;
			n = 0;
			try {
				writer = new FileWriter(Statics.workingDir.replace("\\", "/")
						+ "/ficher/etape.txt", false);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			while (rs.next()) {

				COD_IND = rs.getInt("COD_IND");
				LIB_ETP = rs.getString("LIB_ETP");
				COD_ANU = rs.getInt("COD_ANU");
				COD_ETP = rs.getString("COD_ETP");
				LIB_DIP = rs.getString("LIB_DIP");
				LIC_DIP = rs.getString("LIC_DIP");

				String texte = COD_ETP + ";" + COD_IND + ";" + LIB_ETP + ";"
						+ COD_ANU + ";" + LIB_DIP + ";" + LIC_DIP + ";"
						+ datsychr + " \n";

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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("Insertion etape");
			PreparedStatement Pindividu = conn
					.prepareStatement("LOAD DATA LOCAL INFILE '"
							+ Statics.workingDir.replace("\\", "/")
							+ "/ficher/etape.txt' "
							+ "INTO TABLE etape "
							+ "FIELDS "
							+ "TERMINATED BY ';' "
							+ "ESCAPED BY '\\\\' LINES STARTING BY '' "
							+ "TERMINATED BY '\\n' "
							+ " (cod_etp, cod_ind, lib_etp, cod_annee, lib_dip, lic_dip, datesync) ");
			Pindividu.executeUpdate();
			System.out.println("Fin Insertion etape");

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
