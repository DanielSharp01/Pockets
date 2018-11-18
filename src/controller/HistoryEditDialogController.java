package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.entities.HistoryEntry;
import model.entities.Item;
import model.entities.Tag;
import utils.ColorUtils;
import utils.DI;
import view.ItemComboItem;
import view.TagComboItem;

import java.util.List;

public class HistoryEditDialogController {
    @FXML
    private ComboBox itemTypeField;

    @FXML
    private ComboBox incomeField;

    @FXML
    private ComboBox expenseField;

    @FXML
    private Label itemErrorLabel;

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
    private HistoryEntry model;

    @FXML
    public void initialize()
    {
        incomeField.setButtonCell(new ItemComboItem());
        expenseField.setButtonCell(new ItemComboItem());
        incomeField.setCellFactory(cb -> new ItemComboItem());
        expenseField.setCellFactory(cb -> new ItemComboItem());

        incomeField.setItems(DI.getRepositories().incomes.asObservable());
        expenseField.setItems(DI.getRepositories().expenses.asObservable());

        itemTypeField.valueProperty().addListener((observable, oldValue, newValue) -> changeItemType((String)newValue));

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

    /**
     * Change item type (Income or Expense)
     * @param type Item type (Income or Expense)
     */
    private void changeItemType(String type)
    {
        if (type.equals("Income"))
        {
            incomeField.setManaged(true);
            incomeField.setVisible(true);
            expenseField.setManaged(false);
            expenseField.setVisible(false);
            if (incomeField.getItems().size() == 0)
            {
                itemErrorLabel.setVisible(true);
                itemErrorLabel.setManaged(true);
                itemErrorLabel.setText("No selectable income source, come back after adding one in the items tab!");
                submitButton.setDisable(true);
            }
            else
            {
                itemErrorLabel.setVisible(false);
                itemErrorLabel.setManaged(false);
                submitButton.setDisable(false);
            }
        }
        else
        {
            incomeField.setManaged(false);
            incomeField.setVisible(false);
            expenseField.setManaged(true);
            expenseField.setVisible(true);
            if (expenseField.getItems().size() == 0)
            {
                itemErrorLabel.setVisible(true);
                itemErrorLabel.setManaged(true);
                itemErrorLabel.setText("No selectable expense item, come back after adding one in the items tab!");
                submitButton.setDisable(true);
            }
            else
            {
                itemErrorLabel.setVisible(false);
                itemErrorLabel.setManaged(false);
                submitButton.setDisable(false);
            }
        }
    }

    /**
     * Updates the model before submitting
     */
    private void updateModel()
    {
        model.setItemType(itemTypeField.getValue().equals("Income") ? HistoryEntry.Type.Income : HistoryEntry.Type.Expense);
        Item item = model.getItemType() == HistoryEntry.Type.Income ? (Item)incomeField.getValue() : (Item)expenseField.getValue();
        model.setItemId(item.getId());

        System.out.println(DI.gson.toJson(model, HistoryEntry.class));
    }

    /**
     * Sets the model of this controller to be a clone of the specified one
     * @param model Model to set
     */
    public void setModel(HistoryEntry model)
    {
        submitted = false;
        this.model = model.clone();

        if (model.getItemType() == HistoryEntry.Type.Income)
        {
            itemTypeField.setValue("Income");
            changeItemType("Income");

            if (model.getItemId() != 0)
                incomeField.setValue(model.getItem());
            else if (incomeField.getItems().size() > 0)
                incomeField.getSelectionModel().select(0);

            if (expenseField.getItems().size() > 0)
                expenseField.getSelectionModel().select(0);
        }
        else
        {
            itemTypeField.setValue("Expense");
            changeItemType("Expense");

            if (model.getItemId() != 0)
                expenseField.setValue(model.getItem());
            else if (expenseField.getItems().size() > 0)
                expenseField.getSelectionModel().select(0);

            if (incomeField.getItems().size() > 0)
                incomeField.getSelectionModel().select(0);
        }

        setupTagBox();
    }

    /**
     * @return Model edited
     */
    public HistoryEntry getModel()
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
