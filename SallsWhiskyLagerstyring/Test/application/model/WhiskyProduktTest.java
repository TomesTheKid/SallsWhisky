package application.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class WhiskyProduktTest {
    private int batchID;
    private double vandMængde;
    private String kvalitetsstempel;
    private double alkoholprocent;
    private double størrelse;
    private String vandkilde;
    private String beskrivelse;
    private int nummer;
    private int årstal;

    private WhiskyProdukt wp;
    private Fad fad;
    private Destillat d1;
    private Påfyldning påfyldning;
    private VæskeMængde vm;
    private Fadbidrag fb;

    @BeforeEach
    void setUp(){
        batchID = 1;
        vandMængde = 10.5;
        kvalitetsstempel = "Single cask";
        alkoholprocent = 45.0;
        størrelse = 70.0;
        vandkilde = "Begravet kilde";
        beskrivelse = "Flaske nummer 11";
        nummer = 11;
        årstal = 2026;

        wp = new WhiskyProdukt(batchID, vandMængde, kvalitetsstempel, alkoholprocent, størrelse, vandkilde, beskrivelse, nummer, årstal);
        fad = new Fad(1, "Lars", "Spanien", 1, Alkoholtype.RØDVIN, 300);
        d1 = new Destillat(LocalDate.of(2026,5,1), LocalDate.of(2026,5,2), "Muld", Kornsort.IRINA, 300, 70, "Tørv", null, 1, 101, 300);
        påfyldning = new Påfyldning(LocalDate.of(2026, 5, 10), "Jens", fad);
        vm = påfyldning.createVæskeMængde(100.0, d1);
        fb = new Fadbidrag(50.0);

        vm.setDestillat(d1);
        fb.setPåfyldning(påfyldning);
        wp.addFadbidragTilWP(fb);
    }


    @Test
    void genererHistoriskRapport() {
        //TC1
        StringBuilder testBuilder = new StringBuilder();
        testBuilder.append("--- HISTORISK RAPPORT ---\n");
        testBuilder.append("Batch ID:         1\n");
        testBuilder.append("Nummer:           11\n");
        testBuilder.append("Årstal:           2026\n");
        testBuilder.append("Kvalitetsstempel: Single cask\n");
        testBuilder.append(String.format("Alkoholprocent:   %.1f%%\n", 45.0));
        testBuilder.append(String.format("Størrelse:        %.1f L\n", 70.0));
        testBuilder.append("Vandkilde:        Begravet kilde\n");
        testBuilder.append(String.format("Vandmængde:       %.1f L\n", 10.5));
        testBuilder.append("Beskrivelse:      Flaske nummer 11\n");
        testBuilder.append("\n--- Fadbidrag ---\n");

        testBuilder.append(String.format("\nFadbidrag: %.1f L\n", 50.0));
        testBuilder.append("  Påfyldningsdato: 2026-05-10\n");
        testBuilder.append("  Ansvarlig:       Jens\n");
        testBuilder.append("  Fad nr:          1\n");
        testBuilder.append(String.format("  Fad størrelse:   %.1f L\n", 300.0));
        testBuilder.append("  Fad leverandør:  Lars\n");
        testBuilder.append("  Fad land:        Spanien\n");
        testBuilder.append("  Tidl. alkohol:   RØDVIN\n");
        testBuilder.append("  Destillater i påfyldning:\n");
        testBuilder.append(String.format("    - %.1f L", 100.0));
        testBuilder.append(" | ID: 101");
        testBuilder.append(" | Kornsort: IRINA");
        testBuilder.append(String.format(" | Alkohol: %.1f%%", 70.0));
        testBuilder.append(" | Periode: 2026-05-01 - 2026-05-02");
        testBuilder.append(" | Maltbatch: Muld\n");

        testBuilder.append("\n=========================\n");

        String expected = testBuilder.toString();
        String actual = wp.genererHistoriskRapport();
        assertEquals(expected, actual);

    }
}