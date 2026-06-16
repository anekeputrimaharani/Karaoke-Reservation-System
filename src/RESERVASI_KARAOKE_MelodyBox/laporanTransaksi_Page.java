package RESERVASI_KARAOKE_MelodyBox;

import java.awt.EventQueue;
import java.awt.Color;
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

public class laporanTransaksi_Page extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txt_Tgl_Dari;
	private JTextField txt_Tgl_Sampai;
	private JTable table_LaporanTransaksi;

	/**
	 * Launch the application.
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					laporanTransaksi_Page frame = new laporanTransaksi_Page();
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
	public laporanTransaksi_Page() {
		setTitle("Laporan Transaksi MelodyBox (Done Paid Only) ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Dari Tanggal: ");
		lblNewLabel.setBounds(20, 11, 180, 14);
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
		txt_Tgl_Dari.setBounds(200, 8, 120, 20);
		contentPane.add(txt_Tgl_Dari);
		txt_Tgl_Dari.setColumns(10);
		
		JLabel lblSampaiTanggal = new JLabel("Sampai Tanggal : ");
		lblSampaiTanggal.setBounds(20, 39, 180, 14);
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
		txt_Tgl_Sampai.setBounds(200, 36, 120, 20);
		contentPane.add(txt_Tgl_Sampai);
		
		// === FITUR LIVE FILTER & AUTO SAVE ===
		KeyAdapter liveFilterListener = new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String tglDari = txt_Tgl_Dari.getText();
				String tglSampai = txt_Tgl_Sampai.getText();

				// Cek kelengkapan (10 digit) atau jika kosong dua-duanya
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
		scrollPane.setBounds(20, 74, 540, 370);
		contentPane.add(scrollPane);
		
		table_LaporanTransaksi = new JTable();
		DefaultTableModel model = new DefaultTableModel(
	            new Object[][] {},
	            new String[] {
	            		"Tanggal Reservasi", "Jumlah Reservasi (Paid)", "Total Omzet (Paid)"
	           }
	        ) {
	            // Method ini membuat tabel TIDAK BISA diedit secara langsung
	            @Override
	            public boolean isCellEditable(int row, int column) {
	                return false;
	            }
	        };
	        
	        table_LaporanTransaksi.setModel(model);
		
		table_LaporanTransaksi.getColumnModel().getColumn(0).setPreferredWidth(100);
		table_LaporanTransaksi.getColumnModel().getColumn(1).setPreferredWidth(150);
		table_LaporanTransaksi.getColumnModel().getColumn(2).setPreferredWidth(120);
		scrollPane.setViewportView(table_LaporanTransaksi);
		
		// Load data awal (Otomatis load & Otomatis Simpan)
		loadData("", "");
	}
	
	private void loadData(String tglDari, String tglSampai) {
		DefaultTableModel model = (DefaultTableModel) table_LaporanTransaksi.getModel();
		model.setRowCount(0);

		boolean pakaiFilter = (tglDari.length() == 10 && tglSampai.length() == 10);

		try {
			konfigurasi.KoneksiKeServer();
			if (konfigurasi.con == null) return;

			// QUERY PERBAIKAN: Hanya hitung yang statusnya 'Done (Paid)'
			String sql = "SELECT " 
					   + "tgl_reservasi, " 
					   + "COUNT(id_pesanan) AS JumlahReservasi, " 
					   + "SUM(hrg_totPembayaran) AS tot_omzet "   
					   + "FROM reservasi_pemesananfnb "
					   + "WHERE status_pesanan = 'Done (Paid)' "; // <--- PERUBAHAN PENTING DISINI

			if (pakaiFilter) {
				sql += "AND tgl_reservasi BETWEEN ? AND ? ";
			}

			sql += "GROUP BY tgl_reservasi ORDER BY tgl_reservasi DESC";

			PreparedStatement ps = konfigurasi.con.prepareStatement(sql);

			if (pakaiFilter) {
				ps.setString(1, tglDari);
				ps.setString(2, tglSampai);
			}
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				String tgl = rs.getString("tgl_reservasi");
				int jumlah = rs.getInt("JumlahReservasi"); 
				double totalRaw = rs.getDouble("tot_omzet");
				String omzetCustom = String.format("%,.0f", totalRaw).replace(",", "."); 

				model.addRow(new Object[] { 
						tgl, 
						jumlah, 
						omzetCustom 
				});
			}

			rs.close();
			ps.close();
			
			// === OTOMATIS SIMPAN SETELAH LOAD SELESAI ===
			simpanKeTabelArsip(); 

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error Load Laporan: " + e.getMessage());
		}
	}
	
	private void simpanKeTabelArsip() {
		// Cek tabel kosong atau tidak
		if (table_LaporanTransaksi.getRowCount() == 0) {
			return; 
		}
		
		try {
			if (konfigurasi.con == null || konfigurasi.con.isClosed()) {
				konfigurasi.KoneksiKeServer();
			}

			// REPLACE INTO: Update data arsip agar sinkron dengan yang sudah Paid
			String sql = "REPLACE INTO lap_transaksi (tgl_Reservasi, jmlh_reservasi, tot_Omzet) VALUES (?, ?, ?)";

			PreparedStatement ps = konfigurasi.con.prepareStatement(sql);
			konfigurasi.con.setAutoCommit(false); 

			for (int i = 0; i < table_LaporanTransaksi.getRowCount(); i++) {
				
				String tgl = table_LaporanTransaksi.getValueAt(i, 0).toString();
				String jml = table_LaporanTransaksi.getValueAt(i, 1).toString();
				String omzetStr = table_LaporanTransaksi.getValueAt(i, 2).toString(); 
				double omzet = Double.parseDouble(omzetStr.replace(".", ""));

				ps.setString(1, tgl);
				ps.setString(2, jml);
				ps.setDouble(3, omzet);
				ps.addBatch();
			}

			ps.executeBatch();
			konfigurasi.con.commit();
			konfigurasi.con.setAutoCommit(true);
			ps.close();
	
			System.out.println("Auto Save: Data Paid berhasil diarsipkan.");

		} catch (Exception e) {
			try { konfigurasi.con.rollback(); } catch (Exception ex) {}
			System.out.println("Gagal Auto Save: " + e.getMessage());
		}
	}
}