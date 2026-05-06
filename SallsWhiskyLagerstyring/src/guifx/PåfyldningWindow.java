package guifx;

import application.controller.Controller;
import application.model.Destillat;
import application.model.Fad;
import application.model.VæskeMængde;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;

public class PåfyldningWindow extends Stage {
    private final ObservableList<Fad> fadList = FXCollections.observableArrayList();
    private final ObservableList<Destillat> destillatList = FXCollections.observableArrayList();
    private final ObservableList<VæskeMængde> væskeList = FXCollections.observableArrayList();

    private ComboBox<Fad> cbFad;
    private ComboBox<Destillat> cbDestillat;
    private TextField txfLiter;
    private ListView<VæskeMængde> lvwVæske;

    private DatePicker dpDato;
    private TextField txfAnsvarlig;
    private TextField txfFillNr;
    private Label lblError;

    public PåfyldningWindow(Stage owner, Fad valgtFad) {
        initStyle(StageStyle.UTILITY);
        initModality(Modality.APPLICATION_MODAL);
        initOwner(owner);
        setResizable(false);

        setTitle("Registrer påfyldning");
        GridPane pane = new GridPane();
        initContent(pane, valgtFad);
        setScene(new Scene(pane));
    }

    private void initContent(GridPane pane, Fad valgtFad) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        fadList.setAll(Controller.getFade());
        destillatList.setAll(Controller.getDestillater());

        cbFad = new ComboBox<>(fadList);
        cbDestillat = new ComboBox<>(destillatList);
        txfLiter = new TextField();
        lvwVæske = new ListView<>(væskeList);

        dpDato = new DatePicker(LocalDate.now());
        txfAnsvarlig = new TextField();
        txfFillNr = new TextField();
        lblError = new Label();

        txfLiter.setPromptText("fx 50");
        txfAnsvarlig.setPromptText("fx Malthe");
        txfFillNr.setPromptText("fx 1");

        if (valgtFad != null) {
            cbFad.getSelectionModel().select(valgtFad);
        }

        pane.add(new Label("Fad"), 0, 0);
        pane.add(cbFad, 1, 0, 2, 1);

        pane.add(new Label("Destillat"), 0, 1);
        pane.add(cbDestillat, 1, 1);
        pane.add(new Label("Liter"), 2, 1);
        pane.add(txfLiter, 3, 1);

        Button btnAdd = new Button("Gem");
        pane.add(btnAdd, 3, 2);
        GridPane.setHalignment(btnAdd, HPos.RIGHT);
        btnAdd.setOnAction(e -> addVæskeMængde());

        pane.add(new Label("Tilføjede destillater"), 0, 3);
        pane.add(lvwVæske, 0, 4, 4, 1);
        lvwVæske.setPrefHeight(140);

        pane.add(new Label("Påfyldningsdato"), 0, 5);
        pane.add(dpDato, 1, 5, 3, 1);

        pane.add(new Label("Påfyldt af"), 0, 6);
        pane.add(txfAnsvarlig, 1, 6, 3, 1);

        pane.add(new Label("Fill nr"), 0, 7);
        pane.add(txfFillNr, 1, 7, 3, 1);

        Button btnCancel = new Button("Annuller");
        pane.add(btnCancel, 0, 8);
        btnCancel.setOnAction(event -> cancelAction());

        Button btnOK = new Button("Færdig");
        pane.add(btnOK, 3, 8);
        GridPane.setHalignment(btnOK, HPos.RIGHT);
        btnOK.setOnAction(event -> okAction());

        pane.add(lblError, 0, 9, 4, 1);
        lblError.setStyle("-fx-text-fill: red");
    }

    private void addVæskeMængde() {
        lblError.setText("");
        Destillat destillat = cbDestillat.getSelectionModel().getSelectedItem();
        if (destillat == null) {
            lblError.setText("Vælg et destillat.");
            return;
        }

        double liter;
        try {
            liter = Double.parseDouble(txfLiter.getText().trim());
        } catch (NumberFormatException ex) {
            lblError.setText("Liter skal være et tal.");
            return;
        }
        if (liter <= 0) {
            lblError.setText("Liter skal være > 0.");
            return;
        }

        væskeList.add(new VæskeMængde(liter, destillat));
        txfLiter.clear();
        if (væskeList.size() == 1) {
            cbFad.setDisable(true);
        }
    }

    private void cancelAction() {
        hide();
    }

    private void okAction() {
        lblError.setText("");
        Fad fad = cbFad.getSelectionModel().getSelectedItem();
        if (fad == null) {
            lblError.setText("Vælg et fad.");
            return;
        }
        if (væskeList.isEmpty()) {
            lblError.setText("Tilføj mindst et destillat.");
            return;
        }
        if (dpDato.getValue() == null) {
            lblError.setText("Vælg en dato.");
            return;
        }
        String ansvarlig = txfAnsvarlig.getText().trim();
        if (ansvarlig.isEmpty()) {
            lblError.setText("Påfyldt af mangler.");
            return;
        }

        int fillNr;
        try {
            fillNr = Integer.parseInt(txfFillNr.getText().trim());
        } catch (NumberFormatException ex) {
            lblError.setText("Fill nr skal være et tal.");
            return;
        }

        Controller.createPåfyldning(
                dpDato.getValue(),
                ansvarlig,
                fad,
                fillNr,
                væskeList
        );
        hide();
    }
}
