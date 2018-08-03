package com.app.streem.doctormatch.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Hicaro on 27/03/2018.
 */

public class SQLITE extends SQLiteOpenHelper {
    private static final String NOME_BD = "doctorMatch";
    private static final int VERSAO_BD = 11;

    public SQLITE(Context context){
        super(context,NOME_BD,null, VERSAO_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("CREATE TABLE " + "especialidades" + "("
//                + "keyEspec" + " TEXT PRIMARY KEY," + "nome" + " TEXT,"
//                + "dt_cont" + " TEXT, " + "status" + " TEXT" + ")");
        db.execSQL("create table especialidades( id text primary key not null, nome text, dt_cont text,status text);");
        db.execSQL("create table exames( id text primary key not null, nome text, dt_cont text,status text);");
        db.execSQL("create table cidades( dt_cont text,key_estado text,id text primary key not null,  cidade text ,status text);");
        db.execSQL("create table estados(id text primary key not null, estado text, dt_cont text,status text);");
        db.execSQL("create table medicos(id text primary key not null, key_cidade text, key_estado text, key_clinica text, key_especialidade text, dt_cont text,status text);");
        db.execSQL("create table medicosDados(id text primary key not null, endereco1 text, endereco2 text, titular text, registro text, url text,valor text,local text, dt_cont text);");
        db.execSQL("create table consultas(id text primary key not null,KEY_CLINIC text,KEY_MEDICO text, NOME_MEDICO text, DT_AGEND text,KEY_AGEND text,HORA text, ESPECIALIDADE text,STATUS text, DT_CONT text);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table IF EXISTS especialidades;");
        db.execSQL("drop table IF EXISTS exames;");
        db.execSQL("drop table IF EXISTS cidades;");
        db.execSQL("drop table IF EXISTS estados;");
        db.execSQL("drop table IF EXISTS medicos;");
        db.execSQL("drop table IF EXISTS consultas;");
        db.execSQL("drop table IF EXISTS medicosDados;");
        onCreate(db);
    }
}
