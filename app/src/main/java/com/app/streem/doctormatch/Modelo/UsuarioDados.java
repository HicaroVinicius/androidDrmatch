package com.app.streem.doctormatch.Modelo;

public class UsuarioDados {


    public UsuarioDados(){

    }

    public UsuarioDados(String tel,String sexo, String ano_nasc,  String dt_nasc) {
        this.sexo = sexo;
        this.ano_nasc = ano_nasc;
        this.tel = tel;
        this.dt_nasc = dt_nasc;
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDt_nasc() {
        return dt_nasc;
    }

    public void setDt_nasc(String dt_nasc) {
        this.dt_nasc = dt_nasc;
    }

    private String sexo;
    private String ano_nasc;
    private String tel;
    private String dt_nasc;

}
