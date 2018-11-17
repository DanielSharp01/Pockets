package utils;

import app.Settings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.paint.Color;
import model.ImageRepository;
import model.Recurrence;
import model.repository.RepositoryContainer;
import model.serializers.ColorAdapter;
import model.serializers.LocalDateTimeAdapter;
import model.serializers.RecurrenceAdapter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

/**
 * DI container used for dependency injection
 */
public final class DI {
    /**
     * Resource locator for "res" folder
     */
    public static final ResourceLocator resources = new ResourceLocator("res");

    /**
     * Resource locator for "res/layouts" folder
     */
    public static final ResourceLocator layouts = new ResourceLocator("res/layouts");

    /**
     * Resource locator for "res/styles" and "res/Roboto" folder
     */
    public static final ResourceLocator styles = new ResourceLocator("res/styles", "res/Roboto");

    /**
     * Resource locator for "user-images/" folder
     */
    public static final ImageRepository userImages = new ImageRepository();

    /**
     * Global settings object, contains app settings
     */
    public static Settings settings;

    /**
     * JSON file for app settings
     */
    public static final String settingsJsonFile = "app-settings.json";

    /**
     * Gson object used for JSON serialization
     */
    public static final Gson gson;

    static
    {
        gson = new GsonBuilder().serializeNulls()
                .registerTypeAdapter(Recurrence.class, new RecurrenceAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(Color.class, new ColorAdapter())
                .setPrettyPrinting()
                .setDateFormat("yyyy-mm-dd hh:mm:ss").create();


        // TODO: Pull this outside of the DI container
        if (Files.exists(Paths.get(settingsJsonFile)))
        {
            try {

                settings = gson.fromJson(new String(Files.readAllBytes(Paths.get(settingsJsonFile)), StandardCharsets.UTF_8), Settings.class);
            } catch (IOException e) {
                // TODO: Deserialization error
                settings = new Settings();
            }
        }
        else
        {
            // TODO: Save on exit
            settings = new Settings();
        }
    }

    /**
     * Default repository container
     */
    private static final RepositoryContainer repositories = new RepositoryContainer();

    /**
     * Test repository container
     */
    private static RepositoryContainer testRepositories = new RepositoryContainer();

    /**
     * Swaps the repository container, used for unit testing
     * @param testRepositories Repository container to use, if null it swaps back the default repository
     */
    public static void swapRepositories(RepositoryContainer testRepositories)
    {
        DI.testRepositories = testRepositories;
    }

    /**
     * Gets the repository container
     * @return Test repository container if swapped, default otherwise
     */
    public static RepositoryContainer getRepositories()
    {
        return testRepositories == null ? repositories : testRepositories;
    }
}
