package com.app.streem.doctormatch.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.streem.doctormatch.Modelo.Consulta;
import com.app.streem.doctormatch.Modelo.Estado;
import com.app.streem.doctormatch.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hicaro on 07/03/2018.
 */

public class AdapterEstados extends ArrayAdapter<Estado> {
    public AdapterEstados(Context context, ArrayList<Estado> estados) {
        super(context, 0, estados);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Estado estado = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_seletor, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.listviewCity);
        // Populate the data into the template view using the data object
        tvName.setText(estado.getEstado());
        // Return the completed view to render on screen
        return convertView;
    }
}
