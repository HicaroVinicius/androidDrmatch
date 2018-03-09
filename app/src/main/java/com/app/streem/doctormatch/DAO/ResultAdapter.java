package com.app.streem.doctormatch.DAO;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.streem.doctormatch.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/**
 * Created by Hicaro on 06/03/2018.
 */

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultViewHolder> {

    private List<ResultMedicos> listaMedicos;
    private Context context;

    public ResultAdapter(List<ResultMedicos> listaMedicos, Context context) {
        this.listaMedicos = listaMedicos;
        this.context = context;
    }

    @Override
    public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_result,null);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ResultViewHolder holder, int position) {

        ResultMedicos medicos = listaMedicos.get(position);
        holder.valor.setText("R$ "+String.valueOf(medicos.getValor()));
      holder.crmResultID.setText(String.valueOf(medicos.getRegistro()));
      holder.endereco1ResultID.setText(String.valueOf(medicos.getEndereco1()));
      holder.endereco2ResultID.setText(String.valueOf(medicos.getEndereco2()));
      holder.medicoResultID.setText(String.valueOf(medicos.getTitular()));
      holder.localResultID.setText(String.valueOf(medicos.getLocal()));
    }

    @Override
    public int getItemCount() {
        return listaMedicos.size();
    }

    class ResultViewHolder extends RecyclerView.ViewHolder{

        RoundedImageView fotoResultID;
        ImageView avaliacao1ResultID;
        ImageView avaliacao2ResultID;
        ImageView avaliacao3ResultID;
        ImageView imageView11;
        TextView textView2;
        TextView medicoResultID;
        TextView localResultID;
        TextView textView11;
        TextView crmResultID;
        TextView endereco1ResultID;
        TextView endereco2ResultID;
        TextView valor;
        TextView clinica;
        public ResultViewHolder(View itemView) {
            super(itemView);
            fotoResultID = itemView.findViewById(R.id.fotoResultID);
            avaliacao1ResultID = itemView.findViewById(R.id.avaliacao1ResultID);
            avaliacao2ResultID = itemView.findViewById(R.id.avaliacao2ResultID);
            avaliacao3ResultID = itemView.findViewById(R.id.avalicacao3ResultID);
            imageView11 = itemView.findViewById(R.id.imageView11);
            textView2 = itemView.findViewById(R.id.textView2);
            medicoResultID = itemView.findViewById(R.id.medicoResultID);
            localResultID = itemView.findViewById(R.id.localResultID);
            textView11 = itemView.findViewById(R.id.textView11);
            valor = itemView.findViewById(R.id.valorresultID);
            crmResultID = itemView.findViewById(R.id.crmResultID);
            endereco1ResultID = itemView.findViewById(R.id.endereco1ResultID);
            endereco2ResultID = itemView.findViewById(R.id.endereco2ResultID);
        }
    }
}
