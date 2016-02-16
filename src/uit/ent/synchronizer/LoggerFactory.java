package uit.ent.synchronizer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LoggerFactory {

	/* Get actual class name to be printed on */
	public static Logger logger;;
	static final String LOG_PROPERTIES_FILE = "data/resources/log4j.properties";

	
	public static Logger getLogger(String className) {
		if(logger != null)
			return logger;
		
		logger = Logger.getLogger(className);
	    Properties logProperties = new Properties();
	    try {
	    	// load our log4j properties / configuration file
	    	logProperties.load(new FileInputStream(LOG_PROPERTIES_FILE));
	      	PropertyConfigurator.configure(logProperties);
	      	logger.info("Logging initialized.");
	      	tieSystemOutAndErrToLog();
	      	
	      	return logger;
	    }
	    catch(IOException e) {
	    	throw new RuntimeException("Unable to load logging properties file " + LOG_PROPERTIES_FILE);
	    }
	}
	
	private static void tieSystemOutAndErrToLog() {
        System.setOut(createLoggingProxy(System.out));
        System.setErr(createLoggingProxy(System.err));
    }

    public static PrintStream createLoggingProxy(final PrintStream realPrintStream) {
        return new PrintStream(realPrintStream) {
            public void print(final String string) {
                realPrintStream.print(string);
                logger.info(string);
            }
        };
    }
}
