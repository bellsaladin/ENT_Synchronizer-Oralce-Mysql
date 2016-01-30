package uit.ent.synchronizer;

public class Config {
	public static boolean TEST = false;
	public static int LIMIT_NUMBER_OF_ROWS = 500; // used only when Config.TEST = true
	
	public static int LOAD_IN_FILE_BATCH_QTY = 5000;
	public static int START_FROM_INDIVIDU_NBR = 0; // 0 : means start from the beginning
}
