package application.model;

public class Fadbidrag {
    private double mængdeLiter;
    private Påfyldning påfyldning;
    private WhiskyProdukt whiskyProdukt;

    /**
     * Pre: mængdeLiter != null
     */
    public Fadbidrag(double mængdeLiter) {
        this.mængdeLiter = mængdeLiter;
    }

    //-------------------------------------------------
    //Get metoder
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

}
