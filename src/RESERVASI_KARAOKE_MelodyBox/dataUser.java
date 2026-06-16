package RESERVASI_KARAOKE_MelodyBox;

import java.awt.EventQueue;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class dataUser extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtCari_DataUser;
	private JTable tableData_User;

	/**
	 * Launch the application.
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					dataUser frame = new dataUser();
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
	public dataUser() {
		setTitle("Data User");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 829, 525);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Add");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				formData_User formUser = new formData_User();
				formUser.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setBounds(52, 11, 88, 22);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Edit");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int rowTerpilih = tableData_User .getSelectedRow();
		        if (rowTerpilih == -1) {
		            JOptionPane.showMessageDialog(null, "Pilih data yang akan diedit!");
		            return;
		        }
							        String idUser= 
							        		tableData_User .getValueAt(rowTerpilih, 0).toString();
							        String namaUser = 
							        		tableData_User .getValueAt(rowTerpilih, 1).toString();
							        String username =
							        		tableData_User .getValueAt(rowTerpilih, 2).toString();
							        String password =
							        		tableData_User .getValueAt(rowTerpilih, 3).toString();
							        String role =
							        		tableData_User .getValueAt(rowTerpilih, 4).toString();
							        String noTelp =
							        		tableData_User .getValueAt(rowTerpilih, 5).toString();
							        
							         
							        formData_User form = new formData_User(
							            idUser,
							            namaUser,
							            username,
							            password,
							            role,
							            noTelp
							            
							        );

							        form.setVisible(true);
							        dispose();
			}
				
			
		});
		btnNewButton_1.setBounds(161, 11, 88, 22);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Delete");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int rowTerpilih = tableData_User.getSelectedRow();
        	    
        	    if (rowTerpilih == -1) {
        	        JOptionPane.showMessageDialog(null, "Pilih baris yang ingin dihapus!");
        	        return;
        	    }
        	    // Ambil id user dari baris yang dipilih
        	    String idUser =tableData_User.getValueAt(rowTerpilih, 0).toString();

        	    // Konfirmasi sebelum hapus
        	    int konfirmasi = JOptionPane.showConfirmDialog(
        	            null,
        	            "Apakah Anda yakin ingin menghapus user dengan ID " + idUser + "?",
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

        	            String sql = "DELETE FROM user WHERE id_user = ?";
        	            PreparedStatement ps = konfigurasi.con.prepareStatement(sql);
        	            ps.setString(1, idUser);
        	            ps.executeUpdate();
        	            ps.close();

        	            // Refresh tabel
        	            loadDataUser();

        	            JOptionPane.showMessageDialog(null, "Data user berhasil dihapus.");
        	        } catch (SQLException ex) {
        	            JOptionPane.showMessageDialog(null, "Terjadi error: " + ex.getMessage());
        	        }
        	  }
				
			}
		});
		btnNewButton_2.setBounds(272, 11, 88, 22);
		contentPane.add(btnNewButton_2);
		
		JLabel lblNewLabel = new JLabel("Cari : ");
		lblNewLabel.setBounds(382, 15, 48, 14);
		contentPane.add(lblNewLabel);
		
		txtCari_DataUser = new JTextField();
		txtCari_DataUser.setText("Berdasarkan Nama user/id user");
		txtCari_DataUser.setForeground(Color.GRAY); // Sekarang Color sudah dikenali

		txtCari_DataUser.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtCari_DataUser.getText().equals("Berdasarkan Nama user/id user")) {
                	txtCari_DataUser.setText("");
                	txtCari_DataUser.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtCari_DataUser.getText().isEmpty()) {
                	txtCari_DataUser.setForeground(Color.GRAY);
                	txtCari_DataUser.setText("Berdasarkan Nama user/id user");
                }
            }
        });
		txtCari_DataUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String keyword = txtCari_DataUser.getText().trim();

			    if (keyword.isEmpty()) {
			        loadDataUser(); // tampilkan semua data jika kosong
			        return;
			    }

			    DefaultTableModel model = (DefaultTableModel) tableData_User.getModel();
			    model.setRowCount(0); // reset tabel

			    try {
			        konfigurasi.KoneksiKeServer();
			        if (konfigurasi.con == null) {
			            JOptionPane.showMessageDialog(null, "Gagal koneksi database");
			            return;
			        }

			        String sql = "SELECT * FROM user WHERE nama_user LIKE ?OR id_user LIKE ?";
			        PreparedStatement ps = konfigurasi.con.prepareStatement(sql);
			        ps.setString(1, "%" + keyword + "%");
			        ps.setString(2, "%" + keyword + "%");
			        ResultSet rs = ps.executeQuery();

			        while (rs.next()) {
			            Object[] row = {
			                rs.getString("id_user"),
			                rs.getString("nama_user"),
			                rs.getString("username"),
			                rs.getString("password"), 
			                rs.getString("role"),
			                rs.getString("noTelp_user")
			            };
			            model.addRow(row);
			        }

			        rs.close();
			        ps.close();

			    } catch (Exception ex) {
			        JOptionPane.showMessageDialog(
			            null,
			            "Terjadi error saat mencari data user: " + ex.getMessage()
			        );
			        ex.printStackTrace();
			    }
			}
				
			
		});
		txtCari_DataUser.setBounds(440, 12, 365, 20);
		contentPane.add(txtCari_DataUser);
		txtCari_DataUser.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 60, 795, 417);
		contentPane.add(scrollPane);
		
		tableData_User = new JTable();
		DefaultTableModel model = new DefaultTableModel(
	            new Object[][] {},
	            new String[] {
	            		"Id User", "Nama ", "Username", "Password", "Role", "No Telp"
	           }
	        ) {
	            // Method ini membuat tabel TIDAK BISA diedit secara langsung
	            @Override
	            public boolean isCellEditable(int row, int column) {
	                return false;
	            }
	        };
	        
	        tableData_User.setModel(model);
		
		tableData_User.getColumnModel().getColumn(1).setPreferredWidth(211);
		tableData_User.getColumnModel().getColumn(2).setPreferredWidth(82);
		tableData_User.getColumnModel().getColumn(3).setPreferredWidth(87);
		tableData_User.getColumnModel().getColumn(5).setPreferredWidth(91);
		scrollPane.setViewportView(tableData_User);
		
		loadDataUser();

	}
	
	// BUAT LOAD DATA USER
	private void loadDataUser() {
	    DefaultTableModel model = (DefaultTableModel) tableData_User.getModel();
	    model.setRowCount(0); // reset tabel

	    konfigurasi.KoneksiKeServer();
	    if (konfigurasi.con == null) {
	        JOptionPane.showMessageDialog(this, "Gagal koneksi ke database");
	        return;
	    }

	    try {
	        String sql = "SELECT * FROM `user`";
	        Statement st = konfigurasi.con.createStatement();
	        ResultSet rs = st.executeQuery(sql);

	        while (rs.next()) {
	            Object[] row = {
	                rs.getString("id_user"),
	                rs.getString("nama_user"),
	                rs.getString("username"),
	                rs.getString("password"),
	                rs.getString("role"),
	                rs.getString("noTelp_user")
	            };
	            model.addRow(row);
	        }

	        rs.close();
	        st.close();

	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(this, "Gagal load data user: " + e.getMessage());
	        e.printStackTrace();
	    }
	}


}