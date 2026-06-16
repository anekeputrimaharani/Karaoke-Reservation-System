package RESERVASI_KARAOKE_MelodyBox;

import java.awt.EventQueue;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
import java.awt.event.ActionEvent;

public class manajemenStatusRoom_Page extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table_ManajStatusRoom;
	private JTextField txtCari_Room;

	/**
	 * Launch the application.
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					manajemenStatusRoom_Page frame = new manajemenStatusRoom_Page();
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
	public manajemenStatusRoom_Page() {
		setTitle("Manajemen Status Room MelodyBox");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 572, 452);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Edit Status");
		btnNewButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        
		        // 1. Ambil baris yang dipilih
		        int row = table_ManajStatusRoom.getSelectedRow();
		        
		        if (row == -1) {
		            JOptionPane.showMessageDialog(null, "Pilih room yang ingin diedit statusnya!");
		            return;
		        }
		        
		        // 2. Ambil ID Room dari tabel
		        String idRoom = table_ManajStatusRoom.getValueAt(row, 0).toString();
		        
		        // 3. Ambil Detail Lengkap dari Database 
		        // (Karena tabel hanya menampilkan sebagian data, kita butuh Fasilitas & Avail/Maint untuk Constructor Form)
		        try {
		            konfigurasi.KoneksiKeServer();
		            String sql = "SELECT * FROM room WHERE id_room = ?";
		            java.sql.PreparedStatement pst = konfigurasi.con.prepareStatement(sql);
		            pst.setString(1, idRoom);
		            ResultSet rs = pst.executeQuery();
		            
		            if (rs.next()) {
		                // Ambil data dari database
		                String nama = rs.getString("nama_room");
		                String tipe = rs.getString("tipe_room");
		                String fasil = rs.getString("fasilitas_room");
		                String kap = rs.getString("kapasitas_room");
		                String smoke = rs.getString("smoking_room");
		                String avail = rs.getString("avail_maint_room");
		                String status = rs.getString("status_room");
		                
		         
		                formData_Room form = new formData_Room(
		                    idRoom, nama, tipe, fasil, kap, smoke, avail, status
		                );
		                
		                
		                form.kunciFieldUntukKasir();
		                
		                
		                form.setLocationRelativeTo(null);
		                form.setVisible(true);
		                dispose();
		            }
		            
		            rs.close();
		            pst.close();
		            
		        } catch (Exception ex) {
		            JOptionPane.showMessageDialog(null, "Gagal mengambil data room: " + ex.getMessage());
		            ex.printStackTrace();
		        }
		    }
		});
		btnNewButton.setBounds(405, 11, 125, 23);
		contentPane.add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 52, 538, 352);
		contentPane.add(scrollPane);
		
		table_ManajStatusRoom = new JTable();
		table_ManajStatusRoom.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id Room ", "Nama Room ", "Kapasitas", "Tipe", "Smoking Area", "Status Room"
			}
		));
		table_ManajStatusRoom.getColumnModel().getColumn(1).setPreferredWidth(105);
		table_ManajStatusRoom.getColumnModel().getColumn(4).setPreferredWidth(87);
		table_ManajStatusRoom.getColumnModel().getColumn(5).setPreferredWidth(79);
		scrollPane.setViewportView(table_ManajStatusRoom);
		
		JLabel lblNewLabel = new JLabel("Cari : ");
		lblNewLabel.setBounds(10, 15, 48, 14);
		contentPane.add(lblNewLabel);
		
		txtCari_Room = new JTextField();
		txtCari_Room.setText("Berdasarkan Nama room/id room");
		txtCari_Room.setForeground(Color.GRAY); // Sekarang Color sudah dikenali

		txtCari_Room.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtCari_Room.getText().equals("Berdasarkan Nama room/id room")) {
                	txtCari_Room.setText("");
                	txtCari_Room.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtCari_Room.getText().isEmpty()) {
                	txtCari_Room.setForeground(Color.GRAY);
                	txtCari_Room.setText("Berdasarkan Nama room/id room");
                }
            }
        });
		txtCari_Room.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// 1. Ambil kata kunci
				String keyword = txtCari_Room.getText().trim();

				// 2. Jika kosong, load semua data
				if (keyword.isEmpty()) {
					loadDataRoom();
					return;
				}

				DefaultTableModel model = (DefaultTableModel) table_ManajStatusRoom.getModel();
				model.setRowCount(0); // Bersihkan tabel

				try {
					konfigurasi.KoneksiKeServer();
					if (konfigurasi.con == null) return;

					// 3. Query cari berdasarkan nama_room
					String sql = "SELECT id_room, nama_room, kapasitas_room, tipe_room, smoking_room, status_room " +
							 "FROM room WHERE nama_room LIKE ?OR id_room LIKE ?";
				
					PreparedStatement ps = konfigurasi.con.prepareStatement(sql);
					ps.setString(1, "%" + keyword + "%"); 
					ps.setString(2, "%" + keyword + "%");
					ResultSet rs = ps.executeQuery();

					while (rs.next()) {
					
						String stt = rs.getString("status_room");
						if (stt == null || stt.isEmpty()) stt = "Not Booked";

						Object[] row = {
							rs.getString("id_room"),
							rs.getString("nama_room"),
							rs.getString("kapasitas_room"),
							rs.getString("tipe_room"),
							rs.getString("smoking_room"),
							rs.getString("status_room"),
							stt
						};
						model.addRow(row);
					}

					rs.close();
					ps.close();
					konfigurasi.con.close();

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Gagal mencari room: " + ex.getMessage());
					ex.printStackTrace();
				}
			}
		});		txtCari_Room.setBounds(72, 12, 208, 20);
		contentPane.add(txtCari_Room);
		txtCari_Room.setColumns(10);
		loadDataRoom();

	}
	// --- METHOD 1: MENAMPILKAN DATA DARI DATABASE (Tabel Room) ---
		private void loadDataRoom() {
			DefaultTableModel model = (DefaultTableModel) table_ManajStatusRoom.getModel();
			model.setRowCount(0); // Reset tabel
			
			konfigurasi.KoneksiKeServer();
			if (konfigurasi.con == null) return;
			
			try {
				// Ambil semua data room
				String sql = "SELECT id_room, nama_room,kapasitas_room, tipe_room, smoking_room, status_room FROM room";
				Statement st = konfigurasi.con.createStatement();
				ResultSet rs = st.executeQuery(sql);
				
				while(rs.next()) {
					// Cek jika status masih null di database, anggap Available
					String stt = rs.getString("status_room");
					if (stt == null || stt.isEmpty()) stt = "Not Booked";
					
					Object[] row = {
						rs.getString("id_room"),
						rs.getString("nama_room"),
						rs.getString("kapasitas_room"),
						rs.getString("tipe_room"),
						rs.getString("smoking_room"),
						rs.getString("status_room"),
						stt
					};
					model.addRow(row);
				}
				
				rs.close();
				st.close();
				konfigurasi.con.close();
				
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Gagal load room: " + e.getMessage());
			}
		}
}
