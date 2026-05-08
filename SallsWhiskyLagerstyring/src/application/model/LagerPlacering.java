package application.model;

public class LagerPlacering {
    private String placeringPåLager;
    private Lager lager;
    private Fad fad;

    public LagerPlacering(String placeringPåLager, Lager lager, Fad fad) {
        this.placeringPåLager = placeringPåLager;
        this.lager = lager;
        this.fad = fad;
    }

    public String getPlaceringPåLager() {
        return placeringPåLager;
    }

    public Lager getLager() {
        return lager;
    }

    public Fad getFad() {
        return fad;
    }
}
