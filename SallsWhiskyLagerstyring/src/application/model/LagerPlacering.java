package application.model;

import java.io.Serializable;

public class LagerPlacering implements Serializable {
    private int reol;
    private int række;
    private int hylde;
    private Lager lager;
    private Fad fad;

    /**
     * Pre: lager != null
     */
    public LagerPlacering (int reol, int række, int hylde, Lager lager) {
        this.reol = reol;
        this.række = række;
        this.hylde = hylde;
        this.lager = lager;
    }

    //---------GET METODER-------------
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
    //-------------------------------------------

    /**
     * Tildeler et fad til denne placering og opdaterer tovejslinket.
     * Fjerner den gamle relation hvis der allerede sidder et fad.
     */
    public void setFad(Fad fad) {
        if (this.fad != fad) {
            // Fjern gammel relation
            if (this.fad != null) {
                this.fad.setLagerPlacering(null);
            }
            this.fad = fad;
            // Sæt ny relation
            if (fad != null && fad.getLagerPlacering() != this) {
                fad.setLagerPlacering(this);
            }
        }
    }

    //--------------------------------------------
    public boolean erOptaget() {
        return fad != null;
    }

    @Override
    public String toString() {
        return "reol " + reol + ", række " + række + ", hylde " + hylde;
    }
}
