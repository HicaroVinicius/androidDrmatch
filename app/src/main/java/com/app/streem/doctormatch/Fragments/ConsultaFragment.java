package com.app.streem.doctormatch.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.streem.doctormatch.Adapter.AdapterConsultas;
import com.app.streem.doctormatch.DAO.Firebase;
import com.app.streem.doctormatch.DAO.Preferencias;
import com.app.streem.doctormatch.Modelo.Consulta;
import com.app.streem.doctormatch.Modelo.ResultModel;
import com.app.streem.doctormatch.R;
import com.app.streem.doctormatch.ServicoSeletorActivity;
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

    private Button agendar;

    private RecyclerView consultaView;
    private RecyclerView.Adapter adapter;
    private List<Consulta> consultaList = new ArrayList<>();
    private Preferencias preferencias;

    private ConstraintLayout semRegistro;
    private ConstraintLayout cardViewConsulta;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consulta,null);

        Toast.makeText(getApplicationContext(), "Carregando...", Toast.LENGTH_SHORT).show();

        preferencias = new Preferencias(view.getContext());

        agendar = view.findViewById(R.id.agendarConsultaButton);
        agendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ServicoSeletorActivity.class);
                startActivity(intent);
            }
        });

        semRegistro = view.findViewById(R.id.idConstraintConsulta);

        consultaView = view.findViewById(R.id.recyclerConsulta);
        cardViewConsulta = view.findViewById(R.id.idRecyclerConsulta);

        consultaView.setHasFixedSize(true);
        consultaView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        adapter = new AdapterConsultas(consultaList,getApplicationContext(),new AdapterConsultas.OnItemLongClickListener(){
            @Override public void onItemLongClick(Consulta item) {
                Log.i("testedata",item.getMedico());
                Toast.makeText(getApplicationContext(), "Removendo Consulta...", Toast.LENGTH_SHORT).show();
                Firebase.getDatabaseReference().child("CLIENTES").child(item.getMedico()).child("AGENDAMENTO").child(item.getData()).child(item.getHora()).removeValue();
                Firebase.getDatabaseReference().child("USUARIO").child(preferencias.getCHAVE_INDENTIFICADOR()).child("CONSULTA").child(item.getKeyConsulta()).removeValue();
                consultaList.remove(item);
                if(consultaList.size()==0){
                    semRegistro.setVisibility(View.VISIBLE);
                    cardViewConsulta.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();

            }
        });

        adapter.notifyDataSetChanged();

        consultaView.setAdapter(adapter);

        Firebase.getDatabaseReference().child("USUARIO").child(preferencias.getCHAVE_INDENTIFICADOR()).child("CONSULTA").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                if (!dataSnapshot.hasChildren()){
                    semRegistro.setVisibility(View.VISIBLE);
                    cardViewConsulta.setVisibility(View.GONE);
                    Log.i("TESTE","sem filho");
                }else {

                    semRegistro.setVisibility(View.GONE);
                    cardViewConsulta.setVisibility(View.VISIBLE);

                    for (DataSnapshot data : dataSnapshot.getChildren()) {

                        Log.i("TESTE", data.getValue().toString());
                        Consulta consulta = data.getValue(Consulta.class);
                        consultaList.add(consulta);
                        adapter.notifyDataSetChanged();

                    }

                }
            }
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;
    }
}
