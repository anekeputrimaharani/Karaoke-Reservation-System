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

public class mainPage_kasir extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainPage_kasir frame = new mainPage_kasir();
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
	public mainPage_kasir() {
		setTitle("Sistem Reservasi Karaoke MelodyBox");
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
		
		JMenuItem menuItem_DataMember = new JMenuItem("Data Member");
		menuItem_DataMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dataMember datamember = new dataMember();
				datamember.setVisible(true);
			}
		});
		menu_DataMaster.add(menuItem_DataMember);
		
		JMenu menu_Operasional = new JMenu("Operasional");
		menuBar.add(menu_Operasional);
		
		JMenuItem menuItem_Res_PemesananFnB = new JMenuItem("Reservasi dan Pemesanan F&B");
		menuItem_Res_PemesananFnB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reservasiKaraoke_PemesananFnB_Page reservasiPemesanan_Page = new reservasiKaraoke_PemesananFnB_Page();
				reservasiPemesanan_Page.setVisible(true);
			}
		});
		menu_Operasional.add(menuItem_Res_PemesananFnB);
		
		JMenuItem menuItem_ManajStatusRoom = new JMenuItem("Manajemen Status Room");
		menuItem_ManajStatusRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manajemenStatusRoom_Page manajemenStatusRoom_Page = new manajemenStatusRoom_Page();
				manajemenStatusRoom_Page.setVisible(true);
			}
		});
		menu_Operasional.add(menuItem_ManajStatusRoom);
		
		JMenuItem mntmRiwayatReservasi = new JMenuItem("Riwayat Reservasi");
		mntmRiwayatReservasi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				String idKasir = konfigurasi.idUserLogin; 

				riwayatReservasi_Page page = new riwayatReservasi_Page(idKasir); 
				page.setVisible(true);
			}
		});
		menu_Operasional.add(mntmRiwayatReservasi);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

	}
}
