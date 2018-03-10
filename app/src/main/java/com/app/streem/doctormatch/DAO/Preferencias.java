package com.app.streem.doctormatch.DAO;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by josue on 27/02/2018.
 */

public class Preferencias {
    private Context context;
    private SharedPreferences preferences;
    private String NOME_ARQUIVO = "datadrmatch.conf";
    private int MODE = 0;
    private SharedPreferences.Editor editor;

    private final String CHAVE_INDENTIFICADOR = "logadoKey";
    private final String CHAVE_NOME_USUARIO = "logadoNome";
    private final String CHAVE_TEL = "logadoNome";
    private final String CHAVE_DATABASE = "offline";

    private final String CHAVE_TIPO_BUSCA = "tipoBusca"; // para tela de seletor
    private final String CHAVE_TIPO_FILTRO = "tipoFiltro"; // para tela de filtro

    private final String CHAVE_ESPECIALIDADE = "especSelect";
    private final String CHAVE_ESTADO = "estadoSelect";
    private final String CHAVE_CIDADE = "cidadeSelect";
    private final String CHAVE_DATA = "dataSelect";

    public void setCHAVE_DATA(String origem){
        editor.putString(CHAVE_DATA, origem);
        editor.commit();
    }

    public String getCHAVE_DATA(){
        return preferences.getString(CHAVE_DATA,"");
    }

    public void setCHAVE_TIPO_FILTRO(String origem){
        editor.putString(CHAVE_TIPO_FILTRO, origem);
        editor.commit();
    }

    public String getCHAVE_TIPO_FILTRO(){
        return preferences.getString(CHAVE_TIPO_FILTRO,"ESPECIALIDADE");
    }

    public void setCHAVE_TIPO_BUSCA(String origem){
        editor.putString(CHAVE_TIPO_BUSCA, origem);
        editor.commit();
    }

    public String getCHAVE_TIPO_BUSCA(){
        return preferences.getString(CHAVE_TIPO_BUSCA,null);
    }

    public Preferencias(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(NOME_ARQUIVO, MODE);
        editor = preferences.edit();
    }

    public void setUsuarioLogado(String keyUsuario, String nomeUsurio, String tel){
        editor.putString(CHAVE_INDENTIFICADOR, keyUsuario);
        editor.putString(CHAVE_NOME_USUARIO, nomeUsurio);
        editor.putString(CHAVE_TEL, tel);
        editor.commit();
    }

    public String getCHAVE_INDENTIFICADOR(){
        return preferences.getString(CHAVE_INDENTIFICADOR, null);
    }

    public String getCHAVE_NOME_USUARIO(){
        return preferences.getString(CHAVE_NOME_USUARIO, "Erro!");
    }

    public String getCHAVE_TEL(){
        return preferences.getString(CHAVE_TEL, "Erro!");
    }

    public void setCHAVE_DATABASE(){
        editor.putString(CHAVE_DATABASE, "1");
        editor.commit();
    }

    public String getCHAVE_DATABASE(){
        return preferences.getString(CHAVE_DATABASE,"0");
    }

    public void setCHAVE_ESPECIALIDADE(String origem){
        editor.putString(CHAVE_ESPECIALIDADE, origem);
        editor.commit();
    }

    public String getCHAVE_ESPECIALIDADE(){
        return preferences.getString(CHAVE_ESPECIALIDADE,"");
    }

    public void setCHAVE_ESTADO(String origem){
        editor.putString(CHAVE_ESTADO, origem);
        editor.commit();
    }

    public String getCHAVE_ESTADO(){
        return preferences.getString(CHAVE_ESTADO,"");
    }

    public void setCHAVE_CIDADE(String origem){
        editor.putString(CHAVE_CIDADE, origem);
        editor.commit();
    }

    public String getCHAVE_CIDADE(){
        return preferences.getString(CHAVE_CIDADE,"");
    }
}
