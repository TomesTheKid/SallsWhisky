package guifx;

import application.controller.Controller;
import application.model.Fad;
import application.model.Påfyldning;
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

public class FadPane extends GridPane {
    private final Stage owner;
    private final ObservableList<Fad> fadList = FXCollections.observableArrayList();
    private final ListView<Fad> lvwFade = new ListView<>(fadList);
    private final TextArea txaFadDetaljer = new TextArea();

    public FadPane(Stage owner) {
        this.owner = owner;
        setPadding(new Insets(20));
        setHgap(20);
        setVgap(10);
        setGridLinesVisible(false);

        Label lblFade = new Label("Fade");
        add(lblFade, 0, 0);

        add(lvwFade, 0, 1, 1, 2);
        lvwFade.setPrefWidth(260);
        lvwFade.setPrefHeight(420);

        Label lblDetaljer = new Label("Detaljer");
        add(lblDetaljer, 1, 0);

        add(txaFadDetaljer, 1, 1, 1, 2);
        txaFadDetaljer.setPrefWidth(520);
        txaFadDetaljer.setPrefHeight(320);
        txaFadDetaljer.setEditable(false);

        Button btnOpretFad = new Button("Opret fad");
        Button btnPaafylning = new Button("Registrer påfyldning");
        HBox hbxButtons = new HBox(40);
        add(hbxButtons, 0, 3, 2, 1);
        hbxButtons.setPadding(new Insets(10, 0, 0, 0));
        hbxButtons.setAlignment(Pos.BASELINE_CENTER);
        hbxButtons.getChildren().addAll(btnOpretFad, btnPaafylning);
        btnOpretFad.setOnAction(e -> openFadWindow());
        btnPaafylning.setOnAction(e -> openPåfyldningWindow());

        lvwFade.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldV, newV) -> selectedFadChanged());

        refresh();
    }

    public void refresh() {
        fadList.setAll(Controller.getFade());
        if (!fadList.isEmpty() && lvwFade.getSelectionModel().getSelectedItem() == null) {
            lvwFade.getSelectionModel().select(0);
        }
        updateControls();
    }

    private void visFadDetaljer(Fad fad) {
        if (fad == null) {
            txaFadDetaljer.clear();
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Nr: ").append(fad.getNummer())
                .append("\nLeverandør: ").append(fad.getLeverandør() == null || fad.getLeverandør().isBlank() ? "-" : fad.getLeverandør())
                .append("\nLand: ").append(fad.getLand() == null || fad.getLand().isBlank() ? "-" : fad.getLand())
                .append("\nAntal brug: ").append(fad.getAntalBrug())
                .append("\nTidligere alkoholindhold: ")
                .append(fad.getTidligereAlkoholindhold() == null ? "-" : fad.getTidligereAlkoholindhold().name())
                .append("\nStørrelse: ").append(String.format("%.1f", fad.getStørrelse())).append(" L")
                .append("\n\nPåfyldninger:");
        for (Påfyldning p : fad.getPåfyldninger()) {
            sb.append("\n- ").append(p);
        }
        txaFadDetaljer.setText(sb.toString());
    }

    private void selectedFadChanged() {
        updateControls();
    }

    private void updateControls() {
        Fad fad = lvwFade.getSelectionModel().getSelectedItem();
        visFadDetaljer(fad);
    }

    private void openFadWindow() {
        FadWindow window = new FadWindow(owner);
        window.showAndWait();
        refresh();
        if (!fadList.isEmpty()) {
            lvwFade.getSelectionModel().select(fadList.size() - 1);
        }
    }

    private void openPåfyldningWindow() {
        PåfyldningWindow window = new PåfyldningWindow(owner, lvwFade.getSelectionModel().getSelectedItem());
        window.showAndWait();
        refresh();
    }

}
