package guifx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class StartWindow extends Application {
    private Stage stage;
    private DestilleringPane destilleringPane;
    private Pane lagerPane;
    private FadPane fadPane;
    private Pane whiskyPane;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        this.lagerPane = new Pane();
        this.destilleringPane = new DestilleringPane(stage);
        this.fadPane = new FadPane(stage);
        this.whiskyPane = new Pane();
        initContent();
        stage.show();
    }

    private void initContent() {
        stage.setTitle("Sall Whiskey - Lagerstyring");

        TabPane tabs = new TabPane();
        Tab tabLager = new Tab("Lager", lagerPane);
        Tab tabDestilleringer = new Tab("Destilleringer", destilleringPane);
        Tab tabFade = new Tab("Fade", fadPane);
        Tab tabWhisky = new Tab("Whisky produkter", whiskyPane);
        tabLager.setClosable(false);
        tabDestilleringer.setClosable(false);
        tabFade.setClosable(false);
        tabWhisky.setClosable(false);
        tabs.getTabs().addAll(tabLager, tabDestilleringer, tabFade, tabWhisky);

        stage.setScene(new Scene(tabs, 1000, 720));
    }

}
