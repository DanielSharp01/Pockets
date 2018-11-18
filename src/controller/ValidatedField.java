package controller;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Helps with validating fields
 */
public class ValidatedField {

    /**
     * Field that is validated
     */
    private TextField field;

    /**
     * Label where the error is shown
     */
    private Label errorLabel;

    /**
     * Validation rule used
     */
    private ValidationRule rule;

    /**
     * Called when validation passes
     */
    private Runnable successCallback;

    /**
     * Called when validation fails
     */
    private Runnable failCallback;

    /**
     * @param field Field that is validated
     * @param errorLabel Label where the error is shown
     * @param rule Validation rule used
     */
    public ValidatedField(TextField field, Label errorLabel, ValidationRule rule) {
        this(field, errorLabel, rule, null, null);
    }

    /**
     * @param field Field that is validated
     * @param errorLabel Label where the error is shown
     * @param rule Validation rule used
     * @param okCallback Called when validation passes
     */
    public ValidatedField(TextField field, Label errorLabel, ValidationRule rule, Runnable okCallback, Runnable failCallback) {
        this.field = field;
        this.errorLabel = errorLabel;
        this.rule = rule;
        this.successCallback = okCallback;
        this.failCallback = failCallback;
    }

    /**
     * Validates the field and displays it's error
     */
    public void validate(String text)
    {
        String error = rule.validate(text);
        if (error != null)
        {
            errorLabel.setText(error);
            errorLabel.setManaged(true);
            errorLabel.setVisible(true);
            field.getStyleClass().add("invalid");
            if (failCallback != null) failCallback.run();
        }
        else
        {
            errorLabel.setManaged(false);
            errorLabel.setVisible(false);
            field.getStyleClass().removeIf(s -> s.equals("invalid"));
            if (successCallback != null) successCallback.run();
        }
    }
}
