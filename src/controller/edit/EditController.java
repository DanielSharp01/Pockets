package controller.edit;

import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import view.Dialogs;

import java.util.Optional;

/**
 * Controller for an edit dialog's scene
 * @param <T> Type of the model
 */
public abstract class EditController<T> {

    /**
     * Stage of the edit dialog
     */
    private EditDialogStage stage;

    /**
     * Model of the dialog
     */
    protected T model;

    /**
     * Setup the fields to match the model specified
     * @param updated Sets the internal model to a copy of this (clone)
     */
    public abstract void setModel(T updated);

    /**
     * Updates the model when the stage is about to be submitted
     */
    protected abstract void updateModel();

    /**
     * @return Model of the dialog
     */
    public T getModel()
    {
        return model;
    }

    /**
     * @param stage Stage of the edit dialog
     */
    public void setStage(EditDialogStage stage)
    {
        this.stage = stage;
    }

    /**
     * Submits the model and closes the stage
     */
    public void submit()
    {
        updateModel();
        stage.gotSubmitted();
        stage.close();
    }

    /**
     * Cancels (doesn't submit the model) only closes the stage
     * @return True if user really wants to cancel, false if the cancel was canceled
     */
    public boolean cancel()
    {
        Optional<ButtonType> button = Dialogs.showWarningYesNo("Warning!",
                "Do you really want to cancel without saving?");
        if (button.isPresent() && button.get().getButtonData().equals(ButtonBar.ButtonData.YES))
        {
            stage.close();
            return true;
        }

        return false;
    }
}
