package com.pepe.deporte.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import com.pepe.deporte.listener.DialogListener;
import com.pepe.deporte.listener.Recargar;

public class GaleriaDialog extends DialogFragment implements DialogListener {
    private Context ctx;
    private DialogListener mListener;
    private DialogInterface dialogInter;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Galeria")
                .setMessage("Esta es la galeria de mi Aplicacion")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
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
        return builder.create();
    }

    public void contexto(Context ctx){
        this.ctx = ctx;
    }

}
