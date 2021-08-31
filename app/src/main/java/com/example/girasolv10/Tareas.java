package com.example.girasolv10;

public class Tareas {
    private int Id, Imagen;
    private String nombTarea, potencia, tiempo, interaccion, repeticion;

    public Tareas(int id, int imagen, String nombTarea, String potencia, String tiempo, String interaccion, String repeticion) {
        Id = id;
        Imagen = imagen;
        this.nombTarea = nombTarea;
        this.potencia = potencia;
        this.tiempo = tiempo;
        this.interaccion = interaccion;
        this.repeticion = repeticion;
    }

    public Tareas() {

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

    public String getRepeticion() {
        return repeticion;
    }

    public void setRepeticion(String repeticion) {
        this.repeticion = repeticion;
    }
}
