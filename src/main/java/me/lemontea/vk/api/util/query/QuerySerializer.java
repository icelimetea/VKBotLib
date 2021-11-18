package me.lemontea.vk.api.util.query;

import me.lemontea.vk.api.Bot;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class QuerySerializer {

    private static final BiFunction<QuerySerializer, Object, String> PRIMITIVE_SERIALIZER = (serializer, obj) -> obj.toString();

    private final Map<Class<?>, BiFunction<QuerySerializer, Object, String>> serializers = new HashMap<>();

    public QuerySerializer() {
        serializers.put(String.class, PRIMITIVE_SERIALIZER);
    }

    public String toQuery(Object obj) {
        StringBuilder builder = new StringBuilder();

        for (Field field : obj.getClass().getDeclaredFields()) {
            if ((field.getModifiers() & Modifier.TRANSIENT) == 0) {
                field.setAccessible(true);

                ParameterName parameterName = field.getAnnotation(ParameterName.class);

                try {
                    Object val = field.get(obj);

                    if (val == null)
                        continue;

                    BiFunction<QuerySerializer, Object, String> serializer = serializers.get(field.getType());

                    if (serializer == null) {
                        if (field.getType().isPrimitive()) {
                            serializer = PRIMITIVE_SERIALIZER;
                        } else {
                            Bot.LOGGER.warning(String.format(
                                    "No serializer found for the field %s in %s!",
                                    field.getName(), obj.getClass().getName()
                            ));

                            continue;
                        }
                    }

                    builder.append(URLEncoder.encode(
                                    parameterName == null ? field.getName() : parameterName.value(),
                                    StandardCharsets.UTF_8
                            ))
                            .append('=')
                            .append(URLEncoder.encode(serializer.apply(this, val), StandardCharsets.UTF_8))
                            .append('&');
                } catch (IllegalAccessException ignored) {}
            }
        }

        return builder.toString();
    }

    public Map<Class<?>, BiFunction<QuerySerializer, Object, String>> getSerializers() {
        return serializers;
    }

}
