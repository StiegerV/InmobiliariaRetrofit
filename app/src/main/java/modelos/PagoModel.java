package modelos;

public class PagoModel {

    private int numeroPago,contratoId,idPago;
    private String fechaPago,detalle;
    private double importe;

    private boolean estado;


    public int getIdPago() { return idPago; }
    public void setIdPago(int idPago) { this.idPago = idPago; }

    public int getNumeroPago() { return numeroPago; }
    public void setNumeroPago(int numeroPago) { this.numeroPago = numeroPago; }

    public String getFechaPago() { return fechaPago; }
    public void setFechaPago(String fechaPago) { this.fechaPago = fechaPago; }

    public double getImporte() { return importe; }
    public void setImporte(double importe) { this.importe = importe; }

    public int getContratoId() { return contratoId; }
    public void setContratoId(int contratoId) { this.contratoId = contratoId; }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
