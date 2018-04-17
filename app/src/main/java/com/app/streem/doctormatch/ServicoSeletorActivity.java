package com.app.streem.doctormatch;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.app.streem.doctormatch.Adapter.TabAdapter;
import com.app.streem.doctormatch.DAO.BD;
import com.app.streem.doctormatch.DAO.Firebase;
import com.app.streem.doctormatch.Fragments.ConsultaTab;
import com.app.streem.doctormatch.Fragments.ExameTab;
import com.app.streem.doctormatch.Fragments.RetornoTab;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ServicoSeletorActivity extends AppCompatActivity implements ConsultaTab.OnFragmentInteractionListener,ExameTab.OnFragmentInteractionListener,RetornoTab.OnFragmentInteractionListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servico_seletor);

        final BD bd = new BD(this);

        final ArrayList<String> consulta = bd.buscarEspec();
        Log.i("testeBDConsulta",consulta.toString());

        final ArrayList<String> exame = new ArrayList();


        final ArrayAdapter[] adapterAuto = new ArrayAdapter[2];
        adapterAuto[0] = new ArrayAdapter(ServicoSeletorActivity.this, R.layout.recyclerview_servicos, consulta);
        adapterAuto[1] = new ArrayAdapter(ServicoSeletorActivity.this, R.layout.recyclerview_servicos, exame);

        final AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoCompleteSeletor);

        autoCompleteTextView.setDropDownHeight(0);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setHint("Especialidade");
        autoCompleteTextView.setAdapter(adapterAuto[0]);

        TabLayout tabLayout = findViewById(R.id.tabSeletor);
        tabLayout.addTab(tabLayout.newTab().setText("Consulta"));
        tabLayout.addTab(tabLayout.newTab().setText("Exame"));
        tabLayout.addTab(tabLayout.newTab().setText("Retorno"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = findViewById(R.id.viewPagerSeletor);
        final PagerAdapter adapter = new TabAdapter(getSupportFragmentManager(),tabLayout.getTabCount(), adapterAuto[0]);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()){
                    case 0:
                        autoCompleteTextView.setHint("Especialidade");
                        autoCompleteTextView.setAdapter(adapterAuto[0]);
                        adapter.notifyDataSetChanged();

                        break;
                    case 1:
                        autoCompleteTextView.setHint("Exames");
                        break;
                    case 2:
                        autoCompleteTextView.setHint("");
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
