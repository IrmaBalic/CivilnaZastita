package models;

import javax.persistence.*;
import play.db.ebean.*;
import com.avaje.ebean.*;

@Entity
public class User extends Model {

    @Id
    public String email;
    public String name;
    public String surname;
    public String password;
    public String phone;
    public String profession;
    
    public User(String name, String surname, String email, String password, String phone, String profession) {
      this.email = email;
      this.name = name;
      this.password = password;
      this.surname = surname;
      this.phone = phone;
      this.profession = profession;
    }

    public static Finder<String,User> find = new Finder<String,User>(
        String.class, User.class
    );
    public static User authenticate(String email, String password) {
        return find.where().eq("email", email)
            .eq("password", password).findUnique();
    }
    public static void insert(String name, String surname, String email, String password, String phone, 
      String profession) {
        User u = new User(name, surname, email, password, phone, profession);
        u.save();
    }
}