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
        aktuelMængdeVæske = 300;
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

        //TC2
        Exception exception2 = assertThrows(IllegalArgumentException.class, () ->{
            Controller.createDestillat(LocalDate.of(2026,5,5), LocalDate.of(2026,5,2),maltbatch,kornsort,mængdeVæske,alkoholprocent,rygemateriale,kommentar,newMakeNr, ID, aktuelMængdeVæske);
        });
        assertEquals(exception2.getMessage(), "Slutdato er før startdato");

        //TC3
        Exception exception3 = assertThrows(IllegalArgumentException.class, () ->{
            Controller.createDestillat(startDato, slutDato,maltbatch,kornsort,-100,alkoholprocent,rygemateriale,kommentar,newMakeNr, ID, aktuelMængdeVæske);
        });
        assertEquals(exception3.getMessage(), "Mængde væske skal være større end 0");

        //TC4
        Exception exception4 = assertThrows(IllegalArgumentException.class, () ->{
            Controller.createDestillat(startDato, slutDato,maltbatch,kornsort,0,alkoholprocent,rygemateriale,kommentar,newMakeNr, ID, aktuelMængdeVæske);
        });
        assertEquals(exception4.getMessage(), "Mængde væske skal være større end 0");

        //TC5
        Exception exception5 = assertThrows(IllegalArgumentException.class, () ->{
            Controller.createDestillat(startDato, slutDato,maltbatch,kornsort,mængdeVæske,35,rygemateriale,kommentar,newMakeNr, ID, aktuelMængdeVæske);
        });
        assertEquals(exception5.getMessage(), "Alkoholprocent skal være mellem 40 og 100");

        //TC6
        Destillat td6 = Controller.createDestillat(startDato, slutDato, maltbatch, kornsort, mængdeVæske, 40, rygemateriale, kommentar, newMakeNr, ID, aktuelMængdeVæske);
        assertEquals(40, td6.getAlkoholprocent());
        assertTrue(Controller.getStorage().getDestillater().contains(td6));

        //TC7
        Destillat td7 = Controller.createDestillat(startDato, slutDato, maltbatch, kornsort, mængdeVæske, 100, rygemateriale, kommentar, newMakeNr, ID, aktuelMængdeVæske);
        assertEquals(100, td7.getAlkoholprocent());
        assertTrue(Controller.getStorage().getDestillater().contains(td7));

        //TC8
        Exception exception8 = assertThrows(IllegalArgumentException.class, () ->{
            Controller.createDestillat(startDato, slutDato,maltbatch,kornsort,mængdeVæske,101,rygemateriale,kommentar,newMakeNr, ID, aktuelMængdeVæske);
        });
        assertEquals(exception8.getMessage(), "Alkoholprocent skal være mellem 40 og 100");

        //TC9
        Exception exception9 = assertThrows(IllegalArgumentException.class, () ->{
            Controller.createDestillat(startDato, slutDato,maltbatch,kornsort,mængdeVæske,alkoholprocent,rygemateriale,kommentar,newMakeNr, ID, -10);
        });
        assertEquals(exception9.getMessage(), "Aktuel mængde væske kan ikke overstige den samlede mængde");

        //TC10
        Exception exception10 = assertThrows(IllegalArgumentException.class, () ->{
            Controller.createDestillat(startDato, slutDato,maltbatch,kornsort,mængdeVæske,alkoholprocent,rygemateriale,kommentar,newMakeNr, ID, 301);
        });
        assertEquals(exception10.getMessage(), "Aktuel mængde væske kan ikke overstige den samlede mængde");

        //TC11
        Destillat td11 = Controller.createDestillat(startDato, slutDato, maltbatch, kornsort, mængdeVæske, alkoholprocent, rygemateriale, kommentar, newMakeNr, ID, 0);
        assertEquals(0, td11.getAktuelMaengdeVaeske());
        assertTrue(Controller.getStorage().getDestillater().contains(td11));
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
        assertEquals(exception.getMessage(), "Der er ikke plads på fadet");

    }
}