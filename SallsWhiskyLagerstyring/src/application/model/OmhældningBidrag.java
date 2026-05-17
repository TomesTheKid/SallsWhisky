package application.model;

import java.io.Serializable;

public class OmhældningBidrag implements Serializable {
    private double mængde;
    private Påfyldning kilde;

    public OmhældningBidrag(double mængde, Påfyldning kilde) {
        this.mængde = mængde;
        this.kilde = kilde;
    }

    public double getMængde() { return mængde; }
    public Påfyldning getKilde() { return kilde; }
}