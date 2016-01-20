package org.eclipse.wb.swing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.acl.LastOwnerException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import uit.ent.synchronizer.Config;

public class individu {

	public static List<Integer> listCodesIndividus = new ArrayList<Integer>();
	
	private static ResultSet rs1;
	private static int COD_IND;
	private static String cod_ind_mysql;
	private static String query;
	private static String query2;
	private static ResultSet rs2;
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
	private static String LIB_NOM_IND_ARB1;
	private static String LIB_PRN_IND_ARB1;
	private static String LIB_VIL_NAI_ETU_ARB;
	private static int COD_ANU_INA;
	private static String nationaissan;
	private static String provadressfix;
	private static String comunadressfix;
	private static String provadressfix2;
	private static String comunadressfix2;
	private static String departementnaissance;
	private static String paysnaissance;
	private static int n;
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
	private static String DB_DRIVER;
	private static String DB_CONNECTION;
	private static String DB_USER;
	private static String DB_PASSWORD;
	private static String JDBC_DRIVER;
	private static String DB_URL;
	private static String USER;
	private static String PASS;
	private static String filename = "individu";
	private static FileWriter writer;
	private static String txtDate;
	private static String adressfixtt;

	String polecenie;

	public static void Tableindividu(String dateanne, String datsychr)
			throws SQLException {
		entconnexion entcon = new entconnexion();
		_Statics cc = new _Statics();
		entcon.entconnexion();

		DB_DRIVER = entcon.getDB_DRIVER();
		DB_CONNECTION = entcon.getDB_CONNECTION();
		DB_USER = entcon.getDB_USER();
		DB_PASSWORD = entcon.getDB_PASSWORD();

		JDBC_DRIVER = entcon.getJDBC_DRIVER();
		DB_URL = entcon.getDB_URL();
		USER = entcon.getUSER();
		PASS = entcon.getPASS();

		txtDate = new SimpleDateFormat("yyyy-MM-dd hh:m:ss", Locale.FRANCE)
				.format(new Date());

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		Connection conn = null;
		Statement stmt = null;
		Statement stmt1 = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			try {
				conn = DriverManager.getConnection(DB_URL, USER, PASS);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				stmt1 = conn.createStatement();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
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
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();
			int i = 0;
			n = 1;

			try {
				writer = new FileWriter(_Statics.workingDir.replace("\\", "/")
						+ "/ficher/individu.txt", false);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			while (rs.next()) {
				COD_IND = rs.getInt("COD_IND");
				
				listCodesIndividus.add(COD_IND);
				
				LIB_NOM_PAT_IND = rs.getString("LIB_NOM_PAT_IND");
				LIB_PR1_IND = rs.getString("LIB_PR1_IND");
				CIN_IND = rs.getString("CIN_IND");
				COD_NNE_IND = rs.getString("COD_NNE_IND");
				DATE_NAI_IND = rs.getString("DATE_NAI_IND");
				LIB_VIL_NAI_ETU = rs.getString("LIB_VIL_NAI_ETU");
				LIB_PAY = rs.getString("nationalite");
				COD_SEX_ETU = rs.getString("COD_SEX_ETU");
				LIB_FAM = rs.getString("LIB_FAM");
				LIB_THP = rs.getString("LIB_THP");
				preinscriptionensigsuper = rs
						.getString("preinscriptionensigsuper");
				preinscriptidansetablimarocain = rs
						.getString("preinscriptidansetablimarocain");
				LIB_ETB = rs.getString("LIB_ETB");
				preinscridansetablis = rs.getString("preinscridansetablis");
				adressfix = rs.getString("adressfix");
				adress2fix = rs.getString("adress2fix");
				payefix = rs.getString("payefix");
				villefix = rs.getString("villefix");
				telefix = rs.getString("telefix");
				adressfix2 = rs.getString("adressfix2");
				adress2fix2 = rs.getString("adress2fix2");
				payefix2 = rs.getString("payefix2");
				villefix2 = rs.getString("villefix2");
				telefix2 = rs.getString("telefix2");
				LIB_NOM_IND_ARB = rs.getString("LIB_NOM_IND_ARB");
				LIB_PRN_IND_ARB = rs.getString("LIB_PRN_IND_ARB");
				LIB_VIL_NAI_ETU_ARB = rs.getString("LIB_VIL_NAI_ETU_ARB");
				COD_ANU_INA = rs.getInt("COD_ANU");
				nationaissan = rs.getString("nationaissan");
				provadressfix = rs.getString("provadressfix");
				comunadressfix = rs.getString("comunadressfix");
				provadressfix2 = rs.getString("provadressfix2");
				comunadressfix2 = rs.getString("comunadressfix2");
				departementnaissance = rs.getString("departementnaissance");
				paysnaissance = rs.getString("paysnaissance");
				cod_apogee = rs.getString("COD_ETU");
				lib_etablissement = rs.getString("INT_1_EDI_DIP_CMP");
				lic_etablissement = rs.getString("LIC_CMP");
				cod_postal_fix = rs.getString("COD_COM");
				cod_postal_anull = rs.getString("cod_pos_anu");
				lib_ttr = rs.getString("lib_ttr");
				lib_cmp_arb = rs.getString("lib_cmp_arb");
				lic_cmp_arb = rs.getString("lic_cmp_arb");
				lib_ad1_cmp = rs.getString("lib_ad1_cmp");
				lib_ad2_cmp = rs.getString("lib_ad2_cmp");
				lib_ad3_cmp = rs.getString("lib_ad3_cmp");

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
			
			
			System.out.println("Insertion Individu");
			PreparedStatement Pindividu = conn
					.prepareStatement("LOAD DATA LOCAL INFILE '"
							+ _Statics.workingDir.replace("\\", "/")
							+ "/ficher/individu.txt' "
							+ "INTO TABLE individu "
							+ "FIELDS "
							+ "TERMINATED BY ';' "
							+ "ESCAPED BY '\\\\' LINES STARTING BY '' "
							+ "TERMINATED BY '\\n' "
							+ " (cod_ind, nom, prenom, cin, cne, date_naissance, lieu_naissance, pays_naissance, sexe, situation_familiale, handicap, dans_ens_sup, en_unive_marocain, etablissement, dans_uitk, adresse_fix, pays_fix, ville_fix, ntelephone_fix, adresse_annuelle, pays_annuelle, ville_annuelle, ntelephone_annuelle, nom_arabe, prenom_arabe, lieu_naissance_arabe, cod_anu_ina, nationalite_naissance, province_fix, commun_fix, province_annuelle, commun_annuelle, departement_naissance, cod_apogee, libelle_long_etablissement, libelle_court_etablissement, username, cod_postal_fix, cod_postal_annuelle, lib_attestation, lib_etablissement_arabe, lic_etablissement_arabe, adresse_etablissement, villepays_etablissement, telephone_etablissement, datesync)");
			Pindividu.executeUpdate();
			System.out.println("Fin Insertion Individu");

			fosuser fosuser = new fosuser();
			try {
				fosuser.Tablefosuser(dateanne, datsychr);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			Bac bac = new Bac();
			try {
				bac.TableBac(dateanne, datsychr);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			etape etape = new etape();
			try {
				etape.TableEtape(dateanne, datsychr);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			diplom diplom = new diplom();
			try {
				diplom.Tabledip(dateanne, datsychr);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			examen examen = new examen();
			try {
				examen.Tableexam(dateanne, datsychr);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			cursus_annee cursusannee = new cursus_annee();
			try {
				System.out.println("BEGIN :  Sycn Cursus Annee");
				cursusannee.TableCursus(dateanne, datsychr);
				System.out.println("END :  Sycn Cursus Annee");
			} catch (SQLException e) {
				e.printStackTrace();
			}

			cursus cursus = new cursus();
			try {
				cursus.TableCursus(dateanne, datsychr);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			conn.close();

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

			if (dbConnection != null) {
				try {
					dbConnection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}

	}

	private static Connection getDBConnection() {

		Connection dbConnection = null;

		try {

			Class.forName(DB_DRIVER);

		} catch (ClassNotFoundException e) {

			System.out.println(e.getMessage());

		}

		try {

			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER,
					DB_PASSWORD);
			return dbConnection;

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}

		return dbConnection;

	}
}
