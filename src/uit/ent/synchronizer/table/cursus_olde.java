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

public class cursus_olde {

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

	private static int n;
	private static FileWriter writer;
	private static String txtDate;

	public static void TableCursus(String dateanne, String datsychr)
			throws SQLException {

		EntConnexion entcon = new EntConnexion();
		Statics cc= new Statics();
		
		DB_DRIVER = entcon.getDB_DRIVER();
		DB_CONNECTION = entcon.getDB_CONNECTION();
		DB_USER = entcon.getDB_USER();
		DB_PASSWORD = entcon.getDB_PASSWORD();

		JDBC_DRIVER = entcon.getJDBC_DRIVER();
		DB_URL = entcon.getDB_URL();
		USER = entcon.getUSER();
		PASS = entcon.getPASS();
		
		txtDate = new SimpleDateFormat("yyyy-MM-dd hh:m:ss", Locale.FRANCE).format(new Date());
		
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		Connection conn = null;
		Statement stmt = null;
		Statement stmt1 = null;

		// select from ind
		
		
		String selectSQL = "SELECT DISTINCT RESULTAT_ELP.COD_IND,"
				+ "LSE_REGROUPE_ELP.COD_ELP,"
				+ "LSE_REGROUPE_ELP.COD_LSE,"
				+ "RESULTAT_ELP.COD_ANU,"
				+ "ep1.LIB_ELP AS LIB_ELP, "
				+ "ep2.LIB_ELP AS LIB_LSE,"
				+ "ep1.COD_NEL AS COD_NEL_ELP, "
				+ "ep2.COD_NEL AS COD_NEL_LSE, "
				+ "RESULTAT_ELP.COD_SES,"
				+ "RESULTAT_ELP.NOT_ELP,"
				+ "RESULTAT_ELP.COD_TRE,"
				+ "INS_ADM_ETP.COD_ETP "
				+ "FROM LSE_REGROUPE_ELP, ELEMENT_PEDAGOGI ep1, ELEMENT_PEDAGOGI ep2, RESULTAT_ELP, INS_ADM_ETP "
				+ "WHERE LSE_REGROUPE_ELP.COD_LSE = ep1.COD_ELP "
				+ "AND LSE_REGROUPE_ELP.COD_ELP = ep2.COD_ELP "
				+ "AND RESULTAT_ELP.COD_ELP = LSE_REGROUPE_ELP.COD_ELP "
				+ "AND INS_ADM_ETP.COD_IND = RESULTAT_ELP.COD_IND "
				+ "AND INS_ADM_ETP.ETA_IAE = 'E' "
				+ "AND RESULTAT_ELP.COD_ADM ='1' "
				+ "AND INS_ADM_ETP.COD_ANU = " + dateanne;
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
				//writer = new FileWriter(cc.workingDir+"\\ficher\\"+ filename +".txt", false);
				writer = new FileWriter( Statics.workingDir.replace("\\", "/")+"/ficher/cursus.txt", false);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			while (rs.next()) {

				COD_IND = rs.getInt("COD_IND");
				COD_ELP = rs.getString("COD_ELP");
				COD_LSE = rs.getString("COD_LSE");
				COD_ANU = rs.getString("COD_ANU");
				LIB_ETB = rs.getString("LIB_ELP");
				LIB_LSE = rs.getString("LIB_LSE");
				COD_SES = rs.getInt("COD_SES");
				NOT_ELP = rs.getString("NOT_ELP");
				COD_TRE = rs.getString("COD_TRE");
				COD_NEL_ELP = rs.getString("COD_NEL_ELP");
				COD_NEL_LSE = rs.getString("COD_NEL_LSE");
				COD_ETP = rs.getString("COD_ETP");

				//System.out.println("cursus = "+i);
				
							
				//FileWriter writer = null;
				String texte = COD_IND +";"+ COD_ELP +";"+ COD_LSE +";"+ COD_ANU +";"+
						NOT_ELP +";"+ LIB_ETB +";"+ COD_SES +";"+ COD_TRE +";"+ LIB_LSE +";"+
						COD_NEL_ELP +";"+ COD_NEL_LSE +";"+ COD_ETP +";"+ datsychr +" \n";
				
				//String texte =  LIB_NOM_IND_ARB +" \n";
				
				
				texte = texte.replace("null", "---");
				String stre2 = new String (texte.getBytes(Charset.forName("ISO-8859-9")));
				//System.out.println(stre2);

				try{
				     //writer = new FileWriter("ficher/"+ filename +".txt", true);
				     writer.write(stre2,0,stre2.length());
				}catch(IOException ex){
				    ex.printStackTrace();
				}

				i++;
				/*
				 * if (n > 1000) { try { Thread.sleep(180000); n = 0; } catch
				 * (InterruptedException e) { // TODO Auto-generated catch block
				 * e.printStackTrace(); } }
				 */
				n++;

			} 
			
			 try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			 
			System.out.println("Insertion Cursus");		
			 //PreparedStatement Pindividu = conn .prepareStatement("LOAD DATA LOCAL INFILE 'ficher/cursus.txt' " +
			 PreparedStatement Pindividu = conn .prepareStatement("LOAD DATA LOCAL INFILE '" + Statics.workingDir.replace("\\", "/")+ "/ficher/cursus.txt' " +
					"INTO TABLE cursusresultat " +
					"FIELDS " +
					"TERMINATED BY ';' " +
					"ESCAPED BY '\\\\' LINES STARTING BY '' " +
					"TERMINATED BY '\\n' " +
					" (cod_ind, cod_elp, cod_lse, cod_annee, note, lib_elp, cod_session, resultat, lib_lse, cod_nel_elp, cod_nel_lse, cod_etp, datesync) ");
					//"SET datesync = NOW() ");
			Pindividu.executeUpdate();
			System.out.println("Fin Insertion Cursus");
			
		    
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
