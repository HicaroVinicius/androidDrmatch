package com.app.streem.doctormatch.Modelo;

/**
 * Created by Hicaro on 17/03/2018.
 */

public class DependenteModel {
    public String getNome() {
        return nome;
    }

    public DependenteModel(String id,String nome, String idade, String sexo, String cpf, String dt_cont) {
        this.nome = nome;
        this.idade = idade;
        this.sexo = sexo;
        this.cpf = cpf;
        this.dt_cont = dt_cont;
        this.id = id;
    }

    public DependenteModel(){

    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDt_cont() {
        return dt_cont;
    }

    public void setDt_cont(String dt_cont) {
        this.dt_cont = dt_cont;
    }

    private String nome;
    private String idade;
    private String sexo;
    private String cpf;
    private String dt_cont;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
