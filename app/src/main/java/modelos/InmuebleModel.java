package modelos;

import java.io.Serializable;

public class InmuebleModel implements Serializable {
    private int idInmueble,ambientes,superficie,idPropietario;
    private String direccion,uso,tipo,imagen;
    private double latitud,valor,longitud;
    private boolean disponible;

    private PropietarioModel propietario;


    public PropietarioModel getPropietario() {
        return propietario;
    }

    public void setPropietario(PropietarioModel propietario) {
        this.propietario = propietario;
    }

    public int getIdInmueble() {
        return idInmueble;
    }

    public void setIdInmueble(int idInmueble) {
        this.idInmueble = idInmueble;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getUso() {
        return uso;
    }

    public void setUso(String uso) {
        this.uso = uso;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getAmbientes() {
        return ambientes;
    }

    public void setAmbientes(int ambientes) {
        this.ambientes = ambientes;
    }

    public int getSuperficie() {
        return superficie;
    }

    public void setSuperficie(int superficie) {
        this.superficie = superficie;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public int getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(int idPropietario) {
        this.idPropietario = idPropietario;
    }

    @Override
    public String toString() {
        return "InmuebleModel{" +
                "idInmueble=" + idInmueble +
                ", ambientes=" + ambientes +
                ", superficie=" + superficie +
                ", idPropietario=" + idPropietario +
                ", direccion='" + direccion + '\'' +
                ", uso='" + uso + '\'' +
                ", tipo='" + tipo + '\'' +
                ", imagen='" + imagen + '\'' +
                ", latitud=" + latitud +
                ", valor=" + valor +
                ", longitud=" + longitud +
                ", disponible=" + disponible +
                ", propietario=" + propietario +
                '}';
    }
}
