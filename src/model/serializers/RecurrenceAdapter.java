package model.serializers;

import com.google.gson.*;
import model.*;

import java.lang.reflect.Type;

/**
 * Serializes and deserializes {@link Recurrence}, can be added to the GSON object
 */
public class RecurrenceAdapter implements JsonSerializer<Recurrence>, JsonDeserializer<Recurrence> {

    @Override
    public JsonElement serialize(Recurrence recurrence, Type type, JsonSerializationContext context) {
        if (recurrence instanceof NullRecurrence || recurrence == null)
        {
            return new JsonPrimitive(false);
        }
        JsonObject obj = context.serialize(recurrence).getAsJsonObject();
        if (recurrence instanceof  MonthlyRecurrence)
        {
            obj.addProperty("type", "monthly");
        }
        else if (recurrence instanceof WeeklyRecurrence)
        {
            obj.addProperty("type", "weekly");
        }
        else if (recurrence instanceof DailyRecurrence)
        {
            obj.addProperty("type", "daily");
        }
        else throw new UnsupportedOperationException();

        return obj;
    }

    @Override
    public Recurrence deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        if (jsonElement.isJsonPrimitive())
        {
            JsonPrimitive prim = jsonElement.getAsJsonPrimitive();
            if (prim.isBoolean() && prim.getAsBoolean() == false)
            {
                return new NullRecurrence();
            }
        }
        JsonObject obj = jsonElement.getAsJsonObject();
        JsonPrimitive prim = (JsonPrimitive) obj.get("type");
        switch (prim.getAsString())
        {
            case "monthly":
                return context.deserialize(jsonElement, MonthlyRecurrence.class);
            case "weekly":
                return context.deserialize(jsonElement, WeeklyRecurrence.class);
            case "daily":
                return context.deserialize(jsonElement, DailyRecurrence.class);
            default:
                throw new UnsupportedOperationException();
        }
    }
}
