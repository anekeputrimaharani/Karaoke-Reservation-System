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

public class laporanPaketKaraokeTerlaris_Page extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txt_Tgl_Dari;
	private JTextField txt_Tgl_Sampai;
	private JTable table_Laporan_PaketKaraokeTerlaris;

	/**
	 * Launch the application.
	 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					laporanPaketKaraokeTerlaris_Page frame = new laporanPaketKaraokeTerlaris_Page();
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
	public laporanPaketKaraokeTerlaris_Page() {
		setTitle("Laporan Paket Karaoke MelodyBox");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Dari Tanggal: ");
		lblNewLabel.setBounds(22, 11, 160, 14);
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
		lblSampaiTanggal.setBounds(22, 38, 160, 14);
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
		txt_Tgl_Sampai.setBounds(180, 38, 120, 20);
		contentPane.add(txt_Tgl_Sampai);
		
		// === FITUR LIVE FILTER & AUTO UPDATE ===
		KeyAdapter liveFilterListener = new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String tglDari = txt_Tgl_Dari.getText();
				String tglSampai = txt_Tgl_Sampai.getText();

				// Filter jalan jika tanggal lengkap (10 digit) atau dikosongkan
				boolean lengkap = (tglDari.length() == 10 && tglSampai.length() == 10);
				boolean kosong = (tglDari.isEmpty() && tglSampai.isEmpty());

				if (lengkap || kosong) {
					loadData(tglDari, tglSampai);
				}
			}
		};
		
		// Pasang listener ke textfield
		txt_Tgl_Dari.addKeyListener(liveFilterListener);
		txt_Tgl_Sampai.addKeyListener(liveFilterListener);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 80, 560, 370);
		contentPane.add(scrollPane);
		
		table_Laporan_PaketKaraokeTerlaris = new JTable();
		DefaultTableModel model = new DefaultTableModel(
	            new Object[][] {},
	            new String[] {
	            		"Tanggal", "Nama Paket", "Jumlah Pesanan (Paid)", "Jumlah Pendapatan (Paid)"
	           }
	        ) {
	            // Method ini membuat tabel TIDAK BISA diedit secara langsung
	            @Override
	            public boolean isCellEditable(int row, int column) {
	                return false;
	            }
	        };
	        
	        table_Laporan_PaketKaraokeTerlaris.setModel(model);
		
		
		// Atur lebar kolom
		table_Laporan_PaketKaraokeTerlaris.getColumnModel().getColumn(0).setPreferredWidth(90);  // Tanggal
		table_Laporan_PaketKaraokeTerlaris.getColumnModel().getColumn(1).setPreferredWidth(150); // Nama Paket
		table_Laporan_PaketKaraokeTerlaris.getColumnModel().getColumn(2).setPreferredWidth(130); // Jumlah
		table_Laporan_PaketKaraokeTerlaris.getColumnModel().getColumn(3).setPreferredWidth(150); // Pendapatan
		
		scrollPane.setViewportView(table_Laporan_PaketKaraokeTerlaris);

		// Load data awal
		loadData("", "");
	}
	
	private void loadData(String tglDari, String tglSampai) {
		DefaultTableModel model = (DefaultTableModel) table_Laporan_PaketKaraokeTerlaris.getModel();
		model.setRowCount(0);

		boolean pakaiFilter = (tglDari.length() == 10 && tglSampai.length() == 10);

		try {
			konfigurasi.KoneksiKeServer();
			if (konfigurasi.con == null) return;

			// QUERY: Grouping berdasarkan Tanggal DAN Nama Paket
			// Filter HANYA status 'Done (Paid)'
			String sql = "SELECT " 
					   + "tgl_reservasi, " 
					   + "nama_paket, " 
					   + "COUNT(id_pesanan) AS TotalPesanan, " 
					   + "SUM(hrg_tot_karaoke) AS TotalPendapatan " 
					   + "FROM reservasi_pemesananfnb "
					   + "WHERE status_pesanan = 'Done (Paid)' "; // <--- PERUBAHAN UTAMA DI SINI

			if (pakaiFilter) {
				// Gunakan AND karena sudah ada WHERE di atas
				sql += "AND tgl_reservasi BETWEEN ? AND ? ";
			}

			// Grouping Ganda (Tanggal + Nama Paket)
			sql += "GROUP BY tgl_reservasi, nama_paket ORDER BY tgl_reservasi DESC, TotalPesanan DESC";

			PreparedStatement ps = konfigurasi.con.prepareStatement(sql);

			if (pakaiFilter) {
				ps.setString(1, tglDari); 
				ps.setString(2, tglSampai); 
			}

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String tanggal = rs.getString("tgl_reservasi");
				String namaPaket = rs.getString("nama_paket");
				int jumlah = rs.getInt("TotalPesanan");
				double total = rs.getDouble("TotalPendapatan");
				
				// Format Rupiah (tanpa desimal, pakai titik)
				String pendapatanFormatted = String.format("%,.0f", total).replace(",", ".");

				model.addRow(new Object[] { 
						tanggal, 
						namaPaket, 
						jumlah, 
						pendapatanFormatted 
				});
			}

			rs.close();
			ps.close();
			
			// === OTOMATIS SIMPAN KE ARSIP ===
			// Karena data yang diload di tabel sudah difilter 'Paid', 
			// otomatis yang tersimpan ke arsip juga data yang valid (Paid).
			simpanKeTabelArsip();

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error Load Laporan: " + e.getMessage());
		}
	}
	
	private void simpanKeTabelArsip() {
		// Jika tabel kosong, tidak usah simpan
		if (table_Laporan_PaketKaraokeTerlaris.getRowCount() == 0) return;

		try {
			if (konfigurasi.con == null || konfigurasi.con.isClosed()) {
				konfigurasi.KoneksiKeServer();
			}

			// SYARAT: Tabel 'lap_paket_terlaris' harus punya Primary Key (tgl_reservasi, nama_paket)
			String sql = "REPLACE INTO lap_paket_terlaris "
					   + "(tgl_reservasi, nama_paket, jmlh_pesanan_paket, jmlh_pendapatan_paket) "
					   + "VALUES (?, ?, ?, ?)";

			PreparedStatement ps = konfigurasi.con.prepareStatement(sql);
			konfigurasi.con.setAutoCommit(false);

			for (int i = 0; i < table_Laporan_PaketKaraokeTerlaris.getRowCount(); i++) {

				String tanggal = table_Laporan_PaketKaraokeTerlaris.getValueAt(i, 0).toString();
				String namaPaket = table_Laporan_PaketKaraokeTerlaris.getValueAt(i, 1).toString();
				
				int jumlah = Integer.parseInt(table_Laporan_PaketKaraokeTerlaris.getValueAt(i, 2).toString());
				
				// Bersihkan format rupiah sebelum convert ke double
				String pendapatanStr = table_Laporan_PaketKaraokeTerlaris.getValueAt(i, 3).toString();
				double pendapatan = Double.parseDouble(pendapatanStr.replace(".", ""));

				ps.setString(1, tanggal);
				ps.setString(2, namaPaket);
				ps.setInt(3, jumlah);
				ps.setDouble(4, pendapatan);
				
				ps.addBatch();
			}

			ps.executeBatch();
			konfigurasi.con.commit();
			konfigurasi.con.setAutoCommit(true);
			ps.close();
			
			System.out.println("Auto Sync Paket Terlaris (Paid) Berhasil.");

		} catch (Exception e) {
			try { konfigurasi.con.rollback(); } catch (Exception ex) {}
			System.out.println("Gagal Auto Save: " + e.getMessage());
		}
	}
}