package com.ande.reclamos.model;

//    numero = models.CharField(max_length=50)
//    coordx = models.IntegerField(null=True, blank=True)
//    coordy = models.IntegerField(null=True, blank=True)

public class Movil {

    private int id;
    private String numero;
    private Float coordx;
    private Float coordy;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Float getCoordx() {
        return coordx;
    }

    public void setCoordx(Float coordx) {
        this.coordx = coordx;
    }

    public Float getCoordy() {
        return coordy;
    }

    public void setCoordy(Float coordy) {
        this.coordy = coordy;
    }
}
