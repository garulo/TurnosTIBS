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
public class Turno {

    private String idTurno;
    private String intTurno;
    private String intVentanilla;
    private String intServicio;
    private String strCliente;
    private String strEstatusTurno;
    private String strTipoTurno;
    private String dtmFechaEspera;
    private String dtmFechaAtencion;
    private String intOficinadeServicios;
    private String dtmFechaTermino;
    private String enviado;
    private String dt_enviado;
    private String num_atenciones;

    public String getEnviado() {
        return enviado;
    }

    public void setEnviado(String enviado) {
        this.enviado = enviado;
    }

    public String getDt_enviado() {
        return dt_enviado;
    }

    public void setDt_enviado(String dt_enviado) {
        this.dt_enviado = dt_enviado;
    }

    public String getNum_atenciones() {
        return num_atenciones;
    }

    public void setNum_atenciones(String num_atenciones) {
        this.num_atenciones = num_atenciones;
    }

    public String getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(String idTurno) {
        this.idTurno = idTurno;
    }

    public String getIntTurno() {
        return intTurno;
    }

    public void setIntTurno(String intTurno) {
        this.intTurno = intTurno;
    }

    public String getIntVentanilla() {
        return intVentanilla;
    }

    public void setIntVentanilla(String intVentanilla) {
        this.intVentanilla = intVentanilla;
    }

    public String getIntServicio() {
        return intServicio;
    }

    public void setIntServicio(String intServicio) {
        this.intServicio = intServicio;
    }

    public String getStrCliente() {
        return strCliente;
    }

    public void setStrCliente(String strCliente) {
        this.strCliente = strCliente;
    }

    public String getStrEstatusTurno() {
        return strEstatusTurno;
    }

    public void setStrEstatusTurno(String strEstatusTurno) {
        this.strEstatusTurno = strEstatusTurno;
    }

    public String getStrTipoTurno() {
        return strTipoTurno;
    }

    public void setStrTipoTurno(String strTipoTurno) {
        this.strTipoTurno = strTipoTurno;
    }

    public String getDtmFechaEspera() {
        return dtmFechaEspera;
    }

    public void setDtmFechaEspera(String dtmFechaEspera) {
        this.dtmFechaEspera = dtmFechaEspera;
    }

    public String getDtmFechaAtencion() {
        return dtmFechaAtencion;
    }

    public void setDtmFechaAtencion(String dtmFechaAtencion) {
        this.dtmFechaAtencion = dtmFechaAtencion;
    }

    public String getIntOficinadeServicios() {
        return intOficinadeServicios;
    }

    public void setIntOficinadeServicios(String intOficinadeServicios) {
        this.intOficinadeServicios = intOficinadeServicios;
    }

    public String getDtmFechaTermino() {
        return dtmFechaTermino;
    }

    public void setDtmFechaTermino(String dtmFechaTermino) {
        this.dtmFechaTermino = dtmFechaTermino;
    }

}
