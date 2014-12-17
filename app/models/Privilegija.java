package models;

import javax.persistence.*;
import play.db.ebean.*;
import com.avaje.ebean.*;

@Entity
public class Privilegija extends Model {

    @Id
    public String naziv;
    
    public Privilegija(String name) {
      this.naziv = name;
    }
    public String getNaziv() {
        return this.naziv;
    }
    public static Finder<String,Privilegija> find = new Finder<String,Privilegija>(
        String.class, Privilegija.class
    );

    public static void insert(String name) {
        Privilegija p = new Privilegija(name);
        p.save();
    }
}