package me.lemontea.vk.api.objects.templates;

import com.google.gson.*;
import me.lemontea.vk.api.Bot;

import java.lang.reflect.Type;
import java.util.logging.Level;

public class TemplateJSONAdapter implements JsonDeserializer<Template>, JsonSerializer<Template> {

    @Override
    public Template deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject obj = jsonElement.getAsJsonObject();

        Template.TemplateType templateType = Template.TemplateType.fromName(obj.get("type").getAsString());

        if (templateType == null) {
            Bot.LOGGER.log(Level.WARNING, "Unknown template type received. Returning null.");

            return null;
        }

        return jsonDeserializationContext.deserialize(obj, templateType.getTemplateClass());
    }

    @Override
    public JsonElement serialize(Template template, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonElement elem = jsonSerializationContext.serialize(template);

        if (elem.isJsonNull())
            return elem;

        Template.TemplateType templateType = Template.TemplateType.fromClass(template.getClass());

        if (templateType == null) {
            Bot.LOGGER.log(Level.WARNING, "Unknown template type. Returning null.");

            return JsonNull.INSTANCE;
        }

        JsonObject obj = elem.getAsJsonObject();

        obj.addProperty("type", templateType.getTypeName());

        return obj;
    }

}
