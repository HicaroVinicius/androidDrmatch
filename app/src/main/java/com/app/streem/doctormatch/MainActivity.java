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
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.streem.doctormatch.DAO.BD;
import com.app.streem.doctormatch.DAO.Firebase;
import com.app.streem.doctormatch.DAO.Preferencias;
import com.app.streem.doctormatch.DAO.SDFormat;
import com.app.streem.doctormatch.Fragments.AgendamentoFragment;
import com.app.streem.doctormatch.Fragments.ConsultaFragment;
import com.app.streem.doctormatch.Fragments.ExameFragment;
import com.app.streem.doctormatch.Modelo.Cidade;
import com.app.streem.doctormatch.Modelo.Consulta;
import com.app.streem.doctormatch.Modelo.Especialidade;
import com.app.streem.doctormatch.Modelo.Estado;
import com.app.streem.doctormatch.Modelo.Exame;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {


    private SDFormat format;
    private Calendar myCalendar;

    private Preferencias preferencias;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferencias = new Preferencias(getApplicationContext());
        format = new SDFormat();


        final BD bd = new BD(this);



        Firebase.getDatabaseReference().child("USUARIO").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("CONSULTA").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Consulta consulta = data.getValue(Consulta.class);
                    Log.i("testeBDvalueConsulta",consulta.getKeyConsulta().toString());
                    Log.i("testeBDvalueConsulta",consulta.getNomeMedico().toString());
                    bd.inserirConsulta(consulta);
                }

            }
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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
        View headerView = navigationLatetal.getHeaderView(0);
        TextView username = (TextView) headerView.findViewById(R.id.nome_user);
        TextView useremail = (TextView) headerView.findViewById(R.id.email_user);
        username.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        useremail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
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
        final BD bd = new BD(this);
        try {
            Firebase.getFirebaseAuth().signOut();
            bd.deleteEstado();
            bd.deleteCidade();
            bd.deleteConsulta();
            bd.deleteEspec();
            bd.deleteExame();
            //NESSE MOMENTO -> ALTERAR CHAVE PARA AVISAR QUE DEVE BAIXAR NÓ DE CONSULTAS
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
