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
import javax.swing.JOptionPane;
import java.sql.*;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class dataRoom extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableData_RoomKaraoke;
	private JTextField txtCari_DataRoomKaraoke;

	/**
	 * Launch the application.
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					dataRoom frame = new dataRoom();
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
	public dataRoom() {
		setTitle("Data Room Karaoke ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 712, 586);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Add");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				formData_Room formRoom = new formData_Room();
				formRoom.setVisible(true);
				dispose();}
		});
		btnNewButton.setBounds(31, 21, 88, 22);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Edit");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int rowTerpilih = tableData_RoomKaraoke.getSelectedRow();
		        if (rowTerpilih == -1) {
		            JOptionPane.showMessageDialog(null, "Pilih data yang akan diedit!");
		            return;
		        }
							        String idRoom = 
							        		tableData_RoomKaraoke.getValueAt(rowTerpilih, 0).toString();
							        String namaRoom = 
							        		tableData_RoomKaraoke.getValueAt(rowTerpilih, 1).toString();
							        String tipeRoom =
							        		tableData_RoomKaraoke.getValueAt(rowTerpilih, 2).toString();
							        String fasilitasRoom =
							        		tableData_RoomKaraoke.getValueAt(rowTerpilih, 3).toString();
							        String kapasitasRoom =
							        		tableData_RoomKaraoke.getValueAt(rowTerpilih, 4).toString();
							        String smokingRoom =
							        		tableData_RoomKaraoke.getValueAt(rowTerpilih, 5).toString();
							        String available_Maintenance =
							        		tableData_RoomKaraoke.getValueAt(rowTerpilih, 6).toString();
							        String statusRoom =
							        		tableData_RoomKaraoke.getValueAt(rowTerpilih, 7).toString();
							         
							     // Buka formData_Room dengan constructor yang menerima data
							        formData_Room form= new formData_Room(
							                idRoom,
							                namaRoom,
							                tipeRoom,
							                fasilitasRoom,
							                kapasitasRoom,
							                smokingRoom,
							                available_Maintenance,
							                statusRoom
							        );

							        form.setVisible(true);
			}
				
				
			
		});
		btnNewButton_1.setBounds(137, 21, 88, 22);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Delete");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int rowTerpilih = tableData_RoomKaraoke.getSelectedRow();
				if (rowTerpilih == -1) {
				    JOptionPane.showMessageDialog(null, "Pilih baris yang ingin dihapus!");
				    return;
				}

				// Ambil idRoom dari baris yang dipilih
				String idRoom = tableData_RoomKaraoke.getValueAt(rowTerpilih, 0).toString(); // misal kolom 0 adalah ID Room

				// Konfirmasi sebelum hapus
				int konfirmasi = JOptionPane.showConfirmDialog(
				        null,
				        "Apakah Anda yakin ingin menghapus Room dengan ID " + idRoom + "?",
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

				        String sql = "DELETE FROM room WHERE id_room = ?";
				        PreparedStatement ps = konfigurasi.con.prepareStatement(sql);
				        ps.setString(1, idRoom);
				        ps.executeUpdate();
				        ps.close();

				        // Refresh tabel room
				        loadData(null);

				        JOptionPane.showMessageDialog(null, "Data Room berhasil dihapus.");
				    } catch (SQLException ex) {
				        JOptionPane.showMessageDialog(null, "Terjadi error: " + ex.getMessage());
				    }
				}

			}
		});
		btnNewButton_2.setBounds(242, 21, 88, 22);
		contentPane.add(btnNewButton_2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(31, 67, 657, 471);
		contentPane.add(scrollPane);
		
		
		tableData_RoomKaraoke = new JTable();
		tableData_RoomKaraoke.setBackground(new Color(255, 255, 255));
		DefaultTableModel model = new DefaultTableModel(
	            new Object[][] {},
	            new String[] {
	            		"Id Room", "Nama Room", "Tipe", "Fasilitas", "Kapasitas", "Smoking Area", "Available / Maintenance", "Status Room"
	            }
	        ) {
	            // Method ini membuat tabel TIDAK BISA diedit secara langsung
	            @Override
	            public boolean isCellEditable(int row, int column) {
	                return false;
	            }
	        };
	        
	        tableData_RoomKaraoke.setModel(model);
		
		
		tableData_RoomKaraoke.getColumnModel().getColumn(0).setPreferredWidth(71);
		tableData_RoomKaraoke.getColumnModel().getColumn(1).setPreferredWidth(118);
		tableData_RoomKaraoke.getColumnModel().getColumn(3).setPreferredWidth(90);
		tableData_RoomKaraoke.getColumnModel().getColumn(4).setPreferredWidth(80);
		tableData_RoomKaraoke.getColumnModel().getColumn(5).setPreferredWidth(106);
		tableData_RoomKaraoke.getColumnModel().getColumn(6).setPreferredWidth(157);
		tableData_RoomKaraoke.getColumnModel().getColumn(7).setPreferredWidth(100);
		scrollPane.setViewportView(tableData_RoomKaraoke);
		
		loadData(null);
		
		JLabel lblNewLabel = new JLabel("Cari : ");
		lblNewLabel.setBounds(348, 25, 48, 14);
		contentPane.add(lblNewLabel);
		
		txtCari_DataRoomKaraoke = new JTextField();
		txtCari_DataRoomKaraoke.setText("Berdasarkan Nama room/id room");
		txtCari_DataRoomKaraoke.setForeground(Color.GRAY); // Sekarang Color sudah dikenali

		txtCari_DataRoomKaraoke.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtCari_DataRoomKaraoke.getText().equals("Berdasarkan Nama room/id room")) {
                	txtCari_DataRoomKaraoke.setText("");
                	txtCari_DataRoomKaraoke.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtCari_DataRoomKaraoke.getText().isEmpty()) {
                	txtCari_DataRoomKaraoke.setForeground(Color.GRAY);
                	txtCari_DataRoomKaraoke.setText("Berdasarkan Nama room/id room");
                }
            }
        });
		txtCari_DataRoomKaraoke.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String keyword = txtCari_DataRoomKaraoke.getText().trim();

				if (keyword.isEmpty()) {
				    loadData(null); // tampilkan semua data
				    return;
				}

				DefaultTableModel model = (DefaultTableModel) tableData_RoomKaraoke.getModel();
				model.setRowCount(0);

				try {
				    konfigurasi.KoneksiKeServer();
				    if (konfigurasi.con == null) {
				        JOptionPane.showMessageDialog(null, "Gagal koneksi database");
				        return;
				    }

				    String sql = "SELECT * FROM room WHERE id_room LIKE ?OR nama_room LIKE ?";
				    PreparedStatement ps = konfigurasi.con.prepareStatement(sql);
				    ps.setString(1, "%" + keyword + "%");
				    ps.setString(2, "%" + keyword + "%");
				    ResultSet rs = ps.executeQuery();

				    while (rs.next()) {
				        Object[] row = {
				            rs.getString("id_room"),
				            rs.getString("nama_room"),
				            rs.getString("tipe_room"),
				            rs.getString("fasilitas_room"),
				            rs.getString("kapasitas_room"),
				            rs.getString("smoking_room"),
				            rs.getString("avail_maint_room"),
				            rs.getString("status_room")
				        };
				        model.addRow(row);
				    }

				    rs.close();
				    ps.close();
				} catch (SQLException ex) {
				    JOptionPane.showMessageDialog(null, "Terjadi error saat mencari: " + ex.getMessage());
				}
			}
		});
		txtCari_DataRoomKaraoke.setBounds(386, 22, 302, 20);
		contentPane.add(txtCari_DataRoomKaraoke);
		txtCari_DataRoomKaraoke.setColumns(10);

	}
	public void loadData(String keyword) {
	    DefaultTableModel model = (DefaultTableModel) tableData_RoomKaraoke.getModel();
	    model.setRowCount(0); // bersihin tabel

	    try {
	    	konfigurasi.KoneksiKeServer();
	        String sql;
	        PreparedStatement ps;

	        if (keyword == null || keyword.isEmpty()) {
	            sql = "SELECT * FROM room";
	            ps = konfigurasi.con.prepareStatement(sql);
	        } else {
	            sql = "SELECT * FROM room WHERE id_room LIKE ?";
	            ps = konfigurasi.con.prepareStatement(sql);
	            ps.setString(1, "%" + keyword + "%");
	        }

	        ResultSet rs = ps.executeQuery();
	        
	        while (rs.next()) {
	            model.addRow(new Object[] {
	                rs.getString("id_room"),
	                rs.getString("nama_room"),
	                rs.getString("tipe_room"),
	                rs.getString("fasilitas_room"),
	                rs.getString("kapasitas_room"),
	                rs.getString("smoking_room"),
	                rs.getString("avail_maint_room"),
	                rs.getString("status_room")
	            });
	        }

	    } catch (SQLException e) {
	        System.out.println("Error load data: " + e);
	    }
	}
}
