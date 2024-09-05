package com.gugawag.pdist.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Mensagem implements Serializable {

    @Id
    private long id;
    private String mensagem;

    public Mensagem(){

    }

    public Mensagem(long id, String mensagem) {
        this.id = id;
        this.mensagem = mensagem;
    }

    public long getId() {
        return id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String nome) {
        this.mensagem = nome;
    }
}
