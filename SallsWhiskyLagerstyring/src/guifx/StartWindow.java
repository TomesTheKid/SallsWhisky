package guifx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class StartWindow extends Application {
    private Stage stage;
    private DestilleringPane destilleringPane;
    private LagerPane lagerPane;
    private FadPane fadPane;
    private WhiskyPane whiskyPane;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        this.lagerPane = new LagerPane(stage);
        this.destilleringPane = new DestilleringPane(stage);
        this.fadPane = new FadPane(stage);
        this.whiskyPane = new WhiskyPane(stage);
        initContent();
        stage.show();
    }

    private void initContent() {
        stage.setTitle("Sall Whisky - Lagerstyring");

        // Logo header
        ImageView logo = new ImageView(new Image(getClass().getResourceAsStream("sallwhiskylogo.png")));
        logo.setFitHeight(40);
        logo.setPreserveRatio(true);

        HBox header = new HBox(logo);
        header.setStyle("-fx-background-color: #f5f0eb; -fx-padding: 10;");
        header.setAlignment(Pos.CENTER);

        // Tabs
        TabPane tabs = new TabPane();
        Tab tabLager        = new Tab("Lager", lagerPane);
        Tab tabDestillering = new Tab("Destilleringer", destilleringPane);
        Tab tabFade         = new Tab("Fade", fadPane);
        Tab tabWhisky       = new Tab("Whisky produkter", whiskyPane);
        tabLager.setClosable(false);
        tabDestillering.setClosable(false);
        tabFade.setClosable(false);
        tabWhisky.setClosable(false);
        tabs.getTabs().addAll(tabLager, tabDestillering, tabFade, tabWhisky);

        VBox root = new VBox(header, tabs);
        VBox.setVgrow(tabs, Priority.ALWAYS);

        Scene scene = new Scene(root, 1000, 760);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
    }
}