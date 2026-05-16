package application.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Påfyldning implements Serializable {
    private LocalDate dato;
    private String ansvarlig;
    private Fad fad;
    private ArrayList<VæskeMængde> væskeMængder;
    private final ArrayList<Fadbidrag> fadbidrags = new ArrayList<>();

    /**
     * Opretter Påfyldning og tæller fadet antalBrug op med 1
     * Pre: dato, ansvarlig, fad != null
     */
    public Påfyldning(LocalDate dato, String ansvarlig, Fad fad){
        this.dato = dato;
        this.ansvarlig = ansvarlig;
        this.fad = fad;
        this.væskeMængder = new ArrayList<>();
        fad.optælAntalBrug();
    }

    //---------GET METODER------------
    public LocalDate getDato() {
        return dato;
    }

    public String getAnsvarlig() {
        return ansvarlig;
    }

    public ArrayList<VæskeMængde> getVæskeMængder() {
        return new ArrayList<>(væskeMængder);
    }

    //-------------------------------------------------
    //Link til Fad
    public Fad getFad() {
        return fad;
    }

    public void setFad(Fad fad){
        if (this.fad != fad){
            Fad oldFad = this.fad;
            if (oldFad != null){
                oldFad.removePåfyldning(this);
            }
            this.fad = fad;
            if (fad != null){
                fad.addPåfyldning(this);
            }
        }
    }

    //-----------------------------------------------
    //Link til Fadbidrag
    public ArrayList<Fadbidrag> getFadbidrags() {
        return new ArrayList<>(fadbidrags);
    }

    public void addFadbidragTilP(Fadbidrag fadbidrag){
        if (!fadbidrags.contains(fadbidrag)){
            fadbidrags.add(fadbidrag);
            fadbidrag.setPåfyldning(this);
        }
    }

    public void removeFadbidragFraP(Fadbidrag fadbidrag){
        if (fadbidrags.contains(fadbidrag)){
            fadbidrags.remove(fadbidrag);
            fadbidrag.setPåfyldning(null);
        }
    }
    //-----------------------------------------------
    /**
     * Opretter VæskeMængde objekt og tilføjer det til Påfyldnings objektets Liste over væskemængder
     * Pre: mængde, destillat != null
     * @return objekt af væskemængde
     */
    public VæskeMængde createVæskeMængde(double mængde, Destillat destillat){
        VæskeMængde væskeMængde = new VæskeMængde(mængde, destillat);
        væskeMængder.add(væskeMængde);
        return væskeMængde;
    }

    /**
     * Regner samlet mængde ud for påfyldnings væskeObjekter
     * @return sum af alle væskemængder
     */
    public double getSamletPåfyldtMængde(){
        double sum = 0;
        for (VæskeMængde v : væskeMængder){
            sum += v.getMængde();
        }
        return sum;
    }

    //----------------------------------------------------

    public void appendReportBlock(StringBuilder sb) {
        sb.append("  Påfyldningsdato: ").append(dato).append("\n");
        sb.append("  Ansvarlig:       ").append(ansvarlig).append("\n");
        if (fad != null) {
            fad.appendReportLine(sb);
        }
        sb.append("  Destillater i påfyldning:\n");
        for (VæskeMængde vm : væskeMængder) {
            vm.appendReportLine(sb);
        }
    }

    @Override
    public String toString() {
        return "Påfyldt " + dato + " (" + String.format("%.0f", getSamletPåfyldtMængde()) + " L)";
    }
}
