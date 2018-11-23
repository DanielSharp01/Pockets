package controller.list;

import controller.edit.EditController;
import controller.edit.EditDialogStage;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import model.entities.HistoryEntry;
import model.entities.Tag;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import utils.DI;
import view.*;

import java.util.Optional;

/**
 * List controller for the tag list
 */
public class TagListController extends EntityListController<Tag> {

    private static final FXMLInflater editDialogInflater = DI.layouts.getFXMLInflater("tag-edit.fxml");
    /**
     * Filtered list of tags
     */
    private FilteredList<Tag> filteredTags;

    public TagListController()
    {
        filteredTags = DI.getRepositories().tags.asFilteredList();
    }

    @Override
    public FilteredList<Tag> getFilteredList() {
        return filteredTags;
    }

    @Override
    public Callback<ListView, ListCell<HistoryEntry>> getCellFactory() {
        throw new NotImplementedException();
    }

    @Override
    public Callback<TileListView, ViewHolder<Tag>> getHolderFactory() {
        return l -> new TagHolder(this);
    }

    @Override
    public void newEntity() {
        FXMLTuple tuple = editDialogInflater.inflate();
        EditDialogStage<Tag> editDialog = new EditDialogStage<>(tuple.getRoot(), 380, 400, (EditController<Tag>) tuple.getController());
        editDialog.setTitle("Add tag");
        Tag tag = new Tag(0);
        tag.setName(currentFilter);
        editDialog.showAndWaitForSubmit(tag);
    }

    @Override
    public void edit(Tag model) {
        FXMLTuple tuple = editDialogInflater.inflate();
        EditDialogStage<Tag> editDialog = new EditDialogStage<>(tuple.getRoot(), 380, 400, (EditController<Tag>) tuple.getController());
        editDialog.setTitle("Edit tag");
        editDialog.showAndWaitForSubmit(model);
    }

    @Override
    public void delete(Tag model) {
        if (DI.getRepositories().tags.isUsed(model))
        {
            Optional<ButtonType> button = Dialogs.showWarningYesNo("Warning!", "This tag is used in one or more items. Do you still want to delete it?");
            if (button.isPresent() && button.get().getButtonData().equals(ButtonBar.ButtonData.YES))
            {
                DI.getRepositories().tags.removeTagUsage(model);
                DI.getRepositories().tags.delete(model);
            }
        }
        else
        {
            DI.getRepositories().tags.delete(model);
        }
    }
}
