package application.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Omhældning implements Serializable {
    private LocalDate dato;
    private String ansvarlig;
    private final List<OmhældningBidrag> bidrag = new ArrayList<>();

    public Omhældning(LocalDate dato, String ansvarlig) {
        this.dato = dato;
        this.ansvarlig = ansvarlig;
    }

    public void addBidrag(OmhældningBidrag b) {
        if (!bidrag.contains(b)) bidrag.add(b);
    }

    public List<OmhældningBidrag> getBidrag() { return new ArrayList<>(bidrag); }
    public LocalDate getDato() { return dato; }
    public String getAnsvarlig() { return ansvarlig; }

    @Override
    public String toString() {
        return "Omhældt " + dato + " af " + ansvarlig;
    }
}