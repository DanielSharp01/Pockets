package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Recurrence;
import model.repository.RepositoryContainer;
import model.serializers.LocalDateTimeAdapter;
import model.serializers.RecurrenceAdapter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public static final Gson gson;

    static
    {
        gson = new GsonBuilder().serializeNulls()
                .registerTypeAdapter(Recurrence.class, new RecurrenceAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .setPrettyPrinting()
                .setDateFormat("yyyy-mm-dd hh:mm:ss").create();
    }

    private static final RepositoryContainer repositories = new RepositoryContainer();
    private static RepositoryContainer testRepositories = new RepositoryContainer();

    /**
     * Default date time formatter
     */
    public static final DateTimeFormatter defaultDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm:ss");

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
