package com.pepe.deporte.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.Toast;

import com.pepe.deporte.DetalleGrupoActivity;
import com.pepe.deporte.LoginActivity;
import com.pepe.deporte.MainActivity;
import com.pepe.deporte.R;
import com.pepe.deporte.fragment.MisGruposFragment;
import com.pepe.deporte.listener.DialogListener;
import com.pepe.deporte.listener.Recargar;
import com.pepe.deporte.model.response.GrupoResponse;
import com.pepe.deporte.retrofit.Utils;
import com.pepe.deporte.retrofit.generator.ServiceGenerator;
import com.pepe.deporte.retrofit.generator.TipoAutenticacion;
import com.pepe.deporte.retrofit.service.GrupoService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteGrupoDialog  extends DialogFragment implements DialogListener {
    private Context ctx;

    private DialogListener mListener;
    private Recargar r;

    private String id;

    private boolean inicio = false;
    private DialogInterface dialogInter;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Borar Grupo")
                .setMessage("Estas apunto de borrar una grupo, ¿Estas seguro?")
                .setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialogInter = dialog;
                        this.deleteGrupo( );
                    }
                    private void deleteGrupo() {
                        GrupoService service = ServiceGenerator.createService(GrupoService.class, Utils.getToken(ctx), TipoAutenticacion.JWT);
                        Call<String> call = service.deleteDeporte(id);
                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if (response.isSuccessful()) {
                                    if(inicio == true){
                                        dialogInter.dismiss();
                                        ctx.startActivity(new Intent(ctx, MainActivity.class));
                                    }
                                    else{
                                        r = (Recargar) ctx;
                                        r.recargar();
                                    }
                                    Toast.makeText(ctx , "Creado Correctamente", Toast.LENGTH_LONG);
                                } else {
                                    Toast.makeText(ctx , "Error duarnate la creacion", Toast.LENGTH_LONG);
                                }
                            }
                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Log.e("RequestError", "onFailure");
                            }
                        });
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*mListener = (DialogListener) ctx;
                        mListener.inicio();*/
                        //startActivity(new Intent(ctx, MainActivity.class));
                        dialog.dismiss();
                    }
                });
        return builder.create();
    }

    public void idDelete(String id){this.id = id; }
    public void inicio(boolean inicio){this.inicio = inicio; }
    public void contexto(Context ctx){
        this.ctx = ctx;
    }

}
