package com.ande.reclamos.io;

import com.ande.reclamos.io.response.ReclamosDetalleResponse;
import com.ande.reclamos.model.Averia;
import com.ande.reclamos.model.Movil;
import com.ande.reclamos.model.Reclamo;
import com.ande.reclamos.model.ReclamosDetalle;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
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

    @HTTP(method = "DELETE", path = "reclamos_detalle/{pk}/", hasBody = true)
    Call<Void> deleteReclamoDetalle(@Path("pk") String pk);

    @FormUrlEncoded
    @POST("reclamos_detalle/")
    Call<ReclamosDetalleResponse> addReclamoDetalle(
            @Field("observacion") String observacion,
            @Field("averia") int averia_id,
            @Field("reclamo") int reclamo_id
    );
}
