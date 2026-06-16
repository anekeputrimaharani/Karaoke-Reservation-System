package RESERVASI_KARAOKE_MelodyBox;

import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JToolBar;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class mainPage_admin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	
	/**
	 * Launch the application.
	 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainPage_admin frame = new mainPage_admin();
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
	public mainPage_admin() {
		setTitle("Sistem Reservasi Karaoke MelodyBox");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 833, 469);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menuSystem = new JMenu("System");
		menuBar.add(menuSystem);
		
		JMenuItem menuItemLogout = new JMenuItem("Logout");
		menuItemLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginPage loginPage = new loginPage();
				loginPage.setVisible(true);
				dispose();
			}
		});
		menuSystem.add(menuItemLogout);
		
		JMenu menuDataMaster = new JMenu("Data Master");
		menuBar.add(menuDataMaster);
		
		JMenuItem menuItem_DataRoom = new JMenuItem("Data Room");
		menuItem_DataRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dataRoom dataRoom = new dataRoom();
				dataRoom.setVisible(true);
			}
		});
		menuDataMaster.add(menuItem_DataRoom);
		
		JMenuItem menuItem_DataPaketKaraoke = new JMenuItem("Paket Karaoke");
		menuItem_DataPaketKaraoke.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dataPaket_Karaoke dataPaket = new dataPaket_Karaoke();
				dataPaket.setVisible(true);
			}
		});
		menuDataMaster.add(menuItem_DataPaketKaraoke);
		
		JMenuItem menuItem_DataMenuFnB = new JMenuItem("Data Menu F&B");
		menuItem_DataMenuFnB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dataMenu_Food_Beverages dataMenu_Food_Beverages = new dataMenu_Food_Beverages ();
				dataMenu_Food_Beverages.setVisible(true);
			}
		});
		menuDataMaster.add(menuItem_DataMenuFnB);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

	}
}
