package com.app.streem.doctormatch.Modelo;

/**
 * Created by Hicaro on 17/03/2018.
 */

public class DependenteModel {

    public DependenteModel() {
    }

    public DependenteModel(String NOME,String KEY) {
        this.KEY = KEY;
        this.NOME = NOME;
    }


    public String getKEY() {
        return KEY;
    }

    public void setKEY(String KEY) {
        this.KEY = KEY;
    }

    public String getNOME() {
        return NOME;
    }

    public void setNOME(String NOME) {
        this.NOME = NOME;
    }

    private String NOME;
    private String KEY;
}
