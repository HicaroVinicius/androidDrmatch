package com.app.streem.doctormatch.Modelo;

import java.io.Serializable;

/**
 * Created by Hicaro on 07/03/2018.
 */

public class Consulta implements Serializable {

    private String KEY;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    private String cpf;

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    private String valor;

    public String getKEY() {
        return KEY;
    }

    public Consulta(){


    }

    public Consulta(String KEY, String KEY_CLINIC, String KEY_MEDICO, String NOME_MEDICO, String DT_AGEND, String KEY_AGEND, String HORA, String ESPECIALIDADE, String STATUS, String DT_CONT, String cpf, String valor) {
        this.KEY = KEY;
        this.KEY_CLINIC = KEY_CLINIC;
        this.KEY_MEDICO = KEY_MEDICO;
        this.NOME_MEDICO = NOME_MEDICO;
        this.DT_AGEND = DT_AGEND;
        this.KEY_AGEND = KEY_AGEND;
        this.HORA = HORA;
        this.ESPECIALIDADE = ESPECIALIDADE;
        this.STATUS = STATUS;
        this.DT_CONT = DT_CONT;
        this.cpf = cpf;
        this.valor = valor;
    }

    public void setKEY(String KEY) {
        this.KEY = KEY;
    }

    public String getKEY_CLINIC() {
        return KEY_CLINIC;
    }

    public void setKEY_CLINIC(String KEY_CLINIC) {
        this.KEY_CLINIC = KEY_CLINIC;
    }

    public String getKEY_MEDICO() {
        return KEY_MEDICO;
    }

    public void setKEY_MEDICO(String KEY_MEDICO) {
        this.KEY_MEDICO = KEY_MEDICO;
    }

    public String getNOME_MEDICO() {
        return NOME_MEDICO;
    }

    public void setNOME_MEDICO(String NOME_MEDICO) {
        this.NOME_MEDICO = NOME_MEDICO;
    }

    public String getDT_AGEND() {
        return DT_AGEND;
    }

    public void setDT_AGEND(String DT_AGEND) {
        this.DT_AGEND = DT_AGEND;
    }

    public String getKEY_AGEND() {
        return KEY_AGEND;
    }

    public void setKEY_AGEND(String KEY_AGEND) {
        this.KEY_AGEND = KEY_AGEND;
    }

    public String getHORA() {
        return HORA;
    }

    public void setHORA(String HORA) {
        this.HORA = HORA;
    }

    public String getESPECIALIDADE() {
        return ESPECIALIDADE;
    }

    public void setESPECIALIDADE(String ESPECIALIDADE) {
        this.ESPECIALIDADE = ESPECIALIDADE;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getDT_CONT() {
        return DT_CONT;
    }

    public void setDT_CONT(String DT_CONT) {
        this.DT_CONT = DT_CONT;
    }

    private String KEY_CLINIC;
    private String KEY_MEDICO;
    private String NOME_MEDICO;
    private String DT_AGEND;
    private String KEY_AGEND;
    private String HORA;
    private String ESPECIALIDADE;
    private String STATUS;
    private String DT_CONT;
}
