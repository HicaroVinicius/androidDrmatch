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
import android.widget.Toast;

import com.app.streem.doctormatch.DAO.Preferencias;

import java.util.ArrayList;
import java.util.Arrays;

public class SeletorActivity extends AppCompatActivity {

    private ArrayList<String> listItens;
    private ArrayAdapter<String> adapterList;
    private ListView listView;
    private AutoCompleteTextView autoCompleteCity;
    private String[] itens = {"Clinco Geral", "Cardiologista", "Nutricionista", "Infectologia", "Neurologia"};
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

        //definir o titulo da barra e hint do edittext dinamicamento
        // Qual seu estado?
        // Qual sua cidade?
        if(preferencias.getCHAVE_TIPO_BUSCA().equals("1")){
            autoCompleteCity.setHint("  "+"O que você procura?");
            getSupportActionBar().setTitle("Especialidades");
        }

        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_checked, itens);
        autoCompleteCity.setDropDownHeight(0);
        autoCompleteCity.setThreshold(1);
        autoCompleteCity.setAdapter(adapter);
        autoCompleteCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SeletorActivity.this,adapter.getItem(position).toString(),Toast.LENGTH_LONG).show();
                retornarCidade(adapter.getItem(position).toString());

            }
        });

        listItens = new ArrayList<>(Arrays.asList(itens));
        adapterList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, listItens);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                String cidade = adapter.getItem(position).toString();//correção para pegar valor do listview
                retornarCidade(cidade);
            }
        });


    }


    private void disableEditTextCity(AutoCompleteTextView editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        // listenerCity = editText.getKeyListener();
        editText.setKeyListener(null);
        //editText.setBackgroundColor(Color.TRANSPARENT);
    }

    private void enableEditTextCity(AutoCompleteTextView editText) {
        editText.setFocusableInTouchMode(true);
        editText.setEnabled(true);
        editText.setCursorVisible(true);
        editText.setKeyListener(new EditText(getApplicationContext()).getKeyListener());
    }


    public void retornarCidade(String cidade) {
        preferencias.setCHAVE_CIDADE(cidade);
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
}