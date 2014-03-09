package controllers;

import com.avaje.ebean.Ebean;
import models.Evento;
import play.api.templates.Html;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import java.io.IOException;
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
        Evento evento = fromRequest.get();
        Ebean.save(evento);
        return redirect(routes.EventosController.listar());
    }

    public static Result listar(){
        List<Evento> eventos = Ebean.find(Evento.class).findList();
        return ok(views.html.eventos.lista.render(eventos));
    }
}
