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


    @Override
    public List<Destillat> getDestillater() {
        return new ArrayList<>(destillater);
    }

    @Override
    public void addDestillat(Destillat destillat){
        destillater.add(destillat);
    }

    @Override
    public void removeDestillat(Destillat destillat){destillater.remove(destillat);}

    //-----------------------------------------------
    //Fad

    @Override
    public List<Fad> getFade(){
        return new ArrayList<>(fade);
    }

    @Override
    public void addFad(Fad fad){
        fade.add(fad);
    }

    @Override
    public void removeFad(Fad fad){
        fade.remove(fad);
    }
    //--------------------------------------------------

}
