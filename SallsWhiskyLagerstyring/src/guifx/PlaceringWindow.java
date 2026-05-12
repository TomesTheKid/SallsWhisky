package guifx;

import application.controller.Controller;
import application.model.Lager;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PlaceringWindow extends Stage {
    private final Lager lager;
    private TextField txfReol;
    private TextField txfRække;
    private TextField txfHylde;
    private Label lblError;

    public PlaceringWindow(Stage owner, Lager lager) {
        this.lager = lager;
        initStyle(StageStyle.UTILITY);
        initModality(Modality.APPLICATION_MODAL);
        initOwner(owner);
        setResizable(false);
        setTitle("Opret placering i " + lager.getLagerNavn());

        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);

        txfReol = new TextField(); txfReol.setPromptText("fx 1");
        txfRække = new TextField(); txfRække.setPromptText("fx 2");
        txfHylde = new TextField(); txfHylde.setPromptText("fx 3");

        pane.add(new Label("Reol:"), 0, 0); pane.add(txfReol, 1, 0);
        pane.add(new Label("Række:"), 0, 1); pane.add(txfRække, 1, 1);
        pane.add(new Label("Hylde:"), 0, 2); pane.add(txfHylde, 1, 2);

        lblError = new Label();
        lblError.setStyle("-fx-text-fill: red");
        pane.add(lblError, 0, 3, 2, 1);

        Button btnCancel = new Button("Annuller");
        pane.add(btnCancel, 0, 4);
        GridPane.setHalignment(btnCancel, HPos.LEFT);
        btnCancel.setOnAction(e -> hide());

        Button btnGem = new Button("Gem");
        pane.add(btnGem, 1, 4);
        GridPane.setHalignment(btnGem, HPos.RIGHT);
        btnGem.setOnAction(e -> okAction());

        setScene(new Scene(pane));
    }

    private void okAction() {
        try {
            int reol = Integer.parseInt(txfReol.getText().trim());
            int række = Integer.parseInt(txfRække.getText().trim());
            int hylde = Integer.parseInt(txfHylde.getText().trim());
            Controller.createLagerPlacering(lager, reol, række, hylde);
            hide();
        } catch (NumberFormatException ex) {
            lblError.setText("Reol, række og hylde skal være hele tal.");
        } catch (IllegalArgumentException ex) {
            lblError.setText(ex.getMessage());
        }
    }
}