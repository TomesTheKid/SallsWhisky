package application.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Lager implements Serializable {
    private String lagerNavn;
    private String adresse;
    private final ArrayList<LagerPlacering> lokationer = new ArrayList<>();

    public Lager(String lagerNavn, String adresse) {
        this.lagerNavn = lagerNavn;
        this.adresse = adresse;
    }
    //------GET METODER---------
    public String getLagerNavn() {
        return lagerNavn;
    }

    public ArrayList<LagerPlacering> getLokationer() {
        return lokationer;
    }

    public String getAdresse() {
        return adresse;
    }

    //----------------------------------------------

    /**
     * Opretter en LagerPlacering og tilføjer den til dette lagers lokationer.
     * Pre: reol, række, hylde >= 0
     */
    public LagerPlacering createLagerPlacering(int reol, int række, int hylde) {
        LagerPlacering lagerPlacering = new LagerPlacering(reol, række, hylde, this);
        lokationer.add(lagerPlacering);
        return lagerPlacering;
    }

    @Override
    public String toString() {
        return lagerNavn + " " + adresse;
    }
}
