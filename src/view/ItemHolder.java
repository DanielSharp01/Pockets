package view;

import controller.ItemController;
import javafx.scene.Node;
import model.entities.Item;
import utils.DI;

public class ItemHolder extends ViewHolder<Item> {

    private FXMLTuple fxmlTuple;
    private static FXMLInflater inflater;

    static
    {
        inflater = DI.layouts.getFXMLInflater("item.fxml");
    }

    @Override
    public void update(Item item) {
        fxmlTuple = inflater.inflate();
        ((ItemController)fxmlTuple.getController()).setContent(item);
    }

    @Override
    public Node getNode() {
        return fxmlTuple.getRoot();
    }
}