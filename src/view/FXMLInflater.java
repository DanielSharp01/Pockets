package view;

import javafx.fxml.FXMLLoader;
import utils.ResourceLocator;

import java.io.IOException;
import java.net.URL;

/**
 * Multi fire FXMLLoader
 */
public class FXMLInflater {

    /**
     * URL of the FXMLTuple resource
     * @see ResourceLocator
     */
    private URL resource;

    /**
     * @param resource URL of the FXML resource
     */
    public FXMLInflater(URL resource)
    {
        this.resource = resource;
    }

    /**
     * Loads the FXML and creates a tuple holding the Node and the Controller
     * @return A tuple holding the Node and the Controller
     */
    public FXMLTuple inflate()
    {
        FXMLLoader loader = new FXMLLoader(resource);
        try {
            return new FXMLTuple(loader.load(), loader.getController());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
