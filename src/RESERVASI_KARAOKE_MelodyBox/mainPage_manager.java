package RESERVASI_KARAOKE_MelodyBox;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class mainPage_manager extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainPage_manager frame = new mainPage_manager();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	 */

	/**
	 * Create the frame.
	 */
	public mainPage_manager() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 833, 469);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menu_System = new JMenu("System");
		menuBar.add(menu_System);
		
		JMenuItem menuItem_Logout = new JMenuItem("Logout");
		menuItem_Logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginPage loginPage = new loginPage();
				loginPage.setVisible(true);
				dispose();
			}
		});
		menu_System.add(menuItem_Logout);
		
		JMenu menu_DataMaster = new JMenu("Data Master");
		menuBar.add(menu_DataMaster);
		
		JMenuItem menuItem_DataUser = new JMenuItem("Data User");
		menuItem_DataUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dataUser dataUser= new dataUser();
				dataUser.setVisible(true);
			}
		});
		menu_DataMaster.add(menuItem_DataUser);
		
		JMenu menu_Laporan = new JMenu("Laporan");
		menuBar.add(menu_Laporan);
		
		JMenuItem menuItem_LaporanTransaksi = new JMenuItem("Laporan Transaksi");
		menuItem_LaporanTransaksi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				laporanTransaksi_Page laporanTransaksi = new laporanTransaksi_Page();
				laporanTransaksi.setVisible(true);
			}
		});
		menu_Laporan.add(menuItem_LaporanTransaksi);
		
		JMenuItem menuItem_RiwayatSemuaReservasi = new JMenuItem("Riwayat Semua Reservasi");
		menuItem_RiwayatSemuaReservasi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				riwayatSeluruhReservasi riwayatSlh_Reservasi = new riwayatSeluruhReservasi();
				riwayatSlh_Reservasi.setVisible(true);
			}
		});
		menu_Laporan.add(menuItem_RiwayatSemuaReservasi);
		
		JMenuItem menuItem_Laporan_PaketKaraokeTerlaris = new JMenuItem("Laporan Paket Karaoke");
		menuItem_Laporan_PaketKaraokeTerlaris.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				laporanPaketKaraokeTerlaris_Page lap_PaketTerlaris = new laporanPaketKaraokeTerlaris_Page();
				lap_PaketTerlaris.setVisible(true);
			}
		});
		menu_Laporan.add(menuItem_Laporan_PaketKaraokeTerlaris);
		
		JMenuItem menuItem_Laporan_FnBTerlaris = new JMenuItem("Laporan F&B");
		menuItem_Laporan_FnBTerlaris.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				laporanFnBTerlaris_Page lap_FnB_Terlaris = new laporanFnBTerlaris_Page();
				lap_FnB_Terlaris.setVisible(true);
			}
		});
		menu_Laporan.add(menuItem_Laporan_FnBTerlaris);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

	}
}
