package com.ande.reclamos.model;

import com.ande.reclamos.io.MyApiAdapter;
import com.google.gson.annotations.SerializedName;

public class Tiporeclamo {

    @SerializedName("id")
    private int id;

    @SerializedName("tipo")
    private String tipo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Método que permite acceder a una imagen que representa un tipo de reclamos.
     * La obligación es de que en el servidor de imagen se guarde con el nombre igual al id del registro.
     * Ejemplo: id=1, filename: 1.png
     * @return
     */
    public String getImageTipoReclamoUrl(){
        return MyApiAdapter.getImageTipoReclamoUrl()+this.id+".png";
    }
}
