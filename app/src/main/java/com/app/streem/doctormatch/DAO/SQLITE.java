package com.app.streem.doctormatch.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Hicaro on 27/03/2018.
 */

public class SQLITE extends SQLiteOpenHelper {
    private static final String NOME_BD = "doctorMatch";
    private static final int VERSAO_BD = 5;

    public SQLITE(Context context){
        super(context,NOME_BD,null, VERSAO_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table especialidades(nome text primary key not null);");
        db.execSQL("create table exames(nome text primary key not null);");
        db.execSQL("create table cidades(nome text primary key not null, estado text);");
        db.execSQL("create table estados(nome text primary key not null);");
        db.execSQL("create table consultas(keyConsulta text primary key not null,medico text,data text, hora text, nome text,nomeMedico text,info text, espec text);");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table IF EXISTS especialidades;");
        db.execSQL("drop table IF EXISTS exames;");
        db.execSQL("drop table IF EXISTS cidades;");
        db.execSQL("drop table IF EXISTS estados;");
        db.execSQL("drop table IF EXISTS consultas;");
        onCreate(db);
    }
}
