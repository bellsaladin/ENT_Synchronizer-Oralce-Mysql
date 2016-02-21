package uit.ent.synchronizer;

public class Config {
	public static boolean TEST = false;
	public static int LIMIT_NUMBER_OF_ROWS = 500; // used only when Config.TEST = true
	
	public static int LOAD_IN_FILE_BATCH_QTY = 100;
	public static int START_FROM_INDIVIDU_NBR = 0; // 0 : means start from the beginning
	
	public static String DB_ORACLE_DRIVER      =  "oracle.jdbc.driver.OracleDriver";
	public static String DB_ORACLE_CONNECTION  =  "jdbc:oracle:thin:@10.1.0.251:2001:kenitra";
	public static String DB_ORACLE_USER        =  "ent";
	public static String DB_ORACLE_PASSWORD    =  "jsffhhfg5847296";

	public static String DB_MYSQL_DRIVER       =  "com.mysql.jdbc.Driver";
	public static String DB_MYSQL_URL          =  "jdbc:mysql://localhost/ent";
	public static String DB_MYSQL_USER         =  "root";
	public static String DB_MYSQL_PASSWORD     =  "root";
	
	public static String workingDir            =  System.getProperty("user.dir");
	
}
