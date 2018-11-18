package controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.*;
import model.entities.IncomeSource;
import model.entities.Item;
import model.entities.Tag;
import utils.ColorUtils;
import utils.DI;
import view.DateTimePicker;
import view.EditableIntegerSpinner;
import view.ImageComboItem;
import view.TagComboItem;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ItemEditDialogController {
    @FXML
    private TextField nameField;

    @FXML
    private Label nameErrorLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private TextField priceField;

    @FXML
    private Label priceErrorLabel;

    @FXML
    private ComboBox imageField;

    @FXML
    private ColorPicker colorField;

    @FXML
    private ComboBox recurrenceTypeField;

    @FXML
    private DateTimePicker recurrenceDateField;

    @FXML
    private Label recurrenceXLabel;

    @FXML
    private EditableIntegerSpinner recurrenceXField;

    @FXML
    private ComboBox addTagField;

    @FXML
    private FlowPane tagBox;

    @FXML
    private Button submitButton;

    /**
     * The dialog was submitted and not cancelled
     */
    private boolean submitted = false;

    /**
     * Model edited
     */
    private Item model;

    @FXML
    public void initialize()
    {
        imageField.setButtonCell(new ImageComboItem());
        imageField.setCellFactory(cb -> new ImageComboItem());

        recurrenceTypeField.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            if (newValue.equals("No recurrence"))
            {
                recurrenceDateField.setDisable(true);
                recurrenceXField.setDisable(true);
            }
            else
            {
                recurrenceDateField.setDisable(false);
                recurrenceXField.setDisable(false);
            }
        });

        addTagField.setConverter(new StringConverter<Tag>() {
            @Override
            public String toString(Tag object) {
                if (object == null) return "";

                return object.getName();
            }

            @Override
            public Tag fromString(String string) {

                List<Tag> tags = DI.getRepositories().tags.filterBySearch(string);

                if (tags.size() == 0) return null;
                else return tags.get(0);
            }
        });

        addTagField.setButtonCell(new TagComboItem());
        addTagField.setCellFactory(combo -> new TagComboItem());
        addTagField.setItems(DI.getRepositories().tags.asObservable());
        addTagField.setValue(null);

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
                        for (Item item : (model instanceof IncomeSource) ? DI.getRepositories().incomes : DI.getRepositories().expenses)
                        {
                            if (item.getName().equals(field) && model.getId() != item.getId())
                                return "Item name already exists!";
                        }

                        return null;
                    }
                }), (success) -> submitButton.setDisable(!success));

        nameField.textProperty().addListener((observable, oldValue, newValue) -> validatedNameField.validate(newValue));

        ValidatedField validatedPriceField = new ValidatedField(priceField, priceErrorLabel, new ValidationRuleList(
                new NonEmptyRule(),
                new ValidationRule() {
                    @Override
                    public String validate(String field) {
                        Money parsed = Money.parse(field);
                        if (parsed == null)
                            return "Money is not in the correct format!";

                        return null;
                    }
                }), success -> submitButton.setDisable(!success));

        priceField.textProperty().addListener((observable, oldValue, newValue) -> validatedPriceField.validate(newValue));
    }

    /**
     * Updates the model before submitting
     */
    private void updateModel()
    {
        model.setName(nameField.getText());
        model.setMoney(Money.parse(priceField.getText()));
        model.setColor(colorField.getValue());
        model.setImageResource((URL) imageField.getValue());

        if (recurrenceTypeField.getValue().equals(recurrenceTypeField.getItems().get(0)))
        {
            model.setRecurrence(new NullRecurrence());
        }
        else if (recurrenceTypeField.getValue().equals(recurrenceTypeField.getItems().get(1)))
        {
            model.setRecurrence(new DailyRecurrence(recurrenceDateField.getDateTimeValue(), (Integer)recurrenceXField.getValueFactory().getValue()));
        }
        else if (recurrenceTypeField.getValue().equals(recurrenceTypeField.getItems().get(2)))
        {
            model.setRecurrence(new WeeklyRecurrence(recurrenceDateField.getDateTimeValue(), (Integer)recurrenceXField.getValueFactory().getValue()));
        }
        else if (recurrenceTypeField.getValue().equals(recurrenceTypeField.getItems().get(3)))
        {
            model.setRecurrence(new MonthlyRecurrence(recurrenceDateField.getDateTimeValue(), (Integer)recurrenceXField.getValueFactory().getValue()));
        }
    }

    /**
     * Sets the model of this controller to be a clone of the specified one
     * @param model Model to set
     */
    public void setModel(Item model)
    {
        submitted = false;
        this.model = model.clone();

        nameField.setText(model.getName());

        priceLabel.setText(model instanceof IncomeSource ? "Income:" : "Price:");
        priceField.setText(model.getMoney().toString());

        colorField.setValue(model.getColor());
        try {
            imageField.setItems(FXCollections.observableList(DI.userImages.getImages()));
            imageField.getItems().add(0, new URL("mailto:null@null")); // None item
            imageField.setValue(model.getImageResource() == null ? new URL("mailto:null@null") : model.getImageResource());
        } catch (IOException e) {
            // TODO: Warning dialog
        }

        if (model.getRecurrence() instanceof DailyRecurrence)
        {
            recurrenceTypeField.setValue(recurrenceTypeField.getItems().get(1));
            recurrenceXLabel.setText("Days that must pass:");
        }
        else if (model.getRecurrence() instanceof WeeklyRecurrence)
        {
            recurrenceTypeField.setValue(recurrenceTypeField.getItems().get(2));
            recurrenceXLabel.setText("Weeks that must pass:");
        }
        else if (model.getRecurrence() instanceof MonthlyRecurrence)
        {
            recurrenceTypeField.setValue(recurrenceTypeField.getItems().get(3));
            recurrenceXLabel.setText("Months that must pass:");
        }
        else
        {
            recurrenceTypeField.setValue(recurrenceTypeField.getItems().get(0));
            recurrenceDateField.setDisable(true);
            recurrenceXField.setDisable(true);
        }

        LocalDateTime lastOccurrence = model.getRecurrence().getLastOccurrence();
        recurrenceDateField.setValue(lastOccurrence == null ? LocalDate.now() : lastOccurrence.toLocalDate()); // TODO: This should be datetime
        recurrenceXField.getValueFactory().setValue(model.getRecurrence().getEveryX());

        setupTagBox();
    }

    /**
     * @return Model edited
     */
    public Item getModel()
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

    /**
     * Sets up the tag box
     */
    private void setupTagBox()
    {
        tagBox.getChildren().clear();
        for (int tagId : model.getTagIds())
        {
            Tag tag = DI.getRepositories().tags.findById(tagId);
            if (tag != null)
            {
                Label label = new Label();
                label.setOnMouseClicked(ev ->
                {
                    tagBox.getChildren().remove(label);
                    addTagField.getItems().add(tag);
                    this.model.getTagIds().remove((Integer)tag.getId());
                });
                label.setText(tag.getName());
                label.setStyle("-fx-background-color: " + ColorUtils.toHex(tag.getColor()) + ";");
                label.getStyleClass().add("tag-label");
                tagBox.getChildren().add(label);
                addTagField.getItems().remove(tag);
            }
        }
    }

    @FXML
    private void addImageActionPerformed(ActionEvent e)
    {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.jpg"));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png"));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("BMP files (*.bmp)", "*.bmp"));
        File file = chooser.showOpenDialog(null);
        if (file != null)
        {
            try {
                DI.userImages.addImage(file.toPath());
                imageField.getItems().add(file.toPath().toUri().toURL());
            } catch (IOException e1) {
                // TODO: Warning dialog
            }
        }
    }

    @FXML
    private void addTagActionPerformed(ActionEvent e)
    {
        Tag tag = (Tag)addTagField.getValue();
        if (tag != null)
        {
            if (!model.getTagIds().contains(tag.getId()))
            {
                model.getTagIds().add(tag.getId());
                Label label = new Label();
                label.setOnMouseClicked(ev ->
                {
                    tagBox.getChildren().remove(label);
                    addTagField.getItems().add(tag);
                    model.getTagIds().remove((Integer)tag.getId());
                });
                label.setText(tag.getName());
                label.setStyle("-fx-background-color: " + ColorUtils.toHex(tag.getColor()) + ";");
                label.getStyleClass().add("tag-label");
                tagBox.getChildren().add(label);
                addTagField.getItems().remove(tag);
            }
        }
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
        // TODO: Warning dialog

        closeStage((Node)e.getSource());
    }

    private void closeStage(Node source)
    {
        ((Stage)source.getScene().getWindow()).close();
    }
}