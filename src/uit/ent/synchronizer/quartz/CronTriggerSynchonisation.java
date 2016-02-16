package uit.ent.synchronizer.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class CronTriggerSynchonisation {
	// public static void main( String[] args ) throws Exception
	
	public static void premieresynchronisation(String p1, String m1, String date) throws Exception {
		
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
				/*� Tous les jours du lundi au vendredi � 08h00 � se traduit
				par � 0 0 8 ? * MON-FRI � ;
				� Tous les derniers vendredis du mois � 10h15 � se traduit
				par � 0 15 10 ? * 6L �.
				CronScheduleBuilder.cronSchedule("0 0 18 ? * MON-SUN"))
				 */
				.build();
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.start();
		scheduler.scheduleJob(job, trigger);
	}


	public static void deuxiemesynchronisation(String p2, String m2, String date) throws Exception {
		
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
