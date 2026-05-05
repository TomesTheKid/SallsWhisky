package application.model;

import java.io.Serializable;

public class VæskeMængde implements Serializable {
    private double mængde;
    private Destillat destillat;

    public VæskeMængde(double mængde, Destillat destillat) {
        this.mængde = mængde;
        this.destillat = destillat;
    }

    //Get metoder
    public double getMængde() {
        return mængde;
    }
    //----------------------------------
    //Linkt til Destillat-Klasse
    public Destillat getDestillat() {
        return destillat;
    }

    public void setDestillat(Destillat destillat){
        if (this.destillat != destillat){
            this.destillat = destillat;
        }
    }
    //---------------------------------------------
}
