package RESERVASI_KARAOKE_MelodyBox;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.net.URL; 

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class loginPage extends JDialog {

    private static final long serialVersionUID = 1L;

    // --- PANEL KHUSUS BACKGROUND ---
    private final JPanel contentPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            // 1. Cek Lokasi Gambar (Anti-Crash)
            // Pastikan folder 'img' ada di dalam 'src' sejajar dengan folder package
            URL url = getClass().getResource("/img/login_bg.png");
            
            if (url != null) {
                // Jika gambar ketemu, tampilkan
                ImageIcon icon = new ImageIcon(url);
                Image img = icon.getImage();
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            } else {
                // Jika gambar TIDAK ketemu, pakai warna polos agar tidak error
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(0, 0, getWidth(), getHeight());
                System.err.println("ERROR: Gambar 'login_bg.png' tidak ditemukan di folder /src/img/");
            }
        }
    };

    private JTextField txtUsername;
    private JPasswordField passwordField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            loginPage dialog = new loginPage();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public loginPage() {
        setTitle("Login MelodyBox");
        setBounds(100, 100, 500, 273);
        getContentPane().setLayout(null);

        contentPanel.setBounds(0, 0, 484, 229);
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel);
        contentPanel.setLayout(null);

        // --- LABEL USERNAME ---
        JLabel lblUsername = new JLabel("Username :");
        // Mengubah warna teks menjadi #364fab
        lblUsername.setForeground(Color.decode("#364fab")); 
        lblUsername.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblUsername.setBounds(97, 103, 100, 20);
        contentPanel.add(lblUsername);

        // --- LABEL PASSWORD ---
        JLabel lblPassword = new JLabel("Password :");
        // Mengubah warna teks menjadi #364fab
        lblPassword.setForeground(Color.decode("#364fab"));
        lblPassword.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblPassword.setBounds(97, 143, 100, 20);
        contentPanel.add(lblPassword);

        // --- INPUT TEXT ---
        txtUsername = new JTextField();
        txtUsername.setBounds(207, 103, 200, 25);
        contentPanel.add(txtUsername);
        txtUsername.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setBounds(207, 143, 200, 25);
        contentPanel.add(passwordField);

        // --- PANEL TOMBOL ---
        JPanel buttonPane = new JPanel();
        buttonPane.setOpaque(false); // Transparan agar background terlihat
        buttonPane.setBounds(0, 180, 484, 50);
        contentPanel.add(buttonPane);
        buttonPane.setLayout(null);

        {
            JButton okButton = new JButton("Login");
            okButton.setFont(new Font("Tahoma", Font.BOLD, 11));
            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Panggil koneksi database
                    konfigurasi.KoneksiKeServer();

                    String role = CekLogin(
                        txtUsername.getText(), 
                        new String(passwordField.getPassword())
                    );

                    if (role != null) {
                        konfigurasi.statusLogin = true;
                        dispose(); // Tutup login

                        // Buka halaman sesuai role
                        switch (role) {
                            case "kasir":
                                new mainPage_kasir().setVisible(true);
                                break;
                            case "admin":
                                new mainPage_admin().setVisible(true);
                                break;
                            case "manager":
                                new mainPage_manager().setVisible(true);
                                break;
                        }

                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Username atau Password masih Salah");
                    }
                }
            });
            okButton.setBounds(283, 18, 80, 23);
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
            cancelButton.setBounds(383, 18, 80, 23);
            cancelButton.setActionCommand("Cancel");
            buttonPane.add(cancelButton);
        }
    }

    public String CekLogin(String username, String password) {
        String role = null;
        try {
          
            String sql = "SELECT id_user, nama_user, role FROM user WHERE username=? AND BINARY password=?";
            PreparedStatement ps = konfigurasi.con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                konfigurasi.idUserLogin = rs.getString("id_user");
                konfigurasi.namaUserLogin = rs.getString("nama_user");
                return rs.getString("role");
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return role;
    }
}