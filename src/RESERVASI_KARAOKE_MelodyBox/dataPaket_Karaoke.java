package RESERVASI_KARAOKE_MelodyBox;

import java.awt.Color;

import java.awt.EventQueue;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionEvent;

public class dataPaket_Karaoke extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableData_PaketKaraoke;
	private JTextField txtCari_DataPaketKaraoke;

	/**
	 * Launch the application.
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					dataPaket_Karaoke frame = new dataPaket_Karaoke();
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
	public dataPaket_Karaoke() {
		setTitle("Data Paket Karaoke MelodyBox");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1227, 525);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Add");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				formData_PaketKaraoke formPaketKaraoke = new formData_PaketKaraoke();
	            formPaketKaraoke.setVisible(true);
	            dispose();
			}
		});
		btnNewButton.setBounds(40, 22, 88, 22);
		contentPane.add(btnNewButton);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int rowTerpilih = tableData_PaketKaraoke.getSelectedRow();
		        if (rowTerpilih == -1) {
		            JOptionPane.showMessageDialog(null, "Pilih data yang akan diedit!");
		            return;
		        }
							        String idPaket= 
							        		tableData_PaketKaraoke.getValueAt(rowTerpilih, 0).toString();
							        String namaPaket = 
							        		tableData_PaketKaraoke.getValueAt(rowTerpilih, 1).toString();
							        String smokingPaket =
							        		tableData_PaketKaraoke.getValueAt(rowTerpilih, 2).toString();
							        String kapasitasPaket =
							        		tableData_PaketKaraoke.getValueAt(rowTerpilih, 3).toString();
							        String hargaWeekdayPagi =
							        		tableData_PaketKaraoke.getValueAt(rowTerpilih, 4).toString();
							        String hargaWeekdaySore =
							        		tableData_PaketKaraoke.getValueAt(rowTerpilih, 5).toString();
							        String hargaWeekendPagi =
							        		tableData_PaketKaraoke.getValueAt(rowTerpilih, 6).toString();
							        String hargaWeekendSore =
							        		tableData_PaketKaraoke.getValueAt(rowTerpilih, 7).toString();
							         
							        formData_PaketKaraoke form = new formData_PaketKaraoke(
							            idPaket,
							            namaPaket,
							            smokingPaket,
							            kapasitasPaket,
							            hargaWeekdayPagi,
							            hargaWeekdaySore,
							            hargaWeekendPagi,
							            hargaWeekendSore
							        );

							        form.setVisible(true);
							        dispose();
			}
			
		});
		btnEdit.setBounds(138, 22, 88, 22);
		contentPane.add(btnEdit);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int rowTerpilih = tableData_PaketKaraoke.getSelectedRow();
        	    
        	    if (rowTerpilih == -1) {
        	        JOptionPane.showMessageDialog(null, "Pilih baris yang ingin dihapus!");
        	        return;
        	    }
        	    // Ambil noTelp member dari baris yang dipilih
        	    String idPaket =tableData_PaketKaraoke.getValueAt(rowTerpilih, 0).toString();

        	    // Konfirmasi sebelum hapus
        	    int konfirmasi = JOptionPane.showConfirmDialog(
        	            null,
        	            "Apakah Anda yakin ingin menghapus paket dengan ID " + idPaket + "?",
        	            "Konfirmasi Hapus",
        	            JOptionPane.YES_NO_OPTION
        	    );

        	    if (konfirmasi == JOptionPane.YES_OPTION) {
        	        try {
        	            konfigurasi.KoneksiKeServer();
        	            if (konfigurasi.con == null) {
        	                JOptionPane.showMessageDialog(null, "Gagal koneksi database");
        	                return;
        	            }

        	            String sql = "DELETE FROM paket_karaoke WHERE id_Paket = ?";
        	            PreparedStatement ps = konfigurasi.con.prepareStatement(sql);
        	            ps.setString(1, idPaket);
        	            ps.executeUpdate();
        	            ps.close();

        	            // Refresh tabel
        	            loadDataPaketKaraoke();

        	            JOptionPane.showMessageDialog(null, "Data paket berhasil dihapus.");
        	        } catch (SQLException ex) {
        	            JOptionPane.showMessageDialog(null, "Terjadi error: " + ex.getMessage());
        	        }
        	  }
			}
		});
		btnDelete.setBounds(236, 22, 88, 22);
		contentPane.add(btnDelete);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 66, 1193, 411);
		contentPane.add(scrollPane);
		
		tableData_PaketKaraoke = new JTable();
		DefaultTableModel model = new DefaultTableModel(
	            new Object[][] {},
	            new String[] {
	            		"Id", "Nama Paket", "Smoking Paket", "Kapasitas", "Hrg (Sen - Jum) | 10.00 - 15.59", "Hrg (Sen - Jum) | 16.00 - 23.59", "Hrg (Sab - Ming) | 10.00 - 15.59", "Hrg (Sab - Ming) | 16.00 - 23.59"
	          }
	        ) {
	            // Method ini membuat tabel TIDAK BISA diedit secara langsung
	            @Override
	            public boolean isCellEditable(int row, int column) {
	                return false;
	            }
	        };
	        
	        tableData_PaketKaraoke.setModel(model);
		
		tableData_PaketKaraoke.getColumnModel().getColumn(0).setPreferredWidth(61);
		tableData_PaketKaraoke.getColumnModel().getColumn(1).setPreferredWidth(148);
		tableData_PaketKaraoke.getColumnModel().getColumn(2).setPreferredWidth(99);
		tableData_PaketKaraoke.getColumnModel().getColumn(3).setPreferredWidth(88);
		tableData_PaketKaraoke.getColumnModel().getColumn(4).setPreferredWidth(167);
		tableData_PaketKaraoke.getColumnModel().getColumn(5).setPreferredWidth(167);
		tableData_PaketKaraoke.getColumnModel().getColumn(6).setPreferredWidth(167);
		tableData_PaketKaraoke.getColumnModel().getColumn(7).setPreferredWidth(167);
		scrollPane.setViewportView(tableData_PaketKaraoke);
		
		JLabel lblNewLabel = new JLabel("Cari  : ");
		lblNewLabel.setBounds(347, 26, 48, 14);
		contentPane.add(lblNewLabel);
		
		txtCari_DataPaketKaraoke = new JTextField();
		txtCari_DataPaketKaraoke.setText("Berdasarkan Nama paket/id paket");
		txtCari_DataPaketKaraoke.setForeground(Color.GRAY); 

		txtCari_DataPaketKaraoke.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtCari_DataPaketKaraoke.getText().equals("Berdasarkan Nama paket/id paket")) {
                	txtCari_DataPaketKaraoke.setText("");
                	txtCari_DataPaketKaraoke.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtCari_DataPaketKaraoke.getText().isEmpty()) {
                	txtCari_DataPaketKaraoke.setForeground(Color.GRAY);
                	txtCari_DataPaketKaraoke.setText("Berdasarkan Nama paket/id paket");
                }
            }
        });
		txtCari_DataPaketKaraoke.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String keyword = txtCari_DataPaketKaraoke.getText().trim();

				if (keyword.isEmpty()) {
				    loadDataPaketKaraoke(); // tampilkan semua data
				    return;
				}

				DefaultTableModel model = (DefaultTableModel) tableData_PaketKaraoke.getModel();
				model.setRowCount(0);

				try {
				    konfigurasi.KoneksiKeServer();
				    if (konfigurasi.con == null) {
				        JOptionPane.showMessageDialog(null, "Gagal koneksi database");
				        return;
				    }

				    String sql = "SELECT * FROM paket_karaoke WHERE id_paket LIKE ?OR nama_paket LIKE ?";
				    PreparedStatement ps = konfigurasi.con.prepareStatement(sql);
				    ps.setString(1, "%" + keyword + "%");
				    ps.setString(2, "%" + keyword + "%");
				    ResultSet rs = ps.executeQuery();

				    while (rs.next()) {
				        Object[] row = {
				            rs.getString("id_paket"),
				            rs.getString("nama_paket"),
				            rs.getString("smoking_paket"),
				            rs.getString("kapasitas_paket"),
				            rs.getString("hrg_weekdays_pagi"),
				            rs.getString("hrg_weekdays_sore"),
				            rs.getString("hrg_weekend_pagi"),
				            rs.getString("hrg_weekend_sore")
				        };
				        model.addRow(row);
				    }

				    rs.close();
				    ps.close();

				} catch (SQLException ex) {
				    JOptionPane.showMessageDialog(
				        null,
				        "Terjadi error saat mencari data paket: " + ex.getMessage()
				    );
				}

			}
		});
		txtCari_DataPaketKaraoke.setBounds(382, 23, 327, 20);
		contentPane.add(txtCari_DataPaketKaraoke);
		txtCari_DataPaketKaraoke.setColumns(10);
		
		 loadDataPaketKaraoke(); 

	}
	//BUAT LOAD DATANYA:
		private void loadDataPaketKaraoke() {
		    DefaultTableModel model = (DefaultTableModel) tableData_PaketKaraoke.getModel();
		    model.setRowCount(0); // reset tabel

		    konfigurasi.KoneksiKeServer();
		    if (konfigurasi.con == null) {
		        JOptionPane.showMessageDialog(this, "Gagal koneksi ke database");
		        return;
		    }

		    try {
		        String sql = "SELECT * FROM paket_karaoke";
		        Statement st = konfigurasi.con.createStatement();
		        ResultSet rs = st.executeQuery(sql);

		        while (rs.next()) {
		            Object[] row = {
		                rs.getString("id_paket"),
		                rs.getString("nama_paket"),
		                rs.getString("smoking_paket"),
		                rs.getString("kapasitas_paket"),
		                rs.getString("hrg_weekdays_pagi"),
		                rs.getString("hrg_weekdays_sore"),
		                rs.getString("hrg_weekend_pagi"),
		                rs.getString("hrg_weekend_sore")
		            };
		            model.addRow(row);
		        }

		        rs.close();
		        st.close();

		    } catch (Exception e) {
		        JOptionPane.showMessageDialog(this, "Gagal load data: " + e.getMessage());
		        e.printStackTrace();
		    }
		}
}
