package uit.ent.synchronizer.table;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import uit.ent.synchronizer.Config;
import uit.ent.synchronizer.Statics;
import uit.ent.synchronizer.table.generic.Synchronizable;

public class Cursus_v2 extends Synchronizable {
	
	private static int nbrOfIndividusInBatch = 0;
	
	private static String LIS_ELP_COD_ANU;
	private static String LIS_ELP_COD_ANU_PLUS_1;
	private static String LIS_ELP_COD_NEL;
	private static String LIS_RNG_ELP;
	private static String LIS_ELP_LIC_NEL;
	private static String LIS_ELP_COD_ELP;
	private static String LIS_ELP_COD_LSE;
	private static String LIS_ELP_COD_ETP;
	private static String LIS_ELP_LIB_ELP;
	private static String LIS_COD_IND;
	private static String not_elp;
	private static String cod_tre_elp;
	private static String cod_ses_elp;
	private static FileWriter writer;
	

	public void synchronize(String dateanne, String datsychr)
			throws SQLException {
		
		int n = 0;
		for(int codeIndividu: Individu.listCodesIndividus){
			if(Cursus_v2.nbrOfIndividusInBatch == 0 && n >= Config.START_FROM_INDIVIDU_NBR){ 
				// nouveau batch(lot) d'un load infile
				System.out.println("Cursus : New Batch (Use '" + (n)+"' in Config.START_FROM_INDIVIDU_NBR to continue sychronisation from this point) ");
			}
			if(n >= Config.START_FROM_INDIVIDU_NBR){
				syncIndividu(codeIndividu, datsychr);
				System.out.println("Cursus: Individu n° "  +n + " traité !");
			}
			n++;
		}
		
		closeConnection("mysql");

	}

