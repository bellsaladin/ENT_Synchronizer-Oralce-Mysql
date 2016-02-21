package uit.ent.synchronizer.table;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import uit.ent.synchronizer.Config;
import uit.ent.synchronizer.table.generic.Synchronizable;

public class Bac extends Synchronizable{

	private static int COD_IND;
	private static String DAA_OBT_BAC_IBA;
	private static String LIB_BAC;
	private static String LIB_MNB;
	private static String LIB_ETB;
	private static String LIB_DEP;
	private static FileWriter writer;

	public void synchronize(String dateanne, String datsychr)
			throws SQLException {

		PreparedStatement preparedStatement = null;

		String selectSQL = "SELECT DISTINCT IND_BAC.COD_BAC,"
				+ "INDIVIDU.COD_IND," + "BAC_OUX_EQU.LIB_BAC,"
				+ "MENTION_NIV_BAC.LIB_MNB," + "ETABLISSEMENT.LIB_ETB,"
				+ "DEPARTEMENT.LIB_DEP," + "IND_BAC.DAA_OBT_BAC_IBA "
				+ "FROM INDIVIDU " + "FULL JOIN IND_BAC "
				+ "ON INDIVIDU.COD_IND = IND_BAC.COD_IND "
				+ "FULL JOIN DEPARTEMENT "
				+ "ON DEPARTEMENT.COD_DEP = IND_BAC.COD_DEP "
				+ "FULL JOIN BAC_OUX_EQU "
				+ "ON BAC_OUX_EQU.COD_BAC = IND_BAC.COD_BAC "
				+ "FULL JOIN ETABLISSEMENT "
				+ "ON ETABLISSEMENT.COD_ETB = IND_BAC.COD_ETB "
				+ "FULL JOIN MENTION_NIV_BAC "
				+ "ON MENTION_NIV_BAC.COD_MNB = IND_BAC.COD_MNB "
				+ "FULL JOIN INS_ADM_ETP "
				+ "ON INS_ADM_ETP.COD_IND = INDIVIDU.COD_IND "
				+ "WHERE INS_ADM_ETP.COD_ANU = " + dateanne
				+ " AND INS_ADM_ETP.ETA_IAE = 'E'";
		// AND INDIVIDU.COD_IND = '38032'
		try {
			preparedStatement = getConnection("oracle").prepareStatement(selectSQL);


			ResultSet rs = preparedStatement.executeQuery();

			try {
				writer = new FileWriter(Config.workingDir.replace("\\", "/")
						+ "/ficher/Bac.txt", false);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			while (rs.next()) {

				rs.getString("COD_BAC");
				COD_IND = rs.getInt("COD_IND");
				LIB_BAC = rs.getString("LIB_BAC");
				LIB_MNB = rs.getString("LIB_MNB");
				LIB_ETB = rs.getString("LIB_ETB");
				LIB_DEP = rs.getString("LIB_DEP");
				DAA_OBT_BAC_IBA = rs.getString("DAA_OBT_BAC_IBA");

				String texte = COD_IND + ";" + LIB_BAC + ";" + LIB_MNB + ";"
						+ LIB_ETB + ";" + LIB_DEP + ";" + DAA_OBT_BAC_IBA + ";"
						+ datsychr + " \n";

				texte = texte.replace("null", "---");
				String stre2 = new String(texte.getBytes(Charset
						.forName("ISO-8859-9")));
				// System.out.println(stre2);

				try {
					writer.write(stre2, 0, stre2.length());
				} catch (IOException ex) {
					ex.printStackTrace();
				}

			}

			try {
				writer.close();
			} catch (IOException e) {
				getLogger().error("Error while closing file 'bac.txt' ",e);
			}

			System.out.println("Insertion Bac");
			PreparedStatement Pindividu = getConnection("mysql")
					.prepareStatement("LOAD DATA LOCAL INFILE '"
							+ Config.workingDir.replace("\\", "/")
							+ "/ficher/Bac.txt' "
							+ "INTO TABLE bac "
							+ "FIELDS "
							+ "TERMINATED BY ';' "
							+ "ESCAPED BY '\\\\' LINES STARTING BY '' "
							+ "TERMINATED BY '\\n' "
							+ "(cod_ind, serie, mention, etablissmenet, province, date, datesync) ");
			Pindividu.executeUpdate();
			System.out.println("Fin Insertion Bac");

			closeConnection("mysql");
			closeConnection("oracle");

		} catch (SQLException e) {
			getLogger().error("Error at Synchronisation of Bac Table ",e);

		} finally {

		}

	}


}
