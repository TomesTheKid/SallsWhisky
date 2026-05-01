package application.model;

import java.io.Serializable;

public class Fad implements Serializable {
    private int nummer;
    private String leverandør;
    private String land;
    private int antalBrug;
    private String tidligereAlkoholindhold;
    private double størrelse;
    //private Lokation lokation

    public Fad(int nummer, String leverandør, String land, int antalBrug, String tidligereAlkoholindhold, double størrelse){
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

    public String getTidligereAlkoholindhold() {
        return tidligereAlkoholindhold;
    }

//    public Lokation getLokation(){
//        return lokation;
//    }
    //------------------------------------------------------------------------------------

    //TODO
    //public void flytFad(Lokation lokation){
    // }
}
