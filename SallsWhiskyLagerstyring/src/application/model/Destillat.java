package application.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Destillat implements Serializable {
    private LocalDate startDato;
    private LocalDate slutDato;
    private String maltbatch;
    private final Kornsort kornsort;
    private double mængdeVæske;
    private double alkoholprocent;
    private String rygemateriale;
    private String kommentar;
    private int newMakeNr;
    private int ID;
    private double aktuelMængdeVæske;

    /**
     * Opretter Destillat objekt
     * Pre: startDato, slutDato, maltbatch, kornsort, mængdeVæske, alkoholprocent, newMakeNr, ID != null
     */
    public Destillat(LocalDate startDato, LocalDate slutDato, String maltbatch, Kornsort kornsort,
                     double mængdeVæske, double alkoholprocent, String rygemateriale, String kommentar,
                     int newMakeNr, int ID, double aktuelMængdeVæske) {
        this.startDato = startDato;
        this.slutDato = slutDato;
        this.maltbatch = maltbatch;
        this.kornsort = kornsort;
        this.mængdeVæske = mængdeVæske;
        this.alkoholprocent = alkoholprocent;
        this.rygemateriale = rygemateriale;
        this.kommentar = kommentar;
        this.newMakeNr = newMakeNr;
        this.ID = ID;
        this.aktuelMængdeVæske = aktuelMængdeVæske;
    }

    //Alle get metoder
    public LocalDate getStartDato() {
        return startDato;
    }

    public LocalDate getSlutDato() {
        return slutDato;
    }

    public String getMaltbatch() {
        return maltbatch;
    }

    public Kornsort getKornsort() {
        return kornsort;
    }

    public double getMaengdeVaeske() {
        return mængdeVæske;
    }

    public double getAlkoholprocent() {
        return alkoholprocent;
    }

    public String getRygemateriale() {
        return rygemateriale;
    }

    public String getKommentar() {
        return kommentar;
    }

    public int getNewMakeNr() {
        return newMakeNr;
    }

    public int getID() {
        return ID;
    }

    public double getAktuelMaengdeVaeske() {
        return aktuelMængdeVæske;
    }
    //----------------------------------------------------------------
    /**
     * Sætter Destillatets slutdato til dagens dato
     */
    public void afslutDestillering(){
        this.slutDato = LocalDate.now();
    }

    /**
     * Reducere destillates aktuelMængdeVæske, hvis mængden > aktuelMængdeVæske
     * kastes en IllegalArgumentException og der bliver ikke reduceret
     * Pre: mængde != null
     */
    public void reducerAktuelMængdeVæske(double mængde){
        if(mængde < 0){
            return;
        }
        if(mængde > aktuelMængdeVæske){
            throw new IllegalArgumentException("Ikke nok destillat tilbage");
        }
        aktuelMængdeVæske -= mængde;
    }

    @Override
    public String toString() {
        return "Destillat med id: " + ID + " med: " + aktuelMængdeVæske + " L";
    }
}
