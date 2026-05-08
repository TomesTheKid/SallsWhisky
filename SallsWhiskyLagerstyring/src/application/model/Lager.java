package application.model;

import java.util.ArrayList;

public class Lager {
    private String lagerNavn;
    private String adresse;
    private final ArrayList<LagerPlacering> lokationer = new ArrayList<>();

    public Lager(String lagerNavn, String adresse) {
        this.lagerNavn = lagerNavn;
        this.adresse = adresse;
    }

    public String getLagerNavn() {
        return lagerNavn;
    }

    public ArrayList<LagerPlacering> getLokationer() {
        return lokationer;
    }

    public String getAdresse() {
        return adresse;
    }

    public LagerPlacering createLagerPlacering(String placeringPaaLageret, Lager lager, Fad fad) {
        LagerPlacering lagerPlacering = new LagerPlacering(placeringPaaLageret, lager, fad);
        lokationer.add(lagerPlacering);
        return lagerPlacering;
    }
}
