package com.example.katherine.ovsp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by katherine on 5/12/17.
 */

public interface SitiosService {
    @GET("krh6-ay3a.json")
    Call<List<Acelerografo>> obtenerListadeSitios();

    @GET("efg9-8jrp.json")
    Call<List<Gps>> obtenerListadeEquipos();

}
