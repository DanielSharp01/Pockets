package controller.validation;

/**
 * Used to validate a field
 */
public abstract class ValidationRule {
    /**
     * Gives back an error string for a given field
     * @param field Field to be validated
     * @return Validation error, null if none occurred
     */
    public abstract String validate(String field);
}
