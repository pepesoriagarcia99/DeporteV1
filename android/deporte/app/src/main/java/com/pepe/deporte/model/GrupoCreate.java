package com.pepe.deporte.model;

public class GrupoCreate {
    private String id_deporte;
    private String titulo;
    private String descripcion;
    private String localizacion;
    private String fecha;

    public GrupoCreate(String id_deporte, String titulo, String descripcion, String localizacion, String fecha) {
        this.id_deporte = id_deporte;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.localizacion = localizacion;
        this.fecha = fecha;
    }

    public String getId_deporte() {
        return id_deporte;
    }

    public void setId_deporte(String id_deporte) {
        this.id_deporte = id_deporte;
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
}
