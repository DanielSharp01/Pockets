package controller.edit;

import controller.validation.NonEmptyRule;
import controller.validation.ValidatedField;
import controller.validation.ValidationRule;
import controller.validation.ValidationRuleList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import model.*;
import model.entities.IncomeSource;
import model.entities.Item;
import model.entities.Tag;
import model.repository.EntityRepository;
import utils.ColorUtils;
import utils.DI;
import view.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ItemEditController extends EditController<Item> {
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

    @FXML
    public void initialize()
    {
        imageField.setButtonCell(new ImageComboItem());
        imageField.setCellFactory(cb -> new ImageComboItem());
        try {
            imageField.getItems().add(Paths.get(".NULL.")); // Sentry value, null would cause exception
            imageField.getItems().addAll(DI.userImages.getImages());
        } catch (IOException e) {
            Dialogs.showErrorOk("Error!", "User images could not be loaded! Image selection will be disabled for now.");
            imageField.setDisable(true);
        }

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
        addTagField.setItems(DI.getRepositories().tags.asObservableList());
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
                            return "Money is not in the correct format or uses an unknown currency!";

                        return null;
                    }
                }), success -> submitButton.setDisable(!success));

        priceField.textProperty().addListener((observable, oldValue, newValue) -> validatedPriceField.validate(newValue));
    }

    @Override
    public void setModel(Item model)
    {
        this.model = model.clone();

        nameField.setText(model.getName());

        priceLabel.setText(model instanceof IncomeSource ? "Income:" : "Price:");
        priceField.setText(model.getMoney() == null ? "" : model.getMoney().toString());

        colorField.setValue(model.getColor());
        imageField.setValue(model.getImageResource());

        if (imageField.getValue() == null)
        {
            imageField.getSelectionModel().select(0);
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

    @Override
    protected void updateModel()
    {
        model.setName(nameField.getText());
        model.setMoney(Money.parse(priceField.getText()));
        model.setColor(colorField.getValue());

        if (imageField.getValue().toString().equals(".NULL."))
            model.setImageResource(null);
        else
            model.setImageResource((Path) imageField.getValue());

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

        EntityRepository repository = (model instanceof IncomeSource) ?  DI.getRepositories().incomes :  DI.getRepositories().expenses;
        if (model.getId() == 0)
            repository.add(model);
        else
            repository.update(model);
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
                label.setStyle("tag-color: " + ColorUtils.toHex(tag.getColor()) + ";");
                label.getStyleClass().add("tag-label");
                label.getStyleClass().add("editable");
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
                Dialogs.showErrorOk("Error!", "Image could not be loaded or added!");
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
        submit();
    }

    @FXML
    private void cancelActionPerformed(ActionEvent e)
    {
        cancel();
    }
}
