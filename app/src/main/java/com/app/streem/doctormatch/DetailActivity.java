package com.app.streem.doctormatch;

import android.content.Intent;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.streem.doctormatch.Adapter.SpinnerDependenteAdapter;
import com.app.streem.doctormatch.Adapter.VagasAdapter;
import com.app.streem.doctormatch.DAO.Firebase;
import com.app.streem.doctormatch.DAO.Preferencias;
import com.app.streem.doctormatch.Modelo.DependenteModel;
import com.app.streem.doctormatch.Modelo.VagasModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import noman.weekcalendar.WeekCalendar;
import noman.weekcalendar.listener.OnDateClickListener;

public class DetailActivity extends AppCompatActivity {
    private RadioGroup radio;
    private RadioButton buttonProprio;
    private RadioButton buttonDependente;
    private TextView especialidade;
    private TextView end1Details;
    private TextView end2Details;
    private TextView titularDetails;
    private TextView registroDetails;
    private TextView classifDetails;
    private TextView semHorario;
    private String urlFoto;
    private RoundedImageView fotoMedico;
    private RecyclerView horaView;
    private RecyclerView.Adapter adapter;
    private List<VagasModel> vagasList = new ArrayList<>();
    private List<DependenteModel> arrayDependentes = new ArrayList<>();
    private LinearLayout novoCliente;
    private WeekCalendar weekCalendar;


    private Spinner spinner;
    private Preferencias preferencias;

    private String nome;
    private String nomeDependente = null;

    private boolean hasDependente = false;
    private String dayWeek;
    private String dataNova;
    private String dataFormattNovo;

    /*

    RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.proprioUser:
                    nome = preferencias.getCHAVE_NOME_USUARIO();
                    //Toast.makeText(DetailActivity.this, nome, Toast.LENGTH_SHORT).show();
                    novoCliente.setVisibility(View.GONE);
                    break;
                case R.id.outroUser:
                        nome = nomeDependente;
                        //Toast.makeText(DetailActivity.this, nome, Toast.LENGTH_SHORT).show();
                        novoCliente.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };*/

