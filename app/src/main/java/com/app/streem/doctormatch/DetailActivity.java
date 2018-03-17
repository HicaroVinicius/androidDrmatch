package com.app.streem.doctormatch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.streem.doctormatch.Adapter.ResultAdapter;
import com.app.streem.doctormatch.Adapter.VagasAdapter;
import com.app.streem.doctormatch.DAO.Firebase;
import com.app.streem.doctormatch.Modelo.ResultModel;
import com.app.streem.doctormatch.Modelo.VagasModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private TextView end1Details;
    private TextView end2Details;
    private TextView titularDetails;
    private TextView registroDetails;
    private TextView classifDetails;
    private String urlFoto;
    private RoundedImageView fotoMedico;
    private RecyclerView horaView;
    private RecyclerView.Adapter adapter;
    private List<VagasModel> vagasList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        titularDetails = findViewById(R.id.titularDetails);
        end1Details = findViewById(R.id.end1Details);
        end2Details = findViewById(R.id.end2Details);
        registroDetails = findViewById(R.id.registroDetails);
        classifDetails = findViewById(R.id.classifDetails);
        fotoMedico = findViewById(R.id.fotoDetails);
        horaView = findViewById(R.id.recyclerHoraDetail);

        horaView.setHasFixedSize(true);
        horaView.setLayoutManager(new LinearLayoutManager(this));

        Intent dados = getIntent();
        String titular = dados.getStringExtra("titular");
        String end1 = dados.getStringExtra("end1");
        String end2 = dados.getStringExtra("end2");
        String registro = dados.getStringExtra("registro");
        String classif = dados.getStringExtra("classif");
        final String key = dados.getStringExtra("key");
        final String data = dados.getStringExtra("data");

        urlFoto = dados.getStringExtra("url");

        Picasso.with(getApplicationContext()).load(urlFoto).into(fotoMedico);

        titularDetails.setText(titular);
        end1Details.setText(end1);
        end2Details.setText(end2);
        registroDetails.setText(registro);
        classifDetails.setText(classif);

        Toast.makeText(getApplicationContext(),"Carregando... Aguarde",Toast.LENGTH_SHORT).show();

        adapter = new VagasAdapter(vagasList, getApplicationContext(), new VagasAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(VagasModel item) {
                Toast.makeText(getApplicationContext(),item.getHora(),Toast.LENGTH_SHORT).show();
            }
        });

        adapter.notifyDataSetChanged();

        horaView.setAdapter(adapter);

        Firebase.getDatabaseReference().child("CLIENTES").child(key).child("AGENDAMENTO").child(data).orderByChild("status").equalTo("Disponível").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                if (!dataSnapshot.hasChildren()){
                    Log.i("TESTE","sem filho");
                }



                for (DataSnapshot data : dataSnapshot.getChildren()) {


                        Log.i("TESTE",data.getValue().toString());
                        VagasModel vaga = data.getValue(VagasModel.class);
                        vagasList.add(vaga);
                        adapter.notifyDataSetChanged();

                }
            }
            public void onCancelled(DatabaseError databaseError) {

            }
        });






    }




}
