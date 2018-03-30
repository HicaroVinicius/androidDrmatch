package com.app.streem.doctormatch.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.app.streem.doctormatch.Modelo.Consulta;
import com.app.streem.doctormatch.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Hicaro on 07/03/2018.
 */

public class AdapterConsultas extends RecyclerView.Adapter<AdapterConsultas.ResultViewHolder> {

    public AdapterConsultas(List<Consulta> consulta, Context context, OnItemLongClickListener listener) {
        this.consulta = consulta;
        this.context = context;
        this.listener = listener;
    }

    private List<Consulta> consulta;
    private Context context;
    private OnItemLongClickListener listener;


    public interface OnItemLongClickListener {
        void onItemLongClick(Consulta item);
    }

    @Override
    public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_consultas, null);
        return new AdapterConsultas.ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ResultViewHolder holder, int position) {
        Consulta consultas = consulta.get(position);
        holder.bind(consulta.get(position), (OnItemLongClickListener) listener);
        holder.nome.setText(String.valueOf(consultas.getNomeMedico()));
        holder.info.setText(String.valueOf(consultas.getInfo()));
        holder.espec.setText(String.valueOf(consultas.getEspec()));
    }

    @Override
    public int getItemCount() {
        return consulta.size();
    }

    class ResultViewHolder extends RecyclerView.ViewHolder {

        TextView nome;
        TextView espec;
        TextView info;

        public ResultViewHolder(View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.nomeMedicoConsultaRec);
            espec = itemView.findViewById(R.id.especConsultaRec);
            info = itemView.findViewById(R.id.infoConsultaRec);

        }

        public void bind(final Consulta item, final AdapterConsultas.OnItemLongClickListener listener) {
            itemView.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {

                    listener.onItemLongClick(item);
                    return false;
                }

            });
        }

    }

}
