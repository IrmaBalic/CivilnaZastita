package controllers;

import play.*;
import play.mvc.*;

import views.html.*;
import models.*;

import play.data.*;
import static play.data.Form.*;
import play.mvc.Http.*;
import play.mvc.Http.MultipartFormData.*;
import java.lang.Object;
import java.io.File;
import java.util.List;


public class Application extends Controller {
	static Form<Login> loginForm = form(Login.class);
	static Form<Register> registerForm = form(Register.class);
	static Form<NewObject> newObjectForm = form(NewObject.class);
	static Form<EditObject> editObjectForm = form(EditObject.class);

	// Prikaz viewa
	public static Result adminHome() {
		String user = session("email");
		String role = session("role");
        return ok(
        	admin_home.render(user, role, Objekat.find.all(), StanjeObjekta.find.all(), NivoHitnosti.find.all())
        ); 
    }
    public static Result index() {
    	String user = session("email");
    	String role = session("role");
    	String user_name = null;
    	if(user != null) {
    		user_name = Korisnik.getKorisnik(user).getIme();
    	}
        return ok(
        	index.render(user, role, newObjectForm, user_name)
        	);
    }
    public static Result login() {
    	String user = session("email");
		String role = session("role");
        return ok(
        	login.render(user, role, loginForm)
        	);
    }
    public static Result register() {
    	String user = session("email");
		String role = session("role");
        return ok(
        	register.render(user, role, registerForm)
        	);
    }
    public static Result showObject(int id) {
    	String user = session("email");
		String role = session("role");
    	Objekat o = Objekat.getObjekat(id);
    	return ok(
        	showObject.render(user,role,o,StanjeObjekta.find.all(), NivoHitnosti.find.all(),editObjectForm,o.getSlikeObjekta())
        );
    }
    // Funkcionalnosti
    public static Result logout() {
    	session().clear();
	    return redirect(
	        routes.Application.index()
	    );
	}
    public static Result authenticate() {
        Form<Login> loginForm = form(Login.class).bindFromRequest();
	    if (loginForm.hasErrors()) {
	    	String user = session("email");
			String role = session("role");
	        return badRequest(login.render(user, role, loginForm));
	    } else {
	        session().clear();
	        session("email", loginForm.get().email);
	        String role = Korisnik.getPrivilegija(loginForm.get().email);
	        session("role", role);
	        if(role.equals("User")) {
		        return redirect(
		            routes.Application.index()
		        );
	    	}
	    	else {
	    		return redirect(
		            routes.Application.adminHome()
		        );
	    	}
	    }
    }
    public static Result addUser() {        
	    if (registerForm.hasErrors()) {
	    	String user = session("email");
			String role = session("role");
	        return badRequest(register.render(user, role, registerForm));
	    } else {
		    Form<Register> registerForm = form(Register.class).bindFromRequest();
	        String name = registerForm.get().name;
	        String surname = registerForm.get().surname;
	        String email = registerForm.get().email;
	        String password = registerForm.get().password;
	        String phone = registerForm.get().phone;
	        String profession = registerForm.get().profession;
	        Korisnik.insert(name, surname, email, password, phone, profession, "User");
	        return redirect(
	            routes.Application.index()
	        );
	    }
    }
    public static Result addObject() {
		Form<NewObject> newObjectForm = form(NewObject.class).bindFromRequest();
        String title = newObjectForm.get().title;
        String description = newObjectForm.get().description;
        String location = newObjectForm.get().location;
        String typeOfDisaster = newObjectForm.get().typeOfDisaster;
        String state="Waiting";
        int objekatId = Objekat.insert(title, description, location, typeOfDisaster, state);
        // Images
        MultipartFormData body = request().body().asMultipartFormData();
        String myUploadPath = "C:/Users/irma/Desktop/civilna-zastita/public/images/";
        List<FilePart> files = body.getFiles();
        for(FilePart picture: files){
        	File file = picture.getFile();
        	String fileName = picture.getFilename();
        	file.renameTo(new File(myUploadPath, fileName));
        	SlikaObjekta.insert(fileName,objekatId);
        }
        return redirect(
            routes.Application.index()
        );
    }
    public static Result updateObject(int id) {
    	String user = session("email");
		String role = session("role");
    	Objekat o = Objekat.getObjekat(id);
    	Form<EditObject> editObjectForm = form(EditObject.class).bindFromRequest();
        String state = editObjectForm.get().objectState;
        String emergency = editObjectForm.get().emergencyLevel;
        if(emergency.equals("In")) emergency = "In progress";
        o.setNivoHitnosti(emergency);
        o.setStanjeObjekta(state);
        o.save();
        return redirect(
            routes.Application.adminHome()
        );
    }
	
	public static Result objects() {
		String user = session("email");
		String role = session("role");
		List<Objekat> objekti = Objekat.searchAll();
        return ok(
        	objects.render(user, role, objekti)
        );
    }
	

    public static class Login {
	    public String email;
	    public String password;	
		public String validate() {
		    if (Korisnik.authenticate(email, password) == null) {
		      return "Invalid user or password";
		    }
		    return null;
		}
	}
	 public static class Register {
	    public String email;
	    public String name;
	    public String surname;
	    public String password;
	    public String phone;
	    public String profession;
	}
	public static class NewObject {
	    public String title;
	    public String description;
	    public String location;
	    public String typeOfDisaster;
	}
	public static class EditObject {
		public String objectState;
	    public String emergencyLevel;
	}
}
