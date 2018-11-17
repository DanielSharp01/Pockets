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
import model.DailyRecurrence;
import model.MonthlyRecurrence;
import model.WeeklyRecurrence;
import model.entities.ExpenseItem;
import model.entities.IncomeSource;
import model.entities.Item;
import model.entities.Tag;
import utils.ColorUtils;
import utils.DI;
import view.ImageComboItem;
import view.TagComboItem;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ItemEditDialogController {
    @FXML
    private TextField nameField;

    @FXML
    private Label nameErrorLabel;

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
    private DatePicker recurrenceDateField;

    @FXML
    private Label recurrenceDateErrorLabel;

    @FXML
    private Label recurrenceXLabel;

    @FXML
    private Spinner recurrenceXField;

    @FXML
    private Label recurrenceXFieldErrorLabel;

    @FXML
    private ComboBox addTagField;

    @FXML
    private FlowPane tagBox;

    private Item model;

    @FXML
    public void initialize()
    {
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
    }

    public void setModel(Item model)
    {
        this.model = model.clone();

        nameField.setText(model.getName());
        nameErrorLabel.setManaged(false);

        priceField.setText(model.getMoney().toString());
        priceErrorLabel.setManaged(false);

        colorField.setValue(model.getColor());
        imageField.setButtonCell(new ImageComboItem());
        imageField.setCellFactory(cb -> new ImageComboItem());
        imageField.setItems(FXCollections.observableArrayList());
        imageField.getItems().add(null);
        try
        {
            imageField.getItems().addAll(DI.userImages.getImages());
        }
        catch (IOException e)
        {
            // TODO: Warning dialog
        }
        imageField.setValue(model.getImageResource());

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
        recurrenceDateErrorLabel.setManaged(false);
        recurrenceXFieldErrorLabel.setManaged(false);

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
        // TODO: Set model up for submission
        if (model instanceof IncomeSource)
        {
            DI.getRepositories().incomes.update((IncomeSource)model);
        }
        else
        {
            DI.getRepositories().expenses.update((ExpenseItem)model);
        }
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
