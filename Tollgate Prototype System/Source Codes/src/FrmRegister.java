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
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.DefaultComboBoxModel;
import java.awt.Toolkit;

public class FrmRegister extends JFrame {

	private JPanel contentPane;
	private JTextField strPlateNo;
	private JTextField strName;
	private JTextField fltBal;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmRegister frame = new FrmRegister();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrmRegister() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\DELL\\Downloads\\unnamed.png"));
		setTitle("Registration");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 347, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPlateNo = new JLabel("Plate No. :");
		lblPlateNo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblPlateNo.setBounds(10, 28, 132, 42);
		contentPane.add(lblPlateNo);
		
		JLabel lblName = new JLabel("Name :");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblName.setBounds(10, 60, 132, 42);
		contentPane.add(lblName);
		
		JLabel lblCarType = new JLabel("Car Type :");
		lblCarType.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCarType.setBounds(10, 93, 132, 42);
		contentPane.add(lblCarType);
		
		JLabel lblBal = new JLabel("Balance :");
		lblBal.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblBal.setBounds(10, 125, 132, 42);
		contentPane.add(lblBal);
		
		strPlateNo = new JTextField();
		strPlateNo.setColumns(10);
		strPlateNo.setBounds(100, 41, 181, 20);
		contentPane.add(strPlateNo);
		
		strName = new JTextField();
		strName.setColumns(10);
		strName.setBounds(100, 73, 181, 20);
		contentPane.add(strName);
		
		fltBal = new JTextField();
		fltBal.setColumns(10);
		fltBal.setBounds(100, 138, 181, 20);
		contentPane.add(fltBal);
		
		JComboBox cmbBoxCar = new JComboBox();
		cmbBoxCar.setModel(new DefaultComboBoxModel(new String[] {"", "1", "2", "3"}));
		cmbBoxCar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		cmbBoxCar.setBounds(100, 105, 181, 22);
		contentPane.add(cmbBoxCar);
		
		JButton btnReg = new JButton("REGISTER");
		
		btnReg.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnReg.setBackground(Color.LIGHT_GRAY);
		btnReg.setBounds(100, 195, 137, 37);
		contentPane.add(btnReg);
		
		JLabel lblNewLabel = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/2018-white-texture-background-image.jpg")).getImage();
		lblNewLabel.setIcon(new ImageIcon(img));
		lblNewLabel.setBounds(0, 0, 331, 261);
		contentPane.add(lblNewLabel);
		
		btnReg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String strDriver = "com.mysql.cj.jdbc.Driver";
		        String strConn = "jdbc:mysql://localhost:3306/carRegister";
		        String strUser = "deificz";
		        String strPass = "pass123";
		        
		        try {        
		            Class.forName(strDriver);
		            Connection objConn = DriverManager.getConnection(strConn, strUser, strPass);   
		            System.out.println("Successfully connected to the database server..");
		            
		            String database = "INSERT INTO tblUserInfo VALUE (?, ?, ?, ?)";
		            PreparedStatement prstmt = objConn.prepareStatement(database);
		            prstmt.setString(1, strPlateNo.getText());
		            prstmt.setString(2, strName.getText());
		            String carType = cmbBoxCar.getSelectedItem().toString();
		            prstmt.setInt(3, Integer.parseInt(carType));
		            prstmt.setFloat(4, Float.parseFloat(fltBal.getText()));
		            
		            
		            prstmt.executeUpdate();
		            JOptionPane.showMessageDialog(null, "Registered Successfully");
		            
		            objConn.close();
		        } catch (Exception objEx) {
		            System.out.println("Problem retrieving information..");
		            System.out.println(objEx);
		            
		        }
		        
			}
		});
	}
}
