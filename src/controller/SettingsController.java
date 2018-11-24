package controller;

import app.Settings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.CurrencySymbol;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * Controller for settings
 */
public class SettingsController {
    /**
     * Date format item for the date format ComboBox
     */
    public class DateTimeFormatItem
    {
        /**
         * Date time formatter matching the set date format
         */
        private DateTimeFormatter dateTimeFormatter;

        /**
         * The internal yyyy-MM-dd like representation
         */
        private String format;

        /**
         * Displayed date
         */
        private LocalDateTime displayDate;

        /**
         * @param format The internal yyyy-MM-dd like representation
         * @param displayDate Displayed date
         */
        public DateTimeFormatItem(String format, LocalDateTime displayDate) {
            this.format = format;
            this.displayDate = displayDate;
            dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        }


        @Override
        public String toString() {
            return dateTimeFormatter.format(displayDate);
        }

        /**
         * @return The internal yyyy-MM-dd like representation
         */
        public String getFormat() {
            return format;
        }
    }

    @FXML
    private ComboBox dateTimeFormatComboBox;
    @FXML
    private ComboBox displayCurrencyComboBox;
    @FXML
    private CheckBox alwaysShowSymbolOnRightCheckBox;
    @FXML
    private CheckBox useAPICheckBox;
    @FXML
    private CheckBox useEnvVarCheckBox;
    @FXML
    private TextField apiKeyField;
    @FXML
    private Label apiErrorLabel;
    @FXML
    private Button submitButton;

    /**
     * ISO date formats (for each country which uses mildly sane date formats)
     */
    private static final String[] dateFormats = new String[] {
            "yyyy-MM-dd","dd/MM/yyyy","dd/MM/yyyy","d/MM/yyyy","dd.MM.yyyy", "d/MM/yyyy","d/MM/yyyy","yyyy-M-d","dd/MM/yyyy","yyyy-MM-dd","d.M.yyyy","dd-MM-yyyy",
            "dd/MM/yyyy","yyyy-MM-dd","dd/MM/yyyy","dd.MM.yyyy","dd.MM.yyyy","dd.MM.yyyy", "dd-MM-yyyy","yyyy-M-d","d/MM/yyyy","dd/MM/yyyy","dd/MM/yyyy",
            "d.M.yyyy","dd.MM.yyyy","dd-MM-yyyy","MM/dd/yyyy","dd/MM/yyyy","dd/MM/yyyy", "dd/MM/yyyy","d/MM/yyyy","dd/MM/yyyy","d.MM.yyyy","d.M.yyyy","dd/MM/yyyy",
            "dd/MM/yyyy","d/M/yyyy","d/MM/yyyy","MM-dd-yyyy","dd.MM.yyyy.","yyyy.MM.dd.", "dd/MM/yyyy","d/M/yyyy","dd/MM/yyyy","dd/MM/yyyy","dd/MM/yyyy","d.M.yyyy",
            "dd/MM/yyyy","dd/MM/yyyy","dd/MM/yyyy","yyyy/MM/dd", "yyyy. M. d","dd/MM/yyyy","dd/MM/yyyy","dd/MM/yyyy","yyyy.M.d","dd/MM/yyyy","dd.MM.yyyy","yyyy.d.M",
            "dd/MM/yyyy","d/MM/yyyy","d.M.yyyy", "dd/MM/yyyy","dd/MM/yyyy","d.M.yyyy.","dd/MM/yyyy","MM-dd-yyyy","d-M-yyyy","dd.MM.yyyy","dd.MM.yyyy","d/MM/yyyy",
            "dd/MM/yyyy","MM/dd/yyyy","dd/MM/yyyy","M/d/yyyy","dd.MM.yyyy","MM-dd-yyyy","dd-MM-yyyy","dd/MM/yyyy", "dd/MM/yyyy","dd.MM.yyyy","dd.MM.yyyy","dd/MM/yyyy",
            "d.M.yyyy.","dd/MM/yyyy","dd/MM/yyyy","M/d/yyyy", "MM-dd-yyyy","d.M.yyyy.","d.M.yyyy","d.M.yyyy","yyyy-MM-dd","dd/MM/yyyy","dd/MM/yyyy","dd.MM.yyyy","yyyy/M/d",
            "dd.MM.yyyy","dd/MM/yyyy","M/d/yyyy", "M/d/yyyy","dd/MM/yyyy","dd/MM/yyyy","dd/MM/yyyy","yyyy/MM/dd"};

    private boolean changedAPI = false;

