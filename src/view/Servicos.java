package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;
import utils.Validador;

@SuppressWarnings("serial")
public class Servicos extends JDialog {
	private JTextField txtOS;
	private JTextField txtData;
	private JTextField txtServicos;
	private JTextField txtValor;
	private JTextField txtID;
	private JTextField txtCliente;

	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	@SuppressWarnings("rawtypes")
	private JList listaCliente;
	private JScrollPane scrollPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Servicos dialog = new Servicos();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@SuppressWarnings("rawtypes")
	public Servicos() {
		setTitle("Serviços");
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("OS");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(21, 80, 24, 14);
		getContentPane().add(lblNewLabel);

		txtOS = new JTextField();
		txtOS.setEditable(false);
		txtOS.setBounds(55, 73, 106, 33);
		getContentPane().add(txtOS);
		txtOS.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Data");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(21, 183, 57, 14);
		getContentPane().add(lblNewLabel_1);

		txtData = new JTextField();
		txtData.setEditable(false);
		txtData.setBounds(73, 176, 162, 33);
		getContentPane().add(txtData);
		txtData.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Serviços");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_2.setBounds(21, 227, 86, 14);
		getContentPane().add(lblNewLabel_2);

		txtServicos = new JTextField();
		txtServicos.setBounds(88, 220, 192, 33);
		getContentPane().add(txtServicos);
		txtServicos.setColumns(10);
		txtServicos.setDocument(new Validador(25));

		JLabel lblNewLabel_4 = new JLabel("Valor");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_4.setBounds(21, 272, 86, 14);
		getContentPane().add(lblNewLabel_4);

		txtValor = new JTextField();
		txtValor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtValor.setBounds(68, 265, 106, 33);
		getContentPane().add(txtValor);
		txtValor.setColumns(10);
		txtValor.setDocument(new Validador(10));

		JButton btnAdicionar = new JButton("");
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setIcon(new ImageIcon(Servicos.class.getResource("/img/adicionar.png")));
		btnAdicionar.setBorder(null);
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Add();
			}
		});
		btnAdicionar.setBounds(30, 399, 89, 66);
		getContentPane().add(btnAdicionar);

		JButton btnEditar = new JButton("");
		btnEditar.setContentAreaFilled(false);
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setIcon(new ImageIcon(Servicos.class.getResource("/img/editar.png")));
		btnEditar.setBorder(null);
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Editar();
			}
		});
		btnEditar.setBounds(146, 399, 89, 66);
		getContentPane().add(btnEditar);

		JButton btnExcluir = new JButton("");
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setIcon(new ImageIcon(Servicos.class.getResource("/img/excluir.png")));
		btnExcluir.setBorder(null);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Excluir();
			}
		});
		btnExcluir.setBounds(267, 399, 89, 66);
		getContentPane().add(btnExcluir);

		JButton btnBuscar = new JButton("");
		btnBuscar.setContentAreaFilled(false);
		btnBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBuscar.setBorder(null);
		btnBuscar.setIcon(new ImageIcon(Servicos.class.getResource("/img/pesquisar.png")));
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscar();
			}
		});
		btnBuscar.setBounds(171, 65, 65, 41);
		getContentPane().add(btnBuscar);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Cliente",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(396, 14, 301, 166);
		getContentPane().add(panel);
		panel.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBorder(null);
		scrollPane.setBounds(10, 41, 227, 45);
		panel.add(scrollPane);

		listaCliente = new JList();
		listaCliente.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				BuscarNomeCliente();
			}
		});
		scrollPane.setViewportView(listaCliente);

		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setBounds(38, 85, 86, 20);
		panel.add(txtID);
		txtID.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel("ID");
		lblNewLabel_5.setBounds(10, 87, 18, 17);
		panel.add(lblNewLabel_5);

		txtCliente = new JTextField();
		txtCliente.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				ListarCliente();

			}
		});
		txtCliente.setBounds(10, 21, 227, 20);
		panel.add(txtCliente);
		txtCliente.setColumns(10);

		JButton btnLimpar = new JButton("");
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.setIcon(new ImageIcon(Servicos.class.getResource("/img/eraser.png")));
		btnLimpar.setBorder(null);
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Limpar();

			}
		});
		btnLimpar.setBounds(389, 399, 89, 66);
		getContentPane().add(btnLimpar);

		JButton btnOS = new JButton("");
		btnOS.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOS.setToolTipText("OS");
		btnOS.setIcon(new ImageIcon("C:\\Users\\ryan.vfsouto\\Downloads\\icons8-papel-50.png"));
		btnOS.setContentAreaFilled(false);
		btnOS.setBorder(null);
		btnOS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imprimirOS();
			}
		});
		btnOS.setBounds(458, 191, 89, 66);
		getContentPane().add(btnOS);

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBackground(new Color(128, 128, 192));
		panel_1.setBounds(-11, 495, 795, 66);
		getContentPane().add(panel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Pinturas Resident");
		lblNewLabel_1_1.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD | Font.ITALIC, 24));
		lblNewLabel_1_1.setBounds(10, 11, 352, 33);
		getContentPane().add(lblNewLabel_1_1);

		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(Servicos.class.getResource("/img/icons8-rolo-de-pintura-96.png")));
		lblNewLabel_3.setBounds(581, 241, 128, 128);
		getContentPane().add(lblNewLabel_3);

	}

	@SuppressWarnings("unchecked")
	private void ListarCliente() {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listaCliente.setModel(modelo);

		String readlistaCliente = "select * from clientes where nome like '" + txtCliente.getText() + "%'"
				+ "order by nome";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readlistaCliente);
			rs = pst.executeQuery();
			while (rs.next()) {
				scrollPane.setVisible(true);
				modelo.addElement(rs.getString(2));
				if (txtCliente.getText().isEmpty()) {
					scrollPane.setVisible(false);
				}
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	private void BuscarNomeCliente() {
		int linha = listaCliente.getSelectedIndex();
		if (linha >= 0) {
			String readlistaServico = "select * from clientes where nome like '" + txtCliente.getText() + "%'"
					+ "order by nome limit " + (linha) + " , 1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readlistaServico);
				rs = pst.executeQuery();
				if (rs.next()) {
					scrollPane.setVisible(false);
					txtID.setText(rs.getString(1));

				} else {
					JOptionPane.showMessageDialog(null, "Cliente Inexistente");
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}

		} else {
			scrollPane.setVisible(false);
		}
	}

	private void buscar() {
		// captura do número da OS sem usar a Caixa de Texto
		String numOS = JOptionPane.showInputDialog("Número da OS");

		String read = "select * from servicos where os=?";
		try {
			con = dao.conectar();

			pst = con.prepareStatement(read);

			pst.setString(1, numOS);
			rs = pst.executeQuery();
			if (rs.next()) {
				txtOS.setText(rs.getString(1));
				txtData.setText(rs.getString(2));
				txtServicos.setText(rs.getString(3));
				txtValor.setText(rs.getString(4));
				txtID.setText(rs.getString(5));
			} else {
				JOptionPane.showMessageDialog(null, "Serviço Inexistente");
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void Add() {

		if (txtServicos.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Insira o Serviço do Cliente");
			txtServicos.requestFocus();
		} else if (txtValor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Insira o Valor do Serviço");
			txtValor.requestFocus();
		} else {
			String create = "insert into servicos (equipamento,valor,idcli) values (?,?,?)";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(create);
				pst.setString(1, txtServicos.getText());
				pst.setString(2, txtValor.getText());
				pst.setString(3, txtID.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Serviço Registrado");
				Limpar();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}

	private void Limpar() {
		txtID.setText(null);
		txtOS.setText(null);
		txtData.setText(null);
		txtServicos.setText(null);
		txtValor.setText(null);
		txtCliente.setText(null);

	}

	private void Excluir() {
		int confirmar = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste Serviço?", "Atenção!",
				JOptionPane.YES_NO_OPTION);
		if (confirmar == JOptionPane.YES_OPTION) {

			String delete = "delete from servicos where os=?";

			try {
				con = dao.conectar();
				pst = con.prepareStatement(delete);
				pst.setString(1, txtOS.getText());
				pst.executeUpdate();
				Limpar();
				JOptionPane.showMessageDialog(null, "Serviço Excluído");
				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "Não foi Possível Excluir o Cliente!\nHá um Serviço Pendente.");
			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}

	private void Editar() {
		if (txtServicos.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Insira o Serviço do Cliente");
			txtServicos.requestFocus();
		} else if (txtValor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Insira o Valor do Serviço");
			txtValor.requestFocus();
		} else {
			String update = "update servicos set equipamento=?,valor=? where os=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(update);
				pst.setString(1, txtServicos.getText());
				pst.setString(2, txtValor.getText());
				pst.setString(3, txtOS.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Dados do Serviço editados com sucesso!");
				Limpar();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}

		}

	}

	/**
	 * Impressão da OS
	 */
	private void imprimirOS() {
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream("os.pdf"));
			document.open();
			String readOS = "select * from servicos where os = ?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readOS);
				pst.setString(1, txtOS.getText());
				rs = pst.executeQuery();
				if (rs.next()) {
					Paragraph os = new Paragraph("OS: " + rs.getString(1));
					os.setAlignment(Element.ALIGN_RIGHT);
					document.add(os);

					Paragraph dataos = new Paragraph("Data: " + rs.getString(2));
					dataos.setAlignment(Element.ALIGN_LEFT);
					document.add(dataos);

					Paragraph servico = new Paragraph("Servico: " + rs.getString(3));
					servico.setAlignment(Element.ALIGN_LEFT);
					document.add(servico);

					Paragraph valor = new Paragraph("Valor: " + rs.getString(4));
					valor.setAlignment(Element.ALIGN_LEFT);
					document.add(valor);

					Image imagem = Image.getInstance(Servicos.class.getResource("/img/urubu do pix.jpg"));
					imagem.scaleToFit(450, 450);
					imagem.setAbsolutePosition(20, 30);
					document.add(imagem);
				}

				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		document.close();
		try {
			Desktop.getDesktop().open(new File("os.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
