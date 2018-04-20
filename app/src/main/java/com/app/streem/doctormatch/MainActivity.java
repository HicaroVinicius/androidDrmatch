package com.app.streem.doctormatch;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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

    //Modifica aqui se quiser baixar. Depois faço o tratamamento pra pegar cada variável do Firebase.
    final boolean baixar = false;

    private boolean buscaEspecFirebase = baixar;
    private boolean buscaEstadoFirebase = baixar;
    private boolean buscaCidadeFirebase = baixar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final BD bd = new BD(this);

        //buscaEspecFirebase = true;

        if(buscaEspecFirebase){


            bd.deleteEspec();

            Firebase.getDatabaseReference().child("ATUACAO").child("ESPECIALIDADE").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for(DataSnapshot data : dataSnapshot.getChildren()){
                        bd.inserirEspecialidade(data.getValue().toString());

                    }}

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }

        if(buscaEstadoFirebase){

            bd.deleteEstado();

            Firebase.getDatabaseReference().child("ATUACAO").child("ESTADO").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for(DataSnapshot data : dataSnapshot.getChildren()){
                        bd.inserirEstado(data.getValue().toString());

                    }}

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        if(buscaCidadeFirebase){

            bd.deleteCidade();

            Firebase.getDatabaseReference().child("ATUACAO").child("CIDADE").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    for(DataSnapshot data : dataSnapshot.getChildren()){

                        for(DataSnapshot data1 : data.getChildren()){
                            Log.i("testeBDvalue",data1.getValue().toString());
                            Log.i("testeBDestado",data.getKey().toString());
                            bd.inserirCidade(data1.getValue().toString(),data.getKey().toString());
                        }
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

        NavigationView navigationLatetal = (NavigationView) findViewById(R.id.nav_view);
        navigationLatetal.setNavigationItemSelectedListener(this);




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
            Toast.makeText(MainActivity.this, "teste", Toast.LENGTH_LONG).show();
            return true;
        }
        return true;
    }




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
            case R.id.nav_camera:
                Intent intent = new Intent(MainActivity.this,cadastraVagaActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_logout:
                AlertDialog.Builder confirm = new AlertDialog.Builder(MainActivity.this);
                confirm.setTitle("Deseja sair da sua conta?");
                confirm.setIcon(R.drawable.ic_done_black_24dp).setMessage("Ao sair, você não poderá receber notificações dos seus agendamentos").setCancelable(true);
                confirm.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });

                confirm.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logout();
                    }
                });

                AlertDialog alertDialog = confirm.create();
                alertDialog.show();

                break;



        }

        return carregarFragment(fragment);
    }

    private void logout() {

        try {
            Firebase.getFirebaseAuth().signOut();
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
