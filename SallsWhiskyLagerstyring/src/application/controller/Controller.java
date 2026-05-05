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
    /*
     Kommentar må gerne være null
     aktuelMængdeVæske må gerne være null
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


    public static Påfyldning createPåfyldning(LocalDate dato,
                                              String ansvarlig,
                                              Fad fad,
                                              int fillNr,
                                              List<VæskeMængde> væskeMængder) {
        if (fad == null) {
            throw new IllegalArgumentException("Fad mangler");
        }
        if (væskeMængder == null || væskeMængder.isEmpty()) {
            throw new IllegalArgumentException("Ingen væske angivet");
        }

        Påfyldning påfyldning = new Påfyldning(dato, ansvarlig, fad, fillNr);
        for(VæskeMængde vm : væskeMængder){
            påfyldning.createVæskeMængde(vm.getMængde(), vm.getDestillat());
            vm.getDestillat().reducerAktuelMængdeVæske(vm.getMængde());
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
