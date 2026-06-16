package RESERVASI_KARAOKE_MelodyBox;

import java.awt.BorderLayout;


import javax.swing.ButtonGroup;
import java.awt.FlowLayout;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class formData_Member extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txt_NamaMember;
	private JTextField txt_NoTel_Member;
	private JFormattedTextField txt_TglReg_Member;
	private JFormattedTextField txt_TglLahir_Member;
	private JFormattedTextField txt_TglExp_Member;
	private JRadioButton rdbtnAktif;
	private JRadioButton rdbtnNonAktif;
	
	private void setPlaceholder(JTextField textField, String placeholder) {
	    textField.setForeground(Color.GRAY);
	    textField.setText(placeholder);

	    textField.addFocusListener(new FocusAdapter() {
	        @Override
	        public void focusGained(FocusEvent e) {
	            if (textField.getText().equals(placeholder)) {
	                textField.setText("");
	                textField.setForeground(Color.BLACK);
	            }
	        }

	        @Override
	        public void focusLost(FocusEvent e) {
	            if (textField.getText().isEmpty()) {
	                textField.setForeground(Color.GRAY);
	                textField.setText(placeholder);
	            }
	        }
	    });
	}


	/**
	 * Launch the application.
	 
	public static void main(String[] args) {
		try {
			formData_Member dialog = new formData_Member();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
	
	
	public formData_Member(
	        String namaMember,
	        String tglRegistrasi,
	        String noTelp,
	        String tglLahir,
	        String statusMember,
	        String tglExpired
	        
	) {
	    this(); // panggil constructor default (inisialisasi komponen)

	    txt_NamaMember.setText(namaMember);
	    txt_TglReg_Member.setText(tglRegistrasi);
	    txt_NoTel_Member.setText(noTelp);
	    txt_TglLahir_Member.setText(tglLahir);
	    txt_TglExp_Member.setText(tglExpired);

	    if (statusMember.equalsIgnoreCase("Aktif")) {
	        rdbtnAktif.setSelected(true);
	    } else {
	        rdbtnNonAktif.setSelected(true);
	    }
	}



	/**
	 * Create the dialog.
	 */
	public formData_Member() {
		setTitle("Form Data Member MelodyBox");
		setBounds(100, 100, 531, 369);
		getContentPane().setLayout(null);
	
		
		contentPanel.setBounds(0, 0, 517, 300);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Nama : ");
			lblNewLabel.setBounds(24, 29, 48, 14);
			contentPanel.add(lblNewLabel);
			
		}
		{
			JLabel lblTanggalRegistrasi = new JLabel("Tanggal Registrasi : ");
			lblTanggalRegistrasi.setBounds(24, 64, 148, 14);
			contentPanel.add(lblTanggalRegistrasi);
		}
		{
			JLabel lblNoTelp = new JLabel("No Telp : ");
			lblNoTelp.setBounds(24, 98, 113, 14);
			contentPanel.add(lblNoTelp);
			
		}
		{
			JLabel lblTanggalLahir = new JLabel("Tanggal Lahir : ");
			lblTanggalLahir.setBounds(24, 134, 113, 14);
			contentPanel.add(lblTanggalLahir);
			
		}
		{
			JLabel lblStatusMember = new JLabel("Status Member : ");
			lblStatusMember.setBounds(24, 175, 113, 14);
			contentPanel.add(lblStatusMember);
		}
		{
			JLabel lblTanggalExpired = new JLabel("Tanggal Expired : ");
			lblTanggalExpired.setBounds(24, 240, 113, 14);
			contentPanel.add(lblTanggalExpired);
			
		}
		{
			txt_NamaMember = new JTextField();
			txt_NamaMember.setBounds(182, 26, 312, 20);
			contentPanel.add(txt_NamaMember);
			txt_NamaMember.setColumns(10);
		}
		
		txt_TglReg_Member = new JFormattedTextField();
		txt_TglReg_Member.setBounds(182, 61, 148, 20);
		contentPanel.add(txt_TglReg_Member);
		
		txt_NoTel_Member = new JTextField();
		txt_NoTel_Member.setColumns(10);
		txt_NoTel_Member.setBounds(182, 95, 148, 20);
		contentPanel.add(txt_NoTel_Member);
		
		txt_TglLahir_Member = new JFormattedTextField();
		txt_TglLahir_Member.setBounds(182, 131, 148, 20);
		contentPanel.add(txt_TglLahir_Member);
		
		rdbtnAktif = new JRadioButton("Aktif");
		rdbtnAktif.setBounds(182, 171, 110, 22);
		contentPanel.add(rdbtnAktif);

		rdbtnNonAktif = new JRadioButton("Non Aktif");
		rdbtnNonAktif.setBounds(182, 196, 110, 22);
		contentPanel.add(rdbtnNonAktif);

		ButtonGroup bgStatusMember = new ButtonGroup();
		bgStatusMember.add(rdbtnAktif);
		bgStatusMember.add(rdbtnNonAktif);
		
		txt_TglExp_Member = new JFormattedTextField();
		txt_TglExp_Member.setBounds(182, 237, 148, 20);
		contentPanel.add(txt_TglExp_Member);
		
		setPlaceholder(txt_NamaMember, "Nama panggilan member");
		setPlaceholder(txt_NoTel_Member, "08XXXXXXXXXX");
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 299, 517, 33);
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
				            String namaMember = txt_NamaMember.getText();
				            String tglRegistrasi = txt_TglReg_Member.getText();
				            String noTelp = txt_NoTel_Member.getText();
				            String tglLahir = txt_TglLahir_Member.getText();
				            String tglExpired = txt_TglExp_Member.getText();
				            
				            
				            if (namaMember.trim().isEmpty()
				            	    || namaMember.equals("Nama panggilan member")
				            	    || noTelp.trim().isEmpty()
				            	    || noTelp.equals("08XXXXXXXXXX")
				            	    || tglRegistrasi.trim().isEmpty()
				            	    || tglLahir.trim().isEmpty()
				            	    || tglExpired.trim().isEmpty()
				            	    || (!rdbtnAktif.isSelected() && !rdbtnNonAktif.isSelected())) {

				            	    JOptionPane.showMessageDialog(
				            	        rootPane,
				            	        "Mohon lengkapi seluruh form",
				            	        "Form belum lengkap",
				            	        JOptionPane.WARNING_MESSAGE
				            	    );
				            	    return;
				            	}


				            // Ambil status dari RadioButton
				            String statusMember = "";
				            if (rdbtnAktif.isSelected()) {
				                statusMember = "Aktif";
				            } else if (rdbtnNonAktif.isSelected()) {
				                statusMember = "Nonaktif";
				            } else {
				                JOptionPane.showMessageDialog(rootPane, "Pilih status member");
				                return;
				            }

				            // Cek apakah member sudah ada
				            String sqlCheck = "SELECT * FROM member WHERE noTelp_member = ?";
				            PreparedStatement psCheck = konfigurasi.con.prepareStatement(sqlCheck);
				            psCheck.setString(1, noTelp);
				            java.sql.ResultSet rs = psCheck.executeQuery();

				            if (rs.next()) {
				                // UPDATE
				                String sqlUpdate = "UPDATE member SET nama_member=?, tgl_regis_member=?, tgl_lahir_member=?, status_member=?, tgl_exp_member=? WHERE noTelp_member=?";
				                PreparedStatement psUpdate = konfigurasi.con.prepareStatement(sqlUpdate);
				                psUpdate.setString(1, namaMember);
				                psUpdate.setString(2, tglRegistrasi);
				                psUpdate.setString(3, tglLahir);
				                psUpdate.setString(4, statusMember);
				                psUpdate.setString(5, tglExpired);
				                psUpdate.setString(6, noTelp);
				                psUpdate.executeUpdate();
				                psUpdate.close();
				            } else {
				                // INSERT
				                String sqlInsert = "INSERT INTO member (nama_member, tgl_regis_member, tgl_lahir_member, status_member, tgl_exp_member, noTelp_member) VALUES (?, ?, ?, ?, ?, ?)";
				                PreparedStatement psInsert = konfigurasi.con.prepareStatement(sqlInsert);
				                psInsert.setString(1, namaMember);
				                psInsert.setString(2, tglRegistrasi);
				                psInsert.setString(3, tglLahir);
				                psInsert.setString(4, statusMember);
				                psInsert.setString(5, tglExpired);
				                psInsert.setString(6, noTelp);
				                psInsert.executeUpdate();
				                psInsert.close();
				            }

				            rs.close();
				            psCheck.close();
				            
				            JOptionPane.showMessageDialog(null, "Data Member berhasil disimpan!");
				            dispose();
				            new dataMember().setVisible(true);

				        } catch (SQLException ex) {
				            System.out.println("Terjadi error : " + ex);
				        }
				    }
				});

				okButton.setBounds(314, 5, 76, 23);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
						dataMember dataMember = new dataMember();
			            dataMember.setVisible(true);
					}
				});
				cancelButton.setBounds(408, 5, 76, 23);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		setTanggalMemberOtomatis();
	}
	
	private void setTanggalMemberOtomatis() {
		try {
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			// Ambil Hari Ini
			Calendar cal = Calendar.getInstance();
			String hariIni = sdf.format(cal.getTime());
			
			txt_TglReg_Member.setText(hariIni);
			
			// Tambah 1 Tahun dari hari ini
			cal.add(Calendar.YEAR, 1);
			String tahunDepan = sdf.format(cal.getTime());
			
		
			txt_TglExp_Member.setText(tahunDepan);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
