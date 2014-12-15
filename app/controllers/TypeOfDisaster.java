package models;

import javax.persistence.*;
import play.db.ebean.*;
import com.avaje.ebean.*;

@Entity
public class TypeOfDisaster extends Model {

    @Id
    public String name;
    
    public TypeOfDisaster(String name) {
      this.name = name;
    }

    public static Finder<String,TypeOfDisaster> find = new Finder<String,TypeOfDisaster>(
        String.class, TypeOfDisaster.class
    );

    public static void insert(String name) {
        TypeOfDisaster td = new TypeOfDisaster(name);
        td.save();
    }
}