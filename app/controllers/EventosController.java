package controllers;

import com.avaje.ebean.Ebean;
import models.Evento;
import org.apache.commons.io.FileUtils;
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
        File destino = gravarArquivo();
        Evento evento = fromRequest.get();
        evento.setCaminhoImagem(destino.getName());
        try {
            Ebean.save(evento);
        }catch (RuntimeException e){
            destino.delete();
        }
        return redirect(routes.EventosController.listar());
    }

    private static File gravarArquivo() throws IOException {
        Http.RequestBody requestBody = request().body();
        Http.MultipartFormData multipartFormData = requestBody.asMultipartFormData();
        Http.MultipartFormData.FilePart destaque = multipartFormData.getFile("destaque");
        File file = destaque.getFile();
        File destino = getFileDistino(destaque);
        //Files.move(file.toPath(), destino.toPath());
        FileUtils.moveFile(file, destino);
        return destino;
    }

    private static File getFileDistino(Http.MultipartFormData.FilePart destaque) {
        return new File("public/images/destaques", System.currentTimeMillis() + "_" + destaque.getFilename());
    }

    public static Result listar(){
        List<Evento> eventos = Ebean.find(Evento.class).findList();
        return ok(views.html.eventos.lista.render(eventos));
    }
}
