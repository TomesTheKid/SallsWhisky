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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OmhældningWindow extends Stage {
    private final List<Påfyldning> valgtePåfyldninger = new ArrayList<>();
    private final List<Double> valgteMængder = new ArrayList<>();
    private final ObservableList<String> visningList = FXCollections.observableArrayList();

    private ComboBox<Fad> cbKildeFad;
    private ComboBox<Påfyldning> cbKildePåfyldning;
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
        cbKildePåfyldning = new ComboBox<>();
        txfMængde = new TextField();
        txfMængde.setPromptText("fx 50");
        cbDestinationFad = new ComboBox<>(FXCollections.observableArrayList(Controller.getFade()));
        dpDato = new DatePicker(LocalDate.now());
        txfAnsvarlig = new TextField();
        txfAnsvarlig.setPromptText("fx Bo");
        lblError = new Label();
        lblError.setStyle("-fx-text-fill: red");

        cbKildeFad.setOnAction(e -> {
            Fad fad = cbKildeFad.getSelectionModel().getSelectedItem();
            cbKildePåfyldning.setItems(fad != null
                    ? FXCollections.observableArrayList(fad.getPåfyldninger())
                    : FXCollections.observableArrayList());
        });

        ListView<String> lvwBidrag = new ListView<>(visningList);
        lvwBidrag.setPrefHeight(120);

        pane.add(new Label("Kildefad:"), 0, 0);        pane.add(cbKildeFad, 1, 0);
        pane.add(new Label("Påfyldning:"), 0, 1);      pane.add(cbKildePåfyldning, 1, 1);
        pane.add(new Label("Mængde (L):"), 0, 2);      pane.add(txfMængde, 1, 2);

        Button btnTilføj = new Button("Tilføj");
        pane.add(btnTilføj, 1, 3);
        GridPane.setHalignment(btnTilføj, HPos.RIGHT);
        btnTilføj.setOnAction(e -> tilføjBidrag());

        pane.add(new Label("Tilføjede bidrag:"), 0, 4);
        pane.add(lvwBidrag, 0, 5, 2, 1);

        pane.add(new Label("Destinationsfad:"), 0, 6); pane.add(cbDestinationFad, 1, 6);
        pane.add(new Label("Dato:"), 0, 7);            pane.add(dpDato, 1, 7);
        pane.add(new Label("Ansvarlig:"), 0, 8);       pane.add(txfAnsvarlig, 1, 8);
        pane.add(lblError, 0, 9, 2, 1);

        Button btnCancel = new Button("Annuller");
        pane.add(btnCancel, 0, 10);
        btnCancel.setOnAction(e -> hide());

        Button btnOK = new Button("Gem");
        pane.add(btnOK, 1, 10);
        GridPane.setHalignment(btnOK, HPos.RIGHT);
        btnOK.setOnAction(e -> okAction());
    }

    private void tilføjBidrag() {
        lblError.setText("");
        Påfyldning påfyldning = cbKildePåfyldning.getSelectionModel().getSelectedItem();
        if (påfyldning == null) { lblError.setText("Vælg en påfyldning."); return; }
        double mængde;
        try {
            mængde = Double.parseDouble(txfMængde.getText().trim());
        } catch (NumberFormatException ex) { lblError.setText("Mængde skal være et tal."); return; }
        if (mængde <= 0) { lblError.setText("Mængde skal være > 0."); return; }

        valgtePåfyldninger.add(påfyldning);
        valgteMængder.add(mængde);
        visningList.add(String.format("%.1f L fra %s", mængde, påfyldning));
        txfMængde.clear();
    }

    private void okAction() {
        lblError.setText("");
        Fad destinationFad = cbDestinationFad.getSelectionModel().getSelectedItem();
        if (destinationFad == null) { lblError.setText("Vælg et destinationsfad."); return; }
        if (valgtePåfyldninger.isEmpty()) { lblError.setText("Tilføj mindst ét bidrag."); return; }
        String ansvarlig = txfAnsvarlig.getText().trim();
        if (ansvarlig.isEmpty()) { lblError.setText("Ansvarlig mangler."); return; }

        try {
            Controller.createOmhældning(
                    dpDato.getValue(), ansvarlig, destinationFad,
                    new ArrayList<>(valgtePåfyldninger),
                    new ArrayList<>(valgteMængder)
            );
            hide();
        } catch (IllegalArgumentException ex) {
            lblError.setText(ex.getMessage());
        }
    }
}