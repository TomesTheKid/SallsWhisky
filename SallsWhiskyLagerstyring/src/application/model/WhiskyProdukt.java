package application.model;

public class WhiskyProdukt {
    private int batchID;
    private double vandMængde;
    private String kvalitetsstempel;
    private double alkoholprocent;
    private double størrelse;
    private String vandkilde;
    private String beskrivelse;
    private int nummer;
    private int årstal;

    /**
     * Opretter WhiskyProdukt
     * Pre: batchID, kvalitetsstempel, alkoholprocent, størrelse, vandkilde, nummer, årstal != null
     */
    public WhiskyProdukt(int batchID, double vandMængde, String kvalitetsstempel, double alkoholprocent,
                         double størrelse, String vandkilde, String beskrivelse, int nummer, int årstal) {
        this.batchID = batchID;
        this.vandMængde = vandMængde;
        this.kvalitetsstempel = kvalitetsstempel;
        this.alkoholprocent = alkoholprocent;
        this.størrelse = størrelse;
        this.vandkilde = vandkilde;
        this.beskrivelse = beskrivelse;
        this.nummer = nummer;
        this.årstal = årstal;
    }
    //--------------------------------------
    //Get metoder
    public int getBatchID() {
        return batchID;
    }

    public double getVandMængde() {
        return vandMængde;
    }

    public String getKvalitetsstempel() {
        return kvalitetsstempel;
    }

    public double getAlkoholprocent() {
        return alkoholprocent;
    }

    public double getStørrelse() {
        return størrelse;
    }

    public String getVandkilde() {
        return vandkilde;
    }

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public int getNummer() {
        return nummer;
    }

    public int getÅrstal() {
        return årstal;
    }
    //--------------------------------------

}
