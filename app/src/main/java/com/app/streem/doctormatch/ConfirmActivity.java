package com.app.streem.doctormatch;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.streem.doctormatch.DAO.Firebase;
import com.app.streem.doctormatch.Modelo.ResultModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;

public class ConfirmActivity extends AppCompatActivity {
    private TextView end1Details;
    private TextView end2Details;
    private TextView titularDetails;
    private TextView registroDetails;
    private TextView classifDetails;
    private TextView horaConfirm;
    private TextView dataConfirm;
    private TextView nomeCliente;
    private String urlFoto;
    private RoundedImageView fotoMedico;
    private Button confirmarButton;

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
        nomeCliente = findViewById(R.id.nomeConfirm);
        confirmarButton = findViewById(R.id.buttonConfirmID);


        Intent dados = getIntent();
        final String titular = dados.getStringExtra("titular");
        final String end1 = dados.getStringExtra("end1");
        final String end2 = dados.getStringExtra("end2");
        final String registro = dados.getStringExtra("registro");
        final String classif = dados.getStringExtra("classif");
        final String keyMedico = dados.getStringExtra("key");
        final String data = dados.getStringExtra("data");
        final String dataFormatt = dados.getStringExtra("dataFormatt");
        final String url = dados.getStringExtra("url");
        final String hora = dados.getStringExtra("hora");
        final String keyHora = dados.getStringExtra("keyHora");
        final String nome = dados.getStringExtra("nome");
        final String cidade = dados.getStringExtra("cidade");
        final String estado = dados.getStringExtra("estado");
        final String espec = dados.getStringExtra("espec");

        titularDetails.setText(titular);
        end1Details.setText(end1);
        end2Details.setText(end2);
        registroDetails.setText(registro);
        classifDetails.setText(classif);
        horaConfirm.setText("Hora: ".concat(hora));
        dataConfirm.setText("Dia: ".concat(dataFormatt));
        nomeCliente.setText(nome);

        confirmarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder confirm = new AlertDialog.Builder(ConfirmActivity.this);
                confirm.setTitle("Confirmar Agendamento");
                confirm.setIcon(R.drawable.ic_done_black_24dp).setMessage("Deseja confirmar o agendamento?").setCancelable(true);
                confirm.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       return;
                    }
                });

                confirm.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        confirmar(data,keyMedico,cidade,estado,espec,keyHora);
                    }
                });

                AlertDialog alertDialog = confirm.create();
                alertDialog.show();
            }
        });


    }

    public void confirmar(String data,String keyMedico,String cidade,String estado,String espec,String keyHora){
        Toast.makeText(ConfirmActivity.this, "Agendando...", Toast.LENGTH_SHORT).show();

        Firebase.getDatabaseReference().child("VAGAS").child(estado).child(cidade).child(espec).child(data).child(keyMedico).child(keyHora).removeValue();



    }


}
