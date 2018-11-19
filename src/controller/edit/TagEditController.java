package controller.edit;

import controller.validation.NonEmptyRule;
import controller.validation.ValidatedField;
import controller.validation.ValidationRule;
import controller.validation.ValidationRuleList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Tag;
import utils.DI;

public class TagEditController extends EditController<Tag> {
    @FXML
    private TextField nameField;

    @FXML
    private Label nameErrorLabel;

    @FXML
    private ColorPicker colorField;

    @FXML
    private Button submitButton;

    /**
     * The dialog was submitted and not cancelled
     */
    private boolean submitted = false;

    @FXML
    public void initialize()
    {
        setupValidation();
    }

    /**
     * Setup validated fields
     */
    private void setupValidation()
    {
        ValidatedField validatedNameField = new ValidatedField(nameField, nameErrorLabel, new ValidationRuleList(
                new NonEmptyRule(),
                new ValidationRule() {
                    @Override
                    public String validate(String field) {
                        for (Tag tag : DI.getRepositories().tags)
                        {
                            if (tag.getName().equals(field) && model.getId() != tag.getId())
                                return "Tag name already exists!";
                        }

                        return null;
                    }
                }), (success) -> submitButton.setDisable(!success));

        nameField.textProperty().addListener((observable, oldValue, newValue) -> validatedNameField.validate(newValue));
    }

    @Override
    public void setModel(Tag model)
    {
        this.model = model.clone();

        nameField.setText(model.getName());
        colorField.setValue(model.getColor());
    }

    @Override
    protected void updateModel()
    {
        model.setName(nameField.getText());
        model.setColor(colorField.getValue());
        if (model.getId() == 0)
            DI.getRepositories().tags.add(model);
        else
            DI.getRepositories().tags.update(model);

        System.out.println(DI.gson.toJson(model, Tag.class));
    }

    @FXML
    private void submitActionPerformed(ActionEvent e)
    {
        submit();
    }

    @FXML
    private void cancelActionPerformed(ActionEvent e)
    {
        cancel();
    }
}
