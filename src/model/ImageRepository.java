package model;

import utils.FileUtils;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains user added images
 */
public class ImageRepository {

    /**
     * Supported image formats by JavaFX
     */
    public static final String[] supportedImageFormats = new String[] {"png", "gif", "jpg", "jps", "mpo"};

    /**
     * User images folder path
     */
    public static final Path path = Paths.get("user-images/");

    /**
     * Gets all image URLs
     * @return List of image URLs
     * @throws java.nio.file.NotDirectoryException if the file could not otherwise be opened because it is not a directory (optional specific exception)
     * @throws java.io.IOException if an I/O error occurs
     * @throws SecurityException In the case of the default provider, and a security manager is installed, the checkRead method is invoked to check read access to the directory.
     */
    public List<URL> getImages() throws IOException {
        List<URL> urls = new ArrayList<>();

        for (Path file : Files.newDirectoryStream(path))
        {
            for (String format : supportedImageFormats) {
                if (file.endsWith(format))
                {
                    urls.add(file.toUri().toURL());
                }
            }
        }

        return urls;
    }

    /**
     * Copies the image to this folder
     * @param image Image to copy
     * @throws IOException if an I/O error occurs
     */
    public void addImage(Path image) throws IOException
    {
        Files.copy(image, FileUtils.getUnusedNumberedPath(path, image));
    }
}
