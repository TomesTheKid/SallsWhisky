package application.model;

public class Fadbidrag {
    private double mængdeLiter;

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
}
