package view;

import javafx.scene.Node;
import javafx.scene.control.Label;

public class DefaultViewHolder extends ViewHolder {

    private Label label = new Label();

    @Override
    public void update(Object model) {
        label.setText(model.toString());
    }

    @Override
    public Node getNode() {
        return label;
    }
}
