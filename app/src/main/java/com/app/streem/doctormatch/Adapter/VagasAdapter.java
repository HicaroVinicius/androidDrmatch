package com.app.streem.doctormatch.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.streem.doctormatch.Modelo.ResultModel;
import com.app.streem.doctormatch.Modelo.VagasModel;
import com.app.streem.doctormatch.R;

import java.util.List;

/**
 * Created by Hicaro on 13/03/2018.
 */

public class VagasAdapter extends RecyclerView.Adapter<VagasAdapter.ResultViewHolder> {

    private List<VagasModel> vagas;
    private Context context;
    private VagasAdapter.OnItemClickListener listener;

    public VagasAdapter(List<VagasModel> vagas, Context context, VagasAdapter.OnItemClickListener listener) {
        this.vagas = vagas;
        this.context = context;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(VagasModel item);
    }


    @Override
    public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_hora,null);
        return new VagasAdapter.ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ResultViewHolder holder, int position) {
        VagasModel vaga = vagas.get(position);
        holder.bind(vagas.get(position), (VagasAdapter.OnItemClickListener) listener);
        holder.horas.setText(String.valueOf(vaga.getHora()));
    }

    @Override
    public int getItemCount() {
        return vagas.size();
    }

    class ResultViewHolder extends RecyclerView.ViewHolder{

        TextView horas;
        public ResultViewHolder(View itemView) {
            super(itemView);

            horas = itemView.findViewById(R.id.horaRecyclerView);
        }

        public void bind(final VagasModel item, final VagasAdapter.OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    listener.onItemClick(item);

                }

            });
        }

    }




}
