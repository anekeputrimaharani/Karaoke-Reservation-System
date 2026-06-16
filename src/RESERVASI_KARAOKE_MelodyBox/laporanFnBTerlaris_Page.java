package RESERVASI_KARAOKE_MelodyBox;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class laporanFnBTerlaris_Page extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txt_Tgl_Dari;
	private JTextField txt_Tgl_Sampai;
	private JTable table_Laporan_FnBTerlaris;

	/**
	 * Launch the application.
	 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					laporanFnBTerlaris_Page frame = new laporanFnBTerlaris_Page();
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
	public laporanFnBTerlaris_Page() {
		setTitle("Laporan F&B Harian MelodyBox");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Dari Tanggal:  ");
		lblNewLabel.setBounds(20, 11, 160, 14);
		contentPane.add(lblNewLabel);
		
		txt_Tgl_Dari = new JTextField();
		txt_Tgl_Dari.setText("yyyy-mm-dd");
		txt_Tgl_Dari.setForeground(Color.GRAY);

		txt_Tgl_Dari.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txt_Tgl_Dari.getText().equals("yyyy-mm-dd")) {
                	txt_Tgl_Dari.setText("");
                	txt_Tgl_Dari.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txt_Tgl_Dari.getText().isEmpty()) {
                	txt_Tgl_Dari.setForeground(Color.GRAY);
                	txt_Tgl_Dari.setText("yyyy-mm-dd");
                }
            }
        });
		txt_Tgl_Dari.setBounds(180, 8, 120, 20);
		contentPane.add(txt_Tgl_Dari);
		txt_Tgl_Dari.setColumns(10);
		
		JLabel lblSampaiTanggal = new JLabel("Sampai Tanggal : ");
		lblSampaiTanggal.setBounds(20, 40, 160, 14);
		contentPane.add(lblSampaiTanggal);
		
		txt_Tgl_Sampai = new JTextField();
		txt_Tgl_Sampai.setText("yyyy-mm-dd");
		txt_Tgl_Sampai.setForeground(Color.GRAY); 

		txt_Tgl_Sampai.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txt_Tgl_Sampai.getText().equals("yyyy-mm-dd")) {
                	txt_Tgl_Sampai.setText("");
                	txt_Tgl_Sampai.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txt_Tgl_Sampai.getText().isEmpty()) {
                	txt_Tgl_Sampai.setForeground(Color.GRAY);
                	txt_Tgl_Sampai.setText("yyyy-mm-dd");
                }
            }
        });
		txt_Tgl_Sampai.setColumns(10);
		txt_Tgl_Sampai.setBounds(180, 37, 120, 20);
		contentPane.add(txt_Tgl_Sampai);
		
		// === FITUR LIVE FILTER & AUTO UPDATE ===
		KeyAdapter liveFilterListener = new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String tglDari = txt_Tgl_Dari.getText();
				String tglSampai = txt_Tgl_Sampai.getText();

				// Filter hanya jalan jika tanggal lengkap (10 digit) atau kosong
				boolean lengkap = (tglDari.length() == 10 && tglSampai.length() == 10);
				boolean kosong = (tglDari.isEmpty() && tglSampai.isEmpty());

				if (lengkap || kosong) {
					loadData(tglDari, tglSampai);
				}
			}
		};
		
		txt_Tgl_Dari.addKeyListener(liveFilterListener);
		txt_Tgl_Sampai.addKeyListener(liveFilterListener);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 76, 640, 410);
		contentPane.add(scrollPane);
		
		table_Laporan_FnBTerlaris = new JTable();
		DefaultTableModel model = new DefaultTableModel(
	            new Object[][] {},
	            new String[] {
	            		"Tanggal", "Nama Menu", "Total Terjual (Paid)", "Total Pendapatan (Paid)"
	            		
	           }
	        ) {
	            // Method ini membuat tabel TIDAK BISA diedit secara langsung
	            @Override
	            public boolean isCellEditable(int row, int column) {
	                return false;
	            }
	        };
	        
	        table_Laporan_FnBTerlaris.setModel(model);
		
		// Atur Lebar Kolom
		table_Laporan_FnBTerlaris.getColumnModel().getColumn(0).setPreferredWidth(100); // Tanggal
		table_Laporan_FnBTerlaris.getColumnModel().getColumn(1).setPreferredWidth(200); // Nama Menu
		table_Laporan_FnBTerlaris.getColumnModel().getColumn(2).setPreferredWidth(120); // Terjual
		table_Laporan_FnBTerlaris.getColumnModel().getColumn(3).setPreferredWidth(150); // Pendapatan
		
		scrollPane.setViewportView(table_Laporan_FnBTerlaris);
		
		// Load Awal
		loadData("", "");
	}
	
	private void loadData(String tglDari, String tglSampai) {
		DefaultTableModel model = (DefaultTableModel) table_Laporan_FnBTerlaris.getModel();
		model.setRowCount(0);

		boolean pakaiFilter = (tglDari.length() == 10 && tglSampai.length() == 10);

		try {
			konfigurasi.KoneksiKeServer();
			if (konfigurasi.con == null) return;

			// QUERY FIX: Tambahkan WHERE status_pesanan = 'Done (Paid)'
			String sql = "SELECT " 
					   + "rp.tgl_reservasi, " 
					   + "pf.nama_pesanan, " 
					   + "SUM(pf.kuantitas) AS TotalTerjual, " 
					   + "SUM(pf.hrg_totMenu) AS TotalPendapatan " 
					   + "FROM pemesanan_fnb pf " 
					   + "JOIN reservasi_pemesananfnb rp ON pf.id_pesanan = rp.id_pesanan "
					   + "WHERE rp.status_pesanan = 'Done (Paid)' "; // <--- PERUBAHAN UTAMA DI SINI

			if (pakaiFilter) {
				// Pakai AND karena sudah ada WHERE sebelumnya
				sql += "AND rp.tgl_reservasi BETWEEN ? AND ? ";
			}

			// Grouping Ganda (Tanggal + Nama) agar laporan jadi per hari per menu
			sql += "GROUP BY rp.tgl_reservasi, pf.nama_pesanan ORDER BY rp.tgl_reservasi DESC, TotalTerjual DESC";

			PreparedStatement ps = konfigurasi.con.prepareStatement(sql);

			if (pakaiFilter) {
				ps.setString(1, tglDari); 
				ps.setString(2, tglSampai); 
			}

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String tanggal = rs.getString("tgl_reservasi");
				String nama = rs.getString("nama_pesanan");
				int jumlah = rs.getInt("TotalTerjual");
				double total = rs.getDouble("TotalPendapatan");
				
				String TotalPendapatan = String.format("%,.0f", total).replace(",", ".");

				model.addRow(new Object[] { 
						tanggal, 
						nama, 
						jumlah, 
						TotalPendapatan 
				});
			}

			rs.close();
			ps.close();
			
			// === OTOMATIS SIMPAN KE ARSIP ===
			// Karena tabel GUI sekarang isinya cuma data PAID, 
			// maka yang tersimpan ke database arsip juga otomatis cuma yang PAID.
			simpanKeTabelArsip();

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error Load Laporan: " + e.getMessage());
		}
	}
	
	private void simpanKeTabelArsip() {
		if (table_Laporan_FnBTerlaris.getRowCount() == 0) return;

		try {
			if (konfigurasi.con == null || konfigurasi.con.isClosed()) {
				konfigurasi.KoneksiKeServer();
			}

			// SYARAT: Tabel 'laporan_fnb_terlaris' punya PRIMARY KEY (Tanggal, Nama_Menu)
			String sql = "REPLACE INTO laporan_fnb_terlaris (Tanggal, Nama_Menu, Total_Terjual, Total_Pendapatan) VALUES (?, ?, ?, ?)";

			PreparedStatement ps = konfigurasi.con.prepareStatement(sql);
			konfigurasi.con.setAutoCommit(false);

			for (int i = 0; i < table_Laporan_FnBTerlaris.getRowCount(); i++) {

				String tanggal = table_Laporan_FnBTerlaris.getValueAt(i, 0).toString();
				String namamenu = table_Laporan_FnBTerlaris.getValueAt(i, 1).toString();
				
				int totaljual = Integer.parseInt(table_Laporan_FnBTerlaris.getValueAt(i, 2).toString());
				
				String totalpendapatan = table_Laporan_FnBTerlaris.getValueAt(i, 3).toString();
				double total = Double.parseDouble(totalpendapatan.replace(".", ""));

				ps.setString(1, tanggal);
				ps.setString(2, namamenu);
				ps.setInt(3, totaljual);
				ps.setDouble(4, total);
				ps.addBatch();
			}

			ps.executeBatch();
			konfigurasi.con.commit();
			konfigurasi.con.setAutoCommit(true);
			ps.close();
			
			System.out.println("Auto Sync F&B Harian (Paid) Berhasil.");

		} catch (Exception e) {
			try { konfigurasi.con.rollback(); } catch (Exception ex) {}
			System.out.println("Gagal Auto Save: " + e.getMessage());
		}
	}
}