package controllers.admin;

import com.avaje.ebean.Ebean;
import daos.Eventos;
import models.Evento;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

/**
 * Created by danilo on 11/03/14.
 */
public class TodosEventosController extends Controller {

    public static Result todos(){
        List<Evento> naoAprovado = Eventos.aprovados(false);
        List<Evento> aprovados = Eventos.aprovados(true);
        return ok(views.html.eventos.admin.todos.render(naoAprovado, aprovados));
    }

    public static Result aprovar(Integer id){
        Evento evento = Ebean.find(Evento.class, id);
        if(evento != null){
            evento.setAprovado(true);
            Ebean.save(evento);
        }
        return redirect(controllers.admin.routes.TodosEventosController.todos());
    }
}
