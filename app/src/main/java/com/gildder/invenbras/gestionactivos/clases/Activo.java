package com.gildder.invenbras.gestionactivos.clases;

import java.io.Serializable;

/**
 * Created by gildder on 15/10/2015.
 */
public class Activo implements Serializable{
    private static final long serialVersionUID = 103458757245584525L;

    private int id;
    private String descripcion;
    private String marca;
    private String modelo;
    private String serie;
    private String estado;
    private String color;
    private float alto;
    private float ancho;
    private float profundidad;
    private String contenido;
    private float peso;
    private String nro;
    private String fechaMantenimiento;
    private String unidad;
    private int cantidad;
    private String material;
    private String codigoTIC;   //codigo TIC
    private String codigoPAT;   //codigo patrimnio
    private String codigoAF;    //codigo Activo Fijo
    private String codigoGER;    //codigo Grencia
    private String otroCodigo;
    private String imagen;
    private String observacion;
    private int idTipo;
    private int idEmpleado;
    private int idUbicacion;
    private int idInventario;

    /* Contructores */
    public Activo() {
        this.descripcion = "ninguno";
        this.marca = "ninguno";
        this.modelo = "ninguno";
        this.serie = "ninguno";
        this.estado = "S";
        this.color = "ninguno";
        this.alto = 0;
        this.ancho = 0;
        this.profundidad = 0;
        this.contenido = "ninguno";
        this.peso = 0;
        this.nro = "ninguno";
        this.fechaMantenimiento = "ninguno";
        this.unidad = "ninguno";
        this.cantidad = 1;
        this.material = "ninguno";
        this.codigoTIC = "ninguno";
        this.codigoPAT = "ninguno";
        this.codigoAF = "ninguno";
        this.codigoGER = "ninguno";
        this.otroCodigo = "ninguno";
        this.imagen = "ninguno";
        this.observacion = "ninguno";
        this.idTipo = 0;
        this.idEmpleado = 0;
        this.idUbicacion = 0;
        this.idInventario = 0;
    }




    public Activo(int id, String descripcion, String marca, String modelo, String serie, String estado, String color, float alto,
                  float ancho, float profundidad, String contenido, float peso, String nro, String fechaMantenimiento, String unidad, int cantidad,
                  String material, String codigoTIC, String codigoPAT, String codigoAF, String codigoGER, String otroCodigo,
                  String imagen, String observacion, int idTipo, int idEmpleado, int idUbicacion, int idInventario) {
        this.id = id;
        this.descripcion = descripcion;
        this.marca = marca;
        this.modelo = modelo;
        this.serie = serie;
        this.estado = estado;
        this.color = color;
        this.alto = alto;
        this.ancho = ancho;
        this.profundidad = profundidad;
        this.contenido = contenido;
        this.peso = peso;
        this.nro = nro;
        this.fechaMantenimiento = fechaMantenimiento;
        this.unidad = unidad;
        this.cantidad = cantidad;
        this.material = material;
        this.codigoTIC = codigoTIC;
        this.codigoPAT = codigoPAT;
        this.codigoAF = codigoAF;
        this.codigoGER = codigoGER;
        this.otroCodigo = otroCodigo;
        this.imagen = imagen;
        this.observacion = observacion;
        this.idTipo = idTipo;
        this.idEmpleado = idEmpleado;
        this.idUbicacion = idUbicacion;
        this.idInventario = idInventario;
    }

    public Activo(Activo activo){
        this.id = activo.id;
        this.descripcion = activo.descripcion;
        this.marca = activo.marca;
        this.modelo = activo.modelo;
        this.serie = activo.serie;
        this.estado = activo.estado;
        this.color = activo.color;
        this.alto = activo.alto;
        this.ancho = activo.ancho;
        this.profundidad = activo.profundidad;
        this.contenido = activo.contenido;
        this.peso = activo.peso;
        this.nro = activo.nro;
        this.fechaMantenimiento = activo.fechaMantenimiento;
        this.unidad = activo.unidad;
        this.cantidad = activo.cantidad;
        this.material = activo.material;
        this.codigoTIC = activo.codigoTIC;
        this.codigoPAT = activo.codigoPAT;
        this.codigoAF = activo.codigoAF;
        this.codigoGER = activo.codigoGER;
        this.otroCodigo = activo.otroCodigo;
        this.imagen = activo.observacion;
        this.observacion = activo.imagen;
        this.idTipo = activo.idTipo;
        this.idEmpleado = activo.idEmpleado;
        this.idUbicacion = activo.idUbicacion;
        this.idInventario = activo.idInventario;

    }

    /*  Getter y Setter */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public float getAlto() {
        return alto;
    }

    public void setAlto(float alto) {
        this.alto = alto;
    }

    public float getAncho() {
        return ancho;
    }

    public void setAncho(float ancho) {
        this.ancho = ancho;
    }

    public float getProfundidad() {
        return profundidad;
    }

    public void setProfundidad(float profundidad) {
        this.profundidad = profundidad;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public String getNro() {
        return nro;
    }

    public void setNro(String nro) {
        this.nro = nro;
    }

    public String getFechaMantenimiento() {
        return fechaMantenimiento;
    }

    public void setFechaMantenimiento(String fechaMantenimiento) {
        this.fechaMantenimiento = fechaMantenimiento;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getCodigoTIC() {
        return codigoTIC;
    }

    public void setCodigoTIC(String codigoTIC) {
        this.codigoTIC = codigoTIC;
    }

    public String getCodigoPAT() {
        return codigoPAT;
    }

    public void setCodigoPAT(String codigoPAT) {
        this.codigoPAT = codigoPAT;
    }

    public String getCodigoAF() {
        return codigoAF;
    }

    public void setCodigoAF(String codigoAF) {
        this.codigoAF = codigoAF;
    }

    public String getCodigoGER() {
        return codigoGER;
    }

    public void setCodigoGER(String codigoGER) {
        this.codigoGER = codigoGER;
    }

    public String getOtroCodigo() {
        return otroCodigo;
    }

    public void setOtroCodigo(String otroCodigo) {
        this.otroCodigo = otroCodigo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(int idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public int getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
    }
}
