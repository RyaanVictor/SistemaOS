package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.toedter.calendar.JDateChooser;

import model.DAO;
import utils.Validador;

public class produtos extends JDialog {

	private static final long serialVersionUID = 1L;
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	private FileInputStream fis;
	private int tamanho;

	private boolean produtoCadastrado;

	private JLabel lblFoto;
	private JTextField txtCodigo;
	private JTextField txtBarcode;
	private JTextField txtLote;
	private JTextField txtEstoque;
	private JTextField txtEstoquemin;
	private JTextField txtCusto;
	private JTextField txtLocal;
	private JButton btnLimpar;
	private JButton btnEditar;
	private JButton btnAdicionar;
	private JButton btnExcluir;
	@SuppressWarnings("rawtypes")
	private JComboBox cboUN;
	private JTextField txtNome;
	private JLabel lblNewLabel_1;
	private JTextField txtFabricante;
	private JTextField txtIdfor;
	private JTextField txtLucro;
	private JTextArea txtDescricao;
	private JButton btnPesquisarProduto;
	private JDateChooser dateEntrada;
	private JDateChooser dateValidade;
	private JTextField txtFornecedor;
	private JPanel panelFor;
	@SuppressWarnings("rawtypes")
	private JList listFor;
	private JScrollPane scrollPaneFor;
	private JScrollPane scrollPanePr;
	@SuppressWarnings("rawtypes")
	private JList listPr;
	private JButton btnCarregar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					produtos dialog = new produtos();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public produtos() {
		getContentPane().setBackground(SystemColor.scrollbar);
		setTitle("Produtos");
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(null);

		scrollPanePr = new JScrollPane();
		scrollPanePr.setVisible(false);
		scrollPanePr.setBorder(null);
		scrollPanePr.setBounds(69, 128, 188, 60);
		getContentPane().add(scrollPanePr);

		listPr = new JList();
		listPr.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarProdutoLista();
			}
		});
		scrollPanePr.setViewportView(listPr);

		panelFor = new JPanel();
		panelFor.setBackground(SystemColor.scrollbar);
		panelFor.setBorder(
				new TitledBorder(new TitledBorder(
						new TitledBorder(
								new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255),
										new Color(160, 160, 160)),
								"", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)),
						"", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)), "Fornecedor",
						TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelFor.setBounds(419, 0, 307, 84);
		getContentPane().add(panelFor);
		panelFor.setLayout(null);

		txtIdfor = new JTextField();
		txtIdfor.setEnabled(false);
		txtIdfor.setBounds(211, 38, 86, 20);
		panelFor.add(txtIdfor);
		txtIdfor.setColumns(10);

		JLabel lblNewLabel_10 = new JLabel("ID");
		lblNewLabel_10.setBounds(185, 41, 46, 14);
		panelFor.add(lblNewLabel_10);

		txtFornecedor = new JTextField();
		txtFornecedor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarFornecedor();
			}
		});
		txtFornecedor.setBounds(10, 20, 165, 20);
		panelFor.add(txtFornecedor);
		txtFornecedor.setColumns(10);

		scrollPaneFor = new JScrollPane();
		scrollPaneFor.setBounds(10, 39, 165, 34);
		panelFor.add(scrollPaneFor);
		scrollPaneFor.setVisible(false);
		scrollPaneFor.setBorder(null);

		listFor = new JList();
		listFor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarFornecedorLista();
			}
		});
		listFor.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		scrollPaneFor.setViewportView(listFor);

		lblFoto = new JLabel("      ");
		lblFoto.setHorizontalAlignment(SwingConstants.CENTER);
		lblFoto.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblFoto.setIcon(new ImageIcon(produtos.class.getResource("/img/icons8-câmera-96.png")));
		lblFoto.setBounds(489, 97, 256, 256);
		getContentPane().add(lblFoto);

		btnCarregar = new JButton("Carregar Foto");
		btnCarregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				carregarFoto();
			}
		});
		btnCarregar.setForeground(SystemColor.textHighlight);
		btnCarregar.setBounds(553, 364, 142, 23);
		getContentPane().add(btnCarregar);

		JLabel lblNewLabel = new JLabel("Código do produto");
		lblNewLabel.setBounds(10, 14, 116, 14);
		getContentPane().add(lblNewLabel);

		txtCodigo = new JTextField();
		txtCodigo.setBounds(120, 11, 56, 20);
		getContentPane().add(txtCodigo);
		txtCodigo.setColumns(10);

		txtBarcode = new JTextField();
		txtBarcode.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					pesquisarProdutoBarcode();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.',";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtBarcode.setBounds(68, 61, 143, 23);
		getContentPane().add(txtBarcode);
		txtBarcode.setColumns(10);
		txtBarcode.setDocument(new Validador(13));

		lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(produtos.class.getResource("/img/icons8-código-de-barras-50.png")));
		lblNewLabel_1.setBounds(10, 50, 50, 45);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Lote");
		lblNewLabel_2.setBounds(15, 143, 58, 14);
		getContentPane().add(lblNewLabel_2);

		txtLote = new JTextField();
		txtLote.setBounds(69, 140, 142, 14);
		getContentPane().add(txtLote);
		txtLote.setColumns(10);
		txtLote.setDocument(new Validador(50));

		JLabel lblNewLabel_3 = new JLabel("Estoque");
		lblNewLabel_3.setBounds(10, 267, 46, 14);
		getContentPane().add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Estoque Minimo");
		lblNewLabel_4.setBounds(302, 190, 102, 14);
		getContentPane().add(lblNewLabel_4);

		txtEstoque = new JTextField();
		txtEstoque.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtEstoque.setBounds(78, 264, 86, 20);
		getContentPane().add(txtEstoque);
		txtEstoque.setColumns(10);
		txtEstoque.setDocument(new Validador(15));

		txtEstoquemin = new JTextField();
		txtEstoquemin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtEstoquemin.setBounds(302, 215, 86, 20);
		getContentPane().add(txtEstoquemin);
		txtEstoquemin.setColumns(10);
		txtEstoquemin.setDocument(new Validador(15));

		txtCusto = new JTextField();
		txtCusto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtCusto.setBounds(60, 326, 86, 20);
		getContentPane().add(txtCusto);
		txtCusto.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel("Custo");
		lblNewLabel_5.setBounds(14, 329, 46, 14);
		getContentPane().add(lblNewLabel_5);

		cboUN = new JComboBox();
		cboUN.setModel(new DefaultComboBoxModel(new String[] { "UN", "CX", "PC", "KG", "M" }));
		cboUN.setBounds(312, 246, 56, 22);
		getContentPane().add(cboUN);

		JLabel lblNewLabel_6 = new JLabel("Local de armazenagem");
		lblNewLabel_6.setBounds(10, 298, 136, 14);
		getContentPane().add(lblNewLabel_6);

		btnLimpar = new JButton("");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setToolTipText("Limpar Campos");
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setBorder(null);
		btnLimpar.setIcon(new ImageIcon(produtos.class.getResource("/img/eraser.png")));
		btnLimpar.setBounds(10, 399, 89, 60);
		getContentPane().add(btnLimpar);

		btnEditar = new JButton("");
		btnEditar.setEnabled(false);
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editar();
			}
		});
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setContentAreaFilled(false);
		btnEditar.setBorder(null);
		btnEditar.setToolTipText("Editar Produto");
		btnEditar.setIcon(new ImageIcon(produtos.class.getResource("/img/editar.png")));
		btnEditar.setBounds(110, 399, 89, 60);
		getContentPane().add(btnEditar);

		btnAdicionar = new JButton("");
		btnAdicionar.setEnabled(false);
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar.setToolTipText("Adicionar Produto");
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setBorder(null);
		btnAdicionar.setIcon(new ImageIcon(produtos.class.getResource("/img/adicionar.png")));
		btnAdicionar.setBounds(203, 399, 89, 60);
		getContentPane().add(btnAdicionar);

		btnExcluir = new JButton("");
		btnExcluir.setEnabled(false);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluir();
			}
		});
		btnExcluir.setToolTipText("Excluir Produto");
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setBorder(null);
		btnExcluir.setIcon(new ImageIcon(produtos.class.getResource("/img/excluir.png")));
		btnExcluir.setBounds(302, 399, 89, 60);
		getContentPane().add(btnExcluir);

		JLabel lblNewLabel_7 = new JLabel("Produto");
		lblNewLabel_7.setBounds(10, 106, 65, 26);
		getContentPane().add(lblNewLabel_7);

		txtNome = new JTextField();
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarProdutos();
			}
		});
		txtNome.setBounds(69, 109, 188, 20);
		getContentPane().add(txtNome);
		txtNome.setColumns(10);
		txtNome.setDocument(new Validador(30));

		JLabel lblNewLabel_8 = new JLabel("Descrição");
		lblNewLabel_8.setBounds(10, 190, 65, 14);
		getContentPane().add(lblNewLabel_8);

		txtDescricao = new JTextArea();
		txtDescricao.setBounds(71, 168, 188, 48);
		getContentPane().add(txtDescricao);

		txtFabricante = new JTextField();
		txtFabricante.setBounds(71, 227, 171, 20);
		getContentPane().add(txtFabricante);
		txtFabricante.setColumns(10);

		JLabel lblNewLabel_9 = new JLabel("Fabricante");
		lblNewLabel_9.setBounds(10, 230, 65, 14);
		getContentPane().add(lblNewLabel_9);

		txtLucro = new JTextField();
		txtLucro.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.',";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtLucro.setText("0");
		txtLucro.setBounds(203, 326, 65, 20);
		getContentPane().add(txtLucro);
		txtLucro.setColumns(10);

		JLabel lblNewLabel_11 = new JLabel("Lucro");
		lblNewLabel_11.setBounds(165, 329, 46, 14);
		getContentPane().add(lblNewLabel_11);

		JLabel lblNewLabel_12 = new JLabel("%");
		lblNewLabel_12.setBounds(271, 329, 37, 14);
		getContentPane().add(lblNewLabel_12);

		JLabel lblNewLabel_13 = new JLabel("Validade");
		lblNewLabel_13.setBounds(10, 361, 63, 14);
		getContentPane().add(lblNewLabel_13);

		JLabel lblNewLabel_14 = new JLabel("Entrada");
		lblNewLabel_14.setBounds(323, 126, 46, 14);
		getContentPane().add(lblNewLabel_14);

		txtLocal = new JTextField();
		txtLocal.setBounds(136, 295, 137, 20);
		getContentPane().add(txtLocal);
		txtLocal.setColumns(10);
		txtLocal.setDocument(new Validador(30));

		dateEntrada = new JDateChooser();
		dateEntrada.setBounds(296, 143, 108, 20);
		getContentPane().add(dateEntrada);
		dateEntrada.setEnabled(false);

		dateValidade = new JDateChooser();
		dateValidade.setBounds(60, 357, 108, 20);
		getContentPane().add(dateValidade);

		btnPesquisarProduto = new JButton("Pesquisar");
		btnPesquisarProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisarProduto();
			}

		});
		btnPesquisarProduto.setBounds(186, 10, 116, 23);
		getContentPane().add(btnPesquisarProduto);

		JPanel panelRodape = new JPanel();
		panelRodape.setLayout(null);
		panelRodape.setBackground(new Color(128, 128, 192));
		panelRodape.setBounds(0, 488, 799, 73);
		getContentPane().add(panelRodape);

	}

	@SuppressWarnings("unchecked")
	private void listarProdutos() {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listPr.setModel(modelo);
		String readListaPr = "select * from produtos where produto like '" + txtNome.getText() + "%'"
				+ "order by produto";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readListaPr);
			rs = pst.executeQuery();
			while (rs.next()) {
				scrollPanePr.setVisible(true);
				modelo.addElement(rs.getString(3));
				if (txtNome.getText().isEmpty()) {
					scrollPanePr.setVisible(false);
				}
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void buscarProdutoLista() {
		int linha = listPr.getSelectedIndex();
		if (linha >= 0) {
			String readListaCli = "select * from produtos where produto like '" + txtNome.getText() + "%'"
					+ "order by produto limit " + (linha) + " , 1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readListaCli);
				rs = pst.executeQuery();
				if (rs.next()) {
					scrollPanePr.setVisible(false);
					txtCodigo.setText(rs.getString(1));
					txtBarcode.setText(rs.getString(2));
					txtNome.setText(rs.getString(3));
					txtLote.setText(rs.getString(4));
					txtDescricao.setText(rs.getString(5));
					Blob blob = (Blob) rs.getBlob(6);
					byte[] img = blob.getBytes(1, (int) blob.length());
					BufferedImage imagem = null;
					try {
						imagem = ImageIO.read(new ByteArrayInputStream(img));
					} catch (Exception e) {
						System.out.println(e);
					}
					ImageIcon icone = new ImageIcon(imagem);
					Icon foto = new ImageIcon(icone.getImage().getScaledInstance(lblFoto.getWidth(),
							lblFoto.getHeight(), Image.SCALE_SMOOTH));
					lblFoto.setIcon(foto);
					txtFabricante.setText(rs.getString(7));
					String setarDataEnt = rs.getString(8);
					Date dataEntrada = new SimpleDateFormat("yyyy-mm-dd").parse(setarDataEnt);
					dateEntrada.setDate(dataEntrada);
					String setarDataVal = rs.getString(9);
					Date dataValidade = new SimpleDateFormat("yyyy-mm-dd").parse(setarDataVal);
					dateValidade.setDate(dataValidade);
					txtEstoque.setText(rs.getString(10));
					txtEstoquemin.setText(rs.getString(11));
					cboUN.setSelectedItem(rs.getString(12));
					txtLocal.setText(rs.getString(13));
					txtCusto.setText(rs.getString(14));
					txtLucro.setText(rs.getString(15));
					txtIdfor.setText(rs.getString(16));
					produtoCadastrado = true;
					btnPesquisarProduto.setEnabled(false);
					btnCarregar.setEnabled(true);
					btnExcluir.setEnabled(true);
					dateValidade.setEnabled(false);
					panelFor.setEnabled(false);
					txtFornecedor.setEnabled(false);
				} else {
					JOptionPane.showMessageDialog(null, "Produto inexistente");
					produtoCadastrado = false;
					btnCarregar.setEnabled(false);
					txtNome.requestFocus();
					btnCarregar.setEnabled(true);
					btnPesquisarProduto.setEnabled(false);
					cboUN.setSelectedItem("");
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			scrollPanePr.setVisible(false);
		}
	}

	@SuppressWarnings("unchecked")
	private void listarFornecedor() {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listFor.setModel(modelo);
		String readListaFor = "select * from fornecedores where razao like '" + txtFornecedor.getText() + "%'"
				+ "order by razao";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readListaFor);
			rs = pst.executeQuery();
			while (rs.next()) {
				scrollPaneFor.setVisible(true);
				modelo.addElement(rs.getString(2));
				if (txtFornecedor.getText().isEmpty()) {
					scrollPaneFor.setVisible(false);
				}
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void carregarFoto() {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Selecionar Arquivo");
		jfc.setFileFilter(new FileNameExtensionFilter("Arquivo de imagens(*.PNG,*.JPG,*.JPEG)", "png", "jpg", "jpeg"));
		int resultado = jfc.showOpenDialog(this);
		if (resultado == JFileChooser.APPROVE_OPTION) {
			try {
				fis = new FileInputStream(jfc.getSelectedFile());
				tamanho = (int) jfc.getSelectedFile().length();
				Image foto = ImageIO.read(jfc.getSelectedFile()).getScaledInstance(lblFoto.getWidth(),
						lblFoto.getHeight(), Image.SCALE_SMOOTH);
				lblFoto.setIcon(new ImageIcon(foto));
				lblFoto.updateUI();
				if (produtoCadastrado) {
					btnEditar.setEnabled(true);
					btnAdicionar.setEnabled(false);
				} else {
					btnEditar.setEnabled(false);
					btnAdicionar.setEnabled(true);
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}

	private void limparCampos() {
		txtCodigo.setText(null);
		txtBarcode.setText(null);
		txtLote.setText(null);
		txtEstoque.setText(null);
		txtNome.setText(null);
		txtEstoquemin.setText(null);
		txtCusto.setText(null);
		cboUN.setSelectedItem(null);
		lblFoto.setIcon(new ImageIcon(produtos.class.getResource("/img/icons8-câmera-96.png")));
		txtLocal.setText(null);
		txtFornecedor.setText(null);
		txtDescricao.setText(null);
		txtFabricante.setText(null);
		txtLucro.setText("0");
		dateValidade.setDate(null);
		dateEntrada.setDate(null);
		txtIdfor.setText(null);
		txtBarcode.requestFocus();
		produtoCadastrado = false;
		btnCarregar.setEnabled(false);
		btnAdicionar.setEnabled(false);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		txtFornecedor.setEnabled(true);
		txtBarcode.requestFocus();
		dateValidade.setEnabled(true);
		panelFor.setEnabled(true);
		btnPesquisarProduto.setEnabled(true);
	}

	private void buscarFornecedorLista() {
		int linha = listFor.getSelectedIndex();
		if (linha >= 0) {
			String readListaFor = "select * from fornecedores where razao like '" + txtFornecedor.getText() + "%'"
					+ "order by razao limit " + (linha) + " , 1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readListaFor);
				rs = pst.executeQuery();
				if (rs.next()) {
					scrollPaneFor.setVisible(false);
					txtIdfor.setText(rs.getString(1));
					txtFornecedor.setText(rs.getString(2));
				} else {
					JOptionPane.showMessageDialog(null, "Fornecedor inexistente");
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			scrollPaneFor.setVisible(false);
		}
	}

	private void pesquisarProduto() {
		if (txtCodigo.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o código!");
			txtCodigo.requestFocus();
		} else {
			String readCodigo = "select * from produtos where codigo =?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readCodigo);
				pst.setString(1, txtCodigo.getText());
				rs = pst.executeQuery();
				if (rs.next()) {
					txtBarcode.setText(rs.getString(2));
					txtNome.setText(rs.getString(3));
					txtLote.setText(rs.getString(4));
					// area do texto deve ser utilizado getNString
					txtDescricao.setText(rs.getNString(5));
					Blob blob = (Blob) rs.getBlob(6);
					byte[] img = blob.getBytes(1, (int) blob.length());
					BufferedImage imagem = null;
					try {
						imagem = ImageIO.read(new ByteArrayInputStream(img));
					} catch (Exception e) {
						System.out.println(e);
					}
					ImageIcon icone = new ImageIcon(imagem);
					Icon foto = new ImageIcon(icone.getImage().getScaledInstance(lblFoto.getWidth(),
							lblFoto.getHeight(), Image.SCALE_SMOOTH));
					lblFoto.setIcon(foto);
					txtFabricante.setText(rs.getString(7));
					String setarDataEnt = rs.getString(8);
					Date dataEntrada = new SimpleDateFormat("yyyy-MM-dd").parse(setarDataEnt);
					dateEntrada.setDate(dataEntrada);
					String setarDataVal = rs.getString(9);
					Date dataValidade = new SimpleDateFormat("yyyy-MM-dd").parse(setarDataVal);
					dateValidade.setDate(dataValidade);
					// --------------------------------------------------------------------------
					txtEstoque.setText(rs.getString(10));
					txtEstoquemin.setText(rs.getString(11));
					cboUN.setSelectedItem(rs.getString(12));
					txtLocal.setText(rs.getString(13));
					txtCusto.setText(rs.getString(14));
					txtLucro.setText(rs.getString(15));
					txtIdfor.setText(rs.getString(16));
					produtoCadastrado = true;
					btnPesquisarProduto.setEnabled(false);
					btnCarregar.setEnabled(true);
					btnExcluir.setEnabled(true);
					dateValidade.setEnabled(false);
					panelFor.setEnabled(false);
					txtFornecedor.setEnabled(false);
				} else {
					JOptionPane.showMessageDialog(null, "Produto não Encontrado");
					produtoCadastrado = false;
					btnCarregar.setEnabled(false);
					txtNome.requestFocus();
					btnCarregar.setEnabled(true);
					btnPesquisarProduto.setEnabled(false);
					cboUN.setSelectedItem("");
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void pesquisarProdutoBarcode() {
		if (txtBarcode.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o código de barras!");
			txtBarcode.requestFocus();
		} else {
			String readCodigo = "select * from produtos where barcode =?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readCodigo);
				pst.setString(1, txtBarcode.getText());
				rs = pst.executeQuery();
				if (rs.next()) {
					txtCodigo.setText(rs.getString(1));
					txtNome.setText(rs.getString(3));
					txtLote.setText(rs.getString(4));
					txtDescricao.setText(rs.getNString(5));
					Blob blob = (Blob) rs.getBlob(6);
					byte[] img = blob.getBytes(1, (int) blob.length());
					BufferedImage imagem = null;
					try {
						imagem = ImageIO.read(new ByteArrayInputStream(img));
					} catch (Exception e) {
						System.out.println(e);
					}
					ImageIcon icone = new ImageIcon(imagem);
					Icon foto = new ImageIcon(icone.getImage().getScaledInstance(lblFoto.getWidth(),
							lblFoto.getHeight(), Image.SCALE_SMOOTH));
					lblFoto.setIcon(foto);
					txtFabricante.setText(rs.getString(7));
					String setarDataEnt = rs.getString(8);
					Date dataEntrada = new SimpleDateFormat("yyyy-MM-dd").parse(setarDataEnt);
					dateEntrada.setDate(dataEntrada);
					String setarDataVal = rs.getString(9);
					Date dataValidade = new SimpleDateFormat("yyyy-MM-dd").parse(setarDataVal);
					dateValidade.setDate(dataValidade);
					// --------------------------------------------------------------------------
					txtEstoque.setText(rs.getString(10));
					txtEstoquemin.setText(rs.getString(11));
					cboUN.setSelectedItem(rs.getString(12));
					txtLocal.setText(rs.getString(13));
					txtCusto.setText(rs.getString(14));
					txtLucro.setText(rs.getString(15));
					txtIdfor.setText(rs.getString(16));
					produtoCadastrado = true;
					btnPesquisarProduto.setEnabled(false);
					btnCarregar.setEnabled(true);
					btnExcluir.setEnabled(true);
					dateValidade.setEnabled(false);
					panelFor.setEnabled(false);
					txtFornecedor.setEnabled(false);
				} else {
					JOptionPane.showMessageDialog(null, "Produto não Encontrado");
					produtoCadastrado = false;
					btnCarregar.setEnabled(false);
					txtNome.requestFocus();
					btnCarregar.setEnabled(true);
					btnPesquisarProduto.setEnabled(false);
					cboUN.setSelectedItem("");
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void adicionar() {

		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do produto!");
			txtNome.requestFocus();
		} else if (txtEstoque.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a quantidade de estoque do produto!");
			txtEstoque.requestFocus();
		} else if (txtEstoquemin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a quantidade de estoque mínimo do produto!");
			txtEstoquemin.requestFocus();
		} else if (cboUN.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha a unidade de medidas!");
			cboUN.requestFocus();
		} else if (txtDescricao.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a descrição do produto!");
			txtDescricao.requestFocus();
		} else if (txtLote.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o lote do produto!");
			txtLote.requestFocus();
		} else if (txtCusto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o custo do produto!");
			txtCusto.requestFocus();
		} else if (dateValidade.getDate() == null) {
			JOptionPane.showMessageDialog(null, "Preencha a data de validade do produto!");
			dateValidade.requestFocus();
		} else if (txtIdfor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha qual será o fornecedor!");
			txtIdfor.requestFocus();
		} else {
			String create = "insert into produtos(barcode, produto, lote, descricao, foto, fabricante, dataval, estoque, estoquemin, unidade, localarm, custo, lucro, idfor) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(create);
				pst.setString(1, txtBarcode.getText());
				pst.setString(2, txtNome.getText());
				pst.setString(3, txtLote.getText());
				pst.setString(4, txtDescricao.getText());
				pst.setBlob(5, fis, tamanho);
				pst.setString(6, txtFabricante.getText());
				SimpleDateFormat formatador = new SimpleDateFormat("yyyyMMdd");
				String dataFormatada = formatador.format(dateValidade.getDate());
				pst.setString(7, dataFormatada);
				pst.setString(8, txtEstoque.getText());
				pst.setString(9, txtEstoquemin.getText());
				pst.setString(10, cboUN.getSelectedItem().toString());
				pst.setString(11, txtLocal.getText());
				pst.setString(12, txtCusto.getText());
				pst.setString(13, txtLucro.getText());
				pst.setString(14, txtIdfor.getText());
				int confirma = pst.executeUpdate();
				if (confirma == 1) {
					JOptionPane.showMessageDialog(null, "Produto adicionado");
					limparCampos();
					txtBarcode.requestFocus();
				} else {
					JOptionPane.showMessageDialog(null, "Produto inexistente");
				}

				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}

		}
	}

	private void editar() {
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do produto!");
			txtNome.requestFocus();
		} else if (txtEstoque.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a quantidade de estoque do produto!");
			txtEstoque.requestFocus();
		} else if (txtEstoque.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a quantidade de estoque mínimo do produto!");
			txtEstoque.requestFocus();
		} else if (cboUN.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha a unidade de medida!");
			cboUN.requestFocus();
		} else if (txtDescricao.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a descrição do produto!");
			txtDescricao.requestFocus();
		} else if (txtLote.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o lote do produto!");
			txtLote.requestFocus();
		} else if (txtCusto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o custo do produto!");
			txtCusto.requestFocus();
		} else if (dateValidade.getDate() == null) {
			JOptionPane.showMessageDialog(null, "Preencha a data de validade do produto!");
			dateValidade.requestFocus();
		} else if (txtLucro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o lucro do produto!");
			txtLucro.requestFocus();
		} else if (txtIdfor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha qual será o fornecedor!");
			txtIdfor.requestFocus();
		} else {
			String update = "update produtos set produto=?,lote=?,descricao=?,foto=?,fabricante=?,estoque=?,estoquemin=?,unidade=?,localarm=?,custo=?,lucro=? where codigo=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(update);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtLote.getText());
				pst.setString(3, txtDescricao.getText());
				pst.setBlob(4, fis, tamanho);
				pst.setString(5, txtFabricante.getText());
				pst.setString(6, txtEstoque.getText());
				pst.setString(7, txtEstoquemin.getText());
				pst.setString(8, cboUN.getSelectedItem().toString());
				pst.setString(9, txtLocal.getText());
				pst.setString(10, txtCusto.getText());
				pst.setString(11, txtLucro.getText());
				pst.setString(12, txtCodigo.getText());
				int confirma = pst.executeUpdate();
				if (confirma == 1) {
					JOptionPane.showMessageDialog(null, "Dados do produto alterados com sucesso");
					limparCampos();
				} else {
					JOptionPane.showMessageDialog(null, "Erro! Não foi possível alterar os dados do produto");
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}

		}
	}

	private void excluir() {
		int confirmar = JOptionPane.showConfirmDialog(null, "Deseja mesmo excluir o produto?", "Atenção",
				JOptionPane.YES_NO_OPTION);
		if (confirmar == JOptionPane.YES_OPTION) {
			String delete = "delete from produtos where codigo=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(delete);
				pst.setString(1, txtCodigo.getText());
				pst.executeUpdate();
				limparCampos();
				JOptionPane.showMessageDialog(null, "Produto excluido!");
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
}
