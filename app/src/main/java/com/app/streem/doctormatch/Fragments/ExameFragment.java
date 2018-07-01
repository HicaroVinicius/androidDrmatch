package com.app.streem.doctormatch.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.app.streem.doctormatch.Adapter.AdapterConsultas;
import com.app.streem.doctormatch.DAO.BD;
import com.app.streem.doctormatch.DAO.Preferencias;
import com.app.streem.doctormatch.Modelo.Consulta;
import com.app.streem.doctormatch.R;
import com.app.streem.doctormatch.ServicoSeletorActivity;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Hicaro on 20/03/2018.
 */

public class ExameFragment extends Fragment {

    private Button agendar;
    private RecyclerView consultaView;
    private RecyclerView.Adapter adapter;
    private ArrayList<Consulta> consultaList = new ArrayList<>();
    private Preferencias preferencias;
    private ProgressBar progressBar;
    private ConstraintLayout semDados;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_exame,null);

        //========================== Inst. Obj. ================================
        progressBar = view.findViewById(R.id.progressBar3);
        semDados = view.findViewById(R.id.idConstraintExame2);
        consultaView = view.findViewById(R.id.recyclerExame);
        preferencias = new Preferencias(view.getContext());
        //=======================================================================

        //=========================== Adapter ====================================
        agendar = view.findViewById(R.id.agendarExameButton2);
        agendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ServicoSeletorActivity.class);
                startActivity(intent);
            }
        });

        consultaView.setHasFixedSize(true);
        consultaView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        adapter = new AdapterConsultas(consultaList,getApplicationContext(),new AdapterConsultas.OnItemLongClickListener(){
            @Override public void onItemLongClick(Consulta item) {
                //implementar detail da consulta e chamada de chat
            }
        });

        adapter.notifyDataSetChanged();
        consultaView.setAdapter(adapter);
        //========================================================================


        // validar disponibilidade de novos dados no fire e jogar em sqlite
        //=========================  SQLite ======================================
        //getFirebase();
        getSqlite();


        return view;
    }

    public void getSqlite(){
        final BD bd = new BD(getApplicationContext());
        ArrayList<Consulta> consultas = bd.buscarConsulta();
        for (Consulta consulta:consultas) {
            consultaList.add(consulta);
            Log.i("testeBDvalueCon_Frag",consulta.toString());
            adapter.notifyDataSetChanged();
        }

        loadControle();
    }

    public void loadControle(){
        if(adapter.getItemCount() > 0){
            progressBar.setVisibility(View.GONE);
            consultaView.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.GONE);
            consultaView.setVisibility(View.GONE);
            semDados.setVisibility(View.VISIBLE);
        }
    }



}
