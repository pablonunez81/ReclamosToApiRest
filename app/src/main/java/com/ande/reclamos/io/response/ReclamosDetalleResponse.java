package com.ande.reclamos.io.response;

import com.google.gson.annotations.SerializedName;

public class ReclamosDetalleResponse {

    @SerializedName("id")
    private int id;

    @SerializedName("observacion")
    private String observacion;

    @SerializedName("averia")
    private int averia;

    @SerializedName("reclamo")
    private int reclamo;
}
