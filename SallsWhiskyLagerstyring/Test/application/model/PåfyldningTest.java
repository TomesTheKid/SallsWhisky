package application.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PåfyldningTest {
    private Påfyldning testPåfyldning;
    private LocalDate dato;
    private String ansvarlig;
    private Fad oldFad;
    private ArrayList<VæskeMængde> væskeMængder = new ArrayList<>();

    @BeforeEach
    void setUp(){
        dato = LocalDate.of(2026,5,5);
        ansvarlig = "Bo";
        oldFad = new Fad(1,"Lars","Spanien", 1,Alkoholtype.RØDVIN, 300);
        testPåfyldning = new Påfyldning(dato, ansvarlig, oldFad);
    }

    @Test
    void Påfyldning(){
        //TC1
        assertEquals(dato, testPåfyldning.getDato());
        assertEquals(ansvarlig, testPåfyldning.getAnsvarlig());
        assertEquals(oldFad, testPåfyldning.getFad());

        assertEquals(2, oldFad.getAntalBrug());
    }

    @Test
    void getDato() {
        //TC1
        assertEquals(LocalDate.of(2026,5,5), testPåfyldning.getDato());
    }

    @Test
    void setFad() {
        //TC1
        Fad newFad = new Fad(2, "Karl", "Spanien", 2, Alkoholtype.BOURBON, 200);

        assertEquals(oldFad, testPåfyldning.getFad());
        testPåfyldning.setFad(newFad);
        assertEquals(newFad, testPåfyldning.getFad());

        assertFalse(oldFad.getPåfyldninger().contains(testPåfyldning));
        assertTrue(newFad.getPåfyldninger().contains(testPåfyldning));
    }

    @Test
    void createVæskeMængde() {
        //TC1
        Destillat destillat = new Destillat(LocalDate.of(2026,5,4), LocalDate.of(2026,5,5), "Muld", Kornsort.EVERGREEN, 300, 70, "Tørv", null, 1, 101, 300);

        assertEquals(0, testPåfyldning.getVæskeMængder().size());

        VæskeMængde væskeMængde = testPåfyldning.createVæskeMængde(100, destillat);

        assertEquals(1, testPåfyldning.getVæskeMængder().size());
        assertTrue(testPåfyldning.getVæskeMængder().contains(væskeMængde));
    }

    @Test
    void getSamletPåfyldtMængde() {
        //TC1
        Destillat d1 = new Destillat(LocalDate.of(2026,5,4), LocalDate.of(2026,5,5), "Muld", Kornsort.EVERGREEN, 300, 70, "Tørv", null, 1, 101, 300);
        Destillat d2 = new Destillat(LocalDate.of(2026,5,1), LocalDate.of(2026,5,2), "Muld", Kornsort.IRINA, 200, 75, "Tørv", null, 2, 102, 200);

        VæskeMængde v1 = testPåfyldning.createVæskeMængde(100, d1);
        VæskeMængde v2 = testPåfyldning.createVæskeMængde(150, d2);

        assertEquals(250,testPåfyldning.getSamletPåfyldtMængde());
    }
}