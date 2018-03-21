package com.app.streem.doctormatch.Modelo;

/**
 * Created by Hicaro on 07/03/2018.
 */

public class Consulta {

    public Consulta(String medico, String data, String hora,String nome) {
        this.medico = medico;
        this.data = data;
        this.hora = hora;
        this.nome = nome;
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
}
