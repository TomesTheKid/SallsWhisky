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
    //private Lokation lokation

    public Fad(int nummer, String leverandør, String land, int antalBrug, Alkoholtype tidligereAlkoholindhold, double størrelse){
        this.nummer = nummer;
        this.leverandør = leverandør;
        this.land = land;
        this.antalBrug = antalBrug;
        this.tidligereAlkoholindhold = tidligereAlkoholindhold;
        this.størrelse = størrelse;
        //this.loktaion = lokation;
        //Husk at tilføje lokation

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

    //    public Lokation getLokation(){
//        return lokation;
//    }
    //------------------------------------------------------------------------------------
    //Link til Påfyldning-Klasse
    public ArrayList<Påfyldning> getPåfyldninger() {
        return new ArrayList<>(påfyldninger);
    }

    public void addPåfyldning(Påfyldning påfyldning){
        if (!påfyldninger.contains(påfyldning)){
            påfyldninger.add(påfyldning);
            påfyldning.setFad(this);
        }
    }

    public void removePåfyldning(Påfyldning påfyldning){
        if (påfyldninger.contains(påfyldning)){
            påfyldninger.contains(påfyldning);
            påfyldning.setFad(this);
        }
    }
    //-----------------------------------
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
