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

public class lse_regroupe_elp {

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
	private static int n;
	private static Connection conn;
	private static String filename = "Bac";
	private static FileWriter writer;
	private static String txtDate;
	private static String COD_LSE;
	private static String COD_ELP;
	private static String COE_ADM_1;
	private static String COE_ADM_2;
	private static String COE_ADM_A;
	private static String COE_ELP_1;
	private static String COE_ELP_2;
	private static String COE_ELP_A;
	private static String DUR_CSV_ELP;
	private static String NOT_MA1_NUM;
	private static String NOT_MA1_DEN;
	private static String NOT_MA2_NUM;
	private static String NOT_MA2_DEN;
	private static String NOT_MAA_NUM;
	private static String NOT_MAA_DEN;
	private static String NOT_MI1_NUM;
	private static String NOT_MI1_DEN;
	private static String NOT_MI2_NUM;
	private static String NOT_MI2_DEN;
	private static String NOT_MIA_NUM;
	private static String NOT_MIA_DEN;
	private static String TEM_CAL_CNS;
	private static String TEM_ELP_CSV;
	private static String TEM_NOT_ELP;
	private static String TEM_RES_ELP;
	private static String TEM_ELP_ASD;
	private static String NOT_DIS_FAC_OBL_CHX_NUM;
	private static String NOT_DIS_FAC_OBL_CHX_DEN;
	private static String NOT_MIN_PNT_NUM_FAC;
	private static String NOT_MIN_PNT_DEN_FAC;
	private static String NBR_PNT_MAX_FAC;
	private static String TEM_GES_PNT_FAC;
	private static String TEM_ELP_CPB_OBL_CHX;

	public static void TablelseregroupeelpBac(String dateanne, String datsychr)
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

		String selectSQL = "SELECT * FROM LSE_REGROUPE_ELP";

		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(selectSQL);
			// preparedStatement.setInt(1, 1001);

			// Connexion Mysql

			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(DB_URL, USER, PASS);
				stmt1 = conn.createStatement();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// execute select SQL stetement
			ResultSet rs = preparedStatement.executeQuery();

			int i = 0;
			n = 0;

