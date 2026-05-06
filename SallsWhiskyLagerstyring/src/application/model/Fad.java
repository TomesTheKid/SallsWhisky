package application.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Fad implements Serializable {
    private int nummer;
    private String leverandør;
    private String land;
    private int antalBrug;
    private Alkoholtype tidligereAlkoholindhold;
    private double størrelse;
    private final ArrayList<Påfyldning> påfyldninger = new ArrayList<>();
    private boolean erAktiv;
    //private Lokation lokation

    /**
     * Opretter Fad
     * Pre: nummer, leverandør, land, antalBrug, tidligereAlkoholindhold, størrelse != null
     */
    public Fad(int nummer, String leverandør, String land, int antalBrug, Alkoholtype tidligereAlkoholindhold, double størrelse){
        this.nummer = nummer;
        this.leverandør = leverandør;
        this.land = land;
        this.antalBrug = antalBrug;
        this.tidligereAlkoholindhold = tidligereAlkoholindhold;
        this.størrelse = størrelse;
        //this.loktaion = lokation;
        //Husk at tilføje lokation
        this.erAktiv = false;
    }

    //Alle get metoder
    public int getNummer() {
        return nummer;
    }

    public String getLeverandør() {
        return leverandør;
    }

    public String getLand() {
        return land;
    }

    public int getAntalBrug() {
        return antalBrug;
    }

    public Alkoholtype getTidligereAlkoholindhold() {
        return tidligereAlkoholindhold;
    }

    public double getStørrelse() { return størrelse;}

    public boolean isErAktiv() {
        return erAktiv;
    }

    //    public Lokation getLokation(){
//        return lokation;
//    }
    //------------------------------------------------------------------------------------
    //Link til Påfyldning-Klasse
    public ArrayList<Påfyldning> getPåfyldninger() {
        return new ArrayList<>(påfyldninger);
    }

    public void addPåfyldning(Påfyldning påfyldning){
        if (påfyldning != null && !påfyldninger.contains(påfyldning)){
            påfyldninger.add(påfyldning);
        }
    }

    public void removePåfyldning(Påfyldning påfyldning){
        påfyldninger.remove(påfyldning);
    }
    //-----------------------------------

    /**
     * Tæller fadet antalBrug op med 1
     */
    public void optælAntalBrug(){
        antalBrug++;
    }
    //-----------------------------

    //TODO
    //public void flytFad(Lokation lokation){
    // }


    @Override
    public String toString() {
        return "Fad{" +
                "nummer=" + nummer +
                ", tidligereAlkoholindhold='" + tidligereAlkoholindhold + '\'' +
                '}';
    }
}
