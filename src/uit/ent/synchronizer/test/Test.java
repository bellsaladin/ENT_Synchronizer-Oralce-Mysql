package uit.ent.synchronizer.test;

import org.quartz.JobExecutionException;
import uit.ent.synchronizer.quartz.Execution;

public class Test {
	
	public static void main(String[] args) {
		
		Execution execution = new Execution();
		execution.dateannee("2015");
		try {
			execution.execute(null);
		} catch (JobExecutionException e) {
			e.printStackTrace();
		}
	}
	

}
