package application.model;

import java.time.LocalDate;

public class Destillat {
    private LocalDate startDato;
    private LocalDate slutDato;
    private String maltbatch;
    private String kornsort;
    private double mængdeVæske;
    private double alkoholprocent;
    private String rygemateriale;
    private String kommentar;


    public Destillat(LocalDate slutDato, LocalDate startDato, String maltbatch, String kornsort,
                     double mængdeVæske, double alkoholprocent, String rygemateriale, String kommentar) {
        this.slutDato = slutDato;
        this.startDato = startDato;
        this.maltbatch = maltbatch;
        this.kornsort = kornsort;
        this.mængdeVæske = mængdeVæske;
        this.alkoholprocent = alkoholprocent;
        this.rygemateriale = rygemateriale;
        this.kommentar = kommentar;
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

    public double getMængdeVæske() {
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
    //----------------------------------------------------------------

}
