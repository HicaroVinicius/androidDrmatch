package com.app.streem.doctormatch.Modelo;

/**
 * Created by Hicaro on 07/03/2018.
 */

public class Cidade {





    private String id;

    public Cidade(String id, String key_estado, String cidade, String dt_cont, String status) {
        this.id = id;
        this.key_estado = key_estado;
        this.cidade = cidade;
        this.dt_cont = dt_cont;
        this.status = status;
    }

    public Cidade() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey_estado() {
        return key_estado;
    }

    public void setKey_estado(String key_estado) {
        this.key_estado = key_estado;
    }

    private String key_estado;


    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
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

    private String cidade;
    private String dt_cont;
    private String status;


}
