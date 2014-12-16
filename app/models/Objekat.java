package models;

import javax.persistence.*;
import play.db.ebean.*;
import com.avaje.ebean.*;
import com.avaje.ebean.Ebean;  
import com.avaje.ebean.config.GlobalProperties;  

@Entity
public class Objekat extends Model {

    @Id
    public int id;
    public String naziv;
    public String opis;
    public String lokacija;
    @Lob
    public byte[] izvjestaj;
    @ManyToOne
    public StanjeObjekta stanjeObjekta;
    @ManyToOne
    public TipNepogode tipNepogode;
    @ManyToOne
    public NivoHitnosti nivoHitnosti;
    @ManyToOne
    public Podrucje podrucje;
    public Objekat(String title, String description, String location, String image) {
      this.naziv = title;
      this.opis = description;
      this.lokacija = location;
    }

    public static Finder<String,Objekat> find = new Finder<String,Objekat>(
        String.class, Objekat.class
    );
    /*public static List<AffectedObject> findObjectsWithState(String objectStateName) {
       return find.fetch("state").where().eq("project.members.email", user).findList();
    }*/
    public void setNivoHitnosti(String nivo) {
        this.nivoHitnosti = NivoHitnosti.find.ref(nivo);
    }
    public void setStanjeObjekta(String stanje) {
        this.stanjeObjekta = StanjeObjekta.find.ref(stanje);
    }
    public void setPodrucje(String podrucje) {
        this.podrucje = Podrucje.find.ref(podrucje);
    }
    public static void insert(String title, String description, String location, String image, String typeOfDisaster, String state) {
        Objekat o = new Objekat(title, description, location, image);
        o.stanjeObjekta = StanjeObjekta.find.ref(state);
        o.tipNepogode = TipNepogode.find.ref(typeOfDisaster);
        o.save();
        SlikaObjekta.insert(image, o.id);
        /*so.objekat = Ebean.find(Objekat.class, o.id);
        so.save();*/
    }
}