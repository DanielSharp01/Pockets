package view;

import javafx.scene.Parent;

/**
 * Contains the root node and it's controller
 */
public class FXMLTuple {
    private Parent root;
    private Object controller;

    public FXMLTuple(Parent root, Object controller) {
        this.root = root;
        this.controller = controller;
    }

    public Parent getRoot() {
        return root;
    }

    public Object getController() {
        return controller;
    }
}
