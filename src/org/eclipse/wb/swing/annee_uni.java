package org.eclipse.wb.swing;

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

public class annee_uni {

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
	private static int COD_ANU;
	private static String ETA_ANU_IAE;
	private static String LIB_ANU;
	private static String LIC_ANU;
	private static String DAT_OUV_OPI;
	private static String DAT_FRM_OPI;
	private static String ETA_ANU_IPE;
	private static String ETA_ANU_RES;
	private static int n;
	private static Connection conn;
	private static String filename = "annee_uni";
	private static FileWriter writer;
	private static String txtDate;

	public static void TableAnueeuni(String datsychr) throws SQLException {

		entconnexion entcon = new entconnexion();
		_Statics cc = new _Statics();
		entcon.entconnexion();

		DB_DRIVER = entcon.getDB_DRIVER();
		DB_CONNECTION = entcon.getDB_CONNECTION();
		DB_USER = entcon.getDB_USER();
		DB_PASSWORD = entcon.getDB_PASSWORD();

		JDBC_DRIVER = entcon.getJDBC_DRIVER();
		DB_URL = entcon.getDB_URL();
		USER = entcon.getUSER();
		PASS = entcon.getPASS();

		txtDate = new SimpleDateFormat("MM/dd/yyyy hh:m:ss", Locale.FRANCE)
				.format(new Date());

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		Connection conn = null;
		Statement stmt = null;
		Statement stmt1 = null;

		String selectSQL = "SELECT * FROM ANNEE_UNI";
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
				writer = new FileWriter(_Statics.workingDir.replace("\\", "/")
						+ "/ficher/annee_uni.txt", false);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			while (rs.next()) {

				COD_ANU = rs.getInt("COD_ANU");
				ETA_ANU_IAE = rs.getString("ETA_ANU_IAE");
				LIB_ANU = rs.getString("LIB_ANU");
				LIC_ANU = rs.getString("LIC_ANU");
				DAT_OUV_OPI = rs.getString("DAT_OUV_OPI");
				DAT_FRM_OPI = rs.getString("DAT_FRM_OPI");
				ETA_ANU_IPE = rs.getString("ETA_ANU_IPE");
				ETA_ANU_RES = rs.getString("ETA_ANU_RES");

				String query3 = "SELECT * FROM annee_uni Where cod_anu = ?";
				PreparedStatement prest = conn.prepareStatement(query3);
				prest.setInt(1, COD_ANU);
				ResultSet rs2 = null;
				rs2 = prest.executeQuery();

				String texte = COD_ANU + ";" + ETA_ANU_IAE + ";" + LIB_ANU
						+ ";" + LIC_ANU + ";" + DAT_OUV_OPI + ";" + DAT_FRM_OPI
						+ ";" + ETA_ANU_IPE + ";" + ETA_ANU_RES + ";"
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
				e.printStackTrace();
			}

			PreparedStatement Pindividu = conn
					.prepareStatement("LOAD DATA LOCAL INFILE '"
							+ _Statics.workingDir.replace("\\", "/")
							+ "/ficher/annee_uni.txt' "
							+ "INTO TABLE annee_uni "
							+ "FIELDS "
							+ "TERMINATED BY ';' "
							+ "ESCAPED BY '\\\\' LINES STARTING BY '' "
							+ "TERMINATED BY '\\n' "
							+ "(cod_anu, eta_anu_iae, lib_anu, lic_anu, dat_ouv_opi, dat_frm_opi, eta_anu_ipe, eta_anu_res, datesync) ");
			Pindividu.executeUpdate();

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
