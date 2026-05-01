package guifx;

import application.controller.Controller;
import application.controller.Storage;
import javafx.application.Application;
import storage.StorageFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
        }
    }
}
