package com.pepe.deporte.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.pepe.deporte.adapter.GruposTodosRecyclerViewAdapter;
import com.pepe.deporte.R;
import com.pepe.deporte.listener.GruposInteractionListener;
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


/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link GruposInteractionListener}
 * interface.
 */
public class GruposTodosFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    private GruposInteractionListener mListener;
    private GruposTodosRecyclerViewAdapter adapter;

    private Context ctx;
    private List<GrupoResponse> listaGrupos;
    private RecyclerView recyclerView;

    private SwipeRefreshLayout swipe;

    private ImageView btnBuscar;
    private EditText buscarGrupoPorNombre;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public GruposTodosFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static GruposTodosFragment newInstance(int columnCount) {
        GruposTodosFragment fragment = new GruposTodosFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grupos_list, container, false);

        buscarGrupoPorNombre = view.findViewById(R.id.findGrupo);
        btnBuscar = view.findViewById(R.id.btnBuscarGrupo);

        // Set the adapter
        if (view instanceof SwipeRefreshLayout) {
            Context context = view.getContext();

            recyclerView = view.findViewById(R.id.my_list);

            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            listaGrupos = new ArrayList<>();
            this.buscarGrupo(false);
            //this.getGrupos(false);

            swipe = (SwipeRefreshLayout) view;
            swipe.setOnRefreshListener(
                    new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            Log.i("TAG", "onRefresh called from SwipeRefreshLayout");

                            // This method performs the actual data-refresh operation.
                            // The method calls setRefreshing(false) when it's finished.
                            buscarGrupoPorNombre.setText("");
                            getGrupos(true);
                            if (swipe.isRefreshing()) {
                                swipe.setRefreshing(false);
                            }
                        }
                    }
            );
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.ctx = context;

        if (context instanceof GruposInteractionListener) {
            mListener = (GruposInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement GruposInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void buscarGrupo(final boolean update) {
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("clic buscar","entra" );

                String findProducto = buscarGrupoPorNombre.getText().toString().trim();

                final ProgressDialog progressDialog = new ProgressDialog(getContext(), R.style.Theme_AppCompat_DayNight_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Buscando Grupos...");
                progressDialog.show();

                InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(buscarGrupoPorNombre.getWindowToken(), 0);


                GrupoService service = ServiceGenerator.createService(GrupoService.class, Utils.getToken(ctx), TipoAutenticacion.JWT);
                Call<ResponseContainer<GrupoResponse>> call = service.listaGruposQuery(findProducto);

                call.enqueue(new Callback<ResponseContainer<GrupoResponse>>() {
                    @Override
                    public void onResponse(Call<ResponseContainer<GrupoResponse>> call, Response<ResponseContainer<GrupoResponse>> response) {
                        if (response.isSuccessful()) {
                            Log.e("retrofit","ok" );
                            progressDialog.dismiss();
                            listaGrupos = response.body().getRows();
                            adapter = new GruposTodosRecyclerViewAdapter(
                                    ctx,
                                    listaGrupos,
                                    mListener,
                                    "search"
                            );
                            recyclerView.setAdapter(adapter);
                        } else {
                            Log.e("retrofit","error" );

                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Error al obtener los datos", Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseContainer<GrupoResponse>> call, Throwable t) {
                        Log.e("retrofit","error" );

                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Error de conexión.", Toast.LENGTH_LONG).show();

                    }
                });
            }
        });

    }

    public void getGrupos(final boolean update){

        GrupoService service = ServiceGenerator.createService(GrupoService.class, Utils.getToken(ctx), TipoAutenticacion.JWT);
        Call<ResponseContainer<GrupoResponse>> call = service.listaGrupos();

        call.enqueue(new Callback<ResponseContainer<GrupoResponse>>() {
            @Override
            public void onResponse(Call<ResponseContainer<GrupoResponse>> call, Response<ResponseContainer<GrupoResponse>> response) {
                if (response.isSuccessful()) {
                    // error
                    listaGrupos = response.body().getRows();
                    adapter = new GruposTodosRecyclerViewAdapter(
                            ctx,
                            listaGrupos,
                            mListener,
                            "search"
                    );

                    recyclerView.setAdapter(adapter);

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
    @Override
    public void onResume() {
        super.onResume();
        //this.buscarGrupo(true);
        this.getGrupos(true);

    }
}
