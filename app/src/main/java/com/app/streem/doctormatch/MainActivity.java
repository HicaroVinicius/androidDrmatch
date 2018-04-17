package com.app.streem.doctormatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.app.streem.doctormatch.DAO.BD;
import com.app.streem.doctormatch.DAO.Firebase;
import com.app.streem.doctormatch.Fragments.AgendamentoFragment;
import com.app.streem.doctormatch.Fragments.ConsultaFragment;
import com.app.streem.doctormatch.Fragments.ExameFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {

    private boolean buscaEspecFirebase = false;

    private void setNavigationViewListner() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_camera);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //buscaEspecFirebase = true;

        if(buscaEspecFirebase){

            final BD bd = new BD(this);

            bd.deleteEspec();

            Firebase.getDatabaseReference().child("ATUACAO").child("ESPECIALIDADE").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for(DataSnapshot data : dataSnapshot.getChildren()){
                        bd.inserirEspecialidade(data.getValue().toString());
                        Log.i("testeBD",bd.buscarEspec().toString());
                        Log.i("testeBDDATA",data.getValue().toString());
                    }}

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }
         /*

        preferencias = new Preferencias(this);


        consultaView = findViewById(R.id.recyclerConsulta);

        consultaView.setHasFixedSize(true);
        consultaView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new AdapterConsultas(consultaList,getApplicationContext());

        adapter.notifyDataSetChanged();

        consultaView.setAdapter(adapter);

        */



        BottomNavigationView navigationView =  findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
        navigationView.setSelectedItemId(R.id.navigation_agendamento);
        carregarFragment(new AgendamentoFragment());




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Toast.makeText(this, "teste", Toast.LENGTH_SHORT).show();

        if (id == R.id.nav_camera) {

            return true;
        }


<<<<<<< HEAD
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Toast.makeText(this, "teste", Toast.LENGTH_SHORT).show();

        if (id == R.id.nav_camera) {
            Toast.makeText(MainActivity.this, "teste", Toast.LENGTH_LONG).show();
            return true;
        }
        return true;
    }


=======
        return super.onOptionsItemSelected(item);
    }
>>>>>>> parent of d21741d... mvp


    private boolean carregarFragment(Fragment fragment){
        if(fragment != null){
            //setCustomAnimations( android.R.anim.fade_in, android.R.anim.fade_out )
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();

            return true;
        }

        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Log.i("testeNav", String.valueOf(item.getItemId()));

        Fragment fragment = null;

        switch (item.getItemId()){

            case R.id.navigation_agendamento:
                fragment = new AgendamentoFragment();
                break;
            case R.id.navigation_consultas:
                fragment = new ConsultaFragment();
                break;
            case R.id.navigation_exames:
                fragment = new ExameFragment();
                break;


        }

        return carregarFragment(fragment);
    }



}
