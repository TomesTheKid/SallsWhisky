package storage;

import application.model.Destillat;
import application.model.Fad;

import java.util.ArrayList;
import java.util.List;

public class StorageFile {

    private final List<Destillat> distillater = new ArrayList<>();
    private final List<Fad> fade = new ArrayList<>();

    //-----------------------------------------------
    //Destillat
    public List<Destillat> getDistillater() {
        return new ArrayList<>(distillater);
    }

    public void addDestillat(Destillat destillat){
        distillater.add(destillat);
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
