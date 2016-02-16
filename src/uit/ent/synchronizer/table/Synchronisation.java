package uit.ent.synchronizer.table;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import uit.ent.synchronizer.Statics;

public class Synchronisation {
	private static String txtDate;
	private static PreparedStatement datesynch;
	private static String JDBC_DRIVER;
	private static String DB_URL;
	private static String USER;
	private static String PASS;
	private String iddatesynch;
	private Date date;
	private FileWriter writer;

	public void operationSynchronisationMysql(String dateanne)
			throws SQLException {

		EntConnexion entcon = new EntConnexion();

		// ------------ vider les tables Mysql ----------
		JDBC_DRIVER = entcon.getJDBC_DRIVER();
		DB_URL = entcon.getDB_URL();
		USER = entcon.getUSER();
		PASS = entcon.getPASS();

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		Connection conn = null;
		Statement stmt = null;
		Statement stmt1 = null;
		PreparedStatement ps = null;

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

		txtDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRANCE)
				.format(new Date());
		System.out.println("Date de synchronisation" + txtDate);
		try {
			writer = new FileWriter(Statics.workingDir.replace("\\", "/")
					+ "/ficher/datesynch.txt", false);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		String texte = txtDate + ";0 \n";

		try {
			writer.write(texte, 0, texte.length());
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("BEGIN :  Sycn datesynch");
		
		PreparedStatement Pindividu = conn
				.prepareStatement("LOAD DATA LOCAL INFILE '"
						+ Statics.workingDir.replace("\\", "/")
						+ "/ficher/datesynch.txt' " + "INTO TABLE datesynch "
						+ "FIELDS " + "TERMINATED BY ';' "
						+ "ESCAPED BY '\\\\' LINES STARTING BY '' "
						+ "TERMINATED BY '\\n' " + " (date, etat)");
		Pindividu.executeUpdate();

		System.out.println("END :  Sycn datesynch");

		System.out.println("BEGIN :  Sycn anneeUni");
		
		AnneeUni anneeuni = new AnneeUni();
		try {
			anneeuni.TableAnueeuni(txtDate);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("END :  Sycn anneeUni");
		
		System.out.println("BEGIN : Sync Individu");
		
		Individu individu = new Individu();
		try {
			individu.tableIndividu(dateanne, txtDate);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("BEGIN : Sync Individu");

	}
}
