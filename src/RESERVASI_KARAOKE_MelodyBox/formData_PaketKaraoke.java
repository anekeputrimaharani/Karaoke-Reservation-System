package RESERVASI_KARAOKE_MelodyBox;

import java.awt.BorderLayout;



import java.awt.FlowLayout;
import java.sql.PreparedStatement;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class formData_PaketKaraoke extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txt_Id_PaketKaraoke;
	private JTextField txt_Nama_PaketKaraoke;
	private JTextField txt_Hrg_WeekdaysPagi;
	private JTextField txt_Hrg_WeekdaysSore;
	private JTextField txt_Hrg_WeekendPagi;
	private JTextField txt_Hrg_WeekendSore;
	private JComboBox<String> comboBox_Kps_PaketKaraoke;
	private JRadioButton rdbtnYes;
	private JRadioButton rdbtnNo;
	
	

	/**
	 * Launch the application.
	
	public static void main(String[] args) {
		try {
			formData_PaketKaraoke dialog = new formData_PaketKaraoke();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 */
	
	public formData_PaketKaraoke(
	        String idPaket,
	        String namaPaket,
	        String smokingPaket,
	        String kapasitasPaket,
	        String hargaWeekdayPagi,
	        String hargaWeekdaySore,
	        String hargaWeekendPagi,
	        String hargaWeekendSore
	) {
	    this(); 

	    txt_Id_PaketKaraoke.setText(idPaket);
	    txt_Nama_PaketKaraoke.setText(namaPaket);
	    comboBox_Kps_PaketKaraoke.setSelectedItem(kapasitasPaket);
	    txt_Hrg_WeekdaysPagi.setText(hargaWeekdayPagi);
	    txt_Hrg_WeekdaysSore.setText(hargaWeekdaySore);
	    txt_Hrg_WeekendPagi.setText(hargaWeekendPagi);
	    txt_Hrg_WeekendSore.setText(hargaWeekendSore);
	    

	    if (smokingPaket.equalsIgnoreCase("Yes")) {
	        rdbtnYes.setSelected(true);
	    } else {
	        rdbtnNo.setSelected(true);
	    }

	}
	/**
	 * Create the dialog.
	 */
	public formData_PaketKaraoke() {
		setTitle("Form Data Paket Kraoke MelodyBox");
		setBounds(100, 100, 526, 532);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 512, 462);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Id Paket : ");
		lblNewLabel.setBounds(41, 37, 70, 14);
		contentPanel.add(lblNewLabel);
		
		JLabel lblNamaPaket = new JLabel("Nama Paket : ");
		lblNamaPaket.setBounds(41, 82, 88, 14);
		contentPanel.add(lblNamaPaket);
		
		JLabel lblNamaPaket_1 = new JLabel("Smoking Paket : ");
		lblNamaPaket_1.setBounds(41, 125, 110, 14);
		contentPanel.add(lblNamaPaket_1);
		
		JLabel lblNamaPaket_1_1 = new JLabel("Kapasitas : ");
		lblNamaPaket_1_1.setBounds(41, 188, 88, 14);
		contentPanel.add(lblNamaPaket_1_1);
		
		txt_Id_PaketKaraoke = new JTextField();
		txt_Id_PaketKaraoke.setBounds(174, 34, 96, 20);
		contentPanel.add(txt_Id_PaketKaraoke);
		txt_Id_PaketKaraoke.setColumns(10);
		
		txt_Nama_PaketKaraoke = new JTextField();
		txt_Nama_PaketKaraoke.setColumns(10);
		txt_Nama_PaketKaraoke.setBounds(174, 79, 296, 20);
		contentPanel.add(txt_Nama_PaketKaraoke);
		
		rdbtnYes = new JRadioButton("Yes");
		rdbtnYes.setBounds(174, 121, 110, 22);
		contentPanel.add(rdbtnYes);

		rdbtnNo = new JRadioButton("No");
		rdbtnNo.setBounds(174, 146, 110, 22);
		contentPanel.add(rdbtnNo);
		
		ButtonGroup groupSmoking = new ButtonGroup();
		groupSmoking.add(rdbtnYes);
		groupSmoking.add(rdbtnNo);
		
		
		comboBox_Kps_PaketKaraoke = new JComboBox<>();
		comboBox_Kps_PaketKaraoke.setBounds(174, 184, 296, 22);
		contentPanel.add(comboBox_Kps_PaketKaraoke);

		comboBox_Kps_PaketKaraoke.addItem("4");
		comboBox_Kps_PaketKaraoke.addItem("8");
		comboBox_Kps_PaketKaraoke.addItem("12");
		
		JPanel panel = new JPanel();
		panel.setBounds(41, 232, 429, 219);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("HARGA ");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(0, 11, 82, 14);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Senin - Jumat : ");
		lblNewLabel_2.setBounds(10, 46, 109, 14);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("10.00 - 15.59");
		lblNewLabel_2_1.setBounds(129, 46, 88, 14);
		panel.add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_2_1_1 = new JLabel("16.00 - 23.59");
		lblNewLabel_2_1_1.setBounds(129, 71, 88, 14);
		panel.add(lblNewLabel_2_1_1);
		
		txt_Hrg_WeekdaysPagi = new JTextField();
		txt_Hrg_WeekdaysPagi.setBounds(240, 43, 141, 20);
		panel.add(txt_Hrg_WeekdaysPagi);
		txt_Hrg_WeekdaysPagi.setColumns(10);
		
		txt_Hrg_WeekdaysSore = new JTextField();
		txt_Hrg_WeekdaysSore.setColumns(10);
		txt_Hrg_WeekdaysSore.setBounds(240, 68, 141, 20);
		panel.add(txt_Hrg_WeekdaysSore);
		
		JLabel lblNewLabel_2_2 = new JLabel("Sabtu - Minggu : ");
		lblNewLabel_2_2.setBounds(10, 130, 109, 14);
		panel.add(lblNewLabel_2_2);
		
		JLabel lblNewLabel_2_1_2 = new JLabel("10.00 - 15.59");
		lblNewLabel_2_1_2.setBounds(129, 130, 88, 14);
		panel.add(lblNewLabel_2_1_2);
		
		JLabel lblNewLabel_2_1_1_1 = new JLabel("16.00 - 23.59");
		lblNewLabel_2_1_1_1.setBounds(129, 154, 88, 14);
		panel.add(lblNewLabel_2_1_1_1);
		
		txt_Hrg_WeekendPagi = new JTextField();
		txt_Hrg_WeekendPagi.setColumns(10);
		txt_Hrg_WeekendPagi.setBounds(240, 127, 141, 20);
		panel.add(txt_Hrg_WeekendPagi);
		
		txt_Hrg_WeekendSore = new JTextField();
		txt_Hrg_WeekendSore.setColumns(10);
		txt_Hrg_WeekendSore.setBounds(240, 151, 141, 20);
		panel.add(txt_Hrg_WeekendSore);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 462, 512, 33);
			getContentPane().add(buttonPane);
			buttonPane.setLayout(null);
			{JButton okButton = new JButton("OK");
			okButton.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {

			        konfigurasi.KoneksiKeServer();
			        if (konfigurasi.con == null) {
			            JOptionPane.showMessageDialog(rootPane, "Gagal koneksi ke database");
			            return;
			        }

			        try {
			            // Ambil data dari form
			            String idPaket = txt_Id_PaketKaraoke.getText().trim();
			            String namaPaket = txt_Nama_PaketKaraoke.getText().trim();
			            String smokingPaket = rdbtnYes.isSelected() ? "Yes" : "No";
			            String kapasitasPaket = comboBox_Kps_PaketKaraoke.getSelectedItem().toString();
			            String hargaWeekdayPagi = txt_Hrg_WeekdaysPagi.getText().trim();
			            String hargaWeekdaySore = txt_Hrg_WeekdaysSore.getText().trim();
			            String hargaWeekendPagi = txt_Hrg_WeekendPagi.getText().trim();
			            String hargaWeekendSore = txt_Hrg_WeekendSore.getText().trim();

			            // Validasi form
			            if (idPaket.isEmpty() 
			            	|| namaPaket.isEmpty()
			                || kapasitasPaket.isEmpty()
			                || hargaWeekdayPagi.isEmpty()
			                || hargaWeekdaySore.isEmpty()
			                || hargaWeekendPagi.isEmpty()
			                || hargaWeekendSore.isEmpty()
			                || (!rdbtnYes.isSelected() && !rdbtnNo.isSelected())) {

			                JOptionPane.showMessageDialog(
			                    rootPane,
			                    "Mohon lengkapi seluruh form",
			                    "Form belum lengkap",
			                    JOptionPane.WARNING_MESSAGE
			                );
			                return;
			            }

			            // Cek apakah ID Paket sudah ada
			            String sqlCheck = "SELECT id_paket FROM paket_karaoke WHERE id_paket=?";
			            PreparedStatement psCheck = konfigurasi.con.prepareStatement(sqlCheck);
			            psCheck.setString(1, idPaket);
			            java.sql.ResultSet rs = psCheck.executeQuery();

			            if (rs.next()) {
			                // UPDATE jika ID sudah ada
			                String sqlUpdate =
			                    "UPDATE paket_karaoke SET "
			                    + "nama_paket=?, smoking_paket=?, kapasitas_paket=?, "
			                    + "hrg_weekdays_pagi=?, hrg_weekdays_sore=?, "
			                    + "hrg_weekend_pagi=?, hrg_weekend_sore=? "
			                    + "WHERE id_paket=?";

			                PreparedStatement psUpdate = konfigurasi.con.prepareStatement(sqlUpdate);
			                psUpdate.setString(1, namaPaket);
			                psUpdate.setString(2, smokingPaket);
			                psUpdate.setString(3, kapasitasPaket);
			                psUpdate.setString(4, hargaWeekdayPagi);
			                psUpdate.setString(5, hargaWeekdaySore);
			                psUpdate.setString(6, hargaWeekendPagi);
			                psUpdate.setString(7, hargaWeekendSore);
			                psUpdate.setString(8, idPaket);
			                psUpdate.executeUpdate();
			                psUpdate.close();

			            } else {
			                // INSERT jika ID belum ada
			                String sqlInsert =
			                    "INSERT INTO paket_karaoke "
			                    + "(id_paket, nama_paket, smoking_paket, kapasitas_paket, "
			                    + "hrg_weekdays_pagi, hrg_weekdays_sore, "
			                    + "hrg_weekend_pagi, hrg_weekend_sore) "
			                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

			                PreparedStatement psInsert = konfigurasi.con.prepareStatement(sqlInsert);
			                psInsert.setString(1, idPaket);
			                psInsert.setString(2, namaPaket);
			                psInsert.setString(3, smokingPaket);
			                psInsert.setString(4, kapasitasPaket);
			                psInsert.setString(5, hargaWeekdayPagi);
			                psInsert.setString(6, hargaWeekdaySore);
			                psInsert.setString(7, hargaWeekendPagi);
			                psInsert.setString(8, hargaWeekendSore);
			                psInsert.executeUpdate();
			                psInsert.close();
			            }

			            rs.close();
			            psCheck.close();

			            JOptionPane.showMessageDialog(null, "Data Paket Karaoke berhasil disimpan!");
			            dispose();
			            new dataPaket_Karaoke().setVisible(true);

			        } catch (Exception ex) {
			            JOptionPane.showMessageDialog(null, "Gagal simpan: " + ex.getMessage());
			            ex.printStackTrace();
			        }
			    }
			});

				okButton.setBounds(306, 5, 96, 23);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
						dataPaket_Karaoke dataPaketKaraoke = new dataPaket_Karaoke ();
			            dataPaketKaraoke.setVisible(true);
						
					}
				});
				cancelButton.setBounds(412, 5, 89, 23);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				
				generateIdPaket();
				txt_Id_PaketKaraoke.setEditable(false);
			}
		}
	}
	
	private void generateIdPaket() {
	    try {
	       
	        konfigurasi.KoneksiKeServer();

	        // Query mengambil ID terakhir dari tabel paket
	        String sql = "SELECT id_paket FROM paket_karaoke ORDER BY id_paket DESC LIMIT 1";
	        PreparedStatement ps = konfigurasi.con.prepareStatement(sql);
	        java.sql.ResultSet rs = ps.executeQuery();

	        String newId;

	        if (rs.next()) {
	           
	            String lastId = rs.getString("id_paket"); 
	           
	            int number = Integer.parseInt(lastId.substring(1)); 
	          
	            number++;
	            newId = String.format("P%03d", number); 
	        } else {
	           
	            newId = "P001"; 
	        }

	        // Set ke TextField id paket Anda
	        txt_Id_PaketKaraoke.setText(newId);
	        txt_Id_PaketKaraoke.setEditable(false); 

	        rs.close();
	        ps.close();

	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "Gagal generate ID Paket: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
}
