package controllers;

import com.avaje.ebean.Ebean;
import models.Evento;
import play.api.templates.Html;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * Created by danilo on 06/03/14.
 */
public class EventosController extends Controller {

    private static Form<Evento> eventoForm = Form.form(Evento.class);

    public static Result novo(){
        Html view = views.html.eventos.novo.render(eventoForm);
        return ok(view);
    }

    public static Result criar() throws IOException{
        Form<Evento> fromRequest = eventoForm.bindFromRequest();
        if(fromRequest.hasErrors()){
            return badRequest(views.html.eventos.novo.render(fromRequest));
        }
        Http.RequestBody requestBody = request().body();
        Http.MultipartFormData multipartFormData = requestBody.asMultipartFormData();
        Http.MultipartFormData.FilePart destaque = multipartFormData.getFile("destaque");
        File file = destaque.getFile();
        File distino = new File("public/images/destaques", System.currentTimeMillis() + "_" + destaque.getFilename());
        Files.move(file.toPath(), distino.toPath());
        Evento evento = fromRequest.get();
        
        Ebean.save(evento);
        return redirect(routes.EventosController.listar());
    }

    public static Result listar(){
        List<Evento> eventos = Ebean.find(Evento.class).findList();
        return ok(views.html.eventos.lista.render(eventos));
    }
}
