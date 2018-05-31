package com.app.streem.doctormatch;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AgendConcluido extends AppCompatActivity {

    private Button buttonOkConcluido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agend_concluido);

        buttonOkConcluido = findViewById(R.id.buttonOkConcluido);
        buttonOkConcluido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AgendConcluido.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
