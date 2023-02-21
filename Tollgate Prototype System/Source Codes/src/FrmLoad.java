import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Toolkit;

public class FrmLoad extends JFrame {

	private JPanel contentPane;
	private JTextField textAmount;
	private JLabel lblPlateNo;
	private JTextField textPlate;
	private JLabel lblNewLabel;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmLoad frame = new FrmLoad();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public FrmLoad() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\DELL\\Downloads\\unnamed.png"));
		setTitle("Load");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 376, 237);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAmount = new JLabel("Amount :");
		lblAmount.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblAmount.setBounds(32, 89, 113, 45);
		contentPane.add(lblAmount);
		
		textAmount = new JTextField();
		textAmount.setBounds(127, 97, 169, 32);
		contentPane.add(textAmount);
		textAmount.setColumns(10);
		
		JButton btnLoad = new JButton("LOAD");

		btnLoad.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnLoad.setBounds(139, 140, 129, 47);
		contentPane.add(btnLoad);
		
		lblPlateNo = new JLabel("Plate No :");
		lblPlateNo.setForeground(Color.BLACK);
		lblPlateNo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblPlateNo.setBounds(32, 35, 113, 45);
		contentPane.add(lblPlateNo);
		
		textPlate = new JTextField();
		textPlate.setColumns(10);
		textPlate.setBounds(127, 46, 169, 32);
		contentPane.add(textPlate);
		
		lblNewLabel = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/istockphoto-1139678803-612x612.jpg")).getImage();
		lblNewLabel.setIcon(new ImageIcon(img));
		lblNewLabel.setBounds(0, 0, 360, 198);
		contentPane.add(lblNewLabel);
		
		
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String strDriver = "com.mysql.cj.jdbc.Driver";
		        String strConn = "jdbc:mysql://localhost:3306/carRegister";
		        String strUser = "deificz";
		        String strPass = "pass123";
		        
		        String PlateNo = textPlate.getText();
		        float fltAmount = Float.parseFloat(textAmount.getText());
		        float usrBal = 0;
		        
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
		        	}
		        	
		        	usrBal += fltAmount;
		        	
		        	String sq2 = "UPDATE tblUserInfo SET fltBal = '"+usrBal+"' WHERE strPlateNo ='"+PlateNo+"' ";
		        	
		        	PreparedStatement pst1 = objConn.prepareStatement(sq2);
		        	pst1.execute();
		        	
		        	JOptionPane.showMessageDialog(null, "New Balance: " + usrBal);
		            objConn.close();
		        } catch (Exception objEx) {
		            System.out.println("Problem retrieving information..");
		            System.out.println(objEx);
		            
		        }
			}
		});
	}
}
