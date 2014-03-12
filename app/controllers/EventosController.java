package controllers;

import com.avaje.ebean.Ebean;
import helper.ControladorEmails;
import models.Evento;
import org.apache.commons.io.FileUtils;
import play.api.templates.Html;
import play.data.Form;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.io.File;
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

    public static  F.Promise<Result> criar() throws IOException{

        final Form<Evento> fromRequest = eventoForm.bindFromRequest();

        if(fromRequest.hasErrors()){
            return getResultPromise(fromRequest);
        }
        File destino = gravarArquivo();
        final Evento evento = fromRequest.get();
        evento.setCaminhoImagem(destino.getName());
        try {
            Ebean.save(evento);
            F.Promise<Result> result = getResultPromiseFromEmail(evento);
            return result;
        }catch (RuntimeException e){
            destino.delete();
        }

        return F.Promise.promise(new F.Function0<Result>() {
            @Override
            public Result apply() throws Throwable {
                return redirect(routes.EventosController.listar());
            }
        });
    }

    private static F.Promise<Result> getResultPromiseFromEmail(final Evento evento) {
        F.Promise<Void> enviandoEmail = F.Promise.promise(new F.Function0<Void>() {
            @Override
            public Void apply() throws Throwable {
                ControladorEmails.informaNovo(evento);
                return null;
            }
        });

        return enviandoEmail.map(new F.Function<Void, Result>() {
            @Override
            public Result apply(Void aVoid) throws Throwable {
                return redirect(controllers.routes.EventosController.listar());
            }
        });
    }

    private static F.Promise<Result> getResultPromise(final Form<Evento> fromRequest) {
        return F.Promise.promise(new F.Function0<Result>() {
            @Override
            public Result apply() throws Throwable {
                return badRequest(views.html.eventos.novo.render(fromRequest));
            }
        });
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
