/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibs.turnos.dao;

/**
 *
 * @author marco
 */
public class Servicio {

    private String id;
    private String DescripcionServicio;
    private String Estatus;
    private String UserLastUpdate;
    private String FechaLastUpdate;
    private String Color;
    private String chk;

    public String getChk() {
        return chk;
    }

    public void setChk(String chk) {
        this.chk = chk;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String Color) {
        this.Color = Color;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcionServicio() {
        return DescripcionServicio;
    }

    public void setDescripcionServicio(String DescripcionServicio) {
        this.DescripcionServicio = DescripcionServicio;
    }

    public String getEstatus() {
        return Estatus;
    }

    public void setEstatus(String Estatus) {
        this.Estatus = Estatus;
    }

    public String getUserLastUpdate() {
        return UserLastUpdate;
    }

    public void setUserLastUpdate(String UserLastUpdate) {
        this.UserLastUpdate = UserLastUpdate;
    }

    public String getFechaLastUpdate() {
        return FechaLastUpdate;
    }

    public void setFechaLastUpdate(String FechaLastUpdate) {
        this.FechaLastUpdate = FechaLastUpdate;
    }

}
