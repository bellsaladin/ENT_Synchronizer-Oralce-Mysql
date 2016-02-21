package uit.ent.synchronizer.table;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import uit.ent.synchronizer.Config;
import uit.ent.synchronizer.table.generic.Synchronizable;

public class FosUser extends Synchronizable {

	private static String COD_NNE_IND;
	private static String cod_apogee;
	private static FileWriter writer;
	String polecenie;

	public void synchronize(String dateanne, String datsychr)
			throws SQLException {

		PreparedStatement preparedStatement = null;

		String selectSQL = "SELECT DISTINCT INDIVIDU.COD_NNE_IND, "
				+ "INDIVIDU.COD_ETU " + "FROM INDIVIDU "
				+ "FULL JOIN INS_ADM_ETP "
				+ "ON INDIVIDU.COD_IND = INS_ADM_ETP.COD_IND "
				+ "WHERE INS_ADM_ETP.COD_ANU = " + dateanne
				+ "AND INS_ADM_ETP.ETA_IAE = 'E'";

		try {
			preparedStatement = getConnection("oracle").prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();
			try {
				writer = new FileWriter(Config.workingDir.replace("\\", "/")
						+ "/ficher/fos_user.txt", false);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			while (rs.next()) {
				COD_NNE_IND = rs.getString("COD_NNE_IND");
				cod_apogee = rs.getString("COD_ETU");

				String passfos = getMD5(COD_NNE_IND);
				//System.out.println(passfos);

				String texte = cod_apogee + ";" + cod_apogee + ";" + cod_apogee
						+ ";" + cod_apogee + ";" + cod_apogee
						+ ";4sw9a67pdim8s8g04ws808sswosk40s;" + passfos
						+ ";a:0:{};" + datsychr + " \n";
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

			System.out.println("Insertion Fosuser");

			PreparedStatement loadInFileStatement = getConnection("mysql")
					.prepareStatement("LOAD DATA LOCAL INFILE '"
							+ Config.workingDir.replace("\\", "/")
							+ "/ficher/fos_user.txt' "
							+ "INTO TABLE fos_user "
							+ "FIELDS "
							+ "TERMINATED BY ';' "
							+ "ESCAPED BY '\\\\' LINES STARTING BY '' "
							+ "TERMINATED BY '\\n' "
							+ "(id, username, username_canonical, email, email_canonical, salt, password, roles, datesync)");
			loadInFileStatement.executeUpdate();

			System.out.println("Fin Insertion Fosuser");


		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {
			closeConnection("oracle");
			closeConnection("mysql");
		}

	}
	
	private String getMD5(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
}
