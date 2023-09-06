package view;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;

@SuppressWarnings("serial")
public class Relatorioss extends JDialog {

	// JDBC
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	private JButton btnReposicao;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Relatorioss dialog = new Relatorioss();
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
	public Relatorioss() {
		setTitle("Relatórios");
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(null);

		JButton btnClientes = new JButton("Clientes");
		btnClientes.setIcon(new ImageIcon(Relatorioss.class.getResource("/img/icons8-clientes-96.png")));
		btnClientes.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioClientes();
			}
		});
		btnClientes.setBounds(30, 90, 269, 79);
		getContentPane().add(btnClientes);

		JButton btnServicos = new JButton("Serviços");
		btnServicos.setIcon(new ImageIcon(Relatorioss.class.getResource("/img/icons8-spray-de-tinta-96.png")));
		btnServicos.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnServicos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				servicoCliente();
			}
		});
		btnServicos.setBounds(30, 197, 269, 79);
		getContentPane().add(btnServicos);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(128, 128, 192));
		panel.setBounds(0, 482, 784, 79);
		getContentPane().add(panel);

		JLabel lblNewLabel_1_1 = new JLabel("Pinturas Resident");
		lblNewLabel_1_1.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD | Font.ITALIC, 24));
		lblNewLabel_1_1.setBounds(253, 11, 352, 33);
		getContentPane().add(lblNewLabel_1_1);

		JLabel lblNewLabel_7 = new JLabel("");
		lblNewLabel_7.setIcon(new ImageIcon("C:\\Users\\ryan.vfsouto\\Downloads\\icons8-rolo-de-pintura-96.png"));
		lblNewLabel_7.setBounds(526, 155, 119, 96);
		getContentPane().add(lblNewLabel_7);

		JPanel panel_1 = new JPanel();
		panel_1.setForeground(new Color(128, 128, 192));
		panel_1.setBounds(0, 37, 446, 1);
		getContentPane().add(panel_1);

		btnReposicao = new JButton("Reposicao Geral");
		btnReposicao.setIcon(new ImageIcon(Relatorioss.class.getResource("/img/icons8-papel-80.png")));
		btnReposicao.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnReposicao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioPatrimonio();
			}
		});
		btnReposicao.setBounds(29, 312, 270, 79);
		getContentPane().add(btnReposicao);

	}

	private void relatorioClientes() {
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream("clientes.pdf"));
			document.open();
			Date dataRelatorio = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));
			document.add(new Paragraph("Clientes:"));
			document.add(new Paragraph(" "));
			String readClientes = "select nome,fone from clientes order by nome";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readClientes);
				rs = pst.executeQuery();
				PdfPTable tabela = new PdfPTable(2);
				PdfPCell col1 = new PdfPCell(new Paragraph("Cliente"));
				PdfPCell col2 = new PdfPCell(new Paragraph("Fone"));
				tabela.addCell(col1);
				tabela.addCell(col2);
				while (rs.next()) {
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
				}
				document.add(tabela);
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		document.close();
		try {
			Desktop.getDesktop().open(new File("clientes.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void servicoCliente() {
		Document document = new Document();
		document.setPageSize(PageSize.A4.rotate());
		try {
			PdfWriter.getInstance(document, new FileOutputStream("servicos.pdf"));
			document.open();
			Date dataRelatorio = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));
			document.add(new Paragraph("Servicos:"));
			document.add(new Paragraph(" "));
			String readServicos = "select * from servicos inner join clientes on servicos.idcli = clientes.idcli;";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readServicos);
				rs = pst.executeQuery();
				PdfPTable tabela = new PdfPTable(4);
				PdfPCell col1 = new PdfPCell(new Paragraph("dataOS"));
				PdfPCell col2 = new PdfPCell(new Paragraph("Serviço"));
				PdfPCell col3 = new PdfPCell(new Paragraph("Valor"));
				PdfPCell col4 = new PdfPCell(new Paragraph("Nome"));

				tabela.addCell(col1);
				tabela.addCell(col2);
				tabela.addCell(col3);
				tabela.addCell(col4);
				while (rs.next()) {
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
					tabela.addCell(rs.getString(4));
					tabela.addCell(rs.getString(5));

				}
				document.add(tabela);
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		document.close();
		try {
			Desktop.getDesktop().open(new File("servicos.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	private void relatorioPatrimonio() {

		Document document = new Document();

		try {
			PdfWriter.getInstance(document, new FileOutputStream("estoque.pdf"));
			document.open();
			Date dataRelatorio = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));
			document.add(new Paragraph("Estoque:"));
			document.add(new Paragraph(" "));
			String readClientes = "select codigo as código, produto, date_format(dataval, '%d/%m/%Y') as validade, estoque, estoquemin as estóque_mínimo from produtos where estoque < estoquemin";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readClientes);
				rs = pst.executeQuery();
				PdfPTable tabela = new PdfPTable(5);
				PdfPCell col1 = new PdfPCell(new Paragraph("código: "));
				PdfPCell col2 = new PdfPCell(new Paragraph("produto: "));
				PdfPCell col3 = new PdfPCell(new Paragraph("validade: "));
				PdfPCell col4 = new PdfPCell(new Paragraph("estoque: "));
				PdfPCell col5 = new PdfPCell(new Paragraph("estoque mínimo: "));
				tabela.addCell(col1);
				tabela.addCell(col2);
				tabela.addCell(col3);
				tabela.addCell(col4);
				tabela.addCell(col5);
				while (rs.next()) {
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
					tabela.addCell(rs.getString(4));
					tabela.addCell(rs.getString(5));
				}
				document.add(tabela);
				document.add(new Paragraph("Validade:"));
				document.add(new Paragraph(" "));
				String read = "select codigo as código, produto, date_format(dataval, '%d/%m/%Y') as validade from produtos where dataval < dataent";
				pst = con.prepareStatement(read);
				rs = pst.executeQuery();
				PdfPTable tabela2 = new PdfPTable(3);
				PdfPCell col6 = new PdfPCell(new Paragraph("código: "));
				PdfPCell col7 = new PdfPCell(new Paragraph("produto: "));
				PdfPCell col8 = new PdfPCell(new Paragraph("validade: "));
				tabela2.addCell(col6);
				tabela2.addCell(col7);
				tabela2.addCell(col8);
				while (rs.next()) {
					tabela2.addCell(rs.getString(1));
					tabela2.addCell(rs.getString(2));
					tabela2.addCell(rs.getString(3));
				}
				document.add(tabela2);
				document.add(new Paragraph(" "));
				document.add(new Paragraph("Patrimônio (Custo):"));
				document.add(new Paragraph(" "));
				String read2 = "select sum(custo * estoque) as Total from produtos";
				pst = con.prepareStatement(read2);
				rs = pst.executeQuery();
				PdfPTable tabela3 = new PdfPTable(1);
				PdfPCell col12 = new PdfPCell(new Paragraph("Patrimônio custo: "));
				tabela3.addCell(col12);
				while (rs.next()) {
					tabela3.addCell(rs.getString(1));
				}
				document.add(tabela3);
				document.add(new Paragraph(" "));
				document.add(new Paragraph("Patrimônio (venda):"));
				document.add(new Paragraph(" "));
				String readVenda = "select sum((custo + (custo * lucro)/100) * estoque) as total from produtos";
				pst = con.prepareStatement(readVenda);
				rs = pst.executeQuery();
				PdfPTable tabela4 = new PdfPTable(1);
				PdfPCell col43 = new PdfPCell(new Paragraph("Patrimônio venda: "));
				tabela4.addCell(col43);
				while (rs.next()) {
					tabela4.addCell(rs.getString(1));
				}
				document.add(tabela4);
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		document.close();
		try {
			Desktop.getDesktop().open(new File("estoque.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}