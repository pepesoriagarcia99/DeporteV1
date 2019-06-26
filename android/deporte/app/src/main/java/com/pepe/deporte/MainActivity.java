package com.pepe.deporte;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.SearchView;

import com.pepe.deporte.dialog.CrearGrupoDialog;
import com.pepe.deporte.dialog.DeleteGrupoDialog;
import com.pepe.deporte.dialog.EditUserDialog;
import com.pepe.deporte.dialog.EditarGrupoDialog;
import com.pepe.deporte.dialog.GaleriaDialog;
import com.pepe.deporte.dialog.NewPassDialog;
import com.pepe.deporte.fragment.GruposTodosFragment;
import com.pepe.deporte.fragment.MapasFragment;
import com.pepe.deporte.fragment.MisGruposFragment;
import com.pepe.deporte.fragment.UserFragment;
import com.pepe.deporte.listener.GruposInteractionListener;
import com.pepe.deporte.listener.MapasInteractionListener;
import com.pepe.deporte.listener.Recargar;
import com.pepe.deporte.listener.UserInteractionListener;

import java.util.ArrayList;
import java.util.List;
                                                                                                                            //filtro----------------
public class MainActivity extends AppCompatActivity implements GruposInteractionListener, UserInteractionListener, Recargar, MapasInteractionListener/* ,SearchView.OnQueryTextListener*/ {
    private Menu menuSuperior;
    private MenuItem mapa;

    private FloatingActionButton crearGrupo;

    private boolean listaMapa = false;

    private boolean click = false;
    //private View viewBotonAñadir;


    //filtrar------------
    /*private List<String> names;*/
    //-----------------------
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            BottomNavigationView n = (BottomNavigationView) findViewById(R.id.navigation);

            Menu menu = n.getMenu();
            menu.findItem(R.id.navigation_home).setIcon(R.drawable.home_blanco);
            menu.findItem(R.id.navigation_search).setIcon(R.drawable.search_24px);
            menu.findItem(R.id.navigation_user).setIcon(R.drawable.person_blanco);

            crearGrupo = (FloatingActionButton) findViewById(R.id.add_grupo);

            mapa = menuSuperior.findItem(R.id.lista_m_superior);




            switch (item.getItemId()) {
                case R.id.navigation_home:
                    listaMapa = false;

                    item.setIcon(R.drawable.ic_home_black_24dp);
                    crearGrupo.show();

                    mapa.setIcon(R.drawable.mapa_blanco);
                    mapa.setVisible(true);


                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.containerFragmentMain, new MisGruposFragment(), "misGrupos")
                            .commit();

                    return true;
                case R.id.navigation_search:
                    item.setIcon(R.drawable.search_24px);
                    crearGrupo.hide();

                    mapa.setVisible(false);


                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.containerFragmentMain, new GruposTodosFragment(), "buscarGrupos")
                            .commit();

                    return true;
                case R.id.navigation_user:
                    item.setIcon(R.drawable.person_24px);
                    crearGrupo.hide();
                    mapa.setVisible(false);


                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.containerFragmentMain, new UserFragment(), "buscarGrupos")
                            .commit();

                    return true;
            }
            return false;
        }
    };

    @Override
    public void onAttachFragment(Fragment fragment) {
         super.onAttachFragment(fragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //if(!Utils.getToken(MainActivity.this).isEmpty()){}
        getMenuInflater().inflate(R.menu.menu_superior, menu);
        this.menuSuperior = menu;

        crearGrupo = findViewById(R.id.add_grupo);
        mapa = menuSuperior.findItem(R.id.lista_m_superior);
        mapa.setVisible(true);

        //buscar--------------
        /*SearchView search = (SearchView) buscar.getActionView();
        search.setOnQueryTextListener(this);*/
        //--------------------

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.lista_m_superior:
                //cambiar icon
                if(listaMapa == true){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.containerFragmentMain, new MisGruposFragment(), "misGrupos")
                            .commit();

                    item.setIcon(R.drawable.mapa_blanco);
                    listaMapa = false;
                }else{
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.containerFragmentMain, new MapasFragment(), "mapa")
                            .commit();

                    item.setIcon(R.drawable.ic_list_black_24dp);
                    listaMapa = true;
                }
                return true;

            case R.id.logout:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                //borrar el token ya lo hace el onCreate de login
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Menu menu = navigation.getMenu();
        menu.findItem(R.id.navigation_home).setIcon(R.drawable.ic_home_black_24dp);

        //inicio
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.containerFragmentMain, new MisGruposFragment(), "misGrupos")
                .commit();
    }
    //buscar--------------------**
    /*public void updateList(List<String> newList){
        names = new ArrayList<>();
        names.addAll(newList);
        //notifyDataSetChanged();
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
    @Override
    public boolean onQueryTextChange(String newText) {
        String input = newText.toLowerCase();
        List<String> newList = new ArrayList<>();

        for(String name : names){
            if(name.toLowerCase().contains(input)){
                newList.add(name);
            }
        }
        updateList(newList);
        return false;
    }*/
    //-----------------------------**
    public void OpenDialogNewGroup(View view) {
        /*viewBotonAñadir = view;

        click =! click;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Interpolator interpolador = AnimationUtils.loadInterpolator(getBaseContext(), android.R.interpolator.fast_out_slow_in);

            view.animate()
                    .rotation(click ? 45f : 0)
                    .setInterpolator(interpolador)
                    .start();
        }*/

        CrearGrupoDialog dialogo = new CrearGrupoDialog();
        dialogo.contexto(MainActivity.this);
        dialogo.show(MainActivity.this.getSupportFragmentManager (), "addDialog");

        //rotarIcon(click);
    }

    @Override
    public void deleteDialog(String id) {
        DeleteGrupoDialog dialogo = new DeleteGrupoDialog();
        dialogo.idDelete(id);
        dialogo.contexto(MainActivity.this);
        dialogo.inicio(false);
        dialogo.show(MainActivity.this.getSupportFragmentManager (), "deleteDialogo");
    }

    @Override
    public void editDialog(String id) {
        EditarGrupoDialog dialogo = new EditarGrupoDialog();
        dialogo.idGrupoEdit(id);
        dialogo.contexto(MainActivity.this);
        dialogo.inicio(false);
        dialogo.show(MainActivity.this.getSupportFragmentManager (), "editDialogo");
    }

    public void recargar() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerFragmentMain, new MisGruposFragment(), "misGrupos")
                .commit();
    }


    @Override
    public void avatar() {
        GaleriaDialog dialogo = new GaleriaDialog();
        dialogo.contexto(MainActivity.this);
        dialogo.show(MainActivity.this.getSupportFragmentManager (), "galeriaDialogo");
    }
    @Override
    public void editarUsuario() {
        EditUserDialog dialogo = new EditUserDialog();
        dialogo.contexto(MainActivity.this);
        dialogo.show(MainActivity.this.getSupportFragmentManager (), "galeriaDialogo");
    }

    @Override
    public void cambiarcontraseña() {
        NewPassDialog dialogo = new NewPassDialog();
        dialogo.contexto(MainActivity.this);
        dialogo.show(MainActivity.this.getSupportFragmentManager (), "NewPassDialog");
    }
 }
