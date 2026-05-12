package guifx;

import application.controller.Controller;
import application.model.Fad;
import application.model.Lager;
import application.model.LagerPlacering;
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

public class TildelFadWindow extends Stage {
    private final LagerPlacering fraPlacering;
    private final boolean erFlytning;

    private ComboBox<Fad> cbFad;
    private ComboBox<Lager> cbLager;
    private ComboBox<LagerPlacering> cbNyPlacering;
    private Label lblError;

    /**
     * @param fraPlacering  Den valgte placering (tildel: tom; flyt: har fad)
     * @param erFlytning    true = flyt fad til ny placering, false = tildel fad til placering
     */
    public TildelFadWindow(Stage owner, LagerPlacering fraPlacering, boolean erFlytning) {
        this.fraPlacering = fraPlacering;
        this.erFlytning = erFlytning;

        initStyle(StageStyle.UTILITY);
        initModality(Modality.APPLICATION_MODAL);
        initOwner(owner);
        setResizable(false);
        setTitle(erFlytning ? "Flyt fad" : "Tildel fad til placering");

        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);

        lblError = new Label();
        lblError.setStyle("-fx-text-fill: red");

        int row = 0;

        if (!erFlytning) {
            // Tildel: vælg fad til den allerede valgte (tomme) placering
            pane.add(new Label("Placering:"), 0, row);
            pane.add(new Label(fraPlacering.toString()), 1, row++);

            pane.add(new Label("Vælg fad:"), 0, row);
            cbFad = new ComboBox<>(FXCollections.observableArrayList(Controller.getFade()));
            pane.add(cbFad, 1, row++);
        } else {
            // Flyt: fadet er fastsat – vælg ny lagerplacering
            Fad fad = fraPlacering.getFad();
            pane.add(new Label("Fad:"), 0, row);
            pane.add(new Label(fad.toString()), 1, row++);

            pane.add(new Label("Vælg lager:"), 0, row);
            cbLager = new ComboBox<>(FXCollections.observableArrayList(Controller.getLagre()));
            pane.add(cbLager, 1, row++);

            pane.add(new Label("Vælg ny placering:"), 0, row);
            ObservableList<LagerPlacering> placeringer = FXCollections.observableArrayList();
            cbNyPlacering = new ComboBox<>(placeringer);
            pane.add(cbNyPlacering, 1, row++);

            cbLager.setOnAction(e -> {
                Lager valgtLager = cbLager.getSelectionModel().getSelectedItem();
                placeringer.clear();
                if (valgtLager != null) {
                    // Vis kun ledige placeringer (eller den nuværende)
                    for (LagerPlacering lp : valgtLager.getLokationer()) {
                        if (!lp.erOptaget() || lp == fraPlacering) {
                            placeringer.add(lp);
                        }
                    }
                }
            });
        }

        pane.add(lblError, 0, row++, 2, 1);

        Button btnCancel = new Button("Annuller");
        pane.add(btnCancel, 0, row);
        GridPane.setHalignment(btnCancel, HPos.LEFT);
        btnCancel.setOnAction(e -> hide());

        Button btnGem = new Button("Gem");
        pane.add(btnGem, 1, row);
        GridPane.setHalignment(btnGem, HPos.RIGHT);
        btnGem.setOnAction(e -> okAction());

        setScene(new Scene(pane));
    }

    private void okAction() {
        try {
            if (!erFlytning) {
                Fad fad = cbFad.getSelectionModel().getSelectedItem();
                if (fad == null) { lblError.setText("Vælg et fad."); return; }
                Controller.tildelFadTilPlacering(fraPlacering, fad);
            } else {
                LagerPlacering nyPlacering = cbNyPlacering.getSelectionModel().getSelectedItem();
                if (nyPlacering == null) { lblError.setText("Vælg en ny placering."); return; }
                Controller.flytFad(fraPlacering.getFad(), nyPlacering);
            }
            hide();
        } catch (IllegalArgumentException ex) {
            lblError.setText(ex.getMessage());
        }
    }
}