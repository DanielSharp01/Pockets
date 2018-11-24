package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.URL;

/**
 * IO utilities
 */
public class IOUtils {

    /**
     * Gets path in a folder for the specified file (preserves filename)
     * @param folder Folder to put the file in
     * @param file File to put in the folder
     * @return File path in the folder (may collide)
     * @see #getUnusedNumberedPathInFolder
     */
    public static Path getPathInFolder(Path folder, Path file)
    {
        String[] fileParts = file.getFileName().toString().split("\\.", 2);
        Path ret = Paths.get(folder.toString(), fileParts[0] + "." + fileParts[1]);
        return ret;
    }

    /**
     * Tries to add to the number after the filename until there are no collisions in the specified folder
     * @param folder Folder to put the file in
     * @param file File to put in the folder
     * @return File path which will definitely not collide in the folder
     * @throws UnsupportedOperationException If you have {@link Integer#MAX_VALUE} + 1 files with the same name (that does not happen often)
     * @see #getPathInFolder
     */
    public static Path getUnusedNumberedPathInFolder(Path folder, Path file)
    {
        String[] fileParts = file.getFileName().toString().split("\\.", 2);
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            Path ret = Paths.get(folder.toString(), fileParts[0] + i + "." + fileParts[1]);
            if (!Files.exists(ret))
                return ret;
        }

        throw new UnsupportedOperationException("That's crazy!");
    }

    /**
     * @param urlString URL as string
     * @return Read string
     * @throws IOException If any error occurs during reading
     */
    public static String readAllFromURL(String urlString) throws IOException {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1) {
                buffer.append(chars, 0, read);
            }

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }
}
