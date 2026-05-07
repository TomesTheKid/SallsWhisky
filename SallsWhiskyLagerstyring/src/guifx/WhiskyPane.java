package guifx;

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
    private final ObservableList<String> whiskyList = FXCollections.observableArrayList();
    private final ListView<String> lvwWhisky = new ListView<>(whiskyList);
    private final TextArea txaWhiskyDetaljer = new TextArea();

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
        HBox hbxButtons = new HBox(40);
        add(hbxButtons, 0, 3, 2, 1);
        hbxButtons.setPadding(new Insets(10, 0, 0, 0));
        hbxButtons.setAlignment(Pos.BASELINE_CENTER);
        hbxButtons.getChildren().add(btnOpret);
        btnOpret.setOnAction(e -> openWhiskyWindow());

        lvwWhisky.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldV, newV) -> selectedWhiskyChanged());

        refresh();
    }

    public void refresh() {
        if (!whiskyList.isEmpty() && lvwWhisky.getSelectionModel().getSelectedItem() == null) {
            lvwWhisky.getSelectionModel().select(0);
        }
        updateControls();
    }

    private void visWhiskyDetaljer(String whisky) {
        txaWhiskyDetaljer.clear();
    }

    private void selectedWhiskyChanged() {
        updateControls();
    }

    private void updateControls() {
        String whisky = lvwWhisky.getSelectionModel().getSelectedItem();
        visWhiskyDetaljer(whisky);
    }

    private void openWhiskyWindow() {
        // Pladsholder til senere når WhiekyProdukt og Fadbirdrag bliver implementeret
    }
}
