package view;

import controller.item.ItemController;
import controller.list.EntityListController;
import javafx.scene.Node;
import model.entities.Item;
import utils.DI;

/**
 * ViewHolder for IncomeSource and ExpenseItem
 */
public class ItemHolder extends ViewHolder<Item> {

    /**
     * FXMLInflater that loads the underlying FXMLTuple
     */
    private static FXMLInflater inflater = DI.layouts.getFXMLInflater("item.fxml");

    /**
     * Underlying FXML node and controller
     */
    private FXMLTuple fxmlTuple;

    /**
     * ListController of the parent list
     */
    private EntityListController<Item> listController;

    /**
     * @param listController ListController of the parent list
     */
    public ItemHolder(EntityListController<Item> listController)
    {
        this.listController = listController;
    }

    @Override
    public void update(Item item) {
        fxmlTuple = inflater.inflate();
        ((ItemController)fxmlTuple.getController()).setModel(item);
        ((ItemController)fxmlTuple.getController()).setListController(listController);
    }

    @Override
    public Node getNode() {
        return fxmlTuple.getRoot();
    }
}