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

public class Diplome {

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
	private static int cod_ind;
	private static String cod_dip;
	private static String lic_vdi;
	private static String lib_dip;
	private static int cod_anu;
	public static int cod_ses;
	private static String not_vdi;
	private static String cod_tre;
	private static String not_vdi_1;
	private static int num_obt_vdi;
	private static String id_dip;
	private static int n;
	private static String filename = "diplom";
	private static FileWriter writer;
	private static String txtDate;

	public static void Tabledip(String dateanne, String datsychr)
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

		String selectSQL = "SELECT DISTINCT RESULTAT_VDI.COD_IND,"
				+ "VERSION_DIPLOME.COD_DIP," + "VERSION_DIPLOME.LIC_VDI,"
				+ "DIPLOME.LIB_DIP," + "RESULTAT_VDI.COD_ANU,"
				+ "RESULTAT_VDI.COD_IND," + "RESULTAT_VDI.COD_SES,"
				+ "RESULTAT_VDI.NOT_VDI," + "RESULTAT_VDI.COD_TRE,"
				+ "RESULTAT_VDI.NOT_VDI AS NOT_VDI_1,"
				+ "RESULTAT_VDI.NUM_OBT_VDI " + "FROM RESULTAT_VDI "
				+ "FULL JOIN DIPLOME "
				+ "ON RESULTAT_VDI.COD_DIP = DIPLOME.COD_DIP "
				+ "FULL JOIN VERSION_DIPLOME "
				+ "ON DIPLOME.COD_DIP = VERSION_DIPLOME.COD_DIP "
				+ "AND RESULTAT_VDI.COD_VRS_VDI = VERSION_DIPLOME.COD_VRS_VDI "
				+ "AND RESULTAT_VDI.NUM_OBT_VDI IS NOT NULL "
				+ "FULL JOIN INS_ADM_ETP "
				+ "ON INS_ADM_ETP.COD_IND = RESULTAT_VDI.COD_IND "
				+ "WHERE INS_ADM_ETP.COD_ANU = " + dateanne
				+ "	AND INS_ADM_ETP.ETA_IAE = 'E'";
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
						+ "/ficher/diplom.txt", false);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			while (rs.next()) {

				cod_ind = rs.getInt("COD_IND");
				cod_dip = rs.getString("COD_DIP");
				lic_vdi = rs.getString("LIC_VDI");
				lib_dip = rs.getString("LIB_DIP");
				cod_anu = rs.getInt("COD_ANU");
				cod_ses = rs.getInt("COD_SES");
				not_vdi = rs.getString("NOT_VDI");
				cod_tre = rs.getString("COD_TRE");
				not_vdi_1 = rs.getString("NOT_VDI_1");
				num_obt_vdi = rs.getInt("NUM_OBT_VDI");

				String texte = cod_ind + ";" + cod_dip + ";" + lic_vdi + ";"
						+ lib_dip + ";" + cod_anu + ";" + cod_ses + ";"
						+ not_vdi + ";" + cod_tre + ";" + not_vdi_1 + ";"
						+ num_obt_vdi + ";" + datsychr + " \n";

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

			System.out.println("Insertion Diplom");
			PreparedStatement Pindividu = conn
					.prepareStatement("LOAD DATA LOCAL INFILE '"
							+ Statics.workingDir.replace("\\", "/")
							+ "/ficher/diplom.txt' "
							+ "INTO TABLE diplome "
							+ "FIELDS "
							+ "TERMINATED BY ';' "
							+ "ESCAPED BY '\\\\' LINES STARTING BY '' "
							+ "TERMINATED BY '\\n' "
							+ " (cod_ind, cod_dip, lic_vdi, lib_dip, cod_annee, cod_sesion, not_vdi, cod_tre, not_vdi_1, num_obt_vdi, datesync) ");
			// "SET datesync = NOW() ");
			Pindividu.executeUpdate();
			System.out.println("Fin Insertion Diplom");

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
