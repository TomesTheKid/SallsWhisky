package application.model;

import java.time.LocalDate;

public class Påfyldning {
    private LocalDate dato;
    private String ansvarlig;
    private Fad fad;
    private int fillNr;

    public Påfyldning(LocalDate dato, String ansvarlig, Fad fad, int fillNr){
        this.dato = dato;
        this.ansvarlig = ansvarlig;
        this.fad = fad;
        this.fillNr = fillNr;
    }

    //Get metoder
    public LocalDate getDato() {
        return dato;
    }

    public String getAnsvarlig() {
        return ansvarlig;
    }

    public int getFillNr() {
        return fillNr;
    }
    //-------------------------------------------------
    //Link til Fad-Klasse
    public Fad getFad() {
        return fad;
    }

    public void setFad(Fad fad){
        if (this.fad != fad){
            Fad oldFad = this.fad;
            if (oldFad != null){
                oldFad.removePåfyldning(this);
            }
            this.fad = fad;
            if (fad != null){
                fad.addPåfyldning(this);
            }
        }
    }

    //-----------------------------------------------

    //TODO
//    public væskeMængde createVæskeMængde(){
//        return væskeMængde;
//    }

    //TODO
//    public double getSamletPåfyldtMængde(){
//        return 0;
//    }
}
