package application.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Påfyldning implements Serializable {
    private LocalDate dato;
    private String ansvarlig;
    private Fad fad;
    private int fillNr;
    private ArrayList<VæskeMængde> væskeMængder = new ArrayList<>();

    public Påfyldning(LocalDate dato, String ansvarlig, Fad fad,
                      int fillNr, ArrayList<VæskeMængde> væskeMængder){
        this.dato = dato;
        this.ansvarlig = ansvarlig;
        this.fad = fad;
        this.fillNr = fillNr;
        this.væskeMængder = væskeMængder;
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

    public VæskeMængde createVæskeMængde(double mængde, Destillat destillat){
        VæskeMængde væskeMængde = new VæskeMængde(mængde, destillat);
        væskeMængder.add(væskeMængde);
        return væskeMængde;
    }

    public double getSamletPåfyldtMængde(){
        double sum = 0;
        for (VæskeMængde v : væskeMængder){
            sum += v.getMængde();
        }
        return sum;
    }
}
