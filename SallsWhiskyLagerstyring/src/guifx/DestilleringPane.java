package guifx;

import application.controller.Controller;
import application.model.Destillat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class DestilleringPane extends GridPane {
    private final Stage owner;
    private final ObservableList<Destillat> destillatList = FXCollections.observableArrayList();
    private final ListView<Destillat> lvwDestillater = new ListView<>(destillatList);
    private final TextArea txaDestillatDetaljer = new TextArea();

    public DestilleringPane(Stage owner) {
        this.owner = owner;
        setPadding(new Insets(20));
        setHgap(20);
        setVgap(10);
        setGridLinesVisible(false);

        Label lblDestillater = new Label("Destilleringer");
        add(lblDestillater, 0, 0);

        add(lvwDestillater, 0, 1, 1, 2);
        lvwDestillater.setPrefWidth(260);
        lvwDestillater.setPrefHeight(420);

        Label lblDetaljer = new Label("Detaljer");
        add(lblDetaljer, 1, 0);

        add(txaDestillatDetaljer, 1, 1, 1, 2);
        txaDestillatDetaljer.setPrefWidth(520);
        txaDestillatDetaljer.setPrefHeight(320);
        txaDestillatDetaljer.setEditable(false);

        Button btnDestillat = new Button("Opret destillering");
        HBox hbxButtons = new HBox(40);
        add(hbxButtons, 0, 3, 2, 1);
        hbxButtons.setPadding(new Insets(10, 0, 0, 0));
        hbxButtons.setAlignment(Pos.BASELINE_CENTER);
        hbxButtons.getChildren().add(btnDestillat);
        btnDestillat.setOnAction(e -> openDestillatWindow());

        lvwDestillater.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldV, newV) -> selectedDestillatChanged());

        refresh();
    }

    public void refresh() {
        destillatList.setAll(Controller.getDestillater());
        if (!destillatList.isEmpty() && lvwDestillater.getSelectionModel().getSelectedItem() == null) {
            lvwDestillater.getSelectionModel().select(0);
        }
        updateControls();
    }

    private void visDestillatDetaljer(Destillat destillat) {
        if (destillat == null) {
            txaDestillatDetaljer.clear();
            return;
        }
        String text = "ID: " + destillat.getID()
                + "\nNew make nr: " + destillat.getNewMakeNr()
                + "\nPeriode: " + destillat.getStartDato() + " til " + destillat.getSlutDato()
                + "\nMaltbatch: " + destillat.getMaltbatch()
                + "\nKornsort: " + (destillat.getKornsort() == null || destillat.getKornsort().isBlank() ? "-" : destillat.getKornsort())
                + "\nMaengde/rest: " + String.format("%.1f", destillat.getMaengdeVaeske()) + " / "
                + String.format("%.1f", destillat.getAktuelMaengdeVaeske()) + " L"
                + "\nAlkoholprocent: " + String.format("%.1f", destillat.getAlkoholprocent()) + "%"
                + "\nRygemateriale: " + (destillat.getRygemateriale() == null || destillat.getRygemateriale().isBlank() ? "-" : destillat.getRygemateriale())
                + "\nKommentar: " + (destillat.getKommentar() == null || destillat.getKommentar().isBlank() ? "-" : destillat.getKommentar());
        txaDestillatDetaljer.setText(text);
    }

    private void selectedDestillatChanged() {
        updateControls();
    }

    private void updateControls() {
        Destillat destillat = lvwDestillater.getSelectionModel().getSelectedItem();
        visDestillatDetaljer(destillat);
    }

    private void openDestillatWindow() {
        DestillatWindow window = new DestillatWindow(owner);
        window.showAndWait();
        refresh();
        if (!destillatList.isEmpty()) {
            lvwDestillater.getSelectionModel().select(destillatList.size() - 1);
        }
    }
}
