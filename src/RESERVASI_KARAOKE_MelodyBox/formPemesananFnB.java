package RESERVASI_KARAOKE_MelodyBox;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class formPemesananFnB extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txt_id_MenuFnB;
	private JTextField txt_HrgTot_MenuFnB;
	private JTextField txt_kuan_MenuFnB;
	private JTextField txt_HrgUnit_MenuFnB;
	private String idPesanan;
	private formRincian_ReservasiKaraoke_PemesananFnB parent;
	private JComboBox comboBox_NamaMenuFnB;
	private JTextArea textArea;
	private boolean isEdit = false;

	/**
	 * Launch the application.
	 
	public static void main(String[] args) {
		try {
			formPemesananFnB dialog = new formPemesananFnB();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
	
	
	public formPemesananFnB(
		    String idPesanan,
		    String namaMenu,
		    String idMenu,
		    String hrgUnit,
		    String kuantitas,
		    String hrgTotal,
		    String ket,
		    formRincian_ReservasiKaraoke_PemesananFnB parent
		) {
		    this();

		    this.parent = parent;
		    this.idPesanan = idPesanan;

		    comboBox_NamaMenuFnB.setSelectedItem(namaMenu);
		    txt_id_MenuFnB.setText(idMenu);
		    txt_HrgUnit_MenuFnB.setText(hrgUnit);
		    txt_kuan_MenuFnB.setText(kuantitas);
		    txt_HrgTot_MenuFnB.setText(hrgTotal);
		    textArea.setText(ket);

		    isEdit = true; // MODE EDIT
		}

	
	public formPemesananFnB(
		    String idPesanan,
		    formRincian_ReservasiKaraoke_PemesananFnB parent
		) {
		    this(); // 🔥 WAJIB — init UI dulu

		    this.parent = parent;
		    this.idPesanan = idPesanan;

		    isEdit = false; // MODE TAMBAH
		}

	/**
	 * Create the dialog.
	 */
	public formPemesananFnB() {
		setTitle("Form Pemesanan F&B MelodyBox");
		setBounds(100, 100, 529, 414);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 515, 344);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Nama Menu : ");
			lblNewLabel.setBounds(10, 35, 159, 14);
			contentPanel.add(lblNewLabel);
		}
		{
			JLabel lblIdMakanan = new JLabel("Id Menu : ");
			lblIdMakanan.setBounds(10, 64, 159, 14);
			contentPanel.add(lblIdMakanan);
		}
		{
			txt_id_MenuFnB = new JTextField();
			txt_id_MenuFnB.setBounds(187, 61, 182, 20);
			contentPanel.add(txt_id_MenuFnB);
			txt_id_MenuFnB.setColumns(10);
		}
		{
			JLabel lblKuantitas = new JLabel("Kuantitas : ");
			lblKuantitas.setBounds(10, 148, 159, 14);
			contentPanel.add(lblKuantitas);
		}
		
		JLabel lblHargaUnit = new JLabel("Harga / unit : ");
		lblHargaUnit.setBounds(10, 106, 159, 14);
		contentPanel.add(lblHargaUnit);
		
		JLabel lblHargaTotal = new JLabel("Harga Total : ");
		lblHargaTotal.setBounds(10, 189, 159, 14);
		contentPanel.add(lblHargaTotal);
		
		comboBox_NamaMenuFnB = new JComboBox();
		comboBox_NamaMenuFnB.setBounds(187, 31, 309, 22);
		contentPanel.add(comboBox_NamaMenuFnB);
		
		comboBox_NamaMenuFnB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				  Object selected = comboBox_NamaMenuFnB.getSelectedItem();

				    if (selected == null || selected.toString().equals("-- Pilih Menu --")) {
				        txt_id_MenuFnB.setText("");
				        txt_HrgUnit_MenuFnB.setText("");
				        txt_HrgTot_MenuFnB.setText("");
				        return;
				    }

				    try {
				        konfigurasi.KoneksiKeServer();

				        String sql = "SELECT id_menu, hrg_per_unit FROM menu_fnb WHERE nama_menu = ?";
				        PreparedStatement pst = konfigurasi.con.prepareStatement(sql);
				        pst.setString(1, selected.toString());

				        var rs = pst.executeQuery();

				        if (rs.next()) {
				            txt_id_MenuFnB.setText(rs.getString("id_menu"));
				            txt_HrgUnit_MenuFnB.setText(
				                    String.format("%,.0f", rs.getDouble("hrg_per_unit"))
				            );
				        }

				        rs.close();
				        pst.close();
				        konfigurasi.con.close();

				        // langsung hitung total kalau kuantitas sudah diisi
				        hitungTotalFnB();

				    } catch (SQLException ex) {
				        JOptionPane.showMessageDialog(
				                null,
				                "Gagal ambil data menu: " + ex.getMessage()
				        );
				        ex.printStackTrace();
				    }
			}
			
		});
		comboBox_NamaMenuFnB.setBounds(187, 31, 309, 22);
		contentPanel.add(comboBox_NamaMenuFnB);
		
		txt_HrgTot_MenuFnB = new JTextField();
		txt_HrgTot_MenuFnB.setColumns(10);
		txt_HrgTot_MenuFnB.setBounds(187, 186, 182, 20);
		contentPanel.add(txt_HrgTot_MenuFnB);
		
		txt_kuan_MenuFnB = new JTextField();
		txt_kuan_MenuFnB.addKeyListener(new java.awt.event.KeyAdapter() {
		    @Override
		    public void keyReleased(java.awt.event.KeyEvent e) {
		        hitungTotalFnB();
		    }
		});
		txt_kuan_MenuFnB.setBounds(187, 145, 48, 20);
		contentPanel.add(txt_kuan_MenuFnB);
		txt_kuan_MenuFnB.setColumns(10);
		
		txt_HrgUnit_MenuFnB = new JTextField();
		txt_HrgUnit_MenuFnB.setColumns(10);
		txt_HrgUnit_MenuFnB.setBounds(187, 106, 182, 20);
		contentPanel.add(txt_HrgUnit_MenuFnB);
		
		JLabel lblKeterangan = new JLabel("Keterangan :");
		lblKeterangan.setBounds(10, 226, 159, 14);
		contentPanel.add(lblKeterangan);
		
		textArea = new JTextArea();
		textArea.setBounds(187, 221, 309, 112);
		contentPanel.add(textArea);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 344, 515, 33);
			getContentPane().add(buttonPane);
			buttonPane.setLayout(null);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						// ========================================================
						// 1. VALIDASI INPUT (MENCEGAH ERROR NUMBER FORMAT)
						// ========================================================
						
						// Ambil data teks dulu (jangan langsung convert ke int/double)
						String namaPesanan = "";
						if (comboBox_NamaMenuFnB.getSelectedItem() != null) {
							namaPesanan = comboBox_NamaMenuFnB.getSelectedItem().toString();
						}
						
						String idMenu = txt_id_MenuFnB.getText().trim();
						String qtyStr = txt_kuan_MenuFnB.getText().trim();
						String priceStr = txt_HrgUnit_MenuFnB.getText().trim();
						
						// Cek Kelengkapan (Kecuali Keterangan)
						if (namaPesanan.equals("-- Pilih Menu --") || namaPesanan.isEmpty()) {
							JOptionPane.showMessageDialog(rootPane, "Silakan pilih menu terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
							return;
						}
						
						if (idMenu.isEmpty()) {
							JOptionPane.showMessageDialog(rootPane, "ID Menu kosong. Silakan pilih menu ulang!", "Peringatan", JOptionPane.WARNING_MESSAGE);
							return;
						}
						
						if (qtyStr.isEmpty()) {
							JOptionPane.showMessageDialog(rootPane, "Kuantitas (Jumlah) tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
							return;
						}
						
						// Cek apakah Kuantitas itu Angka?
						if (!qtyStr.matches("\\d+")) { // Regex: hanya angka
							JOptionPane.showMessageDialog(rootPane, "Kuantitas harus berupa angka!", "Peringatan", JOptionPane.WARNING_MESSAGE);
							return;
						}
						
						// ========================================================
						// 2. PROSES SIMPAN KE DATABASE (SETELAH VALIDASI AMAN)
						// ========================================================
						
						try {
						    konfigurasi.KoneksiKeServer();
						    
						    // Aman untuk convert sekarang
				            int kuantitas = Integer.parseInt(qtyStr);

				            double hargaUnit = Double.parseDouble(priceStr.replace(",", "").replace(".", ""));
				            double hargaTot = Double.parseDouble(txt_HrgTot_MenuFnB.getText().replace(",", "").replace(".", ""));

				            String ket = textArea.getText();
				            
						    if (isEdit) {
						        // ================= UPDATE =================
						        String sqlUpdate =
						            "UPDATE pemesanan_fnb SET " +
						            "nama_pesanan=?, id_menu=?, hrg_unitMenu=?, kuantitas=?, hrg_totMenu=?, ket=? " +
						            "WHERE id_pesanan=? AND id_menu=?";

						        PreparedStatement pst = konfigurasi.con.prepareStatement(sqlUpdate);
						        pst.setString(1, namaPesanan);
						        pst.setString(2, idMenu);
						        pst.setDouble(3, hargaUnit);
						        pst.setInt(4, kuantitas);
						        pst.setDouble(5, hargaTot);
						        pst.setString(6, ket);
						        pst.setString(7, idPesanan);
						        pst.setString(8, idMenu); // patokan row yang diedit

						        pst.executeUpdate();
						        pst.close();

						        JOptionPane.showMessageDialog(
						            formPemesananFnB.this,
						            "Pesanan F&B berhasil diperbarui",
						            "Sukses",
						            JOptionPane.INFORMATION_MESSAGE
						        );

						    } else {
						        // ================= INSERT =================
						        String sqlInsert =
						            "INSERT INTO pemesanan_fnb " +
						            "(nama_pesanan, id_menu, hrg_unitMenu, kuantitas, hrg_totMenu, ket, id_pesanan) " +
						            "VALUES (?, ?, ?, ?, ?, ?, ?)";

						        PreparedStatement pst = konfigurasi.con.prepareStatement(sqlInsert);
						        pst.setString(1, namaPesanan);
						        pst.setString(2, idMenu);
						        pst.setDouble(3, hargaUnit);
						        pst.setInt(4, kuantitas);
						        pst.setDouble(5, hargaTot);
						        pst.setString(6, ket);
						        pst.setString(7, idPesanan);

						        pst.executeUpdate();
						        pst.close();

						        JOptionPane.showMessageDialog(
						            formPemesananFnB.this,
						            "Pesanan F&B berhasil ditambahkan",
						            "Sukses",
						            JOptionPane.INFORMATION_MESSAGE
						        );
						    }

						    // Refresh tabel di form induk
						    if(parent != null) {
							    parent.loadTableFnB(idPesanan);
							    parent.hitungTotalPesananFnB(idPesanan);
						    }
						    dispose();

						} catch (Exception ex) {
						    JOptionPane.showMessageDialog(
						        formPemesananFnB.this,
						        "Error database: " + ex.getMessage(),
						        "Error",
						        JOptionPane.ERROR_MESSAGE
						    );
						    ex.printStackTrace();
						}
					}
				});
				okButton.setBounds(314, 5, 89, 23);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setBounds(413, 5, 92, 23);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		JLabel lblHargaTotal_1 = new JLabel("Harga Total : ");
		lblHargaTotal_1.setBounds(10, 226, 159, 14);
		getContentPane().add(lblHargaTotal_1);
		
		
		hitungTotalFnB();
		isiComboMenuFnB(comboBox_NamaMenuFnB);
		txt_id_MenuFnB.setEditable(false);
		txt_HrgUnit_MenuFnB.setEditable(false);
		txt_HrgTot_MenuFnB.setEditable(false);
	}
	
	private void isiComboMenuFnB(JComboBox comboBox) {
	    try {
	        konfigurasi.KoneksiKeServer();
	        String sql = "SELECT nama_menu FROM menu_fnb";
	        PreparedStatement pst = konfigurasi.con.prepareStatement(sql);
	        var rs = pst.executeQuery();

	        comboBox.removeAllItems();
	        comboBox.addItem("-- Pilih Menu --");

	        while (rs.next()) {
	            comboBox.addItem(rs.getString("nama_menu"));
	        }

	        rs.close();
	        pst.close();
	        konfigurasi.con.close();

	    } catch (SQLException e) {
	        System.out.println("Error load menu F&B : " + e);
	    }
	}
	
	private void hitungTotalFnB() {
	    try {
	        if (txt_kuan_MenuFnB.getText().isEmpty()
	                || txt_HrgUnit_MenuFnB.getText().isEmpty()) {
	            txt_HrgTot_MenuFnB.setText("");
	            return;
	        }

	        int kuantitas = Integer.parseInt(txt_kuan_MenuFnB.getText());
	        double hargaUnit = Double.parseDouble(
	        	    txt_HrgUnit_MenuFnB.getText().replace(",", "")
	        	);

	        double total = kuantitas * hargaUnit;

	        txt_HrgTot_MenuFnB.setText(
	                String.format("%,.0f", total)
	        );

	    } catch (NumberFormatException e) {
	        txt_HrgTot_MenuFnB.setText("");
	    }
	}


}
