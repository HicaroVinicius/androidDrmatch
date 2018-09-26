package com.app.streem.doctormatch.Modelo;

public class UsuarioDados {


    public UsuarioDados(){

    }

    public UsuarioDados(String cidade,String sexo, String ano_nasc,  String dt_nasc, String nome,String url) {
        this.sexo = sexo;
        this.ano_nasc = ano_nasc;
        this.cidade = cidade;
        this.dt_nasc = dt_nasc;
        this.nome = nome;
        this.url = url;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getAno_nasc() {
        return ano_nasc;
    }

    public void setAno_nasc(String ano_nasc) {
        this.ano_nasc = ano_nasc;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getDt_nasc() {
        return dt_nasc;
    }

    public void setDt_nasc(String dt_nasc) {
        this.dt_nasc = dt_nasc;
    }

    private String sexo;
    private String ano_nasc;
    private String cidade;
    private String dt_nasc;
    private String nome;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
