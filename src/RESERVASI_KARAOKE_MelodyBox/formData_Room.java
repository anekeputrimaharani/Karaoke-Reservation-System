package RESERVASI_KARAOKE_MelodyBox;

import java.awt.BorderLayout;


import java.awt.FlowLayout;
import java.sql.PreparedStatement;
import java.sql.SQLException;


import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class formData_Room extends JDialog {

	private static final long serialVersionUID = 1L;
	private static JComboBox ComboBox_Tipe_FormRoom;
	private JComboBox ComboBox_Kps_FormRoom;
	private static JComboBox ComboBox_Status_FormRoom;
	private final JPanel contentPanel = new JPanel();
	private JTextField txt_Id_FormRoom;
	private JTextField txt_Nama_FormRoom;
	private JTextArea txtAreaFasil;
	private JRadioButton rdbtnAvailable;
	private JRadioButton rdbtnMaintenance;
	
	private JRadioButton rdbtnYes;
	private JRadioButton rdbtnNo;
	
	private ButtonGroup groupAvailability; // Agar hanya satu yang bisa dipilih
	private ButtonGroup groupSmoking;      // Untuk Yes/No

	private boolean isModeStatus = false; 

	// --- METHOD BARU: Kunci Field Selain Status ---
	public void kunciFieldUntukKasir() {
	    this.isModeStatus = true; // Tanda bahwa ini mode edit status
	    
	    // Matikan akses edit untuk data master
	    txt_Id_FormRoom.setEditable(false);
	    txt_Nama_FormRoom.setEditable(false);
	    ComboBox_Tipe_FormRoom.setEnabled(false);
	    txtAreaFasil.setEditable(false);
	    ComboBox_Kps_FormRoom.setEnabled(false);
	    rdbtnYes.setEnabled(false);
	    rdbtnNo.setEnabled(false);
	    ComboBox_Status_FormRoom.setEnabled(true);
	    rdbtnAvailable.setEnabled(false);
	    rdbtnMaintenance.setEnabled(false);
	    
	    setTitle("Update Status Room");
	}


	/**
	 * Launch the application.
	
	public static void main(String[] args) {
		try {
			formData_Room dialog = new formData_Room();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 */
	
	
	public formData_Room(
			String idRoom,
		    String namaRoom,
		    String tipeRoom,
		    String fasilitasRoom,
		    String kapasitasRoom,
		    String smokingRoom,
		    String available_Maintenance,
		    String statusRoom
		) {
		    this(); 

		    txt_Id_FormRoom.setText(idRoom);
		    txt_Nama_FormRoom.setText(namaRoom);
		    ComboBox_Tipe_FormRoom.setSelectedItem(tipeRoom);
		    txtAreaFasil.setText(fasilitasRoom);

		  
		    ComboBox_Kps_FormRoom.setSelectedItem(kapasitasRoom);

		    if (smokingRoom.equalsIgnoreCase("Yes")) {
		        rdbtnYes.setSelected(true);
		    } else {
		        rdbtnNo.setSelected(true);
		    }

		    if (available_Maintenance.equalsIgnoreCase("Available")) {
		        rdbtnAvailable.setSelected(true);
		    } else {
		        rdbtnMaintenance.setSelected(true);
		    }

		    ComboBox_Status_FormRoom.setSelectedItem(statusRoom);
		}


	/**
	 * Create the dialog.
	 */
	public formData_Room() {
		setTitle("Form Data Room MelodyBox");
		setBounds(100, 100, 541, 493);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 527, 413);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Id Room  : ");
			lblNewLabel.setBounds(21, 23, 77, 14);
			contentPanel.add(lblNewLabel);
		}
		{
			JLabel lblNamaRoom = new JLabel("Nama Room :");
			lblNamaRoom.setBounds(21, 66, 96, 14);
			contentPanel.add(lblNamaRoom);
		}
		{
			JLabel lblTipe = new JLabel("Tipe : ");
			lblTipe.setBounds(21, 108, 77, 14);
			contentPanel.add(lblTipe);
		}
		{
			JLabel lblFasilitas = new JLabel("Fasilitas : ");
			lblFasilitas.setBounds(21, 159, 77, 14);
			contentPanel.add(lblFasilitas);
		}
		{
			txt_Id_FormRoom = new JTextField();
			txt_Id_FormRoom.setBounds(181, 20, 110, 20);
			contentPanel.add(txt_Id_FormRoom);
			txt_Id_FormRoom.setColumns(10);
		}
		{
			txt_Nama_FormRoom = new JTextField();
			txt_Nama_FormRoom.setColumns(10);
			txt_Nama_FormRoom.setBounds(181, 63, 323, 20);
			contentPanel.add(txt_Nama_FormRoom);
		}
		
		ComboBox_Tipe_FormRoom = new JComboBox();
		ComboBox_Tipe_FormRoom.setBounds(181, 104, 323, 22);
		contentPanel.add(ComboBox_Tipe_FormRoom);

		// Tambahkan item tipe
		ComboBox_Tipe_FormRoom.addItem("Small");
		ComboBox_Tipe_FormRoom.addItem("Medium");
		ComboBox_Tipe_FormRoom.addItem("Large");
		{
			JLabel lblKapasitas = new JLabel("Kapasitas : ");
			lblKapasitas.setBounds(21, 209, 77, 14);
			contentPanel.add(lblKapasitas);
		}
		
		ComboBox_Kps_FormRoom = new JComboBox();
		ComboBox_Kps_FormRoom.setBounds(181, 205, 323, 22);
		contentPanel.add(ComboBox_Kps_FormRoom);
		
		// Tambahkan item
		ComboBox_Kps_FormRoom.addItem("4");
		ComboBox_Kps_FormRoom.addItem("8");
		ComboBox_Kps_FormRoom.addItem("12");
		
		ComboBox_Tipe_FormRoom.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        if (ComboBox_Tipe_FormRoom.getSelectedItem() != null) {
		            String tipeTerpilih = ComboBox_Tipe_FormRoom.getSelectedItem().toString();
		            
		            if (tipeTerpilih.equals("Small")) {
		                ComboBox_Kps_FormRoom.setSelectedItem("4");
		            } else if (tipeTerpilih.equals("Medium")) {
		                ComboBox_Kps_FormRoom.setSelectedItem("8");
		            } else if (tipeTerpilih.equals("Large")) {
		                ComboBox_Kps_FormRoom.setSelectedItem("12");
		            }
		        }
		    }
		});
		{
			JLabel lblAvailableMaintenance = new JLabel("Smoking Area : ");
			lblAvailableMaintenance.setBounds(21, 253, 96, 14);
			contentPanel.add(lblAvailableMaintenance);
		}
		{
			JLabel lblAvailableMaintenance_1 = new JLabel("Available / Maintenance : ");
			lblAvailableMaintenance_1.setBounds(21, 323, 154, 14);
			contentPanel.add(lblAvailableMaintenance_1);
		}
		{
			rdbtnAvailable = new JRadioButton("Available");
			rdbtnAvailable.setBounds(181, 319, 110, 22);
			contentPanel.add(rdbtnAvailable);
		}
		{
			rdbtnMaintenance = new JRadioButton("Maintenance");
			rdbtnMaintenance.setBounds(181, 347, 110, 22);
			contentPanel.add(rdbtnMaintenance);
		}
		
		groupAvailability = new ButtonGroup();
		groupAvailability.add(rdbtnAvailable);
		groupAvailability.add(rdbtnMaintenance);

		{
			rdbtnYes = new JRadioButton("Yes");
			rdbtnYes.setBounds(181, 249, 110, 22);
			contentPanel.add(rdbtnYes);
		}
		{
			rdbtnNo = new JRadioButton("No");
			rdbtnNo.setBounds(181, 278, 110, 22);
			contentPanel.add(rdbtnNo);
		}
		
		groupAvailability = new ButtonGroup();
		groupAvailability.add(rdbtnYes);
		groupAvailability.add(rdbtnNo);
		{
			JLabel lblAvailableMaintenance_1 = new JLabel("Status Room : ");
			lblAvailableMaintenance_1.setBounds(21, 388, 154, 14);
			contentPanel.add(lblAvailableMaintenance_1);
		}
		
		ComboBox_Status_FormRoom = new JComboBox();
		ComboBox_Status_FormRoom.setBounds(181, 384, 165, 22);
		contentPanel.add(ComboBox_Status_FormRoom);

		// Tambahkan item status
		ComboBox_Status_FormRoom.addItem("Booked");
		ComboBox_Status_FormRoom.addItem("Not Booked");
		ComboBox_Status_FormRoom.setSelectedItem("Not Booked");
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 412, 527, 44);
			getContentPane().add(buttonPane);
			buttonPane.setLayout(null);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent e) {
				    	
				        konfigurasi.KoneksiKeServer();
				        if (konfigurasi.con == null) {
				            JOptionPane.showMessageDialog(rootPane, "Gagal koneksi ke database");
				            return;
				        }

				        try {
				            String idRoom = txt_Id_FormRoom.getText().trim();
				            String namaRoom = txt_Nama_FormRoom.getText().trim();
				            String tipeRoom = ComboBox_Tipe_FormRoom.getSelectedItem().toString();
				            String fasilitas =  txtAreaFasil.getText().trim();
				            String kapasitas = ComboBox_Kps_FormRoom.getSelectedItem().toString();
				            String statusRoom = ComboBox_Status_FormRoom.getSelectedItem().toString();
				            String smokingArea = rdbtnYes.isSelected() ? "Yes" : "No";
				            String availability = rdbtnAvailable.isSelected() ? "Available" : "Maintenance";

				            // Validasi form
				            if (idRoom.isEmpty() 
				            	|| tipeRoom.isEmpty() 
				            	|| fasilitas.isEmpty() 
				            	|| kapasitas.isEmpty() 
				            	|| statusRoom.isEmpty()
				                || (!rdbtnYes.isSelected() && !rdbtnNo.isSelected())
				                || (!rdbtnAvailable.isSelected() && !rdbtnMaintenance.isSelected())) {
				            	
				                JOptionPane.showMessageDialog(rootPane, "Mohon lengkapi seluruh form", "Form belum lengkap", JOptionPane.WARNING_MESSAGE);
				                return;
				            }

				            // Cek apakah ID sudah ada
				            String sqlCheck = "SELECT * FROM room WHERE id_room=?";
				            PreparedStatement psCheck = konfigurasi.con.prepareStatement(sqlCheck);
				            psCheck.setString(1, idRoom);
				            java.sql.ResultSet rsCheck = psCheck.executeQuery();

				            if (rsCheck.next()) {
				                // UPDATE jika ID sudah ada
				                String sqlUpdate = "UPDATE room SET nama_room=?, tipe_room=?, fasilitas_room=?, kapasitas_room=?, smoking_room=?, avail_maint_room=?, status_room=? WHERE id_room=?";
				                PreparedStatement psUpdate = konfigurasi.con.prepareStatement(sqlUpdate);
				                psUpdate.setString(1, namaRoom);
				                psUpdate.setString(2, tipeRoom);
				                psUpdate.setString(3, fasilitas);
				                psUpdate.setString(4, kapasitas);
				                psUpdate.setString(5, smokingArea);
				                psUpdate.setString(6, availability);
				                psUpdate.setString(7, statusRoom);
				                psUpdate.setString(8, idRoom);
				                psUpdate.executeUpdate();
				                psUpdate.close();
				            } else {
				                // INSERT jika ID belum ada
				                String sqlInsert = "INSERT INTO room (id_room, nama_room, tipe_room, fasilitas_room, kapasitas_room, smoking_room, avail_maint_room, status_room) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
				                PreparedStatement psInsert = konfigurasi.con.prepareStatement(sqlInsert);
				                psInsert.setString(1, idRoom);
				                psInsert.setString(2, namaRoom);
				                psInsert.setString(3, tipeRoom);
				                psInsert.setString(4, fasilitas);
				                psInsert.setString(5, kapasitas);
				                psInsert.setString(6, smokingArea);
				                psInsert.setString(7, availability);
				                psInsert.setString(8, statusRoom);
				                psInsert.executeUpdate();
				                psInsert.close();
				            }

				            psCheck.close();
				            JOptionPane.showMessageDialog(null, "Data berhasil disimpan!");
				            dispose();
				            dataRoom roomPage = new dataRoom();
				            roomPage.setVisible(true);
				            
				         // LOGIKA REDIRECT
				            if (isModeStatus) {
				                new manajemenStatusRoom_Page().setVisible(true); // Kembali ke Manajemen Status
				            } else {
				                new dataRoom().setVisible(true); // Kembali ke Data Master
				            }

				        } catch (Exception ex) {
				            JOptionPane.showMessageDialog(null, "Gagal simpan: " + ex.getMessage());
				            ex.printStackTrace();
				        }
				    }
				});

				okButton.setBounds(353, 11, 65, 23);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent e) {
				        
				        dispose(); // 1. Tutup form saat ini

				        // 2. Cek mau balik ke mana
				        if (isModeStatus) {
				            // Jika mode edit status (Kasir), balik ke Manajemen Status
				            new manajemenStatusRoom_Page().setVisible(true); 
				        } else {
				            // Jika mode normal (Admin), balik ke Data Room
				            new dataRoom().setVisible(true); 
				        }
				    }
				});
				cancelButton.setBounds(428, 11, 78, 23);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				
				
				generateIdRoom();
				txt_Id_FormRoom.setEditable(false);
			}
		}
		{
			txtAreaFasil = new JTextArea();
			txtAreaFasil.setBounds(181, 145, 323, 43);
			contentPanel.add(txtAreaFasil);
		}
		
	}
	
	private void generateIdRoom() {
	    try {
	        konfigurasi.KoneksiKeServer();

	        String sql = "SELECT id_room FROM room ORDER BY id_room DESC LIMIT 1";
	        PreparedStatement ps = konfigurasi.con.prepareStatement(sql);
	        java.sql.ResultSet rs = ps.executeQuery();

	        String newId;

	        if (rs.next()) {
	            String lastId = rs.getString("id_room"); // contoh: R005
	            int number = Integer.parseInt(lastId.substring(1)); // ambil angka
	            number++;
	            newId = String.format("R%03d", number); // R006
	        } else {
	            newId = "R001"; // jika data masih kosong
	        }

	        txt_Id_FormRoom.setText(newId);
	        txt_Id_FormRoom.setEditable(false); // agar tidak bisa diubah manual

	        rs.close();
	        ps.close();

	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "Gagal generate ID Room: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

	
}
