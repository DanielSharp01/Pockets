package controller.validation;

/**
 * Represents a set of validation rules that must be followed.
 */
public class ValidationRuleList extends ValidationRule {

    /**
     * Rule list. Rules get evaluated in the order they are specified in the array.
     */
    private ValidationRule[] rules;

    /**
     * @param rules Rule list. Rules get evaluated in the order they are specified in the array.
     */
    public ValidationRuleList(ValidationRule... rules) {
        this.rules = rules;
    }

    @Override
    public String validate(String field) {
        for (ValidationRule rule : rules)
        {
            String error = rule.validate(field);
            if (error != null) return error;
        }

        return null;
    }
}
