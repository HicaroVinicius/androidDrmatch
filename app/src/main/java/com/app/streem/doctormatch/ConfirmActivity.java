package com.app.streem.doctormatch;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.streem.doctormatch.Adapter.SpinnerDependenteAdapter;
import com.app.streem.doctormatch.DAO.BD;
import com.app.streem.doctormatch.DAO.Firebase;
import com.app.streem.doctormatch.DAO.Preferencias;
import com.app.streem.doctormatch.Modelo.Consulta;
import com.app.streem.doctormatch.Modelo.DependenteModel;
import com.app.streem.doctormatch.Modelo.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ConfirmActivity extends AppCompatActivity {

    private TextView titularDetails;
    private TextView especialidade;
    private TextView info;
    private TextView valorView;
    private String urlFoto;
    private RoundedImageView fotoMedico;
    private Button confirmarButton;
    private List<DependenteModel> arrayDependentes = new ArrayList<>();

    private RadioGroup radio;
    private RadioButton buttonProprio;
    private RadioButton buttonDependente;
    private ImageView imgAdd;
    private ImageView imgTrocar;



    private Spinner spinner;
    private Preferencias preferencias;

    private String nomeDep;
    private String nomeDependente = null;

    private boolean hasDependente = false;

    private TextView nomeProprio;
    private RoundedImageView fotoProprio;


    RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.proprioConfirm:
                    nomeDep = preferencias.getCHAVE_NOME_USUARIO();
                    //Toast.makeText(ConfirmActivity.this, nomeDep, Toast.LENGTH_SHORT).show();
                    nomeProprio.setVisibility(View.VISIBLE);
                    fotoProprio.setVisibility(View.VISIBLE);
                    spinner.setVisibility(View.INVISIBLE);
                    radio.setVisibility(View.GONE);
                    imgAdd.setVisibility(View.GONE);

                    break;
                case R.id.outroConfirm:
                    nomeDep = nomeDependente;
                        //Toast.makeText(ConfirmActivity.this, nomeDep, Toast.LENGTH_SHORT).show();
                        spinner.setVisibility(View.VISIBLE);
                        nomeProprio.setVisibility(View.INVISIBLE);
                        fotoProprio.setVisibility(View.INVISIBLE);
                    radio.setVisibility(View.GONE);
                    imgAdd.setVisibility(View.GONE);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferencias = new Preferencias(this);
        setContentView(R.layout.activity_confirm);

        ImageView voltar = findViewById(R.id.imageView10);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


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

        confirmarButton = findViewById(R.id.buttonConfirmar);
        radio =  findViewById(R.id.radioGroupConfirm);
        radio.setOnCheckedChangeListener(onCheckedChangeListener);
        buttonProprio = findViewById( R.id.proprioConfirm);
        buttonDependente = findViewById(R.id.outroConfirm);
        imgAdd = findViewById(R.id.imgAddConfirm);
        imgTrocar = findViewById(R.id.imgTrocaConfirm);

        imgTrocar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonDependente.isEnabled()){
                radio.setVisibility(View.VISIBLE);
                imgAdd.setVisibility(View.VISIBLE);
                }else{
                    Toast.makeText(ConfirmActivity.this, "Nenhum dependente cadastrado", Toast.LENGTH_SHORT).show();
                }
            }
        });


        nomeProprio = findViewById(R.id.nomeProprioConfirm);
        fotoProprio = findViewById(R.id.fotoPacienteConfirm);

        nomeProprio.setText(preferencias.getCHAVE_NOME_USUARIO());
        nomeDep = preferencias.getCHAVE_NOME_USUARIO();
        preferencias = new Preferencias(this);

        titularDetails = findViewById(R.id.nomeMedicoConfirm);
        especialidade = findViewById(R.id.especialidadeConfirm);
        info = findViewById(R.id.infoConfirm);


        Intent dados = getIntent();
        final String titular = dados.getStringExtra("titular");
        final String end1 = dados.getStringExtra("end1");
        final String end2 = dados.getStringExtra("end2");
        final String registro = dados.getStringExtra("registro");
        final String classif = dados.getStringExtra("classif");
        final String keyMedico = dados.getStringExtra("key");
        final String data = dados.getStringExtra("data");
        final String dataFormatt = dados.getStringExtra("dataFormatt");
        final String dataFormatt2 = dados.getStringExtra("dataFormatt2");
        final String dayWeek = dados.getStringExtra("dayWeek");
        final String url = dados.getStringExtra("url");
        final String hora = dados.getStringExtra("hora");
        final String keyHora = dados.getStringExtra("keyHora");
        final String nome = dados.getStringExtra("nome");
        final String cidade = dados.getStringExtra("cidade");
        final String estado = dados.getStringExtra("estado");
        final String espec = dados.getStringExtra("espec");

        Picasso.with(getApplicationContext()).load(url).into(fotoMedico);

        titularDetails.setText(titular);
        especialidade.setText(preferencias.getCHAVE_ESPECIALIDADE());
        final String inf = dayWeek+", "+dataFormatt2+" às "+hora;
        info.setText(inf);


        spinner = findViewById(R.id.spinnerDependenteConfirm);

        final SpinnerDependenteAdapter spinnerAdapter = new SpinnerDependenteAdapter(this,arrayDependentes);

        spinner.setAdapter(spinnerAdapter);

        Firebase.getDatabaseReference().child("USUARIO").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("DEPENDENTES").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                if (!dataSnapshot.hasChildren()){
                    buttonDependente.setEnabled(false);
                }else{
                    for (DataSnapshot data : dataSnapshot.getChildren()) {

                        if(buttonProprio.isEnabled()) {
                            buttonDependente.setEnabled(true);
                        }

                        Log.i("TESTE",data.getValue().toString());
                        DependenteModel dep = data.getValue(DependenteModel.class);
                        Log.i("TESTEDEP",dep.getKEY()+"-"+dep.getNOME());
                        arrayDependentes.add(dep);
                        nomeDependente = dep.getNOME();
                        spinnerAdapter.notifyDataSetChanged();

                    }
                }


            }
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nomeDependente = arrayDependentes.get(position).getNOME();
                nomeDep = nomeDependente;
                // Toast.makeText(DetailActivity.this,nome, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        confirmarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(ConfirmActivity.this, nomeDep, Toast.LENGTH_SHORT).show();

                if(buttonProprio.isChecked()||buttonDependente.isChecked()){


                    AlertDialog.Builder confirm = new AlertDialog.Builder(ConfirmActivity.this);
                    confirm.setTitle("Confirmar Agendamento");
                    confirm.setIcon(R.drawable.ic_done_black_24dp).setMessage(inf).setCancelable(true);
                    confirm.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });

                    confirm.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            confirmar(data,keyMedico,cidade,estado,espec,keyHora,nomeDep,titular,inf);
                            Log.i("testeConfirm",data+keyMedico+cidade+estado+espec+keyHora+nomeDep+titular+hora+dataFormatt);
                            Intent intent = new Intent(ConfirmActivity.this,AgendConcluido.class);
                            startActivity(intent);

                        }
                    });

                    AlertDialog alertDialog = confirm.create();
                    alertDialog.show();
                }else{
                    Toast.makeText(ConfirmActivity.this, "Selecione um Paciente", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    public void confirmar(String data, String keyMedico, String cidade, String estado, String espec, String keyHora, final String nomeUser,String nomeMedico, String info){

        Log.i("testeRemove",estado+"-"+cidade+"-"+espec+"-"+data+"-"+keyMedico+"-"+keyHora);

       // Firebase.getDatabaseReference().child("VAGAS").child(estado).child(cidade).child(espec).child(data).child(keyMedico).child(keyHora).removeValue();

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

        final BD bd = new BD(this);

        Firebase.getDatabaseReference().child("CLIENTES").child(keyMedico).child("AGENDAMENTO").child(data).child(keyHora).child("key_cliente").setValue(preferencias.getCHAVE_INDENTIFICADOR());

        Firebase.getDatabaseReference().child("CLIENTES").child(keyMedico).child("AGENDAMENTO").child(data).child(keyHora).child("nome").setValue(nomeUser);

        String key = Firebase.getDatabaseReference().child("USUARIO").child(preferencias.getCHAVE_INDENTIFICADOR()).child("CONSULTA").push().getKey();
        Consulta nova = new Consulta(keyMedico,data,keyHora,nomeUser,nomeMedico,info,key,preferencias.getCHAVE_ESPECIALIDADE());
        //inserindo no Sqlite
        bd.inserirConsulta(nova);
        Firebase.getDatabaseReference().child("USUARIO").child(preferencias.getCHAVE_INDENTIFICADOR()).child("CONSULTA").child(key).setValue(nova);

        Intent intent = new Intent(ConfirmActivity.this,MainActivity.class);
        startActivity(intent);
        finish();

    }

}
