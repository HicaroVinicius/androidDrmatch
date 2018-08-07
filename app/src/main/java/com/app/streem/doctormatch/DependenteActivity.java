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
import android.widget.RadioGroup;
import android.widget.Toast;

import com.app.streem.doctormatch.DAO.Firebase;
import com.app.streem.doctormatch.DAO.Preferencias;
import com.app.streem.doctormatch.Modelo.Consulta;
import com.app.streem.doctormatch.Modelo.DependenteModel;
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

public class DependenteActivity extends AppCompatActivity {

    private EditText dataNasc;
    private EditText telefone;
    private EditText nome;
    private EditText email;
    private EditText cpf;

    private String sexo;

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
        setContentView(R.layout.activity_dependente);

        RadioGroup radioGroup = findViewById(R.id.radioGroupCadID);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radioButtonMasculinoCadID:
                        sexo = "Masculino";
                        break;
                    case R.id.radioButtonFeminidoCadID:
                        sexo = "Feminino";
                        break;
                }
            }
        });

        Intent dados = getIntent();

        final String novo = dados.getStringExtra("novo");
        final String id = dados.getStringExtra("id");

        dataNasc = findViewById(R.id.data);
        nome = findViewById(R.id.nome);
        cpf = findViewById(R.id.cpf);

        if(novo.equals("false")){

            // Create a DateFormatter object for displaying date in specified format.
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            // Create a calendar object that will convert the date and time value in milliseconds to date.
            Calendar calendar = Calendar.getInstance();
            try{
                calendar.setTimeInMillis(Long.valueOf(dados.getStringExtra("data")) );
                String data = formatter.format(calendar.getTime());
                dataNasc.setText(data);
            }catch (Exception e){
                Log.i("Dependente-ERRO1",e.getMessage());
                dataNasc.setText("data inválida");
            }

            nome.setText(dados.getStringExtra("nome"));
            cpf.setText(dados.getStringExtra("cpf"));
            String sexo = dados.getStringExtra("sexo");

            Log.i("Dependente-sexo",sexo);
            if(sexo.toLowerCase().equals("feminino")){
                radioGroup.check(R.id.radioButtonFeminidoCadID);
            }else{
                radioGroup.check(R.id.radioButtonMasculinoCadID);
            }


        }



        preferencias = new Preferencias(this);

        final String uid = preferencias.getInfo("id");




        button = findViewById(R.id.editarPerfil);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sexo.equals("null") | nome.getText().toString().isEmpty() |
                        dataNasc.getText().toString().isEmpty() | (dataNasc.getText().toString().length() < 10)){
                    Toast.makeText(DependenteActivity.this,"Complete os dados!",Toast.LENGTH_LONG).show();
                }else{
                    Intent dados = getIntent();
                    final String novo = dados.getStringExtra("novo");

                    Date dataA = new Date();


                    if(novo.equals("false")){
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH);
                        Date date = null;
                        DependenteModel dep = new DependenteModel(id,nome.getText().toString(),"erro",sexo,cpf.getText().toString(),String.valueOf(dataA.getTime()));
                        try {
                            date = sdf.parse(dataNasc.getText().toString());
                            Log.i("testeDependenteTry1",String.valueOf(date.getTime()));
                            dep = new DependenteModel(id,nome.getText().toString(),String.valueOf(date.getTime()),sexo,cpf.getText().toString(),String.valueOf(dataA.getTime()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                            Log.i("testeDependenteCatch1",e.getMessage());
                        }

                        Firebase.getDatabaseReference().child("APP_USUARIOS").child("DEPENDENTES").child(uid).child(id).setValue(dep);

                        Intent intent = new Intent(DependenteActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(DependenteActivity.this, "Dependente Cadastrado!", Toast.LENGTH_SHORT).show();

                    }else{
                        String key = Firebase.getDatabaseReference().child("APP_USUARIOS").child("DEPENDENTES").child(uid).push().getKey();

                        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH);
                        Date date = null;
                        DependenteModel dep = new DependenteModel(key,nome.getText().toString(),"erro",sexo,cpf.getText().toString(),String.valueOf(dataA.getTime()));
                        try {
                            date = sdf.parse(dataNasc.getText().toString());
                            Log.i("testeDependenteTry2",String.valueOf(date.getTime()));
                            dep = new DependenteModel(key,nome.getText().toString(),String.valueOf(date.getTime()),sexo,cpf.getText().toString(),String.valueOf(dataA.getTime()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                            Log.i("testeDependenteCatch1",e.getMessage());
                        }

                        Firebase.getDatabaseReference().child("APP_USUARIOS").child("DEPENDENTES").child(uid).child(key).setValue(dep);

                        Intent intent = new Intent(DependenteActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(DependenteActivity.this, "Dependente Cadastrado!", Toast.LENGTH_SHORT).show();

                         }
                }
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
                new DatePickerDialog(DependenteActivity.this, date, myCalendar
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




