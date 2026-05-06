package application.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Påfyldning implements Serializable {
    private LocalDate dato;
    private String ansvarlig;
    private Fad fad;
    private ArrayList<VæskeMængde> væskeMængder = new ArrayList<>();

    public Påfyldning(LocalDate dato, String ansvarlig, Fad fad){
        this.dato = dato;
        this.ansvarlig = ansvarlig;
        this.fad = fad;
        fad.optælAntalBrug();
    }

    //Get metoder
    public LocalDate getDato() {
        return dato;
    }

    public String getAnsvarlig() {
        return ansvarlig;
    }

    public ArrayList<VæskeMængde> getVæskeMængder() {
        return new ArrayList<>(væskeMængder);
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

    @Override
    public String toString() {
        return "Dato=" + dato + ", ansvarlig=" + ansvarlig + ", væsker=" + væskeMængder;
    }
}
