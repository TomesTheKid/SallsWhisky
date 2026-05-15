package application.model;

import java.io.Serializable;

public class Fadbidrag implements Serializable {
    private double mængdeLiter;
    private Påfyldning påfyldning;
    private WhiskyProdukt whiskyProdukt;

    /**
     * Pre: mængdeLiter != null
     */
    public Fadbidrag(double mængdeLiter) {
        this.mængdeLiter = mængdeLiter;
    }

    //----------GET METODER-----------------
    public double getMængdeLiter() {
        return mængdeLiter;
    }

    //-----------------------------------------
    //Link til Påfyldning
    public Påfyldning getPåfyldning() {
        return påfyldning;
    }

    public void setPåfyldning(Påfyldning påfyldning){
        if (this.påfyldning != påfyldning){
            Påfyldning oldPåfyldning = this.påfyldning;
            if (oldPåfyldning != null){
                oldPåfyldning.removeFadbidragFraP(this);
            }
            this.påfyldning = påfyldning;
            if (påfyldning != null){
                påfyldning.addFadbidragTilP(this);
            }
        }
    }

    //-------------------------------------------------------
    //Link til WhiskyProdukt
    public WhiskyProdukt getWhiskyProdukt() {
        return whiskyProdukt;
    }

    public void setWhiskyProdukt(WhiskyProdukt whiskyProdukt){
        if (this.whiskyProdukt != whiskyProdukt){
            WhiskyProdukt oldWhiskyProdukt = this.whiskyProdukt;
            if (oldWhiskyProdukt != null){
                oldWhiskyProdukt.removeFadbidragFraWP(this);
            }
            this.whiskyProdukt = whiskyProdukt;
            if (whiskyProdukt != null){
                whiskyProdukt.addFadbidragTilWP(this);
            }
        }
    }

    //---------------------------------------------------

    public void appendReportBlock(StringBuilder sb) {
        sb.append("\nFadbidrag: ").append(String.format("%.1f", mængdeLiter)).append(" L\n");
        if (påfyldning != null) {
            påfyldning.appendReportBlock(sb);
        }
    }

    @Override
    public String toString() {
        return "Fadbidrag med: " + mængdeLiter + 'L';
    }
}
