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
    //-----------------------------------

    //CreateMetoder
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
                                            double aktuelMængdeVæske){
        if (slutDato.isBefore(startDato)){
            throw new IllegalArgumentException("Slutdato er før startdato");
        }

        if(mængdeVæske <= 0){
            throw new IllegalArgumentException("Mængde væske skal være større end 0");
        }

        if(alkoholprocent <= 40 || alkoholprocent > 100){
            throw new IllegalArgumentException("Alkoholprocent skal være mellem 40 og 100");
        }

        if(aktuelMængdeVæske < 0 || aktuelMængdeVæske > mængdeVæske){
            throw new IllegalArgumentException("Aktuel mængde væske kan ikke overstige den samlede mængde");
        }

        Destillat destillat = new Destillat(
                startDato,
                slutDato,
                maltbatch,
                kornsort,
                mængdeVæske,
                alkoholprocent,
                rygemateriale,
                kommentar,
                newMakeNr,
                ID,
                aktuelMængdeVæske);
        storage.addDestillat(destillat);
        return destillat;
    }

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

    /**
     * Hvis Listen med væskemængders totalmængde > fadet størrelse kastes en IllegalArgumentException
     * Pre: dato, ansvarlig, fad, væskeMængder != null
     * Pre: List med mængder og List med destillater skal være lige store
     * @return påfyldnings objekt
     */
    public static Påfyldning createPåfyldning(LocalDate dato,
                                              String ansvarlig,
                                              Fad fad,
                                              List<Double> mængder, List<Destillat> destillater) {

        double totalMængde = 0;
        for (Double m : mængder){
            if (m == null || m <= 0) {
                throw new IllegalArgumentException("Mængde skal være > 0");
            }
            totalMængde += m;
        }
        if (totalMængde > fad.getStørrelse()){
            throw new IllegalArgumentException("Der er ikke plads på fadet");
        }

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

    // --- WhiskyProdukt ---
    /**
     * Opretter et WhiskyProdukt. For hvert par (påfyldning, mængde) oprettes et Fadbidrag,
     * som via sine set-metoder kobler sig selv til både Påfyldning og WhiskyProdukt.
     * Pre: påfyldninger og mængder har samme størrelse og er ikke tomme
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
        if (påfyldninger == null || mængder == null
                || påfyldninger.size() != mængder.size()
                || påfyldninger.isEmpty()) {
            throw new IllegalArgumentException("Påfyldninger og mængder skal matche og ikke være tomme");
        }
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

    /**
     * Genererer en historisk rapport for et WhiskyProdukt og skriver den måske til en fil.
     * Pre: wp != null
     */
    // TODO
    public static void gemHistoriskRapport(WhiskyProdukt whiskyProdukt) {
        // pladsHolder til at lave HistoriskRapport
    }


    // Get metode
    public static List<Destillat> getDestillater() {
        return new ArrayList<>(storage.getDestillater());
    }

    public static List<Fad> getFade(){
        return new ArrayList<>(storage.getFade());
    }

    public static List<WhiskyProdukt> getWhiskyProdukter(){
        return new ArrayList<>(storage.getWhiskyProdukter());
    }
}
