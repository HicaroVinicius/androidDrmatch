package com.app.streem.doctormatch.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.app.streem.doctormatch.Modelo.Cidade;
import com.app.streem.doctormatch.Modelo.Consulta;
import com.app.streem.doctormatch.Modelo.Especialidade;
import com.app.streem.doctormatch.Modelo.Estado;
import com.app.streem.doctormatch.Modelo.Exame;

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

    public void inserirEspecialidade(Especialidade espec){
        ContentValues valores = new ContentValues();
        valores.put("keyEspec",espec.getKey());
        valores.put("nome",espec.getNome());
        valores.put("dt_cont",espec.getDt_cont());
        valores.put("status",espec.getStatus());
        int id = (int) db.insertWithOnConflict("especialidades",null,valores,SQLiteDatabase.CONFLICT_IGNORE);
        if (id == -1) {
            db.update("especialidades", valores, "keyEspec=?", new String[] {espec.getKey()});
        }
    }

    public void inserirCidade(Cidade c){
        ContentValues valores = new ContentValues();
        valores.put("keyCidade",c.getKey_cidade());
        valores.put("keyEstado",c.getKey_estado());
        valores.put("cidade",c.getCidade());
        valores.put("dt_cont",c.getDt_cont());
        valores.put("status",c.getStatus());
        int id = (int) db.insertWithOnConflict("cidades",null,valores,SQLiteDatabase.CONFLICT_IGNORE);
        if (id == -1) {
            db.update("cidades", valores, "keyCidade=?", new String[] {c.getKey_cidade()});
        }
    }

    public void inserirEstado(Estado estado){
        ContentValues valores = new ContentValues();
        valores.put("keyEstado",estado.getKey_estado());
        valores.put("estado",estado.getEstado());
        valores.put("dt_cont",estado.getDt_cont());
        valores.put("status",estado.getStatus());
        int id = (int) db.insertWithOnConflict("estados",null,valores,SQLiteDatabase.CONFLICT_IGNORE);
        if (id == -1) {
            db.update("estados", valores, "keyEstado=?", new String[] {estado.getKey_estado()});
        }
    }

    public void inserirExame(Exame exame){
        ContentValues valores = new ContentValues();
        valores.put("keyExames",exame.getKey());
        valores.put("nome",exame.getNome());
        valores.put("dt_cont",exame.getDt_cont());
        valores.put("status",exame.getStatus());
        db.insert("exames",null,valores);
        int id = (int) db.insertWithOnConflict("exames",null,valores,SQLiteDatabase.CONFLICT_IGNORE);
        if (id == -1) {
            db.update("exames", valores, "keyExames=?", new String[] {exame.getKey()});
        }

    }

    public void inserirConsulta(Consulta consulta){
        ContentValues valores = new ContentValues();
        valores.put("keyConsulta",consulta.getKeyConsulta().toString());
        valores.put("medico",consulta.getMedico().toString());
        valores.put("data",consulta.getData().toString());
        valores.put("hora",consulta.getHora().toString());
        valores.put("nome",consulta.getNome().toString());
        valores.put("nomeMedico",consulta.getNomeMedico().toString());
        valores.put("info",consulta.getInfo().toString());
        valores.put("espec",consulta.getEspec().toString());
        db.insert("consultas",null,valores);
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
    public void deleteConsulta(){ db.delete("consultas",null,null); }
    public void deleteExame(){ db.delete("exames",null,null); }

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

    public ArrayList<Consulta> buscarConsulta(){
        ArrayList<Consulta> consultas = new ArrayList();
        //String[] colunas = new String[]{"nome"};
        Cursor cursor = db.query("consultas",null,null,null,null,null,"nome ASC");

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            do{
                Consulta consulta = new Consulta();
                consulta.setKeyConsulta(cursor.getString(0));
                consulta.setMedico(cursor.getString(1));
                consulta.setData(cursor.getString(2));
                consulta.setHora(cursor.getString(3));
                consulta.setNome(cursor.getString(4));
                consulta.setNomeMedico(cursor.getString(5));
                consulta.setInfo(cursor.getString(6));
                consulta.setEspec(cursor.getString(7));

                consultas.add(consulta);
                Log.i("testeBD_cons",consultas.get(0).getNomeMedico());
            }while (cursor.moveToNext());
        }

        return consultas;
    }





}
