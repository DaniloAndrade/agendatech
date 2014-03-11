package controllers.admin;

import com.avaje.ebean.Ebean;
import models.Evento;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

/**
 * Created by danilo on 11/03/14.
 */
public class TodosEventosController extends Controller {

    public static Result todos(){
        List<Evento> naoAprovado = Ebean.find(Evento.class).where().eq("aprovado",false).findList();
        List<Evento> aprovados = Ebean.find(Evento.class).where().eq("aprovado", true).findList();
        return ok(views.html.eventos.admin.todos.render(naoAprovado, aprovados));
    }
}
