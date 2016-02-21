package uit.ent.synchronizer.table;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import uit.ent.synchronizer.Config;
import uit.ent.synchronizer.table.generic.Synchronizable;

public class CursusAnnee extends Synchronizable{
	private static int nbrOfIndividusInBatch = 0;
	private static String LIS_ELP_COD_ANU;
	private static String LIS_ELP_LIC_NEL;
	private static String LIS_ELP_COD_ELP;
	private static String LIS_ELP_LIB_ELP;
	private static String LIS_COD_IND;
	private static String COD_GPE_IAG;
	private static String COD_ANU_IAG;
	private static String COD_EXT_GPE;
	private static String COD_COL;
	private static FileWriter writer;
	private static int n;

	public void synchronize(String dateanne, String datsychr)
			throws SQLException {
		for(int codeIndividu: Individu.listCodesIndividus){
			syncIndividu(codeIndividu, datsychr, dateanne);
			System.out.println("Cursus annee :"+ n);
			n++;
		}
	}

	public void syncIndividu(int codeIndividu, String datsychr, String dateanne) {
		// nouveau lot d'un load infile
		if(CursusAnnee.nbrOfIndividusInBatch == 0){ 
			System.out.println("Cursus annee Nouveau lot ");
			try {
				writer = new FileWriter(Config.workingDir.replace("\\", "/")
						+ "/ficher/cursusannee.txt", false);
			} catch (IOException e) {
				getLogger().error("Error while opening file 'cursusannee.txt' ",e);
			}
		}
		
		CursusAnnee.nbrOfIndividusInBatch++;

		PreparedStatement preparedStatement = null;

		String selectSQL = "select LIS_ELP_COD_ANU,LIS_ELP_COD_ANU_PLUS_1,LIS_ELP_LIC_NEL,LIS_ELP_COD_ELP,LIS_ELP_LIB_ELP,LIS_COD_IND,not_elp,cod_tre_elp,cod_ses_elp,COD_GPE_IAG,COD_ANU_IAG,COD_EXT_GPE,COD_COL from "
				+ "(SELECT "
				+ "ICE.COD_ANU LIS_ELP_COD_ANU,	"
				+ "ICE.COD_ANU+1 LIS_ELP_COD_ANU_PLUS_1, "
				+ "NEL.LIC_NEL LIS_ELP_LIC_NEL,	"
				+ "ICE.COD_ELP LIS_ELP_COD_ELP, "
				+ "ELP.LIB_ELP LIS_ELP_LIB_ELP, "
				+ "IND.COD_IND LIS_COD_IND,"
				+ "IAG.cod_gpe COD_GPE_IAG,"
				+ "IAG.cod_anu COD_ANU_IAG,"
				+ "GPE.COD_EXT_GPE COD_EXT_GPE,"
				+ "GPE.COD_COL COD_COL "
				//+ "COLL.COD_EXT_COL COD_EXT_COL "
				+ "FROM	"
				+ "INDIVIDU IND, "
				+ "IND_CONTRAT_ELP ICE,	"
				+ "ELEMENT_PEDAGOGI ELP, "
				+ "IND_AFFECTE_GPE IAG, "
				+ "GROUPE GPE, "
				//+ "COLLECTION COLL, "
				+ "NATURE_ELP NEL, "
				+ "INS_ADM_ANU IAA "
				+ "WHERE "
				+ "ICE.cod_ind = IAG.cod_ind "
				+ "AND ICE.cod_anu = IAG.COD_ANU "
				+ "AND IAG.cod_gpe = GPE.COD_GPE "
				//+ "AND GPE.COD_COL = COLL.COD_COL "
				+ "AND ICE.cod_anu = "
				+ dateanne + " "
				+ "AND (ICE.COD_IND(+)  = IND.COD_IND) "
				+ "AND (ICE.TEM_PRC_ICE = 'N') "
				+ "AND (ELP.COD_ELP(+) = ICE.COD_ELP) "
				+ "AND (NEL.COD_NEL(+) = ELP.COD_NEL) AND IAA.COD_IND = "
				+ codeIndividu + " "
				+ "AND IAA.ETA_IAA = 'E' "
				+ "AND IND.COD_IND = "
				+ codeIndividu + " "
				+ "AND (ELP.COD_NEL LIKE '%SM0%' OR ELP.COD_NEL LIKE '%MOD%') "
				+ "AND (ICE.COD_ELP NOT LIKE 'CP%') "
				+ "group by ICE.COD_ANU, "
				+ "NEL.LIC_NEL, "
				+ "ICE.COD_ELP, "
				+ "ELP.LIB_ELP, "
				+ "IND.COD_IND, IAG.cod_gpe ,IAG.cod_anu,GPE.COD_EXT_GPE,GPE.COD_COL "
				+ "ORDER BY ICE.COD_ANU DESC, "
				+ "ICE.COD_ELP), "
				+ "(select cod_ind as cod_ind_res,cod_anu as cod_anu_elp,cod_elp as cod_elp_res,not_elp as not_elp,cod_tre as cod_tre_elp,cod_ses as cod_ses_elp "
				+ "from resultat_elp "
				+ "where TEM_NOT_RPT_ELP='N' "
				+ "and cod_adm= 1 "
				+ "and cod_ind= "
				+ codeIndividu + " "
				+ "group by cod_ind,cod_elp,not_elp,cod_tre,cod_ses,cod_anu) "
				+ "where LIS_COD_IND=cod_ind_res "
				+ "and LIS_ELP_COD_ANU=cod_anu_elp "
				+ "and LIS_ELP_COD_ANU= "
				+ dateanne + " "
				+ "and cod_ses_elp ='1' "
				+ "and cod_elp_res=LIS_ELP_COD_ELP "
				+ "ORDER BY  LIS_ELP_COD_ELP ASC";
		//System.out.println("youness::selectSQL : " + selectSQL);
		try {
			
			preparedStatement = getConnection("oracle").prepareStatement(selectSQL);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				LIS_ELP_COD_ANU = rs.getString("LIS_ELP_COD_ANU");
				LIS_ELP_LIC_NEL = rs.getString("LIS_ELP_LIC_NEL");
				LIS_ELP_COD_ELP = rs.getString("LIS_ELP_COD_ELP");
				LIS_ELP_LIB_ELP = rs.getString("LIS_ELP_LIB_ELP").replaceAll(
						"[\r\n]+", "");
				LIS_COD_IND = rs.getString("LIS_COD_IND");
				COD_GPE_IAG = rs.getString("COD_GPE_IAG");
				COD_ANU_IAG = rs.getString("COD_ANU_IAG");
				COD_EXT_GPE = rs.getString("COD_EXT_GPE");
				COD_COL = rs.getString("COD_COL");
				
				String texte = LIS_ELP_COD_ANU + ";" + LIS_ELP_LIC_NEL + ";"
						+ LIS_ELP_COD_ELP + ";" + LIS_ELP_LIB_ELP + ";"
						+ LIS_COD_IND + ";" + COD_GPE_IAG + ";"
						+ COD_ANU_IAG + ";" + COD_EXT_GPE + ";"
						+ COD_COL + ";"+ datsychr + " \n";

				texte = texte.replace("null", "---");
				String stre2 = new String(texte.getBytes(Charset
						.forName("ISO-8859-9")));
				try {
					writer.write(stre2, 0, stre2.length());
				} catch (IOException e) {
					getLogger().error("Error while writing at file 'cursusannee.txt'",e);
				}		
			}
			
			if(CursusAnnee.nbrOfIndividusInBatch == Config.LOAD_IN_FILE_BATCH_QTY || codeIndividu == Integer.valueOf(Individu.listCodesIndividus.get(Individu.listCodesIndividus.size() - 1)) ){
				try {
					writer.close();
				} catch (IOException e) {
					System.err.println("Exception : " + e.getMessage());
				}
				
				System.out.println("Cursus annee Insertion lot ");
				
				PreparedStatement statementLoadInfile = getConnection("mysql")
						.prepareStatement("LOAD DATA LOCAL INFILE '"
								+ Config.workingDir.replace("\\", "/")
								+ "/ficher/cursusannee.txt' "
								+ "INTO TABLE cursusannee "
								+ "FIELDS "
								+ "TERMINATED BY ';' "
								+ "ESCAPED BY '\\\\' LINES STARTING BY '' "
								+ "TERMINATED BY '\\n' "
								+ " (cod_annee, lib_lse, cod_elp, lib_elp, cod_ind,COD_GPE_IAG,COD_ANU_IAG,COD_EXT_GPE,COD_COL, datesync) ");
				statementLoadInfile.executeUpdate();
				statementLoadInfile.close();
				CursusAnnee.nbrOfIndividusInBatch = 0;	
			}
			preparedStatement.close();

		} catch (SQLException e) {
			getLogger().error("Error while Synchronizing " + getClass().getName(), e);
			System.exit(-1); 
		} finally {
			
		}
	}

}
