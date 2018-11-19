package view;

import controller.item.TagController;
import controller.list.EntityListController;
import javafx.scene.Node;
import model.entities.Tag;
import utils.DI;

public class TagHolder extends ViewHolder<Tag> {

    private FXMLTuple fxmlTuple;
    private static FXMLInflater inflater;
    private EntityListController<Tag> listController;


    static
    {
        inflater = DI.layouts.getFXMLInflater("tag.fxml");
    }

    public TagHolder(EntityListController<Tag> listController)
    {
        this.listController = listController;
    }

    @Override
    public void update(Tag item) {
        fxmlTuple = inflater.inflate();
        ((TagController)fxmlTuple.getController()).setContent(item);
        ((TagController)fxmlTuple.getController()).setListController(listController);
    }

    @Override
    public Node getNode() {
        return fxmlTuple.getRoot();
    }
}
