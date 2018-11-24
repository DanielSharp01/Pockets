package utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;

/**
 * Requestable JSON API
 */
public class JsonAPI {

    /**
     * API URL for the JSON API
     */
    protected String apiUrl;

    /**
     * API key for the JSON API
     */
    protected String apiKey;

    /**
     * Minimum time spent between requests
     */
    protected long throttleSeconds;

    /**
     * Timestamp of the last request
     */
    protected long lastTimestamp;

    /**
     * @param apiUrl URL of the requested API, optionally you can put &lt;APY-KEY&gt; anywhere
     *               to replace with the supplied API key
     * @param apiKey API key which will be replaced into &lt;APY-KEY&gt (optional but recommended)
     * @param throttleSeconds Seconds to wait between requests
     */
    public JsonAPI(String apiUrl, String apiKey, int throttleSeconds)
    {
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
        this.throttleSeconds = throttleSeconds;
    }

    /**
     * @param apiUrl URL of the requested API, optionally you can put &lt;APY-KEY&gt; anywhere
     *               to replace with the supplied API key
     * @param apiKey API key which will be replaced into &lt;APY-KEY&gt (optional but recommended)
     */
    public JsonAPI(String apiUrl, String apiKey)
    {
        this(apiUrl, apiKey, 0);
    }

    /**
     * @param apiUrl URL of the requested API
     */
    public JsonAPI(String apiUrl)
    {
        this(apiUrl, null, 0);
    }

    /**
     * @return Gets the request URL with the replaced API key
     */
    protected String getRequestURL() throws APIKeyNotFoundException {
        if (apiUrl.contains("<API-KEY>")) {
            if (apiKey == null) throw new APIKeyNotFoundException();
            return apiUrl.replaceAll("<API-KEY>", apiKey);
        }
        else
            return apiUrl;
    }

    /**
     * Requests the API for a string (may be non JSON)
     * @return String of response, null if throttle is still active
     * @throws IOException If the request failed, see exception for reason
     */
    public String requestString() throws IOException, APIKeyNotFoundException {
        if (throttleSeconds > Instant.now().getEpochSecond() - lastTimestamp)
            return null;

        String response;
        try
        {
            response = IOUtils.readAllFromURL(getRequestURL());
            lastTimestamp = Instant.now().getEpochSecond();
        }
        catch (IOException e)
        {
            throw e;
        }

        return response;
    }

    /**
     * Requests the API for JsonObject
     * @return Response as a JsonObject
     * @throws IOException If the request failed, see exception for reason
     */
    public JsonObject requestJsonObject() throws IOException, APIKeyNotFoundException {
        return new JsonParser().parse(requestString()).getAsJsonObject();
    }

    /**
     * Requests the API and writes the resulting string into a file
     * @param file File to write into (should be .json)
     * @throws IOException If the request failed, see exception for reason
     */
    public void requestIntoFile(Path file) throws IOException, APIKeyNotFoundException {
        BufferedWriter writer = Files.newBufferedWriter(file);
        writer.write(requestString());
        writer.close();
    }

    /**
     * Exception thrown when the JSON API can't find the API key
     */
    public static class APIKeyNotFoundException extends Exception {
    }
}
