package application.model;

import java.io.Serializable;

public class VæskeMængde implements Serializable {
    private double mængde;
    private Destillat destillat;

    /**
     * Opretter VæskeMængde
     * Pre: mængde, destillat != null
     */
    public VæskeMængde(double mængde, Destillat destillat) {
        this.mængde = mængde;
        this.destillat = destillat;
    }

    //--------------GET METODER----------------
    public double getMængde() {
        return mængde;
    }

    public void reducerMængde(double reduktion) {
        if (reduktion > mængde) {
            throw new IllegalArgumentException("Ikke nok væske tilbage");
        }
        mængde -= reduktion;
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

    public void appendReportLine(StringBuilder sb) {
        sb.append("    - ").append(String.format("%.1f", mængde)).append(" L");
        if (destillat != null) {
            sb.append(destillat.toReportLine());
        }
        sb.append("\n");
    }

    @Override
    public String toString() {
        return String.format("%.1f L af %s", mængde, destillat);
    }
}
