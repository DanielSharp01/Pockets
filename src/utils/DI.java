package utils;

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
}
