package models;

import javax.persistence.*;
import play.db.ebean.*;
import com.avaje.ebean.*;

@Entity
public class NivoHitnosti extends Model {

    @Id
    public String naziv;
    
    public NivoHitnosti(String name) {
      this.naziv = name;
    }
     public String getNaziv() {
        return this.naziv;
    }
    public static Finder<String,NivoHitnosti> find = new Finder<String,NivoHitnosti>(
        String.class, NivoHitnosti.class
    );

    public static void insert(String name) {
        NivoHitnosti nh = new NivoHitnosti(name);
        nh.save();
    }
}