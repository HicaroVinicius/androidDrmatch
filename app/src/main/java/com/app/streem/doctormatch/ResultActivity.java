package com.app.streem.doctormatch;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.streem.doctormatch.Modelo.Consulta;
import com.app.streem.doctormatch.DAO.Firebase;
import com.app.streem.doctormatch.DAO.Preferencias;
import com.app.streem.doctormatch.Adapter.ResultAdapter;
import com.app.streem.doctormatch.Modelo.ResultModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ResultActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private TextView semRegistro;

    private List<ResultModel> medicos = new ArrayList<>();
    private ResultModel med;
    private List<Consulta> consultas = new ArrayList<>();
    private Preferencias preferencias;
    private Calendar myCalendar;
    private TextView data;
    private DatePickerDialog dataPicker;



    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));


        if(!dataAtual(sdf.format(myCalendar.getTime()))){
            Toast.makeText(this,"Selecione uma data válida",Toast.LENGTH_LONG).show();
            return;
        }else{
            data.setText(sdf.format(myCalendar.getTime()));
            preferencias.setCHAVE_DATA(sdf.format(myCalendar.getTime()));

            try {
                buscarMedicos(preferencias.getCHAVE_CIDADE().replace(" ",""),preferencias.getCHAVE_ESTADO().replace(" ",""), preferencias.getCHAVE_ESPECIALIDADE().replace(" ",""));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);



        data = findViewById(R.id.dataSelResultID);

        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

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

        showLoadingAnimation();
        preferencias = new Preferencias(this);
        recyclerView = findViewById(R.id.RecyclerViewMedico);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




        try {
            buscarMedicos(preferencias.getCHAVE_CIDADE().replace(" ",""),preferencias.getCHAVE_ESTADO().replace(" ",""), preferencias.getCHAVE_ESPECIALIDADE().replace(" ",""));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        data.setText(preferencias.getCHAVE_DATA());
    }

    //show
    public void showLoadingAnimation()
    {
        RelativeLayout pageLoading = (RelativeLayout) findViewById(R.id.main_layoutPageLoading);
        pageLoading.setVisibility(View.VISIBLE);
    }


    //hide
    public void hideLoadingAnimation()
    {
        RelativeLayout pageLoading = (RelativeLayout) findViewById(R.id.main_layoutPageLoading);
        pageLoading.setVisibility(View.GONE);
    }

    public Boolean dataAtual(String data){
        Calendar calendar1 = Calendar.getInstance();
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
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
        Toast.makeText(getApplicationContext(),"Carregando... Aguarde",Toast.LENGTH_LONG).show();


        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        final Date d = format.parse(preferencias.getCHAVE_DATA());
        final String dataFormatt = format.format(d.getTime());

        showLoadingAnimation();
        medicos.clear();
        adapter = new ResultAdapter(medicos, this, new ResultAdapter.OnItemClickListener() {
            @Override public void onItemClick(ResultModel item) {

           //Toast.makeText(getApplicationContext(),"KEY: "+item.getKEY()+"/DATA: "+preferencias.getCHAVE_DATA(),Toast.LENGTH_SHORT).show();
            Intent newPage = new Intent(ResultActivity.this,DetailActivity.class);
            newPage.putExtra("titular",item.getTitular().toString());
            newPage.putExtra("end1",item.getEndereco1().toString());
            newPage.putExtra("end2",item.getEndereco2().toString());
            newPage.putExtra("registro",item.getRegistro().toString());
            newPage.putExtra("classif",item.getClassif().toString());
            newPage.putExtra("url",item.getUrl());
            newPage.putExtra("key",item.getKey());
            newPage.putExtra("dataFormatt",dataFormatt);
            newPage.putExtra("cidade",cidade);
            newPage.putExtra("estado",estado);
            newPage.putExtra("espec",espec);

            newPage.putExtra("data",String.valueOf(d.getTime()));
            startActivity(newPage);

            }} );

        adapter.notifyDataSetChanged();

        recyclerView.setAdapter(adapter);


        Log.i("dataTESTE",String.valueOf(estado+cidade+espec));
        Log.i("dataTESTE",String.valueOf(d.getTime()));
        Firebase.getDatabaseReference().child("VAGAS").child(estado).child(cidade).child(espec).child(String.valueOf(d.getTime())).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (!dataSnapshot.hasChildren()){
                    semRegistro.setVisibility(View.VISIBLE);
                   // Toast.makeText(getApplicationContext(),"Nenhum Médico encontrado",Toast.LENGTH_LONG).show();
                }else{

                for (DataSnapshot data : dataSnapshot.getChildren()){

                    final String key = data.getKey();
                    Log.i("TESTEMED",data.getKey().concat("fora"));

                    Firebase.getDatabaseReference().child("CLIENTES").child(key).child("DADOS").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            ResultModel med = dataSnapshot.getValue(ResultModel.class);
                            Log.i("TESTE RESULT",dataSnapshot.getValue().toString());
                            med.setKey(key);
                            medicos.add(med);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.i("TESTEMED",databaseError.getDetails());
                        }
                    });

                }
                hideLoadingAnimation();
                semRegistro.setVisibility(View.GONE);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

}