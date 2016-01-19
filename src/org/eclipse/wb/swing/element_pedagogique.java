package org.eclipse.wb.swing;


import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class element_pedagogique  {

	private static String DB_DRIVER;
	private static String DB_CONNECTION;
	private static String DB_USER;
	private static String DB_PASSWORD;
	private static String DB_URL;
	private static String USER;
	private static String PASS;
	private static FileWriter writer;
	private static String COD_ELP;
	private static String COD_CMP;
	private static String COD_NEL;
	private static String COD_PEL;
	private static String LIB_ELP;
	private static String LIC_ELP;
	private static String LIB_CMT_ELP;
	private static String DAT_CRE_ELP;
	private static String DAT_MOD_ELP;
	private static String NBR_VOL_ELP;
	private static String COD_VOL_ELP;
	private static String ETA_ELP;
	private static String LIB_LIE_ELP;
	private static String LIB_NOM_RSP_ELP;
	private static String DAT_DEB_OPE_IPE;
	private static String DAT_FIN_OPE_IPE;
	private static String NBR_ADM_ELP;
	private static String NBR_ADM_FRA;
	private static String NBR_ADM_ETR;
	private static String NBR_PNT_ECT_ELP;
	private static String NOT_OBT_ELP_NUM;
	private static String NOT_OBT_ELP_DEN;
	private static String NOT_MIN_RPT_ELP_NUM;
	private static String NOT_MIN_RPT_ELP_DEN;
	private static String NOT_MIN_ADM_NUM;
	private static String NOT_MIN_ADM_DEN;
	private static String NOT_MAX_ADM_NUM;
	private static String NOT_MAX_ADM_DEN;
	private static String TEM_ELP_CAP;
	private static String TEM_REI_IPE_ACQ;
	private static String TEM_SUS_ELP;
	private static String LIB_SUS_ELP;
	private static String TEM_REL_POS_SYT;
	private static String TEM_SCA_ELP;
	private static String TEM_ELP_PRM_NIV;
	private static String TEM_NOT_ELP;
	private static String BAR_SAI_ELP;
	private static String TEM_PNT_JUR_ELP;
	private static String TEM_MND_ELP;
	private static String COD_CFM;
	private static String TEM_RES_ELP;
	private static String TEM_JUR_ELP;
	private static String TEM_CTL_VAL_CAD_ELP;
	private static String TEM_ANL_RPT_ELP;
	private static String NOT_MIN_RPT_ELP;
	private static String BAR_MIN_RPT_ELP;
	private static String TEM_CON_ELP;
	private static String DUR_CON_ELP;
	private static String NOT_MIN_CON_ELP;
	private static String BAR_MIN_CON_ELP;
	private static String TEM_CAP_ELP;
	private static String TEM_SES_UNI;
	private static String TEM_ADI;
	private static String TEM_ADO;
	private static String TEM_HEU_ENS_ELP;
	private static String COD_SCC;
	private static String NBR_EFF_PRV_ELP;
	private static String NBR_HEU_CM_ELP;
	private static String NBR_HEU_TD_ELP;
	private static String NBR_HEU_TP_ELP;
	private static String TEM_MCC_ELP;
	private static String TEM_RPT_DSC_ELP;
	private static String COD_PAN_1;
	private static String COD_PAN_2;
	private static String COD_PAN_3;
	private static String COD_PAN_4;
	private static String LIB_ELP_ARB;
	private static String LIC_ELP_ARB;
	private static String txtDate;
	private static String JDBC_DRIVER;
	private static int n;


	// ------------------- Fonction pour la table Individu
	// -----------------------
	// static ResultSet rss;
	String polecenie;

	public static void Tableelementpedagogique(String dateanne, String datsychr) throws SQLException {
			
		entconnexion entcon = new entconnexion();
		_Statics cc= new _Statics();
		entcon.entconnexion();

		DB_DRIVER = entcon.getDB_DRIVER();
		DB_CONNECTION = entcon.getDB_CONNECTION();
		DB_USER = entcon.getDB_USER();
		DB_PASSWORD = entcon.getDB_PASSWORD();

		JDBC_DRIVER = entcon.getJDBC_DRIVER();
		DB_URL = entcon.getDB_URL();
		USER = entcon.getUSER();
		PASS = entcon.getPASS();
		
				
		txtDate = new SimpleDateFormat("yyyy-MM-dd hh:m:ss", Locale.FRANCE).format(new Date());
		//System.out.println(txtDate); 

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		Connection conn = null;
		Statement stmt = null;
		Statement stmt1 = null;

		// query = "SELECT * FROM individu";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			try {
				conn = DriverManager.getConnection(DB_URL, USER, PASS);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				stmt1 = conn.createStatement();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String selectSQL = "SELECT * FROM ELEMENT_PEDAGOGI ";

		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();
			int i = 0;
			n = 1;
			
			try {
				//writer = new FileWriter(cc.workingDir+"\\ficher\\"+ filename +".txt", false);
				writer = new FileWriter( _Statics.workingDir.replace("\\", "/")+"/ficher/element_pedagogique.txt", false);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			//----------- Date synchronsation --------
			
			
			//----------------------------------------		
			while (rs.next()) {
				COD_ELP = rs.getString("COD_ELP");
				COD_CMP = rs.getString("COD_CMP");
				COD_NEL = rs.getString("COD_NEL");
				COD_PEL = rs.getString("COD_PEL");
				LIB_ELP = rs.getString("LIB_ELP");
				LIC_ELP = rs.getString("LIC_ELP");
				LIB_CMT_ELP = rs.getString("LIB_CMT_ELP");
				DAT_CRE_ELP = rs.getString("DAT_CRE_ELP");
				DAT_MOD_ELP = rs.getString("DAT_MOD_ELP");
				NBR_VOL_ELP  = rs.getString("NBR_VOL_ELP");
				COD_VOL_ELP = rs.getString("COD_VOL_ELP");
				ETA_ELP = rs.getString("ETA_ELP");
				LIB_LIE_ELP = rs.getString("LIB_LIE_ELP");
				LIB_NOM_RSP_ELP = rs.getString("LIB_NOM_RSP_ELP");
				DAT_DEB_OPE_IPE = rs.getString("DAT_DEB_OPE_IPE");
				DAT_FIN_OPE_IPE = rs.getString("DAT_FIN_OPE_IPE");
				NBR_ADM_ELP = rs.getString("NBR_ADM_ELP");
				NBR_ADM_FRA = rs.getString("NBR_ADM_FRA");
				NBR_ADM_ETR = rs.getString("NBR_ADM_ETR");
				NBR_PNT_ECT_ELP = rs.getString("NBR_PNT_ECT_ELP");
				NOT_OBT_ELP_NUM = rs.getString("NOT_OBT_ELP_NUM");
				NOT_OBT_ELP_DEN = rs.getString("NOT_OBT_ELP_DEN");
				NOT_MIN_RPT_ELP_NUM = rs.getString("NOT_MIN_RPT_ELP_NUM");
				NOT_MIN_RPT_ELP_DEN = rs.getString("NOT_MIN_RPT_ELP_DEN");
				NOT_MIN_ADM_NUM = rs.getString("NOT_MIN_ADM_NUM");
				NOT_MIN_ADM_DEN = rs.getString("NOT_MIN_ADM_DEN");
				NOT_MAX_ADM_NUM = rs.getString("NOT_MAX_ADM_NUM");
				NOT_MAX_ADM_DEN = rs.getString("NOT_MAX_ADM_DEN");
				TEM_ELP_CAP = rs.getString("TEM_ELP_CAP");
				TEM_REI_IPE_ACQ = rs.getString("TEM_REI_IPE_ACQ");
				TEM_SUS_ELP = rs.getString("TEM_SUS_ELP");
				LIB_SUS_ELP = rs.getString("LIB_SUS_ELP");
				TEM_REL_POS_SYT = rs.getString("TEM_REL_POS_SYT");
				TEM_SCA_ELP = rs.getString("TEM_SCA_ELP");
				TEM_ELP_PRM_NIV = rs.getString("TEM_ELP_PRM_NIV");
				TEM_NOT_ELP = rs.getString("TEM_NOT_ELP");
				BAR_SAI_ELP = rs.getString("BAR_SAI_ELP");
				TEM_PNT_JUR_ELP = rs.getString("TEM_PNT_JUR_ELP");
				TEM_MND_ELP = rs.getString("TEM_MND_ELP");
				COD_CFM = rs.getString("COD_CFM");
				TEM_RES_ELP = rs.getString("TEM_RES_ELP");
				TEM_JUR_ELP = rs.getString("TEM_JUR_ELP");
				TEM_CTL_VAL_CAD_ELP = rs.getString("TEM_CTL_VAL_CAD_ELP");
				TEM_ANL_RPT_ELP = rs.getString("TEM_ANL_RPT_ELP");
				NOT_MIN_RPT_ELP = rs.getString("NOT_MIN_RPT_ELP");
				BAR_MIN_RPT_ELP = rs.getString("BAR_MIN_RPT_ELP");
				TEM_CON_ELP = rs.getString("TEM_CON_ELP");
				DUR_CON_ELP = rs.getString("DUR_CON_ELP");
				NOT_MIN_CON_ELP = rs.getString("NOT_MIN_CON_ELP");
				BAR_MIN_CON_ELP = rs.getString("BAR_MIN_CON_ELP");
				TEM_CAP_ELP = rs.getString("TEM_CAP_ELP");
				TEM_SES_UNI = rs.getString("TEM_SES_UNI");
				TEM_ADI = rs.getString("TEM_ADI");
				TEM_ADO = rs.getString("TEM_ADO");
				TEM_HEU_ENS_ELP = rs.getString("TEM_HEU_ENS_ELP");
				COD_SCC = rs.getString("COD_SCC");
				NBR_EFF_PRV_ELP = rs.getString("NBR_EFF_PRV_ELP");
				NBR_HEU_CM_ELP = rs.getString("NBR_HEU_CM_ELP");
				NBR_HEU_TD_ELP = rs.getString("NBR_HEU_TD_ELP");
				NBR_HEU_TP_ELP = rs.getString("NBR_HEU_TP_ELP");
				TEM_MCC_ELP = rs.getString("TEM_MCC_ELP");
				TEM_RPT_DSC_ELP = rs.getString("TEM_RPT_DSC_ELP");
				COD_PAN_1 = rs.getString("COD_PAN_1");
				COD_PAN_2 = rs.getString("COD_PAN_2");
				COD_PAN_3 = rs.getString("COD_PAN_3");
				COD_PAN_4 = rs.getString("COD_PAN_4");
				LIB_ELP_ARB = rs.getString("LIB_ELP_ARB");
				LIC_ELP_ARB = rs.getString("LIC_ELP_ARB");

				
				String texte = COD_ELP +";"
						+COD_CMP+";"
						+COD_NEL+";"
						+COD_PEL+";"
						+LIB_ELP+";"
						+LIC_ELP+";"
						+LIB_CMT_ELP+";"
						+DAT_CRE_ELP+";"
						+DAT_MOD_ELP+";"
						+NBR_VOL_ELP+";"
						+COD_VOL_ELP+";"
						+ETA_ELP+";"
						+LIB_LIE_ELP+";"
						+LIB_NOM_RSP_ELP+";"
						+DAT_DEB_OPE_IPE+";"
						+DAT_FIN_OPE_IPE+";"
						+NBR_ADM_ELP+";"
						+NBR_ADM_FRA+";"
						+NBR_ADM_ETR+";"
						+NBR_PNT_ECT_ELP+";"
						+NOT_OBT_ELP_NUM+";"
						+NOT_OBT_ELP_DEN+";"
						+NOT_MIN_RPT_ELP_NUM+";"
						+NOT_MIN_RPT_ELP_DEN+";"
						+NOT_MIN_ADM_NUM+";"
						+NOT_MIN_ADM_DEN+";"
						+NOT_MAX_ADM_NUM+";"
						+NOT_MAX_ADM_DEN+";"
						+TEM_ELP_CAP+";"
						+TEM_REI_IPE_ACQ+";"
						+TEM_SUS_ELP+";"
						+LIB_SUS_ELP+";"
						+TEM_REL_POS_SYT+";"
						+TEM_SCA_ELP+";"
						+TEM_ELP_PRM_NIV+";"
						+TEM_NOT_ELP+";"
						+BAR_SAI_ELP+";"
						+TEM_PNT_JUR_ELP+";"
						+TEM_MND_ELP+";"
						+COD_CFM+";"
						+TEM_RES_ELP+";"
						+TEM_JUR_ELP+";"
						+TEM_CTL_VAL_CAD_ELP+";"
						+TEM_ANL_RPT_ELP+";"
						+NOT_MIN_RPT_ELP+";"
						+BAR_MIN_RPT_ELP+";"
						+TEM_CON_ELP+";"
						+DUR_CON_ELP+";"
						+NOT_MIN_CON_ELP+";"
						+BAR_MIN_CON_ELP+";"
						+TEM_CAP_ELP+";"
						+TEM_SES_UNI+";"
						+TEM_ADI+";"
						+TEM_ADO+";"
						+TEM_HEU_ENS_ELP+";"
						+COD_SCC+";"
						+NBR_EFF_PRV_ELP+";"
						+NBR_HEU_CM_ELP+";"
						+NBR_HEU_TD_ELP+";"
						+NBR_HEU_TP_ELP+";"
						+TEM_MCC_ELP+";"
						+TEM_RPT_DSC_ELP+";"
						+COD_PAN_1+";"
						+COD_PAN_2+";"
						+COD_PAN_3+";"
						+COD_PAN_4+"; --- ; --- ;"+ datsychr +" \n";
				
			
			
				//texte = texte.replace("null", "---");
				String stre2 = new String (texte.getBytes(Charset.forName("ISO-8859-9")));
				//System.out.println(stre2);

				try{
				     //writer = new FileWriter("ficher/"+ filename +".txt", true);
				     writer.write(texte,0,texte.length());
				}catch(IOException ex){
				    ex.printStackTrace();
				}
			
				i++;
			}
			
			 try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		
		System.out.println("Insertion element_pedagogique");		
	
		PreparedStatement Pindividu = conn .prepareStatement("LOAD DATA LOCAL INFILE '" + _Statics.workingDir.replace("\\", "/")+ "/ficher/element_pedagogique.txt' " +
					"INTO TABLE element_pedagogique " +
					"FIELDS " +
					"TERMINATED BY ';' " +
					"ESCAPED BY '\\\\' LINES STARTING BY '' " +
					"TERMINATED BY '\\n' " +
					//" (id, username, username_canonical, email, email_canonical, enabled) ");
					" (COD_ELP, COD_CMP, COD_NEL, COD_PEL, LIB_ELP, LIC_ELP, LIB_CMT_ELP, DAT_CRE_ELP, DAT_MOD_ELP, NBR_VOL_ELP, COD_VOL_ELP, ETA_ELP, LIB_LIE_ELP, LIB_NOM_RSP_ELP, DAT_DEB_OPE_IPE, DAT_FIN_OPE_IPE, NBR_ADM_ELP, NBR_ADM_FRA, NBR_ADM_ETR, NBR_PNT_ECT_ELP, NOT_OBT_ELP_NUM, NOT_OBT_ELP_DEN, NOT_MIN_RPT_ELP_NUM, NOT_MIN_RPT_ELP_DEN, NOT_MIN_ADM_NUM, NOT_MIN_ADM_DEN, NOT_MAX_ADM_NUM, NOT_MAX_ADM_DEN, TEM_ELP_CAP, TEM_REI_IPE_ACQ, TEM_SUS_ELP, LIB_SUS_ELP, TEM_REL_POS_SYT, TEM_SCA_ELP, TEM_ELP_PRM_NIV, TEM_NOT_ELP, BAR_SAI_ELP, TEM_PNT_JUR_ELP, TEM_MND_ELP, COD_CFM, TEM_RES_ELP, TEM_JUR_ELP, TEM_CTL_VAL_CAD_ELP, TEM_ANL_RPT_ELP, NOT_MIN_RPT_ELP, BAR_MIN_RPT_ELP, TEM_CON_ELP, DUR_CON_ELP, NOT_MIN_CON_ELP, BAR_MIN_CON_ELP, TEM_CAP_ELP, TEM_SES_UNI, TEM_ADI, TEM_ADO, TEM_HEU_ENS_ELP, COD_SCC, NBR_EFF_PRV_ELP, NBR_HEU_CM_ELP, NBR_HEU_TD_ELP, NBR_HEU_TP_ELP, TEM_MCC_ELP, TEM_RPT_DSC_ELP, COD_PAN_1, COD_PAN_2, COD_PAN_3, COD_PAN_4, LIB_ELP_ARB, LIC_ELP_ARB, datesync)" );
		  
		Pindividu.executeUpdate();	
			
			System.out.println("Fin Insertion element_pedagogique");
	
			conn.close();

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (dbConnection != null) {
				try {
					dbConnection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
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
