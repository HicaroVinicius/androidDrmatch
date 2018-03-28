package com.app.streem.doctormatch.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.ArrayAdapter;

import com.app.streem.doctormatch.Fragments.ConsultaTab;
import com.app.streem.doctormatch.Fragments.ExameTab;
import com.app.streem.doctormatch.Fragments.RetornoTab;

/**
 * Created by Hicaro on 26/03/2018.
 */

public class TabAdapter extends FragmentStatePagerAdapter {

    int numbTabs;
    ArrayAdapter adapter;

    public TabAdapter(FragmentManager fm, int tabs, ArrayAdapter adapter){
        super(fm);
        this.numbTabs = tabs;
        this.adapter = adapter;
    }

    @Override
    public Fragment getItem(int position) {

        switch(position){

            case 0:
                ConsultaTab consultaTab = new ConsultaTab(adapter);
                return consultaTab;
            case 1:
                ExameTab exameTab = new ExameTab(adapter);
                return exameTab;
            case 2:
                RetornoTab retornoTab = new RetornoTab();
                return retornoTab;
            default:
                return null;

        }

    }

    @Override
    public int getCount() {
        return numbTabs;
    }
}
