package com.app.streem.doctormatch.Modelo;

public class UsuarioRegistro {


    public  UsuarioRegistro(){

    }


    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDt_alt() {
        return dt_alt;
    }

    public void setDt_alt(String dt_alt) {
        this.dt_alt = dt_alt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    private String cpf;
    private String dt_alt;

    public UsuarioRegistro(String cpf, String dt_alt, String email, String id, String nome) {
        this.cpf = cpf;
        this.dt_alt = dt_alt;
        this.email = email;
        this.id = id;
        this.nome = nome;
    }

    private String email;
    private String id;
    private String nome;

}
