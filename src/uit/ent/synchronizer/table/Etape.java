package uit.ent.synchronizer.table;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import uit.ent.synchronizer.Config;
import uit.ent.synchronizer.table.generic.Synchronizable;

public class Etape extends Synchronizable{

	private static int COD_IND;
	private static String LIB_ETP;
	private static int COD_ANU;
	private static String COD_ETP;
	private static String LIB_DIP;
	private static String LIC_DIP;
	private static FileWriter writer;

	public void synchronize(String dateanne, String datsychr)
			throws SQLException {

		PreparedStatement preparedStatement = null;

		String selectSQL = "SELECT DISTINCT INDIVIDU.COD_IND,"
				+ "ETAPE.LIB_ETP," + "INS_ADM_ETP.COD_ANU,"
				+ "INS_ADM_ETP.COD_ETP," + "DIPLOME.LIB_DIP,"
				+ "DIPLOME.LIC_DIP " + "FROM ETAPE " + "FULL JOIN INS_ADM_ETP "
				+ "ON INS_ADM_ETP.COD_ETP = ETAPE.COD_ETP "
				+ "FULL JOIN INDIVIDU "
				+ "ON INS_ADM_ETP.COD_IND = INDIVIDU.COD_IND "
				+ "FULL JOIN DIPLOME "
				+ "ON DIPLOME.COD_DIP = INS_ADM_ETP.COD_DIP "
				+ "WHERE INS_ADM_ETP.COD_ANU = " + dateanne
				+ " AND INS_ADM_ETP.ETA_IAE = 'E'";
		try {
			preparedStatement = getConnection("oracle").prepareStatement(selectSQL);
			ResultSet rs = preparedStatement.executeQuery();
			
			try {
				writer = new FileWriter(Config.workingDir.replace("\\", "/")
						+ "/ficher/etape.txt", false);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			while (rs.next()) {

				COD_IND = rs.getInt("COD_IND");
				LIB_ETP = rs.getString("LIB_ETP");
				COD_ANU = rs.getInt("COD_ANU");
				COD_ETP = rs.getString("COD_ETP");
				LIB_DIP = rs.getString("LIB_DIP");
				LIC_DIP = rs.getString("LIC_DIP");

				String texte = COD_ETP + ";" + COD_IND + ";" + LIB_ETP + ";"
						+ COD_ANU + ";" + LIB_DIP + ";" + LIC_DIP + ";"
						+ datsychr + " \n";

				texte = texte.replace("null", "---");
				String stre2 = new String(texte.getBytes(Charset
						.forName("ISO-8859-9")));

				try {
					writer.write(stre2, 0, stre2.length());
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}

			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			System.out.println("Insertion etape");
			PreparedStatement Pindividu = getConnection("mysql")
					.prepareStatement("LOAD DATA LOCAL INFILE '"
							+ Config.workingDir.replace("\\", "/")
							+ "/ficher/etape.txt' "
							+ "INTO TABLE etape "
							+ "FIELDS "
							+ "TERMINATED BY ';' "
							+ "ESCAPED BY '\\\\' LINES STARTING BY '' "
							+ "TERMINATED BY '\\n' "
							+ " (cod_etp, cod_ind, lib_etp, cod_annee, lib_dip, lic_dip, datesync) ");
			Pindividu.executeUpdate();
			System.out.println("Fin Insertion etape");
		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			closeConnection("oracle");
			closeConnection("mysql");
		}

	}

}
