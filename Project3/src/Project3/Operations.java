package Project3;

//import com.mysql.jdbc.Connection;         /* 
//import com.mysql.jdbc.Statement;           * MySQL ortamıyla denendi, vazgeçildi
//import com.mysql.jdbc.PreparedStatement;   */

import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.Date;

public class Operations {
    //İmport edilecekler
    static Connection con = null;
    static Statement sta = null;
    static PreparedStatement psta = null;
    public static String admin_id;
    
    public ArrayList <Admin> AdminCome()
    {
        ArrayList <Admin> list = new ArrayList<>();
        String sorgu = "select * from admin";
        try {
            sta = con.createStatement();
            //gelen değerleri tut
            ResultSet rs = sta.executeQuery(sorgu);
            //gelen değerlerin sonuna kadar git
            while(rs.next())
            {
                //tablodaki verileri değişkenlere atayıp liste oluştur.
                int admin_id = rs.getInt("admin_id");
                String id = rs.getString("id");
                String name = rs.getString("name");
                String password = rs.getString("password");
                
                list.add(new Admin(admin_id,id, name, password));
             
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Tablodaki veriler çekilemedi.");
            return null;
        }
        
    }
    
    public ArrayList <Movie> MovieCome()
    {
        ArrayList <Movie> list = new ArrayList<>();
        String sorgu = "select * from film_listesi";
        try {
            sta = con.createStatement();
            //gelen değerleri tut
            ResultSet rs = sta.executeQuery(sorgu);
            //gelen değerlerin sonuna kadar git
            while(rs.next())
            {
                //tablodaki verileri değişkenlere atayıp liste oluştur.
                int id = rs.getInt("film_dizi_id");
                String film_dizi_ad = rs.getString("film_dizi_ad");
                String film_dizi_tur = rs.getString("film_dizi_tur");
                String film_dizi_mi = rs.getString("film_dizi_mi");
                int bolum_sayisi = rs.getInt("bolum_sayisi");
                java.sql.Time program_uzunlugu = rs.getTime("program_uzunlugu");
                int puan = rs.getInt("film_dizi_puan");
                
                list.add(new Movie(id, film_dizi_ad, film_dizi_tur, film_dizi_mi, bolum_sayisi, program_uzunlugu, puan));
             
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Tablodaki veriler çekilemedi.");
            return null;
        }
        
    }
    public ArrayList <Movie> MovieComeRegister()
    {
        ArrayList <Movie> list = new ArrayList<>();
        //puana göre sırala
        String sorgu = "select * from film_listesi ORDER BY film_dizi_puan DESC";
        try {
            sta = con.createStatement();
            //gelen değerleri tut
            ResultSet rs = sta.executeQuery(sorgu);
            while(rs.next())
            {
                //tablodaki verileri değişkenlere atayıp liste oluştur.
                
                int id = rs.getInt("film_dizi_id");
                String film_dizi_ad = rs.getString("film_dizi_ad");
                String film_dizi_tur = rs.getString("film_dizi_tur");
                String film_dizi_mi = rs.getString("film_dizi_mi");
                int bolum_sayisi = rs.getInt("bolum_sayisi");
                int program_uzunlugu1 = rs.getInt("program_uzunlugu");
                java.sql.Time program_uzunlugu = rs.getTime("program_uzunlugu");
                int puan = rs.getInt("film_dizi_puan");
                
                list.add(new Movie(id, film_dizi_ad, film_dizi_tur, film_dizi_mi, bolum_sayisi, program_uzunlugu, puan));
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Tablodaki veriler çekilemedi.");
            return null;
        }
    }
    
    public boolean Login(String id, String password){
        
        String sorgu = "Select * from admin where id=? and password=?";
        try {
            //sorgudaki soru işarelerini alır
            psta = (PreparedStatement) con.prepareStatement(sorgu);
            //1. soru işareti yerine id, 2. soru işareti yerine passwordu atar.
            psta.setString(1, id);
            psta.setString(2, password);
            //gelecek sonucu tutmak için;
            ResultSet rs = psta.executeQuery();
            // admin tablosunun içerisinde varsa 
            
            return rs.next();
            
        } catch (SQLException ex) {
            Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
            //herhangi bir hata olursa 
            return false;
        }
    }
    
    public boolean Login2(String id){
        //kayıt olurken girilen e-posta kayıtlı ise true döndürür, false dönerse kayıt eder
        String sorgu = "Select * from admin where id=?";
        try {
            //sorgudaki soru işarelerini alır
            psta = (PreparedStatement) con.prepareStatement(sorgu);
            // soru işareti yerine id'yi atar.
            psta.setString(1, id);
            //gelecek sonucu tutmak için;
            ResultSet rs = psta.executeQuery();
            // admin tablosunun içerisinde varsa
            
            return rs.next();

        } catch (SQLException ex) {
            Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
            //herhangi bir hata olursa 
            return false;
        }
    }

    public Operations() {
        
        //mySQL bağlantısı (XAMPP ortamıyla bağlanıldı, XAMPP kurulu bir bilgisayarda Apache ve MySQL kısımlarının start denmesi
        //ve MySQL kısmındaki "Admin" yazısına tıklayıp açılan sayfada .sql.zip uzantılı dosyayı içe aktarırsanız bağlantı rahatça sağlanır.)
        //url->jdbc:mysql://host:port/db_name;id;password;                                  //türkçe karakter yap
        String url = "jdbc:mysql://"+Database.host+":"+Database.port+"/"+Database.db_name+"?useUnicode=true&characterEncoding=UTF-8";
        try {
            //mysql bağlantısını kur
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(url, Database.id, Database.password);
            System.out.println("Veritabanına başarıyla bağlandınız.");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Driver çalışmadı :( ");
        } catch (SQLException ex) {
            Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Bağlantı çalışamadı :( ");
        }
    }
    
    public static void main(String[] args) {
        
        Operations op = new Operations();
    }    
}