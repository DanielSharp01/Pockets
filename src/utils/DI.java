package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Recurrence;
import model.repository.RepositoryContainer;
import model.serializers.LocalDateTimeAdapter;
import model.serializers.RecurrenceAdapter;

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

    public static final Gson gson;

    static
    {
        gson = new GsonBuilder().serializeNulls()
                .registerTypeAdapter(Recurrence.class, new RecurrenceAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .setPrettyPrinting()
                .setDateFormat("yyyy-mm-dd hh:mm:ss").create();
    }

    public static final RepositoryContainer repositories = new RepositoryContainer();
}
