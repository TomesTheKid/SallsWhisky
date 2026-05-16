package application.controller;

import application.model.*;
import storage.StorageFile;

import java.io.BufferedWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private static Storage storage = new StorageFile();

    public static void setStorage(Storage storageIn){
        storage = storageIn;
    }

    public static Storage getStorage(){
        return storage;
    }
    //-----DESTILLAT---------
    /**
     * Hvis startDato er efter slutDato kastes en IllegalArgumentException og
     * destillat oprettes ikke
     * Tilføjer objektet til storage
     * Pre: startDato, slutDato, maltbatch, kornsort, mængdeVæske != null
     * Pre: alkoholprocent, rygemateriale, newMakeNr, ID != null
     * @return destilat objekt
     */
    public static Destillat createDestillat(LocalDate startDato,
                                            LocalDate slutDato,
                                            String maltbatch,
                                            Kornsort kornsort,
                                            double mængdeVæske,
                                            double alkoholprocent,
                                            String rygemateriale,
                                            String kommentar,
                                            int newMakeNr,
                                            int ID,
                                            double aktuelMængdeVæske) {
        validerDestillat(startDato, slutDato, mængdeVæske, alkoholprocent, aktuelMængdeVæske);
        Destillat destillat = new Destillat(startDato, slutDato, maltbatch, kornsort,
                mængdeVæske, alkoholprocent, rygemateriale, kommentar, newMakeNr, ID, aktuelMængdeVæske);
        storage.addDestillat(destillat);
        return destillat;
    }

    private static void validerDestillat(LocalDate startDato, LocalDate slutDato,
                                         double mængdeVæske, double alkoholprocent,
                                         double aktuelMængdeVæske) {
        if (slutDato.isBefore(startDato)) {
            throw new IllegalArgumentException("Slutdato er før startdato");
        }
        if (mængdeVæske <= 0) {
            throw new IllegalArgumentException("Mængde væske skal være større end 0");
        }
        if (alkoholprocent < 40 || alkoholprocent > 100) {
            throw new IllegalArgumentException("Alkoholprocent skal være mellem 40 og 100");
        }
        if (aktuelMængdeVæske < 0 || aktuelMængdeVæske > mængdeVæske) {
            throw new IllegalArgumentException("Aktuel mængde væske kan ikke overstige den samlede mængde");
        }
    }

    //---------FAD----------
    /**
     * Pre: nummer, leverandør, land, antalBrug, tidligereAlkoholindhold, størrelse != null
     * Tilføjer objketet til storage
     * @return fad objekt
     */
    public static Fad createFad(int nummer,
                                String leverandør,
                                String land,
                                int antalBrug,
                                Alkoholtype tidligereAlkoholindhold,
                                double størrelse){
        Fad fad = new Fad(nummer, leverandør, land, antalBrug, tidligereAlkoholindhold, størrelse);
        storage.addFad(fad);
        return fad;
    }

    //-------PÅFYLDNING---------
    /**
     * Hvis Listen med væskemængders totalmængde > fadet størrelse kastes en IllegalArgumentException
     * Pre: dato, ansvarlig, fad, væskeMængder != null
     * Pre: List med mængder og List med destillater skal være lige store
     * @return påfyldnings objekt
     */
    public static Påfyldning createPåfyldning(LocalDate dato, String ansvarlig,
                                              Fad fad,
                                              List<Double> mængder, List<Destillat> destillater) {
        validerMængder(mængder, fad);

        Påfyldning påfyldning = new Påfyldning(dato, ansvarlig, fad);
        for (int i = 0; i < mængder.size(); i++) {
            double m = mængder.get(i);
            Destillat d = destillater.get(i);
            påfyldning.createVæskeMængde(m, d);
            d.reducerAktuelMængdeVæske(m);
        }

        fad.addPåfyldning(påfyldning);
        return påfyldning;
    }

    private static void validerMængder(List<Double> mængder, Fad fad) {
        double totalMængde = 0;
        for (Double m : mængder) {
            if (m == null || m <= 0) {
                throw new IllegalArgumentException("Mængde skal være > 0");
            }
            totalMængde += m;
        }
        if (totalMængde > fad.getStørrelse()) {
            throw new IllegalArgumentException("Der er ikke plads på fadet");
        }
    }

    // --- WhiskyProdukt ---
    /**
     * Opretter et WhiskyProdukt. For hvert par (påfyldning, mængde) oprettes et Fadbidrag,
     * som via sine set-metoder kobler sig selv til både Påfyldning og WhiskyProdukt.
     */
    public static WhiskyProdukt createWhiskyProdukt(int batchID,
                                                    double vandMængde,
                                                    String kvalitetsstempel,
                                                    double alkoholprocent,
                                                    double størrelse,
                                                    String vandkilde,
                                                    String beskrivelse,
                                                    int nummer,
                                                    int årstal,
                                                    List<Påfyldning> påfyldninger,
                                                    List<Double> mængder) {
        validerWhiskyProdukt(alkoholprocent, påfyldninger, mængder);
        WhiskyProdukt wp = new WhiskyProdukt(batchID, vandMængde, kvalitetsstempel, alkoholprocent,
                størrelse, vandkilde, beskrivelse, nummer, årstal);
        for (int i = 0; i < påfyldninger.size(); i++) {
            Fadbidrag fb = new Fadbidrag(mængder.get(i));
            fb.setPåfyldning(påfyldninger.get(i));
            fb.setWhiskyProdukt(wp);
        }
        storage.addWhiskyProdukt(wp);
        return wp;
    }

    private static void validerWhiskyProdukt(double alkoholprocent,
                                             List<Påfyldning> påfyldninger,
                                             List<Double> mængder) {
        if (påfyldninger == null || mængder == null
                || påfyldninger.size() != mængder.size()
                || påfyldninger.isEmpty()) {
            throw new IllegalArgumentException("Påfyldninger og mængder skal matche og ikke være tomme");
        }
        if (alkoholprocent <= 0 || alkoholprocent > 100) {
            throw new IllegalArgumentException("Alkoholprocent skal være mellem 0 og 100");
        }
    }

    /**
     * Genererer en historisk rapport for et WhiskyProdukt og skriver den måske til en fil.
     * Pre: wp != null
     */
    // TODO
    public static void gemHistoriskRapport(WhiskyProdukt whiskyProdukt) {
        // pladsHolder til at lave HistoriskRapport
    }

    // ---------Get metode---------
    public static List<Destillat> getDestillater() {
        return new ArrayList<>(storage.getDestillater());
    }

    public static List<Fad> getFade(){
        return new ArrayList<>(storage.getFade());
    }

    public static List<WhiskyProdukt> getWhiskyProdukter(){
        return new ArrayList<>(storage.getWhiskyProdukter());
    }

    // -----------LAGER-------------
    /**
     * Opretter et Lager og gemmer det i storage.
     * Pre: lagerNavn og adresse != null og ikke tomme
     */
    public static Lager createLager(String lagerNavn, String adresse){
        if(lagerNavn == null || lagerNavn.isBlank()){
            throw new IllegalArgumentException("Lagernavn må ikke være tomt");
        }
        if(adresse == null || adresse.isBlank()){
            throw new IllegalArgumentException("Adresse må ikke være tom");
        }
        Lager lager = new Lager(lagerNavn, adresse);
        storage.addLager(lager);
        return lager;
    }

    /**
     * Opretter en LagerPlacering på det givne lager (komposition via Lager).
     * Pre: lager != null, reol/række/hylde >= 0
     */
    public static LagerPlacering createLagerPlacering(Lager lager, int reol, int række, int hylde) {
        if (lager == null) {
            throw new IllegalArgumentException("Lager må ikke være null");
        }
        return lager.createLagerPlacering(reol, række, hylde);
    }

    /**
     * Tildeler et fad til en lagerplacering.
     * Kaster IllegalArgumentException hvis pladsen allerede er optaget.
     * Pre: lagerPlacering og fad != null
     */
    public static void tildelFadTilPlacering(LagerPlacering lagerPlacering, Fad fad) {
        if (lagerPlacering == null || fad == null) {
            throw new IllegalArgumentException("LagerPlacering og Fad må ikke være null");
        }
        if (lagerPlacering.erOptaget() && lagerPlacering.getFad() != fad) {
            throw new IllegalArgumentException("Pladsen er allerede optaget af et andet fad");
        }
        lagerPlacering.setFad(fad);
    }

    /**
     * Flytter et fad til en ny lagerplacering.
     * Den gamle placering bliver frigjort automatisk via tovejslinket.
     * Pre: fad og nyPlacering != null
     */
    public static void flytFad(Fad fad, LagerPlacering nyPlacering) {
        if (fad == null || nyPlacering == null) {
            throw new IllegalArgumentException("Fad og placering må ikke være null");
        }
        if (nyPlacering.erOptaget() && nyPlacering.getFad() != fad) {
            throw new IllegalArgumentException("Den nye placering er allerede optaget");
        }
        nyPlacering.setFad(fad);
    }

    public static List<Lager> getLagre(){
        return new ArrayList<>(storage.getLagre());
    }


    public static List<Fad> getFadeModneTilAftapning() {
        List<Fad> modne = new ArrayList<>();
        LocalDate grænse = LocalDate.now().minusYears(3);
        for (Fad fad : storage.getFade()) {
            for (Påfyldning p : fad.getPåfyldninger()) {
                if (!p.getDato().isAfter(grænse)) {
                    modne.add(fad);
                    break;
                }
            }
        }
        return modne;
    }
}
