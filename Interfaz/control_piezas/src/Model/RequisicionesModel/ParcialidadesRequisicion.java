/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.RequisicionesModel;

/**
 *
 * @author cesar
 */
public class ParcialidadesRequisicion extends MaterialesRequisicion{
        
        
        
        private final int parcialidad;
        private final int cantidad;
        private final String fechaSolicitud;
        private final String fechaEntrega;
        private final int noOrdenProduccion;

        public ParcialidadesRequisicion(int parcialidad, int cantidad, String fechaSolicitud, String fechaEntrega, int noOrdenProduccion) {
            this.parcialidad = parcialidad;
            this.cantidad = cantidad;
            this.fechaSolicitud = fechaSolicitud;
            this.fechaEntrega = fechaEntrega;
            this.noOrdenProduccion = noOrdenProduccion;
        }

        public int getParcialidad() {
            return parcialidad;
        }

        public int getCantidad() {
            return cantidad;
        }

        public String getFechaSolicitud() {
            return fechaSolicitud;
        }

        public String getFechaEntrega() {
            return fechaEntrega;
        }

        public int getNoOrdenProduccion() {
            return noOrdenProduccion;
        }
        
        
    }