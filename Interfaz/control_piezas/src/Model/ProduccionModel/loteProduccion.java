/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.ProduccionModel;

/**
 *
 * @author cesar
 */
public class loteProduccion {
private int noLote;
        private String descLote;
        private int cantidadOperados;
        private int scrapOperador;
        private float merma;
        private String tiempoMuerto;
        private int rechazo;
        private int cantidadAdmin;
        private int scrapAdmin;

        
        public loteProduccion(){
            
        }
        
        public loteProduccion(int noLote, String descLote, int cantidadOperados, int scrapOperador, float merma, String tiempoMuerto, int rechazo, int cantidadAdmin, int scrapAdmin) {
            this.noLote = noLote;
            this.descLote = descLote;
            this.cantidadOperados = cantidadOperados;
            this.scrapOperador = scrapOperador;
            this.merma = merma;
            this.tiempoMuerto = tiempoMuerto;
            this.rechazo = rechazo;
            this.cantidadAdmin = cantidadAdmin;
            this.scrapAdmin = scrapAdmin;
        }
        

        public void setNoLote(int noLote) {
            this.noLote = noLote;
        }

        public void setDescLote(String descLote) {
            this.descLote = descLote;
        }

        public void setCantidadOperados(int cantidadOperados) {
            this.cantidadOperados = cantidadOperados;
        }

        public void setScrapOperador(int scrapOperador) {
            this.scrapOperador = scrapOperador;
        }

        public void setMerma(float merma) {
            this.merma = merma;
        }

        public void setTiempoMuerto(String tiempoMuerto) {
            this.tiempoMuerto = tiempoMuerto;
        }

        public void setRechazo(int rechazo) {
            this.rechazo = rechazo;
        }

        public void setCantidadAdmin(int cantidadAdmin) {
            this.cantidadAdmin = cantidadAdmin;
        }

        public void setScrapAdmin(int scrapAdmin) {
            this.scrapAdmin = scrapAdmin;
        }
        
        public int getNoLote() {
            return noLote;
        }

        public String getDescLote() {
            return descLote;
        }

        public int getCantidadOperados() {
            return cantidadOperados;
        }

        public int getScrapOperador() {
            return scrapOperador;
        }

        public float getMerma() {
            return merma;
        }

        public String getTiempoMuerto() {
            return tiempoMuerto;
        }

        public int getRechazo() {
            return rechazo;
        }

        public int getCantidadAdmin() {
            return cantidadAdmin;
        }

        public int getScrapAdmin() {
            return scrapAdmin;
        }    
}
