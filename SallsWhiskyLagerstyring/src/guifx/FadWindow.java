package guifx;

import application.controller.Controller;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FadWindow extends Stage {
    private TextField txfNummer;
    private TextField txfLeverandør;
    private TextField txfLand;
    private TextField txfAntalBrug;
    private TextField txfTidligereAlkoholindhold;
    private TextField txfStorrelse;
    private Label lblError;

    public FadWindow(Stage owner) {
        initStyle(StageStyle.UTILITY);
        initModality(Modality.APPLICATION_MODAL);
        initOwner(owner);
        setResizable(false);

        setTitle("Opret fad");
        GridPane pane = new GridPane();
        initContent(pane);
        setScene(new Scene(pane));
    }

    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        txfNummer = new TextField();
        txfLeverandør = new TextField();
        txfLand = new TextField();
        txfAntalBrug = new TextField();
        txfTidligereAlkoholindhold = new TextField();
        txfStorrelse = new TextField();

        txfNummer.setPromptText("fx 1");
        txfLeverandør.setPromptText("fx Islay");
        txfLand.setPromptText("fx Skotland");
        txfAntalBrug.setPromptText("fx 2");
        txfTidligereAlkoholindhold.setPromptText("fx sherry");
        txfStorrelse.setPromptText("fx 200");

        pane.add(new Label("Nummer"), 0, 0);
        pane.add(txfNummer, 0, 1);

        pane.add(new Label("Leverandør"), 0, 2);
        pane.add(txfLeverandør, 0, 3);

        pane.add(new Label("Land"), 0, 4);
        pane.add(txfLand, 0, 5);

        pane.add(new Label("Antal brug"), 0, 6);
        pane.add(txfAntalBrug, 0, 7);

        pane.add(new Label("Tidligere alkoholindhold"), 0, 8);
        pane.add(txfTidligereAlkoholindhold, 0, 9);

        pane.add(new Label("Størrelse (L)"), 0, 10);
        pane.add(txfStorrelse, 0, 11);

        Button btnCancel = new Button("Annuller");
        pane.add(btnCancel, 0, 12);
        GridPane.setHalignment(btnCancel, HPos.LEFT);
        btnCancel.setOnAction(event -> cancelAction());

        Button btnOK = new Button("Gem");
        pane.add(btnOK, 0, 12);
        GridPane.setHalignment(btnOK, HPos.RIGHT);
        btnOK.setOnAction(event -> okAction());

        lblError = new Label();
        pane.add(lblError, 0, 13);
        lblError.setStyle("-fx-text-fill: red");
    }

    private void cancelAction() {
        hide();
    }

    private void okAction() {
        try {
            String nummerText = txfNummer.getText().trim();
            if (nummerText.isEmpty()) {
                lblError.setText("Nummer mangler.");
                return;
            }
            int nummer = Integer.parseInt(nummerText);
            int antalBrug = Integer.parseInt(txfAntalBrug.getText().trim());
            double storrelse = Double.parseDouble(txfStorrelse.getText().trim());
            Controller.createFad(
                    nummer,
                    txfLeverandør.getText().trim(),
                    txfLand.getText().trim(),
                    antalBrug,
                    txfTidligereAlkoholindhold.getText().trim(),
                    storrelse
            );
            hide();
        } catch (Exception ex) {
            lblError.setText("Ugyldig input. Kontrollér talfelter.");
        }
    }
}
