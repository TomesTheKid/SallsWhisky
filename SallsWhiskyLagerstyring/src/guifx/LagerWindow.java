package guifx;

import application.controller.Controller;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LagerWindow extends Stage {
    private TextField txfNavn;
    private TextField txfAdresse;
    private Label lblError;

    public LagerWindow(Stage owner) {
        initStyle(StageStyle.UTILITY);
        initModality(Modality.APPLICATION_MODAL);
        initOwner(owner);
        setResizable(false);
        setTitle("Opret lager");

        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);

        txfNavn = new TextField();
        txfAdresse = new TextField();
        txfNavn.setPromptText("fx Lager A");
        txfAdresse.setPromptText("fx Sallsvej 1, 8600 Silkeborg");

        pane.add(new Label("Lagernavn:"), 0, 0);
        pane.add(txfNavn, 1, 0);
        pane.add(new Label("Adresse:"), 0, 1);
        pane.add(txfAdresse, 1, 1);

        lblError = new Label();
        lblError.setStyle("-fx-text-fill: red");
        pane.add(lblError, 0, 2, 2, 1);

        Button btnCancel = new Button("Annuller");
        pane.add(btnCancel, 0, 3);
        GridPane.setHalignment(btnCancel, HPos.LEFT);
        btnCancel.setOnAction(e -> hide());

        Button btnGem = new Button("Gem");
        pane.add(btnGem, 1, 3);
        GridPane.setHalignment(btnGem, HPos.RIGHT);
        btnGem.setOnAction(e -> okAction());

        setScene(new Scene(pane));
    }

    private void okAction() {
        String navn = txfNavn.getText().trim();
        String adresse = txfAdresse.getText().trim();
        if (navn.isEmpty()) { lblError.setText("Navn mangler."); return; }
        if (adresse.isEmpty()) { lblError.setText("Adresse mangler."); return; }
        try {
            Controller.createLager(navn, adresse);
            hide();
        } catch (IllegalArgumentException ex) {
            lblError.setText(ex.getMessage());
        }
    }
}