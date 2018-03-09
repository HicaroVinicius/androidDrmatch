package com.app.streem.doctormatch;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.app.streem.doctormatch.DAO.Preferencias;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FiltroBuscaActivity extends AppCompatActivity {

    private Preferencias preferencias;
    private Calendar myCalendar;
    private TextInputEditText data;
    private TextInputEditText especialidade;
    private TextInputEditText estado;
    private TextInputEditText cidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_busca);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        preferencias = new Preferencias(this);

        especialidade = findViewById(R.id.especialidadesFiltroID);
        estado = findViewById(R.id.estadoFiltroID);
        cidade = findViewById(R.id.cidadeFiltroID);
        Button buttonBuscar = findViewById(R.id.buttonBuscarFiltroID);
        data = findViewById(R.id.dataFiltroID);

        if(!preferencias.getCHAVE_TIPO_FILTRO().equals("EXAME")){
            getSupportActionBar().setTitle("Consultas");
            buttonBuscar.setText("BUSCAR CONSULTAS");
        }else{
            getSupportActionBar().setTitle("Exames");
            buttonBuscar.setText("BUSCAR EXAMES");
        }

        especialidade.setText(preferencias.getCHAVE_ESPECIALIDADE());
        estado.setText(preferencias.getCHAVE_ESTADO());
        cidade.setText(preferencias.getCHAVE_CIDADE());

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
            public void onClick(View view) {
                new DatePickerDialog(FiltroBuscaActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        buttonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (especialidade.getText().toString().isEmpty() |
                        estado.getText().toString().isEmpty() |
                        cidade.getText().toString().isEmpty() |
                        data.getText().toString().isEmpty()){

                    Toast.makeText(FiltroBuscaActivity.this,"Preencha todos os dados!",Toast.LENGTH_LONG).show();
                }else{
                    //chamada do metodo da tela com resultados da busca
                    estado.setText("");
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

        buttonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!preferencias.getCHAVE_TIPO_FILTRO().equals("EXAME")){
                    Intent i = new Intent(FiltroBuscaActivity.this, ResultActivity.class);
                    i.putExtra("tipo","consulta");
                    startActivity(i);
                }else{
                    //consulta exames
                    Intent i = new Intent(FiltroBuscaActivity.this, ResultActivity.class);
                    i.putExtra("tipo","exame");
                    startActivity(i);
                }
            }
        });


    }

    private void activitySelecao(String filtro){
        preferencias.setCHAVE_TIPO_BUSCA(filtro);
        Intent i = new Intent(FiltroBuscaActivity.this, SeletorActivity.class);
        startActivity(i);
    }

    private void updateLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        data.setText(sdf.format(myCalendar.getTime()));
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

}
