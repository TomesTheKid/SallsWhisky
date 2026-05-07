package application.controller;

import application.model.*;
import storage.StorageFile;

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
     * @return påfyldnings objekt
     */
    public static Påfyldning createPåfyldning(LocalDate dato,
                                              String ansvarlig,
                                              Fad fad,
                                              List<Double> mængder, List<Destillat> destillater) {
        if (fad == null) {
            throw new IllegalArgumentException("Fad mangler");
        }
        if (mængder == null || mængder.isEmpty()) {
            throw new IllegalArgumentException("Ingen væske angivet");
        }

        double totalMængde = 0;
        for (Double m : mængder){
            totalMængde += m;
        }
        if (totalMængde > fad.getStørrelse()){
            throw new IllegalArgumentException("Der er ikke plads på faldet");
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

    // Get metode
    public static List<Destillat> getDestillater() {
        return new ArrayList<>(storage.getDestillater());
    }

    public static List<Fad> getFade(){return new ArrayList<>(storage.getFade());}
}
