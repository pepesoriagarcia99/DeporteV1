package com.pepe.deporte.model.response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OneGrupoResponse {
    private String id;
    private Deporte id_deporte;
    private Creador creador_id;
    private String  titulo;
    private String  descripcion;
    private String  fotos[];
    private Creador  integrantes[];
    private String  localizacion;
    private String  fecha;
    private boolean  bloqueado;
    private String  createdAt;
    private String  updatedAt;

    public OneGrupoResponse(String id, Deporte id_deporte, Creador creador_id, String titulo, String descripcion, String[] fotos, Creador[] integrantes, String localizacion, String fecha, boolean bloqueado, String createdAt, String updatedAt) {
        this.id = id;
        this.id_deporte = id_deporte;
        this.creador_id = creador_id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fotos = fotos;
        this.integrantes = integrantes;
        this.localizacion = localizacion;
        this.fecha = fecha;
        this.bloqueado = bloqueado;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Deporte getId_deporte() {
        return id_deporte;
    }

    public void setId_deporte(Deporte id_deporte) {
        this.id_deporte = id_deporte;
    }

    public Creador getCreador_id() {
        return creador_id;
    }

    public void setCreador_id(Creador creador_id) {
        this.creador_id = creador_id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String[] getFotos() {
        return fotos;
    }

    public void setFotos(String[] fotos) {
        this.fotos = fotos;
    }

    public Creador[] getIntegrantes() {
        return integrantes;
    }

    public void setIntegrantes(Creador[] integrantes) {
        this.integrantes = integrantes;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Creador> tIntegrantes(){
        List<Creador> u = new ArrayList<>();
        u.add(creador_id);
        u.addAll(Arrays.asList(integrantes));
        return u;
    }


    /*public int tamañoIntegrantes(){
        return integrantes.length;
    }
    public String getIdIntegrantes(int i){
        return integrantes[i].get_id();
    }
    public String getPictureIntegrantes(int i){
        return integrantes[i].getPicture();
    }
    public String getEmailIntegrantes(int i){
        return integrantes[i].getEmail();
    }*/
}
