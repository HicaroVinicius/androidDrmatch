package com.app.streem.doctormatch;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.streem.doctormatch.DAO.BD;
import com.app.streem.doctormatch.DAO.SDFormat;
import com.app.streem.doctormatch.Modelo.AgendamentoMedico;
import com.app.streem.doctormatch.Modelo.Consulta;
import com.app.streem.doctormatch.DAO.Firebase;
import com.app.streem.doctormatch.DAO.Preferencias;
import com.app.streem.doctormatch.Adapter.ResultAdapter;
import com.app.streem.doctormatch.Modelo.Especialidade;
import com.app.streem.doctormatch.Modelo.Estado;
import com.app.streem.doctormatch.Modelo.Medico;
import com.app.streem.doctormatch.Modelo.ResultModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class ResultActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private TextView semRegistro;
    private List<ResultModel> resultModels = new ArrayList<>();
    private ResultModel med;
    private List<Consulta> consultas = new ArrayList<>();
    private Preferencias preferencias;
    private Calendar myCalendar;
    private TextView data;
    private DatePickerDialog dataPicker;
    private ResultModel  value;
    public int contador = 0;
    public int maximo = 0;

    private ProgressBar progress_bar;

    private SDFormat sdFormat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        ImageView voltar = findViewById(R.id.imageView10);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        progress_bar = findViewById(R.id.progress_bar);

        TextView voltarText = findViewById(R.id.textView44);
        voltarText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        data = findViewById(R.id.dataSelResultID);

        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                semRegistro.setVisibility(View.INVISIBLE);
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataPicker = new DatePickerDialog(ResultActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dataPicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                dataPicker.show();
            }
        });

        semRegistro = findViewById(R.id.semRegistroResultID);
        semRegistro.setVisibility(View.INVISIBLE);

        preferencias = new Preferencias(this);

        final String idEstado = preferencias.getInfo("idEstado");
        final String idCidade = preferencias.getInfo("idCidade");
        final String espec = preferencias.getCHAVE_ESPECIALIDADE().replace(" ","");

        recyclerView = findViewById(R.id.RecyclerViewMedico);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final BD bd = new BD(this);
        String dtcont_medico = preferencias.getInfo("dtcont_medico");
        Log.i("TESTEMAIN-dtcont_medico",String.valueOf(dtcont_medico));

        Firebase.getDatabaseReference().child("APP_ATUACAO").child("MEDICOS").orderByChild("dt_cont").startAt(dtcont_medico).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.hasChildren()){
                    Log.i("testeBDvalue1","noChildMED");
                }
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Medico value = data.getValue(Medico.class);
                    Log.i("testeBDvalue2",value.getId().toString());
                    bd.inserirMedico(value);
                    Log.i("TESTEMA_medico",value.getId());
                    Date dataA = new Date();
                    preferencias.setInfo("dtcont_medico",String.valueOf(dataA.getTime()));
                    Log.i("TESTEMA D-dtcont_medico",String.valueOf(dataA.getTime()));


                }

                try {
                    buscarMedicos(idEstado,idCidade,espec);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        data.setText(preferencias.getCHAVE_DATA());
    }

    public Boolean dataAtual(String data){
        Calendar calendar1 = Calendar.getInstance();
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
        formatter1.setTimeZone(TimeZone.getTimeZone("GMT-03:00"));
        String currentDate = formatter1.format(calendar1.getTime());
       // String dataSel = formatter1.format(preferencias.getCHAVE_DATA());

        if(currentDate.compareTo(data) <= 0 ){
           return true;
        }else{
            return false;
        }
    }

    //carrega lista
    public void buscarMedicos(final String cidade, final String estado, final String espec) throws ParseException {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT-03:00"));
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy, HH:mm", new Locale("pt","BR"));
        format.setTimeZone(TimeZone.getTimeZone("GMT-03:00"));
        Log.i("teste entrada data: ", preferencias.getCHAVE_DATA().concat(", 00:00") );
        final Date d = format.parse( preferencias.getCHAVE_DATA().concat(", 00:00") );
        final String dataFormatt = format.format(d.getTime());
        SimpleDateFormat format2 = new SimpleDateFormat("dd/MM");
        format2.setTimeZone(TimeZone.getTimeZone("GMT-03:00"));
        final Date d2 = format.parse(preferencias.getCHAVE_DATA().concat(", 00:00"));
        final String dataFormatt2 = format2.format(d2.getTime());

        String dataNova = dataFormatt;

        resultModels.clear();
        adapter = new ResultAdapter(resultModels, this, new ResultAdapter.OnItemClickListener() {
            @Override public void onItemClick(ResultModel item) {

           //Toast.makeText(getApplicationContext(),"KEY: "+item.getKEY()+"/DATA: "+preferencias.getCHAVE_DATA(),Toast.LENGTH_SHORT).show();
            Intent newPage = new Intent(ResultActivity.this,DetailActivity.class);
            Log.i("testeResultModel",item.toString());
            newPage.putExtra("titular",item.getTitular().toString());
            newPage.putExtra("end1",item.getEndereco1().toString());
            newPage.putExtra("end2",item.getEndereco2().toString());
            newPage.putExtra("registro",item.getRegistro().toString());
            newPage.putExtra("cartao",item.getCartao().toString());
            newPage.putExtra("cheque",item.getCheque().toString());
            newPage.putExtra("dinheiro",item.getDinheiro().toString());
            newPage.putExtra("plano",item.getPlano().toString());
           // newPage.putExtra("classif",item.getClassif().toString());
            newPage.putExtra("url",item.getUrl());
            newPage.putExtra("key",item.getId());
            newPage.putExtra("valor",item.getValor());
            newPage.putExtra("dataFormatt",dataFormatt);
            newPage.putExtra("dataFormatt2",dataFormatt2);
            newPage.putExtra("dataDisp",item.getData().toString());
            newPage.putExtra("cidade",cidade);
            newPage.putExtra("estado",estado);
            newPage.putExtra("espec",espec);

            Log.i("TESTERESULT-key_clinic",item.getKey_clinic());
            preferencias.setInfo("key_clinic",item.getKey_clinic());
            preferencias.setInfo("key_medico",item.getKey_medico());

            newPage.putExtra("data",String.valueOf(d.getTime()));
            Log.i("TESTERESULT-data",String.valueOf(d.getTime()));
            startActivity(newPage);

            }} );

        adapter.notifyDataSetChanged();

        recyclerView.setAdapter(adapter);

        BD bd = new BD(this);
        ArrayList<Medico> medicosSQL = bd.buscarMedicos(cidade,estado,espec);
        ArrayList<String> medicos = new ArrayList<>();
        Iterator<Medico> iterator = medicosSQL.iterator();
        int tamanhoArray = medicosSQL.size();
        while(iterator.hasNext()) {
            Medico m = iterator.next();
            Log.i("TesteMedicosBusca",m.getId());
            contador = 1;
            buscaDisponibilidade(m, d, "dataAtual", dataFormatt,tamanhoArray);
        }


    }

    public Boolean buscaDisponibilidade(final Medico key, final Date d, final String dataFormatt, final String dataN,final int tamanhoArray){
        Log.i("TESTEinput",key.getKey_clinica()+"-"+key.getId()+"-"+String.valueOf(d.getTime()));
        Firebase.getDatabaseReference().child("CRM").child(key.getKey_clinica()).child("AGENDAMENTO").child("MEDICO").child(key.getId()).child(String.valueOf(d.getTime())).orderByChild("status").equalTo("1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChildren()){
                    //vaga indisponível, busca o proximo o dia
                    if(contador < (7*tamanhoArray)){

                        String dataString = String.valueOf(d.getTime());

                        long data = Long.parseLong(dataString)+(24 * 60 * 60 * 1000);
                        Date novaData = new Date(data);

                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                        format.setTimeZone(TimeZone.getTimeZone("GMT-03:00"));

                        final String novoFormatt = format.format(novaData.getTime());

                        buscaDisponibilidade(key,novaData,novoFormatt,novoFormatt,tamanhoArray);
                        Log.i("TESTEMEDnewDAta",String.valueOf(novoFormatt));
                        Log.i("TESTEMEDnewDAtaCont",String.valueOf(contador));
                        contador++;

                    }else{
                        semRegistro.setVisibility(View.VISIBLE);
                        progress_bar.setVisibility(View.INVISIBLE);
                        return;
                    }

                }else{
                    for(DataSnapshot data : dataSnapshot.getChildren()){
                        AgendamentoMedico valueA = data.getValue(AgendamentoMedico.class);
                        Log.i("teste Horas: ",valueA.getHora());
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                        Date hora = Calendar.getInstance().getTime();
                        String horaAtual = sdf.format(hora);
                        Log.i("teste horaAtual: ",horaAtual);
                        if(valueA.getHora().compareTo(horaAtual)>0){

                            Log.i("TESTEmedRESULTdadoos",key.getKey_clinica()+"-"+key.getId());
                            Firebase.getDatabaseReference().child("CRM").child(key.getKey_clinica()).child("CONFIG").child("DADOS_MED_LAB").orderByKey().equalTo(key.getId()).limitToFirst(1).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Log.i("TESTEmedRESULTdadoos", dataSnapshot.toString());
                                    if (!dataSnapshot.hasChildren()) {
                                        Log.i("testeBDvalue1", "Medico sem Dados - CRM");
                                    } else {

                                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                                            value = data.getValue(ResultModel.class);
                                            Log.i("testeBDvalue3", value.getTitular() + " - cont: " + value.getDt_cont() + " - Pref: " + preferencias.getInfo("dtcont_medicoDados"));

                                        }

                                        ResultModel med = value;
                                        ResultModel medico = new ResultModel();
                                        try {
                                            medico.setRegistro(med.getRegistro());
                                            medico.setTitular(med.getTitular());
                                            medico.setLocal(med.getLocal());
                                            medico.setEndereco1(med.getEndereco1());
                                            medico.setEndereco2(med.getEndereco2());
                                            medico.setUrl(med.getUrl());
                                            medico.setId(med.getId());
                                            medico.setPlano(med.getPlano());
                                            medico.setCheque(med.getCheque());
                                            medico.setDinheiro(med.getDinheiro());
                                            medico.setCartao(med.getCartao());
                                        } catch (Exception e) {
                                            Log.i("TESTERESULT-ERRO", e.getMessage());
                                        }
                                        medico.setValor(dataFormatt);
                                        medico.setData(dataN);
                                        medico.setKey_clinic(key.getKey_clinica());
                                        medico.setKey_medico(key.getId());
                                        resultModels.add(medico);
                                        adapter.notifyDataSetChanged();

                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                            break;
                        }

                        }



                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });


        return true;

    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-03:00"));
        if(!dataAtual(sdf.format(myCalendar.getTime()))){
            return;
        }else{
            data.setText(sdf.format(myCalendar.getTime()));
            preferencias.setCHAVE_DATA(sdf.format(myCalendar.getTime()));
            try {
                buscarMedicos(preferencias.getInfo("idCidade"),preferencias.getInfo("idEstado"), preferencias.getCHAVE_ESPECIALIDADE().replace(" ",""));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

}