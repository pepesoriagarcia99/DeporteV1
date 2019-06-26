package com.pepe.deporte;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pepe.deporte.chat.ChatActivity;
import com.pepe.deporte.dialog.DeleteGrupoDialog;
import com.pepe.deporte.dialog.EditarGrupoDialog;
import com.pepe.deporte.dialog.ExpulsarGrupoDialog;
import com.pepe.deporte.dialog.GaleriaDialog;
import com.pepe.deporte.fragment.MisGruposFragment;
import com.pepe.deporte.listener.DialogListener;
import com.pepe.deporte.model.response.Creador;
import com.pepe.deporte.model.response.OneGrupoResponse;
import com.pepe.deporte.retrofit.Utils;
import com.pepe.deporte.retrofit.generator.ServiceGenerator;
import com.pepe.deporte.retrofit.generator.TipoAutenticacion;
import com.pepe.deporte.retrofit.service.GrupoService;
import com.pepe.deporte.retrofit.service.JoinService;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import iammert.com.expandablelib.ExpandableLayout;
import iammert.com.expandablelib.Section;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleGrupoActivity extends AppCompatActivity implements OnMapReadyCallback, DialogListener {
    private MapView mMap;
    private GoogleMap lanzaMapa;

    private String id;
    private String botonPintar;

    private ImageView deporteIcon;
    private TextView deporte, fecha, descripcion;
    private CollapsingToolbarLayout superiorToolbar;

    private Button unirse, salir, completo;
    private FloatingActionsMenu opc;
    private FloatingActionButton foto, edit, delete;

    private OneGrupoResponse data;

    private Button goChat;

    private SliderLayout sliderLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_grupo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sliderLayout = findViewById(R.id.imageSlider);

        mMap = findViewById(R.id.mapa_detalle);
        mMap.onCreate(savedInstanceState);
        mMap.getMapAsync(DetalleGrupoActivity.this);

        unirse = findViewById(R.id.unirse_detalle);
        salir = findViewById(R.id.salir_detalle);
        completo = findViewById(R.id.completo_detalle);
        opc = findViewById(R.id.opc_user_detalle);

        salir.setVisibility(View.GONE);
        unirse.setVisibility(View.GONE);
        completo.setVisibility(View.GONE);
        opc.setVisibility(View.GONE);

        id = getIntent().getExtras().getString("id");
        botonPintar = getIntent().getExtras().getString("boton");

        goChat = findViewById(R.id.cargar_chat);
        goChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetalleGrupoActivity.this, ChatActivity.class));
            }
        });


        this.oneGrupo(id);
    }
    public void salir(View view){
        salir.setVisibility(View.GONE);
        unirse.setVisibility(View.VISIBLE);
        this.exit(id);
    }
    public void unirse(View view){
        salir.setVisibility(View.VISIBLE);
        unirse.setVisibility(View.GONE);
        this.join(id);
    }
    public void completo(View view){
        Toast.makeText(DetalleGrupoActivity.this, "Este grupo ya esta completo", Toast.LENGTH_LONG);
    }
    public void pintar(){
        cargaMapa();

        if(Utils.getUserId(DetalleGrupoActivity.this).equals(data.getCreador_id().get_id())){
            opc.setVisibility(View.VISIBLE);
            salir.setVisibility(View.GONE);
            unirse.setVisibility(View.GONE);
        }
        else{
            if(botonPintar.equals("unirse")){ unirse.setVisibility(View.VISIBLE); }
            else if(botonPintar.equals("salir")){ salir.setVisibility(View.VISIBLE); }
            else if(botonPintar.equals("completo")){ completo.setVisibility(View.VISIBLE); }
        }

        deporte = findViewById(R.id.deporte_detail);
        fecha = findViewById(R.id.fecha_detail);
        descripcion = findViewById(R.id.descripcion_detail);
        superiorToolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout_detail);
        deporteIcon = findViewById(R.id.deporte_icon_detalle);

        foto = findViewById(R.id.accion_foto);
        edit = findViewById(R.id.accion_edit);
        delete = findViewById(R.id.accion_delete);

        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GaleriaDialog dialogo = new GaleriaDialog();
                dialogo.contexto(DetalleGrupoActivity.this);
                dialogo.show(DetalleGrupoActivity.this.getSupportFragmentManager (), "galeriaDialogo");
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditarGrupoDialog dialogo = new EditarGrupoDialog();
                dialogo.idGrupoEdit(id);
                dialogo.contexto(DetalleGrupoActivity.this);
                dialogo.inicio(true);
                dialogo.show(DetalleGrupoActivity.this.getSupportFragmentManager (), "editDialogo");
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteGrupoDialog dialogo = new DeleteGrupoDialog();
                dialogo.idDelete(id);
                dialogo.contexto(DetalleGrupoActivity.this);
                dialogo.inicio(true);
                dialogo.show(DetalleGrupoActivity.this.getSupportFragmentManager (), "deleteDialogo");
            }
        });

        superiorToolbar.setTitle(data.getTitulo());
        //superiorToolbar.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        deporte.setText(data.getId_deporte().getNombre());
        fecha.setText(data.getFecha());
        descripcion.setText(data.getDescripcion());
        Glide.with(DetalleGrupoActivity.this).load(data.getId_deporte().getFoto_id()).into(deporteIcon);


        ExpandableLayout expandableLayout = findViewById(R.id.expandable_layout);
        expandableLayout.setRenderer(new ExpandableLayout.Renderer<String, Creador>() {
            @Override
            public void renderParent(final View view, String s, final boolean isExpandable, int i) {
                ((TextView)view.findViewById(R.id.parent_name)).setText("Integrantes");
                //view.findViewById(R.id.flecha).setBackgroundResource(isExpandable?R.drawable.ic_keyboard_arrow_up_black_24dp:R.drawable.ic_keyboard_arrow_down_black_24dp);

                view.findViewById(R.id.flecha).setBackgroundResource(R.drawable.ic_keyboard_arrow_down_blanca_24dp);

                /*view.findViewById(R.id.flecha).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        view.findViewById(R.id.flecha).setBackgroundResource(R.drawable.ic_keyboard_arrow_up_blanca_24dp);
                    }
                });
                if(isExpandable == true){
                    view.findViewById(R.id.flecha).setBackgroundResource(R.drawable.ic_keyboard_arrow_up_blanca_24dp);
                }else {
                    view.findViewById(R.id.flecha).setBackgroundResource(R.drawable.ic_keyboard_arrow_down_blanca_24dp);
                }*/
            }

            @Override
            public void renderChild(View view, final Creador user, int i, int i1) {
                Glide.with(DetalleGrupoActivity.this).load(user.getPicture()).into(((ImageView)view.findViewById(R.id.child_foto)));
                ((TextView)view.findViewById(R.id.child_name)).setText(user.getEmail());

                Button btn_expulsar = view.findViewById(R.id.expulsar_grupo);
                if(Utils.getUserId(DetalleGrupoActivity.this).equals(data.getCreador_id().get_id())){
                    btn_expulsar.setVisibility(View.VISIBLE);
                }
                else{
                    btn_expulsar.setVisibility(View.GONE);
                }

                btn_expulsar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ExpulsarGrupoDialog dialogo = new ExpulsarGrupoDialog();
                        dialogo.idGrupo(id);
                        dialogo.contexto(DetalleGrupoActivity.this);
                        dialogo.inicio(true);
                        dialogo.setUsuarioName(user.getEmail());
                        dialogo.show(DetalleGrupoActivity.this.getSupportFragmentManager (), "deleteDialogo");
                    }
                });
            }

        });
        expandableLayout.addSection(getSection());

        sliderLayout.setScrollTimeInSec(5); //set scroll delay in seconds :
        setSliderViews();
    }
    private void setSliderViews() {

        for (int i = 0; i <= 3; i++) {
            DefaultSliderView sliderView = new DefaultSliderView(DetalleGrupoActivity.this);
            switch (i) {
                case 0:
                    sliderView.setImageUrl("http://www.diariodevalderrueda.es/multimedia/images/carrerastraveseraddv.jpg");
                    break;
                case 1:
                    sliderView.setImageUrl("https://www.diariovasco.com/multimedia/201409/04/media/Unidos-Dominicana04.jpg");
                    break;
                case 2:
                    sliderView.setImageUrl("https://www.larioja.com/multimedia/201606/22/media/suecia-belgica/A1-53175816.jpg");
                    break;
                case 3:
                    sliderView.setImageUrl("http://graellsia.com/onewebmedia/Can%CC%83o%CC%81nAcen%CC%83a1107201740.jpg");
                    break;
            }
            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {
                    Toast.makeText(DetalleGrupoActivity.this, "Texto detalle " , Toast.LENGTH_SHORT).show();
                }
            });
            sliderLayout.addSliderView(sliderView);
        }
    }
    public Section<String, Creador> getSection(){
        Section<String, Creador> section = new Section<>();

        section.parent = "Integrantes";
        section.children.addAll(data.tIntegrantes());
        return section;
    }
    public void oneGrupo(String id){
        GrupoService service = ServiceGenerator.createService(GrupoService.class, Utils.getToken(DetalleGrupoActivity.this), TipoAutenticacion.JWT);
        Call<OneGrupoResponse> call = service.OneGrupo(id);
        call.enqueue(new Callback<OneGrupoResponse>() {
            @Override
            public void onResponse(Call<OneGrupoResponse> call, Response<OneGrupoResponse> response) {
                if (response.isSuccessful()) {
                    data = response.body();
                    pintar();
                    Log.e("RequestSuccessful", response.message());
                } else {
                    Log.e("RequestError-->", response.message());
                }
            }
            @Override
            public void onFailure(Call<OneGrupoResponse> call, Throwable t) {
                Log.e("RequestError", "onFailure");
            }
        });
    }
    public void join(String id){
        JoinService service = ServiceGenerator.createService(JoinService.class, Utils.getToken(DetalleGrupoActivity.this), TipoAutenticacion.JWT);
        Call<String> call = service.Join(id);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Log.e("join", response.message());
                    Toast.makeText(DetalleGrupoActivity.this, "Te has unido al grupo "+data.getTitulo(), Toast.LENGTH_LONG);
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
    public void exit(String id){
        JoinService service = ServiceGenerator.createService(JoinService.class, Utils.getToken(DetalleGrupoActivity.this), TipoAutenticacion.JWT);
        Call<String> call = service.Exit(id);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Log.e("exit", response.message());
                    Toast.makeText(DetalleGrupoActivity.this, "Te has salido del grupo "+data.getTitulo(), Toast.LENGTH_LONG);
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
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(mMap != null){
            mMap.onResume();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        if(mMap != null){
            mMap.onStart();
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        mMap.onStop();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMap.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMap.onLowMemory();
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(mMap != null){
            mMap.onPause();
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        lanzaMapa = googleMap;
        if(data != null){
            String localizacion = data.getLocalizacion();
            String[] parts = localizacion.split(",");
            String lat_t = parts[0];
            String lon_t = parts[1];

            LatLng ubi = new LatLng(Double.parseDouble(lat_t), Double.parseDouble(lon_t));
            googleMap.addMarker(new MarkerOptions()
                    .position(ubi)
                    .title(data.getTitulo())
                    .snippet(data.getFecha())
            );
            Log.e( "onMapReady: ", String.valueOf(ubi));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubi,10));
        }
        else{
            Log.e("onMapReady: ","No Data" );
        }
    }
    public void cargaMapa(){
        String localizacion = data.getLocalizacion();
        String[] parts = localizacion.split(",");
        String lat_t = parts[0];
        String lon_t = parts[1];

        LatLng ubi = new LatLng(Double.parseDouble(lat_t), Double.parseDouble(lon_t));
        lanzaMapa.addMarker(new MarkerOptions()
                .position(ubi)
                .title(data.getTitulo())
                .snippet(data.getId_deporte().getNombre()+"--"+data.getFecha())
        );
        Log.e( "onMapReady: ", String.valueOf(ubi));
        lanzaMapa.moveCamera(CameraUpdateFactory.newLatLngZoom(ubi,16));
    }



}