    public void atualizarHorarios(String data,String key){
        Firebase.getDatabaseReference().child("CLIENTES").child(key).child("AGENDAMENTO").child(data).orderByChild("status").equalTo("Disponível").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                if (!dataSnapshot.hasChildren()){
                    Log.i("TESTE","sem filho");
                    //Toast.makeText(DetailActivity.this, "Nenhum Horário Disponível", Toast.LENGTH_SHORT).show();
                }


                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    Log.i("TESTE",data.getValue().toString());
                    VagasModel vaga = data.getValue(VagasModel.class);
                    Log.i("TESTEVAGA",vaga.getHora());
                    vagasList.add(vaga);
                    adapter.notifyDataSetChanged();

                }
            }
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        Intent dados = getIntent();
        final String titular = dados.getStringExtra("titular");
        final String end1 = dados.getStringExtra("end1");
        final String end2 = dados.getStringExtra("end2");
        final String registro = dados.getStringExtra("registro");
        final String classif = dados.getStringExtra("classif");
        final String key = dados.getStringExtra("key");
        final String data = dados.getStringExtra("data");
        final String dataFormatt = dados.getStringExtra("dataFormatt");
        final String dataFormatt2 = dados.getStringExtra("dataFormatt2");
        final String dataDisp = dados.getStringExtra("dataDisp");
        final String url = dados.getStringExtra("url");
        final String cidade = dados.getStringExtra("cidade");
        final String estado = dados.getStringExtra("estado");
        final String espec = dados.getStringExtra("espec");
        final String valor = dados.getStringExtra("valor");

        dataNova = data;


        setContentView(R.layout.activity_detail);
        fotoMedico = findViewById(R.id.fotoDetails);

        Button buttonAgendar = findViewById(R.id.buttonDetails);

        buttonAgendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailActivity.this, ConfirmActivity.class);
                startActivity(i);
            }
        });

        preferencias = new Preferencias(this);

        titularDetails = findViewById(R.id.nomeDetails);
        registroDetails = findViewById(R.id.registroDetails);
        fotoMedico = findViewById(R.id.fotoDetails);
        end1Details = findViewById(R.id.end1Details);
        end2Details = findViewById(R.id.end2Details);
        especialidade = findViewById(R.id.especialidadeDetails);

        horaView = findViewById(R.id.recyclerViewHoraDetails);

        weekCalendar = findViewById(R.id.weekCalendarDetails);




        horaView.setHasFixedSize(true);
        horaView.setLayoutManager(new LinearLayoutManager(this));

        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
        DateTime dt = formatter.parseDateTime(dataDisp);

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date d = null;
        try {
            d = format.parse(dataDisp);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        weekCalendar.setStartDate(dt);
        atualizaSemana(dt);
        atualizarHorarios(String.valueOf(d.getTime()),key);
        Log.i("testeAtuaHora",String.valueOf(dt));

        dataFormattNovo = dataDisp;

        weekCalendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(DateTime dateTime) {
                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));
               dataFormattNovo = sdf.format(dateTime.toDate());

                atualizaSemana(dateTime);

                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                Date d = null;
                try {
                    d = format.parse(dataFormattNovo);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                dataNova = String.valueOf(d.getTime());
                Log.i("teste1",String.valueOf(d.getTime()));
                //Toast.makeText(DetailActivity.this,"Procurando Vagas para: "+dataFormattNovo, Toast.LENGTH_SHORT).show();
                vagasList.clear();
                adapter.notifyDataSetChanged();
                atualizarHorarios(String.valueOf(d.getTime()),key);

            }

        });


        urlFoto = url;

        Picasso.with(getApplicationContext()).load(urlFoto).into(fotoMedico);

        titularDetails.setText(titular);
        registroDetails.setText(registro);
        end1Details.setText(end1);
        end2Details.setText(end2);
        especialidade.setText(preferencias.getCHAVE_ESPECIALIDADE());

       // Toast.makeText(getApplicationContext(),"Carregando... Aguarde",Toast.LENGTH_SHORT).show();

        adapter = new VagasAdapter(vagasList, getApplicationContext(), new VagasAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(VagasModel item) {
                //Toast.makeText(getApplicationContext(),nome,Toast.LENGTH_SHORT).show();
                Intent confirmar = new Intent(DetailActivity.this,ConfirmActivity.class);
                confirmar.putExtra("titular",titular);
                confirmar.putExtra("end1",end1);
                confirmar.putExtra("end2",end2);
                confirmar.putExtra("registro",registro);
                confirmar.putExtra("classif",classif);
                confirmar.putExtra("url",url);
                confirmar.putExtra("key",key);
                confirmar.putExtra("valor",valor);

                confirmar.putExtra("nome",nome);


                confirmar.putExtra("data",dataNova);
                confirmar.putExtra("dataFormatt",dataFormatt);
                confirmar.putExtra("dataFormatt2",dataFormattNovo);
                confirmar.putExtra("dayWeek",dayWeek);
                confirmar.putExtra("cidade",cidade);
                confirmar.putExtra("estado",estado);
                confirmar.putExtra("espec",espec);



                confirmar.putExtra("hora",item.getHora());
                confirmar.putExtra("keyHora",item.getKey());
                startActivity(confirmar);
            }
        });

        adapter.notifyDataSetChanged();

        horaView.setAdapter(adapter);




    /*
        buttonProprio = findViewById( R.id.proprioUser);
        buttonDependente = findViewById(R.id.outroUser);


        semHorario = findViewById(R.id.semRegistroResultID);

        preferencias =  new Preferencias(this);

        nome = preferencias.getCHAVE_NOME_USUARIO();

        novoCliente = findViewById(R.id.novoCliente);

        radio =  findViewById(R.id.radioGroupUser);
        radio.setOnCheckedChangeListener(onCheckedChangeListener);

        titularDetails = findViewById(R.id.titularDetails);
        registroDetails = findViewById(R.id.registroDetails);
        classifDetails = findViewById(R.id.classifDetails);
        fotoMedico = findViewById(R.id.fotoDetails);
        horaView = findViewById(R.id.recyclerHoraDetail);

        horaView.setHasFixedSize(true);
        horaView.setLayoutManager(new LinearLayoutManager(this));



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
        final String cidade = dados.getStringExtra("cidade");
        final String estado = dados.getStringExtra("estado");
        final String espec = dados.getStringExtra("espec");

        urlFoto = url;
        */
        //Picasso.with(getApplicationContext()).load("http://doctormatch.com.br/app_files/manasses.jpg").into(fotoMedico);
        /*
        titularDetails.setText(titular);
        registroDetails.setText(registro);
        classifDetails.setText(classif);

       // Toast.makeText(getApplicationContext(),"Carregando... Aguarde",Toast.LENGTH_SHORT).show();

        adapter = new VagasAdapter(vagasList, getApplicationContext(), new VagasAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(VagasModel item) {
                //Toast.makeText(getApplicationContext(),nome,Toast.LENGTH_SHORT).show();
                Intent confirmar = new Intent(DetailActivity.this,ConfirmActivity.class);
                confirmar.putExtra("titular",titular);
                confirmar.putExtra("end1",end1);
                confirmar.putExtra("end2",end2);
                confirmar.putExtra("registro",registro);
                confirmar.putExtra("classif",classif);
                confirmar.putExtra("url",url);
                confirmar.putExtra("key",key);

                confirmar.putExtra("nome",nome);

                confirmar.putExtra("data",data);
                confirmar.putExtra("dataFormatt",dataFormatt);
                confirmar.putExtra("cidade",cidade);
                confirmar.putExtra("estado",estado);
                confirmar.putExtra("espec",espec);



                confirmar.putExtra("hora",item.getHora());
                confirmar.putExtra("keyHora",item.getKey());
                startActivity(confirmar);
            }
        });

        adapter.notifyDataSetChanged();

        horaView.setAdapter(adapter);

        Firebase.getDatabaseReference().child("CLIENTES").child(key).child("AGENDAMENTO").child(data).orderByChild("status").equalTo("Disponível").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                if (!dataSnapshot.hasChildren()){
                    Log.i("TESTE","sem filho");
                    buttonDependente.setEnabled(false);
                    buttonProprio.setEnabled(false);
                    semHorario.setVisibility(View.VISIBLE);

                }



                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    semHorario.setVisibility(View.GONE);

                        buttonProprio.setEnabled(true);

                        Log.i("TESTE",data.getValue().toString());
                        VagasModel vaga = data.getValue(VagasModel.class);
                        Log.i("TESTEVAGA",vaga.getHora());
                        vagasList.add(vaga);
                        adapter.notifyDataSetChanged();

                }
            }
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        spinner = findViewById(R.id.spinnerDependentes);

        final SpinnerDependenteAdapter spinnerAdapter = new SpinnerDependenteAdapter(this,arrayDependentes);

        spinner.setAdapter(spinnerAdapter);

        Firebase.getDatabaseReference().child("USUARIO").child(preferencias.getCHAVE_INDENTIFICADOR()).child("DEPENDENTES").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                if (!dataSnapshot.hasChildren()){
                    buttonDependente.setEnabled(false);
                }



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
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nomeDependente = arrayDependentes.get(position).getNOME();
                nome = nomeDependente;
               // Toast.makeText(DetailActivity.this,nome, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
*/


    }

    private void atualizaSemana(DateTime dt) {

        int week = dt.getDayOfWeek();
        Log.i("testeSemana",String.valueOf(week));
        String semana = "";
        switch (week){
            case 1: semana = "Segunda-Feira"; break;
            case 2: semana = "Terça-Feira"; break;
            case 3: semana = "Quarta-Feira"; break;
            case 4: semana = "Quinta-Feira"; break;
            case 5:  semana = "Sexta-Feira"; break;
            case 6:  semana = "Sábado"; break;
            case 7:  semana = "Domingo"; break;
            default: semana = ""; break;
        }

        dayWeek = semana;

    }


}
