package model.serializers;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Serializes and deserializes {@link Path}, can be added to the GSON object
 */
public class PathAdapter implements JsonSerializer<Path>, JsonDeserializer<Path> {
    @Override
    public Path deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return Paths.get(jsonElement.getAsString());
    }

    @Override
    public JsonElement serialize(Path path, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(path.toString());
    }
}
