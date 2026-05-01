package guifx;

import application.controller.Controller;
import java.time.LocalDate;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DestillatWindow extends Stage {
    private TextField txfNewMakeNr;
    private DatePicker dpStart;
    private DatePicker dpSlut;
    private TextField txfMaltbatch;
    private TextField txfKornsort;
    private TextField txfMaengde;
    private TextField txfAlkohol;
    private TextField txfRoeg;
    private TextField txfKommentar;
    private TextField txfId;
    private TextField txfAktuelMaengde;
    private Label lblError;

    public DestillatWindow(Stage owner) {
        initStyle(StageStyle.UTILITY);
        initModality(Modality.APPLICATION_MODAL);
        initOwner(owner);
        setResizable(false);

        setTitle("Opret destillering");
        GridPane pane = new GridPane();
        initContent(pane);
        setScene(new Scene(pane));
    }

    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        txfNewMakeNr = new TextField();
        dpStart = new DatePicker(LocalDate.now().minusDays(1));
        dpSlut = new DatePicker(LocalDate.now());
        txfMaltbatch = new TextField();
        txfKornsort = new TextField();
        txfMaengde = new TextField();
        txfAlkohol = new TextField();
        txfRoeg = new TextField();
        txfKommentar = new TextField();
        txfId = new TextField();
        txfAktuelMaengde = new TextField();

        txfNewMakeNr.setPromptText("fx 1001");
        txfMaltbatch.setPromptText("fx MB-12");
        txfKornsort.setPromptText("fx byg");
        txfMaengde.setPromptText("fx 500");
        txfAlkohol.setPromptText("fx 63.5");
        txfId.setPromptText("fx 1");
        txfAktuelMaengde.setPromptText("fx 500");

        pane.add(new Label("New make nr"), 0, 0);
        pane.add(txfNewMakeNr, 0, 1);

        pane.add(new Label("Startdato"), 0, 2);
        pane.add(dpStart, 0, 3);

        pane.add(new Label("Slutdato"), 0, 4);
        pane.add(dpSlut, 0, 5);

        pane.add(new Label("Maltbatch"), 0, 6);
        pane.add(txfMaltbatch, 0, 7);

        pane.add(new Label("Kornsort"), 0, 8);
        pane.add(txfKornsort, 0, 9);

        pane.add(new Label("Maengde vaeske"), 0, 10);
        pane.add(txfMaengde, 0, 11);

        pane.add(new Label("Alkoholprocent"), 0, 12);
        pane.add(txfAlkohol, 0, 13);

        pane.add(new Label("Roegemateriale"), 0, 14);
        pane.add(txfRoeg, 0, 15);

        pane.add(new Label("Kommentar"), 0, 16);
        pane.add(txfKommentar, 0, 17);

        pane.add(new Label("ID"), 0, 18);
        pane.add(txfId, 0, 19);

        pane.add(new Label("Aktuel maengde"), 0, 20);
        pane.add(txfAktuelMaengde, 0, 21);

        Button btnCancel = new Button("Annuller");
        pane.add(btnCancel, 0, 22);
        GridPane.setHalignment(btnCancel, HPos.LEFT);
        btnCancel.setOnAction(event -> cancelAction());

        Button btnOK = new Button("Gem");
        pane.add(btnOK, 0, 22);
        GridPane.setHalignment(btnOK, HPos.RIGHT);
        btnOK.setOnAction(event -> okAction());

        lblError = new Label();
        pane.add(lblError, 0, 23);
        lblError.setStyle("-fx-text-fill: red");
    }

    private void cancelAction() {
        hide();
    }

    private void okAction() {
        try {
            String newMakeText = txfNewMakeNr.getText().trim();
            if (newMakeText.isEmpty()) {
                lblError.setText("New make nr mangler.");
                return;
            }
            int newMakeNr = Integer.parseInt(newMakeText);
            int id = Integer.parseInt(txfId.getText().trim());
            double aktuelMaengde = Double.parseDouble(txfAktuelMaengde.getText().trim());

            Controller.createDestillat(
                    dpStart.getValue(),
                    dpSlut.getValue(),
                    txfMaltbatch.getText().trim(),
                    txfKornsort.getText().trim(),
                    Double.parseDouble(txfMaengde.getText().trim()),
                    Double.parseDouble(txfAlkohol.getText().trim()),
                    txfRoeg.getText().trim(),
                    txfKommentar.getText().trim(),
                    newMakeNr,
                    id,
                    aktuelMaengde
            );
            hide();
        } catch (Exception ex) {
            lblError.setText("Ugyldig input. Kontrollér datoer og talfelter.");
        }
    }
}