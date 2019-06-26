package com.pepe.deporte.model.response;

public class Deporte {
    private String nombre;
    private int nParticipantes;
    private String id;
    private String requisitos;
    private String foto_id;

    public Deporte(String nombre, int nParticipantes, String id) {
        this.nombre = nombre;
        this.nParticipantes = nParticipantes;
        this.id = id;
    }

    public Deporte(String nombre, int nParticipantes, String id, String requisitos, String foto_id) {
        this.nombre = nombre;
        this.nParticipantes = nParticipantes;
        this.id = id;
        this.requisitos = requisitos;
        this.foto_id = foto_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getnParticipantes() {
        return nParticipantes;
    }

    public void setnParticipantes(int nParticipantes) {
        this.nParticipantes = nParticipantes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(String requisitos) {
        this.requisitos = requisitos;
    }

    public String getFoto_id() {
        return foto_id;
    }

    public void setFoto_id(String foto_id) {
        this.foto_id = foto_id;
    }
}
