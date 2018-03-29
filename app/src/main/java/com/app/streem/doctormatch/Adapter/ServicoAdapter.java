package com.app.streem.doctormatch.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.streem.doctormatch.Modelo.Consulta;
import com.app.streem.doctormatch.Modelo.VagasModel;
import com.app.streem.doctormatch.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hicaro on 29/03/2018.
 */

public class ServicoAdapter extends RecyclerView.Adapter<ServicoAdapter.ResultViewHolder> {

    private ArrayAdapter service;
    private Context context;
    private ServicoAdapter.OnItemClickListener listener;

    public ServicoAdapter(ArrayAdapter service, Context context, ServicoAdapter.OnItemClickListener listener) {
        this.service = service;
        this.context = context;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(String item);
    }

    @Override
    public ServicoAdapter.ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_servicos,null);
        return new ServicoAdapter.ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ServicoAdapter.ResultViewHolder holder, int position) {
        String serv = service.getItem(position).toString();
        holder.bind(serv, listener);
        holder.servic.setText(String.valueOf(serv));
    }


    @Override
    public int getItemCount() {
        return service.getCount();
    }

    class ResultViewHolder extends RecyclerView.ViewHolder{

        TextView servic;
        public ResultViewHolder(View itemView) {
            super(itemView);

           // servic = itemView.findViewById(R.id.especialidadeRecycler);
        }

        public void bind(final String item, final ServicoAdapter.OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    listener.onItemClick(item);

                }

            });
        }

    }


}
