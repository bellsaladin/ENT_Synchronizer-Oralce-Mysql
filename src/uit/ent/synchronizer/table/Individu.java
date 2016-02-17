package uit.ent.synchronizer.table;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import uit.ent.synchronizer.Config;
import uit.ent.synchronizer.Statics;
import uit.ent.synchronizer.table.generic.Synchronizable;

public class Individu extends Synchronizable{

	public static List<Integer> listCodesIndividus = new ArrayList<Integer>();
	
	private static int    COD_IND;
	private static String CIN_IND;
	private static String LIB_NOM_PAT_IND;
	private static String LIB_PR1_IND;
	private static String COD_NNE_IND;
	private static String DATE_NAI_IND;
	private static String LIB_VIL_NAI_ETU;
	private static String LIB_PAY;
	private static String COD_SEX_ETU;
	private static String LIB_FAM;
	private static String LIB_THP;
	private static String preinscriptionensigsuper;
	private static String preinscriptidansetablimarocain;
	private static String LIB_ETB;
	private static String preinscridansetablis;
	private static String adress2fix;
	private static String adressfix;
	private static String payefix;
	private static String villefix;
	private static String telefix;
	private static String adressfix2;
	private static String adress2fix2;
	private static String payefix2;
	private static String villefix2;
	private static String telefix2;
	private static String LIB_NOM_IND_ARB;
	private static String LIB_PRN_IND_ARB;
	private static int    COD_ANU_INA;
	private static String nationaissan;
	private static String provadressfix;
	private static String comunadressfix;
	private static String provadressfix2;
	private static String comunadressfix2;
	private static String departementnaissance;
	private static String cod_apogee;
	private static String lib_etablissement;
	private static String lic_etablissement;
	private static String cod_postal_fix;
	private static String cod_postal_anull;
	private static String lib_ttr;
	private static String lib_cmp_arb;
	private static String lic_cmp_arb;
	private static String lib_ad1_cmp;
	private static String lib_ad2_cmp;
	private static String lib_ad3_cmp;
	private static FileWriter writer;

