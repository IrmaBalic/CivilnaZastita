package models;

import javax.persistence.*;
import play.db.ebean.*;
import com.avaje.ebean.*;

@Entity
public class ObjectState extends Model {

    @Id
    public String name;
    
    public ObjectState(String name) {
      this.name = name;
    }

    public static Finder<String,ObjectState> find = new Finder<String,ObjectState>(
        String.class, ObjectState.class
    );

    public static void insert(String name) {
        ObjectState os = new ObjectState(name);
        os.save();
    }
}