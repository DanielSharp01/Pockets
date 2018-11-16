package view;

import controller.TagController;
import javafx.scene.Node;
import model.entities.Tag;
import utils.DI;

public class TagHolder extends ViewHolder<Tag> {

    private FXMLTuple fxmlTuple;
    private static FXMLInflater inflater;

    static
    {
        inflater = DI.layouts.getFXMLInflater("tag.fxml");
    }

    @Override
    public void update(Tag item) {
        fxmlTuple = inflater.inflate();
        ((TagController)fxmlTuple.getController()).setContent(item);
    }

    @Override
    public Node getNode() {
        return fxmlTuple.getRoot();
    }
}
