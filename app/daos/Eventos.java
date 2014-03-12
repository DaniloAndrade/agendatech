package daos;

import com.avaje.ebean.Ebean;
import models.Evento;

import java.util.List;

/**
 * Created by danilo on 11/03/14.
 */
public class Eventos {

    public static List<Evento> aprovados(boolean situacao){
        return Ebean.find(Evento.class).where().eq("aprovado", situacao).findList();
    }
}
