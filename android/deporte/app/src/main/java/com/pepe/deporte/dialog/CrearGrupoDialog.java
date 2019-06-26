package com.pepe.deporte.dialog;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
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
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearGrupoDialog  extends DialogFragment implements GruposInteractionListener, Recargar, DialogListener {
    private static final String TAG = "addDialog";

    private Spinner desplegableDeporte;
    private String idDeporte;

    private EditText titulo_txt, descripcion_txt, localizacion_txt, fecha_txt;
    private ImageButton calendario;

    private View view;
    private Context ctx;

    private GruposInteractionListener mListener;
    private Recargar r;

    private List<Deporte> listaDeporte;
    private List<String> listaPinta;

    private GrupoCreate grupoCreate;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)  {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        view = getActivity().getLayoutInflater().inflate(R.layout.dialog_create_grupo,null);

        listaDeporte = new ArrayList<>();
        listaPinta = new ArrayList<>();


        builder.setTitle("Añadir Grupo")
                .setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        grupoCreate = new GrupoCreate(idDeporte,titulo_txt.getText().toString(),
                                descripcion_txt.getText().toString(), localizacion_txt.getText().toString(),
                                fecha_txt.getText().toString());

                        this.createGrupo();
                    }
                    private void createGrupo() {
                        GrupoService service = ServiceGenerator.createService(GrupoService.class, Utils.getToken(ctx), TipoAutenticacion.JWT);
                        Call<String> call = service.GrupoCreate(grupoCreate);
                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if (response.code() != 201) {
                                    r = (Recargar) ctx;
                                    r.recargar();
                                    Toast.makeText(ctx , "Creado Correctamente", Toast.LENGTH_LONG);
                                } else {
                                    Toast.makeText(ctx , "Error durante la creacion", Toast.LENGTH_LONG);
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
                        dialog.dismiss();
                    }
                });
        titulo_txt = view.findViewById(R.id.titulo_create);
        descripcion_txt = view.findViewById(R.id.descripcion_create);
        localizacion_txt = view.findViewById(R.id.localizacion_create);
        fecha_txt = view.findViewById(R.id.fecha_create);

        //desplegable
        desplegableDeporte = view.findViewById(R.id.desplegable_deporte_create);
        this.getDeporte();
        desplegableDeporte.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for(int c=0;c<listaDeporte.size();c++){
                    if(parent.getItemAtPosition(position).toString().equals(listaDeporte.get(c).getNombre())){
                        idDeporte = listaDeporte.get(c).getId();
                        Log.e("Id: ",idDeporte );
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.e("ok", "ey");
            }
        });
        //

        builder.setView(view);
        return builder.create();
    }

    public void getDeporte(){
        DeporteService service = ServiceGenerator.createService(DeporteService.class);
        Call<ResponseContainer<Deporte>> call = service.listaDeportes();
        call.enqueue(new Callback<ResponseContainer<Deporte>>() {
            @Override
            public void onResponse(Call<ResponseContainer<Deporte>> call, Response<ResponseContainer<Deporte>> response) {
                if (response.isSuccessful()) {
                    listaDeporte = response.body().getRows();

                    listaPinta.add("**Deportes**");
                    for (int i=0;i<listaDeporte.size();i++){
                        listaPinta.add(listaDeporte.get(i).getNombre());
                    }

                    ArrayAdapter<String> comboAdapter = new ArrayAdapter<>(ctx,android.R.layout.simple_spinner_item, listaPinta);
                    comboAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    desplegableDeporte.setAdapter(comboAdapter);
                    desplegableDeporte.setSelection(0);
                }else {
                    Log.e("RequestError", response.message());
                    Toast.makeText(ctx , "Error Categorias", Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(Call<ResponseContainer<Deporte>> call, Throwable t) {
                Log.e("RequestError", "onFailure");
            }
        });
    }

    public void contexto(Context ctx){
        this.ctx = ctx;
    }

    @Override
    public void recargar() {

    }


    @Override
    public void deleteDialog(String id) {

    }

    @Override
    public void editDialog(String id) {

    }

}
