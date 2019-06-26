package com.pepe.deporte.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
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
import com.pepe.deporte.listener.GruposInteractionListener;
import com.pepe.deporte.listener.Recargar;
import com.pepe.deporte.model.response.GrupoResponse;
import com.pepe.deporte.retrofit.Utils;
import com.pepe.deporte.retrofit.generator.ServiceGenerator;
import com.pepe.deporte.retrofit.generator.TipoAutenticacion;
import com.pepe.deporte.retrofit.service.JoinService;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MisGruposRecyclerViewAdapter extends RecyclerView.Adapter<MisGruposRecyclerViewAdapter.ViewHolder> implements Recargar{

    private final List<GrupoResponse> mValues;
    private final GruposInteractionListener mListener;
    private final Context ctx;
    private final String inicio;

    private Recargar r;

    private String boton;

    public MisGruposRecyclerViewAdapter(Context ctx, List<GrupoResponse> items, GruposInteractionListener listener, String i) {
        mValues = items;
        mListener = listener;
        this.ctx = ctx;
        this.inicio = i;
    }

    @Override
    public MisGruposRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_grupos, parent, false);
        return new MisGruposRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MisGruposRecyclerViewAdapter.ViewHolder holder, final int position) {
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

        if(inicio.equals("home")){
            if(Utils.getUserId(ctx).equals(holder.mItem.getCreador_id().get_id())){
                holder.edit.setVisibility(View.VISIBLE);
                holder.delete.setVisibility(View.VISIBLE);
            }
            else{
                holder.salir.setVisibility(View.VISIBLE);
                boton = "salir";
            }

            if(holder.mItem.getId_deporte().getnParticipantes() == (holder.mItem.tamañoIntegrantes()+1)){
                holder.completo_imagen.setVisibility(View.VISIBLE);
                boton = "completo";
            }
        }
        else {
            if(holder.mItem.tamañoIntegrantes() != 0){
                String integrantes[] = holder.mItem.getIntegrantes();

                if(Arrays.asList(integrantes).contains(Utils.getUserId(ctx)) == true){
                    boton = "salir";
                    holder.salir.setVisibility(View.VISIBLE);
                }
                else{
                    boton = "unirse";
                    holder.unirse.setVisibility(View.VISIBLE);
                }
            }
            else{
                holder.unirse.setVisibility(View.VISIBLE);
                boton = "unirse";
            }
            if(holder.mItem.getId_deporte().getnParticipantes() == (holder.mItem.tamañoIntegrantes()+1)){
                boton = "completo";
                holder.completo.setVisibility(View.VISIBLE);
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
                    Toast.makeText(ctx, "Este Grupo esta Completo", Toast.LENGTH_LONG);
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

    @Override
    public void recargar() {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public GrupoResponse mItem;

        public TextView titulo, deporte, participantes, fecha;
        public ImageView miniatura, mapa, user, edit, delete, completo_imagen;
        public Button unirse,salir,completo;

        //private ConstraintLayout layout;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            titulo = view.findViewById(R.id.titulo_todos);
            deporte =  view.findViewById(R.id.deporte_todos);
            participantes =  view.findViewById(R.id.participantes_todos);
            fecha =  view.findViewById(R.id.fecha_todos);

            miniatura = view.findViewById(R.id.miniatura_todos);
            mapa = view.findViewById(R.id.mapa_todos);
            user = view.findViewById(R.id.user_todos);

            unirse = view.findViewById(R.id.unirse_todos);
            salir = view.findViewById(R.id.salir_todos);
            completo = view.findViewById(R.id.completo_todos);

            edit = view.findViewById(R.id.edit_todos);
            delete = view.findViewById(R.id.delete_todos);

            completo_imagen = view.findViewById(R.id.imagen_completo);

            //layout = view.findViewById(R.id.mis_grupos_constraint);
        }
    }
    public void join(String id, final String titulo){
        JoinService service = ServiceGenerator.createService(JoinService.class, Utils.getToken(ctx), TipoAutenticacion.JWT);
        Call<String> call = service.Join(id);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    r = (Recargar) ctx;
                    r.recargar();

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
                    r = (Recargar) ctx;
                    r.recargar();

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
