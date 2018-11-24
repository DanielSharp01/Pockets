package view;

import javafx.scene.Parent;

/**
 * Contains the root node and it's controller
 */
public class FXMLTuple {
    /**
     * Root FXML node
     */
    private Parent root;

    /**
     * Controller associated with the FXML
     */
    private Object controller;

    /**
     * @param root Root FXML node
     * @param controller Controller associated with the FXML
     */
    public FXMLTuple(Parent root, Object controller) {
        this.root = root;
        this.controller = controller;
    }

    /**
     * @return Root FXML node
     */
    public Parent getRoot() {
        return root;
    }

    /**
     * @return Controller associated with the FXML
     */
    public Object getController() {
        return controller;
    }
}
