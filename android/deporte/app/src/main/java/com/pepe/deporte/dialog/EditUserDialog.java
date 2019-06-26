package com.pepe.deporte.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.pepe.deporte.R;
import com.pepe.deporte.listener.DialogListener;
import com.pepe.deporte.listener.GruposInteractionListener;
import com.pepe.deporte.listener.Recargar;
import com.pepe.deporte.model.GrupoCreate;
import com.pepe.deporte.model.User;
import com.pepe.deporte.model.response.Deporte;
import com.pepe.deporte.model.response.ResponseContainer;
import com.pepe.deporte.retrofit.Utils;
import com.pepe.deporte.retrofit.generator.ServiceGenerator;
import com.pepe.deporte.retrofit.generator.TipoAutenticacion;
import com.pepe.deporte.retrofit.service.DeporteService;
import com.pepe.deporte.retrofit.service.GrupoService;
import com.pepe.deporte.retrofit.service.UserService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditUserDialog extends DialogFragment implements DialogListener {
    private static final String TAG = "editUserDialog";
    private View view;
    private Context ctx;

    private User data;

    private EditText email, nombre;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)  {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        view = getActivity().getLayoutInflater().inflate(R.layout.edit_user_dialog,null);
        getMeUser();


        builder.setTitle("Editar Perfil")
                .setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        email = view.findViewById(R.id.email_edit_dialog);
        nombre= view.findViewById(R.id.nombre_edit_dialog);

        builder.setView(view);
        return builder.create();
    }
    public void getMeUser(){

        UserService service = ServiceGenerator.createService(UserService.class, Utils.getToken(ctx), TipoAutenticacion.JWT);
        Call<User> call = service.meUser();

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    data = response.body();
                    pintar();
                } else {
                    Log.e("RequestError", response.message());
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("RequestError", "onFailure");
            }
        });
    }
    public void pintar(){
        email.setText(data.getEmail());
        nombre.setText(data.getName());
    }



    public void contexto(Context ctx){
        this.ctx = ctx;
    }



}
