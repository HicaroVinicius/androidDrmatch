package com.app.streem.doctormatch.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.streem.doctormatch.cadastraVagaActivity;
import com.app.streem.doctormatch.FiltroBuscaActivity;
import com.app.streem.doctormatch.R;

/**
 * Created by Hicaro on 20/03/2018.
 */

public class AgendamentoFragment extends Fragment {

    private Button agendar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_agendamento,null);
        agendar = view.findViewById(R.id.agendarConsultaButton);
        agendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), FiltroBuscaActivity.class);
                startActivity(intent);
            }
        });
        Button add = view.findViewById(R.id.addVagaID);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), cadastraVagaActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

}
