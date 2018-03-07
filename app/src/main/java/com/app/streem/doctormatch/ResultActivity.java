package com.app.streem.doctormatch;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.Transformation;
import android.widget.Toast;

import com.app.streem.doctormatch.DAO.Preferencias;
import com.app.streem.doctormatch.DAO.ResultAdapter;
import com.app.streem.doctormatch.DAO.ResultMedicos;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<ResultMedicos> medicos = new ArrayList<>();
    private Preferencias preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toast.makeText(this,"teste",Toast.LENGTH_SHORT).show();

        recyclerView = findViewById(R.id.RecyclerViewMedico);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        medicos.add(new ResultMedicos("Clinica Viver","999999999","Rua A","N.104 Centro","CRM 9999","Dr. Josué Medeiros","300,00","teste","teste","teste"));

        adapter = new ResultAdapter(medicos,this);

        recyclerView.setAdapter(adapter);

    }


}

