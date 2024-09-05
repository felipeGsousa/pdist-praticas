package com.gugawag.pdist.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Usuario implements Serializable {

    @Id
    private long id;
    private String nome;

    public Usuario(){

    }

    public Usuario(long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
