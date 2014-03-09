package models;

import org.hibernate.validator.constraints.URL;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by danilo on 07/03/14.
 */

@Entity
public class Evento {

    @Id
    @GeneratedValue
    private Integer id;
    @Constraints.Email
    private String emailParaContato;
    @Enumerated(EnumType.STRING)
    private Estado estado;
    @Column(columnDefinition = "text")
    @Constraints.Required
    private String descricao;
    @URL
    private String site;
    private String twitter;
    @Constraints.Required
    private String nome;
    private Calendar dataDeInicio;
    private Calendar dataDeFim;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmailParaContato() {
        return emailParaContato;
    }

    public void setEmailParaContato(String emailParaContato) {
        this.emailParaContato = emailParaContato;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Calendar getDataDeInicio() {
        return dataDeInicio;
    }

    public void setDataDeInicio(Calendar dataDeInicio) {
        this.dataDeInicio = dataDeInicio;
    }

    public Calendar getDataDeFim() {
        return dataDeFim;
    }

    public void setDataDeFim(Calendar dataDeFim) {
        this.dataDeFim = dataDeFim;
    }
}
