package helper;

import com.typesafe.plugin.MailerAPI;
import com.typesafe.plugin.MailerPlugin;
import models.Evento;
import play.Play;
import play.api.templates.Html;

/**
 * Created by danilo on 11/03/14.
 */
public class ControladorEmails {
    public static void informaNovo(Evento evento){
        MailerAPI email = Play.application().plugin(MailerPlugin.class).email();
        email.setSubject("mailer");
        email.addFrom("cadastros@agendatech.com.br");
        email.addRecipient("danilo.s.andrade@gmail.com");
        Html html = views.html.email.novo.render(evento);
        email.sendHtml(html.body());
    }
}
