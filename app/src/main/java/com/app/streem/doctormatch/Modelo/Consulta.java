package com.app.streem.doctormatch.Modelo;

/**
 * Created by Hicaro on 07/03/2018.
 */

public class Consulta {

    public Consulta(String medico, String data, String hora,String nome,String nomeMedico,String info,String keyConsulta,String espec) {
        this.medico = medico;
        this.data = data;
        this.hora = hora;
        this.nome = nome;
        this.nomeMedico = nomeMedico;
        this.info = info;
        this.keyConsulta = keyConsulta;
        this.espec = espec;
    }

    public Consulta(){

    }


    private String medico;

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    private String nome;
    private String nomeMedico;

    public String getNomeMedico() {
        return nomeMedico;
    }

    public void setNomeMedico(String nomeMedico) {
        this.nomeMedico = nomeMedico;
    }

    public String getHoraFormat() {
        return horaFormat;
    }

    public void setHoraFormat(String horaFormat) {
        this.horaFormat = horaFormat;
    }

    private String horaFormat;

    public String getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(String dataFormat) {
        this.dataFormat = dataFormat;
    }

    private String dataFormat;

    public String getKeyConsulta() {
        return keyConsulta;
    }

    public void setKeyConsulta(String keyConsulta) {
        this.keyConsulta = keyConsulta;
    }

    private String keyConsulta;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    private String info;

    public String getEspec() {
        return espec;
    }

    public void setEspec(String espec) {
        this.espec = espec;
    }

    private String espec;
}