	public void synchronize(String dateanne, String datsychr)
			throws SQLException {

		PreparedStatement preparedStatement = null;
		try {
			getConnection("mysql").createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String selectSQL = "SELECT DISTINCT INDIVIDU.COD_IND, "
				+ "INDIVIDU.LIB_NOM_PAT_IND, " + "INDIVIDU.LIB_PR1_IND, "
				+ "INDIVIDU.CIN_IND, " + "INDIVIDU.COD_NNE_IND, "
				+ "INDIVIDU.DATE_NAI_IND, " + "INDIVIDU.LIB_VIL_NAI_ETU, "
				+ "PAYS.LIB_PAY AS nationalite, " + "INDIVIDU.COD_SEX_ETU, "
				+ "SIT_FAM.LIB_FAM, " + "TYP_HANDICAP.LIB_THP, "
				+ "INDIVIDU.DAA_ENS_SUP AS preinscriptionensigsuper, "
				+ "INDIVIDU.DAA_ENT_ETB AS preinscriptidansetablimarocain, "
				+ "ETABLISSEMENT.LIB_ETB, "
				+ "INDIVIDU.DAA_ETB AS preinscridansetablis, "
				+ "a1.LIB_AD1 AS adressfix, " + "a1.LIB_AD3 AS adress2fix, "
				+ "p.LIB_PAY  AS payefix, " + "a1.LIB_AD2 AS villefix, "
				+ "a1.NUM_TEL AS telefix, " + "a2.LIB_AD1 AS adressfix2, "
				+ "a2.LIB_AD3 AS adress2fix2, " + "p1.LIB_PAY AS payefix2, "
				+ "a2.LIB_AD2 AS villefix2, " + "a2.NUM_TEL AS telefix2, "
				+ "INDIVIDU.LIB_NOM_IND_ARB, " + "INDIVIDU.LIB_PRN_IND_ARB, "
				+ "INDIVIDU.LIB_VIL_NAI_ETU_ARB, " + "INS_ADM_ETP.COD_ANU, "
				+ "PAYS.LIB_NAT AS nationaissan, "
				+ "DEPARTEMENT.LIB_DEP  AS provadressfix, "
				+ "COMMUNE.LIB_COM AS comunadressfix, "
				+ "COMMUNE1.LIB_COM AS provadressfix2, "
				+ "DEPARTEMENT1.LIB_DEP AS comunadressfix2, "
				+ "DEPARTEMENT2.LIB_DEP AS departementnaissance, "
				+ "PAYS1.LIB_PAY AS paysnaissance, " + "INDIVIDU.COD_ETU, "
				+ "COMPOSANTE.INT_1_EDI_DIP_CMP, " + "COMPOSANTE.LIC_CMP, "
				+ "COMMUNE.COD_COM, " + "COMMUNE1.COD_COM AS cod_pos_anu, "
				+ "COMPOSANTE.lib_ttr, " + "COMPOSANTE.lib_cmp_arb, "
				+ "COMPOSANTE.lic_cmp_arb, " + "COMPOSANTE.lib_ad1_cmp, "
				+ "COMPOSANTE.lib_ad2_cmp, " + "COMPOSANTE.lib_ad3_cmp "
				+ "FROM INDIVIDU " + "FULL JOIN ADRESSE a1 "
				+ "ON INDIVIDU.COD_IND = a1.COD_IND " + "FULL JOIN PAYS p "
				+ "ON p.COD_PAY = a1.COD_PAY " + "FULL JOIN PAYS "
				+ "ON PAYS.COD_PAY = INDIVIDU.COD_PAY_NAT "
				+ "FULL JOIN ADRESSE a2 " + "ON a1.COD_IND = a2.COD_IND_INA "
				+ "FULL JOIN PAYS p1 " + "ON a2.COD_PAY = p1.COD_PAY "
				+ "FULL JOIN INS_ADM_ETP "
				+ "ON INDIVIDU.COD_IND = INS_ADM_ETP.COD_IND "
				+ "FULL JOIN COMPOSANTE "
				+ "ON INS_ADM_ETP.COD_CMP = COMPOSANTE.COD_CMP "
				+ "FULL JOIN SIT_FAM "
				+ "ON SIT_FAM.COD_FAM = INDIVIDU.COD_FAM "
				+ "FULL JOIN TYP_HANDICAP "
				+ "ON TYP_HANDICAP.COD_THP = INDIVIDU.COD_THP "
				+ "FULL JOIN ETABLISSEMENT "
				+ "ON ETABLISSEMENT.COD_ETB = INDIVIDU.COD_ETB "
				+ "FULL JOIN DEPARTEMENT "
				+ "ON DEPARTEMENT.COD_DEP = a1.COD_DEP "
				+ "FULL JOIN DEPARTEMENT DEPARTEMENT1 "
				+ "ON DEPARTEMENT1.COD_DEP = a2.COD_DEP "
				+ "FULL JOIN COMMUNE " + "ON COMMUNE.COD_COM = a1.COD_COM "
				+ "FULL JOIN COMMUNE COMMUNE1 "
				+ "ON COMMUNE1.COD_COM = a2.COD_COM "
				+ "FULL JOIN DEPARTEMENT DEPARTEMENT2 "
				+ "ON DEPARTEMENT2.COD_DEP = INDIVIDU.COD_DEP_PAY_NAI "
				+ "FULL JOIN PAYS PAYS1 "
				+ "ON PAYS1.COD_PAY = INDIVIDU.COD_DEP_PAY_NAI "
				+ "WHERE INS_ADM_ETP.COD_ANU = " + dateanne
				+ " AND a2.COD_ANU_INA = " + dateanne
				+ " AND INS_ADM_ETP.ETA_IAE = 'E' ";
		
		//if(Config.TEST) selectSQL += " AND ROWNUM <= " + Config.LIMIT_NUMBER_OF_ROWS;
		//selectSQL += " ORDER BY INDIVIDU.COD_IND";
		System.out.println("Individu::selectSQL: " + selectSQL);
		try {
			preparedStatement = getConnection("oracle").prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();
			int i = 0;
			try {
				writer = new FileWriter(Statics.workingDir.replace("\\", "/")
						+ "/ficher/individu.txt", false);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			while (rs.next()) {
				COD_IND = rs.getInt("COD_IND");
				
				listCodesIndividus.add(COD_IND);
				
				LIB_NOM_PAT_IND = (rs.getString("LIB_NOM_PAT_IND") != null)?rs.getString("LIB_NOM_PAT_IND").replace(";", ""):"";
				LIB_PR1_IND =(rs.getString("LIB_PR1_IND") != null)?rs.getString("LIB_PR1_IND").replace(";", ""):"";
				CIN_IND =(rs.getString("CIN_IND") != null)?rs.getString("CIN_IND").replace(";", ""):"";
				COD_NNE_IND =(rs.getString("COD_NNE_IND") != null)?rs.getString("COD_NNE_IND").replace(";", ""):"";
				DATE_NAI_IND =(rs.getString("DATE_NAI_IND") != null)?rs.getString("DATE_NAI_IND").replace(";", ""):"";
				LIB_VIL_NAI_ETU =(rs.getString("LIB_VIL_NAI_ETU") != null)?rs.getString("LIB_VIL_NAI_ETU").replace(";", ""):"";
				LIB_PAY =(rs.getString("nationalite") != null)?rs.getString("nationalite").replace(";", ""):"";
				COD_SEX_ETU =(rs.getString("COD_SEX_ETU") != null)?rs.getString("COD_SEX_ETU").replace(";", ""):"";
				LIB_FAM =(rs.getString("LIB_FAM") != null)?rs.getString("LIB_FAM").replace(";", ""):"";
				LIB_THP =(rs.getString("LIB_THP") != null)?rs.getString("LIB_THP").replace(";", ""):"";
				preinscriptionensigsuper = (rs
						.getString("preinscriptionensigsuper") != null)?rs.getString("preinscriptionensigsuper").replace(";", ""):"";
				preinscriptidansetablimarocain = (rs.getString("preinscriptidansetablimarocain") != null)?rs.getString("preinscriptidansetablimarocain").replace(";", ""):"";
				LIB_ETB =(rs.getString("LIB_ETB") != null)?rs.getString("LIB_ETB").replace(";", ""):"";
				preinscridansetablis =(rs.getString("preinscridansetablis") != null)?rs.getString("preinscridansetablis").replace(";", ""):"";
				adressfix =(rs.getString("adressfix") != null)?rs.getString("adressfix").replace(";", ""):"";
				adress2fix =(rs.getString("adress2fix") != null)?rs.getString("adress2fix").replace(";", ""):"";
				payefix =(rs.getString("payefix") != null)?rs.getString("payefix").replace(";", ""):"";
				villefix =(rs.getString("villefix") != null)?rs.getString("villefix").replace(";", ""):"";
				telefix =(rs.getString("telefix") != null)?rs.getString("telefix").replace(";", ""):"";
				adressfix2 =(rs.getString("adressfix2") != null)?rs.getString("adressfix2").replace(";", ""):"";
				adress2fix2 =(rs.getString("adress2fix2") != null)?rs.getString("adress2fix2").replace(";", ""):"";
				payefix2 =(rs.getString("payefix2") != null)?rs.getString("payefix2").replace(";", ""):"";
				villefix2 =(rs.getString("villefix2") != null)?rs.getString("villefix2").replace(";", ""):"";
				telefix2 =(rs.getString("telefix2") != null)?rs.getString("telefix2").replace(";", ""):"";
				LIB_NOM_IND_ARB =(rs.getString("LIB_NOM_IND_ARB") != null)?rs.getString("LIB_NOM_IND_ARB").replace(";", ""):"";
				LIB_PRN_IND_ARB =(rs.getString("LIB_PRN_IND_ARB") != null)?rs.getString("LIB_PRN_IND_ARB").replace(";", ""):"";
				COD_ANU_INA = rs.getInt("COD_ANU");
				nationaissan =(rs.getString("nationaissan") != null)?rs.getString("nationaissan").replace(";", ""):"";
				provadressfix =(rs.getString("provadressfix") != null)?rs.getString("provadressfix").replace(";", ""):"";
				comunadressfix =(rs.getString("comunadressfix") != null)?rs.getString("comunadressfix").replace(";", ""):"";
				provadressfix2 =(rs.getString("provadressfix2") != null)?rs.getString("provadressfix2").replace(";", ""):"";
				comunadressfix2 =(rs.getString("comunadressfix2") != null)?rs.getString("comunadressfix2").replace(";", ""):"";
				departementnaissance =(rs.getString("departementnaissance") != null)?rs.getString("departementnaissance").replace(";", ""):"";
				cod_apogee =(rs.getString("COD_ETU") != null)?rs.getString("COD_ETU").replace(";", ""):"";
				lib_etablissement =(rs.getString("INT_1_EDI_DIP_CMP") != null)?rs.getString("INT_1_EDI_DIP_CMP").replace(";", ""):"";
				lic_etablissement =(rs.getString("LIC_CMP") != null)?rs.getString("LIC_CMP").replace(";", ""):"";
				cod_postal_fix =(rs.getString("COD_COM") != null)?rs.getString("COD_COM").replace(";", ""):"";
				cod_postal_anull =(rs.getString("cod_pos_anu") != null)?rs.getString("cod_pos_anu").replace(";", ""):"";
				lib_ttr =(rs.getString("lib_ttr") != null)?rs.getString("lib_ttr").replace(";", ""):"";
				lib_cmp_arb =(rs.getString("lib_cmp_arb") != null)?rs.getString("lib_cmp_arb").replace(";", ""):"";
				lic_cmp_arb =(rs.getString("lic_cmp_arb") != null)?rs.getString("lic_cmp_arb").replace(";", ""):"";
				lib_ad1_cmp =(rs.getString("lib_ad1_cmp") != null)?rs.getString("lib_ad1_cmp").replace(";", ""):"";
				lib_ad2_cmp =(rs.getString("lib_ad2_cmp") != null)?rs.getString("lib_ad2_cmp").replace(";", ""):"";
				lib_ad3_cmp =(rs.getString("lib_ad3_cmp") != null)?rs.getString("lib_ad3_cmp").replace(";", ""):"";

				String texte = COD_IND + ";" + LIB_NOM_PAT_IND + ";"
						+ LIB_PR1_IND + ";" + CIN_IND + ";" + COD_NNE_IND + ";"
						+ DATE_NAI_IND + ";" + LIB_VIL_NAI_ETU + ";" + LIB_PAY
						+ ";" + COD_SEX_ETU + ";" + LIB_FAM + ";" + LIB_THP
						+ ";" + preinscriptionensigsuper + ";"
						+ preinscriptidansetablimarocain + ";" + LIB_ETB + ";"
						+ preinscridansetablis + ";" + adressfix + " "
						+ adress2fix + ";" + payefix + ";" + villefix + ";"
						+ telefix + ";" + adressfix2 + " " + adress2fix2 + ";"
						+ payefix2 + ";" + villefix2 + ";" + telefix2 + ";"
						+ LIB_NOM_IND_ARB + ";" + LIB_PRN_IND_ARB + "; --- ;"
						+ COD_ANU_INA + ";" + nationaissan + ";"
						+ provadressfix + ";" + comunadressfix + ";"
						+ provadressfix2 + ";" + comunadressfix2 + ";"
						+ departementnaissance + ";" + cod_apogee + ";"
						+ lib_etablissement + ";" + lic_etablissement + ";"
						+ cod_apogee + ";" + cod_postal_fix + ";"
						+ cod_postal_anull + ";" + lib_ttr + ";" + lib_cmp_arb
						+ ";" + lic_cmp_arb + ";" + lib_ad1_cmp + ";"
						+ lib_ad2_cmp + ";" + lib_ad3_cmp + ";" + datsychr
						+ " \n";

				texte = texte.replace("null", "---");
				String stre2 = new String(texte.getBytes(Charset
						.forName("ISO-8859-9")));
				try {
					writer.write(stre2, 0, stre2.length());
				} catch (IOException ex) {
					ex.printStackTrace();
				}

				i++;
				
				if(Config.TEST && i >= Config.LIMIT_NUMBER_OF_ROWS ) break; //break while 
			}

			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			closeConnection("oracle");
			
			
			System.out.println("Insertion Individu");
			PreparedStatement loadInFileStatement = getConnection("mysql")
					.prepareStatement("LOAD DATA LOCAL INFILE '"
							+ Statics.workingDir.replace("\\", "/")
							+ "/ficher/individu.txt' "
							+ "INTO TABLE individu "
							+ "FIELDS "
							+ "TERMINATED BY ';' "
							+ "ESCAPED BY '\\\\' LINES STARTING BY '' "
							+ "TERMINATED BY '\\n' "
							+ " (cod_ind, nom, prenom, cin, cne, date_naissance, lieu_naissance, pays_naissance, sexe, situation_familiale, handicap, dans_ens_sup, en_unive_marocain, etablissement, dans_uitk, adresse_fix, pays_fix, ville_fix, ntelephone_fix, adresse_annuelle, pays_annuelle, ville_annuelle, ntelephone_annuelle, nom_arabe, prenom_arabe, lieu_naissance_arabe, cod_anu_ina, nationalite_naissance, province_fix, commun_fix, province_annuelle, commun_annuelle, departement_naissance, cod_apogee, libelle_long_etablissement, libelle_court_etablissement, username, cod_postal_fix, cod_postal_annuelle, lib_attestation, lib_etablissement_arabe, lic_etablissement_arabe, adresse_etablissement, villepays_etablissement, telephone_etablissement, datesync)");
			loadInFileStatement.executeUpdate();
			System.out.println("Fin Insertion Individu");
			getConnection("mysql").close();
			
			FosUser fosuser = new FosUser();
			try {
				fosuser.synchronize(dateanne, datsychr);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			Bac bac = new Bac();
			try {
				bac.synchronize(dateanne, datsychr);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			Etape etape = new Etape();
			try {
				etape.synchronize(dateanne, datsychr);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			Diplome diplom = new Diplome();
			try {
				diplom.synchronize(dateanne, datsychr);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			Examen examen = new Examen();
			try {
				examen.synchronize(dateanne, datsychr);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			CursusAnnee cursusannee = new CursusAnnee();
			try {
				cursusannee.synchronize(dateanne, datsychr);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			/*cursus cursus = new cursus();
			try {
				cursus.TableCursus(dateanne, datsychr);
			} catch (SQLException e) {
				e.printStackTrace();
			}*/
			
			Cursus_v2 cursus_v2 = new Cursus_v2();
			try {
				cursus_v2.synchronize(dateanne, datsychr);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {

			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}

	}

}
