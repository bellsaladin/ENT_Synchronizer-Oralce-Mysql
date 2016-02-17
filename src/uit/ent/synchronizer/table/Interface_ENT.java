package uit.ent.synchronizer.table;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

import uit.ent.synchronizer.quartz.CronTriggerSynchonisation;

public class Interface_ENT extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String DB_CONNECTION = "jdbc:oracle:thin:@10.1.0.251:2001:kenitra";
	private static final String DB_USER = "ent";
	private static final String DB_PASSWORD = "jsffhhfg5847296";
	Connection dbConnection = null;
	PreparedStatement preparedStatement = null;
	public static String COD_ANU;

	private JPanel contentPane;
	static Locale locale = Locale.getDefault();
	static Date actuelle = new Date();
	static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	static DateFormat dateFormatAnnee = new SimpleDateFormat("yyyy");
	static Interface_ENT frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new Interface_ENT();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Interface_ENT() {

		String selectSQL = "SELECT * FROM (SELECT * FROM ANNEE_UNI ORDER BY COD_ANU DESC) where rownum = 1";
		dbConnection = getDBConnection();
		try {
			preparedStatement = dbConnection.prepareStatement(selectSQL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ResultSet rs = null;
		try {
			rs = preparedStatement.executeQuery();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			while (rs.next()) {
				try {
					COD_ANU = rs.getString("COD_ANU");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		setTitle("Universit\u00E9 Ibn Tofail - Kenitra Maroc  (Espace Synchronisation Apogee / ETN");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 740, 515);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(51, 204, 255));
		contentPane.setForeground(Color.PINK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel(
				"Environnement Num\u00E9rique de Travail  (ENT) ");
		lblNewLabel.setBounds(0, 0, 0, 0);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel1 = new JLabel(
				"Environnement Num\u00E9rique de Travail  (ENT) de  ");
		lblNewLabel1.setBounds(0, 11, 722, 27);
		lblNewLabel1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel1.setFont(new Font("Tahoma", Font.PLAIN, 22));
		contentPane.add(lblNewLabel1);

		JLabel lblNewLabel_1 = new JLabel(
				"l'Universit\u00E9 Ibn Tofail, K\u00E9nitra (Maroc)");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblNewLabel_1.setBounds(170, 49, 379, 27);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel(
				"Synchronisation entre APOGEE et L'ENT");
		lblNewLabel_2.setForeground(Color.BLUE);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblNewLabel_2.setBounds(134, 87, 461, 27);
		contentPane.add(lblNewLabel_2);

		JButton btnNewButton = new JButton("D\u00E9marrer la Synchronisation ");
		btnNewButton.addActionListener(new ActionListener() {
			private String periode1;
			private String periode2;
			private String min1;
			private String min2;
			private String dateanne;

			public void actionPerformed(ActionEvent arg0) {
				JOptionPane
						.showMessageDialog(frame,
								"Opération en cours de traitement... Merci de patienter. ");

				// synchronisation a 12h
				CronTriggerSynchonisation operation1 = new CronTriggerSynchonisation();
				try {

					periode1 = textField_1.getText();
					min1 = textField_2.getText();
					dateanne = textField_4.getText();
					operation1.premiereSynchronisation(periode1, min1, dateanne);

					System.out.println("Demarrer : " + dateanne);

				} catch (Exception e) {
					e.printStackTrace();
				}

				// synchronisation a 19h
				CronTriggerSynchonisation operation2 = new CronTriggerSynchonisation();
				try {
					periode2 = textField.getText();
					min2 = textField_3.getText();
					dateanne = textField_4.getText();
					operation2.deuxiemeSynchronisation(periode2, min2, dateanne);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnNewButton.setBounds(181, 309, 368, 94);
		contentPane.add(btnNewButton);

		JLabel label = new JLabel(dateFormat.format(actuelle).toString());
		label.setFont(new Font("Tahoma", Font.BOLD, 18));
		label.setBounds(301, 125, 125, 22);
		contentPane.add(label);

		JLabel lblPriode = new JLabel("P\u00E9riode 1 :");
		lblPriode.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPriode.setBounds(170, 186, 75, 14);
		contentPane.add(lblPriode);

		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.BOLD, 14));
		textField.setText("20");
		textField.setBounds(471, 178, 52, 27);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("P\u00E9riode 2 :");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_3.setBounds(386, 186, 83, 14);
		contentPane.add(lblNewLabel_3);

		textField_1 = new JTextField();
		textField_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		textField_1.setText("12");
		textField_1.setBounds(255, 178, 52, 27);
		contentPane.add(textField_1);
		textField_1.setColumns(10);

		JLabel lblH = new JLabel("h");
		lblH.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblH.setBounds(310, 189, 46, 16);
		contentPane.add(lblH);

		JLabel label_1 = new JLabel("h");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_1.setBounds(527, 189, 46, 16);
		contentPane.add(label_1);

		JButton btnNewButton_1 = new JButton("Arr\u00EAt\u00E9 l'execution");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnNewButton_1.setBounds(181, 414, 368, 63);
		contentPane.add(btnNewButton_1);

		textField_2 = new JTextField();
		textField_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		textField_2.setText("0");
		textField_2.setBounds(255, 216, 52, 28);
		contentPane.add(textField_2);
		textField_2.setColumns(10);

		textField_3 = new JTextField();
		textField_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		textField_3.setText("0");
		textField_3.setBounds(471, 216, 52, 28);
		contentPane.add(textField_3);
		textField_3.setColumns(10);

		JLabel lblMin = new JLabel("min");
		lblMin.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblMin.setBounds(310, 228, 46, 16);
		contentPane.add(lblMin);

		JLabel label_2 = new JLabel("min");
		label_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_2.setBounds(527, 228, 46, 16);
		contentPane.add(label_2);

		JLabel lblAnneUniversitaire = new JLabel("Ann\u00E9e Universitaire :");
		lblAnneUniversitaire.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblAnneUniversitaire.setBounds(214, 279, 190, 14);
		contentPane.add(lblAnneUniversitaire);

		textField_4 = new JTextField(COD_ANU);
		textField_4.setFont(new Font("Tahoma", Font.BOLD, 20));
		textField_4.setBounds(399, 271, 70, 27);
		contentPane.add(textField_4);
		textField_4.setColumns(10);
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
