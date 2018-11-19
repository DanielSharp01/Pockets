package controller.item;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import model.entities.Tag;
import utils.ColorUtils;

public class TagController {
    @FXML
    Pane mainPanel;

    @FXML
    Label nameLabel;

    public void setContent(Tag model)
    {
        mainPanel.setStyle("tag-color: " + ColorUtils.toHex(model.getColor()) + ";");
        nameLabel.setText(model.getName());
    }
}
