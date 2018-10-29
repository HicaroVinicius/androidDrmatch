package com.app.streem.doctormatch;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.streem.doctormatch.DAO.Firebase;
import com.app.streem.doctormatch.DAO.Preferencias;
import com.app.streem.doctormatch.Modelo.UsuarioDados;
import com.app.streem.doctormatch.Modelo.UsuarioRegistro;
import com.app.streem.doctormatch.Modelo.UsuarioRegistro;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
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

public class PerfilActivity extends AppCompatActivity {

    private EditText dataNasc;
    private EditText sobrenome;
    private EditText nome;
    private EditText email;
    private EditText sexo;
    private String sex;
    private EditText cpf;

    private RadioGroup radioGroup;

    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener date;

    private Button button;

    private UsuarioRegistro usuarioRegistro;
    private UsuarioDados usuarioDados;

    private Preferencias preferencias;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

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

        sex = "null";
        preferencias = new Preferencias(this);

        final String idUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        dataNasc = findViewById(R.id.dataPerfil);
        sobrenome = findViewById(R.id.sobrenomePerfil);
        nome = findViewById(R.id.nomePerfil);
        email = findViewById(R.id.emailPerfil);
        cpf = findViewById(R.id.cpfPerfil);

        RadioButton masculino = findViewById(R.id.radioButtonMasculinoCadID);
        RadioButton feminino = findViewById(R.id.radioButtonFeminidoCadID);

        radioGroup = findViewById(R.id.radioGroupCadID);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radioButtonMasculinoCadID:
                        sex = "Masculino";
                        break;
                    case R.id.radioButtonFeminidoCadID:
                        sex = "Feminino";
                        break;
                }
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PerfilActivity.this, "Você não pode alterar o seu Email", Toast.LENGTH_SHORT).show();
            }
        });
        cpf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PerfilActivity.this, "Você não pode alterar o CPF", Toast.LENGTH_SHORT).show();
            }
        });

        button = findViewById(R.id.editarPerfil);



        Firebase.getDatabaseReference().child("APP_USUARIOS").child("DADOS").child(idUser).child("DADOS").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usuarioDados = dataSnapshot.getValue(UsuarioDados.class);

                //USER
                if(usuarioDados.getNome() == null){
                    nome.setText("");
                }else {
                    nome.setText(usuarioDados.getNome());
                }

                if(usuarioDados.getEmail() == null){
                    email.setText("");
                }else {
                    email.setText(usuarioDados.getEmail());
                }

                if(usuarioDados.getCpf() == null){
                    cpf.setText("");
                }else {
                    cpf.setText(usuarioDados.getCpf());
                }

                if(usuarioDados.getSobrenome() == null){
                    sobrenome.setText("");
                }else {
                    sobrenome.setText(usuarioDados.getSobrenome());
                }

                if (usuarioDados.getSexo().equals("Masculino")) {
                    radioGroup.check(R.id.radioButtonMasculinoCadID);
                }else if(usuarioDados.getSexo().equals("Feminino")){
                    radioGroup.check(R.id.radioButtonFeminidoCadID);
                }else{
                    radioGroup.clearCheck();
                }

                // Create a DateFormatter object for displaying date in specified format.
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

                // Create a calendar object that will convert the date and time value in milliseconds to date.
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(Long.valueOf(usuarioDados.getDt_nasc()));
                String data = formatter.format(calendar.getTime());
                dataNasc.setText(data);

            }
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("testePerfil","perfil");

                SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH);
                Date date = null;
                //String nome, String sobrenome,String sexo, String dt_nasc, String email,String dt_cont,String dt_cad,String adm,String cpf,String url
                UsuarioDados dados = new UsuarioDados(nome.getText().toString(),sobrenome.getText().toString(),sex,usuarioDados.getDt_nasc(),usuarioDados.getEmail(),usuarioDados.getDt_cont(),usuarioDados.getDt_cad(),usuarioDados.getAdm(),usuarioDados.getCpf(),usuarioDados.getUrl());
                try {
                    date = sdf.parse(dataNasc.getText().toString());
                    dados = new UsuarioDados(nome.getText().toString(),sobrenome.getText().toString(),sex,String.valueOf(date.getTime()),usuarioDados.getEmail(),usuarioDados.getDt_cont(),usuarioDados.getDt_cad(),usuarioDados.getAdm(),usuarioDados.getCpf(),usuarioDados.getUrl());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Firebase.getDatabaseReference().child("APP_USUARIOS").child("DADOS").child(idUser).child("DADOS").setValue(dados);

//                UsuarioRegistro registro = new UsuarioRegistro(cpf.getText().toString(),"1",email.getText().toString(),idUser,nome.getText().toString());
//                Firebase.getDatabaseReference().child("APP_USUARIOS").child("REGISTRO").child(idUser).setValue(registro);
                preferencias.setInfo("nome",nome.getText().toString());
                preferencias.setInfo("sexo",sex);

                Toast.makeText(PerfilActivity.this, "Informações Salvas com Sucesso", Toast.LENGTH_LONG).show();
                Intent i = new Intent(PerfilActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        myCalendar = Calendar.getInstance();

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dataNasc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(PerfilActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });




    }

    private void updateLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        dataNasc.setText(sdf.format(myCalendar.getTime()));
    }


}




