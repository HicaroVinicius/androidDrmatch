package com.app.streem.doctormatch.Modelo;

/**
 * Created by Hicaro on 06/03/2018.
 */

public class AgendamentoMedico {
    private String cpf;
    private String hora;
    private String id;
    private String key_pac;

    public String getCpf() {
        return cpf;
    }

    public AgendamentoMedico(String cpf, String hora, String id, String key_pac, String key_ui_app, String mili, String status, String tipo, String tp_vaga,String valor, String fpag) {
        this.cpf = cpf;
        this.hora = hora;
        this.id = id;
        this.key_pac = key_pac;
        this.key_ui_app = key_ui_app;
        this.mili = mili;
        this.status = status;
        this.tipo = tipo;
        this.tp_vaga = tp_vaga;
        this.valor = valor;
        this.fpag = fpag;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey_pac() {
        return key_pac;
    }

    public void setKey_pac(String key_pac) {
        this.key_pac = key_pac;
    }

    public String getKey_ui_app() {
        return key_ui_app;
    }

    public void setKey_ui_app(String key_ui_app) {
        this.key_ui_app = key_ui_app;
    }

    public String getMili() {
        return mili;
    }

    public void setMili(String mili) {
        this.mili = mili;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTp_vaga() {
        return tp_vaga;
    }

    public void setTp_vaga(String tp_vaga) {
        this.tp_vaga = tp_vaga;
    }

    private String key_ui_app;
    private String mili;
    private String status;
    private String tipo;
    private String tp_vaga;

    public String getFpag() {
        return fpag;
    }

    public void setFpag(String fpag) {
        this.fpag = fpag;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    private String fpag;
    private String valor;


}
