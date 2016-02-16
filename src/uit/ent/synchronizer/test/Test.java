package uit.ent.synchronizer.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.quartz.JobExecutionException;

import uit.ent.synchronizer.LoggerFactory;
import uit.ent.synchronizer.Statics;
import uit.ent.synchronizer.quartz.Execution;


public class Test {

	static final String LOG_PROPERTIES_FILE = "data/resources/log4j.properties";

	
	public static void main(String[] args) {
		System.out.println("Working Directory = " +  System.getProperty("user.dir"));
		Logger logger = LoggerFactory.getLogger(Test.class.getName());
		logger.debug("Hello this is a debug message");
		logger.info("Hello this is an info message");
	    	      
		Execution execution = new Execution();
		execution.dateannee("2015");
		try {
			execution.execute(null);
		} catch (JobExecutionException e) {
			e.printStackTrace();
		}
	}
	

}
