package com.app.streem.doctormatch;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.app.streem.doctormatch.DAO.Firebase;
import com.app.streem.doctormatch.Modelo.Consulta;
import com.app.streem.doctormatch.Modelo.VagasModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class cadastraVagaActivity extends AppCompatActivity {

    private DatePickerDialog dataPicker;
    private Calendar myCalendar;
    private TextInputEditText data;
    private TextInputEditText hora;
    private Button addBtn;
    private Date d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastra_vaga);

        ArrayAdapter<String> adapterEsp = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, Espec);
        ArrayAdapter<String> adapterEst = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, Est);
        ArrayAdapter<String> adapterCit = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, Cit);
        ArrayAdapter<String> adapterMed = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, Med);

        final AutoCompleteTextView Espec = (AutoCompleteTextView)
                findViewById(R.id.autoCompleteEspec);
        Espec.setAdapter(adapterEsp);
        final AutoCompleteTextView Estado = (AutoCompleteTextView)
                findViewById(R.id.autoCompleteestado);
        Estado.setAdapter(adapterEst);
        final AutoCompleteTextView Cidade = (AutoCompleteTextView)
                findViewById(R.id.autoCompletecidade);
        Cidade.setAdapter(adapterCit);
        final AutoCompleteTextView Medico = (AutoCompleteTextView)
                findViewById(R.id.autoCompletemedico);
        Medico.setAdapter(adapterMed);


        myCalendar = Calendar.getInstance();
        data = findViewById(R.id.dataAddID);
        hora = findViewById(R.id.horaAdd);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                try {
                    updateLabel();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        };

        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataPicker = new DatePickerDialog(cadastraVagaActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dataPicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                dataPicker.show();
            }
        });

        hora.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                // If the event is a key-down event on the "enter" button
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    addBtn.callOnClick();
                    return true;
                }
                return false;
            }
        });

        addBtn = findViewById(R.id.addVagaButton);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data.getText().toString().isEmpty() |
                        hora.getText().toString().isEmpty() |
                        Espec.getText().toString().isEmpty() |
                        Estado.getText().toString().isEmpty() |
                        Cidade.getText().toString().isEmpty() |
                        Medico.getText().toString().isEmpty()){

                    Toast.makeText(cadastraVagaActivity.this,"Preencha todos os dados!",Toast.LENGTH_LONG).show();
                }else{

                    String key = Firebase.getDatabaseReference().child("CLIENTES").child(Medico.getText().toString()).child("AGENDAMENTO").child(String.valueOf(d.getTime())).push().getKey();
                    VagasModel vaga = new VagasModel("","Disponível",hora.getText().toString(),key,String.valueOf(d.getTime()),"");
                    Firebase.getDatabaseReference().child("CLIENTES").child(Medico.getText().toString()).child("AGENDAMENTO").child(String.valueOf(d.getTime())).child(key).setValue(vaga);

                    Firebase.getDatabaseReference().child("VAGAS").child(Estado.getText().toString().replace(" ","")).child(Cidade.getText().toString().replace(" ","")).child(Espec.getText().toString().replace(" ","")).child(String.valueOf(d.getTime())).child(Medico.getText().toString()).child(key).setValue(hora.getText().toString());

                    Toast.makeText(cadastraVagaActivity.this, "Adicionado Com Sucesso...", Toast.LENGTH_SHORT).show();
                    hora.setText("");
                    //Intent intent = new Intent(cadastraVagaActivity.this,MainActivity.class);
                    //startActivity(intent);

                }
            }
        });

    }

    private static final String[] Espec = new String[] {
            "Clinico Geral", "Cardiologista", "Nutricionista"
    };
    private static final String[] Est = new String[] {
            "Ceará"
    };
    private static final String[] Cit = new String[] {
            "Sobral", "Tiangua", "Frecheirinha"
    };
    private static final String[] Med = new String[] {
            "sssKEY_MEDsss3", "sssKEY_MEDsss4", "sssKEY_MEDsss5"
    };

    private void updateLabel() throws ParseException {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        data.setText(sdf.format(myCalendar.getTime()));
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        d = format.parse(data.getText().toString());

    }

}
