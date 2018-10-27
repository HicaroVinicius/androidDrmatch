package com.app.streem.doctormatch;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.app.streem.doctormatch.Modelo.AgendamentoGeral;
import com.app.streem.doctormatch.Modelo.AgendamentoMedico;
import com.app.streem.doctormatch.Modelo.Consulta;
import com.app.streem.doctormatch.Modelo.DependenteModel;
import com.app.streem.doctormatch.Modelo.Estado;
import com.app.streem.doctormatch.Modelo.Medico;
import com.app.streem.doctormatch.Modelo.ResultModel;
import com.app.streem.doctormatch.Modelo.UsuarioRegistro;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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

    private boolean isConclusion = false;

    private Spinner spinner;
    private Preferencias preferencias;

    private String nomeDep;
    private String nomeDependente;

    private boolean hasDependente = false;

    private TextView nomeProprio;
    private RoundedImageView fotoProprio;


    private boolean dependente = false;
    private String cpfDependente;


    RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.proprioConfirm:
                    nomeDep = preferencias.getInfo("nome");
                    dependente = false;
                    Log.i("TESTE-confirmNome",nomeDep);
                    nomeProprio.setVisibility(View.VISIBLE);
                    fotoProprio.setVisibility(View.VISIBLE);
                    spinner.setVisibility(View.INVISIBLE);
                    radio.setVisibility(View.GONE);
                    imgAdd.setVisibility(View.GONE);

                    break;
                case R.id.outroConfirm:
                    nomeDep = nomeDependente;
                    Log.i("TESTE-confirmNome",nomeDep);
                    cpfDependente = arrayDependentes.get(0).getCpf();
                    dependente = true;
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

        TextView voltarText = findViewById(R.id.textView6);
        voltarText.setOnClickListener(new View.OnClickListener() {
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

        nomeProprio.setText(preferencias.getInfo("nome"));
        nomeDep = preferencias.getInfo("nome");
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
        final String fpag = dados.getStringExtra("fpag");
        final String valor = dados.getStringExtra("valor");
        final String mili = dados.getStringExtra("mili");
        final String KEY_AGEND = dados.getStringExtra("KEY_AGEND");
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

        String uid = FirebaseAuth.getInstance().getUid();
        String dtcont_dependente = preferencias.getInfo("dtcont_dependente");
        Log.i("TESTcont_dependente",dtcont_dependente);
        Firebase.getDatabaseReference().child("APP_USUARIOS").child("DEPENDENTES").child(uid).orderByChild("dt_cont").startAt(dtcont_dependente).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("TESTEDEP",dataSnapshot.toString());

                if (!dataSnapshot.hasChildren()){
                    buttonDependente.setEnabled(false);
                    Log.i("TESTEDEP","2");
                }else{
                    for (DataSnapshot data : dataSnapshot.getChildren()) {

                        DependenteModel dep = data.getValue(DependenteModel.class);
                        Log.i("TESTEDEP",dep.getId()+"-"+dep.getNome());
                        BD bd = new BD(getApplicationContext());
                        bd.inserirDependente(dep);
//                        arrayDependentes.add(dep);
//                        nomeDependente = dep.getNome();
//                        spinnerAdapter.notifyDataSetChanged();
                        Date dataA = new Date();
                        preferencias.setInfo("dtcont_dependente", String.valueOf(dataA.getTime()));
                        Log.i("TESTEdtcont_dependente", String.valueOf(dataA.getTime()));

                    }
                }
                BD bd = new BD(getApplicationContext());
                arrayDependentes.clear();
                DependenteModel dependenteModel = new DependenteModel("","-Selecione um Dependente-","","","","");
                arrayDependentes.add(0,dependenteModel);
                arrayDependentes.addAll(bd.buscarDependente()) ;
                if(arrayDependentes.size()>1){
                    nomeDependente = arrayDependentes.get(1).getNome();
                    if(buttonProprio.isEnabled()) {
                        buttonDependente.setEnabled(true);
                        Log.i("TESTEDEP","1");
                    }
                }
                spinnerAdapter.notifyDataSetChanged();

//
//                Iterator<DependenteModel> iterator = arrayDependentes.iterator();
//                while(iterator.hasNext()) {
//                    DependenteModel m = iterator.next();
//                    Log.i("TesteDepBusca",m.getNome());
//                }

            }
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    nomeDependente = arrayDependentes.get(position).getNome();
                    Log.i("TESTEDEP-click",arrayDependentes.get(position).getNome());
                    nomeDep = nomeDependente;
                    cpfDependente = arrayDependentes.get(position).getCpf();
                    // Toast.makeText(DetailActivity.this,nome, Toast.LENGTH_SHORT).show();
                    hasDependente = true;
                }else{
                    hasDependente = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        confirmarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(ConfirmActivity.this, nomeDep, Toast.LENGTH_SHORT).show();

                if(buttonProprio.isChecked()||hasDependente){


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
                            ConnectivityManager cm =
                                    (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

                            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                            final boolean isConnected = activeNetwork != null &&
                                    activeNetwork.isConnectedOrConnecting();

                            if(isConnected){
                                confirmar(data,keyMedico,cidade,estado,espec,keyHora,cpfDependente,titular,inf,hora,nomeDep,fpag,valor,mili);
                                Log.i("testeConfirm",data+keyMedico+cidade+estado+espec+keyHora+nomeDep+titular+hora+dataFormatt);
                                Intent intent = new Intent(ConfirmActivity.this,AgendConcluido.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(getApplicationContext(), "Necessário conexão com a internet", Toast.LENGTH_LONG).show();
                            }

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

    public void confirmar(String data, String keyMedico, String cidade, String estado, String espec, String keyHora, String cpfDep,String nomeMedico, String info,String hora,String nomeDep,String fpag,String valor, String mili){

        Log.i("testeRemove",estado+"-"+cidade+"-"+espec+"-"+data+"-"+keyMedico+"-"+keyHora);
        preferencias = new Preferencias(getApplicationContext());
        String key_clinic = preferencias.getInfo("key_clinic");
        String key_medico = preferencias.getInfo("key_medico");
        String especialidade = preferencias.getCHAVE_ESPECIALIDADE();
        String tipo = preferencias.getInfo("tipo");

        Date dataA = new Date();

        String uid = preferencias.getInfo("id");
        String cpf;
        if(dependente == true){
            cpf = cpfDep;
            Log.i("testeConfirmDependente","Agendando para Dependente - "+cpf);
        }else {
            cpf = preferencias.getInfo("cpf");
        }

        AgendamentoGeral agendamentoGeral = new AgendamentoGeral(String.valueOf(dataA.getTime()),keyHora,key_medico,"","2");
        Firebase.getDatabaseReference().child("CRM").child(key_clinic).child("AGENDAMENTO").child("GERAL").child(keyHora).setValue(agendamentoGeral);

        AgendamentoMedico agendamentoMedico = new AgendamentoMedico(cpf,hora,keyHora,nomeDep,uid,mili,"2",tipo,"2",valor,fpag);
        Firebase.getDatabaseReference().child("CRM").child(key_clinic).child("AGENDAMENTO").child("MEDICO").child(key_medico).child(data).child(keyHora).setValue(agendamentoMedico);

        String key = Firebase.getDatabaseReference().child("APP_USUARIOS").child("CONSULTAS").child(uid).push().getKey();
        //KEY,  KEY_CLINIC,  KEY_MEDICO,  NOME_MEDICO,  DT_AGEND,  KEY_AGEND,  HORA,  ESPECIALIDADE,  STATUS,  DT_CONT
        Consulta nova = new Consulta(key,key_clinic,key_medico,nomeMedico,data,keyHora,hora,especialidade,"1",String.valueOf(dataA.getTime()),cpf,valor);

        //inserindo no Sqlite
        BD bd = new BD(getApplicationContext());
        bd.inserirConsulta(nova);

        Firebase.getDatabaseReference().child("APP_USUARIOS").child("CONSULTAS").child(uid).child(key).setValue(nova);

        isConclusion = true;

        Intent intent = new Intent(ConfirmActivity.this,AgendConcluido.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder confirm = new AlertDialog.Builder(ConfirmActivity.this);
        confirm.setTitle("Deseja sair sem confirmar?");
        confirm.setMessage("Saindo sem confirmar, não será possível garantir sua vaga.").setCancelable(true);
        confirm.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("testeConfirm","mudando status");
                return;
            }
        });

        confirm.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent dados = getIntent();
                final String data = dados.getStringExtra("data");
                final String keyHora = dados.getStringExtra("keyHora");
                preferencias = new Preferencias(getApplicationContext());
                String key_clinic = preferencias.getInfo("key_clinic");
                String key_medico = preferencias.getInfo("key_medico");

                Log.i("testeConfirm","mudando status"+" - data -"+data+" - keyHora -"+keyHora+" - key_clinic -"+key_clinic+" - key_medico -"+key_medico);

                FirebaseDatabase.getInstance().getReference().child("CRM").child(key_clinic).child("AGENDAMENTO").child("MEDICO").child(key_medico).child(data).child(keyHora).child("status").setValue("1");

                Intent intent = new Intent(ConfirmActivity.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

        AlertDialog alertDialog = confirm.create();
        alertDialog.show();


    }

    @Override
    protected void onStop() {

        super.onStop();

        if(isConclusion==false){

            Toast.makeText(this, "Agendamento cancelado...", Toast.LENGTH_SHORT).show();

            Intent dados = getIntent();
            final String data = dados.getStringExtra("data");
            final String keyHora = dados.getStringExtra("keyHora");
            preferencias = new Preferencias(getApplicationContext());
            String key_clinic = preferencias.getInfo("key_clinic");
            String key_medico = preferencias.getInfo("key_medico");

            Log.i("testeConfirm","mudando status"+" - data -"+data+" - keyHora -"+keyHora+" - key_clinic -"+key_clinic+" - key_medico -"+key_medico);

            FirebaseDatabase.getInstance().getReference().child("CRM").child(key_clinic).child("AGENDAMENTO").child("MEDICO").child(key_medico).child(data).child(keyHora).child("status").setValue("1");

            Intent intent = new Intent(ConfirmActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }


    }

}
