package me.lemontea.vk.api.objects.keyboard.adapters;

import com.google.gson.*;
import me.lemontea.vk.api.Bot;
import me.lemontea.vk.api.objects.keyboard.Action;

import java.lang.reflect.Type;
import java.util.logging.Level;

public class ActionJSONAdapter implements JsonDeserializer<Action>, JsonSerializer<Action> {

    @Override
    public Action deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject obj = jsonElement.getAsJsonObject();

        Action.ActionType actionType = Action.ActionType.fromName(obj.get("type").getAsString());

        if (actionType == null) {
            Bot.LOGGER.log(Level.WARNING, "Unknown action type received in the keyboard object.");

            return null;
        }

        return jsonDeserializationContext.deserialize(obj, actionType.getActionClass());
    }

    @Override
    public JsonElement serialize(Action action, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonElement elem = jsonSerializationContext.serialize(action);

        if (elem.isJsonNull())
            return elem;

        Action.ActionType actionType = Action.ActionType.fromClass(action.getClass());

        if (actionType == null) {
            Bot.LOGGER.log(Level.WARNING, "Unknown button action type. Returning null.");

            return JsonNull.INSTANCE;
        }

        JsonObject keyboardObj = elem.getAsJsonObject();

        keyboardObj.addProperty("type", actionType.getActionName());

        return keyboardObj;
    }

}
