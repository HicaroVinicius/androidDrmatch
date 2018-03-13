package com.app.streem.doctormatch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.streem.doctormatch.Adapter.ResultAdapter;
import com.app.streem.doctormatch.DAO.Firebase;
import com.app.streem.doctormatch.Modelo.ResultModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {

    private TextView end1Details;
    private TextView end2Details;
    private TextView titularDetails;
    private TextView registroDetails;
    private TextView classifDetails;
    private String urlFoto;
    private RoundedImageView fotoMedico;


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

        Intent dados = getIntent();
        String titular = dados.getStringExtra("titular");
        String end1 = dados.getStringExtra("end1");
        String end2 = dados.getStringExtra("end2");
        String registro = dados.getStringExtra("registro");
        String classif = dados.getStringExtra("classif");

        urlFoto = dados.getStringExtra("url");

        Picasso.with(getApplicationContext()).load(urlFoto).into(fotoMedico);

        titularDetails.setText(titular);
        end1Details.setText(end1);
        end2Details.setText(end2);
        registroDetails.setText(registro);
        classifDetails.setText(classif);





    }




}
