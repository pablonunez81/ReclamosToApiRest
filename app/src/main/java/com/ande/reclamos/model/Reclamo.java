package com.ande.reclamos.model;


import com.ande.reclamos.GeoPunto;
import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class Reclamo {

    @SerializedName("id")
    private int id;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("direccion")
    private String direccion;

    @SerializedName("telefono")
    private int telefono;

    @SerializedName("nis")
    private String nis;

    @SerializedName("coordx")
    private double coordx;

    @SerializedName("coordy")
    private double coordy;

    @SerializedName("fecha_recepcion")
    private Timestamp fecha_recepcion;

    @SerializedName("fecha_comunicacion")
    private Timestamp fecha_comunicacion;

    @SerializedName("fecha_ubicacion")
    private Timestamp fecha_ubicacion;

    @SerializedName("fecha_restitucion")
    private Timestamp fecha_restitucion;

    @SerializedName("estado")
    private boolean estado;

    @SerializedName("suministroid")
    private Suministro suministroid;

    @SerializedName("ciudadid")
    private Ciudad ciudadid;

    @SerializedName("tiporeclamoid")
    private Tiporeclamo tiporeclamoid;

    @SerializedName("barrio")
    private Barrio barrio;

    @SerializedName("tipoaveria")
    private Tipoaveria tipoaveria;

    @SerializedName("movil")
    private Movil  movil;

    @SerializedName("personal")
    private Personal personal;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public Timestamp getFecha_recepcion() {
        return fecha_recepcion;
    }

    public void setFecha_recepcion(String fecha_recepcion) {
        this.fecha_recepcion = Timestamp.valueOf(fecha_recepcion);
    }

    public Timestamp getFecha_comunicacion() {
        return fecha_comunicacion;
    }

    public void setFecha_comunicacion(String fecha_comunicacion) {
        this.fecha_comunicacion = Timestamp.valueOf(fecha_comunicacion);
    }

    public Timestamp getFecha_ubicacion() {
        return fecha_ubicacion;
    }

    public void setFecha_ubicacion(String fecha_ubicacion) {
        this.fecha_ubicacion = Timestamp.valueOf(fecha_ubicacion);
    }

    public Timestamp getFecha_restitucion() {
        return fecha_restitucion;
    }

    public void setFecha_restitucion(String fecha_restitucion) {
        this.fecha_restitucion = Timestamp.valueOf(fecha_restitucion);
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Suministro getSuministroid() {
        return suministroid;
    }

    public void setSuministroid(Suministro suministroid) {
        this.suministroid = suministroid;
    }

    public Ciudad getCiudadid() {
        return ciudadid;
    }

    public void setCiudadid(Ciudad ciudadid) {
        this.ciudadid = ciudadid;
    }

    public Tiporeclamo getTiporeclamoid() {
        return tiporeclamoid;
    }

    public void setTiporeclamoid(Tiporeclamo tiporeclamoid) {
        this.tiporeclamoid = tiporeclamoid;
    }

    public Barrio getBarrio() {
        return barrio;
    }

    public void setBarrio(Barrio barrio) {
        this.barrio = barrio;
    }

    public Tipoaveria getTipoaveria() {
        return tipoaveria;
    }

    public void setTipoaveria(Tipoaveria tipoaveria) {
        this.tipoaveria = tipoaveria;
    }

    public Movil getMovil() {
        return movil;
    }

    public void setMovil(Movil movil) {
        this.movil = movil;
    }

    public Personal getPersonal() {
        return personal;
    }

    public void setPersonal(Personal personal) {
        this.personal = personal;
    }

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public double getCoordx() {
        return coordx;
    }

    public void setCoordx(double coordx) {
        this.coordx = coordx;
    }

    public double getCoordy() {
        return coordy;
    }

    public void setCoordy(double coordy) {
        this.coordy = coordy;
    }

    /**
     * Retorna las coordenadas georefenciales en Latitud (Y) - Longitud (X)
     * @return
     */
    public GeoPunto getPosicion() {
        if(!Double.isNaN(this.getCoordx()) &&
           !Double.isNaN(this.getCoordy())) {
            GeoPunto p = new GeoPunto(this.getCoordy(), this.getCoordx());
            return p;
        }else {
            return null;
        }
    }
}
