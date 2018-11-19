package model;

import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import utils.FileUtils;
import view.Dialogs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
     * Gets all image paths
     * @return List of image paths
     * @throws java.nio.file.NotDirectoryException if the file could not otherwise be opened because it is not a directory (optional specific exception)
     * @throws java.io.IOException if an I/O error occurs
     * @throws SecurityException In the case of the default provider, and a security manager is installed, the checkRead method is invoked to check read access to the directory.
     */
    public List<Path> getImages() throws IOException {
        List<Path> paths = new ArrayList<>();

        if (Files.isDirectory(path)) {
            for (Path path : Files.newDirectoryStream(path)) {
                for (String format : supportedImageFormats) {
                    if (path.toString().endsWith(format)) {
                        paths.add(path);
                    }
                }
            }
        }

        return paths;
    }

    /**
     * Copies the image to this folder
     * @param image Image to copy
     * @throws IOException if an I/O error occurs
     */
    public void addImage(Path image) throws IOException
    {
        if (!Files.isDirectory(path))
        {
            Files.createDirectory(path);
        }

        Path movePath = FileUtils.getPathInFolder(path, image);
        if (Files.exists(movePath))
        {
            Optional<ButtonType> result = Dialogs.showWarningYesNo("Warning!",
                    "Image already added. Do you want to overwrite \"" + image.getFileName() + "\"?");
            if (!result.isPresent() || !result.get().getButtonData().equals(ButtonBar.ButtonData.YES))
            {
                movePath = FileUtils.getUnusedNumberedPathInFolder(path, image);
            }
        }

        Files.copy(image, movePath, StandardCopyOption.REPLACE_EXISTING);
    }
}
