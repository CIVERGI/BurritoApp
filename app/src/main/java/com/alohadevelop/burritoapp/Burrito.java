package com.alohadevelop.burritoapp;

public class Burrito {

    private String nombre;
    private String descripcion;
    private String tag;
    private int cant;
    private String id;

    Burrito(){
        //Constructor default obligatorio para firebase
    }

    Burrito(String nombre, String descripcion, String tag, int cant, String id){
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tag = tag;
        this.cant = cant;
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getTag() {
        return tag;
    }

    public int getCant() {return cant;}

    public void setCant(int cant){this.cant = cant;}

    public String getId(){ return (id); }

    public void setId(String id){this.id = id;}
}

