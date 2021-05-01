
package Project3;

public class Movie {
    
    private int id;
    private String film_dizi_ad;
    private String film_dizi_tur;
    private String film_dizi_mi;
    private int bolum_sayisi;
    private java.sql.Time program_uzunlugu;
    private int film_dizi_puan;

    public int getFilm_dizi_puan() {
        return film_dizi_puan;
    }

    public void setFilm_dizi_puan(int film_dizi_puan) {
        this.film_dizi_puan = film_dizi_puan;
    }
    
    public Movie(int id, String film_dizi_ad, String film_dizi_tur, String film_dizi_mi, int bolum_sayisi, java.sql.Time program_uzunlugu, int film_dizi_puan) {
        this.id = id;
        this.film_dizi_ad = film_dizi_ad;
        this.film_dizi_tur = film_dizi_tur;
        this.film_dizi_mi = film_dizi_mi;
        this.bolum_sayisi = bolum_sayisi;
        this.program_uzunlugu = program_uzunlugu;
        this.film_dizi_puan = film_dizi_puan;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilm_dizi_ad() {
        return film_dizi_ad;
    }

    public void setFilm_dizi_ad(String film_dizi_ad) {
        this.film_dizi_ad = film_dizi_ad;
    }

    public String getFilm_dizi_tur() {
        return film_dizi_tur;
    }

    public void setFilm_dizi_tur(String film_dizi_tur) {
        this.film_dizi_tur = film_dizi_tur;
    }

    public String getFilm_dizi_mi() {
        return film_dizi_mi;
    }

    public void setFilm_dizi_mi(String film_dizi_mi) {
        this.film_dizi_mi = film_dizi_mi;
    }

    public int getBolum_sayisi() {
        return bolum_sayisi;
    }

    public void setBolum_sayisi(int bolum_sayisi) {
        this.bolum_sayisi = bolum_sayisi;
    }

    public java.sql.Time getProgram_uzunlugu() {
        return program_uzunlugu;
    }

    public void setProgram_uzunlugu(java.sql.Time program_uzunlugu) {
        this.program_uzunlugu = program_uzunlugu;
    }
    
}
