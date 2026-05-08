package guifx;

import application.controller.Controller;
import application.model.Fad;
import application.model.Påfyldning;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.List;

public class WhiskyProduktWindow extends Stage {

    // Fadbidrag-opbygning
    private final List<Påfyldning> valgtePåfyldninger = new ArrayList<>();
    private final List<Double> valgteMængder = new ArrayList<>();
    private final ObservableList<Påfyldning> påfyldningerIFad = FXCollections.observableArrayList();
    private final ObservableList<String> visningList = FXCollections.observableArrayList();

    private ComboBox<Fad> cbFad;
    private ComboBox<Påfyldning> cbPåfyldning;
    private TextField txfMængde;
    private ListView<String> lvwFadbidrags;

    // Produkt-felter
    private TextField txfBatchID;
    private TextField txfNummer;
    private TextField txfÅrstal;
    private TextField txfKvalitetsstempel;
    private TextField txfAlkoholprocent;
    private TextField txfStørrelse;
    private TextField txfVandkilde;
    private TextField txfVandmængde;
    private TextField txfBeskrivelse;

    private Label lblError;

    public WhiskyProduktWindow(Stage owner) {
        initStyle(StageStyle.UTILITY);
        initModality(Modality.APPLICATION_MODAL);
        initOwner(owner);
        setResizable(false);
        setTitle("Opret whisky produkt");

        GridPane pane = new GridPane();
        initContent(pane);
        setScene(new Scene(pane));
    }

    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        // Venstre side: produktinformation
        txfBatchID          = new TextField(); txfBatchID.setPromptText("fx 42");
        txfNummer           = new TextField(); txfNummer.setPromptText("fx 1");
        txfÅrstal           = new TextField(); txfÅrstal.setPromptText("fx 2024");
        txfKvalitetsstempel = new TextField(); txfKvalitetsstempel.setPromptText("fx Single Malt");
        txfAlkoholprocent   = new TextField(); txfAlkoholprocent.setPromptText("fx 46.0");
        txfStørrelse        = new TextField(); txfStørrelse.setPromptText("fx 200");
        txfVandkilde        = new TextField(); txfVandkilde.setPromptText("fx Kildevand");
        txfVandmængde       = new TextField(); txfVandmængde.setPromptText("fx 50");
        txfBeskrivelse      = new TextField(); txfBeskrivelse.setPromptText("fx Røget, frugtig");

        pane.add(new Label("Batch ID:"),         0, 0); pane.add(txfBatchID,          1, 0);
        pane.add(new Label("Nummer:"),           0, 1); pane.add(txfNummer,            1, 1);
        pane.add(new Label("Årstal:"),           0, 2); pane.add(txfÅrstal,            1, 2);
        pane.add(new Label("Kvalitetsstempel:"), 0, 3); pane.add(txfKvalitetsstempel,  1, 3);
        pane.add(new Label("Alkoholprocent:"),   0, 4); pane.add(txfAlkoholprocent,    1, 4);
        pane.add(new Label("Størrelse (L):"),    0, 5); pane.add(txfStørrelse,         1, 5);
        pane.add(new Label("Vandkilde:"),        0, 6); pane.add(txfVandkilde,         1, 6);
        pane.add(new Label("Vandmængde (L):"),   0, 7); pane.add(txfVandmængde,        1, 7);
        pane.add(new Label("Beskrivelse:"),      0, 8); pane.add(txfBeskrivelse,       1, 8);

        // Højre side: fadbidrag-tilføjelse
        pane.add(new Label("Vælg fad:"), 2, 0);
        cbFad = new ComboBox<>(FXCollections.observableArrayList(Controller.getFade()));
        pane.add(cbFad, 3, 0);
        cbFad.setOnAction(e -> opdaterPåfyldninger());

        pane.add(new Label("Vælg påfyldning:"), 2, 1);
        cbPåfyldning = new ComboBox<>(påfyldningerIFad);
        pane.add(cbPåfyldning, 3, 1);

        pane.add(new Label("Mængde (L):"), 2, 2);
        txfMængde = new TextField(); txfMængde.setPromptText("fx 80");
        pane.add(txfMængde, 3, 2);

