package guifx;

import application.controller.Controller;
import application.model.Fad;
import application.model.Lager;
import application.model.LagerPlacering;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class LagerPane extends GridPane {
    private final Stage owner;

    private final ObservableList<Lager> lagerList = FXCollections.observableArrayList();
    private final ListView<Lager> lvwLagre = new ListView<>(lagerList);

    private final ObservableList<LagerPlacering> placeringList = FXCollections.observableArrayList();
    private final ListView<LagerPlacering> lvwPlaceringer = new ListView<>(placeringList);

    private final TextArea txaOversigt = new TextArea();

    public LagerPane(Stage owner) {
        this.owner = owner;
        setPadding(new Insets(20));
        setHgap(20);
        setVgap(10);

        // Venstre: lagerliste
        add(new Label("Lagre"), 0, 0);
        add(lvwLagre, 0, 1, 1, 2);
        lvwLagre.setPrefWidth(200);
        lvwLagre.setPrefHeight(380);

        // Midten: placeringer i valgt lager
        add(new Label("Placeringer"), 1, 0);
        add(lvwPlaceringer, 1, 1, 1, 2);
        lvwPlaceringer.setPrefWidth(220);
        lvwPlaceringer.setPrefHeight(380);

        // Højre: lager-oversigt (tekstbaseret)
        add(new Label("Lageroversigt"), 2, 0);
        add(txaOversigt, 2, 1, 1, 2);
        txaOversigt.setPrefWidth(380);
        txaOversigt.setPrefHeight(380);
        txaOversigt.setEditable(false);

        // Knapper
        Button btnOpretLager = new Button("Opret lager");
        Button btnOpretPlacering = new Button("Opret placering");
        Button btnTildelFad = new Button("Tildel fad til placering");
        Button btnFlytFad = new Button("Flyt fad");

        HBox hbx = new HBox(15, btnOpretLager, btnOpretPlacering, btnTildelFad, btnFlytFad);
        hbx.setPadding(new Insets(10, 0, 0, 0));
        hbx.setAlignment(Pos.CENTER_LEFT);
        add(hbx, 0, 3, 3, 1);

        btnOpretLager.setOnAction(e -> opretLager());
        btnOpretPlacering.setOnAction(e -> opretPlacering());
        btnTildelFad.setOnAction(e -> tildelFad());
        btnFlytFad.setOnAction(e -> flytFad());

        lvwLagre.getSelectionModel().selectedItemProperty()
                .addListener((obs, o, n) -> lagerValgt());

        refresh();
    }

    public void refresh() {
        Lager valgt = lvwLagre.getSelectionModel().getSelectedItem();
        lagerList.setAll(Controller.getLagre());
        if (valgt != null && lagerList.contains(valgt)) {
            lvwLagre.getSelectionModel().select(valgt);
        } else if (!lagerList.isEmpty()) {
            lvwLagre.getSelectionModel().select(0);
        }
        opdaterPlaceringer();
        opdaterOversigt();
    }

    private void lagerValgt() {
        opdaterPlaceringer();
        opdaterOversigt();
    }

    private void opdaterPlaceringer() {
        Lager lager = lvwLagre.getSelectionModel().getSelectedItem();
        if (lager != null) {
            placeringList.setAll(lager.getLokationer());
        } else {
            placeringList.clear();
        }
    }

    private void opdaterOversigt() {
        StringBuilder sb = new StringBuilder();
        for (Lager lager : Controller.getLagre()) {
            sb.append("=== ").append(lager).append(" ===\n");
            for (LagerPlacering lp : lager.getLokationer()) {
                sb.append("  ").append(lp);
                if (lp.getFad() != null) {
                    sb.append("  →  ").append(lp.getFad());
                } else {
                    sb.append("  →  [Ledig]");
                }
                sb.append("\n");
            }
            sb.append("\n");
        }
        if (sb.isEmpty()) {
            sb.append("Ingen lagre oprettet endnu.");
        }
        txaOversigt.setText(sb.toString());
    }

    // --- Handlinger ---

    private void opretLager() {
        LagerWindow window = new LagerWindow(owner);
        window.showAndWait();
        refresh();
    }

    private void opretPlacering() {
        Lager lager = lvwLagre.getSelectionModel().getSelectedItem();
        if (lager == null) {
            visAlert("Vælg et lager først.");
            return;
        }
        PlaceringWindow window = new PlaceringWindow(owner, lager);
        window.showAndWait();
        refresh();
    }

    private void tildelFad() {
        LagerPlacering lp = lvwPlaceringer.getSelectionModel().getSelectedItem();
        if (lp == null) {
            visAlert("Vælg en placering først.");
            return;
        }
        TildelFadWindow window = new TildelFadWindow(owner, lp, false);
        window.showAndWait();
        refresh();
    }

    private void flytFad() {
        LagerPlacering lp = lvwPlaceringer.getSelectionModel().getSelectedItem();
        if (lp == null || lp.getFad() == null) {
            visAlert("Vælg en placering med et fad.");
            return;
        }
        // Vælg ny placering via samme vindue men med fadet allerede sat
        TildelFadWindow window = new TildelFadWindow(owner, lp, true);
        window.showAndWait();
        refresh();
    }

    private void visAlert(String besked) {
        Alert alert = new Alert(Alert.AlertType.WARNING, besked, ButtonType.OK);
        alert.showAndWait();
    }
}