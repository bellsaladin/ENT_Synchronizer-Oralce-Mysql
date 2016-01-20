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

import uit.ent.synchronizer.Config;

public class cursus_annee {
	
	private static int loadInFileIndividusCount= 0;
	
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
	private static String COD_IND;
	private static String COD_ELP;
	private static String COD_LSE;
	private static String COD_ANU;
	private static int COD_SES;
	private static String id_curresu;
	private static String LIB_ETB;
	private static String LIB_LSE;
	private static String NOT_ELP;
	private static String COD_TRE;
	private static String COD_NEL_ELP;
	private static String COD_NEL_LSE;
	private static String COD_ETP;
	private static String filename = "cursus";

	private static String LIS_ELP_COD_ANU;
	private static String LIS_ELP_COD_ANU_PLUS_1;
	private static String LIS_ELP_LIC_NEL;
	private static String LIS_ELP_COD_ELP;
	private static String LIS_ELP_LIB_ELP;
	private static String LIS_COD_IND;
	private static String not_elp;
	private static String cod_tre_elp;
	private static String cod_ses_elp;
	private static String COD_NNE_IND;

	private static int n;
	private static FileWriter writer;
	private static String txtDate;

	public static void TableCursus(String dateanne, String datsychr)
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
		
		for(int codeIndividu: individu.listCodesIndividus){
			
			
			youness(codeIndividu, datsychr, dateanne);
			System.out.println("Cursus annee :"+ n);
			n++;
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

	public static void youness(int codeIndividu, String datsychr, String dateanne) {
		
		if(cursus_annee.loadInFileIndividusCount == 0){ // nouveau lot d'un load infile
			System.out.println("Cursus annee Nouveau lot ");
			try {
				writer = new FileWriter(_Statics.workingDir.replace("\\", "/")
						+ "/ficher/cursusannee.txt", false);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		cursus_annee.loadInFileIndividusCount++;
		
		//System.out.println("youness (" +codeIndividu + "," + datsychr + "," + dateanne + ")"); 
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

		PreparedStatement preparedStatement1 = null;

		Connection conn = null;
		Statement stmt = null;
		Statement stmt1 = null;

		String selectSQL = "select LIS_ELP_COD_ANU,LIS_ELP_COD_ANU_PLUS_1,LIS_ELP_LIC_NEL,LIS_ELP_COD_ELP,LIS_ELP_LIB_ELP,LIS_COD_IND,not_elp,cod_tre_elp,cod_ses_elp from "
				+ "(SELECT "
				+ "ICE.COD_ANU LIS_ELP_COD_ANU,	"
				+ "ICE.COD_ANU+1 LIS_ELP_COD_ANU_PLUS_1, "
				+ "NEL.LIC_NEL LIS_ELP_LIC_NEL,	"
				+ "ICE.COD_ELP LIS_ELP_COD_ELP, "
				+ "ELP.LIB_ELP LIS_ELP_LIB_ELP, "
				+ "IND.COD_IND LIS_COD_IND "
				+ "FROM	"
				+ "INDIVIDU IND, "
				+ "IND_CONTRAT_ELP ICE,	"
				+ "ELEMENT_PEDAGOGI ELP, "
				+ "NATURE_ELP NEL,"
				+ "INS_ADM_ANU IAA	"
				+ "WHERE 1=1 "
				+ "AND (ICE.COD_IND(+)  = IND.COD_IND) "
				+ "AND (ICE.TEM_PRC_ICE = 'N') "
				+ "AND (ELP.COD_ELP(+) = ICE.COD_ELP) "
				+ "AND (NEL.COD_NEL(+) = ELP.COD_NEL) AND IAA.COD_IND = "
				+ codeIndividu + " "
				+ "AND IAA.ETA_IAA = 'E' "
				+ "AND IND.COD_IND = "
				+ codeIndividu + " "
				+ "AND (ELP.COD_NEL LIKE '%SM0%' OR ELP.COD_NEL LIKE '%MOD%') "
				+ "AND (ICE.COD_ELP NOT LIKE 'CP%') "
				+ "group by ICE.COD_ANU, "
				+ "NEL.LIC_NEL, "
				+ "ICE.COD_ELP, "
				+ "ELP.LIB_ELP, "
				+ "IND.COD_IND "
				+ "ORDER BY ICE.COD_ANU DESC, "
				+ "ICE.COD_ELP), "
				+ "(select cod_ind as cod_ind_res,cod_anu as cod_anu_elp,cod_elp as cod_elp_res,not_elp as not_elp,cod_tre as cod_tre_elp,cod_ses as cod_ses_elp "
				+ "from resultat_elp "
				+ "where TEM_NOT_RPT_ELP='N' "
				+ "and cod_adm= 1 "
				+ "and cod_ind= "
				+ codeIndividu + " "
				+ "group by cod_ind,cod_elp,not_elp,cod_tre,cod_ses,cod_anu) "
				+ "where LIS_COD_IND=cod_ind_res "
				+ "and LIS_ELP_COD_ANU=cod_anu_elp "
				+ "and LIS_ELP_COD_ANU= "
				+ dateanne + " "
				+ "and cod_ses_elp ='1' "
				+ "and cod_elp_res=LIS_ELP_COD_ELP "
				+ "ORDER BY  LIS_ELP_COD_ELP ASC";
		//System.out.println("youness::selectSQL : " + selectSQL);
		try {
			
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(selectSQL);
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(DB_URL, USER, PASS);
				stmt1 = conn.createStatement();
			} catch (ClassNotFoundException e) {
				System.out.println("Exception");
				System.out.println(e.getMessage());
			}
			ResultSet rs = preparedStatement.executeQuery();
			//System.out.println("CursusAnnee::Youness::ResultSet" + preparedStatement.toString());
			/*try {
				writer = new FileWriter(_Statics.workingDir.replace("\\", "/")
						+ "/ficher/cursusannee.txt", true);
			} catch (IOException e) {
				System.out.println("Exception");
				System.out.println(e.getMessage());
			}*/
			//System.out.println("CursusAnnee::Youness::ResultSet");
			while (rs.next()) {
				LIS_ELP_COD_ANU = rs.getString("LIS_ELP_COD_ANU");
				LIS_ELP_COD_ANU_PLUS_1 = rs.getString("LIS_ELP_COD_ANU_PLUS_1");
				LIS_ELP_LIC_NEL = rs.getString("LIS_ELP_LIC_NEL");
				LIS_ELP_COD_ELP = rs.getString("LIS_ELP_COD_ELP");
				LIS_ELP_LIB_ELP = rs.getString("LIS_ELP_LIB_ELP").replaceAll(
						"[\r\n]+", "");
				LIS_COD_IND = rs.getString("LIS_COD_IND");
				not_elp = rs.getString("not_elp");
				cod_tre_elp = rs.getString("cod_tre_elp");
				cod_ses_elp = rs.getString("cod_ses_elp");

				String texte = LIS_ELP_COD_ANU + ";" + LIS_ELP_LIC_NEL + ";"
						+ LIS_ELP_COD_ELP + ";" + LIS_ELP_LIB_ELP + ";"
						+ LIS_COD_IND + ";" + datsychr + " \n";

				texte = texte.replace("null", "---");
				//System.out.println(texte);
				String stre2 = new String(texte.getBytes(Charset
						.forName("ISO-8859-9")));
				try {
					writer.write(stre2, 0, stre2.length());
					//System.out.println("Write" + stre2);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				
			}
			
			if(cursus_annee.loadInFileIndividusCount == Config.LOAD_IN_FILE_LOT_QTY || String.valueOf(codeIndividu).equals(individu.listCodesIndividus.get(individu.listCodesIndividus.size() - 1)) ){
				
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				System.out.println("Cursus annee Insertion lot ");
				
				PreparedStatement Pindividu = conn
						.prepareStatement("LOAD DATA LOCAL INFILE '"
								+ _Statics.workingDir.replace("\\", "/")
								+ "/ficher/cursusannee.txt' "
								+ "INTO TABLE cursusannee "
								+ "FIELDS "
								+ "TERMINATED BY ';' "
								+ "ESCAPED BY '\\\\' LINES STARTING BY '' "
								+ "TERMINATED BY '\\n' "
								+ " (cod_annee, lib_lse, cod_elp, lib_elp, cod_ind, datesync) ");
				Pindividu.executeUpdate();
				cursus_annee.loadInFileIndividusCount = 0;
				
			}
			
			//System.out.println("Insertion Cursus");
			
			//System.out.println("Fin Insertion Cursus");

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Exception : " + e.getMessage());
		} finally {

		}
	}

}
