package com.app.streem.doctormatch;

import android.app.DatePickerDialog;
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

    private String cidade,espec,estado,n;

    private List<ResultModel> medicos = new ArrayList<>();
    private ResultModel med;
    private List<Consulta> consultas = new ArrayList<>();
    private Preferencias preferencias;
    private Calendar myCalendar;
    private TextView data;

    private void updateLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        data.setText(sdf.format(myCalendar.getTime()));
        preferencias.setCHAVE_DATA(sdf.format(myCalendar.getTime()));
        try {
            buscarMedicos(cidade,estado,espec);
        } catch (ParseException e) {
            e.printStackTrace();
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
                new DatePickerDialog(ResultActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        semRegistro = findViewById(R.id.semRegistroResultID);

        showLoadingAnimation();
        preferencias = new Preferencias(this);
        recyclerView = findViewById(R.id.RecyclerViewMedico);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        cidade = preferencias.getCHAVE_CIDADE().replace(" ","");
        estado = preferencias.getCHAVE_ESTADO().replace(" ","");
        espec = preferencias.getCHAVE_ESPECIALIDADE().replace(" ","");

        try {
            buscarMedicos(cidade,estado,espec);
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

    //carrega lista
    public void buscarMedicos(String cidade,String estado,String espec) throws ParseException {

        showLoadingAnimation();
        Toast.makeText(this,"Carregando... Aguarde",Toast.LENGTH_LONG).show();
        medicos.clear();

        medicos.clear();

        adapter = new ResultAdapter(medicos, this);

        adapter.notifyDataSetChanged();

        recyclerView.setAdapter(adapter);

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date d = format.parse(preferencias.getCHAVE_DATA());
       // final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        //Toast.makeText(this,"busca",Toast.LENGTH_SHORT).show();
        Log.i("dataTESTE",String.valueOf(d.getTime()));
        Firebase.getDatabaseReference().child("VAGAS").child(estado).child(cidade).child(espec).child(String.valueOf(d.getTime())).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (!dataSnapshot.hasChildren()){
                    semRegistro.setVisibility(View.VISIBLE);
                   // Toast.makeText(getApplicationContext(),"Nenhum Médico encontrado",Toast.LENGTH_LONG).show();
                }else{

                for (DataSnapshot data : dataSnapshot.getChildren()){

                    String key = data.getKey();
                    Log.i("TESTEMED",data.getKey().concat("fora"));

                    Firebase.getDatabaseReference().child("CLIENTES").child(key).child("DADOS").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            ResultModel med = dataSnapshot.getValue(ResultModel.class);
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
                semRegistro.setVisibility(View.INVISIBLE);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

}