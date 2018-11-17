package model.serializers;

import com.google.gson.*;
import javafx.scene.paint.Color;
import utils.ColorUtils;

import java.lang.reflect.Type;

public class ColorAdapter implements JsonSerializer<Color>, JsonDeserializer<Color> {
    @Override
    public Color deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return ColorUtils.fromHex(jsonElement.getAsString());
    }

    @Override
    public JsonElement serialize(Color color, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(ColorUtils.toHex(color));
    }
}
