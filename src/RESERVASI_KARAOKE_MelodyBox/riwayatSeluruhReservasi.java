package RESERVASI_KARAOKE_MelodyBox;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class riwayatSeluruhReservasi extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txt_Tgl_Dari;
	private JTextField txt_Tgl_Sampai;
	private JTable table_Seluruh_RiwayatReservasi;
	private JComboBox<String> comboBox_Filter;
	private JTextField textField_Cari;

	/**
	 * Launch the application.
	 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					riwayatSeluruhReservasi frame = new riwayatSeluruhReservasi();
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
	public riwayatSeluruhReservasi() {
		setTitle("Riwayat Seluruh Reservasi MelodyBox (Done & Canceled Only)");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 982, 519);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Dari Tanggal:");
		lblNewLabel.setBounds(10, 11, 160, 14);
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
		txt_Tgl_Dari.setBounds(180, 8, 110, 20);
		contentPane.add(txt_Tgl_Dari);
		txt_Tgl_Dari.setColumns(10);
		
		JLabel lblSampaiTanggal = new JLabel("Sampai Tanggal :");
		lblSampaiTanggal.setBounds(10, 39, 160, 14);
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
		txt_Tgl_Sampai.setBounds(180, 36, 110, 20);
		contentPane.add(txt_Tgl_Sampai);
		
		// Listener Tanggal
		KeyAdapter filterListener = new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				refreshData();
			}
		};
		
		txt_Tgl_Dari.addKeyListener(filterListener);
		txt_Tgl_Sampai.addKeyListener(filterListener);
		
		JLabel lblNewLabel_1 = new JLabel("Filter : ");
		lblNewLabel_1.setBounds(310, 21, 70, 14);
		contentPane.add(lblNewLabel_1);
		
		comboBox_Filter = new JComboBox<>();
		// Hanya tampilkan pilihan yang relevan
		comboBox_Filter.setModel(new DefaultComboBoxModel<>(new String[] {"Semua (Done & Canceled)", "Done (Paid)", "Canceled"}));
		
		comboBox_Filter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshData();
			}
		});
		
		comboBox_Filter.setBounds(360, 17, 180, 22);
		contentPane.add(comboBox_Filter);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 68, 948, 403);
		contentPane.add(scrollPane);
		
		table_Seluruh_RiwayatReservasi = new JTable();
		table_Seluruh_RiwayatReservasi.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] {
				"Id Kasir", "Nama Kasir", "Id Pesanan ", "Tanggal", "Nama Pelanggan", "Room", "Paket", "Total Pembayaran ", "Metode", "Status "
			}
		) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // membuat tabel "Read-Only"
			}
		});
		
		table_Seluruh_RiwayatReservasi.getColumnModel().getColumn(0).setPreferredWidth(60);
		table_Seluruh_RiwayatReservasi.getColumnModel().getColumn(1).setPreferredWidth(100);
		table_Seluruh_RiwayatReservasi.getColumnModel().getColumn(2).setPreferredWidth(110);
		table_Seluruh_RiwayatReservasi.getColumnModel().getColumn(7).setPreferredWidth(100);
		
		scrollPane.setViewportView(table_Seluruh_RiwayatReservasi);
		
		JLabel lblNewLabel_2 = new JLabel("Cari :");
		lblNewLabel_2.setBounds(602, 21, 48, 14);
		contentPane.add(lblNewLabel_2);
		
		textField_Cari = new JTextField();
		textField_Cari.setText("Berdasarkan nama kasir/nama pelanggan/id pesanan");
		textField_Cari.setForeground(Color.GRAY);

		textField_Cari.addFocusListener(new FocusAdapter() {
	            @Override
	            public void focusGained(FocusEvent e) {
	                if (textField_Cari.getText().equals("Berdasarkan nama kasir/nama pelanggan/id pesanan")) {
	                	textField_Cari.setText("");
	                	textField_Cari.setForeground(Color.BLACK);
	                }
	            }

	            @Override
	            public void focusLost(FocusEvent e) {
	                if (textField_Cari.getText().isEmpty()) {
	                	textField_Cari.setForeground(Color.GRAY);
	                	textField_Cari.setText("Berdasarkan nama kasir/nama pelanggan/id pesanan");
	                }
	            }
	        });
		textField_Cari.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				refreshData();
			}
		});
		textField_Cari.setBounds(640, 18, 318, 20);
		contentPane.add(textField_Cari);
		textField_Cari.setColumns(10);
		
		loadData_RiwayatSeluruhReservasi("", "", "");
		contentPane.setFocusable(true);
        contentPane.requestFocusInWindow();
	}
	
	private void refreshData() {
		String tglDari = txt_Tgl_Dari.getText();
		String tglSampai = txt_Tgl_Sampai.getText();
		String keyword = textField_Cari.getText();
		
		// PERBAIKAN: Jika teks adalah placeholder, kosongkan string-nya
		if (tglDari.equals("yyyy-mm-dd")) tglDari = "";
		if (tglSampai.equals("yyyy-mm-dd")) tglSampai = "";
		if (keyword.equals("Berdasarkan nama kasir/nama pelanggan/id pesanan")) keyword = "";
		
		// Cek validitas tanggal
		boolean lengkap = (tglDari.length() == 10 && tglSampai.length() == 10);
		boolean kosong = (tglDari.isEmpty() && tglSampai.isEmpty());
		
		// Panggil Load Data
		// Perhatikan: Kalau tanggal tidak lengkap, kita hanya pakai keyword filter
		if (lengkap || kosong) {
			loadData_RiwayatSeluruhReservasi(tglDari, tglSampai, keyword);
		} else {
            // Jika user baru ngetik setengah tanggal, jangan load dulu (biar gak error SQL)
            // Tapi kalau keyword ada isinya, user mungkin mau cari tanpa tanggal
            if (!keyword.isEmpty()) {
                loadData_RiwayatSeluruhReservasi("", "", keyword);
            }
        }
	}
	
	private void loadData_RiwayatSeluruhReservasi(String tglDari, String tglSampai, String keyword) {
		DefaultTableModel model = (DefaultTableModel) table_Seluruh_RiwayatReservasi.getModel();
		model.setRowCount(0);

		String statusFilter = comboBox_Filter.getSelectedItem().toString();

		try {
			konfigurasi.KoneksiKeServer();
			if (konfigurasi.con == null) return;

			// QUERY: Ambil semua data, tapi nanti difilter di WHERE
			String sql = "SELECT * FROM reservasi_pemesananfnb WHERE 1=1 "; 

			boolean pakaiTanggal = (tglDari.length() == 10 && tglSampai.length() == 10);
			boolean pakaiCari = !keyword.trim().isEmpty();
			
			// --- LOGIC FILTER STATUS (KUNCI UTAMA) ---
			// Jika user pilih "Semua", kita tetap harus menyaring agar On Going TIDAK MASUK.
			// Jadi "Semua" di sini artinya "Semua yang Done atau Canceled".
			
			if (statusFilter.equals("Semua (Done & Canceled)")) {
				// Tampilkan Done ATAU Canceled
				sql += " AND status_pesanan IN ('Done (Paid)', 'Canceled') ";
			} else if (statusFilter.equals("Done (Paid)")) {
				sql += " AND status_pesanan = 'Done (Paid)' ";
			} else if (statusFilter.equals("Canceled")) {
				sql += " AND status_pesanan = 'Canceled' ";
			}

			// filter tanggal
			if (pakaiTanggal) {
				sql += " AND tgl_reservasi BETWEEN ? AND ? ";
			}
			
			// filter pencarian keyword
			if (pakaiCari) {
				sql += " AND (id_pesanan LIKE ? OR nama_user LIKE ? OR nama_pelanggan LIKE ?) ";
			}

			sql += " ORDER BY tgl_reservasi DESC";

			PreparedStatement ps = konfigurasi.con.prepareStatement(sql);

			int paramIndex = 1;
			
			if (pakaiTanggal) {
				ps.setString(paramIndex++, tglDari);
				ps.setString(paramIndex++, tglSampai);
			}
			
			if (pakaiCari) {
				String polaCari = "%" + keyword + "%";
				ps.setString(paramIndex++, polaCari); // id_user
				ps.setString(paramIndex++, polaCari); // nama_user
				ps.setString(paramIndex++, polaCari); // nama_pelanggan
			}

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				double harga = rs.getDouble("hrg_totPembayaran");
				String hargaTerformat = String.format("Rp %,.0f", harga).replace(",", ".");
				
				model.addRow(new Object[] { 
						rs.getString("id_user"),            
						rs.getString("nama_user"),            
						rs.getString("id_pesanan"),         
						rs.getString("tgl_reservasi"),      
						rs.getString("nama_pelanggan"),     
						rs.getString("nama_room"),          
						rs.getString("nama_paket"),         
						hargaTerformat,                     
						rs.getString("metode_pembayaran"),  
						rs.getString("status_pesanan")      
				});
			}

			rs.close();
			ps.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error load data: " + e.getMessage());
		}
	}
}