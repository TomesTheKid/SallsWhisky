package guifx;

import application.controller.Controller;
import application.model.Fadbidrag;
import application.model.WhiskyProdukt;
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

public class WhiskyPane extends GridPane {
    private final Stage owner;
    private final ObservableList<WhiskyProdukt> whiskyList = FXCollections.observableArrayList();
    private final ListView<WhiskyProdukt> lvwWhisky = new ListView<>(whiskyList);
    private final TextArea txaWhiskyDetaljer = new TextArea();
    private Button btnRapport;

    public WhiskyPane(Stage owner) {
        this.owner = owner;
        setPadding(new Insets(20));
        setHgap(20);
        setVgap(10);
        setGridLinesVisible(false);

        Label lblWhisky = new Label("Whisky produkter");
        add(lblWhisky, 0, 0);

        add(lvwWhisky, 0, 1, 1, 2);
        lvwWhisky.setPrefWidth(260);
        lvwWhisky.setPrefHeight(420);

        Label lblDetaljer = new Label("Detaljer");
        add(lblDetaljer, 1, 0);

        add(txaWhiskyDetaljer, 1, 1, 1, 2);
        txaWhiskyDetaljer.setPrefWidth(520);
        txaWhiskyDetaljer.setPrefHeight(320);
        txaWhiskyDetaljer.setEditable(false);

        Button btnOpret = new Button("Opret whisky produkt");
        btnRapport = new Button("Vis historisk rapport");
        HBox hbxButtons = new HBox(40);
        add(hbxButtons, 0, 3, 2, 1);
        hbxButtons.setPadding(new Insets(10, 0, 0, 0));
        hbxButtons.setAlignment(Pos.BASELINE_CENTER);
        hbxButtons.getChildren().addAll(btnOpret, btnRapport);
        btnOpret.setOnAction(e -> openWhiskyWindow());
        btnRapport.setOnAction(e -> printRapport());

        lvwWhisky.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldV, newV) -> updateControls());

        refresh();
    }

    public void refresh() {
        whiskyList.setAll(Controller.getWhiskyProdukter());
        if (!whiskyList.isEmpty() && lvwWhisky.getSelectionModel().getSelectedItem() == null) {
            lvwWhisky.getSelectionModel().select(0);
        }
        updateControls();
    }

    private void updateControls() {
        WhiskyProdukt wp = lvwWhisky.getSelectionModel().getSelectedItem();
        visWhiskyDetaljer(wp);
        btnRapport.setDisable(wp == null);
    }

    private void visWhiskyDetaljer(WhiskyProdukt wp) {
        if (wp == null) {
            txaWhiskyDetaljer.clear();
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Batch ID:         ").append(wp.getBatchID()).append("\n");
        sb.append("Nummer:           ").append(wp.getNummer()).append("\n");
        sb.append("Årstal:           ").append(wp.getÅrstal()).append("\n");
        sb.append("Kvalitetsstempel: ").append(wp.getKvalitetsstempel()).append("\n");
        sb.append("Alkoholprocent:   ").append(String.format("%.1f", wp.getAlkoholprocent())).append("%\n");
        sb.append("Størrelse:        ").append(String.format("%.1f", wp.getStørrelse())).append(" L\n");
        sb.append("Vandkilde:        ").append(wp.getVandkilde()).append("\n");
        sb.append("Vandmængde:       ").append(String.format("%.1f", wp.getVandMængde())).append(" L\n");
        sb.append("Beskrivelse:      ").append(wp.getBeskrivelse()).append("\n");
        sb.append("\nFadbidrags (").append(wp.getFadbidrags().size()).append("):");
        for (Fadbidrag fb : wp.getFadbidrags()) {
            sb.append("\n  - ").append(String.format("%.1f", fb.getMængdeLiter())).append(" L");
            if (fb.getPåfyldning() != null && fb.getPåfyldning().getFad() != null) {
                sb.append(" fra fad nr. ").append(fb.getPåfyldning().getFad().getNummer());
            }
        }
        txaWhiskyDetaljer.setText(sb.toString());
    }

    private void openWhiskyWindow() {
        WhiskyProduktWindow window = new WhiskyProduktWindow(owner);
        window.showAndWait();
        refresh();
        if (!whiskyList.isEmpty()) {
            lvwWhisky.getSelectionModel().select(whiskyList.size() - 1);
        }
    }

    private void printRapport() {
        WhiskyProdukt wp = lvwWhisky.getSelectionModel().getSelectedItem();
        if(wp != null){
            txaWhiskyDetaljer.setText(wp.genererHistoriskRapport());
        }
    }
}