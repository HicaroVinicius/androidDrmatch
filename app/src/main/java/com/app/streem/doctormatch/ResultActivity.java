package com.app.streem.doctormatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.streem.doctormatch.Modelo.Consulta;
import com.app.streem.doctormatch.DAO.Firebase;
import com.app.streem.doctormatch.DAO.Preferencias;
import com.app.streem.doctormatch.Adapter.ResultAdapter;
import com.app.streem.doctormatch.Modelo.ResultModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ResultActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private TextView semRegistro;

    private String cidade,espec,estado,n;

    private List<ResultModel> medicos = new ArrayList<>();
    private ResultModel med;
    private List<Consulta> consultas = new ArrayList<>();
    private Preferencias preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView dataSel = findViewById(R.id.dataSelResultID);

        semRegistro = findViewById(R.id.semRegistroResultID);

        showLoadingAnimation();
        preferencias = new Preferencias(this);
        recyclerView = findViewById(R.id.RecyclerViewMedico);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        cidade = preferencias.getCHAVE_CIDADE().replace(" ","");
        estado = preferencias.getCHAVE_ESTADO().replace(" ","");
        espec = preferencias.getCHAVE_ESPECIALIDADE().replace(" ","");

        try {
            buscarMedicos(cidade,estado,espec);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dataSel.setText(preferencias.getCHAVE_DATA());
    }

    //show
    public void showLoadingAnimation()
    {
        RelativeLayout pageLoading = (RelativeLayout) findViewById(R.id.main_layoutPageLoading);
        pageLoading.setVisibility(View.VISIBLE);
    }


    //hide
    public void hideLoadingAnimation()
    {
        RelativeLayout pageLoading = (RelativeLayout) findViewById(R.id.main_layoutPageLoading);
        pageLoading.setVisibility(View.GONE);
    }

    //carrega lista
    public void buscarMedicos(String cidade,String estado,String espec) throws ParseException {

        adapter = new ResultAdapter(medicos, this);

        recyclerView.setAdapter(adapter);

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date d = format.parse(preferencias.getCHAVE_DATA());
       // final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        //Toast.makeText(this,"busca",Toast.LENGTH_SHORT).show();
        Firebase.getDatabaseReference().child("VAGAS").child(estado).child(cidade).child(espec).child(String.valueOf(d.getTime())).addListenerForSingleValueEvent(new ValueEventListener() {
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
                            ResultModel med = dataSnapshot.getValue(ResultModel.class);
                            medicos.add(med);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.i("TESTEMED",databaseError.getDetails());
                        }
                    });

                }
                hideLoadingAnimation();
                semRegistro.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

}