package guifx;

import application.controller.Controller;
import application.controller.Storage;
import application.model.Alkoholtype;
import application.model.Kornsort;
import javafx.application.Application;
import storage.StorageFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;

public class App {
    private static final String STORAGE_FILE = "storage.ser";

    public static void main(String[] args) {
        File file = new File(STORAGE_FILE);
        if (file.exists()) {
            loadStorage(STORAGE_FILE);
        } else {
            Controller.setStorage(new StorageFile());
            initStorage();
        }
        Application.launch(StartWindow.class);
        saveStorage(STORAGE_FILE);
    }

    public static void initStorage() {
        Controller.createDestillat(
                LocalDate.now().minusDays(14),
                LocalDate.now().minusDays(7),
                "MB-12",
                Kornsort.EVERGREEN,
                500.0,
                63.5,
                "Eg",
                "",
                1001,
                1,
                500.0
        );

        Controller.createDestillat(
                LocalDate.now().minusDays(10),
                LocalDate.now().minusDays(3),
                "MB-13",
                Kornsort.IRINA,
                420.0,
                62.0,
                "Torv",
                "",
                1002,
                2,
                420.0
        );

        Controller.createFad(
                1,
                "Islay",
                "Skotland",
                2,
                Alkoholtype.SHERRY,
                200.0
        );

        Controller.createFad(
                2,
                "Speyside",
                "Skotland",
                1,
                Alkoholtype.BOURBON,
                225.0
        );
    }

    public static void saveStorage(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(Controller.getStorage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadStorage(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            Storage storage = (Storage) ois.readObject();
            Controller.setStorage(storage);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            Controller.setStorage(new StorageFile());
            initStorage();
        }
    }
}
