package com.ande.reclamos.io;

import com.ande.reclamos.model.Averia;
import com.ande.reclamos.model.Movil;
import com.ande.reclamos.model.Reclamo;
import com.ande.reclamos.model.ReclamosDetalle;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MyApiService {

    @GET("login/{numero_movil}")
    Call<Movil> login(@Path("numero_movil") String numero);

    @FormUrlEncoded
    @PUT("movil/{movil_id}/")
    Call<Movil> actualizarUbicacion(
            @Path("movil_id") String movilId,
            @Field("numero") String movilNumero,
            @Field("coordx") String coordx,
            @Field("coordy") String coordy);

    @GET("reclamos_movil/{numero_movil}/")
    Call<ArrayList<Reclamo>> reclamosMovil(@Path("numero_movil") String numero);

    @GET("averia/")
    Call<ArrayList<Averia>> averias();

    @GET("averias_x_reclamo/{reclamo_id}/")
    Call<ArrayList<ReclamosDetalle>> averiasXReclamo(@Path("reclamo_id") String reclamoId);
}
