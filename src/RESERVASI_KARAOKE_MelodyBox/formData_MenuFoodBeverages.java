package RESERVASI_KARAOKE_MelodyBox;

import java.awt.BorderLayout;

import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class formData_MenuFoodBeverages extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txt_IdMenu_FnB;
	private JTextField txt_Nama_FnB;
	private JTextField txt_harga_FnB;
	private JRadioButton rdbtnMakanan;
	private JRadioButton rdbtnMinuman;
	private JRadioButton rdbtnInStock;
	private JRadioButton rdbtnOutOfStock;
	

	/**
	 * Launch the application.
	
	public static void main(String[] args) {
		try {
			formData_MenuFoodBeverages dialog = new formData_MenuFoodBeverages();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 */
	public formData_MenuFoodBeverages(
	        String idMenu,
	        String namaMenu,
	        String kategori,
	        String hrg_unit,
	        String keterangan,
	        String statusMenu
	){
	    this(); 

	    txt_IdMenu_FnB.setText(idMenu);
	    txt_Nama_FnB.setText(namaMenu);

	    if (kategori.equalsIgnoreCase("Makanan")) {
	    	rdbtnMakanan.setSelected(true);
	    } else {
	        rdbtnMinuman.setSelected(true);
	    }
	    
		txt_harga_FnB.setText(hrg_unit);
	   
	    
	    if (statusMenu.equalsIgnoreCase("In Stock")) {
	    	rdbtnInStock.setSelected(true);
	    } else {
	        rdbtnOutOfStock.setSelected(true);
	    } 

	}

	/**
	 * Create the dialog.
	 */
	public formData_MenuFoodBeverages() {
		setTitle("Form Data Menu F&B MelodyBox");
		setBounds(100, 100, 450, 386);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 436, 312);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Id Menu : ");
			lblNewLabel.setBounds(24, 11, 80, 14);
			contentPanel.add(lblNewLabel);
		}
		{
			JLabel lblNama = new JLabel("Nama : ");
			lblNama.setBounds(24, 38, 62, 14);
			contentPanel.add(lblNama);
		}
		{
			JLabel lblKategori = new JLabel("Kategori : ");
			lblKategori.setBounds(24, 66, 80, 14);
			contentPanel.add(lblKategori);
		}
		{
			JLabel lblHargaUnit = new JLabel("Harga / unit : ");
			lblHargaUnit.setBounds(24, 116, 96, 14);
			contentPanel.add(lblHargaUnit);
		}
		{
			txt_IdMenu_FnB = new JTextField();
			txt_IdMenu_FnB.setBounds(124, 8, 96, 20);
			contentPanel.add(txt_IdMenu_FnB);
			txt_IdMenu_FnB.setColumns(10);
		}
		{
			txt_Nama_FnB = new JTextField();
			txt_Nama_FnB.setColumns(10);
			txt_Nama_FnB.setBounds(124, 35, 244, 20);
			contentPanel.add(txt_Nama_FnB);
		}
		
		rdbtnMakanan = new JRadioButton("Makanan");
		rdbtnMakanan.setBounds(124, 62, 110, 22);
		contentPanel.add(rdbtnMakanan);

		rdbtnMinuman = new JRadioButton("Minuman");
		rdbtnMinuman.setBounds(124, 87, 110, 22);
		contentPanel.add(rdbtnMinuman);
		
		
		ButtonGroup groupKategori = new ButtonGroup();
		groupKategori.add(rdbtnMakanan);
		groupKategori.add(rdbtnMinuman);
		
		{
			txt_harga_FnB = new JTextField();
			txt_harga_FnB.setColumns(10);
			txt_harga_FnB.setBounds(124, 113, 96, 20);
			contentPanel.add(txt_harga_FnB);
		}
		
		JLabel lblHarketerangan = new JLabel("Keterangan : ");
		lblHarketerangan.setBounds(24, 152, 96, 14);
		contentPanel.add(lblHarketerangan);
		
		JTextArea txtArea_Ket = new JTextArea();
		txtArea_Ket.setText("rincian varian yang tersedia");
		txtArea_Ket.setForeground(Color.GRAY); // Sekarang Color sudah dikenali

		txtArea_Ket.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtArea_Ket.getText().equals("rincian varian yang tersedia")) {
                	txtArea_Ket.setText("");
                	txtArea_Ket.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtArea_Ket.getText().isEmpty()) {
                	txtArea_Ket.setForeground(Color.GRAY);
                	txtArea_Ket.setText("rincian varian yang tersedia");
                }
            }
        });
		txtArea_Ket.setBounds(124, 144, 244, 86);
		contentPanel.add(txtArea_Ket);
		
		ButtonGroup groupStatus = new ButtonGroup();
		rdbtnInStock = new JRadioButton("In stock");
		rdbtnInStock.setBounds(124, 240, 110, 22);
		contentPanel.add(rdbtnInStock);
		groupStatus.add(rdbtnInStock);
		
				rdbtnOutOfStock = new JRadioButton("Out of stock");
				rdbtnOutOfStock.setBounds(124, 268, 110, 22);
				contentPanel.add(rdbtnOutOfStock);
				groupStatus.add(rdbtnOutOfStock);
				{
					JLabel lblStatus = new JLabel("Status : ");
					lblStatus.setBounds(24, 248, 80, 14);
					contentPanel.add(lblStatus);
				}
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 316, 436, 33);
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
					            String idMenu = txt_IdMenu_FnB.getText().trim();
					            String namaMenu = txt_Nama_FnB.getText().trim();
					            String kategori = rdbtnMakanan.isSelected() ? "Makanan" : "Minuman";
					            String hrg_unit = txt_harga_FnB.getText().trim();
					            String keterangan = txtArea_Ket.getText().trim();
					            String statusMenu = rdbtnInStock.isSelected() ? "In Stock" : "Out of Stock";
					            

					            // Validasi form
					            if (idMenu.isEmpty() 
					            	|| namaMenu.isEmpty()
					                || hrg_unit.isEmpty()
					                || (!rdbtnMakanan.isSelected() && !rdbtnMinuman.isSelected())
					                || (!rdbtnInStock.isSelected() && !rdbtnOutOfStock.isSelected())) {

					                JOptionPane.showMessageDialog(
					                    rootPane,
					                    "Mohon lengkapi seluruh form",
					                    "Form belum lengkap",
					                    JOptionPane.WARNING_MESSAGE
					                );
					                return;
					            }

					            // Cek apakah ID Paket sudah ada
					            String sqlCheck = "SELECT id_menu FROM menu_fnb WHERE id_menu=?";
					            PreparedStatement psCheck = konfigurasi.con.prepareStatement(sqlCheck);
					            psCheck.setString(1, idMenu);
					            java.sql.ResultSet rs = psCheck.executeQuery();

					            if (rs.next()) {
					                // UPDATE jika ID sudah ada
					            	String sqlUpdate =
					            		    "UPDATE menu_fnb SET "
					            		    + "nama_menu=?, kategori_menu=?, hrg_per_unit=?,keterangan=?, status_menu=? "
					            		    + "WHERE id_menu=?";
					            	
					                PreparedStatement psUpdate = konfigurasi.con.prepareStatement(sqlUpdate);
					                psUpdate.setString(1, namaMenu);
					                psUpdate.setString(2, kategori);
					                psUpdate.setString(3, hrg_unit);
					                psUpdate.setString(4, keterangan);
					                psUpdate.setString(5, statusMenu);
					                psUpdate.setString(6, idMenu);
					                psUpdate.executeUpdate();
					                psUpdate.close();

					            } else {
					                // INSERT jika ID belum ada
					            	String sqlInsert =
					            		    "INSERT INTO menu_fnb "
					            		    + "(id_menu, nama_menu, kategori_menu, hrg_per_unit,keterangan, status_menu) "
					            		    + "VALUES (?, ?, ?, ?, ?,?)";

					                PreparedStatement psInsert = konfigurasi.con.prepareStatement(sqlInsert);
					                psInsert.setString(1, idMenu);
					                psInsert.setString(2, namaMenu);
					                psInsert.setString(3, kategori);
					                psInsert.setString(4, hrg_unit);
					                psInsert.setString(5, keterangan);
					                psInsert.setString(5, statusMenu);
					                psInsert.executeUpdate();
					                psInsert.close();
					            }

					            rs.close();
					            psCheck.close();

					            JOptionPane.showMessageDialog(null, "Data Menu F&B berhasil disimpan!");
					            dispose();
					            new dataMenu_Food_Beverages().setVisible(true);

					        } catch (Exception ex) {
					            JOptionPane.showMessageDialog(null, "Gagal simpan: " + ex.getMessage());
					            ex.printStackTrace();
					        }
					    }
					
				});
				okButton.setBounds(226, 5, 101, 23);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
						dataMenu_Food_Beverages dataMenu_FnB = new dataMenu_Food_Beverages();
			            dataMenu_FnB.setVisible(true);
					}
				});
				cancelButton.setBounds(337, 5, 94, 23);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				
				generateIdMenu();
				txt_IdMenu_FnB.setEditable(false);
			}
		}
	}
	
	private void generateIdMenu() {
	    try {
	        
	        konfigurasi.KoneksiKeServer();

	        String sql = "SELECT id_menu FROM menu_fnb ORDER BY id_menu DESC LIMIT 1";
	        PreparedStatement ps = konfigurasi.con.prepareStatement(sql);
	        java.sql.ResultSet rs = ps.executeQuery();

	        String newId;

	        if (rs.next()) {
	          
	            String lastId = rs.getString("id_menu"); 
	       
	            int number = Integer.parseInt(lastId.substring(1)); 
	         
	            number++;
	         
	            newId = String.format("M%03d", number); 
	        } else {
	           
	            newId = "M001"; 
	        }

	        txt_IdMenu_FnB.setText(newId);
	        txt_IdMenu_FnB.setEditable(false); 

	        rs.close();
	        ps.close();

	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "Gagal generate ID Menu: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
}
