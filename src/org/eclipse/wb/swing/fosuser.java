package org.eclipse.wb.swing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.acl.LastOwnerException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class fosuser {

	private static ResultSet rs1;
	private static int COD_IND;
	private static String cod_ind_mysql;
	private static String query;
	private static String query2;
	private static ResultSet rs2;
	private static String CIN_IND;
	private static String LIB_NOM_PAT_IND;
	private static String LIB_PR1_IND;
	private static String COD_NNE_IND;
	private static String DATE_NAI_IND;
	private static String LIB_VIL_NAI_ETU;
	private static String LIB_PAY;
	private static String COD_SEX_ETU;
	private static String LIB_FAM;
	private static String LIB_THP;
	private static String preinscriptionensigsuper;
	private static String preinscriptidansetablimarocain;
	private static String LIB_ETB;
	private static String preinscridansetablis;
	private static String adress2fix;
	private static String adressfix;
	private static String payefix;
	private static String villefix;
	private static String telefix;
	private static String adressfix2;
	private static String adress2fix2;
	private static String payefix2;
	private static String villefix2;
	private static String telefix2;
	private static String LIB_NOM_IND_ARB;
	private static String LIB_PRN_IND_ARB;
	private static String LIB_NOM_IND_ARB1;
	private static String LIB_PRN_IND_ARB1;
	private static String LIB_VIL_NAI_ETU_ARB;
	private static int COD_ANU_INA;
	private static String nationaissan;
	private static String provadressfix;
	private static String comunadressfix;
	private static String provadressfix2;
	private static String comunadressfix2;
	private static String departementnaissance;
	private static String paysnaissance;
	private static int n;
	private static String cod_apogee;
	private static String lib_etablissement;
	private static String lic_etablissement;
	private static String cod_postal_fix;
	private static String cod_postal_anull;
	private static String lib_ttr;
	private static String lib_cmp_arb;
	private static String lic_cmp_arb;
	private static String lib_ad1_cmp;
	private static String lib_ad2_cmp;
	private static String lib_ad3_cmp;
	private static String DB_DRIVER;
	private static String DB_CONNECTION;
	private static String DB_USER;
	private static String DB_PASSWORD;
	private static String JDBC_DRIVER;
	private static String DB_URL;
	private static String USER;
	private static String PASS;
	private static String filename = "individu";
	private static FileWriter writer;
	private static String txtDate;
	private static String passfosuser;

	String polecenie;

	public static String getMD5(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public static void Tablefosuser(String dateanne, String datsychr)
			throws SQLException {

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

		txtDate = new SimpleDateFormat("yyyy-MM-dd hh:m:ss", Locale.FRANCE)
				.format(new Date());
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		Connection conn = null;
		Statement stmt = null;
		Statement stmt1 = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			try {
				conn = DriverManager.getConnection(DB_URL, USER, PASS);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				stmt1 = conn.createStatement();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		String selectSQL = "SELECT DISTINCT INDIVIDU.COD_NNE_IND, "
				+ "INDIVIDU.COD_ETU " + "FROM INDIVIDU "
				+ "FULL JOIN INS_ADM_ETP "
				+ "ON INDIVIDU.COD_IND = INS_ADM_ETP.COD_IND "
				+ "WHERE INS_ADM_ETP.COD_ANU = " + dateanne
				+ "AND INS_ADM_ETP.ETA_IAE = 'E'";

		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();
			int i = 0;
			n = 1;

			try {
				writer = new FileWriter(_Statics.workingDir.replace("\\", "/")
						+ "/ficher/fos_user.txt", false);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			while (rs.next()) {
				COD_NNE_IND = rs.getString("COD_NNE_IND");
				cod_apogee = rs.getString("COD_ETU");

				String passfos = getMD5(COD_NNE_IND);
				//System.out.println(passfos);

				String texte = cod_apogee + ";" + cod_apogee + ";" + cod_apogee
						+ ";" + cod_apogee + ";" + cod_apogee
						+ ";4sw9a67pdim8s8g04ws808sswosk40s;" + passfos
						+ ";a:0:{};" + datsychr + " \n";
				String stre2 = new String(texte.getBytes(Charset
						.forName("ISO-8859-9")));
				try {
					writer.write(stre2, 0, stre2.length());
				} catch (IOException ex) {
					ex.printStackTrace();
				}

				i++;
			}

			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			System.out.println("Insertion Fosuser");

			PreparedStatement Pindividu = conn
					.prepareStatement("LOAD DATA LOCAL INFILE '"
							+ _Statics.workingDir.replace("\\", "/")
							+ "/ficher/fos_user.txt' "
							+ "INTO TABLE fos_user "
							+ "FIELDS "
							+ "TERMINATED BY ';' "
							+ "ESCAPED BY '\\\\' LINES STARTING BY '' "
							+ "TERMINATED BY '\\n' "
							+ "(id, username, username_canonical, email, email_canonical, salt, password, roles, datesync)");
			Pindividu.executeUpdate();

			System.out.println("Fin Insertion Fosuser");

			conn.close();

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (dbConnection != null) {
				try {
					dbConnection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
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