        Button btnTilføj = new Button("Tilføj fadbidrag");
        pane.add(btnTilføj, 3, 3);
        GridPane.setHalignment(btnTilføj, HPos.RIGHT);
        btnTilføj.setOnAction(e -> tilføjFadbidrag());

        pane.add(new Label("Tilføjede fadbidrags:"), 2, 4);
        lvwFadbidrags = new ListView<>(visningList);
        lvwFadbidrags.setPrefHeight(160);
        pane.add(lvwFadbidrags, 2, 5, 2, 1);

        // Fejl og knapper
        lblError = new Label();
        lblError.setStyle("-fx-text-fill: red");
        pane.add(lblError, 0, 9, 4, 1);

        Button btnCancel = new Button("Annuller");
        btnCancel.setPrefWidth(100);
        pane.add(btnCancel, 0, 10);
        GridPane.setHalignment(btnCancel, HPos.LEFT);
        btnCancel.setOnAction(e -> hide());

        Button btnGem = new Button("Gem");
        btnGem.setPrefWidth(100);
        pane.add(btnGem, 3, 10);
        GridPane.setHalignment(btnGem, HPos.RIGHT);
        btnGem.setOnAction(e -> okAction());
    }

    private void opdaterPåfyldninger() {
        Fad fad = cbFad.getSelectionModel().getSelectedItem();
        påfyldningerIFad.clear();
        if (fad != null) {
            påfyldningerIFad.setAll(fad.getPåfyldninger());
        }
        cbPåfyldning.getSelectionModel().clearSelection();
    }

    private void tilføjFadbidrag() {
        lblError.setText("");
        Påfyldning p = cbPåfyldning.getSelectionModel().getSelectedItem();
        if (p == null) {
            lblError.setText("Vælg en påfyldning.");
            return;
        }
        double mængde;
        try {
            mængde = Double.parseDouble(txfMængde.getText().trim());
        } catch (NumberFormatException ex) {
            lblError.setText("Mængde skal være et tal.");
            return;
        }
        if (mængde <= 0) {
            lblError.setText("Mængde skal være > 0.");
            return;
        }
        valgtePåfyldninger.add(p);
        valgteMængder.add(mængde);
        visningList.add(String.format("%.1f L fra fad nr. %d (%s)",
                mængde,
                p.getFad() != null ? p.getFad().getNummer() : -1,
                p.getDato()));
        txfMængde.clear();
        // Fjern valgte påfyldning så den ikke kan tilføjes to gange
        påfyldningerIFad.remove(p);
    }

    private void okAction() {
        lblError.setText("");
        try {
            int batchID        = Integer.parseInt(txfBatchID.getText().trim());
            int nummer         = Integer.parseInt(txfNummer.getText().trim());
            int årstal         = Integer.parseInt(txfÅrstal.getText().trim());
            double alkohol     = Double.parseDouble(txfAlkoholprocent.getText().trim());
            double størrelse   = Double.parseDouble(txfStørrelse.getText().trim());
            double vandmængde  = Double.parseDouble(txfVandmængde.getText().trim());
            String kvalitet    = txfKvalitetsstempel.getText().trim();
            String vandkilde   = txfVandkilde.getText().trim();
            String beskrivelse = txfBeskrivelse.getText().trim();

            if (kvalitet.isEmpty() || vandkilde.isEmpty()) {
                lblError.setText("Kvalitetsstempel og vandkilde må ikke være tomme.");
                return;
            }
            if (valgtePåfyldninger.isEmpty()) {
                lblError.setText("Tilføj mindst ét fadbidrag.");
                return;
            }

            Controller.createWhiskyProdukt(
                    batchID, vandmængde, kvalitet, alkohol, størrelse,
                    vandkilde, beskrivelse, nummer, årstal,
                    new ArrayList<>(valgtePåfyldninger),
                    new ArrayList<>(valgteMængder)
            );
            hide();
        } catch (NumberFormatException ex) {
            lblError.setText("Kontrollér at alle talfelter er udfyldt korrekt.");
        } catch (IllegalArgumentException ex) {
            lblError.setText(ex.getMessage());
        }
    }
}