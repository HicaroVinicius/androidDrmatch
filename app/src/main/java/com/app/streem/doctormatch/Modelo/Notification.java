package com.app.streem.doctormatch.Modelo;

public class Notification {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String id;
    public String msg;
    public String nome;

    public Notification(String id, String msg, String nome, String url) {
        this.id = id;
        this.msg = msg;
        this.nome = nome;
        this.url = url;
    }

    public String url;


}
