package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;

import model.DAO;

public class Principal extends JFrame {

	DAO dao = new DAO();
	private Connection con;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblStatus;
	private JButton btnSobre;
	public JPanel panelRodape;
	private JLabel lblData;
	private JButton btnServicos;
	public JButton btnRelatorios;
	public JLabel lblUsuario;
	public JButton btnUsuarios;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
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
	public Principal() {
		setTitle("Pinturas Resident");
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\ryan.vfsouto\\Downloads\\faroo.jpg"));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				status();
				setarData();
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(128, 128, 192));
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		contentPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnUsuarios = new JButton("");
		btnUsuarios.setEnabled(false);
		btnUsuarios.setBackground(new Color(128, 128, 192));
		btnUsuarios.setBorder(new CompoundBorder());
		btnUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				usuarioos usuarios = new usuarioos();
				usuarios.setVisible(true);
			}
		});
		btnUsuarios.setToolTipText("Usuários");
		btnUsuarios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsuarios.setIcon(new ImageIcon(Principal.class.getResource("/img/users.png")));
		btnUsuarios.setBounds(27, 95, 143, 129);
		contentPane.add(btnUsuarios);

		btnSobre = new JButton("");
		btnSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sobre sobre = new Sobre();
				sobre.setVisible(true);
			}
		});
		btnSobre.setContentAreaFilled(false);
		btnSobre.setBorder(null);
		btnSobre.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSobre.setToolTipText("Sobre");
		btnSobre.setIcon(new ImageIcon(Principal.class.getResource("/img/about.png")));
		btnSobre.setBounds(685, 31, 48, 48);
		contentPane.add(btnSobre);

		panelRodape = new JPanel();
		panelRodape.setBackground(new Color(128, 128, 192));
		panelRodape.setBounds(0, 488, 810, 73);
		contentPane.add(panelRodape);
		panelRodape.setLayout(null);

		lblData = new JLabel("");
		lblData.setBackground(Color.BLACK);
		lblData.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblData.setForeground(new Color(255, 255, 255));
		lblData.setBounds(10, 25, 364, 26);
		panelRodape.add(lblData);

		lblStatus = new JLabel("");
		lblStatus.setBounds(662, 11, 48, 48);
		panelRodape.add(lblStatus);
		lblStatus.setForeground(new Color(128, 255, 255));
		lblStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/dboff.png")));

		JLabel lblNewLabel_2 = new JLabel("Usuário:");
		lblNewLabel_2.setForeground(new Color(255, 255, 255));
		lblNewLabel_2.setBounds(236, 25, 88, 26);
		panelRodape.add(lblNewLabel_2);

		lblUsuario = new JLabel("");
		lblUsuario.setForeground(new Color(255, 255, 255));
		lblUsuario.setBounds(290, 25, 137, 25);
		panelRodape.add(lblUsuario);

		JLabel lblNewLabel_1 = new JLabel("Pinturas Resident");
		lblNewLabel_1.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD | Font.ITALIC, 24));
		lblNewLabel_1.setBounds(27, 11, 352, 33);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Principal.class.getResource("/img/icons8-rolo-de-pintura-96.png")));
		lblNewLabel.setBounds(627, 229, 128, 128);
		contentPane.add(lblNewLabel);

		JButton btnClientes = new JButton("");
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Cliente cliente = new Cliente();
				cliente.setVisible(true);
			}
		});
		btnClientes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnClientes.setToolTipText("Clientes");
		btnClientes.setIcon(new ImageIcon(Principal.class.getResource("/img/icons8-clientes-96.png")));
		btnClientes.setBackground(new Color(128, 128, 192));
		btnClientes.setBounds(220, 95, 143, 129);
		contentPane.add(btnClientes);

		btnServicos = new JButton("");
		btnServicos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Servicos servicos = new Servicos();
				servicos.setVisible(true);
			}
		});
		btnServicos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnServicos.setToolTipText("Serviços");
		btnServicos.setIcon(new ImageIcon(Principal.class.getResource("/img/icons8-spray-de-tinta-96.png")));
		btnServicos.setBackground(new Color(128, 128, 192));
		btnServicos.setBounds(27, 288, 143, 129);
		contentPane.add(btnServicos);

		btnRelatorios = new JButton("");
		btnRelatorios.setEnabled(false);
		btnRelatorios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Relatorioss relatorios = new Relatorioss();
				relatorios.setVisible(true);
			}
		});
		btnRelatorios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRelatorios.setToolTipText("Relatorios");
		btnRelatorios.setIcon(new ImageIcon(Principal.class.getResource("/img/icons8-papel-80.png")));
		btnRelatorios.setBackground(new Color(128, 128, 192));
		btnRelatorios.setBounds(220, 288, 143, 129);
		contentPane.add(btnRelatorios);

		JPanel panel_1 = new JPanel();
		panel_1.setForeground(new Color(128, 128, 192));
		panel_1.setBounds(8, 42, 371, 2);
		contentPane.add(panel_1);

		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Fornecedores fornecedores = new Fornecedores();
				fornecedores.setVisible(true);
			}
		});
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.setBackground(new Color(128, 128, 192));
		btnNewButton.setIcon(new ImageIcon("C:\\Users\\ryan.vfsouto\\Downloads\\icons8-fornecedor-96.png"));
		btnNewButton.setToolTipText("Fornecedores");
		btnNewButton.setBounds(405, 95, 143, 129);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				produtos produtos = new produtos();
				produtos.setVisible(true);
			}
		});
		btnNewButton_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_1.setBackground(new Color(128, 128, 192));
		btnNewButton_1.setIcon(new ImageIcon(Principal.class.getResource("/img/icons8-produtos-64.png")));
		btnNewButton_1.setToolTipText("Produtos");
		btnNewButton_1.setBounds(405, 288, 143, 129);
		contentPane.add(btnNewButton_1);
	}

	private void status() {
		try {

			con = dao.conectar();
			if (con == null) {

				lblStatus.setIcon(new ImageIcon(usuarioos.class.getResource("/img/dboff.png")));
			} else {

				lblStatus.setIcon(new ImageIcon(usuarioos.class.getResource("/img/dbon.png")));
			}

			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void setarData() {
		Date data = new Date();
		DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
		lblData.setText(formatador.format(data));
	}
}
