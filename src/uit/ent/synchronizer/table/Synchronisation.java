package uit.ent.synchronizer.table;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import uit.ent.synchronizer.Statics;
import uit.ent.synchronizer.table.generic.Synchronizable;

public class Synchronisation extends Synchronizable{
	public static String synchronisationDate;
	private FileWriter writer;

	public void run(String dateanne)
			throws SQLException {

		synchronisationDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRANCE)
				.format(new Date());
		System.out.println("Date de synchronisation : " + synchronisationDate);
		try {
			writer = new FileWriter(Statics.workingDir.replace("\\", "/")
					+ "/ficher/datesynch.txt", false);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		String texte = synchronisationDate + ";0 \n";

		try {
			writer.write(texte, 0, texte.length());
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("BEGIN :  Sycn datesynch");
		
		PreparedStatement Pindividu = getConnection("mysql")
				.prepareStatement("LOAD DATA LOCAL INFILE '"
						+ Statics.workingDir.replace("\\", "/")
						+ "/ficher/datesynch.txt' " + "INTO TABLE datesynch "
						+ "FIELDS " + "TERMINATED BY ';' "
						+ "ESCAPED BY '\\\\' LINES STARTING BY '' "
						+ "TERMINATED BY '\\n' " + " (date, etat)");
		Pindividu.executeUpdate();

		closeConnection("mysql");
		
		System.out.println("END :  Sycn datesynch");

		System.out.println("BEGIN :  Sycn anneeUni");
		
		AnneeUni anneeUni = new AnneeUni();
		try {
			anneeUni.synchronize(synchronisationDate);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("END :  Sycn anneeUni");
				
		Individu individu = new Individu();
		try {
			individu.synchronize(dateanne, synchronisationDate);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

	}
}