	public void syncIndividu(int codeIndividu, String datsychr) {
		
		if(Cursus_v2.nbrOfIndividusInBatch == 0){ // nouveau batch(lot) d'un load infile
			System.out.println("Cursus : New Batch ");
			try {
				writer = new FileWriter(Statics.workingDir.replace("\\", "/")
						+ "/ficher/cursus_v2.txt", false);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		Cursus_v2.nbrOfIndividusInBatch++;

		PreparedStatement preparedStatement = null;
		String selectSQL = "SELECT LIS_ELP_COD_ANU,LIS_ELP_COD_ANU_PLUS_1,LIS_ELP_COD_NEL,RNG_ELP,LIS_ELP_LIC_NEL,LIS_ELP_COD_ELP,LIS_ELP_COD_LSE, LIS_ELP_COD_ETP,LIS_ELP_LIB_ELP,LIS_COD_IND,not_elp,cod_tre_elp,cod_ses_elp  FROM "
				+ "(SELECT "
				+ "ICE.COD_ANU LIS_ELP_COD_ANU,	"
				+ "ICE.COD_ANU+1 LIS_ELP_COD_ANU_PLUS_1, "
				+ "NEL.COD_NEL LIS_ELP_COD_NEL,	"
				+ "NEL.LIC_NEL LIS_ELP_LIC_NEL,	"
				+ "ICE.COD_ELP LIS_ELP_COD_ELP,	"
				+ "ICE.COD_LSE LIS_ELP_COD_LSE, "
				+ "ICE.COD_ETP LIS_ELP_COD_ETP, "
				+ "ELP.LIB_ELP LIS_ELP_LIB_ELP,	"
				+ "IND.COD_IND LIS_COD_IND, "
				+ "ICE.RNG_ELP RNG_ELP "
				+ "FROM	"
				+ "INDIVIDU IND, "
				+ "IND_CONTRAT_ELP ICE,	"
				+ "ELEMENT_PEDAGOGI ELP, "
				+ "NATURE_ELP NEL,	"
				+ "INS_ADM_ANU IAA	"
				+ "WHERE (ICE.COD_IND(+)  = IND.COD_IND)	"
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
				// + "AND IND.COD_IND = '87810' "
				// + "AND (ELP.COD_NEL LIKE '%SM0%' OR ELP.COD_NEL LIKE '%MOD%') "
				+ "AND ELP.COD_NEL NOT LIKE 'CP%' "
				+ "GROUP BY ICE.COD_ANU, "
				+ "NEL.COD_NEL , "
				+ "NEL.LIC_NEL , "
				+ "ICE.COD_ELP , "
				+ "ICE.COD_LSE, "
				+ "ICE.COD_ETP, "
				+ "ELP.LIB_ELP , "
				+ "IND.COD_IND, "
				+ "ICE.RNG_ELP "
				+ "ORDER BY ICE.COD_ANU DESC, "
				+ "ICE.COD_ELP), "
				+ "(select cod_ind as cod_ind_res,cod_anu as cod_anu_elp,cod_elp as cod_elp_res,not_elp as not_elp,cod_tre as cod_tre_elp,cod_ses as cod_ses_elp "
				+ "from resultat_elp "
				+ "where TEM_NOT_RPT_ELP='N' "
				+ "and cod_adm = 1 "
				//+ "and (not_elp is not null  OR (not_elp is null AND cod_tre = 'ABI' ) ) "
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
			
			getConnection("mysql").createStatement();
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				LIS_ELP_COD_ANU = rs.getString("LIS_ELP_COD_ANU");
				LIS_ELP_COD_ANU_PLUS_1 = rs.getString("LIS_ELP_COD_ANU_PLUS_1");
				LIS_ELP_COD_NEL = rs.getString("LIS_ELP_COD_NEL");
				LIS_RNG_ELP = rs.getString("RNG_ELP");
				LIS_ELP_LIC_NEL = rs.getString("LIS_ELP_LIC_NEL");
				LIS_ELP_COD_ELP = rs.getString("LIS_ELP_COD_ELP");
				LIS_ELP_COD_LSE = rs.getString("LIS_ELP_COD_LSE");
				LIS_ELP_COD_ETP = rs.getString("LIS_ELP_COD_ETP");
				LIS_ELP_LIB_ELP = (rs.getString("LIS_ELP_LIB_ELP") != null)?rs.getString("LIS_ELP_LIB_ELP").replace(";", "").replaceAll(
						"[\r\n]+", " "):"";
				LIS_COD_IND = rs.getString("LIS_COD_IND");
				not_elp = rs.getString("not_elp");
				cod_tre_elp = rs.getString("cod_tre_elp");
				cod_ses_elp = rs.getString("cod_ses_elp");

				String texte = LIS_ELP_COD_ANU + ";" + LIS_ELP_COD_ANU_PLUS_1 + ";" + LIS_ELP_COD_NEL  + ";" + LIS_RNG_ELP
						+ ";" + LIS_ELP_LIC_NEL + ";" + LIS_ELP_COD_ELP + ";" + LIS_ELP_COD_LSE + ";" + LIS_ELP_COD_ETP + ";"
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

			if(Cursus_v2.nbrOfIndividusInBatch == Config.LOAD_IN_FILE_BATCH_QTY || codeIndividu == Integer.valueOf(Individu.listCodesIndividus.get(Individu.listCodesIndividus.size() - 1)) ) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	
				System.out.println("Cursus : Insertion of Batch ");
				
				PreparedStatement statementLoadInfile = getConnection("mysql")
						.prepareStatement("LOAD DATA LOCAL INFILE '"
								+ Statics.workingDir.replace("\\", "/")
								+ "/ficher/cursus_v2.txt' "
								+ "INTO TABLE cursusresultat_v2 "
								+ "FIELDS "
								+ "TERMINATED BY ';' "
								+ "ESCAPED BY '\\\\' LINES STARTING BY '' "
								+ "TERMINATED BY '\\n' "
								+ " (cod_annee, cod_annee2, cod_nel, rng_elp, lib_lse, cod_elp, cod_lse, cod_etp, lib_elp, cod_ind, note, resultat, cod_session, datesync) ");
				statementLoadInfile.executeUpdate();
				statementLoadInfile.close();
				Cursus_v2.nbrOfIndividusInBatch = 0;
			}
			//System.out.println("Fin Insertion Cursus");
			preparedStatement.close();
			
		} catch (SQLException e) {
			getLogger().error("Error while Synchronizing " + getClass().getName(), e);
			System.exit(-1); 
		} finally {
			
		}
	}

}
