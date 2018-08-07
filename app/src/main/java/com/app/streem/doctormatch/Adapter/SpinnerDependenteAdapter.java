package com.app.streem.doctormatch.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.streem.doctormatch.Modelo.DependenteModel;
import com.app.streem.doctormatch.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hicaro on 19/03/2018.
 */

public class SpinnerDependenteAdapter extends BaseAdapter {

    Activity activity;
    List<DependenteModel> dependenteModels = new ArrayList<>();
    LayoutInflater inflater;

    public SpinnerDependenteAdapter(Activity activity, List<DependenteModel> dependenteModels) {
        this.activity = activity;
        this.dependenteModels = dependenteModels;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public int getCount() {
        return dependenteModels.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = inflater.inflate(R.layout.spinner_dependente,null);
        TextView nomeDep = row.findViewById(R.id.spinnerDepRow);

        DependenteModel dep = dependenteModels.get(position);
        nomeDep.setText(dep.getNome());
        return row;
    }
}
