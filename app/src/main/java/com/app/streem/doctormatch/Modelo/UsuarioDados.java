package com.app.streem.doctormatch.Modelo;

public class UsuarioDados {


    public UsuarioDados(){

    }


    public UsuarioDados( String nome, String sobrenome,String sexo, String dt_nasc, String email,String dt_cont,String dt_cad,String adm,String cpf, String url) {
        this.sexo = sexo;
        this.sobrenome = sobrenome;
        this.dt_nasc = dt_nasc;
        this.nome = nome;
        this.cpf = cpf;
        this.adm = adm;
        this.dt_cad = dt_cad;
        this.dt_cont = dt_cont;
        this.email = email;
        this.url = url;
    }


    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }


    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getDt_nasc() {
        return dt_nasc;
    }

    public void setDt_nasc(String dt_nasc) {
        this.dt_nasc = dt_nasc;
    }

    private String sexo;
    private String sobrenome;
    private String dt_nasc;
    private String nome;
    private String email;
    private String dt_cont;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDt_cont() {
        return dt_cont;
    }

    public void setDt_cont(String dt_cont) {
        this.dt_cont = dt_cont;
    }

    public String getDt_cad() {
        return dt_cad;
    }

    public void setDt_cad(String dt_cad) {
        this.dt_cad = dt_cad;
    }

    public String getAdm() {
        return adm;
    }

    public void setAdm(String adm) {
        this.adm = adm;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    private String dt_cad;
    private String adm;
    private String cpf;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
