package models;

import javax.persistence.*;
import play.db.ebean.*;
import com.avaje.ebean.*;

@Entity
public class Korisnik extends Model {

    @Id
    public String email;
    public String ime;
    public String prezime;
    public String sifra;
    public String telefon;
    public String zanimanje;
    @ManyToOne
    public Privilegija privilegija;
    
    public Korisnik(String name, String surname, String email, String password, String phone, String profession) {
      this.email = email;
      this.ime = name;
      this.prezime = surname;
      this.sifra = password;
      this.telefon = phone;
      this.zanimanje = profession;
    }

    public static Finder<String,Korisnik> find = new Finder<String,Korisnik>(
        String.class, Korisnik.class
    );
    public String getIme() {
        return this.ime;
    }
    public String getPrivilegija() {
        return this.privilegija.getNaziv();
    }
    public static Korisnik authenticate(String email, String password) {
        return find.where().eq("email", email)
            .eq("sifra", password).findUnique();
    }
    public static String getPrivilegija(String email) {
        return find.where().eq("email", email).findUnique().getPrivilegija();
    }
    public static Korisnik getKorisnik(String email) {
        return find.where().eq("email", email).findUnique();
    }
    public static void insert(String name, String surname, String email, String password, String phone, 
      String profession, String role) {
        Korisnik u = new Korisnik(name, surname, email, password, phone, profession);
        u.privilegija = Privilegija.find.ref(role);
        u.save();
    }
}