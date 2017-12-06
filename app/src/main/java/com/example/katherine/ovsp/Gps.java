package com.example.katherine.ovsp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by katherine on 5/12/17.
 */

public class Gps {
    @SerializedName("altitud")
    @Expose
    private String altitud;
    @SerializedName("fecha_de_instalaci_n")
    @Expose
    private String fechaDeInstalaciN;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("latitud")
    @Expose
    private String latitud;
    @SerializedName("longitud")
    @Expose
    private String longitud;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("ovs")
    @Expose
    private String ovs;
    @SerializedName("tipo_de_estaci_n")
    @Expose
    private String tipoDeEstaciN;
    @SerializedName("volc_n")
    @Expose
    private String volcN;

    public String getAltitud() {
        return altitud;
    }

    public void setAltitud(String altitud) {
        this.altitud = altitud;
    }

    public String getFechaDeInstalaciN() {
        return fechaDeInstalaciN;
    }

    public void setFechaDeInstalaciN(String fechaDeInstalaciN) {
        this.fechaDeInstalaciN = fechaDeInstalaciN;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getOvs() {
        return ovs;
    }

    public void setOvs(String ovs) {
        this.ovs = ovs;
    }

    public String getTipoDeEstaciN() {
        return tipoDeEstaciN;
    }

    public void setTipoDeEstaciN(String tipoDeEstaciN) {
        this.tipoDeEstaciN = tipoDeEstaciN;
    }

    public String getVolcN() {
        return volcN;
    }

    public void setVolcN(String volcN) {
        this.volcN = volcN;
    }
}
