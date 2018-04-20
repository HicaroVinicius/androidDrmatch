package com.app.streem.doctormatch.Modelo;

public class Usuario {

    public Usuario(String nome, String apelido, String sexo) {
        this.nome = nome;
        this.apelido = apelido;
        this.sexo = sexo;
    }

    public  Usuario(){

    }

    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    private String apelido;
    private String sexo;

}
