package com.app.streem.doctormatch.Modelo;

/**
 * Created by Hicaro on 07/03/2018.
 */

public class Estado {


    public Estado(String id, String estado, String dt_cont, String status) {
        this.id = id;
        this.estado = estado;
        this.dt_cont = dt_cont;
        this.status = status;
    }

    public Estado(){

    }

    private String id;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }




    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    private String estado;
    private String dt_cont;
    private String status;


}
