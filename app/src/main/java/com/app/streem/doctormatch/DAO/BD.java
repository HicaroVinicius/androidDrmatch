package com.app.streem.doctormatch.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLData;
import java.util.ArrayList;

/**
 * Created by Hicaro on 27/03/2018.
 */

public class BD {
    private SQLiteDatabase db;

    public BD(Context context){
        SQLITE auxBD = new SQLITE(context);
        db = auxBD.getWritableDatabase();
    }

    public void inserirEspecialidade(String especialidades){
        ContentValues valores = new ContentValues();
        valores.put("nome",especialidades);
        db.insert("especialidades",null,valores);
    }

    public void inserirCidade(String valor, String estado){
        ContentValues valores = new ContentValues();
        valores.put("estado",estado);
        valores.put("nome",valor);
        db.insert("cidades",null,valores);
    }

    public void inserirEstado(String valor){
        ContentValues valores = new ContentValues();
        valores.put("nome",valor);
        db.insert("estados",null,valores);
    }

    public void inserirExame(String exames){
        ContentValues valores = new ContentValues();
        valores.put("nome",exames);
        db.insert("exames",null,valores);
    }

    public void deleteEspec(){
        db.delete("especialidades",null,null);
    }
    public void deleteEstado(){
        db.delete("estados",null,null);
    }
    public void deleteCidade(){
        db.delete("cidades",null,null);
    }

    public ArrayList<String> buscarEspec(){
        ArrayList<String> especialidades = new ArrayList();
        String[] colunas = new String[]{"nome"};
        Cursor cursor = db.query("especialidades",colunas,null,null,null,null,"nome ASC");

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            do{
                especialidades.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }

        return especialidades;
    }

    public ArrayList<String> buscarEstado(){
        ArrayList<String> estados = new ArrayList();
        String[] colunas = new String[]{"nome"};
        Cursor cursor = db.query("estados",colunas,null,null,null,null,"nome ASC");

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            do{
                estados.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }

        return estados;
    }

    public ArrayList<String> buscarCidade(String estado){
        ArrayList<String> cidades = new ArrayList();
        String[] colunas = new String[]{"nome"};
        String[] arg = new String[]{estado.replace(" ","")};
        Cursor cursor = db.query("cidades",colunas,"estado = ?",arg,null,null,"estado ASC");

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            do{
                cidades.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }

        return cidades;
    }


    public ArrayList<String> buscarExame(){
        ArrayList<String> exames = new ArrayList();
        String[] colunas = new String[]{"nome"};
        Cursor cursor = db.query("exames",colunas,null,null,null,null,"nome ASC");

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            do{
                exames.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }

        return exames;
    }





}
