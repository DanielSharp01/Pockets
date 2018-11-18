package controller;

/**
 * Represents a set of validation rules that must be followed in order
 */
public class ValidationRuleList extends ValidationRule {

    /**
     * Rule list
     */
    private ValidationRule[] rules;

    /**
     * @param rules Rule list
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
