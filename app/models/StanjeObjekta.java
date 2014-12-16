package models;

import javax.persistence.*;
import play.db.ebean.*;
import com.avaje.ebean.*;

@Entity
public class StanjeObjekta extends Model {

    @Id
    public String naziv;
    
    public StanjeObjekta(String name) {
      this.naziv = name;
    }

    public static Finder<String,StanjeObjekta> find = new Finder<String,StanjeObjekta>(
        String.class, StanjeObjekta.class
    );

    public static void insert(String name) {
        StanjeObjekta os = new StanjeObjekta(name);
        os.save();
    }
}