package RESERVASI_KARAOKE_MelodyBox;

import java.awt.EventQueue;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class dataMember extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table_DataMember;
    private JTextField txtCari_DataMember;
    
    /**

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                dataMember frame = new dataMember();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    */

    
    // Constructor
    public dataMember() {
        setTitle("Data Member MelodyBox");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 708, 526);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Tombol Add
        JButton btnNewButton = new JButton("Add");
        btnNewButton.addActionListener(e -> {
            formData_Member formMember = new formData_Member();
            formMember.setVisible(true);
            dispose();
        });
        btnNewButton.setBounds(20, 11, 88, 22);
        contentPane.add(btnNewButton);

        // Tombol Edit
        JButton btnEdit = new JButton("Edit");
        btnEdit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		int rowTerpilih = table_DataMember.getSelectedRow();
		        if (rowTerpilih == -1) {
		            JOptionPane.showMessageDialog(null, "Pilih data yang akan diedit!");
		            return;
		        }
							        String namaMember= 
							        		table_DataMember.getValueAt(rowTerpilih, 0).toString();
							        String tglRegistrasi = 
							        		table_DataMember.getValueAt(rowTerpilih, 1).toString();
							        String noTelp =
							        		table_DataMember.getValueAt(rowTerpilih, 2).toString();
							        String tglLahir =
							        		table_DataMember.getValueAt(rowTerpilih, 3).toString();
							        String statusMember =
							        		table_DataMember.getValueAt(rowTerpilih, 4).toString();
							        String tglExp =
							        		table_DataMember.getValueAt(rowTerpilih, 5).toString();
							        
							         
							        formData_Member form = new formData_Member(
							            namaMember,
							            tglRegistrasi,
							            noTelp,
							            tglLahir,
							            statusMember,
							            tglExp
							        );

							        form.setVisible(true);
							        dispose();
			}
        });
        btnEdit.setBounds(118, 11, 88, 22);
        contentPane.add(btnEdit);

        // Tombol Delete
        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int rowTerpilih = table_DataMember.getSelectedRow();
        	    
        	    if (rowTerpilih == -1) {
        	        JOptionPane.showMessageDialog(null, "Pilih baris yang ingin dihapus!");
        	        return;
        	    }
        	    // Ambil noTelp member dari baris yang dipilih
        	    String noTelp = table_DataMember.getValueAt(rowTerpilih, 2).toString();

        	    // Konfirmasi sebelum hapus
        	    int konfirmasi = JOptionPane.showConfirmDialog(
        	            null,
        	            "Apakah Anda yakin ingin menghapus member dengan No Telp " + noTelp + "?",
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

        	            String sql = "DELETE FROM member WHERE noTelp_member = ?";
        	            PreparedStatement ps = konfigurasi.con.prepareStatement(sql);
        	            ps.setString(1, noTelp);
        	            ps.executeUpdate();
        	            ps.close();

        	            // Refresh tabel
        	            loadData();

        	            JOptionPane.showMessageDialog(null, "Data member berhasil dihapus.");
        	        } catch (SQLException ex) {
        	            JOptionPane.showMessageDialog(null, "Terjadi error: " + ex.getMessage());
        	        }
        	  }
        	}
        });
        btnDelete.setBounds(216, 11, 88, 22);
        contentPane.add(btnDelete);

        // Label Cari
        JLabel lblNewLabel = new JLabel("Cari : ");
        lblNewLabel.setBounds(314, 15, 48, 14);
        contentPane.add(lblNewLabel);
        
        // CARI:
        txtCari_DataMember = new JTextField();
        txtCari_DataMember.setText("Berdasarkan Nama member/no telp");
        txtCari_DataMember.setForeground(Color.GRAY);

        txtCari_DataMember.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtCari_DataMember.getText().equals("Berdasarkan Nama member/no telp")) {
                	txtCari_DataMember.setText("");
                	txtCari_DataMember.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtCari_DataMember.getText().isEmpty()) {
                	txtCari_DataMember.setForeground(Color.GRAY);
                	txtCari_DataMember.setText("Berdasarkan Nama member/no telp");
                }
            }
        });
        txtCari_DataMember.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		String keyword = txtCari_DataMember.getText().trim();

        	    if (keyword.isEmpty()) {
        	        loadData(); 
        	        return;
        	    }

        	    DefaultTableModel model = (DefaultTableModel) table_DataMember.getModel();
        	    model.setRowCount(0);

        	    try {
        	        konfigurasi.KoneksiKeServer();
        	        if (konfigurasi.con == null) {
        	            JOptionPane.showMessageDialog(null, "Gagal koneksi database");
        	            return;
        	        }
        	        
        	        
          	        String sql = "SELECT * FROM member WHERE nama_member LIKE ? OR noTelp_member LIKE ?";
        	        PreparedStatement ps = konfigurasi.con.prepareStatement(sql);
        	        
        	        ps.setString(1, "%" + keyword + "%");
        	        ps.setString(2, "%" + keyword + "%");
        	        ResultSet rs = ps.executeQuery();

        	        while (rs.next()) {
        	            Object[] row = {
        	                rs.getString("nama_member"),
        	                rs.getString("tgl_regis_member"),
        	                rs.getString("noTelp_member"),
        	                rs.getString("tgl_lahir_member"),
        	                rs.getString("status_member"),
        	                rs.getString("tgl_exp_member")
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
        txtCari_DataMember.setBounds(355, 12, 329, 20);
        contentPane.add(txtCari_DataMember);
        txtCari_DataMember.setColumns(10);

        // ScrollPane dan JTable
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 56, 664, 422);
        contentPane.add(scrollPane);

        table_DataMember = new JTable();
        
        // MODIFIKASI DISINI: Tambahkan override isCellEditable
        DefaultTableModel model = new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "Nama", "Tanggal Registrasi", "No Telp", "Tanggal Lahir", "Status Member", "Tanggal Expired"
            }
        ) {
            // Method ini membuat tabel TIDAK BISA diedit secara langsung
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table_DataMember.setModel(model);

        // set lebar kolom
        table_DataMember.getColumnModel().getColumn(0).setPreferredWidth(94);
        table_DataMember.getColumnModel().getColumn(1).setPreferredWidth(104);
        table_DataMember.getColumnModel().getColumn(2).setPreferredWidth(102);
        table_DataMember.getColumnModel().getColumn(3).setPreferredWidth(85);
        table_DataMember.getColumnModel().getColumn(4).setPreferredWidth(88);
        table_DataMember.getColumnModel().getColumn(5).setPreferredWidth(90);

        scrollPane.setViewportView(table_DataMember);

        // load data setelah semua komponen dibuat
        loadData();
    }

    // METHOD UNTUK LOAD DATA DARI DATABASE
    public void loadData() {
        DefaultTableModel model = (DefaultTableModel) table_DataMember.getModel();
        model.setRowCount(0); // kosongkan tabel dulu

        try {
            konfigurasi.KoneksiKeServer();
            if (konfigurasi.con == null) {
                JOptionPane.showMessageDialog(this, "Gagal koneksi database");
                return;
            }

            String sql = "SELECT * FROM member ORDER BY tgl_regis_member DESC";
            PreparedStatement ps = konfigurasi.con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] row = {
                    rs.getString("nama_member"),
                    rs.getString("tgl_regis_member"),
                    rs.getString("noTelp_member"),
                    rs.getString("tgl_lahir_member"),
                    rs.getString("status_member"),
                    rs.getString("tgl_exp_member")
                };
                model.addRow(row);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal load data: " + e.getMessage());
        }
    }

}
