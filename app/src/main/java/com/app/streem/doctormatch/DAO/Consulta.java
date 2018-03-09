package com.app.streem.doctormatch.DAO;

/**
 * Created by Hicaro on 07/03/2018.
 */

public class Consulta {
    public Consulta(String nome, String data, String hora) {
        this.nome = nome;
        this.data = data;
        this.hora = hora;
    }

    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    private String data;
    private String hora;
}
