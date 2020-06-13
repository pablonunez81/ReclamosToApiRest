package com.ande.reclamos.io;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApiAdapter {

    private static MyApiService API_SERVICE;
    private static String baseUrl, imageTipoReclamoUrl, apiRestUrl;

    public static MyApiService getApiService() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add logging interceptor
        httpClient.addInterceptor(logging);

        baseUrl = "http://192.168.0.100";
        //baseUrl = "http://10.60.14.71";
        apiRestUrl = baseUrl + ":8000/rest/";
        imageTipoReclamoUrl = baseUrl + "/images/tipo_reclamo/";

        if (API_SERVICE == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(apiRestUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build()) // <-- using the log level
                    .build();
            API_SERVICE = retrofit.create(MyApiService.class);
        }

        return API_SERVICE;
    }

    public static String getBaseUrl() {
        return baseUrl;
    }

    /**
     * Método que permite acceder a la Url de imagenes del tipo reclamos
     * @return
     */
    public static String getImageTipoReclamoUrl() {
        return imageTipoReclamoUrl;
    }

    public static String getApiRestUrl() {
        return apiRestUrl;
    }
}
