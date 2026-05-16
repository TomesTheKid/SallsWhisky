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
    private LagerPlacering lagerPlacering;

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
        this.erAktiv = false;
    }

    //----------GET METODER---------------
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

    public LagerPlacering getLagerPlacering(){
        return lagerPlacering;
    }
    //-------------------------------------------------------

    /**
     * Sætter tovejslinket til LagerPlacering.
     * Kaldes normalt via LagerPlacering.setFad().
     */
    public void setLagerPlacering(LagerPlacering lagerPlacering) {
        if (this.lagerPlacering != lagerPlacering) {
            LagerPlacering gammel = this.lagerPlacering;
            this.lagerPlacering = lagerPlacering;
            if (gammel != null && gammel.getFad() == this) {
                gammel.setFad(null);
            }
            if (lagerPlacering != null && lagerPlacering.getFad() != this) {
                lagerPlacering.setFad(this);
            }
        }
    }

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

    public void appendReportLine(StringBuilder sb) {
        sb.append("  Fad nr:          ").append(nummer).append("\n");
        sb.append("  Fad størrelse:   ").append(String.format("%.1f", størrelse)).append(" L\n");
        sb.append("  Fad leverandør:  ").append(leverandør).append("\n");
        sb.append("  Fad land:        ").append(land).append("\n");
        sb.append("  Tidl. alkohol:   ").append(tidligereAlkoholindhold).append("\n");
    }


    @Override
    public String toString() {
        return "Fad " + nummer + " " + tidligereAlkoholindhold +" størrelse: "+ størrelse;
    }
}
