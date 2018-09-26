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
    private EditText cidade;
    private EditText nome;
    private EditText email;
    private EditText sexo;
    private EditText cpf;

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

        preferencias = new Preferencias(this);

        final String idUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        dataNasc = findViewById(R.id.dataPerfil);
        cidade = findViewById(R.id.cidadePerfil);
        nome = findViewById(R.id.nomePerfil);
        email = findViewById(R.id.emailPerfil);
        cpf = findViewById(R.id.cpfPerfil);
        sexo = findViewById(R.id.sexoPerfil);


        button = findViewById(R.id.editarPerfil);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("testePerfil","perfil");

                SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH);
                Date date = null;
                UsuarioDados dados = new UsuarioDados(cidade.getText().toString(),sexo.getText().toString(),"","erro",nome.getText().toString(),"");
                try {
                    date = sdf.parse(dataNasc.getText().toString());
                    dados = new UsuarioDados(cidade.getText().toString(),sexo.getText().toString(),"",String.valueOf(date.getTime()),nome.getText().toString(),"");
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Firebase.getDatabaseReference().child("APP_USUARIOS").child("DADOS").child(idUser).setValue(dados);

                Toast.makeText(PerfilActivity.this, "Informações Salvas com Sucesso", Toast.LENGTH_LONG).show();
                Intent i = new Intent(PerfilActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });


        Firebase.getDatabaseReference().child("APP_USUARIOS").child("REGISTRO").child(idUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()) {
                    usuarioRegistro = dataSnapshot.getValue(UsuarioRegistro.class);

                    //USER
                    //Log.i("testeUSERname",usuarioRegistro.getNome());

                    if (!usuarioRegistro.getNome().isEmpty()) {
                        nome.setText(usuarioRegistro.getNome());
                    }
                    if (!usuarioRegistro.getEmail().isEmpty()) {
                        email.setText(usuarioRegistro.getEmail());
                    }
                    if (!usuarioRegistro.getCpf().isEmpty()) {
                        cpf.setText(usuarioRegistro.getCpf());
                    }

                }

            }
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Firebase.getDatabaseReference().child("APP_USUARIOS").child("DADOS").child(idUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usuarioDados = dataSnapshot.getValue(UsuarioDados.class);

                //USER
                cidade.setText(usuarioDados.getCidade());

                if (usuarioDados.getSexo() == "m") {
                    sexo.setText("Masculino");
                }else if(usuarioDados.getSexo() == "f"){
                    sexo.setText("Feminino");
                }else{
                    sexo.setText(usuarioDados.getSexo());
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




