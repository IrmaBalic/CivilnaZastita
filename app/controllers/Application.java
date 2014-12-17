package controllers;

import play.*;
import play.mvc.*;

import views.html.*;
import models.*;

import play.data.*;
import static play.data.Form.*;
import play.mvc.Http.*;
import play.mvc.Http.MultipartFormData.*;

public class Application extends Controller {
	static Form<Login> loginForm = form(Login.class);
	static Form<Register> registerForm = form(Register.class);
	static Form<NewObject> newObjectForm = form(NewObject.class);

	public static Result adminHome() {
		String role = session("role");
        return ok(admin_home.render( 
            Objekat.find.all(),
            role
        )); 
    }
    public static Result index() {
    	String user = session("email");
        return ok(
        	index.render(user, newObjectForm)
        	);
    }
    public static Result login() {
        return ok(
        	login.render(loginForm)
        	);
    }
    public static Result logout() {
    	session().clear();
	    return redirect(
	        routes.Application.index()
	    );
	}
    public static Result register() {
        return ok(
        	register.render(registerForm)
        	);
    }
    public static Result authenticate() {
        Form<Login> loginForm = form(Login.class).bindFromRequest();
	    if (loginForm.hasErrors()) {
	        return badRequest(login.render(loginForm));
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
	        return badRequest(register.render(registerForm));
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
        // Images
       /* UploadResource resource = newObjectForm.get();*/
        MultipartFormData body = request().body().asMultipartFormData();
        FilePart picture = body.getFile("image");
        String fileName = picture.getFilename();
    	String contentType = picture.getContentType();
    	String state="Waiting";
        // Insert
        Objekat.insert(title, description, location, fileName, typeOfDisaster, state);
        return redirect(
            routes.Application.index()
        );
        /* Multiple files
        multiple = "multiple"
        List<FilePart>file;
        file=md.getFiles();
        for(FilePart p: file){
        Logger.info(p.getFilename());
        }
		*/
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
}
