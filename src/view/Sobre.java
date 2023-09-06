package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Sobre extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JButton btnNewButton;

	public static void main(String[] args) {
		try {
			Sobre dialog = new Sobre();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Sobre() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Sobre.class.getResource("/img/iconee.png")));
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(255, 255, 255));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Pinturas Resident");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(34, 11, 176, 25);
		contentPanel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Autor: Ryan Victor Ferreira Souto");
		lblNewLabel_1.setBounds(34, 72, 340, 14);
		contentPanel.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Sob a licença MIT");
		lblNewLabel_2.setBounds(34, 197, 176, 14);
		contentPanel.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(Sobre.class.getResource("/img/mit-icon.png")));
		lblNewLabel_3.setBounds(296, 122, 128, 128);
		contentPanel.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Pinturas Resident");
		lblNewLabel_4.setBounds(34, 97, 141, 14);
		contentPanel.add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("Versão: 1.0");
		lblNewLabel_5.setBounds(34, 122, 93, 14);
		contentPanel.add(lblNewLabel_5);

		JLabel lblNewLabel_6 = new JLabel("Software: Java - MySQL");
		lblNewLabel_6.setBounds(34, 147, 141, 14);
		contentPanel.add(lblNewLabel_6);

		JLabel lblNewLabel_7 = new JLabel("");
		lblNewLabel_7.setIcon(new ImageIcon("C:\\Users\\ryan.vfsouto\\Downloads\\icons8-rolo-de-pintura-96.png"));
		lblNewLabel_7.setBounds(296, 31, 119, 96);
		contentPanel.add(lblNewLabel_7);

		JPanel panel_1 = new JPanel();
		panel_1.setForeground(new Color(128, 128, 192));
		panel_1.setBounds(10, 31, 219, 3);
		contentPanel.add(panel_1);

		JLabel lblNewLabel_8 = new JLabel("Telefone: (11) 9999-999");
		lblNewLabel_8.setBounds(34, 172, 141, 14);
		contentPanel.add(lblNewLabel_8);

		btnNewButton = new JButton("");
		btnNewButton.setBorderPainted(false);
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setIcon(new ImageIcon(Sobre.class.getResource("/img/icons8-github-24.png")));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				link("https://github.com/RyaanVictor?tab=repositories");
			}
		});
		btnNewButton.setBounds(10, 222, 47, 23);
		contentPanel.add(btnNewButton);

		JLabel lblNewLabel_9 = new JLabel("Git Hub");
		lblNewLabel_9.setBounds(49, 227, 46, 14);
		contentPanel.add(lblNewLabel_9);
	}

	private void link(String site) {

		Desktop desktop = Desktop.getDesktop();

		try {

			URI uri = new URI(site);

			desktop.browse(uri);

		} catch (Exception e) {

			System.out.println(e);

		}

	}
}