    @FXML
    public void initialize()
    {
        LocalDateTime now = LocalDateTime.now();
        for (String dateFormat : dateFormats)
        {
            String dateTimeFormat = dateFormat + " H:mm[:ss]";
            DateTimeFormatItem dateTimeFormatItem = new DateTimeFormatItem(dateTimeFormat, now);
            dateTimeFormatComboBox.getItems().add(dateTimeFormatItem);
        }
        displayCurrencyComboBox.getItems().setAll(Arrays.asList(CurrencySymbol.currencies));

        apiErrorLabel.setManaged(false);
        apiErrorLabel.setVisible(false);

        useAPICheckBox.selectedProperty().addListener((observable, oldValue, newValue) ->
        {
            changedAPI = true;
            if (newValue)
            {
                useEnvVarCheckBox.setDisable(false);
                setUseEnvironmentVariable(useEnvVarCheckBox.isSelected());
            }
            else
            {
                useEnvVarCheckBox.setDisable(true);
                apiKeyField.setDisable(true);

                apiErrorLabel.setManaged(false);
                apiErrorLabel.setVisible(false);
            }

            validateAPI();
        });

        useEnvVarCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            changedAPI = true;
            setUseEnvironmentVariable(newValue);
            validateAPI();
        });

        apiKeyField.textProperty().addListener((observable, oldValue, newValue) ->
        {
            changedAPI = true;
            validateAPI();
        });

        setupFields();
    }

    /**
     * Sets up the fields according to the global Settings object
     */
    private void setupFields()
    {
        DateTimeFormatItem displayDateFormat = null;
        for (Object item : dateTimeFormatComboBox.getItems())
        {
            if (((DateTimeFormatItem)item).getFormat().equals(Settings.getInstance().getDateFormat()))
                displayDateFormat = (DateTimeFormatItem)item;
        }

        dateTimeFormatComboBox.setValue(displayDateFormat);
        displayCurrencyComboBox.setValue(Settings.getInstance().getDisplayCurrency());
        alwaysShowSymbolOnRightCheckBox.setSelected(Settings.getInstance().areCurrencySymbolsAfter());

        useAPICheckBox.setSelected(Settings.getInstance().areUsingApi());
        if (!Settings.getInstance().areUsingApi())
        {
            useEnvVarCheckBox.setDisable(true);
            apiKeyField.setDisable(true);
        }

        useEnvVarCheckBox.setSelected(Settings.getInstance().areUsingEnvironmentVariableApiKey());
        setUseEnvironmentVariable(Settings.getInstance().areUsingEnvironmentVariableApiKey());
    }

    /**
     * Applies fields to the global Settings object
     */
    public void applySettings()
    {
        Settings.getInstance().setDateFormat(((DateTimeFormatItem)dateTimeFormatComboBox.getValue()).getFormat());
        Settings.getInstance().setDisplayCurrency((String) displayCurrencyComboBox.getValue());
        Settings.getInstance().setCurrencySymbolsAfter(alwaysShowSymbolOnRightCheckBox.isSelected());
        Settings.getInstance().setUseApi(useAPICheckBox.isSelected());
        if (useAPICheckBox.isSelected()) {
            Settings.getInstance().setUseEnvironmentVariableApiKey(useEnvVarCheckBox.isSelected());
            if (!useEnvVarCheckBox.isSelected())
                Settings.getInstance().setApiKey(apiKeyField.getText());
        }
    }

    /**
     * Called after setting the use environment variable checkbox, updates the UI
     * @param value Value that the checkbox was set to
     */
    private void setUseEnvironmentVariable(boolean value)
    {
        if (value) {
            apiKeyField.setText(Settings.getInstance().getEnvironmentApiKey());
            apiKeyField.setDisable(true);
        }
        else
        {
            apiKeyField.setText(Settings.getInstance().getApiKey());
            if (apiKeyField.getText() == null)
                apiKeyField.setText("");

            apiKeyField.setDisable(false);
        }
    }

    /**
     * Validates the API field and sets the error label
     */
    private void validateAPI()
    {
        boolean validAPIField = !useAPICheckBox.isSelected() ||  (apiKeyField.getText() != null && !apiKeyField.getText().isEmpty()) || useEnvVarCheckBox.isSelected();
        if (validAPIField)
        {
            apiKeyField.getStyleClass().removeIf(s -> s.equals("invalid"));
        }

        submitButton.setDisable(!validAPIField);

        if (!validAPIField )
        {
            apiErrorLabel.setManaged(true);
            apiErrorLabel.setVisible(true);
            apiErrorLabel.setText("API key field must not be empty.");
            apiKeyField.getStyleClass().add("invalid");
        }
        else if (changedAPI)
        {
            apiErrorLabel.setManaged(true);
            apiErrorLabel.setVisible(true);
            apiErrorLabel.setText("API changes only take effect after restart!");
        }
        else
        {
            apiErrorLabel.setManaged(false);
            apiErrorLabel.setVisible(false);
        }
    }

    @FXML
    private void submitActionPerformed(ActionEvent ee) {
        applySettings();
        setupFields();
    }

    @FXML
    private void cancelActionPerformed(ActionEvent e) {
        setupFields();
    }
}
