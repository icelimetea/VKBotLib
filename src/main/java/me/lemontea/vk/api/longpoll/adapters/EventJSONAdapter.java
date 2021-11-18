package me.lemontea.vk.api.longpoll.adapters;

import com.google.gson.*;
import me.lemontea.vk.api.Bot;
import me.lemontea.vk.api.longpoll.events.Event;
import me.lemontea.vk.api.longpoll.events.EventRegistry;
import me.lemontea.vk.api.longpoll.events.EventType;

import java.lang.reflect.Type;
import java.util.logging.Level;

public class EventJSONAdapter implements JsonDeserializer<Event> {

    private final EventRegistry eventRegistry;

    public EventJSONAdapter(EventRegistry eventRegistry) {
        this.eventRegistry = eventRegistry;
    }

    @Override
    public Event deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject obj = jsonElement.getAsJsonObject();

        String eventName = obj.get("type").getAsString();

        EventType eventType = eventRegistry.fromName(eventName);

        if (eventType == null) {
            Bot.LOGGER.log(Level.WARNING, String.format(
                    "Unknown event type (%s) received, skipping deserialization!",
                    eventName
            ));

            return null;
        }

        return new Event(
                eventType,
                obj.get("group_id").getAsInt(),
                jsonDeserializationContext.deserialize(obj.get("object"), eventType.getPayloadClass())
        );
    }

}
