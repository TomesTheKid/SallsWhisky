package application.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Destillat implements Serializable {
    private LocalDate startDato;
    private LocalDate slutDato;
    private String maltbatch;
    private String kornsort;
    private double mængdeVæske;
    private double alkoholprocent;
    private String rygemateriale;
    private String kommentar;
    private int newMakeNr;
    private int ID;
    private double aktuelMængdeVæske;


    public Destillat(LocalDate startDato, LocalDate slutDato, String maltbatch, String kornsort,
                     double mængdeVæske, double alkoholprocent, String rygemateriale, String kommentar,
                     int newMakeNr, int ID, double aktuelMængdeVæske) {
        this.startDato = startDato;
        this.slutDato = slutDato;
        this.maltbatch = maltbatch;
        this.kornsort = kornsort;
        this.mængdeVæske = mængdeVæske;
        this.alkoholprocent = alkoholprocent;
        this.rygemateriale = rygemateriale;
        this.kommentar = kommentar;
        this.newMakeNr = newMakeNr;
        this.ID = ID;
        this.aktuelMængdeVæske = aktuelMængdeVæske;
    }

    //Alle get metoder
    public LocalDate getStartDato() {
        return startDato;
    }

    public LocalDate getSlutDato() {
        return slutDato;
    }

    public String getMaltbatch() {
        return maltbatch;
    }

    public String getKornsort() {
        return kornsort;
    }

    public double getMaengdeVaeske() {
        return mængdeVæske;
    }

    public double getAlkoholprocent() {
        return alkoholprocent;
    }

    public String getRygemateriale() {
        return rygemateriale;
    }

    public String getKommentar() {
        return kommentar;
    }

    public int getNewMakeNr() {
        return newMakeNr;
    }

    public int getID() {
        return ID;
    }

    public double getAktuelMaengdeVaeske() {
        return aktuelMængdeVæske;
    }
    //----------------------------------------------------------------

    public void afslutDestillering(){
        this.slutDato = LocalDate.now();
    }

    @Override
    public String toString() {
        return "Destillat{" +
                "ID=" + ID +
                ", aktuelMængdeVæske=" + aktuelMængdeVæske +
                '}';
    }
}
