package uit.ent.synchronizer.table;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import uit.ent.synchronizer.Config;
import uit.ent.synchronizer.table.generic.Synchronizable;

public class Cursus extends Synchronizable{

	private static int loadInFileIndividusCount = 0;
	private static String LIS_ELP_COD_ANU;
	private static String LIS_ELP_COD_ANU_PLUS_1;
	private static String LIS_ELP_LIC_NEL;
	private static String LIS_ELP_COD_ELP;
	private static String LIS_ELP_LIB_ELP;
	private static String LIS_COD_IND;
	private static String not_elp;
	private static String cod_tre_elp;
	private static String cod_ses_elp;
	private static FileWriter writer;

	public void TableCursus(String dateanne, String datsychr)
			throws SQLException {
		
		for(int codeIndividu: Individu.listCodesIndividus){
			syncIndividu(codeIndividu, datsychr);
			//System.out.println("Cursus:Individu: "  +n);
		}

	}

	public void syncIndividu(int codeIndividu, String datsychr) {
		
		if(Cursus.loadInFileIndividusCount == 0){ // nouveau lot d'un load infile
			System.out.println("Cursus Nouveau lot ");
			try {
				writer = new FileWriter(Config.workingDir.replace("\\", "/")
						+ "/ficher/cursus.txt", false);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		Cursus.loadInFileIndividusCount++;

		PreparedStatement preparedStatement = null;
		
		String selectSQL = "select LIS_ELP_COD_ANU,LIS_ELP_COD_ANU_PLUS_1,LIS_ELP_LIC_NEL,LIS_ELP_COD_ELP,LIS_ELP_LIB_ELP,LIS_COD_IND,not_elp,cod_tre_elp,cod_ses_elp from "
				+ "(SELECT "
				+ "ICE.COD_ANU LIS_ELP_COD_ANU,	"
				+ "ICE.COD_ANU+1 LIS_ELP_COD_ANU_PLUS_1, "
				+ "NEL.LIC_NEL LIS_ELP_LIC_NEL,	"
				+ "ICE.COD_ELP LIS_ELP_COD_ELP,	"
				+ "ELP.LIB_ELP LIS_ELP_LIB_ELP,	"
				+ "IND.COD_IND LIS_COD_IND	"
				+ "FROM	"
				+ "INDIVIDU IND, "
				+ "IND_CONTRAT_ELP ICE,	"
				+ "ELEMENT_PEDAGOGI ELP, "
				+ "NATURE_ELP NEL,	"
				+ "INS_ADM_ANU IAA	"
				+ "WHERE 1=1	"
				+ "AND (ICE.COD_IND(+)  = IND.COD_IND)	"
				+ "AND (ICE.TEM_PRC_ICE = 'N') "
				+ "AND (ELP.COD_ELP(+) = ICE.COD_ELP)	"
				+ "AND (NEL.COD_NEL(+) = ELP.COD_NEL)	"
				// + "and ELP.cod_cmp='FLK' "
				+ "AND IAA.COD_IND = "
				+ codeIndividu
				// + "AND IAA.COD_IND = '87810' "
				+ "AND IAA.ETA_IAA = 'E' "
				+ "AND IND.COD_IND = "
				+ codeIndividu
				+ "AND (ICE.COD_ELP NOT LIKE 'CP%') "
				// + "AND IND.COD_IND = '87810' "
				+ "group by ICE.COD_ANU, "
				+ "NEL.LIC_NEL , "
				+ "ICE.COD_ELP , "
				+ "ELP.LIB_ELP , "
				+ "IND.COD_IND "
				+ "ORDER BY ICE.COD_ANU DESC, "
				+ "ICE.COD_ELP), "
				+ "(select cod_ind as cod_ind_res,cod_anu as cod_anu_elp,cod_elp as cod_elp_res,not_elp as not_elp,cod_tre as cod_tre_elp,cod_ses as cod_ses_elp "
				+ "from resultat_elp "
				+ "where TEM_NOT_RPT_ELP='N' "
				+ "and cod_adm=1 "
				+ "and (not_elp is not null  OR (not_elp is null AND cod_tre = 'ABI' ) ) "
				+ "and cod_ind= "
				+ codeIndividu
				// + "and cod_ind= '87810' "
				+ "group by cod_ind,cod_elp,not_elp,cod_tre,cod_ses,cod_anu) "
				+ "where LIS_COD_IND=cod_ind_res "
				+ "and LIS_ELP_COD_ANU=cod_anu_elp "
				+ "and cod_elp_res=LIS_ELP_COD_ELP "
				+ "ORDER BY  LIS_ELP_COD_ELP ASC";
		System.out.println("Cursus:selectSQL:" + selectSQL);
		try {
			preparedStatement = getConnection("oracle").prepareStatement(selectSQL);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {

				LIS_ELP_COD_ANU = rs.getString("LIS_ELP_COD_ANU");
				LIS_ELP_COD_ANU_PLUS_1 = rs.getString("LIS_ELP_COD_ANU_PLUS_1");
				LIS_ELP_LIC_NEL = rs.getString("LIS_ELP_LIC_NEL");
				LIS_ELP_COD_ELP = rs.getString("LIS_ELP_COD_ELP");
				LIS_ELP_LIB_ELP = rs.getString("LIS_ELP_LIB_ELP").replaceAll(
						"[\r\n]+", "");
				LIS_COD_IND = rs.getString("LIS_COD_IND");
				not_elp = rs.getString("not_elp");
				cod_tre_elp = rs.getString("cod_tre_elp");
				cod_ses_elp = rs.getString("cod_ses_elp");

				String texte = LIS_ELP_COD_ANU + ";" + LIS_ELP_COD_ANU_PLUS_1
						+ ";" + LIS_ELP_LIC_NEL + ";" + LIS_ELP_COD_ELP + ";"
						+ LIS_ELP_LIB_ELP + ";" + LIS_COD_IND + ";" + not_elp
						+ ";" + cod_tre_elp + ";" + cod_ses_elp + ";"
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

			if(Cursus.loadInFileIndividusCount == Config.LOAD_IN_FILE_BATCH_QTY || codeIndividu == Integer.valueOf(Individu.listCodesIndividus.get(Individu.listCodesIndividus.size() - 1)) ){
				
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				System.out.println("Cursus Insertion lot ");
				
				PreparedStatement Pindividu = getConnection("mysql")
						.prepareStatement("LOAD DATA LOCAL INFILE '"
								+ Config.workingDir.replace("\\", "/")
								+ "/ficher/cursus.txt' "
								+ "INTO TABLE cursusresultat "
								+ "FIELDS "
								+ "TERMINATED BY ';' "
								+ "ESCAPED BY '\\\\' LINES STARTING BY '' "
								+ "TERMINATED BY '\\n' "
								+ " (cod_annee, cod_annee2, lib_lse, cod_elp, lib_elp, cod_ind, note, resultat, cod_session, datesync) ");
				Pindividu.executeUpdate();
				Cursus.loadInFileIndividusCount = 0;
			}
			//System.out.println("Fin Insertion Cursus");
			
			closeConnection("oracle");
			closeConnection("mysql");

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Exception : " + e.getMessage());
		} finally {

		}
	}

}
