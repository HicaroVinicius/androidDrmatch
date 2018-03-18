package com.app.streem.doctormatch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.streem.doctormatch.Adapter.VagasAdapter;
import com.app.streem.doctormatch.DAO.Firebase;
import com.app.streem.doctormatch.Modelo.VagasModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private RadioGroup radio;
    private TextView end1Details;
    private TextView end2Details;
    private TextView titularDetails;
    private TextView registroDetails;
    private TextView classifDetails;
    private String urlFoto;
    private RoundedImageView fotoMedico;
    private RecyclerView horaView;
    private RecyclerView.Adapter adapter;
    private List<VagasModel> vagasList = new ArrayList<>();
    private ArrayList<String> arrayDependentes = new ArrayList<>();
    private LinearLayout novoCliente;

    private String[] dependentes = new String[]{"Josue","Hicaro"};

    private Spinner spinner;



    RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.proprioUser:
                    novoCliente.setVisibility(View.GONE);
                    break;
                case R.id.outroUser:
                    novoCliente.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

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

        urlFoto = url;

        Picasso.with(getApplicationContext()).load(urlFoto).into(fotoMedico);

        titularDetails.setText(titular);
        registroDetails.setText(registro);
        classifDetails.setText(classif);

        Toast.makeText(getApplicationContext(),"Carregando... Aguarde",Toast.LENGTH_SHORT).show();

        adapter = new VagasAdapter(vagasList, getApplicationContext(), new VagasAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(VagasModel item) {
                Toast.makeText(getApplicationContext(),item.getHora(),Toast.LENGTH_SHORT).show();
                Intent confirmar = new Intent(DetailActivity.this,ConfirmActivity.class);
                confirmar.putExtra("titular",titular);
                confirmar.putExtra("end1",end1);
                confirmar.putExtra("end2",end2);
                confirmar.putExtra("registro",registro);
                confirmar.putExtra("classif",classif);
                confirmar.putExtra("url",url);
                confirmar.putExtra("key",key);

                confirmar.putExtra("data",data);
                confirmar.putExtra("dataFormatt",dataFormatt);

                confirmar.putExtra("hora",item.getHora());
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
                }



                for (DataSnapshot data : dataSnapshot.getChildren()) {


                        Log.i("TESTE",data.getValue().toString());
                        VagasModel vaga = data.getValue(VagasModel.class);
                        vagasList.add(vaga);
                        adapter.notifyDataSetChanged();

                }
            }
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,dependentes);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner = findViewById(R.id.spinnerDependentes);

        spinner.setAdapter(adapterSpinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(DetailActivity.this, parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }




}
