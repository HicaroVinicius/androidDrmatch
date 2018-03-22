package com.app.streem.doctormatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.app.streem.doctormatch.Fragments.AgendamentoFragment;
import com.app.streem.doctormatch.Fragments.ConsultaFragment;
import com.app.streem.doctormatch.Fragments.ExameFragment;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Log.i("testeNAV2", String.valueOf(id));

        //noinspection SimplifiableIfStatement
        if (id == R.id.nav_camera) {
            Toast.makeText(this, "aqui", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,cadastraVagaActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
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


        }

        return carregarFragment(fragment);
    }

}
