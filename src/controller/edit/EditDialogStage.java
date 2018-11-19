package controller.edit;

import javafx.scene.Parent;
import javafx.stage.Stage;
import view.SceneFactory;

/**
 * Stage for the edit dialog
 */
public class EditDialogStage<T> extends Stage {

    private EditController<T> sceneController;

    /**
     * Constructs a stage from the root node with the specified controller
     * @param rootNode Root of the scene
     * @param controller Controller to callback
     */
    public EditDialogStage(Parent rootNode, double width, double height, EditController<T> controller)
    {
        setScene(SceneFactory.getScene(rootNode, width, height));
        sceneController = controller;
        sceneController.setStage(this);
        setOnCloseRequest(e ->
        {
            e.consume();
            sceneController.cancel();
        });
    }

    private boolean submitted = false;

    public void gotSubmitted()
    {
        submitted = true;
    }

    /**
     * Shows and waits until the dialog is either submitted or cancelled
     * @param model Model edited
     * @return True if submitted, false otherwise
     */
    public boolean showAndWaitForSubmit(T model)
    {
        submitted = false;
        sceneController.setModel(model);
        showAndWait();
        return submitted;
    }
}
