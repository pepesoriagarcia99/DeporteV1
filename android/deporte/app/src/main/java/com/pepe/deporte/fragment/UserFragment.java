package com.pepe.deporte.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.pepe.deporte.DetalleGrupoActivity;
import com.pepe.deporte.R;
import com.pepe.deporte.adapter.GruposTodosRecyclerViewAdapter;
import com.pepe.deporte.dialog.EditarGrupoDialog;
import com.pepe.deporte.dialog.ExpulsarGrupoDialog;
import com.pepe.deporte.listener.UserInteractionListener;
import com.pepe.deporte.model.User;
import com.pepe.deporte.model.response.Creador;
import com.pepe.deporte.model.response.GrupoResponse;
import com.pepe.deporte.model.response.ResponseContainer;
import com.pepe.deporte.retrofit.Utils;
import com.pepe.deporte.retrofit.generator.ServiceGenerator;
import com.pepe.deporte.retrofit.generator.TipoAutenticacion;
import com.pepe.deporte.retrofit.service.GrupoService;
import com.pepe.deporte.retrofit.service.UserService;

import org.w3c.dom.Text;

import iammert.com.expandablelib.ExpandableLayout;
import iammert.com.expandablelib.Section;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {
    private Context ctx;
    private UserInteractionListener mListener;

    private View view;

    private User data;

    public UserFragment() {

    }

    public static UserFragment newInstance() {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {

        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user, container, false);
        getMeUser();
        return view;
    }
    public void pintar(){
        ImageView avatar = view.findViewById(R.id.avatar_user);
        Glide
                .with(ctx)
                .load(data.getPicture())
                .into(avatar);

        TextView email, nombre, rol, tipo, fecha;
        email = view.findViewById(R.id.email_user);
        nombre = view.findViewById(R.id.nombre_user);
        rol = view.findViewById(R.id.rol_user);
        tipo = view.findViewById(R.id.tipo_user);
        fecha = view.findViewById(R.id.creacion_user);

        email.setText(data.getEmail());
        nombre.setText(data.getName());
        rol.setText("Rol: "+data.getRole());
        tipo.setText("Tipo: "+data.getType());
        fecha.setText(data.getCreatedAt());

        FloatingActionButton avatar_user, edit_user, edit_pass;
        avatar_user = view.findViewById(R.id.accion_foto_user);
        edit_user = view.findViewById(R.id.accion_edit_user);
        edit_pass = view.findViewById(R.id.accion_edit_pass_user);

        avatar_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.avatar();
            }
        });
        edit_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.editarUsuario();
            }
        });
        edit_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cambiarcontraseña();
            }
        });

        /*ExpandableLayout expandableLayout = view.findViewById(R.id.expandable_layout_user);
        expandableLayout.setRenderer(new ExpandableLayout.Renderer<String, Creador>() {
            @Override
            public void renderParent(final View view, String s, final boolean isExpandable, int i) {
                ((TextView)view.findViewById(R.id.parent_name)).setText("Integrantes");
                //view.findViewById(R.id.flecha).setBackgroundResource(isExpandable?R.drawable.ic_keyboard_arrow_up_black_24dp:R.drawable.ic_keyboard_arrow_down_black_24dp);

                view.findViewById(R.id.flecha).setBackgroundResource(R.drawable.ic_keyboard_arrow_down_blanca_24dp);

                *//*view.findViewById(R.id.flecha).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        view.findViewById(R.id.flecha).setBackgroundResource(R.drawable.ic_keyboard_arrow_up_blanca_24dp);
                    }
                });
                if(isExpandable == true){
                    view.findViewById(R.id.flecha).setBackgroundResource(R.drawable.ic_keyboard_arrow_up_blanca_24dp);
                }else {
                    view.findViewById(R.id.flecha).setBackgroundResource(R.drawable.ic_keyboard_arrow_down_blanca_24dp);
                }*//*
            }

            @Override
            public void renderChild(View view, final Creador user, int i, int i1) {
                Glide.with(ctx).load(user.getPicture()).into(((ImageView)view.findViewById(R.id.child_foto)));
                ((TextView)view.findViewById(R.id.child_name)).setText(user.getEmail());

                Button btn_expulsar = view.findViewById(R.id.expulsar_grupo);
                if(Utils.getUserId(ctx).equals(data.getFriend())){
                    btn_expulsar.setVisibility(View.VISIBLE);
                }
                else{
                    btn_expulsar.setVisibility(View.GONE);
                }

                btn_expulsar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        *//*ExpulsarGrupoDialog dialogo = new ExpulsarGrupoDialog();
                        dialogo.idGrupo(id);
                        dialogo.contexto(DetalleGrupoActivity.this);
                        dialogo.inicio(true);
                        dialogo.setUsuarioName(user.getEmail());
                        dialogo.show(DetalleGrupoActivity.this.getSupportFragmentManager (), "deleteDialogo");*//*
                    }
                });
            }

        });
        expandableLayout.addSection(getSection());*/
    }
    /*public Section<String, Creador> getSection(){
        Section<String, Creador> section = new Section<>();

        section.parent = "Integrantes";
        section.children.addAll(data.tIntegrantes());
        return section;
    }*/
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {


        }
    }

    @Override
    public void onAttach(Context context) {
        this.ctx = context;

        super.onAttach(context);
        if (context instanceof UserInteractionListener) {
            mListener = (UserInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement UserInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
}
