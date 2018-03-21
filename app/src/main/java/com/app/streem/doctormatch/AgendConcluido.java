package com.app.streem.doctormatch;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AgendConcluido extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agend_concluido);

        AlertDialog.Builder confirm = new AlertDialog.Builder(AgendConcluido.this);
        confirm.setTitle("Agendamento Concluído");
        confirm.setIcon(R.drawable.ic_done_black_24dp).setMessage("Parabéns! Agendado com sucesso").setCancelable(true);

        confirm.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = confirm.create();
        alertDialog.show();

    }
}
