package Model.ProduccionModel;

import Model.LotePlaneado;
import java.util.Date;

public class LoteProduccion extends LotePlaneado{

    private int noLote;
    private String descLote;
    private int cantidadOperados;
    private int scrapOperador;
    private float merma;
    private int rechazo;
    private int cantidadAdmin;
    private int scrapAdmin;
    private String tiempoMuerto;
    private String descTurno;
    private boolean activacion = true;
    private String codOperador;
    private int cantidadRechazoLiberado;
    private int scrapAjustable;
    private float barrasUtilizadas;
    private int numLote;
    private String fechaTrabajo;
    private String descEstado;
    private reloj tiempoTranscurridoR;
    private float piezasSegundoR;
    private reloj tiempoMuertoR;
    private float cantidadProducidaR;
    
    private boolean aviso1 = false;
    private boolean  aviso2 = false;
    private boolean  aviso3 = false;
        
    public LoteProduccion(){
        super();            
    }

    public boolean isAviso1() {
        return aviso1;
    }

    public void setAviso1(boolean aviso1) {
        this.aviso1 = aviso1;
    }

    public boolean isAviso2() {
        return aviso2;
    }

    public void setAviso2(boolean aviso2) {
        this.aviso2 = aviso2;
    }

    public boolean isAviso3() {
        return aviso3;
    }

    public void setAviso3(boolean aviso3) {
        this.aviso3 = aviso3;
    }

    public String getDescEstado() {
        return descEstado;
    }

    public void setDescEstado(String descEstado) {
        this.descEstado = descEstado;
    }
    
    public int getNumLote() {
        return numLote;
    }

    public void setNumLote(int numLote) {
        this.numLote = numLote;
    }

    public int getCantidadRechazoLiberado() {
        return cantidadRechazoLiberado;
    }

    public void setCantidadRechazoLiberado(int cantidadRechazoLiberado) {
        this.cantidadRechazoLiberado = cantidadRechazoLiberado;
    }

    public int getScrapAjustable() {
        return scrapAjustable;
    }

    public void setScrapAjustable(int scrapAjustable) {
        this.scrapAjustable = scrapAjustable;
    }

    public float getBarrasUtilizadas() {
        return barrasUtilizadas;
    }

    public void setBarrasUtilizadas(float barrasUtilizadas) {
        this.barrasUtilizadas = barrasUtilizadas;
    }
    
       
    public String getDescTurno() {
        return descTurno;
    }

    public void setDescTurno(String descTurno) {
        this.descTurno = descTurno;
    }

    public String getCodOperador() {
        return codOperador;
    }

    public void setCodOperador(String codOperador) {
        this.codOperador = codOperador;
    }
    
    
    

    public boolean isActivacion() {
        return activacion;
    }

    public void setActivacion(boolean activacion) {
        this.activacion = activacion;
    }
    
    

    public float getPiezasSegundoR() {
        return piezasSegundoR;
    }

    public float getCantidadProducidaR() {
        return cantidadProducidaR;
    }   

    public float calcularPiezasSegundo(int piezasTurno){
        return (float) ((((float)piezasTurno/8.0)/60.0)/60.0);
    }    
        
    LoteProduccion(int noLotePlaneado, int cantidadPlaneada, String descEstado,int ordenProduccion) {
        super(noLotePlaneado, cantidadPlaneada, descEstado,ordenProduccion);
        tiempoTranscurridoR = new reloj();
        tiempoMuertoR = new reloj();   
    }

    public void setTiempoTranscurridoR(reloj tiempoTranscurridoR) {
        this.tiempoTranscurridoR = tiempoTranscurridoR;
    }

    public void setPiezasSegundoR(float piezasSegundoR) {
        this.piezasSegundoR = piezasSegundoR;
    }

    public void setTiempoMuertoR(reloj tiempoMuertoR) {
        this.tiempoMuertoR = tiempoMuertoR;
    }

    public void setCantidadProducidaR(float cantidadProducidaR) {
        this.cantidadProducidaR = cantidadProducidaR;
    }

    public void setCantidadProducidaR(int cantidadProducida) {
        this.cantidadProducidaR = cantidadProducida;
    }

    public reloj getTiempoTranscurridoR() {
        return tiempoTranscurridoR;
    }

    public reloj getTiempoMuertoR() {
        return tiempoMuertoR;
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

    public String getFechaTrabajo() {
        return fechaTrabajo;
    }

    public void setFechaTrabajo(String fechaTrabajo) {
        this.fechaTrabajo = fechaTrabajo;
    }
    

    public int getScrapAdmin() {
        return scrapAdmin;
    }    
        
        
    public class reloj{
        private Date fechaInicio;
        private int horas;
        private int minutos;
        private int segundos;
            
        public reloj(){
            fechaInicio = new Date();
            horas = 0;
            minutos = 0;
            segundos = 0;     
        }
               
            @Override
           public String toString(){
               String h = (horas < 10)? "0"+horas:horas+"";
               String m = (minutos < 10)? "0"+minutos:minutos+"";
               String s = (segundos <10)? "0"+segundos:segundos+"";
               return String.format("%s:%s:%s",h,m,s);
           }
            
            public void avanzar(){
               fechaInicio = new Date();
                segundos++;
                if(segundos == 60){
                    segundos = 0;
                    minutos ++;
                    if(minutos == 60){
                        minutos = 0;
                        horas++;
                        if(horas == 24)
                            horas = 0;
                    }
                
                }
                
                if(activacion)
                    cantidadProducidaR+=piezasSegundoR;
                
            }
            
            public void avanzar(Date fecha){
                long segudosTIme = (fecha.getTime() - fechaInicio.getTime())/1000;
                fechaInicio = fecha;
                for(long i = 0;i<segudosTIme;i++)
                    avanzar();
            }
            
        }
}
