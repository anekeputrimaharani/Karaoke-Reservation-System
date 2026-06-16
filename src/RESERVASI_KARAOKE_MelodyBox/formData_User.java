package RESERVASI_KARAOKE_MelodyBox;

import java.awt.BorderLayout;
import java.sql.PreparedStatement;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class formData_User extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txt_Nama_FormUser;
	private JTextField txt_Uname_FormUser;
	private JTextField txt_Pass_FormUser;
	private JTextField txt_NoTelp_FormUser;
	private JTextField txt_Id_FormUser;
	private JComboBox<String> comboBox_Role;
	
	private boolean isEditMode = false;
	/**
	 * Launch the application.
	
	public static void main(String[] args) {
		try {
			formData_User dialog = new formData_User();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 */
	
	public formData_User(
	        String idUser,
	        String namaUser,
	        String username,
	        String password,
	        String role,
	        String noTelp
	) {
		this();

		txt_Id_FormUser.setText(idUser);
		txt_Nama_FormUser.setText(namaUser);
		txt_Uname_FormUser.setText(username);
		txt_Pass_FormUser.setText(password);
		comboBox_Role.setSelectedItem(role);
		txt_NoTelp_FormUser.setText(noTelp);

		txt_Id_FormUser.setEditable(false); 
        isEditMode = false; // Kembalikan ke false jika perlu (opsional)
	    }

	

	/**
	 * Create the dialog.
	 */
	public formData_User() {
		setTitle("Form Data User MelodyBox");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 436, 230);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Nama : ");
			lblNewLabel.setBounds(29, 54, 48, 14);
			contentPanel.add(lblNewLabel);
		}
		{
			JLabel lblNama = new JLabel("Username : ");
			lblNama.setBounds(29, 79, 99, 14);
			contentPanel.add(lblNama);
		}
		{
			JLabel lblPassword = new JLabel("Password : ");
			lblPassword.setBounds(29, 111, 99, 14);
			contentPanel.add(lblPassword);
		}
		{
			JLabel lblRole = new JLabel("Role : ");
			lblRole.setBounds(29, 141, 99, 14);
			contentPanel.add(lblRole);
		}
		{
			JLabel lblNoTelp = new JLabel("No Telp : ");
			lblNoTelp.setBounds(29, 178, 99, 14);
			contentPanel.add(lblNoTelp);
		}
		{
			txt_Nama_FormUser = new JTextField();
			txt_Nama_FormUser.setBounds(135, 51, 270, 20);
			contentPanel.add(txt_Nama_FormUser);
			txt_Nama_FormUser.setColumns(10);
		}
		{
			txt_Uname_FormUser = new JTextField();
			txt_Uname_FormUser.setColumns(10);
			txt_Uname_FormUser.setBounds(135, 80, 270, 20);
			contentPanel.add(txt_Uname_FormUser);
		}
		
		txt_Pass_FormUser = new JTextField();
		txt_Pass_FormUser.setColumns(10);
		txt_Pass_FormUser.setBounds(135, 108, 270, 20);
		contentPanel.add(txt_Pass_FormUser);
		
		txt_NoTelp_FormUser = new JTextField();
		txt_NoTelp_FormUser.setColumns(10);
		txt_NoTelp_FormUser.setBounds(135, 175, 270, 20);
		contentPanel.add(txt_NoTelp_FormUser);
		{
			txt_Id_FormUser = new JTextField();
			txt_Id_FormUser.setColumns(10);
			txt_Id_FormUser.setBounds(135, 18, 270, 20);
			contentPanel.add(txt_Id_FormUser);
		}
		
		comboBox_Role = new JComboBox<>();
		comboBox_Role.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generateIdUser();
			}
		});
		comboBox_Role.setBounds(138, 142, 267, 22);
		contentPanel.add(comboBox_Role);
		
		comboBox_Role.addItem("kasir");
		comboBox_Role.addItem("manager");
		comboBox_Role.addItem("admin");
		
		comboBox_Role.setSelectedItem("kasir");
		
		{
			JLabel lblIdUser = new JLabel("Id User : ");
			lblIdUser.setBounds(29, 21, 99, 14);
			contentPanel.add(lblIdUser);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 230, 436, 33);
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
				            // Ambil data dari form
				            String idUser = txt_Id_FormUser.getText().trim();
				            String namaUser = txt_Nama_FormUser.getText().trim();
				            String username = txt_Uname_FormUser.getText().trim();
				            String password= txt_Pass_FormUser.getText().trim();
				            String role = comboBox_Role.getSelectedItem().toString();
				            String noTelp = txt_NoTelp_FormUser.getText().trim();
				            

				            // Validasi form
				            if (idUser.isEmpty() 
				            	|| namaUser.isEmpty()
				                || username.isEmpty()
				                || password.isEmpty()
				                || role.isEmpty()
				                || noTelp.isEmpty()) {

				                JOptionPane.showMessageDialog(
				                    rootPane,
				                    "Mohon lengkapi seluruh form",
				                    "Form belum lengkap",
				                    JOptionPane.WARNING_MESSAGE
				                );
				                return;
				            }

				            // Cek apakah ID User sudah ada
				            String sqlCheck = "SELECT id_user FROM user WHERE id_user=?";
				            PreparedStatement psCheck = konfigurasi.con.prepareStatement(sqlCheck);
				            psCheck.setString(1, idUser);
				            java.sql.ResultSet rs = psCheck.executeQuery();

				            if (rs.next()) {
				                // UPDATE jika ID sudah ada
				            	String sqlUpdate =
				                        "UPDATE user SET nama_user=?, username=?, password=?, role=?, noTelp_user=? "
				                      + "WHERE id_user=?";

				            	  PreparedStatement psUpdate = konfigurasi.con.prepareStatement(sqlUpdate);
				                  psUpdate.setString(1, namaUser);
				                  psUpdate.setString(2, username);
				                  psUpdate.setString(3, password);
				                  psUpdate.setString(4, role);
				                  psUpdate.setString(5, noTelp);
				                  psUpdate.setString(6, idUser);
				                  psUpdate.executeUpdate();
				                  psUpdate.close();

				            } else {
				            	 // INSERT USER
				                String sqlInsert =
				                    "INSERT INTO user (id_user, nama_user, username, password, role, noTelp_user) "
				                  + "VALUES (?, ?, ?, ?, ?, ?)";

				                PreparedStatement psInsert = konfigurasi.con.prepareStatement(sqlInsert);
				                psInsert.setString(1, idUser);
				                psInsert.setString(2, namaUser);
				                psInsert.setString(3, username);
				                psInsert.setString(4, password);
				                psInsert.setString(5, role);
				                psInsert.setString(6, noTelp);
				                psInsert.executeUpdate();
				                psInsert.close();
				            }

				            rs.close();
				            psCheck.close();

				            JOptionPane.showMessageDialog(null, "Data User berhasil disimpan!");
				            dispose();
				            new dataUser().setVisible(true);
				            
				        } catch (Exception ex) {
				        	 JOptionPane.showMessageDialog(null, "Gagal simpan: " + ex.getMessage());
				        	    ex.printStackTrace();
				        }
						
					}
				});
				okButton.setBounds(251, 5, 76, 23);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
						dataUser dataUser = new dataUser();
			            dataUser.setVisible(true);
					}
				});
				cancelButton.setBounds(337, 5, 89, 23);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		generateIdUser();
	}
	
	private void generateIdUser() {
	    try {
	        konfigurasi.KoneksiKeServer();

	        String role = comboBox_Role.getSelectedItem().toString();
	        String prefix = "";
	        String sql = "";
	        
	        
	        if (role.equalsIgnoreCase("kasir")) {
	            prefix = "K";
	            // Cari ID terakhir CUMA untuk kasir
	            sql = "SELECT id_user FROM user WHERE role = 'kasir' ORDER BY id_user DESC LIMIT 1"; 
	        } else if (role.equalsIgnoreCase("admin")) {
	            prefix = "A";
	            // Cari ID terakhir CUMA untuk admin
	            sql = "SELECT id_user FROM user WHERE role = 'admin' ORDER BY id_user DESC LIMIT 1";
	        } else if (role.equalsIgnoreCase("manager")) {
	            prefix = "M";
	            // Cari ID terakhir CUMA untuk manager
	            sql = "SELECT id_user FROM user WHERE role = 'manager' ORDER BY id_user DESC LIMIT 1";
	        }

	        PreparedStatement ps = konfigurasi.con.prepareStatement(sql);
	        java.sql.ResultSet rs = ps.executeQuery();
	        
	        if (rs.next()) {
	            // KONDISI: Data ditemukan (Sudah ada Kasir/Admin sebelumnya)
	            String lastId = rs.getString("id_user"); // Misal: K005
	            
	            // Ambil angkanya saja (mulai dari index 1 sampai habis)
	            String angkaSaja = lastId.substring(1); 
	            
	            // Ubah jadi integer dan tambah 1
	            int number = Integer.parseInt(angkaSaja) + 1; 
	            
	            // Format balik jadi String (K006)
	            String newId = String.format("%s%03d", prefix, number); 
	            txt_Id_FormUser.setText(newId);
	            
	        } else {
	            // KONDISI: Data Kosong
	            String newId = prefix + "001";
	            txt_Id_FormUser.setText(newId);
	        }
	        
	        txt_Id_FormUser.setEditable(false); // agar tidak bisa diubah manual

	        rs.close();
	        ps.close();

	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "Gagal generate ID Room: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
}