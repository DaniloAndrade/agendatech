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

        F.Promise<F.Function0<Result>> promiseFromCreate = getResultFromCreateEvento(fromRequest);
        F.Promise<Result> promise = promiseFromCreate.map(new F.Function<F.Function0<Result>, Result>() {
            @Override
            public Result apply(F.Function0<Result> resultFunction0) throws Throwable {
                return resultFunction0.apply();
            }
        });
        return promise;
    }

    private static F.Promise<F.Function0<Result>> getResultFromCreateEvento(final Form<Evento> form){
        return F.Promise.promise(new F.Function0<F.Function0<Result>>() {
            @Override
            public F.Function0<Result> apply() throws Throwable {

                if(form.hasErrors()){
                    return new F.Function0<Result>() {
                        @Override
                        public Result apply() throws Throwable {
                            return badRequest(views.html.eventos.novo.render(form));
                        }
                    };
                }
                File destino = null;
                try{
                    destino = gravarArquivo();
                    final Evento evento = form.get();
                    evento.setCaminhoImagem(destino.getName());
                    Ebean.save(evento);
                    ControladorEmails.informaNovo(evento);
                }catch (Exception e){
                    destino.delete();
                }


                return new F.Function0<Result>(){
                    @Override
                    public Result apply() throws Throwable {
                        return redirect(controllers.routes.EventosController.listar());
                    }
                };
            }
        });
    }

    private static File gravarArquivo() throws IOException {
        Http.RequestBody requestBody = request().body();
        Http.MultipartFormData multipartFormData = requestBody.asMultipartFormData();
        Http.MultipartFormData.FilePart destaque = multipartFormData.getFile("destaque");
        File file = destaque.getFile();
        File destino = getFileDistino(destaque);
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
