package view;

import controller.item.ItemController;
import controller.list.EntityListController;
import javafx.scene.Node;
import model.entities.Item;
import utils.DI;

public class ItemHolder extends ViewHolder<Item> {

    private FXMLTuple fxmlTuple;
    private static FXMLInflater inflater;
    private EntityListController<Item> listController;

    static
    {
        inflater = DI.layouts.getFXMLInflater("item.fxml");
    }

    public ItemHolder(EntityListController<Item> listController)
    {
        this.listController = listController;
    }

    @Override
    public void update(Item item) {
        fxmlTuple = inflater.inflate();
        ((ItemController)fxmlTuple.getController()).setContent(item);
        ((ItemController)fxmlTuple.getController()).setListController(listController);
    }

    @Override
    public Node getNode() {
        return fxmlTuple.getRoot();
    }
}