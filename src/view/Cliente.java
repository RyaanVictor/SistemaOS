package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import model.DAO;
import utils.Validador;

@SuppressWarnings("serial")
public class Cliente extends JFrame {

	private JPanel contentPane;
	private JTextField txtNome;
	private JTextField txtBairro;
	private JTextField txtEndereco;
	private JTextField txtFone;

	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	private JTextField txtCep;
	private JTextField txtID;
	private JTextField txtNumero;
	private JTextField txtComplemento;
	private JTextField txtCidade;
	private JButton btnAdicionar;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JButton btnPesquisar;
	@SuppressWarnings("rawtypes")
	private JComboBox cboUf;
	private JScrollPane scrollPane;
	@SuppressWarnings("rawtypes")
	private JList listUsers;
	private JLabel lblNewLabel_5;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cliente frame = new Cliente();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Cliente() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\ryan.vfsouto\\Downloads\\faroo.jpg"));
		setTitle("Pinturas Resident");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBounds(76, 109, 320, 54);
		contentPane.add(scrollPane);
		
		listUsers = new JList();
		scrollPane.setViewportView(listUsers);
		listUsers.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarUsuarioLista();
			}
		});
		listUsers.setBorder(null);

		JLabel lblNewLabel = new JLabel("Nome");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(10, 96, 46, 14);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Bairro");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(254, 260, 46, 14);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Endereço");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(10, 214, 67, 20);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Telefone");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_3.setBounds(10, 136, 98, 14);
		contentPane.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("CEP");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_4.setBounds(11, 177, 46, 14);
		contentPane.add(lblNewLabel_4);

		txtNome = new JTextField();
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarUsuarios();
			}
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
		});
		txtNome.setBounds(76, 95, 320, 20);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		txtNome.setDocument(new Validador(25));

		txtBairro = new JTextField();
		txtBairro.setBounds(295, 259, 190, 20);
		contentPane.add(txtBairro);
		txtBairro.setColumns(10);
		txtBairro.setDocument(new Validador(25));

		txtEndereco = new JTextField();
		txtEndereco.setBounds(87, 212, 168, 20);
		contentPane.add(txtEndereco);
		txtEndereco.setColumns(10);
		txtEndereco.setDocument(new Validador(25));

		txtFone = new JTextField();
		txtFone.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.";
				if (!caracteres.contains(e.getKeyChar()+ "")) {
					e.consume();
				}
			
			}
		});
		txtFone.setBounds(86, 135, 204, 20);
		contentPane.add(txtFone);
		txtFone.setColumns(10);
		txtFone.setDocument(new Validador(12));

		btnAdicionar = new JButton("");
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setBorder(null);
		btnAdicionar.setIcon(new ImageIcon(Cliente.class.getResource("/img/adicionar.png")));
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar.setToolTipText("Adicionar");
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setBounds(10, 415, 89, 54);
		contentPane.add(btnAdicionar);

		JButton btnLimpar = new JButton("");
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setBorder(null);
		btnLimpar.setIcon(new ImageIcon(Cliente.class.getResource("/img/eraser.png")));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setToolTipText("Limpar");
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.setBounds(259, 418, 89, 51);
		contentPane.add(btnLimpar);

		btnExcluir = new JButton("");
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setBorder(null);
		btnExcluir.setIcon(new ImageIcon(Cliente.class.getResource("/img/excluir.png")));
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirContato();
			}
		});
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setToolTipText("Excluir");
		btnExcluir.setBounds(418, 418, 89, 51);
		contentPane.add(btnExcluir);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(128, 128, 192));
		panel.setBounds(-1, 492, 812, 82);
		contentPane.add(panel);
		panel.setLayout(null);

		btnPesquisar = new JButton("");
		btnPesquisar.setContentAreaFilled(false);
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscar();
			}
		});
		btnPesquisar.setToolTipText("Buscar");
		btnPesquisar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPesquisar.setBorder(null);
		btnPesquisar.setIcon(new ImageIcon("C:\\Users\\ryan.vfsouto\\Downloads\\icons8-lupa-48.png"));
		btnPesquisar.setBounds(380, 28, 67, 47);
		contentPane.add(btnPesquisar);

		getRootPane().setDefaultButton(btnPesquisar);

		txtCep = new JTextField();
		txtCep.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.";
				if (!caracteres.contains(e.getKeyChar()+ "")) {
					e.consume();
				}
			
			}
		});
		txtCep.setBounds(86, 173, 179, 20);
		contentPane.add(txtCep);
		txtCep.setColumns(10);
		txtCep.setDocument(new Validador(8));

		JLabel lblNewLabel_1_1 = new JLabel("Pinturas Resident");
		lblNewLabel_1_1.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD | Font.ITALIC, 24));
		lblNewLabel_1_1.setBounds(10, 5, 352, 33);
		contentPane.add(lblNewLabel_1_1);

		btnEditar = new JButton("");
		btnEditar.setContentAreaFilled(false);
		btnEditar.setBorder(null);
		btnEditar.setIcon(new ImageIcon(Cliente.class.getResource("/img/editar.png")));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarContato();
			}
		});
		btnEditar.setToolTipText("Editar");
		btnEditar.setBounds(139, 415, 89, 54);
		contentPane.add(btnEditar);

		JLabel lblNewLabel_4_1 = new JLabel("ID");
		lblNewLabel_4_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_4_1.setBounds(10, 65, 46, 14);
		contentPane.add(lblNewLabel_4_1);

		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setBounds(48, 59, 128, 20);
		contentPane.add(txtID);
		txtID.setColumns(10);

		JLabel lblNewLabel_4_2 = new JLabel("Numero");
		lblNewLabel_4_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_4_2.setBounds(281, 215, 67, 14);
		contentPane.add(lblNewLabel_4_2);

		txtNumero = new JTextField();
		txtNumero.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.";
				if (!caracteres.contains(e.getKeyChar()+ "")) {
					e.consume();
				}
			
			}
		});
		txtNumero.setBounds(347, 214, 86, 20);
		contentPane.add(txtNumero);
		txtNumero.setColumns(10);

		JLabel lblNewLabel_4_3 = new JLabel("Complemento");
		lblNewLabel_4_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_4_3.setBounds(10, 258, 98, 19);
		contentPane.add(lblNewLabel_4_3);

		txtComplemento = new JTextField();
		txtComplemento.setBounds(108, 259, 136, 20);
		contentPane.add(txtComplemento);
		txtComplemento.setColumns(10);

		JLabel lblNewLabel_4_4 = new JLabel("Cidade");
		lblNewLabel_4_4.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_4_4.setBounds(10, 302, 46, 14);
		contentPane.add(lblNewLabel_4_4);

		txtCidade = new JTextField();
		txtCidade.setBounds(87, 301, 213, 20);
		contentPane.add(txtCidade);
		txtCidade.setColumns(10);

		JLabel lblNewLabel_4_5 = new JLabel("UF");
		lblNewLabel_4_5.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_4_5.setBounds(316, 302, 46, 14);
		contentPane.add(lblNewLabel_4_5);

		JButton btnBuscarCep = new JButton("Buscar CEP");
		btnBuscarCep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarCep();
			}
		});
		btnBuscarCep.setBounds(295, 170, 138, 29);
		contentPane.add(btnBuscarCep);

		cboUf = new JComboBox();
		cboUf.setModel(new DefaultComboBoxModel(new String[] {"", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"}));
		cboUf.setBounds(347, 300, 67, 22);
		contentPane.add(cboUf);

		JPanel panel_1 = new JPanel();
		panel_1.setForeground(new Color(128, 128, 192));
		panel_1.setBounds(-1, 37, 274, 1);
		contentPane.add(panel_1);
		
		lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setIcon(new ImageIcon(Cliente.class.getResource("/img/icons8-rolo-de-pintura-96.png")));
		lblNewLabel_5.setBounds(607, 63, 128, 128);
		contentPane.add(lblNewLabel_5);
		
	}

	@SuppressWarnings("unchecked")
	private void listarUsuarios() {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		
		listUsers.setModel(modelo);
		String readLista = "select * from clientes where nome like '"+ txtNome.getText()+ "%'"+ "order by nome";
		try {
			con = dao.conectar();
			
			pst = con.prepareStatement(readLista);
			
			rs = pst.executeQuery();
			while(rs.next()) {
				scrollPane.setVisible(true);
				modelo.addElement(rs.getString(2));
				if(txtNome.getText().isEmpty()) {
					scrollPane.setVisible(false);
				}
			}
			con.close();
			
		}catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private void limparCampos() {
		

		txtID.setText(null);
		txtNome.setText(null);
		txtFone.setText(null);
		txtCep.setText(null);
		txtBairro.setText(null);
		txtComplemento.setText(null);
		txtNumero.setText(null);
		txtEndereco.setText(null);
		txtCidade.setText(null);
		scrollPane.setVisible(false);

	}

	
	private void buscarCep() {
		String logradouro = "";
		String tipoLogradouro = "";
		String resultado = null;
		String cep = txtCep.getText();
		try {
			URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?cep=" + cep + "&formato=xml");
			SAXReader xml = new SAXReader();
			Document documento = xml.read(url);
			Element root = documento.getRootElement();
			for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
				Element element = it.next();
				if (element.getQualifiedName().equals("cidade")) {
					txtCidade.setText(element.getText());
				}
				if (element.getQualifiedName().equals("bairro")) {
					txtBairro.setText(element.getText());
				}
				if (element.getQualifiedName().equals("uf")) {
					cboUf.setSelectedItem(element.getText());
				}
				if (element.getQualifiedName().equals("tipo_logradouro")) {
					tipoLogradouro = element.getText();
				}
				if (element.getQualifiedName().equals("logradouro")) {
					logradouro = element.getText();
				}
				if (element.getQualifiedName().equals("resultado")) {
					resultado = element.getText();
					if (resultado.equals("1")) {
					} else {
						JOptionPane.showMessageDialog(null, "CEP não encontrado");
					}
				}
			}
			txtEndereco.setText(tipoLogradouro + " " + logradouro);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void buscar() {
		String read = "select * from clientes where nome = ?";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(read);
			pst.setString(1, txtNome.getText());
			rs = pst.executeQuery();
			if (rs.next()) {
				txtID.setText(rs.getString(1));
				txtNome.setText(rs.getString(2)); 
				txtFone.setText(rs.getString(3)); 
				txtCep.setText(rs.getString(4));
				txtEndereco.setText(rs.getString(5));
				txtNumero.setText(rs.getString(6));
				txtComplemento.setText(rs.getString(7));
				txtBairro.setText(rs.getString(8)); 
				txtCidade.setText(rs.getString(9));
				cboUf.setSelectedItem("");
				
				

			} else {
				JOptionPane.showMessageDialog(null, "Cliente inexistente");

			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void adicionar() {
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, " Preencha o nome do cliente");
			txtNome.requestFocus();
		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, " Preencha o endereço do cliente");
			txtEndereco.requestFocus();
		} else if (txtBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, " Preencha o email do cliente");
			txtBairro.requestFocus();
		} else if (txtFone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, " Preencha o telefone do cliente");
			txtFone.requestFocus();

		} else if (txtCep.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, " Preencha o CPF do cliente");
			txtCep.requestFocus();

		} else {
			String create = "Insert into clientes(nome,fone,cep,endereco,numero,complemento,bairro,cidade,uf) values (?,?,?,?,?,?,?,?,?)";
			
			try {
				con = dao.conectar();
				pst = con.prepareStatement(create);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtFone.getText());
				pst.setString(3, txtCep.getText());
				pst.setString(4, txtEndereco.getText());
				pst.setString(5, txtNumero.getText());
				pst.setString(6, txtComplemento.getText());
				pst.setString(7, txtBairro.getText());
				pst.setString(8, txtCidade.getText());
				pst.setString(9,cboUf.getSelectedItem().toString());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Cliente adicionado");
				limparCampos();
				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}

		}

	}

	private void editarContato() {
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome do cliente");
			txtNome.requestFocus();
		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o endereço do cliente");
			txtEndereco.requestFocus();
		} else if (txtBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o email do cliente");
			txtBairro.requestFocus();
		} else if (txtFone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o telefone do cliente");
			txtFone.requestFocus();
		} else if (txtCep.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o CPF do usuario");
			txtCep.requestFocus();
		}
		{
			String update = "update clientes set nome=?,fone=?,cep=?,endereco=?,numero=?,complemento=?,bairro=?,cidade=?,uf=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(update);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtFone.getText());
				pst.setString(3, txtCep.getText());
				pst.setString(4, txtEndereco.getText());
				pst.setString(5, txtNumero.getText());
				pst.setString(6, txtComplemento.getText());
				pst.setString(7, txtBairro.getText());
				pst.setString(8, txtCidade.getText());
				pst.setString(9,cboUf.getSelectedItem().toString());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Dados do cliente editados com sucesso.");
				limparCampos();

				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}

	private void excluirContato() {
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste cliente?","Atenção!!",JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			String delete = "delete from clientes where idcli=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(delete);
				pst.setString(1, txtID.getText());
				pst.executeUpdate();
				limparCampos();
				btnPesquisar.setEnabled(true);
				JOptionPane .showMessageDialog(null, "Cliente excluido");
				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "Não foi Possível Excluir o Cliente!\nHá um Serviço Pendente.");
			} catch (Exception e) {
				System.out.println(e);
			}
		}
			
		
	}
	private void buscarUsuarioLista() {
		int linha = listUsers.getSelectedIndex();
		if (linha>= 0) {
			String readlistaUsuario = "select * from clientes where nome like '" + txtNome.getText() + "%'" + "order by nome limit " + (linha) + " , 1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readlistaUsuario);
				rs = pst.executeQuery();
				if (rs.next()) {
					scrollPane.setVisible(false);
					txtID.setText(rs.getString(1)); 
					txtNome.setText(rs.getString(2));
					txtFone.setText(rs.getString(3)); 
					txtCep.setText(rs.getString(4));
					txtEndereco.setText(rs.getString(5));
					txtNumero.setText(rs.getString(6));
					txtComplemento.setText(rs.getString(7));
					txtBairro.setText(rs.getString(8));
					txtCidade.setText(rs.getString(9));
					cboUf.setSelectedItem(rs.getString(10));  
					} else {
					JOptionPane.showMessageDialog(null, "Usuário inexistente");
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			scrollPane.setVisible(false);
			
			
		}
		
	}
	}
