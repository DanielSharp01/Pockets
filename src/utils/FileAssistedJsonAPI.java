package utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;

/**
 * File assisted, requestable JSON API, file assisted when request fails or is throttled
 */
public class FileAssistedJsonAPI extends JsonAPI {

    /**
     * File to save the requests into and load result from when throttled
     */
    protected Path assistFile;

    /**
     * @param apiUrl URL of the requested API, optionally you can put &lt;APY-KEY&gt; anywhere
     *               to replace with the supplied API key
     * @param apiKey API key which will be replaced into &lt;APY-KEY&gt (optional but recommended)
     * @param file File to save the requests into and load result from when throttled
     * @param throttleSeconds Seconds to wait between requests
     */
    public FileAssistedJsonAPI(String apiUrl, String apiKey, Path file, int throttleSeconds) {
        super(apiUrl, apiKey, throttleSeconds);
        assistFile = file;
    }

    /**
     * @param apiUrl URL of the requested API, optionally you can put &lt;APY-KEY&gt; anywhere
     *               to replace with the supplied API key
     * @param apiKey API key which will be replaced into &lt;APY-KEY&gt (optional but recommended)
     * @param file File to save the requests into and load result from when throttled
     */
    public FileAssistedJsonAPI(String apiUrl, String apiKey, Path file) {
        this(apiUrl, apiKey, file, 0);
    }

    /**
     * @param apiUrl URL of the requested API, optionally you can put &lt;APY-KEY&gt; anywhere
     *               to replace with the supplied API key
     * @param file File to save the requests into and load result from when throttled
     */
    public FileAssistedJsonAPI(String apiUrl, Path file) {
        this(apiUrl, null, file, 0);
    }

    /**
     * Requests the API for a string (may be non JSON)
     * @return String of response, it will be read from the file if still throttle still active
     * @throws IOException If the request failed, see exception for reason
     */
    @Override
    public String requestString() throws IOException {
        if (Files.exists(assistFile))
        {
            long fileTimestamp = Files.getLastModifiedTime(assistFile).toInstant().getEpochSecond();
            // We need to avoid cases when the file was not written and we get into a request loop
            if (lastTimestamp < fileTimestamp)
            {
                lastTimestamp = fileTimestamp;
            }
        }

        if (throttleSeconds > Instant.now().getEpochSecond() - lastTimestamp)
        {
            if (assistFile != null && Files.exists(assistFile))
                return new String(Files.readAllBytes(assistFile), StandardCharsets.UTF_8);
            else
                return null;
        }

        String response = "{}";
        try
        {
            response = IOUtils.readAllFromURL(getRequestURL());
            BufferedWriter writer = Files.newBufferedWriter(assistFile);
            writer.write(response);
            writer.close();
            lastTimestamp = Instant.now().getEpochSecond();
        }
        catch (IOException e)
        {
            throw e;
        }

        return response;
    }
}
