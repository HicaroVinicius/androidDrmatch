package com.app.streem.doctormatch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.streem.doctormatch.DAO.Preferencias;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

public class ConfirmActivity extends AppCompatActivity {

    private TextView titularDetails;
    private TextView especialidade;
    private TextView info;
    private TextView valorView;
    private String urlFoto;
    private RoundedImageView fotoMedico;
    private Button confirmarButton;
    private Preferencias preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        fotoMedico = findViewById(R.id.fotoMedicoConfirm);

        RoundedImageView fotoPac = findViewById(R.id.fotoPacienteConfirm);
        Picasso.with(getApplicationContext()).load("http://doctormatch.com.br/app_files/hicaro.jpg").into(fotoPac);

        Button buttonConfirmar = findViewById(R.id.buttonConfirmar);
        buttonConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ConfirmActivity.this, AgendConcluido.class);
                startActivity(i);
            }
        });

        preferencias = new Preferencias(this);

        titularDetails = findViewById(R.id.nomeMedicoConfirm);
        especialidade = findViewById(R.id.especialidadeConfirm);
        info = findViewById(R.id.infoConfirm);
        valorView = findViewById(R.id.valorConfirm);


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
        final String valor = dados.getStringExtra("valor");

        Picasso.with(getApplicationContext()).load(url).into(fotoMedico);

        titularDetails.setText(titular);
        especialidade.setText(preferencias.getCHAVE_ESPECIALIDADE());
        info.setText(dataFormatt+" às "+hora);
        valorView.setText(valor);



/*
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
                        confirmar(data,keyMedico,cidade,estado,espec,keyHora,nome,titular,hora,dataFormatt);
                    }
                });

                AlertDialog alertDialog = confirm.create();
                alertDialog.show();
            }
        });


    }

    public void confirmar(String data, String keyMedico, String cidade, String estado, String espec, String keyHora, final String nomeUser,String nomeMedico, String horaFormat,String dataFormat){

        Log.i("testeRemove",estado+"-"+cidade+"-"+espec+"-"+data+"-"+keyMedico+"-"+keyHora);

        Firebase.getDatabaseReference().child("VAGAS").child(estado).child(cidade).child(espec).child(data).child(keyMedico).child(keyHora).removeValue();

        Firebase.getDatabaseReference().child("CLIENTES").child(keyMedico).child("AGENDAMENTO").child(data).child(keyHora).child("status").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().setValue("Indisponível");
                Log.i("testeStatus",dataSnapshot.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Firebase.getDatabaseReference().child("CLIENTES").child(keyMedico).child("AGENDAMENTO").child(data).child(keyHora).child("key_cliente").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().setValue(preferencias.getCHAVE_INDENTIFICADOR());
                Log.i("testeIdent",dataSnapshot.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Firebase.getDatabaseReference().child("CLIENTES").child(keyMedico).child("AGENDAMENTO").child(data).child(keyHora).child("nome").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().setValue(nomeUser);
                Log.i("testeIdent",dataSnapshot.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        String key = Firebase.getDatabaseReference().child("USUARIO").child(preferencias.getCHAVE_INDENTIFICADOR()).child("CONSULTA").push().getKey();
        Consulta nova = new Consulta(keyMedico,data,keyHora,nomeUser,nomeMedico,horaFormat,dataFormat,key);
        Firebase.getDatabaseReference().child("USUARIO").child(preferencias.getCHAVE_INDENTIFICADOR()).child("CONSULTA").child(key).setValue(nova);

        Intent intent = new Intent(ConfirmActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
        */
    }

}
