package com.example.girasolv10;

import android.graphics.drawable.Drawable;

public class DatosTareas {
    private int Id, Imagen;
    private String nroT, nombTarea, potencia, tiempo, interaccion;

    public DatosTareas(int id, int imagen, String nroT, String nombTarea, String potencia, String tiempo, String interaccion) {
        Id = id;
        Imagen = imagen;
        this.nroT = nroT;
        this.nombTarea = nombTarea;
        this.potencia = potencia;
        this.tiempo = tiempo;
        this.interaccion = interaccion;
    }

    public DatosTareas() {

    }


    public String getNroT() {
        return nroT;
    }

    public void setNroT(String nroT) {
        this.nroT = nroT;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getImagen() {
        return Imagen;
    }

    public void setImagen(int imagen) {
        Imagen = imagen;
    }

    public String getNombTarea() {
        return nombTarea;
    }

    public void setNombTarea(String nombTarea) {
        this.nombTarea = nombTarea;
    }

    public String getPotencia() {
        return potencia;
    }

    public void setPotencia(String potencia) {
        this.potencia = potencia;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public String getInteraccion() {
        return interaccion;
    }

    public void setInteraccion(String interaccion) {
        this.interaccion = interaccion;
    }
}
