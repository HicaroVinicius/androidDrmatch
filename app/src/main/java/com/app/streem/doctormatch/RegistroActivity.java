package com.app.streem.doctormatch;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.streem.doctormatch.DAO.Firebase;
import com.app.streem.doctormatch.DAO.Preferencias;
import com.app.streem.doctormatch.DAO.SDFormat;
import com.app.streem.doctormatch.Modelo.UsuarioDados;
import com.app.streem.doctormatch.Modelo.UsuarioRegistro;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;

public class RegistroActivity extends AppCompatActivity {

    private EditText cpf;
    private EditText celular;
    private EditText fixo;
    private EditText estado;
    private EditText cidade;

    private RadioGroup radioGroup;

    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener date;

    private Button button;

    private UsuarioRegistro usuarioRegistro;
    private UsuarioDados usuarioDados;

    private Preferencias preferencias;

    private String cep,adm,bairro,numero,rua;

    FirebaseAuth auth;

    public RegistroActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        ImageView voltar = findViewById(R.id.imageView10);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView voltarText = findViewById(R.id.textView42);
        voltarText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        preferencias = new Preferencias(this);

        final String idUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        cpf = findViewById(R.id.cpfRegistro);
        celular = findViewById(R.id.celularRegistro);
        fixo = findViewById(R.id.fixoRegistro);
        estado = findViewById(R.id.estadoRegistro);
        cidade = findViewById(R.id.cidadeRegistro);

        MaskEditTextChangedListener maskTel = new MaskEditTextChangedListener("(##)#########", celular);
        celular.addTextChangedListener(maskTel);

        button = findViewById(R.id.editarPerfil);



        Firebase.getDatabaseReference().child("APP_USUARIOS").child("DADOS").child(idUser).child("DADOS").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usuarioDados = dataSnapshot.getValue(UsuarioDados.class);
                cpf.setText(usuarioDados.getCpf());
            }
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Firebase.getDatabaseReference().child("APP_USUARIOS").child("DADOS").child(idUser).child("REGISTRO").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usuarioRegistro = dataSnapshot.getValue(UsuarioRegistro.class);
                celular.setText(usuarioRegistro.getCelular());
                fixo.setText(usuarioRegistro.getFixo());
                estado.setText(usuarioRegistro.getEstado());
                cidade.setText(usuarioRegistro.getCidade());
                adm = usuarioRegistro.getAdm();
                numero = usuarioRegistro.getNumero();
                bairro = usuarioRegistro.getBairro();
                rua = usuarioRegistro.getRua();
                cep = usuarioRegistro.getCep();
            }
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cpf.getText().toString().isEmpty()) {
                    Toast.makeText(RegistroActivity.this, "Por favor, preencha o campo do CPF", Toast.LENGTH_SHORT).show();
                }else if(celular.getText().toString().isEmpty() | cidade.getText().toString().isEmpty() | estado.getText().toString().isEmpty()) {
                    Toast.makeText(RegistroActivity.this, "Os campos com * são obrigatórios", Toast.LENGTH_SHORT).show();
                }else{
                    SDFormat sdFormat = new SDFormat();
                    Date dataA = new Date();

                    //String nome, String sobrenome,String sexo, String dt_nasc, String email,String dt_cont,String dt_cad,String adm,String cpf,String url
                    UsuarioDados dados = new UsuarioDados(usuarioDados.getNome(), usuarioDados.getSobrenome(), usuarioDados.getSexo(), usuarioDados.getDt_nasc(), usuarioDados.getEmail(),String.valueOf(dataA.getTime()), usuarioDados.getDt_cad(), usuarioDados.getAdm(), cpf.getText().toString(), usuarioDados.getUrl());

                    Firebase.getDatabaseReference().child("APP_USUARIOS").child("DADOS").child(idUser).child("DADOS").setValue(dados);

                    UsuarioRegistro registro = new UsuarioRegistro(celular.getText().toString(),fixo.getText().toString(),cidade.getText().toString(),estado.getText().toString(),cep,bairro,rua,numero,adm);
                    Firebase.getDatabaseReference().child("APP_USUARIOS").child("DADOS").child(idUser).child("REGISTRO").setValue(registro);

                    preferencias.setInfo("cpf",cpf.getText().toString());

                    Toast.makeText(RegistroActivity.this, "Informações Salvas com Sucesso", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(RegistroActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        });




    }


}




