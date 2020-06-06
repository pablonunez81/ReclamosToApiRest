package com.ande.reclamos;

public class Reclamo {

    private int id;
    private String numeroReclamo;
    private Integer telefono;
    private String nombreCliente;
    private int nis;
    private TipoReclamo tipoReclamo;
    private TipoAveria tipoAveria;
    //private int peligrosidad;
    private String direccion;
    //private String direccion1;
    //private String direccion2;
    //private int numeroCasa;
    //private int cno;
    //private int cro;
    private Municipio municipio;
    private Barrio barrio;
    //private int zona;
    //private int car;
    private String referencia;
    //private int operadorNro;
    //private String operadorNombre;
    private long fechaRecepcion;
    //private int estadoReclamo;
    private GeoPunto posicion;


  public TipoAveria getTipoAveria() {
        return tipoAveria;
    }

    public void setTipoAveria(TipoAveria tipoAveria) {
        this.tipoAveria = tipoAveria;
    }


    // TODO: Resolver el problema de que un reclamo no tiene posición
    public GeoPunto getPosicion() {
        return posicion;
    }

    public void setPosicion(GeoPunto posicion) {
        this.posicion = posicion;
    }

    public TipoReclamo getTipoReclamo() {
        return tipoReclamo;
    }

    public void setTipoReclamo(TipoReclamo tipoReclamo) {
        this.tipoReclamo = tipoReclamo;
    }

    public Reclamo(String numeroReclamo, int telefono, String nombreCliente, int nis,
                   String direccion, String referencia,
                   TipoReclamo tipoReclamo, TipoAveria tipoAveria,
                    Municipio municipio, Barrio barrio,
                   double longitud, double latitud) {
        this.numeroReclamo = numeroReclamo;
        this.telefono = telefono;
        this.nombreCliente = nombreCliente;
        this.nis = nis;
        this.direccion = direccion;
        this.referencia = referencia;
        this.tipoReclamo = tipoReclamo;
        this.tipoAveria = tipoAveria;
        this.municipio = municipio;
        this.barrio = barrio;
        this.posicion = new GeoPunto(longitud, latitud);
    }

    /**
     * Constructor con parámetros por defecto
     */
    public Reclamo() {
        fechaRecepcion = System.currentTimeMillis();
        posicion = new GeoPunto(0,0);
        tipoReclamo = TipoReclamo.FE;
        tipoAveria = TipoAveria.BT;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeroReclamo() {
        return numeroReclamo;
    }

    public void setNumeroReclamo(String numeroReclamo) {
        this.numeroReclamo = numeroReclamo;
    }

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public int getNis() {
        return nis;
    }

    public void setNis(int nis) {
        this.nis = nis;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public long getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(long fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public Barrio getBarrio() {
        return barrio;
    }

    public void setBarrio(Barrio barrio) {
        this.barrio = barrio;
    }

    @Override
    public String toString() {
        return "Reclamo{" +
                "id=" + id +
                ", numeroReclamo='" + numeroReclamo + '\'' +
                ", telefono=" + telefono +
                ", nombreCliente='" + nombreCliente + '\'' +
                ", nis=" + nis +
                ", tipoReclamo=" + tipoReclamo +
                ", tipoAveria=" + tipoAveria +
                ", direccion='" + direccion + '\'' +
                ", municipio=" + municipio +
                ", barrio=" + barrio +
                ", referencia='" + referencia + '\'' +
                ", fechaRecepcion=" + fechaRecepcion +
                ", posicion=" + posicion +
                '}';
    }
}

