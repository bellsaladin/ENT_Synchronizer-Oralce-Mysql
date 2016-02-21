package uit.ent.synchronizer.table;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import uit.ent.synchronizer.Config;
import uit.ent.synchronizer.table.generic.Synchronizable;

public class AnneeUni extends Synchronizable {

	private static int COD_ANU;
	private static String ETA_ANU_IAE;
	private static String LIB_ANU;
	private static String LIC_ANU;
	private static String DAT_OUV_OPI;
	private static String DAT_FRM_OPI;
	private static String ETA_ANU_IPE;
	private static String ETA_ANU_RES;
	private static FileWriter writer;

	public void synchronize(String datsychr) throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String selectSQL = "SELECT * FROM ANNEE_UNI";
		try {
			preparedStatement = getConnection("oracle").prepareStatement(selectSQL);

			getConnection("mysql").createStatement();

			ResultSet rs = preparedStatement.executeQuery();

			try {
				writer = new FileWriter(Config.workingDir.replace("\\", "/")
						+ "/ficher/annee_uni.txt", false);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			while (rs.next()) {

				COD_ANU = rs.getInt("COD_ANU");
				ETA_ANU_IAE = rs.getString("ETA_ANU_IAE");
				LIB_ANU = rs.getString("LIB_ANU");
				LIC_ANU = rs.getString("LIC_ANU");
				DAT_OUV_OPI = rs.getString("DAT_OUV_OPI");
				DAT_FRM_OPI = rs.getString("DAT_FRM_OPI");
				ETA_ANU_IPE = rs.getString("ETA_ANU_IPE");
				ETA_ANU_RES = rs.getString("ETA_ANU_RES");

				String query3 = "SELECT * FROM annee_uni Where cod_anu = ?";
				PreparedStatement prest =  getConnection("mysql").prepareStatement(query3);
				prest.setInt(1, COD_ANU);
				prest.executeQuery();

				String texte = COD_ANU + ";" + ETA_ANU_IAE + ";" + LIB_ANU
						+ ";" + LIC_ANU + ";" + DAT_OUV_OPI + ";" + DAT_FRM_OPI
						+ ";" + ETA_ANU_IPE + ";" + ETA_ANU_RES + ";"
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
			
			closeConnection("oracle");
			

			PreparedStatement Pindividu = getConnection("mysql")
					.prepareStatement("LOAD DATA LOCAL INFILE '"
							+ Config.workingDir.replace("\\", "/")
							+ "/ficher/annee_uni.txt' "
							+ "INTO TABLE annee_uni "
							+ "FIELDS "
							+ "TERMINATED BY ';' "
							+ "ESCAPED BY '\\\\' LINES STARTING BY '' "
							+ "TERMINATED BY '\\n' "
							+ "(cod_anu, eta_anu_iae, lib_anu, lic_anu, dat_ouv_opi, dat_frm_opi, eta_anu_ipe, eta_anu_res, datesync) ");
			Pindividu.executeUpdate();

			closeConnection("mysql");

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

	}

}
