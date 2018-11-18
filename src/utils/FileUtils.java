package utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {
    /**
     * Tries to add to the number after the filename until there are no collisions in the specified folder
     * @param folder Folder to put the file in
     * @param file File to put in the folder
     * @return File path which will definitely not collide in the folder
     * @throws UnsupportedOperationException If you have {@link Integer#MAX_VALUE} + 1 files with the same name (that does not happen often)
     */
    public static Path getUnusedNumberedPath(Path folder, Path file)
    {
        String[] fileParts = file.getFileName().toString().split("\\.", 2);
        Path ret = Paths.get(folder.toString(), fileParts[0] + "." + fileParts[1]);
        if (!Files.exists(ret))
            return ret;

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            ret = Paths.get(folder.toString(), fileParts[0] + i + "." + fileParts[1]);
            if (!Files.exists(ret))
                return ret;
        }

        throw new UnsupportedOperationException("That's crazy!");
    }
}
