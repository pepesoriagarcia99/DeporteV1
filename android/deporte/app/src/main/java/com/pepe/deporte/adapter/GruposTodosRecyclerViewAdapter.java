package com.pepe.deporte.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pepe.deporte.DetalleGrupoActivity;
import com.pepe.deporte.R;
import com.pepe.deporte.model.response.GrupoResponse;
import com.pepe.deporte.listener.GruposInteractionListener;
import com.pepe.deporte.retrofit.Utils;
import com.pepe.deporte.retrofit.generator.ServiceGenerator;
import com.pepe.deporte.retrofit.generator.TipoAutenticacion;
import com.pepe.deporte.retrofit.service.JoinService;


import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * {@link RecyclerView.Adapter} that can display a {@link GrupoResponse} and makes a call to the
 * specified {@link GruposInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class GruposTodosRecyclerViewAdapter extends RecyclerView.Adapter<GruposTodosRecyclerViewAdapter.ViewHolder> {

    private final List<GrupoResponse> mValues;
    private final GruposInteractionListener mListener;
    private final Context ctx;
    private final String inicio;


    private String boton;

    public GruposTodosRecyclerViewAdapter(Context ctx, List<GrupoResponse> items, GruposInteractionListener listener, String i) {
        mValues = items;
        mListener = listener;
        this.ctx = ctx;
        this.inicio = i;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_mis_grupos, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);

        holder.titulo.setText(holder.mItem.getTitulo());
        holder.deporte.setText(holder.mItem.getId_deporte().getNombre());
        holder.participantes.setText("Participantes: "
                +(holder.mItem.tamañoIntegrantes()+1)+"/"
                +holder.mItem.getId_deporte().getnParticipantes());
        holder.fecha.setText(holder.mItem.getFecha());

        //fotos con glide
        Glide
                .with(ctx)
                .load(mValues.get(position).getCreador_id().getPicture())
                .into(holder.user);

        holder.salir.setVisibility(View.GONE);
        holder.unirse.setVisibility(View.GONE);
        holder.completo.setVisibility(View.GONE);
        holder.edit.setVisibility(View.GONE);
        holder.delete.setVisibility(View.GONE);
        holder.completo_imagen.setVisibility(View.GONE);

        /*Log.e("deporte participantes: ", String.valueOf(holder.mItem.getId_deporte().getnParticipantes()));
        Log.e("grupo participantes: ", String.valueOf(holder.mItem.tamañoIntegrantes()));*/
        if(inicio.equals("home")){
            if(Utils.getUserId(ctx).equals(holder.mItem.getCreador_id().get_id())){
                holder.edit.setVisibility(View.VISIBLE);
                holder.delete.setVisibility(View.VISIBLE);
            }
            else{
                holder.salir.setVisibility(View.VISIBLE);
                boton = "salir";
                //Log.e("Grupo:  ", "Error");
            }

            if(holder.mItem.getId_deporte().getnParticipantes() == (holder.mItem.tamañoIntegrantes()+1)){
                holder.completo_imagen.setVisibility(View.VISIBLE);
                boton = "completo";
            }
        }
        else {
            //Log.e( "Todos grupos: ","error" );
            if(holder.mItem.tamañoIntegrantes() != 0){
                String integrantes[] = holder.mItem.getIntegrantes();

                if(Arrays.asList(integrantes).contains(Utils.getUserId(ctx)) == true){
                    holder.salir.setVisibility(View.VISIBLE);
                    boton = "salir";
                }
                else{
                    holder.unirse.setVisibility(View.VISIBLE);
                    boton = "unirse";
                }
            }
            else{
                holder.unirse.setVisibility(View.VISIBLE);
                boton = "unirse";
                //Log.e("Grupo:  ", "Error");
            }
            if(holder.mItem.getId_deporte().getnParticipantes() == (holder.mItem.tamañoIntegrantes()+1)){
                holder.completo.setVisibility(View.VISIBLE);
                boton = "completo";
                //holder.salir.setVisibility(View.VISIBLE);
            }
        }

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.deleteDialog(holder.mItem.getId());
                }
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.editDialog(holder.mItem.getId());
                }
            }
        });

        holder.salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    holder.salir.setVisibility(View.GONE);
                    holder.unirse.setVisibility(View.VISIBLE);

                    exit(holder.mItem.getId(), holder.mItem.getTitulo());
                }
            }
        });
        holder.unirse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    holder.salir.setVisibility(View.VISIBLE);
                    holder.unirse.setVisibility(View.GONE);

                    join(holder.mItem.getId(), holder.mItem.getTitulo());
                }
            }
        });

        holder.completo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {

                }
            }
        });

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    String idGrupo = holder.mItem.getId();

                    Bundle parmetros = new Bundle();
                    parmetros.putString("id", idGrupo);
                    parmetros.putString("boton", boton);

                    Intent i = new Intent(ctx, DetalleGrupoActivity.class);
                    i.putExtras(parmetros);
                    ctx.startActivity(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public GrupoResponse mItem;

        public TextView titulo, deporte, participantes, fecha;
        public ImageView miniatura, mapa, user, edit, delete, completo_imagen;
        public Button unirse,salir,completo;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            titulo = view.findViewById(R.id.titulo_mis);
            deporte =  view.findViewById(R.id.deporte_mis);
            participantes =  view.findViewById(R.id.participantes_mis);
            fecha =  view.findViewById(R.id.fecha_mis);

            miniatura = view.findViewById(R.id.miniatura_mis);
            mapa = view.findViewById(R.id.mapa_mis);
            user = view.findViewById(R.id.avatar_chat);

            unirse = view.findViewById(R.id.unirse_mis);
            salir = view.findViewById(R.id.salir_mis);
            completo = view.findViewById(R.id.completo_mis);

            edit = view.findViewById(R.id.edit_mis);
            delete = view.findViewById(R.id.delete_mis);

            completo_imagen = view.findViewById(R.id.imagen_mis);
        }
    }
    public void join(String id, final String titulo){
        JoinService service = ServiceGenerator.createService(JoinService.class, Utils.getToken(ctx), TipoAutenticacion.JWT);
        Call<String> call = service.Join(id);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {

                    Log.e("join", response.message());
                    Toast.makeText(ctx, "Te has unido al grupo "+titulo, Toast.LENGTH_LONG);
                } else {
                    Log.e("RequestError", response.message());
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("RequestError", "onFailure");
            }
        });
    }
    public void exit(String id, final String titulo){
        JoinService service = ServiceGenerator.createService(JoinService.class, Utils.getToken(ctx), TipoAutenticacion.JWT);
        Call<String> call = service.Exit(id);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Log.e("exit", response.message());
                    Toast.makeText(ctx, "Te has salido del grupo "+titulo, Toast.LENGTH_LONG);
                } else {
                    Log.e("RequestError", response.message());
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("RequestError", "onFailure");
            }
        });
    }
}
