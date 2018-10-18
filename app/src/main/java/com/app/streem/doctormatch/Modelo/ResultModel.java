package com.app.streem.doctormatch.Modelo;

import java.io.Serializable;

/**
 * Created by Hicaro on 06/03/2018.
 */

public class ResultModel {

    private String registro;
    private String titular;
    private String local;
    private String valor;
    private String url;
    private String classif;
    private String endereco1;
    private String endereco2;

    public String getCartao() {
        return cartao;
    }

    public void setCartao(String cartao) {
        this.cartao = cartao;
    }

    public String getDinheiro() {
        return dinheiro;
    }

    public void setDinheiro(String dinheiro) {
        this.dinheiro = dinheiro;
    }

    public String getCheque() {
        return cheque;
    }

    public void setCheque(String cheque) {
        this.cheque = cheque;
    }

    public String getPlano() {
        return plano;
    }

    public void setPlano(String plano) {
        this.plano = plano;
    }

    private String cartao;
    private String dinheiro;
    private String cheque;
    private String plano;

    public String getKey_clinic() {
        return key_clinic;
    }

    public void setKey_clinic(String key_clinic) {
        this.key_clinic = key_clinic;
    }

    public String getKey_medico() {
        return key_medico;
    }

    public void setKey_medico(String key_medico) {
        this.key_medico = key_medico;
    }

    private String key_clinic;
    private String key_medico;
    private String dataResult;

    public String getDt_cont() {
        return dt_cont;
    }

    public void setDt_cont(String dt_cont) {
        this.dt_cont = dt_cont;
    }

    private String dt_cont;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    private String data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public ResultModel(String registro, String titular, String local, String valor, String url, String classif, String endereco1, String endereco2, String dt_cont, String data, String id,String key_clinic,String key_medico,String cartao,String dinheiro,String cheque, String plano) {
        this.registro = registro;
        this.titular = titular;
        this.local = local;
        this.valor = valor;
        this.url = url;
        this.classif = classif;
        this.endereco1 = endereco1;
        this.endereco2 = endereco2;
        this.dt_cont = dt_cont;
        this.data = data;
        this.id = id;
        this.key_clinic = key_clinic;
        this.key_medico = key_medico;
        this.cartao = cartao;
        this.dinheiro = dinheiro;
        this.cheque = cheque;
        this.plano = plano;
    }

    public ResultModel(){

    }

    public String getEndereco1() {
        return endereco1;
    }

    public void setEndereco1(String endereco1) {
        this.endereco1 = endereco1;
    }

    public String getEndereco2() {
        return endereco2;
    }

    public void setEndereco2(String endereco2) {
        this.endereco2 = endereco2;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getClassif() {
        return classif;
    }

    public void setClassif(String classif) {
        this.classif = classif;
    }


}
