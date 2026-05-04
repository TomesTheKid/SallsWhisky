package application.controller;

import application.model.Destillat;
import application.model.Kornsort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.StorageFile;
import static org.junit.jupiter.api.Assertions.*;


import java.time.LocalDate;

class ControllerTest {
    private LocalDate startDato;
    private LocalDate slutDato;
    private String maltbatch;
    private Kornsort kornsort;
    private double mængdeVæske;
    private double alkoholprocent;
    private String rygemateriale;
    private String kommentar;
    private int newMakeNr;
    private int ID;
    private double aktuelMængdeVæske;
    private Destillat testDestillat;

    @BeforeEach
    void setUp(){
        Controller.setStorage(new StorageFile());
        startDato = LocalDate.of(2026,5,1);
        slutDato = LocalDate.of(2026,5,2);
        maltbatch = "Muld";
        kornsort = Kornsort.EVERGREEN;
        mængdeVæske = 300;
        alkoholprocent = 70;
        rygemateriale = "Tørv";
        kommentar = "Første destillat";
        newMakeNr = 1;
        ID = 1;
        aktuelMængdeVæske = mængdeVæske;
    }

    @Test
    void createDestillat() {
        //TC1
        assertEquals(0,Controller.getStorage().getDestillater().size());

        testDestillat = Controller.createDestillat(startDato, slutDato, maltbatch, kornsort, mængdeVæske, alkoholprocent, rygemateriale, kommentar, newMakeNr, ID, aktuelMængdeVæske);

        assertEquals(startDato, testDestillat.getStartDato());
        assertEquals(slutDato, testDestillat.getSlutDato());
        assertEquals(maltbatch, testDestillat.getMaltbatch());
        assertEquals(kornsort, testDestillat.getKornsort());
        assertEquals(mængdeVæske, testDestillat.getMaengdeVaeske(),0.0001);
        assertEquals(alkoholprocent, testDestillat.getAlkoholprocent());
        assertEquals(rygemateriale, testDestillat.getRygemateriale());
        assertEquals(kommentar,testDestillat.getKommentar());
        assertEquals(newMakeNr,testDestillat.getNewMakeNr());
        assertEquals(ID, testDestillat.getID());
        assertEquals(aktuelMængdeVæske, testDestillat.getAktuelMaengdeVaeske());

        assertEquals(1, Controller.getStorage().getDestillater().size());
        assertTrue(Controller.getStorage().getDestillater().contains(testDestillat));
    }
}