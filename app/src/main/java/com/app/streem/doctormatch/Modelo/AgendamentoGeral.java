package com.app.streem.doctormatch.Modelo;

/**
 * Created by Hicaro on 06/03/2018.
 */

public class AgendamentoGeral {
    private String dt_agend;
    private String id;
    private String key_medico;
    private String key_pac;
    private String status;

    public String getDt_agend() {
        return dt_agend;
    }

    public AgendamentoGeral(String dt_agend, String id, String key_medico, String key_pac, String status) {
        this.dt_agend = dt_agend;
        this.id = id;
        this.key_medico = key_medico;
        this.key_pac = key_pac;
        this.status = status;
    }

    public void setDt_agend(String dt_agend) {
        this.dt_agend = dt_agend;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey_medico() {
        return key_medico;
    }

    public void setKey_medico(String key_medico) {
        this.key_medico = key_medico;
    }

    public String getKey_pac() {
        return key_pac;
    }

    public void setKey_pac(String key_pac) {
        this.key_pac = key_pac;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
