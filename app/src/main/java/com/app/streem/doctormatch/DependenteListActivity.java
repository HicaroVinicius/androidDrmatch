package com.app.streem.doctormatch;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.app.streem.doctormatch.Adapter.SpinnerDependenteAdapter;
import com.app.streem.doctormatch.DAO.BD;
import com.app.streem.doctormatch.DAO.Firebase;
import com.app.streem.doctormatch.DAO.Preferencias;
import com.app.streem.doctormatch.Modelo.DependenteModel;
import com.app.streem.doctormatch.Modelo.Medico;
import com.app.streem.doctormatch.Modelo.UsuarioDados;
import com.app.streem.doctormatch.Modelo.UsuarioRegistro;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

public class DependenteListActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<DependenteModel> arrayDependentes;

    private Preferencias preferencias;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dependente_list);

        preferencias = new Preferencias(this);

        final String idUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        listView = findViewById(R.id.listview_with_fab);

        FloatingActionButton fab = findViewById(R.id.floating_action_button_fab_with_listview);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DependenteListActivity.this,DependenteActivity.class);
                intent.putExtra("novo","true");
                startActivity(intent);
                finish();
            }
        });

        BD bd = new BD(getApplicationContext());
        final ArrayList<String> dependente = new ArrayList<>();

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, dependente);
        listView.setAdapter(adapter);

        String uid = preferencias.getInfo("id");
        String dtcont_dependente = preferencias.getInfo("dtcont_dependente");
        Log.i("TESTcont_dependente",dtcont_dependente);
        Firebase.getDatabaseReference().child("APP_USUARIOS").child("DEPENDENTES").child(uid).orderByChild("dt_cont").startAt(dtcont_dependente).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("TESTEDEP",dataSnapshot.toString());

                if (!dataSnapshot.hasChildren()){
                    Log.i("TESTEDEP","noDep");
                }else{
                    for (DataSnapshot data : dataSnapshot.getChildren()) {

                        DependenteModel dep = data.getValue(DependenteModel.class);
                        Log.i("TESTEDEP",dep.getId()+"-"+dep.getNome());
                        BD bd = new BD(getApplicationContext());
                        bd.inserirDependente(dep);
//                        dependente.add(dep.getNome());
//                        arrayDependentes.add(dep);
//                        adapter.notifyDataSetChanged();
                        Date dataA = new Date();
                        preferencias.setInfo("dtcont_dependente", String.valueOf(dataA.getTime()));
                        Log.i("TESTEdtcont_dependente", String.valueOf(dataA.getTime()));

                    }
                }

                BD bd = new BD(getApplicationContext());
                arrayDependentes = bd.buscarDependente();
                Iterator<DependenteModel> iterator = arrayDependentes.iterator();
                while(iterator.hasNext()) {
                    DependenteModel m = iterator.next();
                    Log.i("TesteMedicosBusca",m.getId());
                    dependente.add(m.getNome());
                    adapter.notifyDataSetChanged();
                }


            }
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(DependenteListActivity.this,DependenteActivity.class);
                intent.putExtra("novo","false");
                intent.putExtra("nome", arrayDependentes.get(i).getNome());
                intent.putExtra("cpf", arrayDependentes.get(i).getCpf());
                intent.putExtra("data", arrayDependentes.get(i).getIdade());
                intent.putExtra("sexo", arrayDependentes.get(i).getSexo());
                intent.putExtra("id", arrayDependentes.get(i).getId());
                Log.i("testeDependenteList",String.valueOf(i));
                startActivity(intent);
                finish();
            }
        });



    }

}




