package com.pepe.deporte;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.pepe.deporte.model.Registro;
import com.pepe.deporte.model.response.LoginResponse;
import com.pepe.deporte.retrofit.Utils;
import com.pepe.deporte.retrofit.generator.ServiceGenerator;
import com.pepe.deporte.retrofit.service.LoginService;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroActivity extends AppCompatActivity {
    EditText name, email, pass1, pass2;
    Spinner tipo;
    String tipo_txt;

    //firebase__chat
    FirebaseAuth authLogin;
    FirebaseAuth authRegister;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        FirebaseApp.initializeApp(RegistroActivity.this);
        //FirebaseApp.initializeApp(this).getInstance().getToken(true);
        //FirebaseInstanceId.getInstance().getToken();

        getSupportActionBar().hide();

        name = findViewById(R.id.name_u);
        email = findViewById(R.id.email_u);
        pass1 = findViewById(R.id.pass1);
        pass2 = findViewById(R.id.pass2);
        tipo = findViewById(R.id.tipo_u);

        authRegister = FirebaseAuth.getInstance();
        authLogin = FirebaseAuth.getInstance();

        Utils.setData(this,"","","","","");
    }
    public void directoLogin(View view){
        startActivity(new Intent(RegistroActivity.this, LoginActivity.class));
        finish();
    }
    public void registro(final View view){
        final ProgressDialog progressDialog = new ProgressDialog(RegistroActivity.this, R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Registrando...");
        progressDialog.show();

        String fullname_txt = name.getText().toString().trim();
        final String email_txt = email.getText().toString().trim();
        final String password1_txt = pass1.getText().toString().trim();
        String password2_txt = pass2.getText().toString().trim();

        tipo_txt = "Particular";
        tipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipo_txt = parent.getItemAtPosition(position).toString();
                Log.e("spinner selected: ",tipo_txt );
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                tipo_txt = "Particular";
            }
        });

        if(!fullname_txt.isEmpty() && !email_txt.isEmpty() && !password1_txt.isEmpty() && !password2_txt.isEmpty()){
            if(password1_txt.equals(password2_txt)){
                if(password1_txt.length() < 6){
                    Toast.makeText(RegistroActivity.this,"Contraseña demasiado corta", Toast.LENGTH_SHORT).show();
                }
                else{
                    //firebase----------------
                    registroFirebaseChat(email_txt,password2_txt,fullname_txt,tipo_txt );
                    //------------------------
                    Registro registro = new Registro(email_txt,password2_txt,fullname_txt,tipo_txt);

                    LoginService service = ServiceGenerator.createService(LoginService.class);

                    Call<LoginResponse> loginReponseCall = service.doRegister(registro);

                    loginReponseCall.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if (response.code() != 201) {
                                //logiFirebase---------------
                                loginFirebaseChat(email_txt, password1_txt);
                                //----------------------------

                                progressDialog.dismiss();
                                pass1.setText("");
                                pass2.setText("");
                                Log.e("RequestError", response.message());
                                Snackbar.make(view,"Error de petición", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                            } else {
                                progressDialog.dismiss();
                                Utils.setData(
                                        RegistroActivity.this,
                                        response.body().getToken(),
                                        response.body().getUser().getId(),
                                        response.body().getUser().getEmail(),
                                        response.body().getUser().getName(),
                                        response.body().getUser().getPicture()
                                );
                                startActivity(new Intent(RegistroActivity.this, MainActivity.class));
                                finish();
                            }
                        }
                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            progressDialog.dismiss();
                            pass1.setText("");
                            pass2.setText("");
                            Snackbar.make(view,"Error en peticion", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        }
                    });
                }
            }
            else {
                progressDialog.dismiss();
                Snackbar.make(view,"Confirme Contraseña", Snackbar.LENGTH_LONG).setAction("Action", null).show();
/*               Log.e("pass 1", password1_txt);
                Log.e("pass 2", password2_txt);
                Log.e("email", fullname_txt);*/
            }
        }
        else{
            progressDialog.dismiss();
            Snackbar.make(view,"Completa todos los campos", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
    }
    public void registroFirebaseChat(String email, String pass, final String name, final String tipo){
        authRegister.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = authRegister.getCurrentUser();
                            assert firebaseUser != null;
                            String userid = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("name", name);
                            hashMap.put("imageURL", "default");
                            hashMap.put("tipo", tipo);

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        /*Intent intent = new Intent(RegistroActivity.this,MainActivity.class);
                                        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        finish();
                                        startActivity(intent);*/
                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(RegistroActivity.this,"Error login Firebase", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void loginFirebaseChat(String email, String pass){
        authLogin.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            /*Intent intent = new Intent(RegistroActivity.this,MainActivity.class);
                                        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();*/
                        }else{
                            Toast.makeText(RegistroActivity.this,"Error login Firebase", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
