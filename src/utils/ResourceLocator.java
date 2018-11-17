package utils;

import javafx.fxml.FXMLLoader;
import view.FXMLInflater;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * Locates resources within a given directory
 */
public class ResourceLocator {

    /**
     * Base directory of resources
     */
    private final List<String> baseDirectories;

    /**
     * @param baseDirectories Possible base directories of the resources
     */
    public ResourceLocator(String... baseDirectories) {
        this.baseDirectories = Arrays.asList(baseDirectories);
    }

    /**
     * Gets a resource with a given path from the matching directory from {@link #baseDirectories}.
     * @param path Path of the resource within the matching directory from {@link #baseDirectories}.
     * @return Resource URL, null if it was not found
     */
    public URL getResource(String path)
    {
        for (String directory : baseDirectories)
        {
            URL resource = getResourceFromDirectory(directory, path);
            if (resource != null) return resource;
        }

        return null;
    }

    /**
     * Gets a resource from the given directory with the given path
     * @param directory Parent directory of the resource
     * @param path Path of the resource
     * @return Resource URL, null if it was not found
     */
    private URL getResourceFromDirectory(String directory, String path)
    {
        return ResourceLocator.class.getResource("../" + directory + "/" + path);
    }

    /**
     * Gets a single fire FXMLLoader initialized with the resource
     * WARNING: Load with this only once
     * @param path Path of the FXMLTuple within the matching directory from {@link #baseDirectories}.
     * @return A single fire FXMLLoader initialized with the resource, null if the resource was not found
     * @see #getFXMLInflater(String)
     */
    public FXMLLoader getFXMLLoader(String path)
    {
        URL resource = getResource(path);
        if (resource != null) return new FXMLLoader(resource);
        else return null;
    }

    /**
     * Gets FXMLInflater initialized with the resource
     * @param path Path of the FXMLTuple within the matching directory from {@link #baseDirectories}.
     * @return An FXMLInflater initialized with the resource, null if the resource was not found
     * @see #getFXMLLoader(String)
     */
    public FXMLInflater getFXMLInflater(String path)
    {
        URL resource = getResource(path);
        if (resource != null) return new FXMLInflater(resource);
        else return null;
    }
}
