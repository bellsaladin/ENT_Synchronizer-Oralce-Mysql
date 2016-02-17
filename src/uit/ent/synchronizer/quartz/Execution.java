package uit.ent.synchronizer.quartz;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import uit.ent.synchronizer.table.Synchronisation;
import uit.ent.synchronizer.table.RemoveExpiredSynchronisationData;
import uit.ent.synchronizer.table.generic.Synchronizable;

public class Execution extends Synchronizable implements Job
{
	private String cod_anu;
	
	public void dateannee (String cod_anu){
		this.cod_anu = cod_anu;
	}
	
	public void execute(JobExecutionContext context)
	throws JobExecutionException {
		
		System.out.println("L'opération de synchronisation a commencé...");
		
		PreparedStatement statementActivateSynchronisation = null;
		
		Synchronisation synch = new Synchronisation();
		try {
			synch.run(cod_anu);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			statementActivateSynchronisation = getConnection("mysql").prepareStatement("UPDATE datesynch SET etat = 1 WHERE id = (Select max(id) From datesynch)");
					statementActivateSynchronisation.executeUpdate();
		} catch (SQLException e) {
			getLogger().error("Update datesync", e);
		}		
				
		// Supprimer les données des anciennes synchronisations
		RemoveExpiredSynchronisationData removeExpiredSynchronisationData = new RemoveExpiredSynchronisationData();
		removeExpiredSynchronisationData.run(Synchronisation.synchronisationDate);
		
		closeConnection("mysql");
		closeConnection("oracle");
				//----------------------------------------------------
		JOptionPane.showMessageDialog(null, "La tâche a été effectuée avec succés !");

	}
	
}
