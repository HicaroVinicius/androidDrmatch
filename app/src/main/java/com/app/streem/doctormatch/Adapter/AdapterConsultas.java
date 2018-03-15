package com.app.streem.doctormatch.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.streem.doctormatch.Modelo.Consulta;
import com.app.streem.doctormatch.R;

import java.util.List;

/**
 * Created by Hicaro on 07/03/2018.
 */

public class AdapterConsultas extends RecyclerView.Adapter<AdapterConsultas.ResultViewHolder>{

    public AdapterConsultas(List<Consulta> consulta, Context context) {
        this.consulta = consulta;
        this.context = context;
    }

    private List<Consulta> consulta;
    private Context context;

    @Override
    public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_result,null);
        return new AdapterConsultas.ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ResultViewHolder holder, int position) {
        Consulta consultas = consulta.get(position);
        holder.nome.setText(String.valueOf(consultas.getNome()));
        holder.data.setText(String.valueOf(consultas.getData())+" ás "+String.valueOf(consultas.getHora()));
    }

    @Override
    public int getItemCount() {
        return consulta.size();
    }

    class ResultViewHolder extends RecyclerView.ViewHolder{

        TextView nome;
        TextView data;

        public ResultViewHolder(View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.horaRecID);
            data = itemView.findViewById(R.id.dataConsultaID);

        }
    }

}
