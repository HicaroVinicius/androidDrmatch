package com.app.streem.doctormatch.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.streem.doctormatch.Adapter.AdapterConsultas;
import com.app.streem.doctormatch.DAO.Firebase;
import com.app.streem.doctormatch.DAO.Preferencias;
import com.app.streem.doctormatch.Modelo.Consulta;
import com.app.streem.doctormatch.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Hicaro on 20/03/2018.
 */

public class ConsultaFragment extends Fragment {


    private RecyclerView consultaView;
    private RecyclerView.Adapter adapter;
    private List<Consulta> consultaList = new ArrayList<>();
    private Preferencias preferencias;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consulta,null);

        preferencias = new Preferencias(view.getContext());


        consultaView = view.findViewById(R.id.recyclerConsulta);

        consultaView.setHasFixedSize(true);
        consultaView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        adapter = new AdapterConsultas(consultaList,getApplicationContext());

        adapter.notifyDataSetChanged();

        consultaView.setAdapter(adapter);

        Firebase.getDatabaseReference().child("USUARIO").child(preferencias.getCHAVE_INDENTIFICADOR()).child("CONSULTA").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                if (!dataSnapshot.hasChildren()){
                    Log.i("TESTE","sem filho");
                }

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    Log.i("TESTE",data.getValue().toString());
                    Consulta consulta = data.getValue(Consulta.class);
                    consultaList.add(consulta);
                    adapter.notifyDataSetChanged();

                }
            }
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }
}
