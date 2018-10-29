package com.app.streem.doctormatch.Modelo;

public class UsuarioRegistro {


    public  UsuarioRegistro(){

    }


    public UsuarioRegistro(String celular, String fixo, String cidade, String estado, String cep, String bairro, String rua, String numero, String adm) {
        this.celular = celular;
        this.fixo = fixo;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
        this.bairro = bairro;
        this.rua = rua;
        this.numero = numero;
        this.adm = adm;
    }

    private String celular;
    private String fixo;
    private String cidade;

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getFixo() {
        return fixo;
    }

    public void setFixo(String fixo) {
        this.fixo = fixo;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getAdm() {
        return adm;
    }

    public void setAdm(String adm) {
        this.adm = adm;
    }

    private String estado;
    private String cep;
    private String bairro;
    private String rua;
    private String numero;
    private String adm;

}
