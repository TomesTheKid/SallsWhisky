package storage;

import application.controller.Storage;
import application.model.Destillat;
import application.model.Fad;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StorageFile implements Storage, Serializable {

    private final List<Destillat> destillater = new ArrayList<>();
    private final List<Fad> fade = new ArrayList<>();

    //-----------------------------------------------
    //Destillat


    public void addDestillat(Destillat destillat){
        destillater.add(destillat);
    }

    @Override
    public List<Destillat> getDestillater() {
        return new ArrayList<>(destillater);
    }

    //-----------------------------------------------
    //Fad
    public List<Fad> getFade(){
        return new ArrayList<>(fade);
    }

    public void addFad(Fad fad){
        fade.add(fad);
    }

    public void removeFad(Fad fad){
        fade.remove(fad);
    }
    //--------------------------------------------------

}
