package uit.ent.synchronizer.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class CronTriggerSynchonisation {
	
	public static void premiereSynchronisation(String p1, String m1, String date) throws Exception {
		
		Execution e1 = new Execution();
		e1.dateannee(date);
		
		JobDetail job = JobBuilder.newJob(Execution.class)
				.withIdentity("dummyJobName", "group1").build();
		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withIdentity("dummyTriggerName", "group1")
				.withSchedule(
						// a 15:58h entre lundi samedi
						CronScheduleBuilder.cronSchedule("0 "+ m1 +" "+ p1 +" ? * MON-SAT"))
				.build();
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.start();
		scheduler.scheduleJob(job, trigger);
	}

	public static void deuxiemeSynchronisation(String p2, String m2, String date) throws Exception {
		
		Execution e2 = new Execution();
		e2.dateannee(date);
		
		JobDetail job1 = JobBuilder.newJob(Execution.class)
				.withIdentity("dummyJobName2", "group1").build();
		Trigger trigger1 = TriggerBuilder
				.newTrigger()
				.withIdentity("dummyTriggerName2", "group1")
				.withSchedule(
						//// a 16:00h entre lundi samedi
						CronScheduleBuilder.cronSchedule("0 "+ m2 +" "+ p2 +" ? * MON-SUN"))
				.build();
		// schedule it
		Scheduler scheduler1 = new StdSchedulerFactory().getScheduler();
		scheduler1.start();
		scheduler1.scheduleJob(job1, trigger1);
		
	}
}
