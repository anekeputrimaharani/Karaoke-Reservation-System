package RESERVASI_KARAOKE_MelodyBox;

import java.awt.EventQueue;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;

public class reservasiKaraoke_PemesananFnB_Page extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table_ResKaraoke_PemesananFnB;
	private JTextField textField;
	private JTextField txttanggalDataReservasi;
	private JTextField txt_jmh_onGoing;
	private JTextField txt_jmh_done;
	private JTextField txt_jmh_canceled;

	/**
	 * Launch the application.
	 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					reservasiKaraoke_PemesananFnB_Page frame = new reservasiKaraoke_PemesananFnB_Page();
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
	public reservasiKaraoke_PemesananFnB_Page() {
		setTitle("Reservasi & Pemesanan F&B");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1010, 559);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Add");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				formRincian_ReservasiKaraoke_PemesananFnB formRes_FnB = new formRincian_ReservasiKaraoke_PemesananFnB();
				formRes_FnB.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setBounds(10, 53, 88, 22);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Edit");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int rowTerpilih = table_ResKaraoke_PemesananFnB.getSelectedRow();

		        if (rowTerpilih == -1) {
		            JOptionPane.showMessageDialog(
		                reservasiKaraoke_PemesananFnB_Page.this,
		                "Pilih data reservasi yang akan diedit!",
		                "Peringatan",
		                JOptionPane.WARNING_MESSAGE
		            );
		            return;
		        }

		        // Cek sttaus sebelum edit 
		        String status = table_ResKaraoke_PemesananFnB.getValueAt(rowTerpilih, 9).toString();

		        // Cek apakah statusnya "Done" atau "Canceled"
		        if (status != null && (status.startsWith("Done") || status.equalsIgnoreCase("Canceled"))) {
		            JOptionPane.showMessageDialog(
		                reservasiKaraoke_PemesananFnB_Page.this,
		                "Pesanan tidak dapat diedit, pesanan telah selesai atau dibatalkan!",
		                "Akses Ditolak",
		                JOptionPane.WARNING_MESSAGE 
		            );
		            return; //stop disini dan edit gabisa dibuka
		        }

		        // Ambil PRIMARY KEY (Id Pesanan)
		        String idPesanan = table_ResKaraoke_PemesananFnB
		                .getValueAt(rowTerpilih, 2)
		                .toString();

		        formRincian_ReservasiKaraoke_PemesananFnB formEdit =
		                new formRincian_ReservasiKaraoke_PemesananFnB(idPesanan);

		        formEdit.addWindowListener(new java.awt.event.WindowAdapter() {
				    @Override
				    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
				        loadDataReservasi();
				    }
				});

		        formEdit.setLocationRelativeTo(null);
		        formEdit.setVisible(true); 
			}
		});
		btnNewButton_1.setBounds(108, 53, 88, 22);
		contentPane.add(btnNewButton_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 86, 976, 414);
		contentPane.add(scrollPane);
		
		table_ResKaraoke_PemesananFnB = new JTable();
		DefaultTableModel model = new DefaultTableModel(
	            new Object[][] {},
	            new String[] {
	            		"Id Kasir", "Nama Kasir", "Id Pesanan", "Tanggal Reservasi", "Nama Pelanggan ", "Room", "Paket", "Total Pembayaran ", "Metode Pembayaran", "Status "
	            }
	        ) {
	            // Method ini membuat tabel TIDAK BISA diedit secara langsung
	            @Override
	            public boolean isCellEditable(int row, int column) {
	                return false;
	            }
	        };
	        
	        table_ResKaraoke_PemesananFnB.setModel(model);
		
		table_ResKaraoke_PemesananFnB.getColumnModel().getColumn(1).setPreferredWidth(133);
		table_ResKaraoke_PemesananFnB.getColumnModel().getColumn(2).setPreferredWidth(136);
		table_ResKaraoke_PemesananFnB.getColumnModel().getColumn(3).setPreferredWidth(121);
		table_ResKaraoke_PemesananFnB.getColumnModel().getColumn(4).setPreferredWidth(119);
		table_ResKaraoke_PemesananFnB.getColumnModel().getColumn(5).setPreferredWidth(81);
		table_ResKaraoke_PemesananFnB.getColumnModel().getColumn(6).setPreferredWidth(79);
		table_ResKaraoke_PemesananFnB.getColumnModel().getColumn(7).setPreferredWidth(119);
		table_ResKaraoke_PemesananFnB.getColumnModel().getColumn(8).setPreferredWidth(125);
		table_ResKaraoke_PemesananFnB.getColumnModel().getColumn(9).setPreferredWidth(110);
		scrollPane.setViewportView(table_ResKaraoke_PemesananFnB);
		
		JLabel lblNewLabel = new JLabel("Cari : ");
		lblNewLabel.setBounds(216, 57, 71, 14);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField .setText("Berdasarkan Nama pelanggan /id pesanan");
		textField .setForeground(Color.GRAY); 

		textField .addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField .getText().equals("Berdasarkan Nama pelanggan /id pesanan")) {
                	textField .setText("");
                	textField .setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField .getText().isEmpty()) {
                	textField .setForeground(Color.GRAY);
                	textField .setText("Berdasarkan Nama pelanggan /id pesanan");
                }
            }
        });
		textField.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        
		        // 1. Ambil kata kunci dari text field
		        String keyword = textField.getText().trim();

		        // 2. Jika kosong, kembalikan ke data awal (semua data)
		        if (keyword.isEmpty()) {
		            loadDataReservasi(); 
		            return;
		        }

		        // 3. Ambil model tabel & kosongkan baris
		        DefaultTableModel model = (DefaultTableModel) table_ResKaraoke_PemesananFnB.getModel();
		        model.setRowCount(0); 

		        try {
		            // 4. Buka Koneksi
		            konfigurasi.KoneksiKeServer();
		            if (konfigurasi.con == null) {
		                JOptionPane.showMessageDialog(null, "Gagal koneksi database");
		                return;
		            }

		            // 5. Query Pencarian menggunakan LIKE
		            // cari berdasarkan nama pelanggan atau id pesanan
		            String sql = "SELECT * FROM reservasi_pemesananfnb WHERE nama_pelanggan LIKE ?OR id_pesanan LIKE ?";
		            java.sql.PreparedStatement ps = konfigurasi.con.prepareStatement(sql);
		            ps.setString(1, "%" + keyword + "%"); // %keyword% artinya cari yang mengandung kata tersebut
		            ps.setString(2, "%" + keyword + "%");
		            ResultSet rs = ps.executeQuery();

		            while (rs.next()) {
		                // 6. Masukkan data ke tabel 
		                Object[] row = {
		                    rs.getString("id_user"),            // Id Kasir
		                    rs.getString("nama_user"),           // nama kasir
		                    rs.getString("id_pesanan"),         // Id Pesanan
		                    rs.getString("tgl_reservasi"),      // Tanggal
		                    rs.getString("nama_pelanggan"),     // Nama Pelanggan
		                    rs.getString("nama_room"),          // Nama Room
			                rs.getString("nama_paket"),         // Nama Paket
		                    rs.getString("hrg_totPembayaran"),  // Total Bayar
		                    rs.getString("metode_pembayaran"),  // Metode
		                    rs.getString("status_pesanan")      // Status
		                };
		                model.addRow(row);
		            }

		            rs.close();
		            ps.close();
		            // konfigurasi.con.close(); 

		        } catch (Exception ex) {
		            JOptionPane.showMessageDialog(
		                null,
		                "Terjadi error saat mencari data: " + ex.getMessage()
		            );
		            ex.printStackTrace();
		        }
		    }
		});
		textField.setBounds(255, 54, 323, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Data reservasi tanggal :");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setBounds(10, 17, 157, 14);
		contentPane.add(lblNewLabel_1);
		
		txttanggalDataReservasi = new JTextField();
		txttanggalDataReservasi.setBounds(156, 17, 115, 18);
		contentPane.add(txttanggalDataReservasi);
		txttanggalDataReservasi.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("On Going (not paid yet) : ");
		lblNewLabel_2.setBounds(464, 17, 139, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("Done (paid) : ");
		lblNewLabel_2_1.setBounds(686, 16, 79, 14);
		contentPane.add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_2_1_1 = new JLabel("Canceled : ");
		lblNewLabel_2_1_1.setBounds(836, 17, 75, 14);
		contentPane.add(lblNewLabel_2_1_1);
		
		txt_jmh_onGoing = new JTextField();
		txt_jmh_onGoing.setBounds(601, 14, 65, 20);
		contentPane.add(txt_jmh_onGoing);
		txt_jmh_onGoing.setColumns(10);
		txt_jmh_onGoing.setEditable(false);
		
		txt_jmh_done = new JTextField();
		txt_jmh_done.setColumns(10);
		txt_jmh_done.setBounds(759, 15, 65, 20);
		contentPane.add(txt_jmh_done);
		txt_jmh_done.setEditable(false);
		
		txt_jmh_canceled = new JTextField();
		txt_jmh_canceled.setColumns(10);
		txt_jmh_canceled.setBounds(908, 14, 65, 20);
		contentPane.add(txt_jmh_canceled);
		txt_jmh_onGoing.setEditable(false);
		
	
		
		loadDataReservasi();
		setTanggalHariIni();

	}
	
	private void loadDataReservasi() {
		DefaultTableModel model = (DefaultTableModel) table_ResKaraoke_PemesananFnB.getModel();
		model.setRowCount(0); 

		konfigurasi.KoneksiKeServer();
		if (konfigurasi.con == null) {
			JOptionPane.showMessageDialog(this, "Gagal koneksi ke database");
			return;
		}

		try {
			// 1. Ambil Tanggal Hari Ini
			java.util.Date now = new java.util.Date();
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
			String hariIni = sdf.format(now);

			// Ambil data hari ini, lalu diurutkan ID dari yang paling besar (paling baru)
			String sql = "SELECT * FROM reservasi_pemesananfnb WHERE tgl_reservasi = ? ORDER BY id_pesanan DESC";
			
			PreparedStatement ps = konfigurasi.con.prepareStatement(sql);
			ps.setString(1, hariIni);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Object[] row = {
					rs.getString("id_user"),
					rs.getString("nama_user"),            
					rs.getString("id_pesanan"),
					rs.getString("tgl_reservasi"),
					rs.getString("nama_pelanggan"),
					rs.getString("nama_room"),
					rs.getString("nama_paket"),
					rs.getString("hrg_totPembayaran"),
					rs.getString("metode_pembayaran"),
					rs.getString("status_pesanan")
				};
				model.addRow(row);
			}

			rs.close();
			ps.close();
			hitungStatistikHarian(hariIni);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Gagal load data reservasi: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void setTanggalHariIni() {
	   
	    java.util.Date now = new java.util.Date();
	    
	    
	    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
	    
	    
	    String tanggalStr = sdf.format(now);
	    
	   
	    txttanggalDataReservasi.setText(tanggalStr);
	    
	    
	    txttanggalDataReservasi.setEditable(false); 
	}
	
	private void hitungStatistikHarian(String tanggalHariIni) {
	    try {
	        String sql = "SELECT status_pesanan, COUNT(*) as jumlah " +
	                     "FROM reservasi_pemesananfnb " +
	                     "WHERE tgl_reservasi = ? " +
	                     "GROUP BY status_pesanan";

	        PreparedStatement ps = konfigurasi.con.prepareStatement(sql);
	        ps.setString(1, tanggalHariIni);
	        ResultSet rs = ps.executeQuery();
	        
	        int onGoing = 0;
	        int done = 0;
	        int canceled = 0;

	        while (rs.next()) {
	            String status = rs.getString("status_pesanan");
	            int jumlah = rs.getInt("jumlah");

	           
	            if (status.equalsIgnoreCase("On Going (not paid yet)")) {
	                onGoing = jumlah;
	            } 
	           
	            else if (status.startsWith("Done (Paid)")) { 
	                done = jumlah; 
	            } 
	            else if (status.equalsIgnoreCase("Canceled")) {
	                canceled = jumlah;
	            }
	        }

	        txt_jmh_onGoing.setText(String.valueOf(onGoing));
	        txt_jmh_done.setText(String.valueOf(done));
	        txt_jmh_canceled.setText(String.valueOf(canceled));

	        rs.close();
	        ps.close();

	    } catch (Exception e) {
	        System.out.println("Gagal hitung statistik: " + e.getMessage());
	    }
	}
	
}
