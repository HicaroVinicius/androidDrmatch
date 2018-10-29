package com.app.streem.doctormatch;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.streem.doctormatch.Adapter.TabAdapter;
import com.app.streem.doctormatch.DAO.BD;
import com.app.streem.doctormatch.DAO.Firebase;
import com.app.streem.doctormatch.DAO.Preferencias;
import com.app.streem.doctormatch.DAO.SDFormat;
import com.app.streem.doctormatch.Fragments.ConsultaTab;
import com.app.streem.doctormatch.Fragments.ExameTab;
import com.app.streem.doctormatch.Fragments.RetornoTab;
import com.app.streem.doctormatch.Modelo.Especialidade;
import com.app.streem.doctormatch.Modelo.Exame;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class ServicoSeletorActivity extends AppCompatActivity implements ConsultaTab.OnFragmentInteractionListener,ExameTab.OnFragmentInteractionListener,RetornoTab.OnFragmentInteractionListener {

    private Preferencias preferencias;
    private BD bd;
    private TextView tituloServico;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        preferencias = new Preferencias(getApplicationContext());
        bd = new BD(this);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servico_seletor);

        tituloServico = findViewById(R.id.tituloServico);

        ImageView voltar = findViewById(R.id.voltaservsel);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView voltarText = findViewById(R.id.textView50);
        voltarText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final ArrayAdapter[] adapterAuto = new ArrayAdapter[2];


        final ArrayList<String> consulta = bd.buscarEspec();
        Log.i("testeBDConsulta",consulta.toString());
        adapterAuto[0] = new ArrayAdapter(ServicoSeletorActivity.this, R.layout.recyclerview_servicos, consulta);

        String dtcont_espec = preferencias.getInfo("dtcont_espec");
        Date dataA = new Date();
        Log.i("TESTEDataAtual",String.valueOf(dataA.getTime()));

        Firebase.getDatabaseReference().child("APP_ATUACAO").child("ESPECIALIDADES").orderByChild("dt_cont").startAt(dtcont_espec).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Log.i("testeBDvalue1","dentroESP");
                if(!dataSnapshot.hasChildren()){
                    Log.i("testeBDvalue1","noChildESP");
                }
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Especialidade value = data.getValue(Especialidade.class);
                    Log.i("testeBDvalue1",data.toString());
                    Log.i("testeBDvalue1",data.getValue().toString());

                    Log.i("testeBDvalue1",value.getNome().toString());
                    bd.inserirEspecialidade(value);

                    consulta.add(value.getNome().toString());
                    adapterAuto[0].notifyDataSetChanged();

                    Date dataA = new Date();
                    preferencias.setInfo("dtcont_espec",String.valueOf(dataA.getTime()));
                    Log.i("TESTEMA D-dtcont_espec",String.valueOf(dataA.getTime()));

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final ArrayList<String> exame = bd.buscarExame();
        Log.i("testeBDExame",exame.toString());

        adapterAuto[1] = new ArrayAdapter(ServicoSeletorActivity.this, R.layout.recyclerview_servicos, exame);

        String dtcont_exame = preferencias.getInfo("dtcont_exame");
        dataA = new Date();
        Log.i("TESTEDataAtual",String.valueOf(dataA.getTime()));
        Log.i("TESTEMAIN-dtcont_exame",String.valueOf(dtcont_exame));

        Firebase.getDatabaseReference().child("APP_ATUACAO").child("EXAMES").orderByChild("dt_cont").startAt(dtcont_exame).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Log.i("testeBDvalue1","dentroCIT-"+String.valueOf(dtcont_cidade));
                if(!dataSnapshot.hasChildren()){
                    Log.i("testeBDvalue1","noChildEXA");
                }
                for(DataSnapshot data : dataSnapshot.getChildren()){

                    Exame value = data.getValue(Exame.class);
                    Log.i("testeBDvalue3", value.getNome().toString());
                    bd.inserirExame(value);
                    exame.add(value.getNome().toString());
                    adapterAuto[1].notifyDataSetChanged();
                    Date dataA = new Date();
                    preferencias.setInfo("dtcont_exame", String.valueOf(dataA.getTime()));
                    Log.i("TESTEMA D-dtcont_exame", String.valueOf(dataA.getTime()));


                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        final AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoCompleteSeletor);

        autoCompleteTextView.setDropDownHeight(0);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setHint("Especialidade");
        tituloServico.setText("Especialidade");
        autoCompleteTextView.setAdapter(adapterAuto[0]);

        final TabLayout tabLayout = findViewById(R.id.tabSeletor);
        tabLayout.addTab(tabLayout.newTab().setText("Consulta"));
        tabLayout.addTab(tabLayout.newTab().setText("Exame"));
        tabLayout.addTab(tabLayout.newTab().setText("Retorno"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = findViewById(R.id.viewPagerSeletor);
        final PagerAdapter adapterConsulta = new TabAdapter(getSupportFragmentManager(),tabLayout.getTabCount(), adapterAuto[0]);
        viewPager.setAdapter(adapterConsulta);
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        autoCompleteTextView.setAdapter(adapterAuto[0]);
                        autoCompleteTextView.setText(autoCompleteTextView.getText());
                        autoCompleteTextView.setSelection(autoCompleteTextView.getText().length());
                        autoCompleteTextView.setHint("Especialidade");
                        tituloServico.setText("Especialidade");
                        viewPager.setAdapter(new TabAdapter(getSupportFragmentManager(),tabLayout.getTabCount(), adapterAuto[0]));
                        viewPager.setCurrentItem(tab.getPosition());
                        viewPager.getAdapter().notifyDataSetChanged();

                        break;
                    case 1:
                        autoCompleteTextView.setAdapter(adapterAuto[1]);
                        autoCompleteTextView.setText(autoCompleteTextView.getText());
                        autoCompleteTextView.setSelection(autoCompleteTextView.getText().length());
                        autoCompleteTextView.setHint("Exames");
                        tituloServico.setText("Exames");
                        viewPager.setAdapter(new TabAdapter(getSupportFragmentManager(),tabLayout.getTabCount(), adapterAuto[1]));
                        viewPager.setCurrentItem(tab.getPosition());
                        viewPager.getAdapter().notifyDataSetChanged();
                        break;
                    case 2:
                        autoCompleteTextView.clearComposingText();
                        autoCompleteTextView.setHint("");
                        tituloServico.setText("");
                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


          }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }



    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }




}
