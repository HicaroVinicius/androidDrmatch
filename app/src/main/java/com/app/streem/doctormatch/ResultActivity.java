package com.app.streem.doctormatch;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Toast;

import com.app.streem.doctormatch.DAO.AdapterConsultas;
import com.app.streem.doctormatch.DAO.Consulta;
import com.app.streem.doctormatch.DAO.Firebase;
import com.app.streem.doctormatch.DAO.Preferencias;
import com.app.streem.doctormatch.DAO.ResultAdapter;
import com.app.streem.doctormatch.DAO.ResultMedicos;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ResultActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private String cidade,espec,estado,n;

    private List<ResultMedicos> medicos = new ArrayList<>();
    private ResultMedicos med;
    private List<Consulta> consultas = new ArrayList<>();
    private Preferencias preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        preferencias = new Preferencias(this);
        recyclerView = findViewById(R.id.RecyclerViewMedico);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        cidade = preferencias.getCHAVE_CIDADE().replace(" ","");
        estado = preferencias.getCHAVE_ESTADO().replace(" ","");
        espec = preferencias.getCHAVE_ESPECIALIDADE().replace(" ","");

        buscarMedicos(cidade,estado,espec);

    }

    //carrega lista
    public void buscarMedicos(String cidade,String estado,String espec) {

        adapter = new ResultAdapter(medicos, this);

        recyclerView.setAdapter(adapter);

       // final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        //Toast.makeText(this,"busca",Toast.LENGTH_SHORT).show();
        Firebase.getDatabaseReference().child("VAGAS").child(estado).child(cidade).child(espec).child("DATA_EM_SEGS").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (!dataSnapshot.hasChildren()){
                    Toast.makeText(getApplicationContext(),"Nenhum Médico encontrado",Toast.LENGTH_LONG).show();
                }

                for (DataSnapshot data : dataSnapshot.getChildren()){

                    String key = data.getKey();
                    Log.i("TESTEMED",data.getKey().concat("fora"));

                    Firebase.getDatabaseReference().child("CLIENTES").child(key).child("DADOS").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {


                                   ResultMedicos med = dataSnapshot.getValue(ResultMedicos.class);
                                   medicos.add(med);
                                   adapter.notifyDataSetChanged();


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.i("TESTEMED",databaseError.getDetails());
                        }
                    });

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

}