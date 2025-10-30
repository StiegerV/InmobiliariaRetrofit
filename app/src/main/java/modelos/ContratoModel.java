package modelos;


public class ContratoModel {
        private int idContrato;
        private String fechaInicio,fechaFinalizacion;
        private double montoAlquiler;
        private boolean estado;
        private InquilinoModel inquilino;
        private InmuebleModel inmueble;

        public int getIdContrato() {
            return idContrato;
        }

        public void setIdContrato(int idContrato) {
            this.idContrato = idContrato;
        }

        public String getFechaInicio() {
            return fechaInicio;
        }

        public void setFechaInicio(String fechaInicio) {
            this.fechaInicio = fechaInicio;
        }

        public String getFechaFinalizacion() {
            return fechaFinalizacion;
        }

        public void setFechaFinalizacion(String fechaFinalizacion) {
            this.fechaFinalizacion = fechaFinalizacion;
        }

        public double getMontoAlquiler() {
            return montoAlquiler;
        }

        public void setMontoAlquiler(double montoAlquiler) {
            this.montoAlquiler = montoAlquiler;
        }

        public boolean isEstado() {
            return estado;
        }

        public void setEstado(boolean estado) {
            this.estado = estado;
        }


        public InquilinoModel getInquilino() {
            return inquilino;
        }

        public void setInquilino(InquilinoModel inquilino) {
            this.inquilino = inquilino;
        }

        public InmuebleModel getInmueble() {
            return inmueble;
        }

        public void setInmueble(InmuebleModel inmueble) {
            this.inmueble = inmueble;
        }

    @Override
    public String toString() {
        return "ContratoModel{" +
                "idContrato=" + idContrato +
                ", fechaInicio='" + fechaInicio + '\'' +
                ", fechaFinalizacion='" + fechaFinalizacion + '\'' +
                ", montoAlquiler=" + montoAlquiler +
                ", estado=" + estado +
                ", inquilino=" + inquilino +
                ", inmueble=" + inmueble +
                '}';
    }
}

