package utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;
import java.net.URL;

/**
 * Multi fire FXMLLoader
 */
public class FXMLInflater {

    /**
     * URL of the FXML resource
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
     * Loads the FXML and creates Node out of the root node in FXML
     * @return FXML root node
     */
    public Node inflate()
    {
        FXMLLoader loader = new FXMLLoader(resource);
        try {
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
