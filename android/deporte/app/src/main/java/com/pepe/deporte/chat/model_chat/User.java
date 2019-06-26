package com.pepe.deporte.chat.model_chat;

public class User {
    private String id;
    private String name;
    private String imageURL;
    private String tipo;

    private String status;
    private String search;

    public User(String id, String name, String imageURL, String tipo, String status, String search) {
        this.id = id;
        this.name = name;
        this.imageURL = imageURL;
        this.tipo = tipo;
        this.status = status;
        this.search = search;
    }

    public User(){}

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
