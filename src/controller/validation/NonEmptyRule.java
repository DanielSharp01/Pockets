package controller.validation;

/**
 * Validation rule which fails if the string is empty
 */
public class NonEmptyRule extends ValidationRule {
    @Override
    public String validate(String field) {
        if (field.isEmpty())
        {
            return "Field must not be empty!";
        }

        return null;
    }
}
