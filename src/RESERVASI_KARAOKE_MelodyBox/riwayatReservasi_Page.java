package RESERVASI_KARAOKE_MelodyBox;

import java.awt.EventQueue;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class riwayatReservasi_Page extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable tableRiwayatReservasi;
    private JTextField txtCariRiwayat;
    private String idKasirLogin; // Variabel global untuk menyimpan ID

    /**
     * Launch the application.
     
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // PERBAIKAN 1: Masukkan String palsu ("TEST") biar tidak merah
                    // Karena main method cuma buat testing run file ini sendirian.
                    riwayatReservasi_Page frame = new riwayatReservasi_Page("KASIR_TEST");
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
    public riwayatReservasi_Page(String idKasir) {
        // Simpan ID yang dikirim dari halaman Login/Main Menu
        this.idKasirLogin = idKasir; 
        
        setTitle("Riwayat Reservasi MelodyBox " + this.idKasirLogin);
        // Ganti jadi DISPOSE biar kalau di-close aplikasi utama gak mati
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setBounds(100, 100, 932, 556);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 70, 898, 438);
        contentPane.add(scrollPane);
        
        tableRiwayatReservasi = new JTable();
        // Setup Model Tabel Awal
        DefaultTableModel model = new DefaultTableModel(
	            new Object[][] {},
	            new String[] {
	            		"Id Kasir", "Id Pesanan", "Tanggal", "Nama Pelanggan", "Room", "Paket", "Total Bayar", "Metode", "Status"
	            }
	        ) {
	            // Method ini membuat tabel TIDAK BISA diedit secara langsung
	            @Override
	            public boolean isCellEditable(int row, int column) {
	                return false;
	            }
	        };
	        
	        tableRiwayatReservasi.setModel(new DefaultTableModel(
	        	new Object[][] {
	        	},
	        	new String[] {
	        		"Id Kasir", "Id Pesanan", "Tanggal", "Nama Pelanggan", "Room", "Paket", "Total Bayar", "Metode", "Status"
	        	}
	        ));
	        tableRiwayatReservasi.getColumnModel().getColumn(1).setPreferredWidth(143);
	        tableRiwayatReservasi.getColumnModel().getColumn(2).setPreferredWidth(106);
	        tableRiwayatReservasi.getColumnModel().getColumn(3).setPreferredWidth(99);
	        tableRiwayatReservasi.getColumnModel().getColumn(6).setPreferredWidth(106);
        
        scrollPane.setViewportView(tableRiwayatReservasi);
        
        JLabel lblNewLabel = new JLabel("Cari : ");
        lblNewLabel.setBounds(10, 42, 46, 14);
        contentPane.add(lblNewLabel);
        
        txtCariRiwayat = new JTextField();
        txtCariRiwayat .setText("Berdasarkan Nama pelanggan/id pesanan");
        txtCariRiwayat .setForeground(Color.GRAY); // Sekarang Color sudah dikenali

        txtCariRiwayat .addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if ( txtCariRiwayat .getText().equals("Berdasarkan Nama pelanggan/id pesanan")) {
                	 txtCariRiwayat .setText("");
                	 txtCariRiwayat .setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if ( txtCariRiwayat .getText().isEmpty()) {
                	 txtCariRiwayat .setForeground(Color.GRAY);
                	 txtCariRiwayat .setText("Berdasarkan Nama pelanggan/id pesanan");
                }
            }
        });
        txtCariRiwayat.setBounds(66, 39, 253, 20);
        contentPane.add(txtCariRiwayat);
        txtCariRiwayat.setColumns(10);
        
        // PERBAIKAN 3: Tambahkan Fungsi Pencarian Otomatis
        txtCariRiwayat.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                // Setiap tombol dilepas (selesai ngetik), panggil fungsi load
                String kataKunci = txtCariRiwayat.getText();
                loadRiwayat(kataKunci);
            }
        });
        
        JLabel lblJudul = new JLabel("Riwayat Reservasi yang Kamu Tangani");
        lblJudul.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblJudul.setBounds(10, 14, 330, 14);
        contentPane.add(lblJudul);

        // PERBAIKAN 2: Panggil method ini saat pertama kali buka
        // Kirim string kosong "" artinya tampilkan semua data tanpa filter nama
        loadRiwayat(""); 
    }
    
    // Method Load Data
    private void loadRiwayat(String keyword) {
        DefaultTableModel model = (DefaultTableModel) tableRiwayatReservasi.getModel();
        model.setRowCount(0); // Hapus data lama di tabel sebelum isi baru
        
        try {
            // Pastikan koneksi terbuka
            if (konfigurasi.con == null || konfigurasi.con.isClosed()) {
                konfigurasi.KoneksiKeServer();
            }
            
            // SQL QUERY:
            // Ambil data milik ID Kasir Login INI SAJA, Status harus Done/Canceled, DAN sesuai kata kunci pencarian
            String sql = "SELECT * FROM reservasi_pemesananfnb " +
                         "WHERE id_user = ? " +
                         "AND status_pesanan IN ('Done (Paid)', 'Canceled') " +
                         "AND (nama_pelanggan LIKE ? OR id_pesanan LIKE ?) " +
                         "ORDER BY tgl_reservasi DESC"; 
            
            PreparedStatement ps = konfigurasi.con.prepareStatement(sql);
            
            ps.setString(1, this.idKasirLogin); // Filter Kasir
            ps.setString(2, "%" + keyword + "%"); // Filter Nama (Search)
            ps.setString(3, "%" + keyword + "%"); // Filter ID Pesanan (Search)
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                Object[] row = {
                    rs.getString("id_user"),
                    rs.getString("id_pesanan"),
                    rs.getString("tgl_reservasi"),
                    rs.getString("nama_pelanggan"),
                    rs.getString("nama_room"),       
                    rs.getString("nama_paket"),      
                    formatRupiah(rs.getDouble("hrg_totPembayaran")), // Format Uang
                    rs.getString("metode_pembayaran"),
                    rs.getString("status_pesanan")
                };
                model.addRow(row);
            }
            
            rs.close();
            ps.close();
            // Jangan close konfigurasi.con kalau mau dipakai terus menerus, cukup close ps dan rs.
            
        } catch (Exception e) {
            // e.printStackTrace(); // Boleh dinyalakan untuk debug di console
            // JOptionPane.showMessageDialog(this, "Gagal load: " + e.getMessage());
        }
    }
    
    // Method helper buat format duit jadi Rp
    private String formatRupiah(double angka) {
        return String.format("Rp %,.0f", angka).replace(",", ".");
    }
}