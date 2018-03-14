package com.app.streem.doctormatch.Modelo;

/**
 * Created by Hicaro on 13/03/2018.
 */

public class VagasModel {

    public VagasModel() {

    }



    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey_cliente() {
        return key_cliente;
    }

    public void setKey_cliente(String key_cliente) {
        this.key_cliente = key_cliente;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    private String key_cliente;
    private String status;
    private String hora;
    private String key;
    private String data;
}
