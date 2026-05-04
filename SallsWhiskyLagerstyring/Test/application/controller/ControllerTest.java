package application.controller;

import application.model.Destillat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.StorageFile;
import static org.junit.jupiter.api.Assertions.*;


import java.time.LocalDate;

class ControllerTest {
    private LocalDate startDato;
    private LocalDate slutDato;
    private String maltbatch;
    private String kornsort;
    private double væskeMængde;
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
        væskeMængde = 300;
        alkoholprocent = 70;
        rygemateriale = "Tørv";
        kommentar = "Første destillat";
        newMakeNr = 1;
        ID = 1;
        aktuelMængdeVæske = væskeMængde;
    }

    @Test
    void createDestillat() {
        testDestillat = Controller.createDestillat(startDato, slutDato, maltbatch, kornsort, væskeMængde, alkoholprocent, rygemateriale, kommentar, newMakeNr, ID, aktuelMængdeVæske);

        //TC1
        assertEquals(startDato, testDestillat.getStartDato());
        assertEquals(slutDato, testDestillat.getSlutDato());
        assertEquals();

    }
}