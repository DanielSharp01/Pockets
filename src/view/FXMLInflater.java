package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;
import java.net.URL;

public class FXMLInflater {

    private URL resource;

    public FXMLInflater(URL resource)
    {
        this.resource = resource;
    }

    public Node Inflate()
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
