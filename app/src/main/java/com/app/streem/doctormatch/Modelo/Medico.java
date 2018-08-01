package com.app.streem.doctormatch.Modelo;

/**
 * Created by Hicaro on 07/03/2018.
 */

public class Medico {


    public Medico(String id, String key_estado, String key_cidade,String key_clinica,String key_especialidade, String dt_cont, String status) {
        this.id = id;
        this.key_estado = key_estado;
        this.key_cidade = key_cidade;
        this.key_clinica = key_clinica;
        this.key_especialidade = key_especialidade;
        this.dt_cont = dt_cont;
        this.status = status;
    }

    public Medico() {

    }


    private String id;

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

    public String getKey_cidade() {
        return key_cidade;
    }

    public void setKey_cidade(String key_cidade) {
        this.key_cidade = key_cidade;
    }

    public String getKey_clinica() {
        return key_clinica;
    }

    public void setKey_clinica(String key_clinica) {
        this.key_clinica = key_clinica;
    }

    public String getKey_especialidade() {
        return key_especialidade;
    }

    public void setKey_especialidade(String key_especialidade) {
        this.key_especialidade = key_especialidade;
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

    private String key_estado;
    private String key_cidade;
    private String key_clinica;
    private String key_especialidade;
    private String dt_cont;
    private String status;


}
