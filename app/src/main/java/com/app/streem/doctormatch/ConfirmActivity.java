package com.app.streem.doctormatch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

public class ConfirmActivity extends AppCompatActivity {
    private TextView end1Details;
    private TextView end2Details;
    private TextView titularDetails;
    private TextView registroDetails;
    private TextView classifDetails;
    private TextView horaConfirm;
    private TextView dataConfirm;
    private String urlFoto;
    private RoundedImageView fotoMedico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);


        titularDetails = findViewById(R.id.titularConfirm);
        end1Details = findViewById(R.id.end1Confirm);
        end2Details = findViewById(R.id.end2Confirm);
        registroDetails = findViewById(R.id.registroConfirm);
        classifDetails = findViewById(R.id.classifConfirm);
        fotoMedico = findViewById(R.id.fotoConfirm);
        horaConfirm = findViewById(R.id.horaConfirm);
        dataConfirm = findViewById(R.id.dataConfirm);

        Intent dados = getIntent();
        final String titular = dados.getStringExtra("titular");
        final String end1 = dados.getStringExtra("end1");
        final String end2 = dados.getStringExtra("end2");
        final String registro = dados.getStringExtra("registro");
        final String classif = dados.getStringExtra("classif");
        final String key = dados.getStringExtra("key");
        final String data = dados.getStringExtra("data");
        final String dataFormatt = dados.getStringExtra("dataFormatt");
        final String url = dados.getStringExtra("url");
        final String hora = dados.getStringExtra("hora");

        titularDetails.setText(titular);
        end1Details.setText(end1);
        end2Details.setText(end2);
        registroDetails.setText(registro);
        classifDetails.setText(classif);
        horaConfirm.setText("Hora: ".concat(hora));
        dataConfirm.setText("Dia: ".concat(data));
    }


}
