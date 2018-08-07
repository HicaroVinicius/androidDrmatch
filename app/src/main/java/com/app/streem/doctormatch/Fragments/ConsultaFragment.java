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
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.app.streem.doctormatch.Adapter.AdapterConsultas;
import com.app.streem.doctormatch.DAO.BD;
import com.app.streem.doctormatch.DAO.Firebase;
import com.app.streem.doctormatch.DAO.Preferencias;
import com.app.streem.doctormatch.Modelo.Consulta;
import com.app.streem.doctormatch.Modelo.Estado;
import com.app.streem.doctormatch.R;
import com.app.streem.doctormatch.ServicoSeletorActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Hicaro on 20/03/2018.
 */

public class ConsultaFragment extends Fragment {

    private Button agendar;
    private RecyclerView consultaView;
    private RecyclerView.Adapter adapter;
    private ArrayList<Consulta> consultaList = new ArrayList<>();
    private Preferencias preferencias;
    private ProgressBar progressBar;
    private ConstraintLayout semDados;
    private ImageView atualiza;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_consulta,null);

        //========================== Inst. Obj. ================================
        progressBar = view.findViewById(R.id.progressBar3);
        semDados = view.findViewById(R.id.idConstraintConsulta2);
        consultaView = view.findViewById(R.id.recyclerConsulta);
        preferencias = new Preferencias(view.getContext());
        atualiza = view.findViewById(R.id.imageView6);

//        atualiza.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                buscaConsulta();
//            }
//        });
        //=======================================================================

        //=========================== Adapter ====================================
        agendar = view.findViewById(R.id.agendarConsultaButton2);
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
        //getSqlite();
        final BD bd = new BD(getApplicationContext());
        String uid = preferencias.getInfo("id");
        String dtcont_consulta = preferencias.getInfo("dtcont_consulta");
        Log.i("TESTE-dtcont_consultaAn", dtcont_consulta);
        Firebase.getDatabaseReference().child("APP_USUARIOS").child("CONSULTAS").child(uid).orderByChild("dt_CONT").startAt(dtcont_consulta).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("testeBDvalue1",dataSnapshot.toString());
                if(!dataSnapshot.hasChildren()){
                    Log.i("testeBDvalue","noChildConsulta");
                }else {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Consulta value = data.getValue(Consulta.class);
                        Log.i("testeBDConsulta", value.getNOME_MEDICO().toString());
                        bd.inserirConsulta(value);
                        consultaList.add(value);
                        adapter.notifyDataSetChanged();
                        Date dataA = new Date();
                        preferencias.setInfo("dtcont_consulta", String.valueOf(dataA.getTime()));
                        Log.i("TESTE-dtcont_consulta", String.valueOf(dataA.getTime()));

                    }
                }

                ArrayList<Consulta> consultas = bd.buscarConsulta();
                consultaList.clear();
                for (Consulta consulta:consultas) {
                    consultaList.add(consulta);
                    Log.i("testeBDvalueCon_Frag",consulta.toString());
                    adapter.notifyDataSetChanged();
                }

                loadControle();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //========================================================================


        // validar disponibilidade de novos dados no fire e jogar em sqlite
        //=========================  SQLite ======================================
        //getFirebase();
        //getSqlite();


        return view;
    }

    public void getSqlite(){

    }

    //========================= Get Firebase =====================================
    public void getFirebase(){
        Firebase.getDatabaseReference().child("USUARIO").child(preferencias.getCHAVE_INDENTIFICADOR()).child("CONSULTA").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Consulta consulta = data.getValue(Consulta.class);
                    consultaList.add(consulta);
                    adapter.notifyDataSetChanged();

                }

                loadControle();

            }
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    //=========================== Load show/hide =================================
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

//    public void buscaConsulta(){
//
//        final BD bd = new BD(getApplicationContext());
//        bd.deleteConsulta();
//
//        Firebase.getDatabaseReference().child("USUARIO").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("CONSULTA").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                for (DataSnapshot data : dataSnapshot.getChildren()) {
//                    Consulta consulta = data.getValue(Consulta.class);
//                    Log.i("testeBDvalueConsulta",consulta.getKeyConsulta().toString());
//                    Log.i("testeBDvalueConsulta",consulta.getNomeMedico().toString());
//                    bd.inserirConsulta(consulta);
//                }
//
//            }
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }

}
