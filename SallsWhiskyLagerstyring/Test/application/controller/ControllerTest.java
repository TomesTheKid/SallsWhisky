package application.controller;

import application.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.StorageFile;
import static org.junit.jupiter.api.Assertions.*;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private String ansvarlig;
    private Fad fad;
    private Destillat d1;
    private Destillat d2;


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
        ansvarlig = "Bo";
        fad = new Fad(3, "Karl", "Spanien", 1, Alkoholtype.RØDVIN, 200);
        d1 = new Destillat(startDato, slutDato, maltbatch, Kornsort.IRINA, mængdeVæske, alkoholprocent, rygemateriale, null, 1, 101, aktuelMængdeVæske);
        d2 = new Destillat(startDato, slutDato, maltbatch, Kornsort.EVERGREEN, mængdeVæske, alkoholprocent, rygemateriale, null, 2, 102, aktuelMængdeVæske);

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

    @Test
    void createPåfyldning(){
        //TC1
        List<Double> mængder = new ArrayList<>();
        mængder.add(50.0);
        mængder.add(100.0);

        List<Destillat> destillater = new ArrayList<>();
        destillater.add(d1);
        destillater.add(d2);

        double startMængdeD1 = d1.getAktuelMaengdeVaeske();
        double startMængdeD2 = d2.getAktuelMaengdeVaeske();

        Påfyldning testPåfyldning = Controller.createPåfyldning(startDato, ansvarlig, fad, mængder, destillater);

        assertNotNull(testPåfyldning);
        assertEquals(startDato, testPåfyldning.getDato());
        assertEquals(ansvarlig, testPåfyldning.getAnsvarlig());
        assertEquals(fad, testPåfyldning.getFad());

        assertEquals(2, testPåfyldning.getVæskeMængder().size());

        assertEquals(startMængdeD1 - 50, d1.getAktuelMaengdeVaeske(), 0.0001);
        assertEquals(startMængdeD2 - 100, d2.getAktuelMaengdeVaeske(), 0.0001);

        assertTrue(fad.getPåfyldninger().contains(testPåfyldning));

        //TC2
        Fad fejlFad = new Fad(3, "Karl", "Spanien", 1, Alkoholtype.RØDVIN, 200);

        List<Double> fejlMængder = new ArrayList<>();
        fejlMængder.add(200.0);
        fejlMængder.add(100.0);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->{
            Controller.createPåfyldning(startDato, ansvarlig, fejlFad, fejlMængder, destillater);
        });
        assertEquals(exception.getMessage(), "Der er ikke plads på faldet");
    }
}