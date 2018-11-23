package com.app.streem.doctormatch;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.streem.doctormatch.Adapter.VagasAdapter;
import com.app.streem.doctormatch.DAO.Firebase;
import com.app.streem.doctormatch.DAO.Preferencias;
import com.app.streem.doctormatch.Modelo.Consulta;
import com.app.streem.doctormatch.Modelo.ResultModel;
import com.app.streem.doctormatch.Modelo.VagasModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import noman.weekcalendar.WeekCalendar;
import noman.weekcalendar.listener.OnDateClickListener;

public class ConsultaDetail extends AppCompatActivity {
    private TextView especialidade;
    private TextView end1Details;
    private TextView end2Details;
    private TextView titularDetails;
    private TextView registroDetails;
    private String urlFoto;
    private RoundedImageView fotoMedico;
    private RecyclerView horaView;
    private RecyclerView.Adapter adapter;
    private List<VagasModel> vagasList = new ArrayList<>();
    private WeekCalendar weekCalendar;
    private Preferencias preferencias;
    private String nome;
    private String dayWeek;
    private String dataNova;
    private String dataFormattNovo;
    private String key_clinic;
    private String key_medico;
    private ChildEventListener agendaRealTime;
    private DatabaseReference referenceAgend;

    private ResultModel value;
    private ResultModel medico;

    private ImageView dinheiro;
    private ImageView cheque;
    private ImageView plano;
    private ImageView cartao;

    private TextView dinheiroText;
    private TextView chequeText;
    private TextView planoText;
    private TextView cartaoText;

    private String dinheiroStatus = "0";
    private String chequeStatus = "0";
    private String planoStatus = "0";
    private String cartaoStatus = "0";

    public TimeZone mTimeZone = TimeZone.getTimeZone("America/Sao_Paulo");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent dados = getIntent();

        final Consulta consulta = (Consulta) dados.getSerializableExtra("consulta");
        final String titular = dados.getStringExtra("titular");
        final String end1 = dados.getStringExtra("end1");
        final String end2 = dados.getStringExtra("end2");
        final String registro = dados.getStringExtra("registro");
        final String classif = dados.getStringExtra("classif");
        final String key = dados.getStringExtra("key");
        final String data = dados.getStringExtra("data");
        final String dataFormatt = dados.getStringExtra("dataFormatt");
        final String dataFormatt2 = dados.getStringExtra("dataFormatt2");
        final String dataDisp = dados.getStringExtra("dataDisp");
        final String url = dados.getStringExtra("url");
        final String cidade = dados.getStringExtra("cidade");
        final String estado = dados.getStringExtra("estado");
        final String espec = dados.getStringExtra("espec");
        final String valor = dados.getStringExtra("valor");



        setContentView(R.layout.activity_consulta_detail);

        dinheiro = findViewById(R.id.dinheiro);
        cheque = findViewById(R.id.cheque);
        plano = findViewById(R.id.plano);
        cartao = findViewById(R.id.cartao);

        dinheiroText = findViewById(R.id.dinheiroText);
        chequeText = findViewById(R.id.chequeText);
        planoText = findViewById(R.id.planoText);
        cartaoText = findViewById(R.id.cartaoText);

        preferencias = new Preferencias(this);

        titularDetails = findViewById(R.id.nomeDetails);
        registroDetails = findViewById(R.id.registroDetails);
        fotoMedico = findViewById(R.id.fotoDetails);
        end1Details = findViewById(R.id.end1Details);
        end2Details = findViewById(R.id.end2Details);
        especialidade = findViewById(R.id.especialidadeDetails);

        //dataNova = data;

        Firebase.getDatabaseReference().child("CRM").child(consulta.getKEY_CLINIC()).child("CONFIG").child("DADOS_MED_LAB").orderByKey().equalTo(consulta.getKEY_MEDICO()).limitToFirst(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("TESTEmedRESULTdadoos", dataSnapshot.toString());
                if (!dataSnapshot.hasChildren()) {
                    Log.i("testeBDvalue1", "Medico sem Dados - CRM");
                } else {

                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        value = data.getValue(ResultModel.class);
                        Log.i("testeBDvalue3", value.getTitular() + " - cont: " + value.getDt_cont() + " - Pref: " + preferencias.getInfo("dtcont_medicoDados"));

                    }

                    ResultModel med = value;
                    medico = new ResultModel();
                    try {
                        medico.setRegistro(med.getRegistro());
                        medico.setTitular(med.getTitular());
                        medico.setLocal(med.getLocal());
                        medico.setEndereco1(med.getEndereco1());
                        medico.setEndereco2(med.getEndereco2());
                        medico.setUrl(med.getUrl());
                        medico.setId(med.getId());
                        medico.setPlano(med.getPlano());
                        medico.setCheque(med.getCheque());
                        medico.setDinheiro(med.getDinheiro());
                        medico.setCartao(med.getCartao());
                    } catch (Exception e) {
                        Log.i("TESTERESULT-ERRO", e.getMessage());
                    }
                    medico.setValor(dataFormatt);
                    medico.setData("");
                    medico.setKey_clinic(consulta.getKEY_CLINIC());
                    medico.setKey_medico(consulta.getKEY_MEDICO());

                    dinheiroStatus = medico.getDinheiro();
                    chequeStatus = medico.getCheque();
                    planoStatus = medico.getPlano();
                    cartaoStatus = medico.getCartao();

                    titularDetails.setText(medico.getTitular());
                    registroDetails.setText(medico.getRegistro());
                    end1Details.setText(medico.getEndereco1());
                    end2Details.setText(medico.getEndereco2());

                    if(dinheiroStatus.equals("0")){
                        dinheiro.setVisibility(View.GONE);
                        dinheiroText.setVisibility(View.GONE);
                    }
                    if(chequeStatus.equals("0")){
                        cheque.setVisibility(View.GONE);
                        chequeText.setVisibility(View.GONE);
                    }
                    if(planoStatus.equals("0")){
                        plano.setVisibility(View.GONE);
                        planoText.setVisibility(View.GONE);
                    }
                    if(cartaoStatus.equals("0")){
                        cartao.setVisibility(View.GONE);
                        cartaoText.setVisibility(View.GONE);
                    }

                    Picasso.with(getApplicationContext()).load(medico.getUrl()).into(fotoMedico);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ImageView voltar = findViewById(R.id.imageView10);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView voltarText = findViewById(R.id.textView36);
        voltarText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        fotoMedico = findViewById(R.id.fotoDetails);

        Button buttonCancelar = findViewById(R.id.buttonDetails);

        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ConsultaDetail.this, MainActivity.class);
                startActivity(i);
            }
        });





    }



}
