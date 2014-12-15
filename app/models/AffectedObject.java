package models;

import javax.persistence.*;
import play.db.ebean.*;
import com.avaje.ebean.*;

@Entity
public class AffectedObject extends Model {

    @Id
    public int id;
    public String title;
    public String description;
    public String location;
    public String typeOfDisaster;
    public String image;
    @ManyToOne
    public ObjectState state;
    @ManyToOne
    public TypeOfDisaster type;
    public AffectedObject(String title, String description, String location, String typeOfDisaster, String image) {
      this.title = title;
      this.description = description;
      this.location = location;
      this.typeOfDisaster = typeOfDisaster;
      this.image = image;
    }

    public static Finder<String,AffectedObject> find = new Finder<String,AffectedObject>(
        String.class, AffectedObject.class
    );
    /*public static List<AffectedObject> findObjectsWithState(String objectStateName) {
       return find.fetch("state").where().eq("project.members.email", user).findList();
    }*/

    public static void insert(String title, String description, String location, String typeOfDisaster, String image, String type, String state) {
        AffectedObject o = new AffectedObject(title, description, location, typeOfDisaster, image);
        o.state = ObjectState.find.ref(state);
        o.type = TypeOfDisaster.find.ref(type);
        o.save();
    }
}