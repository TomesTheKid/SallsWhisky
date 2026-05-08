package application.controller;

import application.model.Destillat;
import application.model.Fad;
import application.model.WhiskyProdukt;

import java.util.List;

public interface Storage {
    void addDestillat(Destillat destillat);
    void removeDestillat(Destillat destillat);
    List<Destillat> getDestillater();

    void addFad(Fad fad);
    void removeFad(Fad fad);
    List<Fad> getFade();

    void addWhiskyProdukt(WhiskyProdukt whiskyProdukt);
    void removeWhiskyProdukt(WhiskyProdukt whiskyProdukt);
    List<WhiskyProdukt> getWhiskyProdukter();
}
