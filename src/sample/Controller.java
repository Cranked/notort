package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.model.Note;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    Note note = new Note();
    @FXML
    private TextField note1;
    @FXML
    private TextField note2;
    @FXML
    private TextField note3;
    @FXML
    private Label resultLabel;
    @FXML
    private Label resultLabelText;
    @FXML
    private Button calculateButton;

    @FXML
    private Button exitButton;

    @FXML
    private ResourceBundle resourceBundle;

    public TextField getNote1() {
        return note1;
    }

    public TextField getNote2() {
        return note2;
    }

    public TextField getNote3() {
        return note3;
    }

    public Label getResultLabel() {
        return resultLabel;
    }

    public Button getCalculateButton() {
        return calculateButton;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init();
        setTextFieldMaxCharacter(getNote1(), 3);
        setTextFieldMaxCharacter(getNote2(), 3);
        setTextFieldMaxCharacter(getNote3(), 3);
        setTextField(getNote1(), "\\d*");
        setTextField(getNote2(), "\\d*");
        setTextField(getNote3(), "\\d*");
    }


    @FXML
    public void exit() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void init() {
        resultLabelText.setText(getStringFromResources("result"));
        calculateButton.setText(getStringFromResources("calculate"));
        exitButton.setText(getStringFromResources("exit"));
        getNote1().setPromptText(getStringFromResources("firstNote"));
        getNote2().setPromptText(getStringFromResources("secondNote"));
        getNote3().setPromptText(getStringFromResources("thirdNote"));
    }

    public double calculate(int... numbers) {
        int toplam = 0;
        double sonuc;
        for (int i : numbers) {
            toplam += i;
        }
        sonuc = Double.parseDouble(String.valueOf(toplam)) / numbers.length;
        return sonuc;
    }

    @FXML
    public void showResult() {
        if (getNote1().getText().isEmpty() || getNote2().getText().isEmpty() || getNote3().getText().isEmpty()) {
            getDialog(getStringFromResources("mustNotNull"), null, Alert.AlertType.ERROR).show();
            return;
        }
        int sayi1 = Integer.parseInt(getNote1().getText());
        int sayi2 = Integer.parseInt(getNote2().getText());
        int sayi3 = Integer.parseInt(getNote3().getText());
        if (!controlNumberRange(sayi1, note.getStart(), note.getFinish())) {
            getDialog(getWarninMessage(sayi1,note.getStart(),note.getFinish(),Locale.getDefault().getLanguage()), null, Alert.AlertType.WARNING).show();
            getNote1().requestFocus();
            return;
        }
        if (!controlNumberRange(sayi2, note.getStart(), note.getFinish())) {
            getDialog(getWarninMessage(sayi2,note.getStart(),note.getFinish(),Locale.getDefault().getLanguage()), null, Alert.AlertType.WARNING).show();
            getNote2().requestFocus();
            return;
        }
        if (!controlNumberRange(sayi3, note.getStart(), note.getFinish())) {
            getDialog(getWarninMessage(sayi3,note.getStart(),note.getFinish(),Locale.getDefault().getLanguage()), null, Alert.AlertType.WARNING).show();
            getNote3().requestFocus();
            return;
        }
        double result = calculate(sayi1, sayi2, sayi3);
        getResultLabel().setText(String.valueOf(String.format("%.2f", result)));
    }

    public String getWarninMessage(int number, int start, int finish, String language) {

        switch (language) {
            case "tr":
                return number + " sayısı  " + start + " ile " + finish + " arasında olmalıdır.";
            case "en":
                return "The number " + number + " must be between " + start + " and " + finish;
        }
        return "";
    }

    public boolean controlNumberRange(int number, int start, int finish) {
        return !(number < start || number > finish);
    }

    public Alert getDialog(String message, String headerText, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(getStringFromResources("warning"));
        alert.setHeaderText(headerText);
        alert.setContentText(message);
        return alert;
    }

    public void setTextFieldMaxCharacter(TextField textField, int maxCharacter) {
        textField.setOnKeyTyped(keyEvent -> {
            if (textField.getText().length() > maxCharacter) {
                int pos = textField.getCaretPosition();
                textField.setText(textField.getText(0, maxCharacter));
                textField.positionCaret(pos);
            }
        });
    }

    public void setTextField(TextField textField, String regex) {
        textField.setTextFormatter(new TextFormatter<>(c -> {
            if (!c.getControlNewText().matches(regex))
                return null;
            else
                return c;
        }
        ));
    }

    public String getStringFromResources(String key) {
        return ResourceBundle.getBundle("sample/strings", Locale.getDefault()).getString(key);
    }

}
