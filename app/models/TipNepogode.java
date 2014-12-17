package models;

import javax.persistence.*;
import play.db.ebean.*;
import com.avaje.ebean.*;

@Entity
public class TipNepogode extends Model {

    @Id
    public String naziv;
    
    public TipNepogode(String name) {
      this.naziv = name;
    }
    public String getNaziv() {
        return this.naziv;
    }
    public static Finder<String,TipNepogode> find = new Finder<String, TipNepogode>(
        String.class, TipNepogode.class
    );

    public static void insert(String name) {
        TipNepogode td = new TipNepogode(name);
        td.save();
    }
}