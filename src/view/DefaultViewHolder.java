package view;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Default view holder (a Label with ToString content)
 */
public class DefaultViewHolder extends ViewHolder {

    /**
     * Content label
     */
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
