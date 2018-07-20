package com.app.streem.doctormatch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.app.streem.doctormatch.DAO.BD;
import com.app.streem.doctormatch.DAO.Firebase;
import com.app.streem.doctormatch.DAO.Preferencias;
import com.app.streem.doctormatch.Modelo.Cidade;
import com.app.streem.doctormatch.Modelo.Estado;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

public class SeletorActivity extends AppCompatActivity {

    private ArrayList<String> listItens;
    private ArrayAdapter<String> adapterList;
    private ListView listView;
    private AutoCompleteTextView autoCompleteCity;
    private String[] itens;
    private Preferencias preferencias;
    private BD bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seletor);

        ImageView voltar = findViewById(R.id.imageView10);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        preferencias = new Preferencias(this);

        atualizarInfos();

        listView = findViewById(R.id.listviewCity);
        autoCompleteCity = findViewById(R.id.autoCompleteCity);
        String caminho = "";

        showLoadingAnimation();

        Intent dados = getIntent();
        final String tipo = dados.getStringExtra("tipo");
        final String idEstado = dados.getStringExtra("idEstado");
        final String nomeEstado = dados.getStringExtra("nomeEstado");
        Log.i("testetipo",tipo);

        switch (tipo){
            case "1":
                autoCompleteCity.setHint("  "+"O que você procura?");
                break;
            case "estado":
                autoCompleteCity.setHint("  "+"Qual seu estado?");

                break;
            case "cidade":
                autoCompleteCity.setHint("  "+"Qual sua cidade?");

                break;
        }

        bd = new BD(this);

        if(tipo.equals("estado")){
            final ArrayList<Estado> estadoSQL = bd.buscarEstado();
            ArrayList<String> estados = new ArrayList<>();

            Iterator<Estado> iterator = estadoSQL.iterator();
            while(iterator.hasNext()) {
                Estado e = iterator.next();
                estados.add(e.getEstado());
            }


            final ArrayAdapter adapter = new ArrayAdapter(SeletorActivity.this, android.R.layout.simple_list_item_checked, estados);
            autoCompleteCity.setDropDownHeight(0);
            autoCompleteCity.setThreshold(1);
            autoCompleteCity.setAdapter(adapter);

            adapterList = new ArrayAdapter<>(SeletorActivity.this, android.R.layout.simple_list_item_checked, estados);
            listView.setAdapter(adapter);


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    String idEstado = estadoSQL.get(position).getId();//correção para pegar valor do listview
                    String nomeEstado = estadoSQL.get(position).getEstado();//correção para pegar valor do listview
                    Log.i("testePosition - ",String.valueOf(position)+"->"+estadoSQL.get(position).getEstado());
                    preferencias.setInfo("nomeEstado",estadoSQL.get(position).getEstado());
                    setValor(idEstado,tipo,idEstado,nomeEstado,null);
                }
            });



        }else if(tipo.equals("cidade")){
            Log.i("testeBDConsulta2 IDD->",idEstado);
            final ArrayList<Cidade> cidadeSQL = bd.buscarCidade(idEstado);
            ArrayList<String> cidades = new ArrayList<>();
            Log.i("testeBDConsulta2",cidadeSQL.toString());

            Iterator<Cidade> iterator = cidadeSQL.iterator();
            while(iterator.hasNext()) {
                Cidade c = iterator.next();
                cidades.add(c.getCidade());
            }

            final ArrayAdapter adapter = new ArrayAdapter(SeletorActivity.this, android.R.layout.simple_list_item_checked, cidades);
            autoCompleteCity.setDropDownHeight(0);
            autoCompleteCity.setThreshold(1);
            autoCompleteCity.setAdapter(adapter);

            adapterList = new ArrayAdapter<>(SeletorActivity.this, android.R.layout.simple_list_item_checked, cidades);
            listView.setAdapter(adapter);


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    //String cidade = adapter.getItem(position).toString();//correção para pegar valor do listview
                    String idCidade = cidadeSQL.get(position).getId();//correção para pegar valor do listview
                    String nomeCidade = cidadeSQL.get(position).getCidade();//correção para pegar valor do listview
                    setValor(nomeCidade,tipo,idEstado,nomeEstado,idCidade);
                }
            });
//


        }else{
            Toast.makeText(this, "Erro - Seletor, contate nosso suporte!", Toast.LENGTH_SHORT).show();
        }



    }

    private void atualizarInfos() {

        String dtcont_cidade = preferencias.getInfo("dtcont_cidade");
        String dtcont_estado = preferencias.getInfo("dtcont_estado");


        Log.i("TESTEMAIN-dtcont_cidade",String.valueOf(dtcont_cidade));
        Log.i("TESTEMAIN-dtcont_estado",String.valueOf(dtcont_estado));

        Firebase.getDatabaseReference().child("APP_ATUACAO").child("ESTADOS").orderByChild("dt_cont").startAt(dtcont_estado).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Log.i("testeBDvalue1","dentroEST-"+String.valueOf(dtcont_estado));
                if(!dataSnapshot.hasChildren()){
                    Log.i("testeBDvalue1","noChildEST");
                }
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Estado value = data.getValue(Estado.class);
                    Log.i("testeBDvalue2",value.getEstado().toString());
                    bd.inserirEstado(value);

                    Date dataA = new Date();
                    preferencias.setInfo("dtcont_estado",String.valueOf(dataA.getTime()));
                    Log.i("TESTEMA D-dtcont_estado",String.valueOf(dataA.getTime()));

                }}

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        Firebase.getDatabaseReference().child("APP_ATUACAO").child("CIDADES").orderByChild("dt_cont").startAt(dtcont_cidade).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Log.i("testeBDvalue1","dentroCIT-"+String.valueOf(dtcont_cidade));
                if(!dataSnapshot.hasChildren()){
                    Log.i("testeBDvalue1","noChildCIT");
                }
                for(DataSnapshot data : dataSnapshot.getChildren()){

                    Cidade value = data.getValue(Cidade.class);
                    Log.i("testeBDvalue3", value.getCidade().toString());
                    bd.inserirCidade(value);

                    Date dataA = new Date();
                    preferencias.setInfo("dtcont_cidade", String.valueOf(dataA.getTime()));
                    Log.i("TESTEMA D-dtcont_cidade", String.valueOf(dataA.getTime()));


                }}

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setValor(String value,String tipo,String idEstado,String nomeEstado,String idCidade) {
        switch (tipo){

            case "estado":
                preferencias.setCHAVE_ESTADO(value);
                Intent i = new Intent(SeletorActivity.this, SeletorActivity.class);
                i.putExtra("tipo","cidade");
                i.putExtra("estado",value);
                i.putExtra("nomeEstado",nomeEstado);
                i.putExtra("idEstado",idEstado);
                startActivity(i);
                finish();
                break;
            case "cidade":
                preferencias.setCHAVE_CIDADE(value);
                Intent intent = new Intent(this, FiltroBuscaActivity.class);
                intent.putExtra("nomeCidade",value);
                intent.putExtra("idCidade",idCidade);
                intent.putExtra("idEstado",idEstado);
                intent.putExtra("nomeEstado",nomeEstado);
                startActivity(intent);
                finish();
                break;
        }

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