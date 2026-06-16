package RESERVASI_KARAOKE_MelodyBox;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionEvent;

public class dataMenu_Food_Beverages extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtCariFnB;
	private JTable table_DataMenuFnB;

	/**
	 * Launch the application.
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					dataMenu_Food_Beverages frame = new dataMenu_Food_Beverages();
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
	public dataMenu_Food_Beverages() {
		setTitle("Data Menu F&B MelodyBox");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 525);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Add");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				formData_MenuFoodBeverages formMenu_FnB= new formData_MenuFoodBeverages();
				formMenu_FnB.setVisible(true);
	            dispose();
			}
		});
		btnNewButton.setBounds(53, 11, 88, 22);
		contentPane.add(btnNewButton);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int rowTerpilih = table_DataMenuFnB.getSelectedRow();
				if (rowTerpilih == -1) {
					JOptionPane.showMessageDialog(null, "Pilih data yang akan diedit!");
					return;
				}
				
				try {
					// PENGAMAN: Cek dulu datanya null atau tidak
					Object objId   = table_DataMenuFnB.getValueAt(rowTerpilih, 0);
					Object objNama = table_DataMenuFnB.getValueAt(rowTerpilih, 1);
					Object objKat  = table_DataMenuFnB.getValueAt(rowTerpilih, 2);
					Object objHrg  = table_DataMenuFnB.getValueAt(rowTerpilih, 3);
					Object objKet  = table_DataMenuFnB.getValueAt(rowTerpilih, 4); 
					Object objStat = table_DataMenuFnB.getValueAt(rowTerpilih, 5);

					// Kalau null, ganti jadi string kosong ""
					String idMenu     = (objId != null)   ? objId.toString()   : "";
					String namaMenu   = (objNama != null) ? objNama.toString() : "";
					String kategori   = (objKat != null)  ? objKat.toString()  : "";
					String hrg_unit   = (objHrg != null)  ? objHrg.toString()  : "";
					String keterangan = (objKet != null)  ? objKet.toString()  : "";
					String statusMenu = (objStat != null) ? objStat.toString() : "";
					
					formData_MenuFoodBeverages form = new formData_MenuFoodBeverages(
						idMenu,
						namaMenu,
						kategori,
						hrg_unit,
						keterangan,
						statusMenu
					);

					form.setVisible(true);
					dispose();
					
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
				}
			}
		});
		btnEdit.setBounds(162, 11, 88, 22);
		contentPane.add(btnEdit);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int rowTerpilih = table_DataMenuFnB.getSelectedRow();

				if (rowTerpilih == -1) {
				    JOptionPane.showMessageDialog(null, "Pilih menu yang ingin dihapus!");
				    return;
				}

				// Ambil ID menu dari baris terpilih
				String idMenu = table_DataMenuFnB.getValueAt(rowTerpilih, 0).toString();

				// Konfirmasi hapus
				int konfirmasi = JOptionPane.showConfirmDialog(
				        null,
				        "Apakah Anda yakin ingin menghapus menu dengan ID " + idMenu + "?",
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

				        String sql = "DELETE FROM menu_fnb WHERE id_menu = ?";
				        PreparedStatement ps = konfigurasi.con.prepareStatement(sql);
				        ps.setString(1, idMenu);
				        ps.executeUpdate();
				        ps.close();

				        // Refresh tabel
				        loadDataMenuFnB();

				        JOptionPane.showMessageDialog(null, "Data menu F&B berhasil dihapus.");

				    } catch (SQLException ex) {
				        JOptionPane.showMessageDialog(null, "Terjadi error: " + ex.getMessage());
				        ex.printStackTrace();
				    }
				}

			}
		});
		btnDelete.setBounds(273, 11, 88, 22);
		contentPane.add(btnDelete);
		
		JLabel lblNewLabel = new JLabel("Cari : ");
		lblNewLabel.setBounds(384, 15, 48, 14);
		contentPane.add(lblNewLabel);
		
		txtCariFnB = new JTextField();
		txtCariFnB.setText("Berdasarkan Nama menu/id menu");
		txtCariFnB.setForeground(Color.GRAY); 

		txtCariFnB.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtCariFnB.getText().equals("Berdasarkan Nama menu/id menu")) {
                	txtCariFnB.setText("");
                	txtCariFnB.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtCariFnB.getText().isEmpty()) {
                	txtCariFnB.setForeground(Color.GRAY);
                	txtCariFnB.setText("Berdasarkan Nama menu/id menu");
                }
            }
        });
		txtCariFnB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String keyword = txtCariFnB.getText().trim();

				if (keyword.isEmpty()) {
				    loadDataMenuFnB(); // tampilkan semua data
				    return;
				}

				DefaultTableModel model = (DefaultTableModel) table_DataMenuFnB.getModel();
				model.setRowCount(0); // reset tabel

				try {
				    konfigurasi.KoneksiKeServer();
				    if (konfigurasi.con == null) {
				        JOptionPane.showMessageDialog(null, "Gagal koneksi database");
				        return;
				    }

				    String sql = "SELECT * FROM menu_fnb WHERE nama_menu LIKE ?OR id_menu LIKE ?";
				    PreparedStatement ps = konfigurasi.con.prepareStatement(sql);
				    ps.setString(1, "%" + keyword + "%");
				    ps.setString(2, "%" + keyword + "%");
				    ResultSet rs = ps.executeQuery();

				    while (rs.next()) {
				        Object[] row = {
				            rs.getString("id_menu"),
				            rs.getString("nama_menu"),
				            rs.getString("kategori_menu"),
				            rs.getString("hrg_per_unit"),
				            rs.getString("keterangan"),
				            rs.getString("status_menu")
				        };
				        model.addRow(row);
				    }

				    rs.close();
				    ps.close();

				} catch (SQLException ex) {
				    JOptionPane.showMessageDialog(
				        null,
				        "Terjadi error saat mencari menu F&B: " + ex.getMessage()
				    );
				}

			}
		});
		txtCariFnB.setBounds(420, 12, 256, 20);
		contentPane.add(txtCariFnB);
		txtCariFnB.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 51, 666, 426);
		contentPane.add(scrollPane);
		
		table_DataMenuFnB = new JTable();
		DefaultTableModel model = new DefaultTableModel(
	            new Object[][] {},
	            new String[] {
	            		"Id Menu", "Nama Menu", "Kategori", "Harga/Unit", "Keterangan", "Status"
	           }
	        ) {
	            // Method ini membuat tabel TIDAK BISA diedit secara langsung
	            @Override
	            public boolean isCellEditable(int row, int column) {
	                return false;
	            }
	        };
	        
	        table_DataMenuFnB.setModel(model);
		
		table_DataMenuFnB.getColumnModel().getColumn(0).setPreferredWidth(61);
		table_DataMenuFnB.getColumnModel().getColumn(1).setPreferredWidth(122);
		table_DataMenuFnB.getColumnModel().getColumn(2).setPreferredWidth(90);
		table_DataMenuFnB.getColumnModel().getColumn(3).setPreferredWidth(95);
		table_DataMenuFnB.getColumnModel().getColumn(4).setPreferredWidth(125);
		table_DataMenuFnB.getColumnModel().getColumn(5).setPreferredWidth(88);
		scrollPane.setViewportView(table_DataMenuFnB);
		
		loadDataMenuFnB();

	}
	// LOAD DATA MENU FOOD & BEVERAGES
	private void loadDataMenuFnB() {
	    DefaultTableModel model = (DefaultTableModel) table_DataMenuFnB.getModel();
	    model.setRowCount(0); // reset tabel

	    konfigurasi.KoneksiKeServer();
	    if (konfigurasi.con == null) {
	        JOptionPane.showMessageDialog(this, "Gagal koneksi ke database");
	        return;
	    }

	    try {
	        String sql = "SELECT * FROM menu_fnb";
	        Statement st = konfigurasi.con.createStatement();
	        ResultSet rs = st.executeQuery(sql);

	        while (rs.next()) {
	            Object[] row = {
	                rs.getString("id_menu"),
	                rs.getString("nama_menu"),
	                rs.getString("kategori_menu"),
	                rs.getString("hrg_per_unit"),
	                rs.getString("keterangan"),
	                rs.getString("status_menu")
	            };
	            model.addRow(row);
	        }

	        rs.close();
	        st.close();

	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(this, "Gagal load data menu F&B: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

}
