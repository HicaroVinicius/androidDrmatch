package com.app.streem.doctormatch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.app.streem.doctormatch.DAO.Firebase;
import com.app.streem.doctormatch.DAO.Preferencias;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class SeletorActivity extends AppCompatActivity {

    private ArrayList<String> listItens;
    private ArrayAdapter<String> adapterList;
    private ListView listView;
    private AutoCompleteTextView autoCompleteCity;
    private String[] itens;
    private Preferencias preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seletor);
        preferencias = new Preferencias(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        listView = findViewById(R.id.listviewCity);
        autoCompleteCity = findViewById(R.id.autoCompleteCity);
        String caminho = "";

        showLoadingAnimation();

        switch (preferencias.getCHAVE_TIPO_BUSCA()){
            case "1":
                autoCompleteCity.setHint("  "+"O que você procura?");
                getSupportActionBar().setTitle("Especialidades");
                caminho = "ESPECIALIDADE";
                break;
            case "2":
                autoCompleteCity.setHint("  "+"Qual seu estado?");
                getSupportActionBar().setTitle("Estado");
                caminho = "ESTADO";
                break;
            case "3":
                autoCompleteCity.setHint("  "+"Qual sua cidade?");
                getSupportActionBar().setTitle("Especialidades");
                caminho = "CIDADE/"+preferencias.getCHAVE_ESTADO().replace(" ","");
                break;
        }

        Firebase.getDatabaseReference().child("ATUACAO").child(caminho).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> temp = new ArrayList();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    temp.add(data.getValue().toString());
                }

                hideLoadingAnimation();
                final ArrayAdapter adapter = new ArrayAdapter(SeletorActivity.this, android.R.layout.simple_list_item_checked, temp);
                autoCompleteCity.setDropDownHeight(0);
                autoCompleteCity.setThreshold(1);
                autoCompleteCity.setAdapter(adapter);
                autoCompleteCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(SeletorActivity.this,adapter.getItem(position).toString(),Toast.LENGTH_LONG).show();
                        setValor(adapter.getItem(position).toString());

                    }
                });

                adapterList = new ArrayAdapter<String>(SeletorActivity.this, android.R.layout.simple_list_item_checked, temp);
                listView.setAdapter(adapter);


                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position,
                                            long id) {
                        String cidade = adapter.getItem(position).toString();//correção para pegar valor do listview
                        setValor(cidade);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void setValor(String cidade) {
        switch (preferencias.getCHAVE_TIPO_BUSCA()){
            case "1":
                preferencias.setCHAVE_ESPECIALIDADE(cidade);
                break;
            case "2":
                preferencias.setCHAVE_ESTADO(cidade);
                break;
            case "3":
               preferencias.setCHAVE_CIDADE(cidade);
                break;
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

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
}