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
import com.app.streem.doctormatch.Modelo.Medico;
import com.app.streem.doctormatch.Modelo.ResultModel;

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

    public void inserirMedico(Medico value){
        ContentValues valores = new ContentValues();
        valores.put("id",value.getId());
        valores.put("status",value.getStatus());
        valores.put("dt_cont",value.getDt_cont());
        valores.put("key_cidade",value.getKey_cidade());
        valores.put("key_estado",value.getKey_estado());
        valores.put("key_clinica",value.getKey_clinica());
        valores.put("key_especialidade",value.getKey_especialidade());
        int id = (int) db.insertWithOnConflict("medicos",null,valores,SQLiteDatabase.CONFLICT_IGNORE);
        if (id == -1) {
            db.update("medicos", valores, "id=?", new String[] {value.getId()});
        }
    }

    public void inserirMedicoDados(ResultModel value){
        ContentValues valores = new ContentValues();
        valores.put("id",value.getId());
        valores.put("endereco1",value.getEndereco1());
        valores.put("dt_cont",value.getDt_cont());
        valores.put("endereco2",value.getEndereco2());
        valores.put("titular",value.getTitular());
        valores.put("registro",value.getRegistro());
        valores.put("url",value.getUrl());
        valores.put("valor",value.getValor());
        valores.put("local",value.getLocal());
        int id = (int) db.insertWithOnConflict("medicosDados",null,valores,SQLiteDatabase.CONFLICT_IGNORE);
        if (id == -1) {
            db.update("medicosDados", valores, "id=?", new String[] {value.getId()});
        }
    }

    public void inserirEspecialidade(Especialidade espec){
        ContentValues valores = new ContentValues();
        valores.put("id",espec.getId());
        valores.put("nome",espec.getNome());
        valores.put("dt_cont",espec.getDt_cont());
        valores.put("status",espec.getStatus());
        int id = (int) db.insertWithOnConflict("especialidades",null,valores,SQLiteDatabase.CONFLICT_IGNORE);
        if (id == -1) {
            db.update("especialidades", valores, "id=?", new String[] {espec.getId()});
        }
    }

    public void inserirCidade(Cidade c){
        ContentValues valores = new ContentValues();
        valores.put("id",c.getId());
        valores.put("key_estado",c.getKey_estado());
        valores.put("cidade",c.getCidade());
        valores.put("dt_cont",c.getDt_cont());
        valores.put("status",c.getStatus());
        int id = (int) db.insertWithOnConflict("cidades",null,valores,SQLiteDatabase.CONFLICT_IGNORE);
        if (id == -1) {
            db.update("cidades", valores, "id=?", new String[] {c.getId()});
        }
    }

    public void inserirEstado(Estado estado){
        ContentValues valores = new ContentValues();
        valores.put("id",estado.getId());
        valores.put("estado",estado.getEstado());
        valores.put("dt_cont",estado.getDt_cont());
        valores.put("status",estado.getStatus());
        int id = (int) db.insertWithOnConflict("estados",null,valores,SQLiteDatabase.CONFLICT_IGNORE);
        Log.i("testeEstado-insert/ ",estado.getEstado());
        if (id == -1) {
            Log.i("testeEstado-update/ ",estado.getEstado());
            int i = db.update("estados", valores, "id=?", new String[] {estado.getId()});
        }

    }

    public void inserirExame(Exame exame){
        ContentValues valores = new ContentValues();
        valores.put("id",exame.getId());
        valores.put("nome",exame.getNome());
        valores.put("dt_cont",exame.getDt_cont());
        valores.put("status",exame.getStatus());
        db.insert("exames",null,valores);
        int id = (int) db.insertWithOnConflict("exames",null,valores,SQLiteDatabase.CONFLICT_IGNORE);
        if (id == -1) {
            db.update("exames", valores, "id=?", new String[] {exame.getId()});
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
    public void deleteMedico(){ db.delete("medicos",null,null); }
    public void deleteMedicoDados(){ db.delete("medicosDados",null,null); }

    public ArrayList<Medico> buscarMedicos(String cidade,String estado, String especialidade){
        Log.i("testeResultMedico",cidade+" - "+estado+" - "+especialidade);
        ArrayList<Medico> medico = new ArrayList<>();
        Medico u;
        Cursor cursor;                               // and key_especialidade=?
        String[] campos =  {"id","dt_cont", "status","key_estado", "key_cidade", "key_clinica", "key_especialidade"};
        cursor = db.query("medicos", campos, "key_cidade=? and key_estado=? and status=? ", new String[] { cidade, estado,"1" }, null, null, null, null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            do{
                u = new Medico();
                u.setId(cursor.getString(0));
                u.setDt_cont(cursor.getString(1));
                u.setStatus(cursor.getString(2));
                u.setKey_estado(cursor.getString(3));
                u.setKey_cidade(cursor.getString(4));
                u.setKey_clinica(cursor.getString(5));
                u.setKey_especialidade(cursor.getString(6));
                medico.add(u);
                Log.i("testeResultMedico-espec",cursor.getString(0)+" - "+especialidade);
            }while (cursor.moveToNext());
        }

        return medico;
    }


    public ResultModel buscarMedicosDados(String id){
        Log.i("testeResultDados-id",id);
        ResultModel u = null;
        Cursor cursor;
        String[] campos =  {"id","dt_cont","endereco1", "endereco2", "valor", "url","local", "titular", "registro"};
        cursor = db.query("medicosDados", campos, null, null, null, null, null, null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            do{
                u = new ResultModel();
                u.setId(cursor.getString(0));
                u.setDt_cont(cursor.getString(1));
                u.setEndereco1(cursor.getString(2));
                u.setEndereco2(cursor.getString(3));
                u.setValor(cursor.getString(4));
                u.setUrl(cursor.getString(5));
                u.setLocal(cursor.getString(6));
                u.setTitular(cursor.getString(7));
                u.setRegistro(cursor.getString(8));
                Log.i("testeResultDados-id",cursor.getString(7));
            }while (cursor.moveToNext());
        }else{
            Log.i("testeResultDados-id","seemDados");
        }

        return u;
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

    public ArrayList<Estado> buscarEstado(){
        ArrayList<Estado> estado = new ArrayList<>();
        Estado u;
        Cursor cursor;
        String[] campos =  {"id"," estado", "dt_cont", "status"};
        cursor = db.query("estados", campos, null, null, null, null, null, null);
        Log.i("testeConsultaCutreso",String.valueOf(cursor.getCount()));
        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            do{
                u = new Estado();
            u.setId(cursor.getString(0));
            u.setEstado(cursor.getString(1));
            u.setDt_cont(cursor.getString(2));
            u.setStatus(cursor.getString(3));
            estado.add(u);
            Log.i("testeConsulta111",cursor.getString(1));
            }while (cursor.moveToNext());
        }

        return estado;
    }

    public ArrayList<Cidade> buscarCidade(String estado){
        ArrayList<Cidade> cidade = new ArrayList<>();
        Cidade u;
        Log.i("testeConsultaIDD->",estado);
        String[] colunas = new String[]{"cidade","key_estado","dt_cont","status","id"};
        String[] arg = new String[]{estado};
        Cursor cursor = db.query("cidades",colunas,"key_estado = ?",arg,null,null,"cidade ASC");

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            do{
                u = new Cidade();
                u.setId(cursor.getString(4));
                u.setCidade(cursor.getString(0));
                u.setKey_estado(cursor.getString(1));
                u.setDt_cont(cursor.getString(2));
                u.setStatus(cursor.getString(3));
                cidade.add(u);
                Log.i("testeConsulta111",cursor.getString(0));
            }while (cursor.moveToNext());
        }

        return cidade;
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
