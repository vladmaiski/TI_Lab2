package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.stage.Window;
import model.Encryptor;
import utils.BitArray;
import utils.LFSR;

import java.io.*;
import java.nio.file.Files;

public class Controller extends Application {

    @FXML
    private TextField keyArea;

    @FXML
    private TextArea cipherText;

    @FXML
    private TextArea plainText;


    private Window window;

    @FXML
    void setupHint() {

    }


    @FXML
    void openToEncrypt() {
        loadFile(true);
    }

    @FXML
    void openToDecrypt() {
        loadFile(false);
    }


    @FXML
    void saveCipherText() {
        saveFile(false);
    }

    @FXML
    void savePlainText() {
        saveFile(true);
    }

    @FXML
    void stopProgram() {
        System.exit(0);
    }


    @FXML
    void encrypt() {
        if (!isFieldsCorrect(true)) return;
        Encryptor encryptor = new Encryptor();
        BitArray result = encryptor.encrypt(new BitArray(keyArea.getText()), new BitArray(plainText.getText()));
        String resText = new StringBuilder(result.toString()).reverse().toString();
        cipherText.setText(resText);
    }

    @FXML
    void decrypt() {
        if (!isFieldsCorrect(false)) return;
        Encryptor encryptor = new Encryptor();
        BitArray result = encryptor.encrypt(new BitArray(keyArea.getText()), new BitArray(cipherText.getText()));
        String resText = new StringBuilder(result.toString()).reverse().toString();
        plainText.setText(resText);
    }

    void saveFile(boolean savePlainText) {
        try {
            File file = chooseFile(true);
            if (file != null) {
                if (savePlainText)
                    Files.write(file.toPath(), BitArray.toBytes(plainText.getText()));
                else
                    Files.write(file.toPath(), BitArray.toBytes(cipherText.getText()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void loadFile(boolean toEncrypt) {
        clearTextAreas();
        try {
            File file = chooseFile(false);
            if (file != null) {
                String fileBytes = new BitArray(Files.readAllBytes(file.toPath())).toString();
                fileBytes = new StringBuilder(fileBytes).reverse().toString();
                if (toEncrypt) {
                    plainText.setText(fileBytes);
                }
                else {
                    cipherText.setText(fileBytes);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearTextAreas() {
        cipherText.clear();
        plainText.clear();
    }

    private boolean isFieldsCorrect(boolean encrypt)
    {
        TextArea textArea = cipherText;
        String areaName = "cipher text area";
        if (encrypt) {
            textArea = plainText;
            areaName = "plain text area";
        }

        if (textArea.getText().length() == 0 && keyArea.getText().length() == 0)
            return false;

        if (keyArea.getText().length() != Encryptor.getKeySize()) {
            showAlert("Incorrect key", "Incorrect amount of symbols in key, It should be " + Encryptor.getKeySize());
            return false;
        }

        if (textArea.getText().length() == 0) {
            showAlert("Error", "Input field is empty.");
            return false;
        }

        if (!BitArray.checkBinaryStr(keyArea.getText())) {
            showAlert("Incorrect key", "Key should consists of 0 and 1");
            return false;
        }

        if (!BitArray.checkBinaryStr(textArea.getText())) {
            showAlert("Incorrect text", "Error in " + areaName + ". Text should consists of 0 and 1");
            return false;
        }

        return true;
    }

    public void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("application.fxml"));
        primaryStage.setTitle("Encryptor");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        window = primaryStage.getOwner();
        primaryStage.show();

    }

    public File chooseFile(boolean toSave) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("res/"));
        File file;
        if (toSave)
            file = fileChooser.showSaveDialog(window);
        else
            file = fileChooser.showOpenDialog(window);

        return file;
    }

    public static void main(String[] args) {
        final int[] toXor = {27, 8, 7, 1};
        LFSR lfsr = new LFSR(new BitArray("111111111111111111111111111"), toXor);
        for (int i = 0; i < 56; i++) {
            lfsr.getNext();
        }
        launch(args);
    }
}
