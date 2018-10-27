package com.app.streem.doctormatch;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.streem.doctormatch.DAO.Firebase;
import com.app.streem.doctormatch.DAO.Preferencias;
import com.app.streem.doctormatch.Modelo.Estado;
import com.app.streem.doctormatch.Modelo.UsuarioDados;
import com.app.streem.doctormatch.Modelo.UsuarioRegistro;
import com.app.streem.doctormatch.Modelo.UsuarioRegistro;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "logsLogin";
    private Preferencias preferencias;
    private FirebaseAuth auth;
    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;

    private CallbackManager mCallbackManager;

    private UsuarioDados usuario = null;

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.app.streem.doctormatch",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

        setContentView(R.layout.activity_login);

        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // ...
            }
        });



        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            preferencias = new Preferencias(this);
            Button buttonEntrar = findViewById(R.id.buttonEntrarLoginID);
            Button buttonRegistro = findViewById(R.id.buttonRegistroLoginID);
            final TextInputEditText editTextEmail = findViewById(R.id.emailLoginID);
            final TextInputEditText editTextSenha = findViewById(R.id.senhaLoginID);
            TextView textViewResetSenha = findViewById(R.id.resetSenhaLoginID);
            ImageView imageButtonFacebook = findViewById(R.id.buttonFaceLoginID);
            ImageView imageButtonGoogle = findViewById(R.id.buttonGoogleLoginID);
            ImageView imageButtonTwitter = findViewById(R.id.buttonTwitterLoginID);

            loginGoogle();
            imageButtonGoogle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showLoadingAnimation();
                    signIn();
                }
            });

            buttonRegistro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(LoginActivity.this,CadastroActivity.class);
                    startActivity(i);
                }
            });


            //Login email
            buttonEntrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String emailTexto = editTextEmail.getText().toString();
                    String senhaTexto = editTextSenha.getText().toString();
                    if (emailTexto.isEmpty() | senhaTexto.isEmpty()) {
                        Toast.makeText(LoginActivity.this, "Email ou Senha inválido", Toast.LENGTH_LONG).show();
                    } else {
                        showLoadingAnimation();
                        validarLogin(emailTexto, senhaTexto);
                    }
                }
            });

        }else{
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }


    private void validarLogin(String email, String senna) {
        auth = FirebaseAuth.getInstance();

        auth.signInWithEmailAndPassword(email, senna).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    if(preferencias.getCHAVE_DATABASE().equals("0")) {
                        Firebase.loginDatabase();
                    }
                    final FirebaseUser user = task.getResult().getUser();



                    Firebase.getDatabaseReference().child("APP_USUARIOS").child("DADOS").child(user.getUid()).child("DADOS").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            usuario = dataSnapshot.getValue(UsuarioDados.class);
                            preferencias.setUsuarioLogado(user.getUid(),user.getDisplayName(),user.getPhoneNumber());
                            preferencias.setInfo("dtcont_cidade","1");
                            preferencias.setInfo("dtcont_estado","1");
                            preferencias.setInfo("dtcont_exame","1");
                            preferencias.setInfo("dtcont_espec","1");
                            preferencias.setInfo("dtcont_medico","1");
                            preferencias.setInfo("dtcont_medicoDados","1");
                            preferencias.setInfo("dtcont_consulta","1");
                            preferencias.setInfo("dtcont_dependente","1");


                            preferencias.setInfo("cpf",usuario.getCpf());
                            preferencias.setInfo("url",usuario.getUrl());
                            preferencias.setInfo("dt_cont",usuario.getDt_cont());
                            preferencias.setInfo("email",usuario.getEmail());
                            preferencias.setInfo("nome",usuario.getNome());
                            preferencias.setInfo("id",user.getUid());

                        }
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                    //Log.i("prefLogin",user.getUid()+user.getDisplayName()+user.getPhoneNumber());
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }else{
                    hideLoadingAnimation();
                    Toast.makeText(LoginActivity.this,"Usuário Inválido", Toast.LENGTH_LONG).show();
                }
            }
        });
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

    public void loginGoogle(){
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);

            } else {
                Toast.makeText(LoginActivity.this,"Erro",Toast.LENGTH_SHORT).show();
            }
        }else{
            // Pass the activity result back to the Facebook SDK
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        Firebase.getFirebaseAuth().signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }else {
                            hideLoadingAnimation();
                            FirebaseUser user = task.getResult().getUser();
                            preferencias.setUsuarioLogado(user.getUid(),user.getDisplayName(),user.getPhoneNumber());
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }



}
