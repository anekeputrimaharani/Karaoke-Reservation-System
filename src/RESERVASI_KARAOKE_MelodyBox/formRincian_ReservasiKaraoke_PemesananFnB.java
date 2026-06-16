package RESERVASI_KARAOKE_MelodyBox;

import java.awt.*;
import java.awt.print.*;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import java.sql.*;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class formRincian_ReservasiKaraoke_PemesananFnB extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txt_TglRes_Pesanan;
	private JTextField txt_NamaPelanggan;
	private JTextField txt_NoTel_Member;
	private JTextField txt_Durasi_Karaoke;
	private JTable table_Orderan_FnB;
	private JTextField txt_HrgTot_Karaoke;
	private JTextField txt_HrgTot_PesananFnB;
	private JTextField textField_7;
	private JTextField txt_TotPembayaran_ResKaraoke_FnB;
	private JTextField txt_Id_Pesanan;
	private JComboBox<String> comboBox_NamaRoom;
	private JComboBox<String> comboBox_Nama_PaketKaraoke;
	private JComboBox<String> comboBox_hrgPerJam_PaketKaraoke;
	private JRadioButton rdbtnTunai;
	private JRadioButton rdbtnNonTunai;
	private JRadioButton rdbtnOnGoing;
	private JRadioButton rdbtnDone;
	private JRadioButton rdbtnCanceled;
	private JTextField text_idRoom;
	private double totalKaraoke = 0;
	private double totalFnB = 0;
	private ButtonGroup groupMetodePembayaran;
	private ButtonGroup groupStatusPesanan;
	private boolean modeEdit = false;
	private JTextField txt_NamaKasir;
	private JTextField textIdKasir;
	private JTextField txtCash;
	private JTextField txtKembalian;
	/**
	 * Launch the application.
	
	public static void main(String[] args) {
		try {
			formRincian_ReservasiKaraoke_PemesananFnB dialog = new formRincian_ReservasiKaraoke_PemesananFnB();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 */

	
	public formRincian_ReservasiKaraoke_PemesananFnB(String idPesanan) {
	    this(); 

	    // --- 1. OTOMATIS ISI DATA KASIR DARI DATABASE ---
	    try {
	     
	        String sql = "SELECT id_user, nama_user FROM user WHERE id_user = ?";
	        java.sql.PreparedStatement ps = konfigurasi.con.prepareStatement(sql);
	        ps.setString(1, konfigurasi.idUserLogin);
	        java.sql.ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            textIdKasir.setText(rs.getString("id_user"));
	            txt_NamaKasir.setText(rs.getString("nama_user"));
	        } else {
	            textIdKasir.setText(konfigurasi.idUserLogin);
	            txt_NamaKasir.setText(konfigurasi.namaUserLogin);
	        }
	        
	        textIdKasir.setEditable(false);
	        txt_NamaKasir.setEditable(false);
	        
	    } catch (Exception e) {
	        System.out.println("Gagal memuat data kasir: " + e.getMessage());
	       
	        textIdKasir.setText(konfigurasi.idUserLogin);
	        txt_NamaKasir.setText(konfigurasi.namaUserLogin);
	    }

	    // --- 2. LISTENER UNTUK PAKET & ROOM ---
	    comboBox_Nama_PaketKaraoke.addActionListener(e -> {
	        if (comboBox_Nama_PaketKaraoke.getSelectedItem() != null) {
	            String paket = comboBox_Nama_PaketKaraoke.getSelectedItem().toString();
	            if (!paket.equals("-- Pilih Paket --")) {
	                updateHargaPaket(paket);
	            }
	        }
	    });

	    comboBox_NamaRoom.addActionListener(e -> {
	        if (comboBox_NamaRoom.getSelectedItem() != null) {
	            String roomTerpilih = comboBox_NamaRoom.getSelectedItem().toString();
	            if (!roomTerpilih.equals("-- Pilih Room --")) {
	                updateIdRoom(roomTerpilih);
	            }
	        }
	    });

	    // --- 3. LOAD DATA TRANSAKSI ---
	    if (txt_Id_Pesanan != null) {
	        txt_Id_Pesanan.setText(idPesanan);
	        txt_Id_Pesanan.setEditable(false);
	    }
	    
	    tampilkanDataReservasi(idPesanan);
	    hitungTotalPesananFnB(idPesanan);
	}
	

	/**
	 * Create the dialog.
	 */
	public formRincian_ReservasiKaraoke_PemesananFnB() {  
		
		
		setTitle("Form Rincian Reservasi Karaoke & Pemesanan F&B MelodyBox");
		setBounds(100, 100, 1127, 749);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 1118, 652);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		{
			JPanel panel = new JPanel();
			panel.setBounds(0, 11, 554, 493);
			contentPanel.add(panel);
			panel.setLayout(null);
			{
				JLabel lblNewLabel = new JLabel("Reservasi Karaoke");
				lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
				lblNewLabel.setBounds(10, 11, 140, 14);
				panel.add(lblNewLabel);
			}
			{
				JLabel lblNewLabel_1 = new JLabel("Id Kasir : ");
				lblNewLabel_1.setBounds(20, 36, 76, 14);
				panel.add(lblNewLabel_1);
			}
			{
				JLabel lblNewLabel_1 = new JLabel("Tanggal Reservasi : ");
				lblNewLabel_1.setBounds(20, 113, 130, 14);
				panel.add(lblNewLabel_1);
			}
			{
				JLabel lblNewLabel_1 = new JLabel("Nama Pelanggan : ");
				lblNewLabel_1.setBounds(20, 218, 114, 14);
				panel.add(lblNewLabel_1);
			}
			{
				JLabel lblNewLabel_1 = new JLabel("No Telp Member : ");
				lblNewLabel_1.setBounds(20, 184, 114, 14);
				panel.add(lblNewLabel_1);
			}
			{
				JLabel lblNewLabel_1 = new JLabel("Paket Karaoke : ");
				lblNewLabel_1.setBounds(20, 253, 114, 14);
				panel.add(lblNewLabel_1);
			}
			{
				JLabel lblNewLabel_1 = new JLabel("Durasi Karaoke : ");
				lblNewLabel_1.setBounds(20, 340, 114, 14);
				panel.add(lblNewLabel_1);
			}
			{
				JLabel lblNewLabel_1 = new JLabel("Id Room : ");
				lblNewLabel_1.setBounds(20, 411, 114, 14);
				panel.add(lblNewLabel_1);
			}
			{
				JLabel lblNewLabel_1 = new JLabel("Nama Room : ");
				lblNewLabel_1.setBounds(20, 378, 114, 14);
				panel.add(lblNewLabel_1);
			}
			{
				txt_TglRes_Pesanan = new JTextField();
				txt_TglRes_Pesanan.setBounds(158, 110, 147, 20);
				panel.add(txt_TglRes_Pesanan);
				txt_TglRes_Pesanan.setColumns(10);
			}
			{
				txt_NamaPelanggan = new JTextField();
				txt_NamaPelanggan.setColumns(10);
				txt_NamaPelanggan.setBounds(158, 215, 291, 20);
				panel.add(txt_NamaPelanggan);
			}
			{
				txt_NoTel_Member = new JTextField();
				txt_NoTel_Member.addKeyListener(new java.awt.event.KeyAdapter() {
				    @Override
				    public void keyReleased(java.awt.event.KeyEvent e) {
				        cariMemberByNoTelp();
				    }
				});
				txt_NoTel_Member.setColumns(10);
				txt_NoTel_Member.setBounds(158, 184, 216, 20);
				panel.add(txt_NoTel_Member);
			}
			{
				comboBox_Nama_PaketKaraoke = new JComboBox();
				comboBox_Nama_PaketKaraoke.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (comboBox_Nama_PaketKaraoke.getSelectedItem() == null) return;

					    String namaPaket = comboBox_Nama_PaketKaraoke.getSelectedItem().toString();

					    if (namaPaket.equals("-- Pilih Paket --")) {
					        comboBox_hrgPerJam_PaketKaraoke.removeAllItems();
					        return;
					    }

					    updateHargaPaket(namaPaket);
					}
				});
				comboBox_Nama_PaketKaraoke.setBounds(158, 249, 291, 22);
				panel.add(comboBox_Nama_PaketKaraoke);
			}
			{
				txt_Durasi_Karaoke = new JTextField();
				txt_Durasi_Karaoke.addKeyListener(new java.awt.event.KeyAdapter() {
				    @Override
				    public void keyReleased(java.awt.event.KeyEvent e) {
				        hitungTotalKaraoke();
				    }
				});
				txt_Durasi_Karaoke.setColumns(10);
				txt_Durasi_Karaoke.setBounds(158, 337, 147, 20);
				panel.add(txt_Durasi_Karaoke);
			}
			{
				comboBox_NamaRoom = new JComboBox();
				comboBox_NamaRoom.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						 if (comboBox_NamaRoom.getSelectedItem() == null) return;

						    String namaRoom = comboBox_NamaRoom.getSelectedItem().toString();

						    if (namaRoom.equals("-- Pilih Room --")) {
						        text_idRoom.setText("");
						        return;
						    }

						    updateIdRoom(namaRoom);
					}
				});
				comboBox_NamaRoom.setBounds(158, 374, 291, 22);
				panel.add(comboBox_NamaRoom);
			}
			{
				JLabel lblNewLabel_1 = new JLabel("Total Harga Karaoke : ");
				lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
				lblNewLabel_1.setBounds(20, 453, 130, 14);
				panel.add(lblNewLabel_1);
			}
			{
				JLabel lblNewLabel_1 = new JLabel("Harga Paket / jam : ");
				lblNewLabel_1.setBounds(20, 297, 114, 14);
				panel.add(lblNewLabel_1);
			}
			{
				txt_HrgTot_Karaoke = new JTextField();
				txt_HrgTot_Karaoke.setColumns(10);
				txt_HrgTot_Karaoke.setBounds(158, 450, 147, 20);
				panel.add(txt_HrgTot_Karaoke);
			}
			
			JLabel lblNewLabel_1 = new JLabel("Id Pesanan : ");
			lblNewLabel_1.setBounds(20, 153, 130, 14);
			panel.add(lblNewLabel_1);
			
			txt_Id_Pesanan = new JTextField();
			txt_Id_Pesanan.setColumns(10);
			txt_Id_Pesanan.setBounds(158, 151, 147, 22);
			panel.add(txt_Id_Pesanan);
			
			comboBox_hrgPerJam_PaketKaraoke = new JComboBox<>();
			comboBox_hrgPerJam_PaketKaraoke.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					 hitungTotalKaraoke();
				}
			});
			comboBox_hrgPerJam_PaketKaraoke.setBounds(158, 293, 291, 22);
			panel.add(comboBox_hrgPerJam_PaketKaraoke);
			
			text_idRoom = new JTextField();
			text_idRoom.setColumns(10);
			text_idRoom.setBounds(158, 408, 147, 20);
			panel.add(text_idRoom);
			
			JLabel lblNewLabel_1_1 = new JLabel("Nama Kasir: ");
			lblNewLabel_1_1.setBounds(20, 76, 130, 14);
			panel.add(lblNewLabel_1_1);
			
			txt_NamaKasir = new JTextField();
			txt_NamaKasir.setColumns(10);
			txt_NamaKasir.setBounds(158, 69, 147, 20);
			panel.add(txt_NamaKasir);
			
			textIdKasir = new JTextField();
			textIdKasir.setEditable(false);
			textIdKasir.setColumns(10);
			textIdKasir.setBounds(158, 33, 147, 20);
			panel.add(textIdKasir);
		}
		{
			textField_7 = new JTextField();
			textField_7.setBounds(384, 697, 126, 20);
			contentPanel.add(textField_7);
			textField_7.setColumns(10);
		}
		{
			JLabel lblNewLabel_4 = new JLabel("Total Pembayaran : ");
			lblNewLabel_4.setBounds(248, 700, 126, 14);
			contentPanel.add(lblNewLabel_4);
		}
		{
			JPanel panel = new JPanel();
			panel.setBounds(564, 11, 554, 415);
			contentPanel.add(panel);
			panel.setLayout(null);
			{
				JLabel lblNewLabel_2 = new JLabel("Food & Beverages");
				lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 13));
				lblNewLabel_2.setBounds(10, 22, 133, 14);
				panel.add(lblNewLabel_2);
			}
			{
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setBounds(10, 82, 524, 292);
				panel.add(scrollPane);
				{
					table_Orderan_FnB = new JTable();
					DefaultTableModel model = new DefaultTableModel(
				            new Object[][] {},
				            new String[] {
				            		"Nama Menu", "Id Menu", "Harga / unit", "Kuantitas", "Harga Total", "Ket Orderan"
				           }
				        ) {
				            // Method ini membuat tabel TIDAK BISA diedit secara langsung
				            @Override
				            public boolean isCellEditable(int row, int column) {
				                return false;
				            }
				        };
				        
				        table_Orderan_FnB.setModel(model);
					
					table_Orderan_FnB.getColumnModel().getColumn(0).setPreferredWidth(151);
					table_Orderan_FnB.getColumnModel().getColumn(1).setPreferredWidth(79);
					table_Orderan_FnB.getColumnModel().getColumn(2).setPreferredWidth(104);
					table_Orderan_FnB.getColumnModel().getColumn(4).setPreferredWidth(104);
					table_Orderan_FnB.getColumnModel().getColumn(5).setPreferredWidth(125);
					scrollPane.setViewportView(table_Orderan_FnB);
				}
			}
			{
				JButton btnAdd = new JButton("Add");
				btnAdd.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						 String idPesanan = txt_Id_Pesanan.getText();

						    if (idPesanan.isEmpty()) {
						        JOptionPane.showMessageDialog(
						            null,
						            "Id Pesanan belum ada!",
						            "Error",
						            JOptionPane.WARNING_MESSAGE
						        );
						        return;
						    }

						    formPemesananFnB fnb =
						            new formPemesananFnB(
						                idPesanan,
						                formRincian_ReservasiKaraoke_PemesananFnB.this
						            );

						    fnb.setLocationRelativeTo(formRincian_ReservasiKaraoke_PemesananFnB.this);
						    fnb.setVisible(true);
					}
				});
				btnAdd.setBounds(20, 49, 88, 22);
				panel.add(btnAdd);
			}
			{
				JButton btnEdit = new JButton("Edit");
				btnEdit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						int rowTerpilih = table_Orderan_FnB.getSelectedRow();
				        if (rowTerpilih == -1) {
				            JOptionPane.showMessageDialog(
				                formRincian_ReservasiKaraoke_PemesananFnB.this,
				                "Pilih menu F&B yang akan diedit!"
				            );
				            return;
				        }

				        // ================= AMBIL DATA DARI TABEL =================
				        String idPesanan = txt_Id_Pesanan.getText();

				        String namaMenu = table_Orderan_FnB.getValueAt(rowTerpilih, 0).toString();
				        String idMenu   = table_Orderan_FnB.getValueAt(rowTerpilih, 1).toString();
				        String hrgUnit  = table_Orderan_FnB.getValueAt(rowTerpilih, 2).toString();
				        String kuantitas= table_Orderan_FnB.getValueAt(rowTerpilih, 3).toString();
				        String hrgTotal = table_Orderan_FnB.getValueAt(rowTerpilih, 4).toString();
				        String ket      = table_Orderan_FnB.getValueAt(rowTerpilih, 5).toString();

				        // ================= BUKA FORM PEMESANAN F&B =================
				        formPemesananFnB formEdit = new formPemesananFnB(
				            idPesanan,
				            namaMenu,
				            idMenu,
				            hrgUnit,
				            kuantitas,
				            hrgTotal,
				            ket,
				            formRincian_ReservasiKaraoke_PemesananFnB.this
				        );

				        formEdit.setLocationRelativeTo(formRincian_ReservasiKaraoke_PemesananFnB.this);
				        formEdit.setVisible(true);
					
					}
				});
				btnEdit.setBounds(115, 47, 88, 22);
				panel.add(btnEdit);
			}
			{
				JButton btnDelete = new JButton("Delete");
				btnDelete.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						int row = table_Orderan_FnB.getSelectedRow();

				        // 1. Validasi baris dipilih
				        if (row == -1) {
				            JOptionPane.showMessageDialog(
				                formRincian_ReservasiKaraoke_PemesananFnB.this,
				                "Pilih data F&B yang ingin dihapus!",
				                "Peringatan",
				                JOptionPane.WARNING_MESSAGE
				            );
				            return;
				        }

				        // 2. Konfirmasi hapus
				        int konfirmasi = JOptionPane.showConfirmDialog(
				            formRincian_ReservasiKaraoke_PemesananFnB.this,
				            "Yakin ingin menghapus pesanan F&B ini?",
				            "Konfirmasi",
				            JOptionPane.YES_NO_OPTION
				        );

				        if (konfirmasi != JOptionPane.YES_OPTION) return;

				        // 3. Ambil data dari tabel
				        String idPesanan = txt_Id_Pesanan.getText();
				        String idMenu = table_Orderan_FnB.getValueAt(row, 1).toString();

				        try {
				            // 4. Koneksi database
				            konfigurasi.KoneksiKeServer();

				            String sql = "DELETE FROM pemesanan_fnb WHERE id_pesanan = ? AND id_menu = ?";
				            PreparedStatement pst = konfigurasi.con.prepareStatement(sql);
				            pst.setString(1, idPesanan);
				            pst.setString(2, idMenu);

				            pst.executeUpdate();

				            pst.close();
				            konfigurasi.con.close();

				            // 5. Refresh tabel & total
				            loadTableFnB(idPesanan);
				            hitungTotalPesananFnB(idPesanan);

				            JOptionPane.showMessageDialog(
				                formRincian_ReservasiKaraoke_PemesananFnB.this,
				                "Pesanan F&B berhasil dihapus",
				                "Sukses",
				                JOptionPane.INFORMATION_MESSAGE
				            );

				        } catch (Exception ex) {
				            JOptionPane.showMessageDialog(
				                formRincian_ReservasiKaraoke_PemesananFnB.this,
				                "Gagal menghapus data: " + ex.getMessage(),
				                "Error",
				                JOptionPane.ERROR_MESSAGE
				            );
				        }
						
					}
				});
				btnDelete.setBounds(212, 47, 88, 22);
				panel.add(btnDelete);
			}
			{
				txt_HrgTot_PesananFnB = new JTextField();
				txt_HrgTot_PesananFnB.setBounds(216, 385, 149, 20);
				panel.add(txt_HrgTot_PesananFnB);
				txt_HrgTot_PesananFnB.setColumns(10);
			}
			{
				JLabel lblNewLabel_3 = new JLabel("Total Harga F&B : ");
				lblNewLabel_3.setBounds(10, 388, 166, 14);
				panel.add(lblNewLabel_3);
				lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 11));
			}
		}
		{
			JLabel lblNewLabel_5 = new JLabel("Metode Pembayaran : ");
			lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblNewLabel_5.setBounds(715, 464, 174, 14);
			contentPanel.add(lblNewLabel_5);
		}
		{
			JLabel lblNewLabel_5 = new JLabel("Total Pembayaran : ");
			lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblNewLabel_5.setBounds(715, 435, 174, 14);
			contentPanel.add(lblNewLabel_5);
		}
		{
			rdbtnTunai = new JRadioButton("Tunai ");
			rdbtnTunai.setBounds(895, 461, 82, 22);
			contentPanel.add(rdbtnTunai);
		}
		
		rdbtnNonTunai = new JRadioButton("Non Tunai");
		rdbtnNonTunai.setBounds(979, 461, 110, 22);
		contentPanel.add(rdbtnNonTunai);
		
		JLabel lblNewLabel_5_1 = new JLabel("Cash :");
	    lblNewLabel_5_1.setFont(new Font("Tahoma", Font.BOLD, 12));
	    lblNewLabel_5_1.setBounds(715, 505, 48, 14);
	    contentPanel.add(lblNewLabel_5_1);
	    lblNewLabel_5_1.setVisible(false);
	    
	    txtCash = new JTextField();
	    txtCash.setColumns(10);
	    txtCash.setBounds(780, 503, 126, 20);
	    contentPanel.add(txtCash);
	    txtCash.setVisible(false);
	    
	    txtKembalian = new JTextField();
	    txtKembalian.setEditable(false);
	    txtKembalian.setColumns(10);
	    txtKembalian.setBounds(782, 530, 124, 20);
	    contentPanel.add(txtKembalian);
	    txtKembalian.setVisible(false);
	    txtKembalian.setEditable(false);
	    
	    JLabel lblNewLabel_5_1_1 = new JLabel("Kembali :");
	    lblNewLabel_5_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
	    lblNewLabel_5_1_1.setBounds(715, 536, 75, 14);
	    contentPanel.add(lblNewLabel_5_1_1);
	    lblNewLabel_5_1_1.setVisible(false);
	    
	    ActionListener listenerMetode = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isTunai = rdbtnTunai.isSelected();
          
                lblNewLabel_5_1.setVisible(isTunai);   
                txtCash.setVisible(isTunai);           
                
                
                lblNewLabel_5_1_1.setVisible(isTunai); 
                txtKembalian.setVisible(isTunai);      
                
                if (!isTunai) {
                    txtCash.setText("0");
                    txtKembalian.setText("0");
                }
            }
        };

        rdbtnTunai.addActionListener(listenerMetode);
        rdbtnNonTunai.addActionListener(listenerMetode);
       
        
        txtCash.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                hitungKembalian();
            }
        });
		
		
		{
			txt_TotPembayaran_ResKaraoke_FnB = new JTextField();
			txt_TotPembayaran_ResKaraoke_FnB.setBounds(899, 433, 154, 20);
			contentPanel.add(txt_TotPembayaran_ResKaraoke_FnB);
			txt_TotPembayaran_ResKaraoke_FnB.setColumns(10);
		}
		
		JLabel lblNewLabel_5 = new JLabel("Status : ");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_5.setBounds(718, 594, 174, 14);
		contentPanel.add(lblNewLabel_5);
		
		rdbtnOnGoing = new JRadioButton("On Going (not paid yet)");
		rdbtnOnGoing.setBounds(898, 564, 174, 22);
		contentPanel.add(rdbtnOnGoing);

		rdbtnDone = new JRadioButton("Done (Paid)");
		// Popup otomatis kalau klik done
        rdbtnDone.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                if (rdbtnDone.isSelected()) {
                  
                    int jawab = JOptionPane.showConfirmDialog(
                        formRincian_ReservasiKaraoke_PemesananFnB.this, 
                        "Status pesanan diubah menjadi DONE.\nApakah ingin mencetak struk sekarang?", 
                        "Konfirmasi Cetak Struk", 
                        JOptionPane.YES_NO_OPTION
                    );
                    
                    // jika pilih yes
                    if (jawab == JOptionPane.YES_OPTION) {
                        cetakStrukPembayaran();
                    }
                }
            }
        });
        
        contentPanel.add(rdbtnDone);
		rdbtnDone.setBounds(898, 586, 174, 22);
		contentPanel.add(rdbtnDone);

		rdbtnCanceled = new JRadioButton("Canceled");
		rdbtnCanceled.setBounds(898, 611, 174, 22);
		contentPanel.add(rdbtnCanceled);
		
		groupStatusPesanan = new ButtonGroup();
		groupStatusPesanan.add(rdbtnOnGoing);
		groupStatusPesanan.add(rdbtnDone);
		groupStatusPesanan.add(rdbtnCanceled);
	
        
        
		{
			
			groupMetodePembayaran = new ButtonGroup();
			groupMetodePembayaran.add(rdbtnTunai);
			groupMetodePembayaran.add(rdbtnNonTunai);
			
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 652, 1118, 49);
			getContentPane().add(buttonPane);
			buttonPane.setLayout(null);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent e) {

				        // 1. Ambil Data dari Form
				        String idPesanan   = txt_Id_Pesanan.getText();
				        String idKasir     = textIdKasir.getText().toString();
				        String namaKasir   = txt_NamaKasir.getText();
				        String tgl         = txt_TglRes_Pesanan.getText();
				        String nama        = txt_NamaPelanggan.getText();
				        String durasi      = txt_Durasi_Karaoke.getText();
				        // Cek null safety untuk combobox agar tidak error jika belum dipilih
				        String namaPaket   = (comboBox_Nama_PaketKaraoke.getSelectedItem() != null) ? comboBox_Nama_PaketKaraoke.getSelectedItem().toString() : "";
				        String idRoom      = text_idRoom.getText();
				        String noTelpMember = txt_NoTel_Member.getText().trim(); // Pakai trim() untuk hapus spasi
				        String hrgPaketJam  = (comboBox_hrgPerJam_PaketKaraoke.getSelectedItem() != null) ? comboBox_hrgPerJam_PaketKaraoke.getSelectedItem().toString() : "0";
				        String namaRoom     = (comboBox_NamaRoom.getSelectedItem() != null) ? comboBox_NamaRoom.getSelectedItem().toString() : "";
				        
				        // Hitung Total (Pastikan totalKaraoke sudah terisi saat Load/Edit Durasi)
				        double totalPembayaran = totalKaraoke + totalFnB;

				        String metodeBayar = rdbtnTunai.isSelected() ? "Tunai" : "Non Tunai";

				        String status = "";
				        if (rdbtnOnGoing.isSelected()) status = "On Going (not paid yet)";
				        else if (rdbtnDone.isSelected()) status = "Done (Paid)";
				        else if (rdbtnCanceled.isSelected()) status = "Canceled";

				        try {
				            konfigurasi.KoneksiKeServer();

				            // ================= CEK APAKAH ID SUDAH ADA (INSERT / UPDATE) =================
				            boolean sudahAda = false;
				            String cekSql = "SELECT id_pesanan FROM reservasi_pemesananfnb WHERE id_pesanan = ?";
				            PreparedStatement psCek = konfigurasi.con.prepareStatement(cekSql);
				            psCek.setString(1, idPesanan);
				            ResultSet rs = psCek.executeQuery();
				            sudahAda = rs.next();
				            rs.close();
				            psCek.close();

				            if (!sudahAda) {
				                // =========================================================
				                // LOGIKA INSERT (DATA BARU)
				                // =========================================================
				                String sqlInsert =
				                    "INSERT INTO reservasi_pemesananfnb " +
				                    "(id_pesanan, id_user, nama_user, tgl_reservasi, nama_pelanggan, durasi_karaoke, " +
				                    "nama_paket, id_room, hrg_tot_karaoke, hrg_tot_pemesananFnB, " +
				                    "hrg_totPembayaran, metode_pembayaran, status_pesanan, noTelp_member, " + 
				                    "hrg_paket_jam, nama_room) " +
				                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				                
				                PreparedStatement ps = konfigurasi.con.prepareStatement(sqlInsert);
				                ps.setString(1, idPesanan);
				                ps.setString(2, idKasir);
				                ps.setString(3, namaKasir);
				                ps.setString(4, tgl);
				                ps.setString(5, nama);
				                ps.setString(6, durasi);
				                ps.setString(7, namaPaket);
				                ps.setString(8, idRoom);
				                ps.setDouble(9, totalKaraoke);
				                ps.setDouble(10, totalFnB);
				                ps.setDouble(11, totalPembayaran);
				                ps.setString(12, metodeBayar);
				                ps.setString(13, status);
				                
				                // --- PERBAIKAN DISINI (INSERT) ---
				                if (noTelpMember.isEmpty()) {
				                    ps.setNull(14, java.sql.Types.VARCHAR); // Kirim NULL jika kosong
				                } else {
				                    ps.setString(14, noTelpMember);
				                }
				                // ----------------------------------
				                
				                ps.setString(15, hrgPaketJam);
				                ps.setString(16, namaRoom);
				                
				                ps.executeUpdate();
				                ps.close();

				                JOptionPane.showMessageDialog(null, "Pesanan berhasil disimpan");

				            } else {
				                // =========================================================
				                // LOGIKA UPDATE (EDIT DATA)
				                // =========================================================
				                String sqlUpdate =
				                    "UPDATE reservasi_pemesananfnb SET " +
				                    "id_user=?,nama_user=?, tgl_reservasi=?, nama_pelanggan=?, durasi_karaoke=?, " +
				                    "nama_paket=?, id_room=?, hrg_tot_karaoke=?, hrg_tot_pemesananFnB=?, " +
				                    "hrg_totPembayaran=?, metode_pembayaran=?, status_pesanan=?, noTelp_member=?, " +
				                    "hrg_paket_jam=?, nama_room=? " +
				                    "WHERE id_pesanan=?";

				                PreparedStatement ps = konfigurasi.con.prepareStatement(sqlUpdate);
				                
				                ps.setString(1, idKasir);
				                ps.setString(2, namaKasir);
				                ps.setString(3, tgl);
				                ps.setString(4, nama);
				                ps.setString(5, durasi);
				                ps.setString(6, namaPaket);
				                ps.setString(7, idRoom);
				                ps.setDouble(8, totalKaraoke);
				                ps.setDouble(9, totalFnB);
				                ps.setDouble(10, totalPembayaran);
				                ps.setString(11, metodeBayar);
				                ps.setString(12, status);
				                
				                
				                if (noTelpMember.isEmpty()) {
				                    ps.setNull(13, java.sql.Types.VARCHAR); // Kirim NULL jika kosong
				                } else {
				                    ps.setString(13, noTelpMember);
				                }
				                // ----------------------------------

				                ps.setString(14, hrgPaketJam);
				                ps.setString(15, namaRoom);
				                
				                
				                ps.setString(16, idPesanan); 

				                ps.executeUpdate();
				                ps.close();

				                JOptionPane.showMessageDialog(null, "Pesanan berhasil diperbarui");
				            }

				            konfigurasi.con.close();
				            dispose(); 
				            
				            // Buka kembali halaman list agar data terrefresh
				            new reservasiKaraoke_PemesananFnB_Page().setVisible(true);
				            
				        } catch (Exception ex) {
				            JOptionPane.showMessageDialog(
				                null,
				                "Gagal menyimpan data: " + ex.getMessage(),
				                "Error",
				                JOptionPane.ERROR_MESSAGE
				            );
				            ex.printStackTrace();
				        }
				    }
				});
				okButton.setBounds(849, 11, 91, 23);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent e) {
				        
				        String idYangSedangDibuat = txt_Id_Pesanan.getText();

				        // Jika ID ini BELUM ADA di tabel Reservasi (artinya User baru mau buat, tapi batal),
				        // maka hapus semua F&B yang terlanjur diinput.
				        // TAPI jika ID sudah ada (artinya User cuma mau Edit), jangan dihapus.
				        
				        if (!cekApakahIdAdaDiDatabase(idYangSedangDibuat)) {
				             hapusSemuaFnB(idYangSedangDibuat); 
				        }

				        dispose();
				        // Buka kembali halaman list
				        reservasiKaraoke_PemesananFnB_Page reservasiPemesanan_Page = new reservasiKaraoke_PemesananFnB_Page();
				        reservasiPemesanan_Page.setVisible(true);
				    }
				});
				cancelButton.setBounds(950, 11, 100, 23);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			
			
		}
		
		  // --- 1. LOAD DATA MASTER  ---
	    isiComboKasir();
	    isiComboPaket();
	    isiComboRoom();
	    loadKasirLogin();
	    text_idRoom.setEnabled(false);
	    txt_HrgTot_Karaoke.setEditable(false);
	    txt_HrgTot_PesananFnB.setEnabled(false);
	    
	    setTanggalOtomatis();
	   
	    // generate ID HANYA untuk MODE ADD
	    if (txt_Id_Pesanan.getText().isEmpty()) {
	        txt_Id_Pesanan.setText(generateIdPesanan());
	    }
	    
	    txt_Id_Pesanan.setEditable(false);
	    txt_TotPembayaran_ResKaraoke_FnB.setEditable(false);
	    txt_NamaKasir.setEditable(false);
  
		
	}
	
	
	
	private void tampilkanDataReservasi(String idPesanan) {
	    
	    // 1. Buka Koneksi Awal
	    konfigurasi.KoneksiKeServer();
	    if (konfigurasi.con == null) {
	        JOptionPane.showMessageDialog(this, "Gagal koneksi ke database");
	        return;
	    }
	    
	    try {
	        // ========================================================================
	        // QUERY 1: HEADER (Data Reservasi Utama)
	        // ========================================================================
	    	String sqlHeader =
	    		"SELECT r.*, " +
	    		"u.id_user, u.nama_user, " + 
	            "m.noTelp_member, " +
	            "rm.nama_room, rm.id_room, " +
	            "p.nama_paket, p.hrg_weekdays_pagi, p.hrg_weekdays_sore, p.hrg_weekend_pagi, p.hrg_weekend_sore " +
	            "FROM reservasi_pemesananfnb r " +
	            "JOIN user u ON r.id_user = u.id_user " +
	            "LEFT JOIN member m ON r.noTelp_member = m.noTelp_member " +
	            "JOIN room rm ON r.id_room = rm.id_room " +
	            "JOIN paket_karaoke p ON r.nama_paket = p.nama_paket " +
	            "WHERE r.id_pesanan = ?";

	        java.sql.PreparedStatement pst = konfigurasi.con.prepareStatement(sqlHeader);
	        pst.setString(1, idPesanan);
	        java.sql.ResultSet rs = pst.executeQuery();

	        if (rs.next()) {
	            // --- ISI FORM BAGIAN KIRI (RESERVASI) ---
	            txt_TglRes_Pesanan.setText(rs.getString("tgl_reservasi"));
	            txt_NamaPelanggan.setText(rs.getString("nama_pelanggan"));
	            txt_NoTel_Member.setText(rs.getString("noTelp_member"));
	            
	            // Update Paket
	            String namaPaket = rs.getString("nama_paket");
	            comboBox_Nama_PaketKaraoke.setSelectedItem(namaPaket);
	            updateHargaPaket(namaPaket); // Method ini menutup koneksi

	            // Update Room
	            txt_Durasi_Karaoke.setText(rs.getString("durasi_karaoke")); 
	            String namaRoom = rs.getString("nama_room");
	            // Cek apakah room ini ada di list ComboBox yang sekarang
	            boolean adaDiList = false;
	            for (int i = 0; i < comboBox_NamaRoom.getItemCount(); i++) {
					if (comboBox_NamaRoom.getItemAt(i).toString().equals(namaRoom)) {
						adaDiList = true;
						break;
					}
				}
	            
	            // jika room itu booked
	            if (!adaDiList) {
					comboBox_NamaRoom.addItem(namaRoom);
				}
	            
	            // set selected
	            comboBox_NamaRoom.setSelectedItem(namaRoom);
	            
	            // Isi Harga
	         
	            totalKaraoke = rs.getDouble("hrg_tot_karaoke");
	            txt_HrgTot_Karaoke.setText(formatRupiah(totalKaraoke));

	         // 3. Update Text Field Total Pembayaran
	         // Menjumlahkan (Total Karaoke yg baru di-load + Total F&B)
	            hitungTotalPembayaran(); 

	         // -------------------------
	            txt_HrgTot_PesananFnB.setText(rs.getString("hrg_tot_pemesananFnB"));
	            txt_TotPembayaran_ResKaraoke_FnB.setText(rs.getString("hrg_totPembayaran"));
	            
	            // Radio Button Metode Pembayaran
	            String metode = rs.getString("metode_pembayaran");
	            if (metode != null) {
	                if (metode.equalsIgnoreCase("Tunai")) rdbtnTunai.setSelected(true);
	                else rdbtnNonTunai.setSelected(true);
	            }

	            // Radio Button Status
	            String status = rs.getString("status_pesanan");
	            if (status != null) {
	                if (status.equalsIgnoreCase("On Going (not paid yet)")) rdbtnOnGoing.setSelected(true);
	                else if (status.equalsIgnoreCase("Done (Paid)")) rdbtnDone.setSelected(true);
	                else if (status.equalsIgnoreCase("Canceled")) rdbtnCanceled.setSelected(true);
	            }
	        }
	        rs.close();
	        pst.close();

	        // ========================================================================
	        // KONEKSI ULANG (PENTING KARENA updateHargaPaket MENUTUP KONEKSI)
	        // ========================================================================
	        konfigurasi.KoneksiKeServer(); 

	        // ========================================================================
	        // QUERY 2: TABEL FOOD & BEVERAGES (F&B)
	        // ========================================================================
	        // ambil langsung dari tabel 'pemesanan_fnb'
	        String sqlFnB = "SELECT * FROM pemesanan_fnb WHERE id_pesanan = ?"; 

	        java.sql.PreparedStatement pstFnB = konfigurasi.con.prepareStatement(sqlFnB);
	        pstFnB.setString(1, idPesanan);
	        java.sql.ResultSet rsFnB = pstFnB.executeQuery();

	        DefaultTableModel modelFnB = (DefaultTableModel) table_Orderan_FnB.getModel();
	        modelFnB.setRowCount(0); 

	        while (rsFnB.next()) {
	            // Ambil data sesuai nama kolom di tabel pemesanan_fnb
	            String namaMenu = rsFnB.getString("nama_pesanan");
	            String idMenu = rsFnB.getString("id_menu");
	            double hrgUnit = rsFnB.getDouble("hrg_unitMenu");
	            int kuantitas = rsFnB.getInt("kuantitas");
	            double hrgTotal = rsFnB.getDouble("hrg_totMenu");
	            String ket = rsFnB.getString("ket");

	            modelFnB.addRow(new Object[]{
	                namaMenu,
	                idMenu,
	                hrgUnit,
	                kuantitas,
	                hrgTotal,
	                ket
	            });
	        }
	        
	        rsFnB.close();
	        pstFnB.close();
	        konfigurasi.con.close(); // Tutup koneksi terakhir

	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(this, "Gagal meload data reservasi: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	private void updateHargaPaket(String namaPaket) {

	    comboBox_hrgPerJam_PaketKaraoke.removeAllItems();

	    try {
	        konfigurasi.KoneksiKeServer();

	        String sql = "SELECT hrg_weekdays_pagi, hrg_weekdays_sore, " +
	                     "hrg_weekend_pagi, hrg_weekend_sore " +
	                     "FROM paket_karaoke WHERE nama_paket = ?";

	        PreparedStatement pst = konfigurasi.con.prepareStatement(sql);
	        pst.setString(1, namaPaket);
	        ResultSet rs = pst.executeQuery();

	        if (rs.next()) {
	            comboBox_hrgPerJam_PaketKaraoke.addItem(rs.getString("hrg_weekdays_pagi"));
	            comboBox_hrgPerJam_PaketKaraoke.addItem(rs.getString("hrg_weekdays_sore"));
	            comboBox_hrgPerJam_PaketKaraoke.addItem(rs.getString("hrg_weekend_pagi"));
	            comboBox_hrgPerJam_PaketKaraoke.addItem(rs.getString("hrg_weekend_sore"));
	        }

	        rs.close();
	        pst.close();
	        konfigurasi.con.close();

	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(this, "Gagal load harga paket: " + e.getMessage());
	    }
	}


	
	// Method Mengisi Dropdown Kasir
	private void isiComboKasir() {
	    
	    try {
	        konfigurasi.KoneksiKeServer();
	        if (konfigurasi.con != null) {
	        	String sql = "SELECT id_user FROM user WHERE role = 'Kasir'"; // Ambil semua ID Kasir
	            Statement stm = konfigurasi.con.createStatement();
	            ResultSet rs = stm.executeQuery(sql);
	            
	            while(rs.next()) {
	                textIdKasir.setText(rs.getString("id_user"));
	            }
	            rs.close();
	            stm.close();
	            konfigurasi.con.close();
	        }
	    } catch (Exception e) {
	        System.out.println("Error load kasir: " + e.getMessage());
	    }
	}

	// Method Mengisi Dropdown Paket Karaoke
	private void isiComboPaket() {
	    comboBox_Nama_PaketKaraoke.removeAllItems();
	    comboBox_Nama_PaketKaraoke.addItem("-- Pilih Paket --");
	    
	    try {
	        konfigurasi.KoneksiKeServer();
	        if (konfigurasi.con != null) {
	            String sql = "SELECT nama_paket FROM paket_karaoke"; 
	            Statement stm = konfigurasi.con.createStatement();
	            ResultSet rs = stm.executeQuery(sql);
	            
	            while(rs.next()) {
	                comboBox_Nama_PaketKaraoke.addItem(rs.getString("nama_paket"));
	            }
	            rs.close();
	            stm.close();
	            konfigurasi.con.close();
	        }
	    } catch (Exception e) {
	        System.out.println("Error load paket: " + e.getMessage());
	    }
	}

	//Method Mengisi Dropdown Room
	private void isiComboRoom() {
	    comboBox_NamaRoom.removeAllItems();
	    comboBox_NamaRoom.addItem("-- Pilih Room --");
	    
	    try {
	        konfigurasi.KoneksiKeServer();
	        if (konfigurasi.con != null) {
	            String sql = "SELECT nama_room FROM room WHERE status_room = 'Not Booked' ORDER BY nama_room ASC"; 
	            Statement stm = konfigurasi.con.createStatement();
	            ResultSet rs = stm.executeQuery(sql);
	            
	            while(rs.next()) {
	                comboBox_NamaRoom.addItem(rs.getString("nama_room"));
	            }
	            rs.close();
	            stm.close();
	            konfigurasi.con.close();
	        }
	    } catch (Exception e) {
	        System.out.println("Error load room: " + e.getMessage());
	    }
	}

	// 4. Method Update ID Room (Agar otomatis terisi saat Room dipilih)
	private void updateIdRoom(String namaRoom) {

	    try {
	        konfigurasi.KoneksiKeServer();

	        String sql = "SELECT id_room FROM room WHERE nama_room = ?";
	        PreparedStatement pst = konfigurasi.con.prepareStatement(sql);
	        pst.setString(1, namaRoom);

	        ResultSet rs = pst.executeQuery();

	        if (rs.next()) {
	            text_idRoom.setText(rs.getString("id_room"));
	        } else {
	            text_idRoom.setText("");
	        }

	        rs.close();
	        pst.close();
	        konfigurasi.con.close();

	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(this, "Gagal mengambil ID Room: " + e.getMessage());
	    }
	}
	
	
	
	private void hitungTotalKaraoke() {
	    try {
	        if (comboBox_hrgPerJam_PaketKaraoke.getSelectedItem() == null) return;
	        if (txt_Durasi_Karaoke.getText().isEmpty()) return;

	        // HAPUS TITIK RIBUAN
	        String hargaStr = comboBox_hrgPerJam_PaketKaraoke
	                .getSelectedItem().toString()
	                .replace(".", "");

	        double hargaPerJam = Double.parseDouble(hargaStr);
	        double durasi = Double.parseDouble(txt_Durasi_Karaoke.getText());

	        totalKaraoke = hargaPerJam * durasi; // ISI VARIABEL CLASS
	        txt_HrgTot_Karaoke.setText(formatRupiah(totalKaraoke));
	        hitungTotalPembayaran();
	    } 
	    catch (Exception e) {
	        txt_HrgTot_Karaoke.setText("");
	    }
	   
	}

	
	private String formatRupiah(double angka) {
	    java.text.DecimalFormat df = new java.text.DecimalFormat("#,###");
	    return df.format(angka).replace(",", ".");
	}
	
	
	
	private void cariMemberByNoTelp() {
	    String noTelp = txt_NoTel_Member.getText().trim();

	   
	    if (noTelp.length() < 5) {
	        txt_NamaPelanggan.setText("");
	        return;
	    }

	    try {
	        konfigurasi.KoneksiKeServer();

	        String sql = "SELECT nama_member FROM member WHERE noTelp_member = ?";
	        PreparedStatement pst = konfigurasi.con.prepareStatement(sql);
	        pst.setString(1, noTelp);

	        ResultSet rs = pst.executeQuery();

	        if (rs.next()) {
	            
	            txt_NamaPelanggan.setText(rs.getString("nama_member"));
	        } else {
	          
	            txt_NamaPelanggan.setText("");
	        }

	        rs.close();
	        pst.close();
	        konfigurasi.con.close();

	    } catch (Exception e) {
	        System.out.println("Error cari member: " + e.getMessage());
	    }
	}
	
	private void setTanggalOtomatis() {
	  
	    java.util.Date sekarang = new java.util.Date();
	    
	   
	    java.text.SimpleDateFormat formatDb = new java.text.SimpleDateFormat("yyyy-MM-dd");
	    
	   
	    String tanggalString = formatDb.format(sekarang);
	    
	    txt_TglRes_Pesanan.setText(tanggalString);
	    
	    txt_TglRes_Pesanan.setEditable(false);
	}
	
	
	private String generateIdPesanan() {
	    String tanggal = new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date());
	    int urutan = 1; // Default urutan pertama jika belum ada data hari ini

	    try {
	        konfigurasi.KoneksiKeServer();
	        
	        // AMBIL ID TERAKHIR HARI INI
	        String sql = "SELECT id_pesanan FROM reservasi_pemesananfnb " +
	                     "WHERE id_pesanan LIKE ? " +
	                     "ORDER BY id_pesanan DESC LIMIT 1";
	                     
	        PreparedStatement pst = konfigurasi.con.prepareStatement(sql);
	        pst.setString(1, "RSV-" + tanggal + "%");

	        ResultSet rs = pst.executeQuery();
	        
	        if (rs.next()) {
	            // Contoh ID: RSV-20250104-005
	            String idTerakhir = rs.getString("id_pesanan");
	            
	            // Ambil 3 digit terakhir (substring dari belakang)
	            String urutanStr = idTerakhir.substring(idTerakhir.length() - 3);
	            
	            // Ubah ke int dan tambah 1
	            urutan = Integer.parseInt(urutanStr) + 1;
	        }

	        rs.close();
	        pst.close();
	        konfigurasi.con.close();
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    // Format menjadi 3 digit (contoh: 001, 010, 100)
	    return "RSV-" + tanggal + "-" + String.format("%03d", urutan);
	}
	
	public void loadTableFnB(String idPesanan) {
	    try {
	        konfigurasi.KoneksiKeServer();
	        String sql = "SELECT * FROM pemesanan_fnb WHERE id_pesanan = ?";
	        PreparedStatement pst = konfigurasi.con.prepareStatement(sql);
	        pst.setString(1, idPesanan);
	        ResultSet rs = pst.executeQuery();

	        DefaultTableModel model = (DefaultTableModel) table_Orderan_FnB.getModel();
	        model.setRowCount(0);

	        while (rs.next()) {
	            model.addRow(new Object[]{
	                rs.getString("nama_pesanan"),
	                rs.getString("id_menu"),
	                rs.getDouble("hrg_unitMenu"),
	                rs.getInt("kuantitas"),
	                rs.getDouble("hrg_totMenu"),
	                rs.getString("ket")
	            });
	        }

	        rs.close();
	        pst.close();
	        konfigurasi.con.close();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void hitungTotalPesananFnB(String idPesanan) {
	    try {
	        konfigurasi.KoneksiKeServer();

	        String sql = "SELECT IFNULL(SUM(hrg_totMenu),0) AS hrg_tot_pemesananFnB "
	                   + "FROM pemesanan_fnb "
	                   + "WHERE id_pesanan = ?";

	        PreparedStatement ps = konfigurasi.con.prepareStatement(sql);
	        ps.setString(1, txt_Id_Pesanan.getText());

	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            totalFnB = rs.getDouble("hrg_tot_pemesananFnB");
	            txt_HrgTot_PesananFnB.setText(formatRupiah(totalFnB));
	        }

	        hitungTotalPembayaran();

	    } catch (Exception e) {
	        totalFnB = 0;
	        txt_HrgTot_PesananFnB.setText("0");
	    }
	}
	
	private void hitungTotalPembayaran() {
	    double totalPembayaran = totalKaraoke + totalFnB;
	    txt_TotPembayaran_ResKaraoke_FnB.setText(
	        formatRupiah(totalPembayaran)
	    );
	}
	
	private void loadKasirLogin() {
	    try {
	        konfigurasi.KoneksiKeServer();

	        String sql = "SELECT id_user, nama_user FROM user WHERE id_user = ?";
	        PreparedStatement ps = konfigurasi.con.prepareStatement(sql);
	        ps.setString(1, konfigurasi.idUserLogin);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            textIdKasir.setText(rs.getString("id_user"));
	            txt_NamaKasir.setText(rs.getString("nama_user"));
	        } else {
	            textIdKasir.setText(konfigurasi.idUserLogin);
	            txt_NamaKasir.setText(konfigurasi.namaUserLogin);
	        }

	        textIdKasir.setEditable(false);
	        txt_NamaKasir.setEditable(false);

	        rs.close();
	        ps.close();
	        konfigurasi.con.close();

	    } catch (Exception e) {
	        textIdKasir.setText(konfigurasi.idUserLogin);
	        txt_NamaKasir.setText(konfigurasi.namaUserLogin);
	    }
	}
	
	
	private void cetakStrukPembayaran() {
        if (txt_Id_Pesanan.getText().isEmpty()) return;

        java.awt.print.PrinterJob pj = java.awt.print.PrinterJob.getPrinterJob();
        
        java.awt.print.PageFormat pf = pj.defaultPage();
        java.awt.print.Paper paper = pf.getPaper();
        paper.setImageableArea(5, 5, paper.getWidth() - 10, paper.getHeight() - 10);
        pf.setPaper(paper);

        
        double bayar = 0;
        double kembalian = 0;
        String metode = rdbtnTunai.isSelected() ? "Tunai" : "Non Tunai";
        
        try {
            if (rdbtnTunai.isSelected()) {
                String byrStr = txtCash.getText().replace(".", "");
                String kmbStr = txtKembalian.getText().replace(".", "");
                bayar = Double.parseDouble(byrStr.isEmpty() ? "0" : byrStr);
                kembalian = Double.parseDouble(kmbStr.isEmpty() ? "0" : kmbStr);
            } else {
                bayar = totalKaraoke + totalFnB;
                kembalian = 0;
            }
        } catch (Exception e) {
            bayar = 0; kembalian = 0;
        }

        
        pj.setPrintable(new StrukPembayaran(
            txt_Id_Pesanan.getText(),
            txt_NamaKasir.getText(),
            txt_TglRes_Pesanan.getText(),
            txt_NamaPelanggan.getText(),
            comboBox_NamaRoom.getSelectedItem().toString(),
            comboBox_Nama_PaketKaraoke.getSelectedItem().toString(),
            comboBox_hrgPerJam_PaketKaraoke.getSelectedItem().toString(), 
            txt_Durasi_Karaoke.getText(),
            totalKaraoke,
            totalFnB,
            totalKaraoke + totalFnB,
            bayar,
            kembalian,
            metode,
            table_Orderan_FnB.getModel()
        ), pf);

        if (pj.printDialog()) {
            try {
                pj.print();
            } catch (java.awt.print.PrinterException e) {
                JOptionPane.showMessageDialog(this, "Gagal mencetak: " + e.getMessage());
            }
        }
    }
    
    private void hitungKembalian() {
        try {
            // 1. Ambil Total Tagihan (Hapus titik ribuan)
            String tagihanStr = txt_TotPembayaran_ResKaraoke_FnB.getText().replace(".", "");
            if (tagihanStr.isEmpty()) tagihanStr = "0";
            double tagihan = Double.parseDouble(tagihanStr);

            // 2. Ambil Uang Bayar (Hapus titik ribuan jika ngetik titik)
            String bayarStr = txtCash.getText().replace(".", "");
            if (bayarStr.isEmpty()) bayarStr = "0";
            double bayar = Double.parseDouble(bayarStr);

            // 3. Hitung Selisih
            double kembalian = bayar - tagihan;

            // 4. Tampilkan (Format Rupiah)
            txtKembalian.setText(formatRupiah(kembalian));
            
            // Ubah warna jika uang kurang
            if (kembalian < 0) {
                txtKembalian.setForeground(Color.RED);
            } else {
                txtKembalian.setForeground(Color.BLACK);
            }

        } catch (Exception e) {
            // Jika user input huruf, biarkan 0 atau error handling silent
        }
    }
    
 // Method untuk mengecek apakah ID Pesanan sudah masuk database reservasi (Header)
    private boolean cekApakahIdAdaDiDatabase(String id) {
        boolean ada = false;
        try {
            konfigurasi.KoneksiKeServer();
            
            String sql = "SELECT id_pesanan FROM reservasi_pemesananfnb WHERE id_pesanan = ?";
            PreparedStatement ps = konfigurasi.con.prepareStatement(sql);
            ps.setString(1, id);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ada = true; // Data ditemukan
            }
            
            rs.close();
            ps.close();
            konfigurasi.con.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ada;
    }
    
    
 // Method untuk menghapus sampah F&B jika Reservasi dibatalkan
    private void hapusSemuaFnB(String idPesanan) {
        try {
            konfigurasi.KoneksiKeServer();
            
            // Hapus semua F&B yang punya ID Pesanan tersebut
            String sql = "DELETE FROM pemesanan_fnb WHERE id_pesanan = ?";
            PreparedStatement ps = konfigurasi.con.prepareStatement(sql);
            ps.setString(1, idPesanan);
            
            ps.executeUpdate();
            
            ps.close();
            konfigurasi.con.close();
            
            System.out.println("Data F&B sampah untuk ID " + idPesanan + " berhasil dibersihkan.");
            
        } catch (Exception e) {
            System.out.println("Gagal membersihkan data F&B: " + e.getMessage());
        }
    }
}


class StrukPembayaran implements Printable {
    
    String idPesanan, namaKasir, tgl, namaPelanggan;
    String room, paket, hargaPerJam, durasi;
    double totalKaraoke, totalFnB, grandTotal;
    double bayar, kembalian;
    String metodeBayar; 
    
    // Variabel untuk menyimpan Waktu Cetak Realtime
    String waktuCetak;
    
    javax.swing.table.TableModel modelFnB;

    public StrukPembayaran(String id, String ksr, String t, String plg, 
                      String rm, String pkt, String hrgJam, String dur,
                      double totK, double totF, double gTot,
                      double byr, double kmbl, String mtd,
                      javax.swing.table.TableModel mod) {
        this.idPesanan = id;
        this.namaKasir = ksr;
        this.tgl = t;
        this.namaPelanggan = plg;
        this.room = rm;
        this.paket = pkt;
        this.hargaPerJam = hrgJam;
        this.durasi = dur;
        this.totalKaraoke = totK;
        this.totalFnB = totF;
        this.grandTotal = gTot;
        this.bayar = byr;
        this.kembalian = kmbl;
        this.metodeBayar = mtd;
        this.modelFnB = mod;
        
        // jam otomatis ambild ari laptop saat ini
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        this.waktuCetak = sdf.format(new java.util.Date());
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex > 0) return NO_SUCH_PAGE;

        Graphics2D g2d = (Graphics2D) graphics;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

        // Font Settings
        Font fontHeader = new Font("Monospaced", Font.BOLD, 12);
        Font fontRegular = new Font("Monospaced", Font.PLAIN, 10);
        
        int y = 20; 
        int x = 10; 

        // header
        g2d.setFont(fontHeader);
        g2d.drawString("KARAOKE MELODY BOX", x, y); y += 15;
        g2d.setFont(fontRegular);
        g2d.drawString("Jl. Raya Padang Luwih, Dalung", x, y); y += 17;
        

        // info transaksi
        g2d.drawString(waktuCetak, x, y); y += 12; 
        g2d.drawString("ID    : " + idPesanan, x, y); y += 12;
        g2d.drawString("Kasir : " + namaKasir, x, y); y += 12;
        g2d.drawString("Plgn  : " + namaPelanggan, x, y); y += 15;
        g2d.drawString("--------------------------------", x, y); y += 15;

        // karaoke
        g2d.setFont(fontHeader);
        g2d.drawString("KARAOKE", x, y); y += 15;
        g2d.setFont(fontRegular);
        g2d.drawString("Room    : " + room, x, y); y += 12;
        g2d.drawString("Paket   : " + paket, x, y); y += 12;
        g2d.drawString("Hrg/Jam : Rp " + hargaPerJam, x, y); y += 12;
        g2d.drawString("Durasi  : " + durasi + " Jam", x, y); y += 12;
        g2d.drawString("Subtotal: Rp " + String.format("%,.0f", totalKaraoke), x, y); y += 20;

        // fnb
        if (modelFnB.getRowCount() > 0) {
            g2d.setFont(fontHeader);
            g2d.drawString("FOOD & BEVERAGES", x, y); y += 15;
            g2d.setFont(fontRegular);
            
            g2d.drawString("Item          Qty     Total", x, y); y += 10;
            
            for (int i = 0; i < modelFnB.getRowCount(); i++) {
                String nama = modelFnB.getValueAt(i, 0).toString();
                String qty = modelFnB.getValueAt(i, 3).toString();
                String tot = modelFnB.getValueAt(i, 4).toString(); 

                if (nama.length() > 12) nama = nama.substring(0, 12);
                
                String baris = String.format("%-13s  x%-3s %10s", nama, qty, tot);
                g2d.drawString(baris, x, y); 
                y += 12;
            }
            g2d.drawString("Subtotal: Rp " + String.format("%,.0f", totalFnB), x, y); y += 20;
        }

        g2d.drawString("--------------------------------", x, y); y += 15;
        
        // total pembayaran
        g2d.setFont(fontHeader);
        g2d.drawString("TOTAL    : Rp " + String.format("%,.0f", grandTotal), x, y); y += 15; 
        
        g2d.setFont(fontRegular);
        g2d.drawString("Metode   : " + metodeBayar, x, y); y += 12;
        g2d.drawString("Bayar    : Rp " + String.format("%,.0f", bayar), x, y); y += 12;
        g2d.drawString("Kembali  : Rp " + String.format("%,.0f", kembalian), x, y); y += 25;
        

        g2d.drawString("--------------------------------", x, y); y += 15;
        

        g2d.drawString("Terima Kasih Kunjungannya!   ", x, y); y += 12;
        g2d.drawString("Kritik/saran(Tlp/WA) : 085904363329      ", x, y);

        return PAGE_EXISTS;
    }
}