			try {
				// writer = new FileWriter(cc.workingDir+"\\ficher\\"+ filename
				// +".txt", false);
				writer = new FileWriter(_Statics.workingDir.replace("\\", "/")
						+ "/ficher/lse_regroupe_elp.txt", false);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			while (rs.next()) {

				COD_LSE = rs.getString("COD_LSE");
				COD_ELP = rs.getString("COD_ELP");
				COE_ADM_1 = rs.getString("COE_ADM_1");
				COE_ADM_2 = rs.getString("COE_ADM_2");
				COE_ADM_A = rs.getString("COE_ADM_A");
				COE_ELP_1 = rs.getString("COE_ELP_1");
				COE_ELP_2 = rs.getString("COE_ELP_2");
				COE_ELP_A = rs.getString("COE_ELP_A");
				DUR_CSV_ELP = rs.getString("DUR_CSV_ELP");
				NOT_MA1_NUM = rs.getString("NOT_MA1_NUM");
				NOT_MA1_DEN = rs.getString("NOT_MA1_DEN");
				NOT_MA2_NUM = rs.getString("NOT_MA2_NUM");
				NOT_MA2_DEN = rs.getString("NOT_MA2_DEN");
				NOT_MAA_NUM = rs.getString("NOT_MAA_NUM");
				NOT_MAA_DEN = rs.getString("NOT_MAA_DEN");
				NOT_MI1_NUM = rs.getString("NOT_MI1_NUM");
				NOT_MI1_DEN = rs.getString("NOT_MI1_DEN");
				NOT_MI2_NUM = rs.getString("NOT_MI2_NUM");
				NOT_MI2_DEN = rs.getString("NOT_MI2_DEN");
				NOT_MIA_NUM = rs.getString("NOT_MIA_NUM");
				NOT_MIA_DEN = rs.getString("NOT_MIA_DEN");
				TEM_CAL_CNS = rs.getString("TEM_CAL_CNS");
				TEM_ELP_CSV = rs.getString("TEM_ELP_CSV");
				TEM_NOT_ELP = rs.getString("TEM_NOT_ELP");
				TEM_RES_ELP = rs.getString("TEM_RES_ELP");
				TEM_ELP_ASD = rs.getString("TEM_ELP_ASD");
				NOT_DIS_FAC_OBL_CHX_NUM = rs.getString("NOT_DIS_FAC_OBL_CHX_NUM");
				NOT_DIS_FAC_OBL_CHX_DEN = rs.getString("NOT_DIS_FAC_OBL_CHX_DEN");
				NOT_MIN_PNT_NUM_FAC = rs.getString("NOT_MIN_PNT_NUM_FAC");
				NOT_MIN_PNT_DEN_FAC = rs.getString("NOT_MIN_PNT_DEN_FAC");
				NBR_PNT_MAX_FAC = rs.getString("NBR_PNT_MAX_FAC");
				TEM_GES_PNT_FAC = rs.getString("TEM_GES_PNT_FAC");
				TEM_ELP_CPB_OBL_CHX = rs.getString("TEM_ELP_CPB_OBL_CHX");

				String texte = 
						        COD_LSE	+ ";"
								+ 	COD_ELP	+ ";"
								+ 	COE_ADM_1	+ ";"
								+ 	COE_ADM_2	+ ";"
								+ 	COE_ADM_A	+ ";"
								+ 	COE_ELP_1	+ ";"
								+ 	COE_ELP_2	+ ";"
								+ 	COE_ELP_A	+ ";"
								+ 	DUR_CSV_ELP	+ ";"
								+ 	NOT_MA1_NUM	+ ";"
								+ 	NOT_MA1_DEN	+ ";"
								+ 	NOT_MA2_NUM	+ ";"
								+ 	NOT_MA2_DEN	+ ";"
								+ 	NOT_MAA_NUM	+ ";"
								+ 	NOT_MAA_DEN	+ ";"
								+ 	NOT_MI1_NUM	+ ";"
								+ 	NOT_MI1_DEN	+ ";"
								+ 	NOT_MI2_NUM	+ ";"
								+ 	NOT_MI2_DEN	+ ";"
								+ 	NOT_MIA_NUM	+ ";"
								+ 	NOT_MIA_DEN	+ ";"
								+ 	TEM_CAL_CNS	+ ";"
								+ 	TEM_ELP_CSV	+ ";"
								+ 	TEM_NOT_ELP	+ ";"
								+ 	TEM_RES_ELP	+ ";"
								+ 	TEM_ELP_ASD	+ ";"
								+ 	NOT_DIS_FAC_OBL_CHX_NUM	+ ";"
								+ 	NOT_DIS_FAC_OBL_CHX_DEN	+ ";"
								+ 	NOT_MIN_PNT_NUM_FAC	+ ";"
								+ 	NOT_MIN_PNT_DEN_FAC	+ ";"
								+ 	NBR_PNT_MAX_FAC	+ ";"
								+ 	TEM_GES_PNT_FAC	+ ";"
								+ 	TEM_ELP_CPB_OBL_CHX	+ ";"
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

			System.out.println("Insertion lse_regroupe_elp");
			PreparedStatement Pindividu = conn
					.prepareStatement("LOAD DATA LOCAL INFILE '"
							+ _Statics.workingDir.replace("\\", "/")
							+ "/ficher/lse_regroupe_elp.txt' "
							+ "INTO TABLE lse_regroupe_elp "
							+ "FIELDS "
							+ "TERMINATED BY ';' "
							+ "ESCAPED BY '\\\\' LINES STARTING BY '' "
							+ "TERMINATED BY '\\n' "
							+ "(COD_LSE, COD_ELP, COE_ADM_1, COE_ADM_2, COE_ADM_A, COE_ELP_1, COE_ELP_2, COE_ELP_A, DUR_CSV_ELP, NOT_MA1_NUM, NOT_MA1_DEN, NOT_MA2_NUM, NOT_MA2_DEN, NOT_MAA_NUM, NOT_MAA_DEN, NOT_MI1_NUM, NOT_MI1_DEN, NOT_MI2_NUM, NOT_MI2_DEN, NOT_MIA_NUM, NOT_MIA_DEN, TEM_CAL_CNS, TEM_ELP_CSV, TEM_NOT_ELP, TEM_RES_ELP, TEM_ELP_ASD, NOT_DIS_FAC_OBL_CHX_NUM, NOT_DIS_FAC_OBL_CHX_DEN, NOT_MIN_PNT_NUM_FAC, NOT_MIN_PNT_DEN_FAC, NBR_PNT_MAX_FAC, TEM_GES_PNT_FAC, TEM_ELP_CPB_OBL_CHX, datesync) ");

			Pindividu.executeUpdate();
			System.out.println("Fin Insertion lse_regroupe_elp");

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
