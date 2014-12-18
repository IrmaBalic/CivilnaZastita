package models;

import javax.persistence.*;
import play.db.ebean.*;
import com.avaje.ebean.*;
import com.avaje.ebean.Ebean;  
import com.avaje.ebean.config.GlobalProperties;  
import java.util.List;
import java.util.*;


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
    public Objekat(String title, String description, String location) {
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
    public static Objekat getObjekat(int id) {
        return find.where().eq("id", id).findUnique();
    }
    public int getId() {
        return this.id;
    }
    public String getNaziv() {
        return this.naziv;
    }
    public String getOpis() {
        return this.opis;
    }
    public String getLokacija() {
        return this.lokacija;
    }
    public String getTipNepogode() {
        return this.tipNepogode.getNaziv();
    }
    public String getStanjeObjekta() {
        return this.stanjeObjekta.getNaziv();
    }
    public String getNivoHitnosti() {
        return this.nivoHitnosti.getNaziv();
    }
    public List<SlikaObjekta> getSlikeObjekta() {
        List<SlikaObjekta> slike = SlikaObjekta.getSlikeObjekta(this.id);
        return slike;
    }
    public void setNivoHitnosti(String nivo) {
        this.nivoHitnosti = NivoHitnosti.find.ref(nivo);
    }
    public void setStanjeObjekta(String stanje) {
        this.stanjeObjekta = StanjeObjekta.find.ref(stanje);
    }
    public void setPodrucje(String podrucje) {
        this.podrucje = Podrucje.find.ref(podrucje);
    }
    public static int insert(String title, String description, String location, String typeOfDisaster, String state) {
        Objekat o = new Objekat(title, description, location);
        o.stanjeObjekta = StanjeObjekta.find.ref(state);
        o.tipNepogode = TipNepogode.find.ref(typeOfDisaster);
        o.nivoHitnosti = NivoHitnosti.find.ref("Nedefinisan");
        o.save();
        /*SlikaObjekta.insert(image, o.id);*/
        /*so.objekat = Ebean.find(Objekat.class, o.id);
        so.save();*/
        return o.id;
    }
	public static List<Objekat> searchAll(){
		List<Objekat> objekti= find.findList();
		List<String> locations=new ArrayList<String>();
		for (int i=0; i<objekti.size(); i++){
			String[] ob = objekti.get(i).lokacija.split(",", 2);
				locations.add(ob[0]);
		};
		return objekti;
	} 
}