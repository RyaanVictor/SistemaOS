package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.DAO;
import javax.swing.JPasswordField;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.Cursor;

public class login extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1;
	//OBJETOS JDBC
	DAO dao = new DAO();
	private PreparedStatement pst;
	private Connection con;
	private ResultSet rs;
	//OBJETO TELA PRINCIPAL
	principal principal = new principal();
	/**
	 * 
	 */
	private JPanel contentPane;
	private JTextField txtLogin;
	private JPasswordField txtSenha;
	private JLabel lblData;
	private JPanel panel;
	private JButton lblStatus;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login frame = new login();
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
	public login() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				setarData();
			}

			private void setarData() {
				Date data = new Date();
				DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
				lblData.setText(formatador.format(data));
				
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(login.class.getResource("/img/pngegg.png")));
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnAcessar = new JButton("Acessar");
		btnAcessar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Logar();
			}
		});
		btnAcessar.setBounds(144, 114, 89, 23);
		contentPane.add(btnAcessar);
		
		txtLogin = new JTextField();
		txtLogin.setBounds(85, 28, 216, 20);
		contentPane.add(txtLogin);
		txtLogin.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(29, 30, 46, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Senha");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(29, 85, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		txtSenha = new JPasswordField();
		txtSenha.setBounds(85, 83, 216, 20);
		contentPane.add(txtSenha);
		
		panel = new JPanel();
		panel.setBackground(new Color(0, 0, 0));
		panel.setBounds(0, 215, 444, 46);
		contentPane.add(panel);
		panel.setLayout(null);
		
		lblData = new JLabel("New label");
		lblData.setFont(new Font("Exotc350 Bd BT", Font.BOLD, 15));
		lblData.setForeground(new Color(255, 255, 255));
		lblData.setBackground(new Color(255, 255, 255));
		lblData.setBounds(10, 11, 261, 24);
		panel.add(lblData);
		
		lblStatus = new JButton("");
		lblStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				status();
			}
		});
		lblStatus.setIcon(new ImageIcon(login.class.getResource("/img/dbon.png")));
		lblStatus.setBorder(null);
		lblStatus.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblStatus.setContentAreaFilled(false);
		lblStatus.setToolTipText("Status");
		lblStatus.setBounds(365, 0, 79, 46);
		panel.add(lblStatus);

	
		
	}
	private void Logar() {
		String capturaSenha = new String(txtSenha.getPassword());
		
		if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Login");
			txtLogin.requestFocus();
		} else if (capturaSenha.length() == 0) {
			JOptionPane.showMessageDialog(null, "Preencha a senha");
			txtSenha.requestFocus();
		} else {
			//lógica principal
			String read = "select * from usuarios where login=? and senha=md5(?)";
			try {
				con =  dao.conectar();
				pst = con.prepareStatement(read);
				pst.setString(1, txtLogin.getText());
				pst.setString(2, capturaSenha);
				rs = pst.executeQuery();
				if (rs.next()) {
					//capturar perfil de usuario
					//System.out.println(rs.getString(5));//apoio a logica
					//tratamento do perfil do usuario
					String perfil = rs.getString(5);
					if(perfil.equals("admin")) {
						principal.setVisible(true);
						principal.lblUsuario.setText(rs.getString(2));
						this.dispose();
						//habilitar os botões
						principal.btnRelatorios.setEnabled(true);
						principal.btnUsuarios.setEnabled(true);
						//mudar cor rodapé
						principal.panelRodape.setBackground(Color.pink);
					} else {
						principal.setVisible(true);
						principal.lblUsuario.setText(rs.getString(2));
						this.dispose();

					}
					
				} else {
					JOptionPane.showMessageDialog(null, "Usuário e/ou senha Inválido(s)");
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
			private void status() {
				try {
					con = dao.conectar();
					if (con == null) {
						lblStatus.setIcon(new ImageIcon(login.class.getResource("/img/dboff.png")));
					} else {
						lblStatus.setIcon(new ImageIcon(login.class.getResource("/img/dbon.png")));
					}
					con.close();
					
				} catch (Exception e) {
					System.out.println(e);
				
			}
				
	}
}//fim do código
