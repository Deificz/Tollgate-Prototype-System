import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.IOException;
import java.sql.*;
import java.awt.Window;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.SystemColor;
import javax.swing.UIManager;


public class FrmToll {

	private JFrame frmWelcomeToAndromeda;
	private JTextField textPlate;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmToll window = new FrmToll();
					window.frmWelcomeToAndromeda.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FrmToll() {
		frmWelcomeToAndromeda = new JFrame();
		frmWelcomeToAndromeda.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\DELL\\Downloads\\unnamed.png"));
		frmWelcomeToAndromeda.setTitle("Welcome to Andromeda Galaxy Expressway");
		frmWelcomeToAndromeda.setBounds(100, 100, 450, 300);
		frmWelcomeToAndromeda.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmWelcomeToAndromeda.getContentPane().setLayout(null);
		
		JButton btnPay = new JButton("PAY");
		btnPay.setBackground(UIManager.getColor("Button.darkShadow"));
		btnPay.setForeground(Color.BLACK);
		btnPay.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnPay.setBounds(124, 206, 80, 35);
		JButton btnReg = new JButton("REGISTER");
		btnReg.setBackground(UIManager.getColor("Button.light"));
		btnReg.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnReg.setBounds(321, 206, 103, 35);
		JLabel lblPlate = new JLabel("Plate No. :");
		lblPlate.setIcon(null);
		lblPlate.setForeground(Color.WHITE);
		lblPlate.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblPlate.setBounds(27, 51, 124, 28);
		JLabel lblEntry = new JLabel("Entry Point");
		lblEntry.setForeground(Color.WHITE);
		lblEntry.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblEntry.setBounds(27, 90, 93, 35);
		JLabel lblExit = new JLabel("Exit Point");
		lblExit.setForeground(Color.WHITE);
		lblExit.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblExit.setBounds(27, 136, 93, 35);
		JComboBox cmbBoxEntry = new JComboBox();
		cmbBoxEntry.setModel(new DefaultComboBoxModel(new String[] {"Mandaluyong City", "Manila City", "Quezon City"}));
		cmbBoxEntry.setBounds(178, 99, 136, 22);
		JComboBox cmbBoxExit = new JComboBox();
		cmbBoxExit.setModel(new DefaultComboBoxModel(new String[] {"Mandaluyong City", "Manila City", "Quezon City"}));
		cmbBoxExit.setBounds(178, 145, 136, 22);
		textPlate = new JTextField();
		textPlate.setBounds(178, 55, 136, 20);
		textPlate.setColumns(10);
		
		frmWelcomeToAndromeda.getContentPane().add(btnPay);
		frmWelcomeToAndromeda.getContentPane().add(btnReg);
		frmWelcomeToAndromeda.getContentPane().add(lblPlate);
		frmWelcomeToAndromeda.getContentPane().add(lblEntry);
		frmWelcomeToAndromeda.getContentPane().add(lblExit);
		frmWelcomeToAndromeda.getContentPane().add(cmbBoxEntry);
		frmWelcomeToAndromeda.getContentPane().add(cmbBoxExit);
		frmWelcomeToAndromeda.getContentPane().add(textPlate);
		
		JButton btnLoad = new JButton("LOAD");
		btnLoad.setForeground(Color.BLACK);
		btnLoad.setBackground(UIManager.getColor("Button.darkShadow"));
		
		btnLoad.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnLoad.setBounds(214, 206, 80, 35);
		frmWelcomeToAndromeda.getContentPane().add(btnLoad);
		
		JLabel lblNewLabel = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/istockphoto-922548034-612x612.jpg")).getImage();
		lblNewLabel.setIcon(new ImageIcon(img));
		lblNewLabel.setBounds(0, 0, 434, 261);
		frmWelcomeToAndromeda.getContentPane().add(lblNewLabel);
		
		btnPay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String strDriver = "com.mysql.cj.jdbc.Driver";
		        String strConn = "jdbc:mysql://localhost:3306/carRegister";
		        String strUser = "deificz";
		        String strPass = "pass123";
		        
		        String PlateNo = textPlate.getText();
		        int CarType = 0;
		        String Entry = cmbBoxEntry.getSelectedItem().toString();
		        String Exit = cmbBoxExit.getSelectedItem().toString();
		        float usrBal = 0;
		        float fltFee =  0;
		        
		        try {           
		        	Class.forName(strDriver);
		        	Connection objConn = DriverManager.getConnection(strConn, strUser, strPass);   
		        	System.out.println("Successfully connected to the database server..");
		        	
		        	String sq1 = "SELECT * FROM tblUserInfo WHERE strPlateNo = '"+PlateNo+"' ";	
		        	
		        	Statement st = objConn.createStatement();
		        	
		        	ResultSet rs = st.executeQuery(sq1);
		        	while(rs.next())
		        	{
		        		usrBal = rs.getFloat("fltBal");
		        		CarType = rs.getInt("intCar");
		        	}
		        	
		        	fltFee = calculateFee(CarType, Entry, Exit);
		        	if(fltFee > usrBal)
		        		JOptionPane.showMessageDialog(null, "Insufficient Balance ! "+ "\n" + "\n" + "Balance: " + usrBal + "Try Different Exit Point");
		        	else if(Entry == Exit)
		        		JOptionPane.showMessageDialog(null, "Plate Number not Registered !");
		        	else 
		        	{
		        	usrBal -= fltFee;
		        	String sq2 = "UPDATE tblUserInfo SET fltBal = '"+usrBal+"' WHERE strPlateNo ='"+PlateNo+"' ";
		        	PreparedStatement pst1 = objConn.prepareStatement(sq2);
		        	pst1.execute();
		        	JOptionPane.showMessageDialog(null, "Thank you!" + "\n" + "New Balance: " + usrBal);
		            objConn.close();
		            }
		        } catch (Exception objEx) {
		            System.out.println("Problem retrieving information..");
		            System.out.println(objEx);
		            
		        }
			}
		});
		
		btnReg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
		           FrmRegister frmReg = new FrmRegister();
		           frmReg.setVisible(true);
		           
		        }
			}
		);
		
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				FrmLoad frmLoad = new FrmLoad();
				frmLoad.setVisible(true);
				
			}
		});
}

	
	public static float calculateFee(int type, String entry, String exit) {
		switch (entry) {
		case "Mandaluyong City":
			if (exit.equals("Manila City")) 
				return 100 * type;
			else // QC
				return 200 * type;
		case "Manila City":
			if (exit.equals("Mandaluyong City"))
				return 100 * type;
			else // QC
				return 50 * type;
		case "Quezon City":
			if (exit.equals("Manila City"))
				return 50 * type;
			else // Mandaluyong
				return 200 * type;
		default:
			System.out.println("Error.");
			return 0.2f;
		}
	}
}
