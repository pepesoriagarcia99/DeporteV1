package com.pepe.deporte;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.pepe.deporte.model.response.LoginResponse;

import com.pepe.deporte.retrofit.Utils;
import com.pepe.deporte.retrofit.generator.ServiceGenerator;
import com.pepe.deporte.retrofit.service.LoginService;

import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText email, pass;

    FirebaseAuth authLogin;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseApp.initializeApp(LoginActivity.this);
        authLogin = FirebaseAuth.getInstance();

        getSupportActionBar().hide();

        btnLogin = findViewById(R.id.btn_login);
        email = findViewById(R.id.email_u);
        pass = findViewById(R.id.password);

        Utils.setData(this,"","","","","");
        FirebaseAuth.getInstance().signOut();

        email.setText("p@p.com");
        pass.setText("123456");
    }
    public void directoRegistro(View view){
        startActivity(new Intent(LoginActivity.this, RegistroActivity.class));
        finish();
    }
    public void sinLogin(View view){
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }
    public void Login(final View view){
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Login...");
        progressDialog.show();

        final String username_txt = email.getText().toString();
        final String password_txt = pass.getText().toString();


        //no funciona siempre da error en peticion
        if(!username_txt.isEmpty() && !password_txt.isEmpty()){
            String credentials = Credentials.basic(username_txt, password_txt);

            LoginService service = ServiceGenerator.createService(LoginService.class);
            Call<LoginResponse> call = service.doLogin(credentials);

            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.code() != 201) {
                        loginFirebaseChat(username_txt, password_txt);
                        progressDialog.dismiss();
                        // error
                        Log.e("RequestError", response.message());
                        //Toast.makeText(LoginActivity.this, "Error de petición !201", Toast.LENGTH_SHORT).show();
                        Snackbar.make(view,"Error de petición !201", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                        email.setText("");
                        pass.setText("");
                    }
                    else {
                        progressDialog.dismiss();
                        //guardar token en utils
                        Utils.setData(
                                LoginActivity.this,
                                response.body().getToken(),
                                response.body().getUser().getId(),
                                response.body().getUser().getEmail(),
                                response.body().getUser().getName(),
                                response.body().getUser().getPicture()
                        );
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                }
                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.e("NetworkFailure", t.getMessage());
                    //Toast.makeText(LoginActivity.this, "onFailure", Toast.LENGTH_SHORT).show();
                    Snackbar.make(view,"Error de red", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                    email.setText("");
                    pass.setText("");
                }
            });
        }
        else{
            progressDialog.dismiss();
            Snackbar.make(view, "Complete los campos", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
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
                            Toast.makeText(LoginActivity.this,"Error login Firebase", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
