package org.eclipse.wb.swing;

public class entconnexion {
	
	public static String DB_DRIVER;
	public static String DB_CONNECTION;
	public static String DB_USER;
	public static String DB_PASSWORD;

	public static String JDBC_DRIVER;
	public static String DB_URL;
	public static String USER;
	public static String PASS;

	public void entconnexion()
	  {
		//Infotmations sur database Oracle
		DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
	    DB_CONNECTION = "jdbc:oracle:thin:@10.1.0.251:2001:kenitra";
		//DB_CONNECTION = "jdbc:oracle:thin:@196.200.142.40:1521:apogee";
		
		DB_USER = "ent";
		DB_PASSWORD = "jsffhhfg5847296";
		
		
		//Infotmations sur database Oracle
		//DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
		//DB_CONNECTION = "jdbc:oracle:thin:@10.1.104.251:1521:kenitra";
		//DB_CONNECTION = "jdbc:oracle:thin:@196.200.142.40:1521:apogee";
		//DB_USER = "kent";
		//DB_PASSWORD = "elik89b";

		
		//Informations sur database Mysql
		JDBC_DRIVER = "com.mysql.jdbc.Driver";
		USER = "root";
	    //DB_URL = "jdbc:mysql://196.200.142.7/ent";
		//DB_URL = "jdbc:mysql://172.16.1.22/ent";
		//PASS = "ent09050211";
		DB_URL = "jdbc:mysql://localhost/ent_v3";
		PASS = "root";
		
	
	  }

	public static String getDB_DRIVER() {
		return DB_DRIVER;
	}

	public static void setDB_DRIVER(String dB_DRIVER) {
		DB_DRIVER = dB_DRIVER;
	}

	public static String getDB_CONNECTION() {
		return DB_CONNECTION;
	}

	public static void setDB_CONNECTION(String dB_CONNECTION) {
		DB_CONNECTION = dB_CONNECTION;
	}

	public static String getDB_USER() {
		return DB_USER;
	}

	public static void setDB_USER(String dB_USER) {
		DB_USER = dB_USER;
	}

	public static String getDB_PASSWORD() {
		return DB_PASSWORD;
	}

	public static void setDB_PASSWORD(String dB_PASSWORD) {
		DB_PASSWORD = dB_PASSWORD;
	}

	public static String getJDBC_DRIVER() {
		return JDBC_DRIVER;
	}

	public static void setJDBC_DRIVER(String jDBC_DRIVER) {
		JDBC_DRIVER = jDBC_DRIVER;
	}

	public static String getDB_URL() {
		return DB_URL;
	}

	public static void setDB_URL(String dB_URL) {
		DB_URL = dB_URL;
	}

	public static String getUSER() {
		return USER;
	}

	public static void setUSER(String uSER) {
		USER = uSER;
	}

	public static String getPASS() {
		return PASS;
	}

	public static void setPASS(String pASS) {
		PASS = pASS;
	}
}
