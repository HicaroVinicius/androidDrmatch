package com.app.streem.doctormatch.Modelo;

/**
 * Created by Hicaro on 07/03/2018.
 */

public class Exame {



    public Exame(String key, String nome, String dt_cont, String status) {
        this.key = key;
        this.nome = nome;
        this.dt_cont = dt_cont;
        this.status = status;
    }

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDt_cont() {
        return dt_cont;
    }

    public void setDt_cont(String dt_cont) {
        this.dt_cont = dt_cont;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String nome;
    private String dt_cont;
    private String status;


}
