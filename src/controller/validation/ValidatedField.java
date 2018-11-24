package controller.validation;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.function.Consumer;

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
    private Consumer<Boolean> validationCallback;

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
        this(field, errorLabel, rule, null);
    }

    /**
     * @param field Field that is validated
     * @param errorLabel Label where the error is shown
     * @param rule Validation rule used
     * @param validationCallback Called with true when validation passes, otherwise called with false
     */
    public ValidatedField(TextField field, Label errorLabel, ValidationRule rule, Consumer<Boolean> validationCallback) {
        this.field = field;
        this.errorLabel = errorLabel;
        this.rule = rule;
        this.validationCallback = validationCallback;
    }

    /**
     * Validates the field and displays it's error
     * @param text text Text to validate (text of the field)
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
            if (validationCallback != null) validationCallback.accept(false);
        }
        else
        {
            errorLabel.setManaged(false);
            errorLabel.setVisible(false);
            field.getStyleClass().removeIf(s -> s.equals("invalid"));
            if (validationCallback != null) validationCallback.accept(true);
        }
    }
}
