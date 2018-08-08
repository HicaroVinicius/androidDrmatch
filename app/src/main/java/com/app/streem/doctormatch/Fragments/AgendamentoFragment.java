package com.app.streem.doctormatch.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.app.streem.doctormatch.DetailActivity;
import com.app.streem.doctormatch.ServicoSeletorActivity;
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
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_agendamento,null);
        agendar = view.findViewById(R.id.agendarConsultaButton);

        ConnectivityManager cm =
                (ConnectivityManager) container.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        final boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        agendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnected){
                Intent intent = new Intent(v.getContext(), ServicoSeletorActivity.class);
                startActivity(intent);
                }else{
                    Toast.makeText(container.getContext(), "Necessário conexão com a internet", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

}
