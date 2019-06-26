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
import android.widget.Spinner;
import android.widget.Toast;

import com.pepe.deporte.R;
import com.pepe.deporte.listener.DialogListener;
import com.pepe.deporte.listener.GruposInteractionListener;
import com.pepe.deporte.listener.Recargar;
import com.pepe.deporte.model.GrupoCreate;
import com.pepe.deporte.model.response.Deporte;
import com.pepe.deporte.model.response.ResponseContainer;
import com.pepe.deporte.retrofit.Utils;
import com.pepe.deporte.retrofit.generator.ServiceGenerator;
import com.pepe.deporte.retrofit.generator.TipoAutenticacion;
import com.pepe.deporte.retrofit.service.DeporteService;
import com.pepe.deporte.retrofit.service.GrupoService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewPassDialog extends DialogFragment implements DialogListener {
    private static final String TAG = "editDialog";


    private View view;
    private Context ctx;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)  {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        view = getActivity().getLayoutInflater().inflate(R.layout.new_pass_user,null);

        builder.setTitle("Cambiar Contraseña")
                .setMessage("Estas apunto de cambiar tu contraseña, ¿Estas seguro?")
                .setPositiveButton("Cambiar", new DialogInterface.OnClickListener() {
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


        builder.setView(view);
        return builder.create();
    }

    public void contexto(Context ctx){
        this.ctx = ctx;
    }
}
