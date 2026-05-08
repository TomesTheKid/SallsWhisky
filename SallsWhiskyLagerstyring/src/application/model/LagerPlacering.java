package application.model;

public class LagerPlacering {
    private int reol;
    private int række;
    private int hylde;
    private Lager lager;
    private Fad fad;

    public LagerPlacering(int reol, int række, int hylde, Lager lager, Fad fad) {
        this.reol = reol;
        this.række = række;
        this.hylde = hylde;
        this.lager = lager;
        this.fad = fad;
    }

    public int getReol() {
        return reol;
    }

    public int getRække() {
        return række;
    }

    public int getHylde() {
        return hylde;
    }

    public Lager getLager() {
        return lager;
    }

    public Fad getFad() {
        return fad;
    }
}
