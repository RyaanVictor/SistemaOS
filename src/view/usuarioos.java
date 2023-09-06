package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import model.DAO;
import utils.Validador;

public class usuarioos extends JDialog {

	private static final long serialVersionUID = 1L;
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private JTextField textID;
	private JTextField textNome;
	private JTextField textLogin;
	private JPasswordField textSenha;
	private JButton btnPesquisar;
	private JButton btnAdicionar;
	private JButton btnLimparCampos;
	private JButton btnEditar;
	private JButton btnExcluir;
	@SuppressWarnings("rawtypes")
	private JList listaUsuarios;
	private JScrollPane scrollPaneUsuarios;
	@SuppressWarnings("rawtypes")
	private JList listaUsuario;
	private JLabel lblPerfil;
	@SuppressWarnings("rawtypes")
	private JComboBox Combo;
	private JCheckBox chckSenha;
	private JLabel lblNewLabel;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					usuarioos dialog = new usuarioos();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public usuarioos() {
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				scrollPaneUsuarios.setVisible(false);
				textNome.setText(null);
			}
		});
		setTitle("Usuários");
		setBounds(100, 100, 800, 600);

		scrollPaneUsuarios = new JScrollPane();
		scrollPaneUsuarios.setBounds(68, 259, 144, 52);
		scrollPaneUsuarios.setVisible(false);
		getContentPane().setLayout(null);
		scrollPaneUsuarios.setBorder(null);
		getContentPane().add(scrollPaneUsuarios);

		listaUsuario = new JList();
		scrollPaneUsuarios.setViewportView(listaUsuario);
		listaUsuario.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				BuscarUsuarioLista();
			}
		});

		JLabel txtid = new JLabel("ID");
		txtid.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtid.setBounds(12, 37, 23, 14);
		getContentPane().add(txtid);

		JLabel txtnome = new JLabel("Nome");
		txtnome.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtnome.setBounds(12, 259, 97, 14);
		getContentPane().add(txtnome);

		JLabel txtlogin = new JLabel("Login");
		txtlogin.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtlogin.setBounds(12, 201, 50, 20);
		getContentPane().add(txtlogin);

		JLabel txtsenha = new JLabel("Senha");
		txtsenha.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtsenha.setBounds(12, 154, 69, 14);
		getContentPane().add(txtsenha);

		textID = new JTextField();
		textID.setBounds(53, 36, 80, 20);
		textID.setEditable(false);
		getContentPane().add(textID);
		textID.setColumns(10);

		textNome = new JTextField();
		textNome.setBounds(68, 251, 137, 34);
		textNome.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		textNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

				listarUsuarios();

			}

		});
		getContentPane().add(textNome);
		textNome.setColumns(10);
		textNome.setDocument(new Validador(20));

		textLogin = new JTextField();
		textLogin.setBounds(68, 196, 137, 34);
		getContentPane().add(textLogin);
		textLogin.setColumns(10);
		textLogin.setDocument(new Validador(10));

		textID = new JTextField();
		textID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.";

				if (!caracteres.contains(e.getKeyChar() + "")) {

					e.consume();
				}

			}

		});

		btnPesquisar = new JButton("");
		btnPesquisar.setBorderPainted(false);
		btnPesquisar.setBounds(160, 8, 80, 68);
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Buscar();
			}
		});
		btnPesquisar.setToolTipText("Pesquisar");
		btnPesquisar.setBorder(null);
		btnPesquisar.setContentAreaFilled(false);
		btnPesquisar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPesquisar.setIcon(new ImageIcon(usuarioos.class.getResource("/img/pesquisar.png")));
		getContentPane().add(btnPesquisar);

		btnLimparCampos = new JButton("");
		btnLimparCampos.setBorderPainted(false);
		btnLimparCampos.setBounds(255, 11, 69, 65);
		btnLimparCampos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LimparCampos();
			}
		});
		btnLimparCampos.setIcon(new ImageIcon(usuarioos.class.getResource("/img/eraser.png")));
		btnLimparCampos.setToolTipText("LimparCampos");
		btnLimparCampos.setContentAreaFilled(false);
		btnLimparCampos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		getContentPane().add(btnLimparCampos);

		textSenha = new JPasswordField();
		textSenha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textSenha.setText(null);
				textSenha.requestFocus();
				textSenha.setBackground(Color.YELLOW);
			}
		});
		textSenha.setBounds(73, 150, 104, 27);
		getContentPane().add(textSenha);
		getRootPane().setDefaultButton(btnPesquisar);

		btnAdicionar = new JButton("");
		btnAdicionar.setBorderPainted(false);
		btnAdicionar.setBounds(12, 362, 80, 80);
		btnAdicionar.setEnabled(false);
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar.setBorder(null);
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setToolTipText("Adicionar");
		btnAdicionar.setIcon(new ImageIcon(usuarioos.class.getResource("/img/adicionar.png")));
		getContentPane().add(btnAdicionar);

		btnEditar = new JButton("");
		btnEditar.setBorderPainted(false);
		btnEditar.setBounds(160, 362, 69, 80);
		btnEditar.setEnabled(false);
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckSenha.isSelected()) {
					editarUsuario();
				} else {
					editarUsuarioExcetoSenha();
				}
			}
		});
		btnEditar.setBorder(null);
		btnEditar.setContentAreaFilled(false);
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setIcon(new ImageIcon(usuarioos.class.getResource("/img/editar.png")));
		btnEditar.setToolTipText("Editar");
		getContentPane().add(btnEditar);

		btnExcluir = new JButton("");
		btnExcluir.setBorderPainted(false);
		btnExcluir.setBounds(329, 366, 69, 76);
		btnExcluir.setEnabled(false);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirContato();
			}
		});
		btnExcluir.setIcon(new ImageIcon(usuarioos.class.getResource("/img/excluir.png")));
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setBorder(null);
		btnExcluir.setToolTipText("Excluir");
		getContentPane().add(btnExcluir);

		listaUsuarios = new JList();
		listaUsuarios.setBounds(605, 174, -92, -58);
		getContentPane().add(listaUsuarios);
		listaUsuarios.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				BuscarUsuarioLista();
			}
		});
		listaUsuarios.setBorder(null);

		lblPerfil = new JLabel("Perfil:");
		lblPerfil.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblPerfil.setBounds(22, 102, 70, 14);
		getContentPane().add(lblPerfil);

		Combo = new JComboBox();
		Combo.setModel(new DefaultComboBoxModel(new String[] { "", "admin", "usuario" }));
		Combo.setBounds(68, 96, 80, 30);
		getContentPane().add(Combo);

		chckSenha = new JCheckBox("Alterar Senha");
		chckSenha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckSenha.isSelected()) {
					textSenha.setText(null);
					textSenha.requestFocus();
					textSenha.setBackground(Color.YELLOW);
				} else {
					textSenha.setBackground(Color.WHITE);

				}
			}
		});
		chckSenha.setBounds(200, 152, 124, 23);
		getContentPane().add(chckSenha);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(128, 128, 192));
		panel.setBounds(0, 493, 811, 68);
		getContentPane().add(panel);

		JLabel lblNewLabel_3 = new JLabel("Ryan Victor Ferreira Souto");
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setBackground(Color.BLACK);
		lblNewLabel_3.setBounds(10, 11, 173, 14);
		panel.add(lblNewLabel_3);

		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(usuarioos.class.getResource("/img/icons8-rolo-de-pintura-96.png")));
		lblNewLabel.setBounds(570, 39, 128, 128);
		getContentPane().add(lblNewLabel);
	}

	@SuppressWarnings("unchecked")
	private void listarUsuarios() {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listaUsuario.setModel(modelo);
		String readlista = "select * from usuarios where nome like '" + textNome.getText() + "%'" + "order by nome";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readlista);
			rs = pst.executeQuery();
			while (rs.next()) {
				scrollPaneUsuarios.setVisible(true);
				modelo.addElement(rs.getString(2));
				if (textNome.getText().isEmpty()) {
					scrollPaneUsuarios.setVisible(false);
				}
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void LimparCampos() {
		textID.setText(null);
		textNome.setText(null);
		textLogin.setText(null);
		textSenha.setText(null);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		btnPesquisar.setEnabled(true);
		btnAdicionar.setEnabled(false);
		scrollPaneUsuarios.setVisible(false);
		Combo.setSelectedItem("");
		textSenha.setBackground(getForeground());
		chckSenha.setSelected(false);

	}

	private void Buscar() {

		String read = "select * from usuarios where login = ?";

		try {

			con = dao.conectar();
			pst = con.prepareStatement(read);
			pst.setString(1, textLogin.getText());
			rs = pst.executeQuery();

			if (rs.next()) {
				textID.setText(rs.getString(1));
				textNome.setText(rs.getString(2));
				textLogin.setText(rs.getString(3));
				textSenha.setText(rs.getString(4));
				Combo.setSelectedItem(rs.getString(5));
				btnExcluir.setEnabled(true);
				btnEditar.setEnabled(true);
				btnPesquisar.setEnabled(true);

			} else {
				JOptionPane.showMessageDialog(null, "Usuário inexistente");
				btnAdicionar.setEnabled(true);
				btnPesquisar.setEnabled(false);
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@SuppressWarnings("deprecation")
	private void adicionar() {
		if (textNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do Usuário");
			textNome.requestFocus();
		} else if (textLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o login do Usuário");
			textLogin.requestFocus();
		} else if (textSenha.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Senha do Usuário");
			textSenha.requestFocus();
		} else {
			String create = "insert into usuarios(nome,login,senha,perfil) values (?,?,md5(?),?)";

			try {
				con = dao.conectar();
				pst = con.prepareStatement(create);
				pst.setString(1, textNome.getText());
				pst.setString(2, textLogin.getText());
				pst.setString(3, textSenha.getText());
				pst.setString(4, Combo.getSelectedItem().toString());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Usuário adicionado");
				LimparCampos();
				con.close();
			} catch (Exception e) {
				System.out.println(e);

			}

		}

	}

	@SuppressWarnings("deprecation")
	private void editarUsuario() {

		String update2 = "update usuarios set nome=?,login=?,senha=md5(?),perfil=? where id=?";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(update2);
			pst.setString(1, textNome.getText());
			pst.setString(2, textLogin.getText());
			pst.setString(3, textSenha.getText());
			pst.setString(4, Combo.getSelectedItem().toString());
			pst.setString(5, textID.getText());
			pst.executeUpdate();
			JOptionPane.showInternalMessageDialog(null, "Dados do Usuário editados com Sucesso");
			LimparCampos();
			con.close();

		} catch (Exception e) {
			System.out.println(e);

		}
	}

	private void editarUsuarioExcetoSenha() {

		String update2 = "update usuarios set nome=?,login=?,perfil=? where id=?";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(update2);
			pst.setString(1, textNome.getText());
			pst.setString(2, textLogin.getText());
			pst.setString(3, Combo.getSelectedItem().toString());
			pst.setString(4, textID.getText());
			pst.executeUpdate();
			JOptionPane.showInternalMessageDialog(null, "Dados do Usuário editados com Sucesso");
			LimparCampos();
			con.close();

		} catch (Exception e) {
			System.out.println(e);

		}
	}

	private void excluirContato() {
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste Usuário?", "Atenção!",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			String delete = "delete from usuarios where id=?";

			try {
				con = dao.conectar();
				pst = con.prepareStatement(delete);
				pst.setString(1, textID.getText());
				pst.executeUpdate();
				LimparCampos();
				JOptionPane.showMessageDialog(null, "Usuário Excluído");
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}

		}

	}

	private void BuscarUsuarioLista() {
		int linha = listaUsuario.getSelectedIndex();
		if (linha > 0) {
			String readlistaUsuario = "select * from usuarios where nome like '" + textNome.getText() + "%'"
					+ "order by nome limit " + (linha) + ", 1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readlistaUsuario);
				rs = pst.executeQuery();
				if (rs.next()) {
					scrollPaneUsuarios.setVisible(false);
					textID.setText(rs.getString(1));
					textNome.setText(rs.getString(2));
					textLogin.setText(rs.getString(3));
					textSenha.setText(rs.getString(4));
					Combo.setSelectedItem(rs.getString(5));
				} else {
					JOptionPane.showMessageDialog(null, "Usuário Inexistente");
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}

		} else {
			scrollPaneUsuarios.setVisible(false);

		}

	}
}
