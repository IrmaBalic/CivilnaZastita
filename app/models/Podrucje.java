package models;

import javax.persistence.*;
import play.db.ebean.*;
import com.avaje.ebean.*;

@Entity
public class Podrucje extends Model {

    @Id
    public String naziv;
    
    public Podrucje(String name) {
      this.naziv = name;
    }

    public static Finder<String,Podrucje> find = new Finder<String,Podrucje>(
        String.class, Podrucje.class
    );

    public static void insert(String name) {
        Podrucje p = new Podrucje(name);
        p.save();
    }
}