package controller;

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
