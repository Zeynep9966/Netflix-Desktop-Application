package Project3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;


public class MainMenu extends javax.swing.JFrame {

    DefaultTableModel model;
    Operations operations = new Operations();
    int x = 0, y=0;
    LocalTime local;
    Date date2;
    java.sql.Time time;
    LocalDate date1;
    ResultSet rs= null;
    PreparedStatement ps = null;
    Connection con = null;
    String cc=null;
    
    
    public void updateYap(){
        try {
            con = (Connection) DriverManager.getConnection("jdbc:mysql://"+Database.host+":"+Database.port+"/"+Database.db_name, Database.id, Database.password);
        } catch (SQLException ex) {
            Logger.getLogger(RegisterPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            //girilen bilgileri user_program tablosuna ekle!!
            String sorgu = "UPDATE user_program SET user_id=?,izleme_tarihi=?,izleme_suresi=?,kalinan_bolum=?,verilen_puan=? WHERE program_ad=?";
            ps = (PreparedStatement) con.prepareStatement(sorgu);
            //sorgudaki soru işaretleri yerine aşağıdaki komutlarla değişkenleri aktar.  
            
            //giriş yapan adminin id si alınamadı 
            ps.setInt(1, 1);
            ps.setString(6, ad_label.getText());
            
            program_ad_label.setText(ad_label.getText());
            
            java.sql.Date date = null;
            DateFormat formatt = new SimpleDateFormat("dd/MM/yyyy");
            date = java.sql.Date.valueOf(date1);
            
            //ekrana yazdırırken kullanabilirsin
            cc= formatt.format(date);
            //System.out.println(cc);
            izleme_tarihi_label.setText("İzleme Tarihi: "+cc);
            
            ps.setDate(2, date);
            ps.setTime(3, time);
            izleme_suresi_label.setText("İzleme Süresi: "+time.toString());
            
            String bolum = bolum_sayisi_label.getText();
            
            int bolum_sayisi = Integer.parseInt(bolum);
            Object xxx = bolum_sayisi-1;
            kalan_bolum_label.setText("Kalan Bölüm: "+xxx.toString());
            
            ps.setInt(4, bolum_sayisi);
            if(puan_field.getText().equals(""))
            {
                 ps.setString(5, "0");
                 verilen_puan_label.setText("Verilen puan: ");
                 
            }else{
                ps.setString(5, puan_field.getText());
                verilen_puan_label.setText("Verilen puan: "+puan_field.getText());
            }
            if(ps.executeUpdate() != 0)
            {
                //eklendi
                message.setText("tablo güncellendi");
            }
            else{
                message.setText("error!");
            }

        } catch (SQLException ex) {
            Logger.getLogger(RegisterPanel.class.getName()).log(Level.SEVERE, null, ex);
            message.setText("Couldn't Update!");
        }
        
    }
    
    public void izleme_bilgisiAl(){
        
        try {
            con = (Connection) DriverManager.getConnection("jdbc:mysql://"+Database.host+":"+Database.port+"/"+Database.db_name, Database.id, Database.password);
        } catch (SQLException ex) {
            Logger.getLogger(RegisterPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            //girilen bilgileri user_program tablosuna ekle!!
            String sorgu = "INSERT INTO user_program(user_id, program_ad, izleme_tarihi, izleme_suresi, kalinan_bolum, verilen_puan) VALUES (?,?,?,?,?,?)";
            ps = (PreparedStatement) con.prepareStatement(sorgu);
            //sorgudaki soru işaretleri yerine aşağıdaki komutlarla değişkenleri aktar.  
            
            //giriş yapan adminin id si alınamadı 
            ps.setInt(1, 1);
            ps.setString(2, ad_label.getText());
            
            program_ad_label.setText(ad_label.getText());
            
            java.sql.Date date = null;
            DateFormat formatt = new SimpleDateFormat("dd/MM/yyyy");
            date = java.sql.Date.valueOf(date1);
            
            //ekrana yazdırırken kullanabilirsin
            cc= formatt.format(date);
            //System.out.println(cc);
            izleme_tarihi_label.setText("İzleme Tarihi: "+cc);
            
            ps.setDate(3, date);
            ps.setTime(4, time);
            izleme_suresi_label.setText("İzleme Süresi: "+time.toString());
            
            String bolum = bolum_sayisi_label.getText();
            
            int bolum_sayisi = Integer.parseInt(bolum);
            Object xxx = bolum_sayisi-1;
            kalan_bolum_label.setText("Kalan Bölüm: "+xxx.toString());
            
            ps.setInt(5, bolum_sayisi);
            if(puan_field.getText().equals(""))
            {
                 ps.setString(6, "0");
                 verilen_puan_label.setText("Verilen puan: ");
                 
            }else{
                ps.setString(6, puan_field.getText());
                verilen_puan_label.setText("Verilen puan: "+puan_field.getText());
            }
            if(ps.executeUpdate() != 0)
            {
                //eklendi
                message.setText("tabloya eklendi.");
            }
            else{
                message.setText("error!");
            }

        } catch (SQLException ex) {
            Logger.getLogger(RegisterPanel.class.getName()).log(Level.SEVERE, null, ex);
            message.setText("Couldn't add!");
        }
        
        
    }
    public MainMenu() {
        initComponents();
        
        // açılan sayfayı ortala
        this.setLocationRelativeTo(null);
        
        izleme_bilgisi_button.setVisible(false);
        puan_ver_button.setVisible(false);
        puan_field.setVisible(false);
        model = (DefaultTableModel) Table_movie.getModel();
        movieView();
    }
    
    public void movieView(){
        
        model.setRowCount(0);
        ArrayList <Movie> movies = new ArrayList<>();
        movies = operations.MovieCome();
        
        if(movies!=null){
            //moviesteki bilgileri mov a parçala
            for(Movie movi : movies){
                Object [] add = {movi.getFilm_dizi_ad(), movi.getFilm_dizi_tur(), movi.getFilm_dizi_mi(), movi.getBolum_sayisi(), movi.getFilm_dizi_puan()};
                model.addRow(add);
            }
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        jDialog1 = new javax.swing.JDialog();
        jCheckBoxMenuItem2 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem3 = new javax.swing.JCheckBoxMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jPanel1 = new javax.swing.JPanel();
        Search_field = new javax.swing.JTextField();
        search_button = new javax.swing.JButton();
        minimize = new javax.swing.JLabel();
        close = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        log_out = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tur_label = new javax.swing.JLabel();
        ad_label = new javax.swing.JLabel();
        film_dizi_mi_label = new javax.swing.JLabel();
        bolum_sayisi_label = new javax.swing.JLabel();
        program_uzunlugu_label = new javax.swing.JLabel();
        izle_buton = new javax.swing.JButton();
        durdur_buton = new javax.swing.JButton();
        puan_ver_button = new javax.swing.JButton();
        puan_field = new javax.swing.JTextField();
        izleme_bilgisi_button = new javax.swing.JButton();
        message = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        imdb_label = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Table_movie = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        program_ad_label = new javax.swing.JLabel();
        izleme_tarihi_label = new javax.swing.JLabel();
        izleme_suresi_label = new javax.swing.JLabel();
        kalan_bolum_label = new javax.swing.JLabel();
        verilen_puan_label = new javax.swing.JLabel();

        jMenu1.setText("jMenu1");

        jMenuItem1.setText("jMenuItem1");

        jMenuItem2.setText("jMenuItem2");

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        jRadioButtonMenuItem1.setSelected(true);
        jRadioButtonMenuItem1.setText("jRadioButtonMenuItem1");

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jCheckBoxMenuItem2.setSelected(true);
        jCheckBoxMenuItem2.setText("jCheckBoxMenuItem2");

        jCheckBoxMenuItem3.setSelected(true);
        jCheckBoxMenuItem3.setText("jCheckBoxMenuItem3");

        jMenu2.setText("jMenu2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        Search_field.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        Search_field.setForeground(new java.awt.Color(51, 51, 51));

        search_button.setBackground(new java.awt.Color(153, 0, 0));
        search_button.setForeground(new java.awt.Color(255, 255, 255));
        search_button.setText("Search");
        search_button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        search_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_buttonActionPerformed(evt);
            }
        });

        minimize.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        minimize.setForeground(new java.awt.Color(255, 255, 255));
        minimize.setText("-");
        minimize.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        minimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minimizeMouseClicked(evt);
            }
        });

        close.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        close.setForeground(new java.awt.Color(255, 255, 255));
        close.setText("X");
        close.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeMouseClicked(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial Unicode MS", 1, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 0, 0));
        jLabel2.setText("NETFLİX");

        log_out.setBackground(new java.awt.Color(153, 0, 0));
        log_out.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        log_out.setForeground(new java.awt.Color(255, 255, 255));
        log_out.setText("Log Out");
        log_out.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        log_out.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                log_outActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Seçilen Program");

        tur_label.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        tur_label.setForeground(new java.awt.Color(255, 255, 255));

        ad_label.setBackground(new java.awt.Color(255, 255, 255));
        ad_label.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        ad_label.setForeground(new java.awt.Color(255, 255, 255));

        film_dizi_mi_label.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        film_dizi_mi_label.setForeground(new java.awt.Color(255, 255, 255));

        bolum_sayisi_label.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        bolum_sayisi_label.setForeground(new java.awt.Color(255, 255, 255));

        program_uzunlugu_label.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        program_uzunlugu_label.setForeground(new java.awt.Color(255, 255, 255));

        izle_buton.setBackground(new java.awt.Color(102, 0, 0));
        izle_buton.setForeground(new java.awt.Color(255, 255, 255));
        izle_buton.setText("İZLE");
        izle_buton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        izle_buton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                izle_butonActionPerformed(evt);
            }
        });

        durdur_buton.setBackground(new java.awt.Color(102, 0, 0));
        durdur_buton.setForeground(new java.awt.Color(255, 255, 255));
        durdur_buton.setText("DURDUR");
        durdur_buton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        durdur_buton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                durdur_butonActionPerformed(evt);
            }
        });

        puan_ver_button.setBackground(new java.awt.Color(0, 0, 51));
        puan_ver_button.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        puan_ver_button.setForeground(new java.awt.Color(255, 255, 255));
        puan_ver_button.setText("Puan Ver");
        puan_ver_button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        puan_ver_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                puan_ver_buttonActionPerformed(evt);
            }
        });

        izleme_bilgisi_button.setBackground(new java.awt.Color(51, 0, 0));
        izleme_bilgisi_button.setForeground(new java.awt.Color(255, 255, 255));
        izleme_bilgisi_button.setText("İzleme Bilgisi");
        izleme_bilgisi_button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        izleme_bilgisi_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                izleme_bilgisi_buttonActionPerformed(evt);
            }
        });

        message.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        message.setForeground(new java.awt.Color(255, 255, 255));
        message.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Adı:");

        imdb_label.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        imdb_label.setForeground(new java.awt.Color(255, 255, 255));
        imdb_label.setToolTipText("");

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Türü:");

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Tipi:");

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Bölüm Sayısı:");

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Program Uzunluğu:");

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("IMDB:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(imdb_label, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(ad_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(film_dizi_mi_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
                                    .addComponent(bolum_sayisi_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(tur_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(program_uzunlugu_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(puan_field, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(64, 64, 64)
                                .addComponent(message, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(puan_ver_button)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(izleme_bilgisi_button, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(izle_buton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(durdur_buton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ad_label, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tur_label, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(film_dizi_mi_label, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(bolum_sayisi_label, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(program_uzunlugu_label, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(imdb_label, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(izle_buton, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(puan_field, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(message, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(puan_ver_button, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(durdur_buton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(izleme_bilgisi_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        Table_movie.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Film Adı", "Film Türü", "Film/Dizi?", "Bölüm Sayısı", "IMDB"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Table_movie.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_movieMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(Table_movie);

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(51, 0, 0));

        jLabel10.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Program İzleme Bilgisi");
        jLabel10.setVerifyInputWhenFocusTarget(false);

        program_ad_label.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        program_ad_label.setForeground(new java.awt.Color(255, 255, 255));
        program_ad_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        program_ad_label.setToolTipText("");

        izleme_tarihi_label.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        izleme_tarihi_label.setForeground(new java.awt.Color(255, 255, 255));
        izleme_tarihi_label.setToolTipText("");

        izleme_suresi_label.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        izleme_suresi_label.setForeground(new java.awt.Color(255, 255, 255));
        izleme_suresi_label.setToolTipText("");

        kalan_bolum_label.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        kalan_bolum_label.setForeground(new java.awt.Color(255, 255, 255));
        kalan_bolum_label.setToolTipText("");

        verilen_puan_label.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        verilen_puan_label.setForeground(new java.awt.Color(255, 255, 255));
        verilen_puan_label.setToolTipText("");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(kalan_bolum_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(izleme_suresi_label, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(502, 502, 502))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(188, 188, 188)
                        .addComponent(program_ad_label, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(verilen_puan_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                            .addComponent(izleme_tarihi_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(verilen_puan_label, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(izleme_tarihi_label, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(izleme_suresi_label, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(56, 56, 56))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(program_ad_label, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addComponent(kalan_bolum_label, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 767, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(minimize, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(close, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(log_out, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(search_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(57, 57, 57)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Search_field, javax.swing.GroupLayout.PREFERRED_SIZE, 688, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(142, 142, 142))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(minimize, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(close, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(log_out, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Search_field, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(search_button, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1350, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void minimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizeMouseClicked

        //basınca küçült
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_minimizeMouseClicked

    private void closeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeMouseClicked

        //basınca kapat
        System.exit(0);
    }//GEN-LAST:event_closeMouseClicked

    private void log_outActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_log_outActionPerformed
        
        //MainMenu kısmını kapat giriş kısmını aç
        this.setVisible(false);
        AdminPanel ad = new AdminPanel();
        ad.setVisible(true);
        
    }//GEN-LAST:event_log_outActionPerformed

    public void filter(String query){
        
        //girilen değeri filmlerde ara ve tabloda göster.
        TableRowSorter <DefaultTableModel> trs = new TableRowSorter<>(model);
        Table_movie.setRowSorter(trs);
        trs.setRowFilter(RowFilter.regexFilter(query));
        
        
    }
    
    private void search_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_buttonActionPerformed
        
        //aranan değeri al, tabloyu filtrele
        String query = Search_field.getText();
        filter(query);
        
    }//GEN-LAST:event_search_buttonActionPerformed

    private void Table_movieMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_movieMouseClicked
        
        x++; y++;
        message.setText("");
        izleme_bilgisi_button.setVisible(false);
        puan_field.setVisible(false);
        puan_ver_button.setVisible(false);
        //seçilen değeri al ve bilgilerini yandaki panele aktar      
        int selectedrow = Table_movie.getSelectedRow();
        Object bolum_sayisi = null;
        Object program_uzunlugu = null; 
        Object imdb = null;
        
        ad_label.setText(model.getValueAt(selectedrow, 0).toString());
        tur_label.setText(model.getValueAt(selectedrow, 1).toString());
        film_dizi_mi_label.setText(model.getValueAt(selectedrow, 2).toString());
        
        ArrayList<Movie> movies = operations.MovieCome();
        
        if(movies!=null){
            //moviesteki bilgileri mov a parçala
            for(Movie movi : movies){
                
                if(movi.getFilm_dizi_ad().equals(model.getValueAt(selectedrow, 0))){
                    //seçilen film adından listedeki yerini bulup integer değerleri OBJECT yap
                    bolum_sayisi = movi.getBolum_sayisi();
                    program_uzunlugu = movi.getProgram_uzunlugu();
                    imdb = movi.getFilm_dizi_puan();
                }
            }
        }
        
        bolum_sayisi_label.setText(bolum_sayisi.toString());
        program_uzunlugu_label.setText(program_uzunlugu.toString());
        imdb_label.setText(imdb.toString());
    }//GEN-LAST:event_Table_movieMouseClicked

    private void izle_butonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_izle_butonActionPerformed
        
         
        //puan ver butonu aktifleştirmek için tabloya basıp izleye basmalı.
        if(x!=0)
        {
            local = LocalTime.now();
            date1 = LocalDate.now();
            
            int gun = date1.getDayOfMonth();
            int ay = date1.getMonthValue();
            int yil = date1.getYear();
            LocalDate date = LocalDate.of(yil, ay, gun);
              
            
            puan_ver_button.setVisible(true);
            puan_field.setVisible(true);
        }
        else{
            puan_field.setVisible(false);
            puan_ver_button.setVisible(false);
            izleme_bilgisi_button.setVisible(false);
        }
        //tablodan başka bir film seçtiğini anlamak için
        x=0;
        y++;
        //izlerken tablodan seçim yapmasın
        Table_movie.setVisible(false);
        
    }//GEN-LAST:event_izle_butonActionPerformed

    private void durdur_butonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_durdur_butonActionPerformed
        
        Table_movie.setVisible(true);
        izleme_bilgisi_button.setVisible(true);
        
        LocalTime local_son = LocalTime.now();
        int saat, dakika, saniye;
        int saat1, dakika1, saniye1;
       
        saat = local_son.getHour();
        dakika =local_son.getMinute();
        saniye = local_son.getSecond();
        saat1 = local.getHour();
        dakika1 = local.getMinute();
        saniye1 = local.getSecond();
        
        LocalTime tim = LocalTime.of(Math.abs((saat1-saat)), Math.abs((dakika1-dakika)), Math.abs((saniye1-saniye)));
        
        time = Time.valueOf(tim);
        
    }//GEN-LAST:event_durdur_butonActionPerformed

    private void izleme_bilgisi_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_izleme_bilgisi_buttonActionPerformed
        
        
        try {
            con = (Connection) DriverManager.getConnection("jdbc:mysql://"+Database.host+":"+Database.port+"/"+Database.db_name, Database.id, Database.password);
        } catch (SQLException ex) {
            Logger.getLogger(RegisterPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        String program_ad = null;
        String query = "SELECT program_ad FROM user_program WHERE program_ad=?";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, ad_label.getText());
            ResultSet rs = ps.executeQuery();
            //System.out.println(rs);
            if(!rs.next())
            {
                //System.out.println(rs);
                izleme_bilgisiAl();
            }
            else{
                
                updateYap();
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("bilgiler çekilemedi.");
        }
        
        
        
       
        
    }//GEN-LAST:event_izleme_bilgisi_buttonActionPerformed

    private void puan_ver_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_puan_ver_buttonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_puan_ver_buttonActionPerformed

    public static void main(String args[]) {
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainMenu().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Search_field;
    private javax.swing.JTable Table_movie;
    private javax.swing.JLabel ad_label;
    private javax.swing.JLabel bolum_sayisi_label;
    private javax.swing.JLabel close;
    private javax.swing.JButton durdur_buton;
    private javax.swing.JLabel film_dizi_mi_label;
    private javax.swing.JLabel imdb_label;
    private javax.swing.JButton izle_buton;
    private javax.swing.JButton izleme_bilgisi_button;
    private javax.swing.JLabel izleme_suresi_label;
    private javax.swing.JLabel izleme_tarihi_label;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem2;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem3;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel kalan_bolum_label;
    private javax.swing.JButton log_out;
    private javax.swing.JLabel message;
    private javax.swing.JLabel minimize;
    private javax.swing.JLabel program_ad_label;
    private javax.swing.JLabel program_uzunlugu_label;
    private javax.swing.JTextField puan_field;
    private javax.swing.JButton puan_ver_button;
    private javax.swing.JButton search_button;
    private javax.swing.JLabel tur_label;
    private javax.swing.JLabel verilen_puan_label;
    // End of variables declaration//GEN-END:variables
}
