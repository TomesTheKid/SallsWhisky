package application.model;

import java.io.Serializable;
import java.util.ArrayList;

public class WhiskyProdukt implements Serializable {
    private int batchID;
    private double vandMængde;
    private String kvalitetsstempel;
    private double alkoholprocent;
    private double størrelse;
    private String vandkilde;
    private String beskrivelse;
    private int nummer;
    private int årstal;
    private final ArrayList<Fadbidrag> fadbidrags = new ArrayList<>();

    /**
     * Opretter WhiskyProdukt
     * Pre: batchID, kvalitetsstempel, alkoholprocent, størrelse, vandkilde, nummer, årstal != null
     */
    public WhiskyProdukt(int batchID, double vandMængde, String kvalitetsstempel, double alkoholprocent,
                         double størrelse, String vandkilde, String beskrivelse, int nummer, int årstal) {
        this.batchID = batchID;
        this.vandMængde = vandMængde;
        this.kvalitetsstempel = kvalitetsstempel;
        this.alkoholprocent = alkoholprocent;
        this.størrelse = størrelse;
        this.vandkilde = vandkilde;
        this.beskrivelse = beskrivelse;
        this.nummer = nummer;
        this.årstal = årstal;
    }
    //--------------------------------------
    //Get metoder
    public int getBatchID() {
        return batchID;
    }

    public double getVandMængde() {
        return vandMængde;
    }

    public String getKvalitetsstempel() {
        return kvalitetsstempel;
    }

    public double getAlkoholprocent() {
        return alkoholprocent;
    }

    public double getStørrelse() {
        return størrelse;
    }

    public String getVandkilde() {
        return vandkilde;
    }

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public int getNummer() {
        return nummer;
    }

    public int getÅrstal() {
        return årstal;
    }
    //--------------------------------------
    //Link til Fadbidrag

    public ArrayList<Fadbidrag> getFadbidrags() {
        return new ArrayList<>(fadbidrags);
    }

    public void addFadbidragTilWP(Fadbidrag fadbidrag){
        if (!fadbidrags.contains(fadbidrag)){
            fadbidrags.add(fadbidrag);
            fadbidrag.setWhiskyProdukt(this);
        }
    }

    public void removeFadbidragFraWP(Fadbidrag fadbidrag){
        if (fadbidrags.contains(fadbidrag)){
            fadbidrags.remove(fadbidrag);
            fadbidrag.setWhiskyProdukt(null);
        }
    }


    /**
     * Genererer en historisk rapport som en String
     * Rapporten beskriver WhiskyProduktet og alle tilknyttede fadbidrags sporbarhed
     */
    public String genererHistoriskRapport(){
        StringBuilder sb = new StringBuilder();
        sb.append("--- HISTORISK RAPPORT ---\n");
        sb.append("Batch ID:         ").append(batchID).append("\n");
        sb.append("Nummer:           ").append(nummer).append("\n");
        sb.append("Årstal:           ").append(årstal).append("\n");
        sb.append("Kvalitetsstempel: ").append(kvalitetsstempel).append("\n");
        sb.append("Alkoholprocent:   ").append(String.format("%.1f", alkoholprocent)).append("%\n");
        sb.append("Størrelse:        ").append(String.format("%.1f", størrelse)).append(" L\n");
        sb.append("Vandkilde:        ").append(vandkilde).append("\n");
        sb.append("Vandmængde:       ").append(String.format("%.1f", vandMængde)).append(" L\n");
        sb.append("Beskrivelse:      ").append(beskrivelse).append("\n");
        sb.append("\n--- Fadbidrag ---\n");

        for (Fadbidrag fb : fadbidrags) {
            Påfyldning p = fb.getPåfyldning();
            sb.append("\nFadbidrag: ").append(String.format("%.1f", fb.getMængdeLiter())).append(" L\n");
            if (p != null) {
                Fad fad = p.getFad();
                sb.append("  Påfyldningsdato: ").append(p.getDato()).append("\n");
                sb.append("  Ansvarlig:       ").append(p.getAnsvarlig()).append("\n");
                if (fad != null) {
                    sb.append("  Fad nr:          ").append(fad.getNummer()).append("\n");
                    sb.append("  Fad størrelse:   ").append(String.format("%.1f", fad.getStørrelse())).append(" L\n");
                    sb.append("  Fad leverandør:  ").append(fad.getLeverandør()).append("\n");
                    sb.append("  Fad land:        ").append(fad.getLand()).append("\n");
                    sb.append("  Tidl. alkohol:   ").append(fad.getTidligereAlkoholindhold()).append("\n");
                }
                sb.append("  Destillater i påfyldning:\n");
                for (VæskeMængde vm : p.getVæskeMængder()) {
                    Destillat d = vm.getDestillat();
                    sb.append("    - ").append(String.format("%.1f", vm.getMængde())).append(" L");
                    if (d != null) {
                        sb.append(" | ID: ").append(d.getID())
                                .append(" | Kornsort: ").append(d.getKornsort())
                                .append(" | Alkohol: ").append(String.format("%.1f", d.getAlkoholprocent())).append("%")
                                .append(" | Periode: ").append(d.getStartDato()).append(" - ").append(d.getSlutDato())
                                .append(" | Maltbatch: ").append(d.getMaltbatch());
                    }
                    sb.append("\n");
                }
            }
        }
        sb.append("\n=========================\n");
        return sb.toString();
    }


    @Override
    public String toString() {
        return "WhiskyProdukt med batchID" + batchID;
    }
}
