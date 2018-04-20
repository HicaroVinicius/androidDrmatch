package com.app.streem.doctormatch;

import android.app.DatePickerDialog;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.app.streem.doctormatch.DAO.Preferencias;
import com.google.firebase.database.ServerValue;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FiltroBuscaActivity extends AppCompatActivity {

    private Preferencias preferencias;
    private Calendar myCalendar;
    private TextInputEditText data;
    private TextInputEditText especialidade;
    private TextInputEditText estado;
    private Button buscar;
    private DatePickerDialog dataPicker;
    private TextView cidadeFiltro;
    private TextView dataFiltro;
    private TextView textoFiltro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_busca);

        ImageView voltar = findViewById(R.id.imageView10);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        preferencias = new Preferencias(this);

        Button buttonBuscara = findViewById(R.id.buttonBuscar);

        textoFiltro = findViewById(R.id.textoFiltroBusca);
        cidadeFiltro = findViewById(R.id.cidadeFiltro);
        dataFiltro = findViewById(R.id.dataFiltro);
        buscar = findViewById(R.id.buttonBuscar);

        Intent dadosFiltro = getIntent();
       final String cidade = dadosFiltro.getStringExtra("cidadeDado");
       final String estado = dadosFiltro.getStringExtra("estadoDado");

        if(cidade==null||estado==null){
            cidadeFiltro.setText("Selecione sua Cidade");
        }else{
            cidadeFiltro.setText(cidade.concat(", ").concat(estado));
        }

        dataFiltro.setText("Escolha uma Data");
        preferencias.setCHAVE_DATA(null);

        textoFiltro.setText("Vamos encontrar o ".concat(preferencias.getCHAVE_ESPECIALIDADE()).concat(" mais próximo de você!"));

        buttonBuscara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FiltroBuscaActivity.this, DetailActivity.class);
                startActivity(i);
            }
        });

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

        dataFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataPicker = new DatePickerDialog(FiltroBuscaActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dataPicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                dataPicker.show();
            }
        });


        cidadeFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferencias.setCHAVE_TIPO_BUSCA("2");
               Intent intent = new Intent(FiltroBuscaActivity.this,SeletorActivity.class);
               intent.putExtra("tipo","estado");
               startActivity(intent);
            }
        });


        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cidade==null||estado==null){
                    Toast.makeText(FiltroBuscaActivity.this, "Selecione uma Cidade...", Toast.LENGTH_SHORT).show();
                }else if(preferencias.getCHAVE_DATA().equals("")){
                    Toast.makeText(FiltroBuscaActivity.this, "Selecione uma Data...", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(FiltroBuscaActivity.this,ResultActivity.class);
                    preferencias.setCHAVE_CIDADE(cidade);
                    preferencias.setCHAVE_ESTADO(estado);
                    startActivity(intent);
                }
            }
        });


        /*
        preferencias = new Preferencias(this);

        especialidade = findViewById(R.id.especialidadesFiltroID);
        estado = findViewById(R.id.estadoFiltroID);


        cidade = findViewById(R.id.cidadeFiltroID);
        Button buttonBuscar = findViewById(R.id.buttonBuscarFiltroID);
        data = findViewById(R.id.dataFiltroID);

        if(!preferencias.getCHAVE_TIPO_FILTRO().equals("EXAME")){
           // getSupportActionBar().setTitle("Consultas");
            buttonBuscar.setText("BUSCAR CONSULTAS");
        }else{
       //     getSupportActionBar().setTitle("Exames");
            buttonBuscar.setText("BUSCAR EXAMES");
        }

        especialidade.setText(preferencias.getCHAVE_ESPECIALIDADE());
        estado.setText(preferencias.getCHAVE_ESTADO());
        cidade.setText(preferencias.getCHAVE_CIDADE());


        buttonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (especialidade.getText().toString().isEmpty() |
                        estado.getText().toString().isEmpty() |
                        estado.getText().toString().isEmpty() |
                        cidade.getText().toString().isEmpty() |
                        data.getText().toString().isEmpty()){

                    Toast.makeText(FiltroBuscaActivity.this,"Preencha todos os dados!",Toast.LENGTH_LONG).show();
                }else{
                    preferencias.setCHAVE_DATA(data.getText().toString());
                    if(dataAtual()) {
                        Intent i = new Intent(FiltroBuscaActivity.this, ResultActivity.class);
                        startActivity(i);
                    }else{
                        Toast.makeText(FiltroBuscaActivity.this,"Selecione uma data válida",Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }
        });

        especialidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activitySelecao("1");
            }
        });

        estado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activitySelecao("2");
            }
        });

        cidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(estado.getText().toString().isEmpty()){
                    Toast.makeText(FiltroBuscaActivity.this,"Selecione seu estado!",Toast.LENGTH_LONG).show();
                }else {
                    activitySelecao("3");
                }
            }
        });
    }

    private void activitySelecao(String filtro){
        preferencias.setCHAVE_TIPO_BUSCA(filtro);
        Intent i = new Intent(FiltroBuscaActivity.this, SeletorActivity.class);
        startActivity(i);
    }


    //botao voltar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                finish();
                break;
            default:break;
        }
        return true;
    }

    public Boolean dataAtual(){
        Calendar calendar1 = Calendar.getInstance();
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = formatter1.format(calendar1.getTime());
        //String dataSel = formatter1.format(preferencias.getCHAVE_DATA());

        if(currentDate.compareTo(preferencias.getCHAVE_DATA()) <= 0 ){
            return true;
        }else{
            return false;
        }

    }*/

    }

    private void updateLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));
        dataFiltro.setText(sdf.format(myCalendar.getTime()));
        preferencias.setCHAVE_DATA(dataFiltro.getText().toString());
        Log.i("testePref",preferencias.getCHAVE_DATA());
    }


}
