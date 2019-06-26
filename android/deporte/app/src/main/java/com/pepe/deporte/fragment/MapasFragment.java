package com.pepe.deporte.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pepe.deporte.DetalleGrupoActivity;
import com.pepe.deporte.MainActivity;
import com.pepe.deporte.R;
import com.pepe.deporte.adapter.MisGruposRecyclerViewAdapter;
import com.pepe.deporte.listener.MapasInteractionListener;
import com.pepe.deporte.model.response.GrupoResponse;
import com.pepe.deporte.model.response.ResponseContainer;
import com.pepe.deporte.retrofit.Utils;
import com.pepe.deporte.retrofit.generator.ServiceGenerator;
import com.pepe.deporte.retrofit.generator.TipoAutenticacion;
import com.pepe.deporte.retrofit.service.GrupoService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapasFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    private MapView mapView;

    private MapasInteractionListener mListener;
    private Context ctx;

    private List<GrupoResponse> listaMapa;


    public MapasFragment() {
            // Required empty public constructor
    }

    public static MapasFragment newInstance() {
        MapasFragment fragment = new MapasFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mapas, container, false);

        return view;
    }

    @Override
    public void onViewCreated( View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mapView = view.findViewById(R.id.map_fragment);

        if(mapView != null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.ctx = context;

        if (context instanceof MapasInteractionListener) {
            mListener = (MapasInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement MapasInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng madrid = new LatLng(40.4211531, -3.6970737);
        /*googleMap.addMarker(
                new MarkerOptions()
                .position(madrid)
                .title("Madrid")
                .snippet("Esta es la Sede")
        );*/
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(madrid,5));

        getMisGruposMapa();
    }

    public void getMisGruposMapa(){
        GrupoService service = ServiceGenerator.createService(GrupoService.class, Utils.getToken(ctx), TipoAutenticacion.JWT);
        Call<ResponseContainer<GrupoResponse>> call = service.myListaGrupos();

        call.enqueue(new Callback<ResponseContainer<GrupoResponse>>() {
            @Override
            public void onResponse(Call<ResponseContainer<GrupoResponse>> call, Response<ResponseContainer<GrupoResponse>> response) {
                if (response.isSuccessful()) {
                    listaMapa = new ArrayList<>();
                    listaMapa = response.body().getRows();
                    aniadirMarkers(listaMapa);
                } else {
                    Log.e("RequestError", response.message());
                }
            }
            @Override
            public void onFailure(Call<ResponseContainer<GrupoResponse>> call, Throwable t) {
                Log.e("RequestError", "onFailure");
            }
        });
    }
    private void aniadirMarkers(List<GrupoResponse> grupos) {
        for (int i = 0; i < grupos.size(); i++) {
            String[] parts = grupos.get(i).getLocalizacion().split(",");
            double lat = Double.valueOf(parts[0]);
            double lon = Double.valueOf(parts[1]);

            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(lat, lon))
                    .title(grupos.get(i).getTitulo())
                    .snippet(grupos.get(i).getId_deporte().getNombre()+"--"+grupos.get(i).getFecha())
            );
        }
    }

}
