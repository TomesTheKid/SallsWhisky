package guifx;

import application.controller.Controller;
import application.model.Fad;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OmhældningWindow extends Stage {
    private final List<Fad> valgteKildeFade = new ArrayList<>();
    private final List<Double> valgteMængder = new ArrayList<>();
    private final ObservableList<String> visningList = FXCollections.observableArrayList();

    private ComboBox<Fad> cbKildeFad;
    private TextField txfMængde;
    private ComboBox<Fad> cbDestinationFad;
    private DatePicker dpDato;
    private TextField txfAnsvarlig;
    private Label lblError;

    public OmhældningWindow(Stage owner) {
        initStyle(StageStyle.UTILITY);
        initModality(Modality.APPLICATION_MODAL);
        initOwner(owner);
        setResizable(false);
        setTitle("Registrer omhældning");

        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        initContent(pane);
        setScene(new Scene(pane));
    }

    private void initContent(GridPane pane) {
        cbKildeFad = new ComboBox<>(FXCollections.observableArrayList(Controller.getFade()));
        txfMængde = new TextField();
        txfMængde.setPromptText("fx 50");
        cbDestinationFad = new ComboBox<>(FXCollections.observableArrayList(Controller.getFade()));
        dpDato = new DatePicker(LocalDate.now());
        txfAnsvarlig = new TextField();
        txfAnsvarlig.setPromptText("fx Bo");
        lblError = new Label();
        lblError.setStyle("-fx-text-fill: red");

        ListView<String> lvwBidrag = new ListView<>(visningList);
        lvwBidrag.setPrefHeight(120);

        pane.add(new Label("Kildefad:"), 0, 0);        pane.add(cbKildeFad, 1, 0);
        pane.add(new Label("Mængde (L):"), 0, 1);      pane.add(txfMængde, 1, 1);

        Button btnTilføj = new Button("Tilføj");
        pane.add(btnTilføj, 1, 2);
        GridPane.setHalignment(btnTilføj, HPos.RIGHT);
        btnTilføj.setOnAction(e -> tilføjBidrag());

        pane.add(new Label("Tilføjede bidrag:"), 0, 3);
        pane.add(lvwBidrag, 0, 4, 2, 1);

        pane.add(new Label("Destinationsfad:"), 0, 5); pane.add(cbDestinationFad, 1, 5);
        pane.add(new Label("Dato:"), 0, 6);            pane.add(dpDato, 1, 6);
        pane.add(new Label("Ansvarlig:"), 0, 7);       pane.add(txfAnsvarlig, 1, 7);
        pane.add(lblError, 0, 8, 2, 1);

        Button btnCancel = new Button("Annuller");
        pane.add(btnCancel, 0, 9);
        btnCancel.setOnAction(e -> hide());

        Button btnOK = new Button("Gem");
        pane.add(btnOK, 1, 9);
        GridPane.setHalignment(btnOK, HPos.RIGHT);
        btnOK.setOnAction(e -> okAction());
    }

    private void tilføjBidrag() {
        lblError.setText("");
        Fad kildeFad = cbKildeFad.getSelectionModel().getSelectedItem();
        if (kildeFad == null) { lblError.setText("Vælg et kildefad."); return; }
        if (kildeFad.getPåfyldninger().isEmpty()) {
            lblError.setText("Fadet har ingen påfyldninger."); return;
        }
        double mængde;
        try {
            mængde = Double.parseDouble(txfMængde.getText().trim());
        } catch (NumberFormatException ex) {
            lblError.setText("Mængde skal være et tal."); return;
        }
        if (mængde <= 0) { lblError.setText("Mængde skal være > 0."); return; }

        valgteKildeFade.add(kildeFad);
        valgteMængder.add(mængde);
        visningList.add(String.format("%.1f L fra Fad %d", mængde, kildeFad.getNummer()));
        txfMængde.clear();
    }

    private void okAction() {
        lblError.setText("");
        Fad destinationFad = cbDestinationFad.getSelectionModel().getSelectedItem();
        if (destinationFad == null) { lblError.setText("Vælg et destinationsfad."); return; }
        if (valgteKildeFade.isEmpty()) { lblError.setText("Tilføj mindst ét bidrag."); return; }
        String ansvarlig = txfAnsvarlig.getText().trim();
        if (ansvarlig.isEmpty()) { lblError.setText("Ansvarlig mangler."); return; }

        try {
            Controller.createOmhældning(
                    dpDato.getValue(), ansvarlig, destinationFad,
                    new ArrayList<>(valgteKildeFade),
                    new ArrayList<>(valgteMængder)
            );
            hide();
        } catch (IllegalArgumentException ex) {
            lblError.setText(ex.getMessage());
        }
    }
}