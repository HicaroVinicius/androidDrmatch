package com.app.streem.doctormatch.Modelo;

import android.support.annotation.NonNull;

/**
 * Created by Hicaro on 13/03/2018.
 */

public class VagasModel implements Comparable<VagasModel> {

    private String id;
    private String hora;
    private String key_pac;
    private String key_ui_pac;
    private String cpf;
    private String status;
    private String tipo;

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


    private String tp_vaga;


    private String mili;

    public VagasModel() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getKey_pac() {
        return key_pac;
    }

    public void setKey_pac(String key_pac) {
        this.key_pac = key_pac;
    }

    public String getKey_ui_pac() {
        return key_ui_pac;
    }

    public void setKey_ui_pac(String key_ui_pac) {
        this.key_ui_pac = key_ui_pac;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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

    public String getMili() {
        return mili;
    }

    public void setMili(String mili) {
        this.mili = mili;
    }

    @Override
    public int compareTo(@NonNull VagasModel vagasModel) {
        return this.getHora().compareTo(vagasModel.getHora());
    }
}
