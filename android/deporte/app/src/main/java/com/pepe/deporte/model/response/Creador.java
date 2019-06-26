package com.pepe.deporte.model.response;

public class Creador {
    private String _id;
    private String picture;
    private String email;

    public Creador(String _id, String picture, String email) {
        this._id = _id;
        this.picture = picture;
        this.email = email;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

