package application.controller;

import application.model.Alkoholtype;
import application.model.Destillat;
import application.model.Fad;
import application.model.Kornsort;
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
    //TODO
    //Sikre at slutdato ikke er før startdato
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

    // Get metode

    public static List<Destillat> getDestillater() {
        return new ArrayList<>(storage.getDestillater());
    }

    public static List<Fad> getFade(){return new ArrayList<>(storage.getFade());}
}
