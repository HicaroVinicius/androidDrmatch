package com.app.streem.doctormatch;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.streem.doctormatch.DAO.Firebase;
import com.app.streem.doctormatch.DAO.Preferencias;
import com.app.streem.doctormatch.DAO.SDFormat;
import com.app.streem.doctormatch.Modelo.UsuarioDados;
import com.app.streem.doctormatch.Modelo.UsuarioRegistro;
import com.app.streem.doctormatch.Modelo.UsuarioRegistro;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;

public class CadastroActivity extends AppCompatActivity {

    private TextInputEditText dataNasc;
    private TextInputEditText telefone;
    private TextInputEditText sobrenome;
    private TextInputEditText nome;
    private TextInputEditText senha;
    private TextInputEditText email;
    private RadioGroup radioGroup;
    private Calendar myCalendar;
    private String sexo;
    private Preferencias preferencias;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        ImageView voltar = findViewById(R.id.imageView10);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView voltarText = findViewById(R.id.textView28);
        voltarText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sexo = "null";
        preferencias = new Preferencias(this);
        auth = Firebase.getFirebaseAuth();
        dataNasc = findViewById(R.id.dataCadastroID);
        sobrenome = findViewById(R.id.sobrenomeCadastroID);
        //telefone = findViewById(R.id.telefoneCadastroID);
        nome = findViewById(R.id.nomeCadastroID);
        senha = findViewById(R.id.senhaCadastroID);
        email = findViewById(R.id.emailCadastroID);

        MaskEditTextChangedListener maskData = new MaskEditTextChangedListener("##/##/####", dataNasc);
        //MaskEditTextChangedListener maskTel = new MaskEditTextChangedListener("(##)#########", telefone);
        dataNasc.addTextChangedListener(maskData);
        //telefone.addTextChangedListener(maskTel);

        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dataNasc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CadastroActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        radioGroup = findViewById(R.id.radioGroupCadID);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radioButtonMasculinoCadID:
                        sexo = "Masculino";
                        break;
                    case R.id.radioButtonFeminidoCadID:
                        sexo = "Feminino";
                        break;
                }
            }
        });

        Button buttonRegistro = findViewById(R.id.buttonRegistroCadastroID);
        buttonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CadastroActivity.this,"Aguarde...",Toast.LENGTH_SHORT).show();
                if(sexo.equals("null") | nome.getText().toString().isEmpty() |
                        dataNasc.getText().toString().isEmpty() | (dataNasc.getText().toString().length() < 10)
                        | email.getText().toString().isEmpty() | senha.getText().toString().isEmpty()
                        | sobrenome.getText().toString().isEmpty()){
                    Toast.makeText(CadastroActivity.this,"Dados incompletos!",Toast.LENGTH_LONG).show();
                }else {
                    showLoadingAnimation();
                    auth.createUserWithEmailAndPassword(email.getText().toString(),senha.getText().toString())
                            .addOnCompleteListener(CadastroActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        if(Firebase.getFirebaseAuth().getCurrentUser() != null) {
                                            String[] nomeC = nome.getText().toString().split(" ");
                                            String apelido = "";
                                            if(nomeC.length >1){
                                                apelido = (nomeC[0] + " " + nomeC[1]);
                                            }else{
                                                apelido = nomeC[0];
                                            }

                                            SDFormat sdFormat = new SDFormat();
                                            //UsuarioRegistro usuario = new UsuarioRegistro(nome.getText().toString(),apelido,sexo);
                                            //Firebase.getDatabaseReference().child("APP_USUARIO").child(Firebase.getFirebaseAuth().getCurrentUser().getUid()).child("REGISTRO").setValue(usuario);
                                            String uid = Firebase.getFirebaseAuth().getCurrentUser().getUid();
                                            preferencias.setUsuarioLogado(uid,
                                                    apelido, sobrenome.getText().toString());
                                            Date dataA = new Date();

                                            //String nome, String sobrenome,String sexo,  String dt_nasc, String email,String dt_cont,String dt_cad,String adm,String cpf,String url
                                            UsuarioDados usuarioDados = new UsuarioDados(nome.getText().toString(),sobrenome.getText().toString(),sexo,sdFormat.dateToMili(dataNasc.getText().toString()),email.getText().toString(),String.valueOf(dataA.getTime()),String.valueOf(dataA.getTime()),String.valueOf(dataA.getTime()),"","");
                                            Firebase.getDatabaseReference().child("APP_USUARIOS").child("DADOS").child(uid).child("DADOS").setValue(usuarioDados);

                                            //String celular, String fixo, String cidade, String estado, String cep, String bairro, String rua, String numero, String adm
                                            UsuarioRegistro usuarioRegistro = new UsuarioRegistro("","","","","","","","",String.valueOf(dataA.getTime()));
                                            Firebase.getDatabaseReference().child("APP_USUARIOS").child("DADOS").child(uid).child("REGISTRO").setValue(usuarioRegistro);

                                            preferencias.setInfo("dtcont_cidade","1");
                                            preferencias.setInfo("dtcont_estado","1");
                                            preferencias.setInfo("dtcont_exame","1");
                                            preferencias.setInfo("dtcont_espec","1");
                                            preferencias.setInfo("dtcont_medico","1");
                                            preferencias.setInfo("dtcont_medicoDados","1");
                                            preferencias.setInfo("dtcont_consulta","1");
                                            preferencias.setInfo("dtcont_dependente","1");

                                            preferencias.setInfo("email",email.getText().toString());
                                            preferencias.setInfo("nome",nome.getText().toString());
                                            preferencias.setInfo("sexo",sexo);
                                            preferencias.setInfo("cpf","");
                                            preferencias.setInfo("url","");
                                            preferencias.setInfo("id",uid);
                                            Intent i = new Intent(CadastroActivity.this, MainActivity.class);
                                            startActivity(i);
                                            finish();
                                            hideLoadingAnimation();
                                        }
                                    }else{
                                        hideLoadingAnimation();
                                        String erro = "";
                                        try{
                                            throw task.getException();
                                        }catch (FirebaseAuthWeakPasswordException e){
                                            erro = "Sua senha deve ter pelo menos 8 caracteres.";
                                        }catch (FirebaseAuthInvalidCredentialsException e){
                                            erro = "O email digitado é inválido, digite um novo email.";
                                        }catch (FirebaseAuthUserCollisionException e){
                                            erro = "Email já cadastrado! Recupere sua senha.";
                                        } catch (Exception e) {
                                            erro = "Erro ao efetuar o registro! Tente novamente.";
                                            e.printStackTrace();
                                        }
                                        Toast.makeText(CadastroActivity.this,erro, Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });

    }

    private void updateLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        dataNasc.setText(sdf.format(myCalendar.getTime()));
    }

    //show
    public void showLoadingAnimation()
    {
        RelativeLayout pageLoading = (RelativeLayout) findViewById(R.id.main_layoutPageLoading);
        pageLoading.setVisibility(View.VISIBLE);
    }


    //hide
    public void hideLoadingAnimation()
    {
        RelativeLayout pageLoading = (RelativeLayout) findViewById(R.id.main_layoutPageLoading);
        pageLoading.setVisibility(View.GONE);
    }

    //botao voltar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                Intent i = new Intent(CadastroActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
                break;
            default:break;
        }
        return true;
    }
}
