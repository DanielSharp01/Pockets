package view;

import controller.item.TagController;
import controller.list.EntityListController;
import javafx.scene.Node;
import model.entities.Tag;
import utils.DI;

public class TagHolder extends ViewHolder<Tag> {
    /**
     * FXMLInflater that loads the underlying FXMLTuple
     */
    private static FXMLInflater inflater = DI.layouts.getFXMLInflater("tag.fxml");

    /**
     * Underlying FXML node and controller
     */
    private FXMLTuple fxmlTuple;

    /**
     * ListController of the parent list
     */
    private EntityListController<Tag> listController;

    /**
     * @param listController ListController of the parent list
     */
    public TagHolder(EntityListController<Tag> listController)
    {
        this.listController = listController;
    }

    @Override
    public void update(Tag item) {
        fxmlTuple = inflater.inflate();
        ((TagController)fxmlTuple.getController()).setModel(item);
        ((TagController)fxmlTuple.getController()).setListController(listController);
    }

    @Override
    public Node getNode() {
        return fxmlTuple.getRoot();
    }
}
