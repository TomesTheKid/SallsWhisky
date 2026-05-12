package guifx;

import application.controller.Controller;
import java.time.LocalDate;

import application.model.Kornsort;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    private ComboBox<Kornsort> cbKornsort;
    private TextField txfMaengde;
    private TextField txfAlkohol;
    private TextField txfRoeg;
    private TextField txfKommentar;
    private TextField txfId;
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
        pane.setHgap(50);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        txfNewMakeNr = new TextField();
        dpStart = new DatePicker(LocalDate.now().minusDays(1));
        dpSlut = new DatePicker(LocalDate.now());
        txfMaltbatch = new TextField();
        cbKornsort = new ComboBox<>(FXCollections.observableArrayList(Kornsort.values()));
        txfMaengde = new TextField();
        txfAlkohol = new TextField();
        txfRoeg = new TextField();
        txfKommentar = new TextField();
        txfId = new TextField();

        txfNewMakeNr.setPromptText("fx 1001");
        txfMaltbatch.setPromptText("fx MB-12");
        cbKornsort.setPromptText("Vælg kornsort");
        txfMaengde.setPromptText("fx 500");
        txfAlkohol.setPromptText("fx 63.5");
        txfId.setPromptText("fx 1");

        pane.add(new Label("New make nr:"), 0, 0);
        pane.add(txfNewMakeNr, 1, 0);

        pane.add(new Label("Startdato:"), 2, 0);
        pane.add(dpStart, 3, 0);

        pane.add(new Label("Slutdato:"), 0, 1);
        pane.add(dpSlut, 1, 1);

        pane.add(new Label("Maltbatch:"), 2, 1);
        pane.add(txfMaltbatch, 3, 1);

        pane.add(new Label("Kornsort:"), 0, 2);
        pane.add(cbKornsort, 1, 2);

        Label lblMaengdeVaeske = new Label("Mængde væske:");
        lblMaengdeVaeske.setMinWidth(120);
        pane.add(lblMaengdeVaeske, 2, 2);
        pane.add(txfMaengde, 3, 2);

        pane.add(new Label("Alkoholprocent:"), 0, 3);
        pane.add(txfAlkohol, 1, 3);

        pane.add(new Label("Rygemateriale:"), 2, 3);
        pane.add(txfRoeg, 3, 3);

        pane.add(new Label("Kommentar:"), 0, 4);
        pane.add(txfKommentar, 1, 4);

        pane.add(new Label("ID:"), 2, 4);
        pane.add(txfId, 3, 4);

        Button btnCancel = new Button("Annuller");
        btnCancel.setPrefWidth(100);
        pane.add(btnCancel, 0, 5);
        GridPane.setHalignment(btnCancel, HPos.LEFT);
        btnCancel.setOnAction(event -> cancelAction());

        Button btnOK = new Button("Gem");
        btnOK.setPrefWidth(100);
        pane.add(btnOK, 3, 5);
        GridPane.setHalignment(btnOK, HPos.RIGHT);
        btnOK.setOnAction(event -> okAction());

        lblError = new Label();
        pane.add(lblError, 0, 6, 4, 1);
        lblError.setStyle("-fx-text-fill: red");
    }

    private void cancelAction() {
        hide();
    }

    private void okAction() {
        lblError.setText("");

        // Tomme felter fanges i GUI
        if (txfNewMakeNr.getText().trim().isEmpty()) { lblError.setText("New make nr mangler."); return; }
        if (txfMaltbatch.getText().trim().isEmpty()) { lblError.setText("Maltbatch mangler."); return; }
        if (cbKornsort.getValue() == null) { lblError.setText("Vælg en kornsort."); return; }
        if (txfMaengde.getText().trim().isEmpty()) { lblError.setText("Mængde væske mangler."); return; }
        if (txfAlkohol.getText().trim().isEmpty()) { lblError.setText("Alkoholprocent mangler."); return; }
        if (txfId.getText().trim().isEmpty()) { lblError.setText("ID mangler."); return; }

        try {
            int newMakeNr = Integer.parseInt(txfNewMakeNr.getText().trim());
            int id = Integer.parseInt(txfId.getText().trim());
            double maengde = Double.parseDouble(txfMaengde.getText().trim());
            double alkohol = Double.parseDouble(txfAlkohol.getText().trim());

            // Forretningslogik-fejl fanges fra controller
            Controller.createDestillat(
                    dpStart.getValue(),
                    dpSlut.getValue(),
                    txfMaltbatch.getText().trim(),
                    cbKornsort.getValue(),
                    maengde,
                    alkohol,
                    txfRoeg.getText().trim(),
                    txfKommentar.getText().trim(),
                    newMakeNr,
                    id,
                    maengde  // aktuel mængde = fuld mængde ved oprettelse
            );
            hide();
        } catch (NumberFormatException ex) {
            lblError.setText("Talfelter skal udfyldes med gyldige tal.");
        } catch (IllegalArgumentException ex) {
            lblError.setText(ex.getMessage());
        }
    }
}