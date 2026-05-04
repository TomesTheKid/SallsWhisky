package application.controller;

import application.model.Destillat;
import application.model.Fad;

import java.util.List;

public interface Storage {
    void addDestillat(Destillat destillat);
    void removeDestillat(Destillat destillat);
    List<Destillat> getDestillater();

    void addFad(Fad fad);
    void removeFad(Fad fad);
    List<Fad> getFade();
}
