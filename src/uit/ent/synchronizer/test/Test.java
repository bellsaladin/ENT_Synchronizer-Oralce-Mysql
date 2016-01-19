package uit.ent.synchronizer.test;

import org.quartz.JobExecutionException;

import com.mkyong.quartz.execution;

public class Test {

	public static void main(String[] args) {
		execution exec = new execution();
		exec.dateannee("2015");
		try {
			exec.execute(null);
		} catch (JobExecutionException e) {
			e.printStackTrace();
		}
		
	}

}
