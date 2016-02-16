package uit.ent.synchronizer.table;

public class EntConnexion {
	
	public static String DB_ORACLE_DRIVER;
	public static String DB_ORACLE_CONNECTION;
	public static String DB_ORACLE_USER;
	public static String DB_ORACLE_PASSWORD;

	public static String DB_MYSQL_DRIVER;
	public static String DB_MYSQL_URL;
	public static String DB_MYSQL_USER;
	public static String DB_MYSQL_PASSWORD;

	public EntConnexion()
	  {
		//Infotmations sur database Oracle
		DB_ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";
	    DB_ORACLE_CONNECTION = "jdbc:oracle:thin:@10.1.0.251:2001:kenitra";
		//DB_CONNECTION = "jdbc:oracle:thin:@196.200.142.40:1521:apogee";
		
		DB_ORACLE_USER = "ent";
		DB_ORACLE_PASSWORD = "jsffhhfg5847296";
		
		
		//Infotmations sur database Oracle
		//DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
		//DB_CONNECTION = "jdbc:oracle:thin:@10.1.104.251:1521:kenitra";
		//DB_CONNECTION = "jdbc:oracle:thin:@196.200.142.40:1521:apogee";
		//DB_USER = "kent";
		//DB_PASSWORD = "elik89b";

		
		//Informations sur database Mysql
		DB_MYSQL_DRIVER = "com.mysql.jdbc.Driver";
		DB_MYSQL_USER = "root";
	    //DB_URL = "jdbc:mysql://196.200.142.7/ent";
		//DB_URL = "jdbc:mysql://172.16.1.22/ent";
		//PASS = "ent09050211";
		DB_MYSQL_URL = "jdbc:mysql://localhost/ent";
		DB_MYSQL_PASSWORD = "root";
		
	
	  }

	public static String getDB_DRIVER() {
		return DB_ORACLE_DRIVER;
	}

	public static void setDB_DRIVER(String dB_DRIVER) {
		DB_ORACLE_DRIVER = dB_DRIVER;
	}

	public static String getDB_CONNECTION() {
		return DB_ORACLE_CONNECTION;
	}

	public static void setDB_CONNECTION(String dB_CONNECTION) {
		DB_ORACLE_CONNECTION = dB_CONNECTION;
	}

	public static String getDB_USER() {
		return DB_ORACLE_USER;
	}

	public static void setDB_USER(String dB_USER) {
		DB_ORACLE_USER = dB_USER;
	}

	public static String getDB_PASSWORD() {
		return DB_ORACLE_PASSWORD;
	}

	public static void setDB_PASSWORD(String dB_PASSWORD) {
		DB_ORACLE_PASSWORD = dB_PASSWORD;
	}

	public static String getJDBC_DRIVER() {
		return DB_MYSQL_DRIVER;
	}

	public static void setJDBC_DRIVER(String jDBC_DRIVER) {
		DB_MYSQL_DRIVER = jDBC_DRIVER;
	}

	public static String getDB_URL() {
		return DB_MYSQL_URL;
	}

	public static void setDB_URL(String dB_URL) {
		DB_MYSQL_URL = dB_URL;
	}

	public static String getUSER() {
		return DB_MYSQL_USER;
	}

	public static void setUSER(String uSER) {
		DB_MYSQL_USER = uSER;
	}

	public static String getPASS() {
		return DB_MYSQL_PASSWORD;
	}

	public static void setPASS(String pASS) {
		DB_MYSQL_PASSWORD = pASS;
	}
}
