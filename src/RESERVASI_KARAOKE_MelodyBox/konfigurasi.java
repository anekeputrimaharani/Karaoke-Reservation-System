package RESERVASI_KARAOKE_MelodyBox;

import java.sql.*;

public class konfigurasi {
	public static Connection con;
	public static Statement stmt;
	public static ResultSet rs;
	public static boolean statusLogin = false;
    public static String idUserLogin = ""; 
    public static String namaUserLogin = "";
    public static void KoneksiKeServer() {
    	
    	try{  
    		Class.forName("com.mysql.jdbc.Driver");  
    		con=DriverManager.getConnection( 
    				// disesuaikan dengan database kita
    				//reservasiKaraoke = databasenya
    		"jdbc:mysql://localhost:3306/reservasiKaraoke","root","");  

    		System.out.println("Sukses koneksi server");
    		}catch(Exception e){ 
    			System.out.println("Gagal Koneksi Server");
    			System.out.println(e);
    		}  
    		} 
	
}
