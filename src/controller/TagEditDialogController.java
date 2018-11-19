package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.entities.Tag;
import utils.DI;
import view.Dialogs;

import java.util.Optional;

public class TagEditDialogController {
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

    /**
     * Model edited
     */
    private Tag model;

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

    /**
     * Updates the model before submitting
     */
    private void updateModel()
    {
        model.setName(nameField.getText());
        model.setColor(colorField.getValue());

        System.out.println(DI.gson.toJson(model, Tag.class));
    }

    /**
     * Sets the model of this controller to be a clone of the specified one
     * @param model Model to set
     */
    public void setModel(Tag model)
    {
        submitted = false;
        this.model = model.clone();

        nameField.setText(model.getName());
        colorField.setValue(model.getColor());
    }

    /**
     * @return Model edited
     */
    public Tag getModel()
    {
        return model;
    }

    /**
     * @return The dialog was submitted and not cancelled
     */
    public boolean isSubmitted()
    {
        return submitted;
    }

    @FXML
    private void submitActionPerformed(ActionEvent e)
    {
        updateModel();
        submitted = true;

        closeStage((Node)e.getSource());
    }

    @FXML
    private void cancelActionPerformed(ActionEvent e)
    {
        if (tryCancel())
            closeStage((Node) e.getSource());
    }

    public boolean tryCancel()
    {
        Optional<ButtonType> button = Dialogs.showWarningYesNo("Warning!",
                "Do you really want to cancel without saving?");
        if (button.isPresent() && button.get().getButtonData().equals(ButtonBar.ButtonData.YES))
        {
            return true;
        }

        return false;
    }

    private void closeStage(Node source)
    {
        ((Stage)source.getScene().getWindow()).close();
    }
}
