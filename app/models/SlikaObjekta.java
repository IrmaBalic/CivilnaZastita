package models;

import javax.persistence.*;
import play.db.ebean.*;
import com.avaje.ebean.*;
import java.util.List;


@Entity
public class SlikaObjekta extends Model {

    @Id
    public int id;
    public String naziv;
    public String komentar;
    @ManyToOne
    public Objekat objekat;
    
    public SlikaObjekta(String name) {
      this.naziv = name;
    }
    public String getNaziv() {
        return this.naziv;
    }
    public static List<SlikaObjekta> getSlikeObjekta(int id) {
      return find.where().eq("id", id).findList();
    }
    public static Finder<String,SlikaObjekta> find = new Finder<String,SlikaObjekta>(
        String.class, SlikaObjekta.class
    );

    public static void insert(String name, int objekat) {
        SlikaObjekta p = new SlikaObjekta(name);
        p.objekat = Ebean.find(Objekat.class, objekat);
        p.save();
    }
}