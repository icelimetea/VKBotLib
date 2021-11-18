package me.lemontea.vk.api.longpoll.events;

import me.lemontea.vk.api.Bot;
import me.lemontea.vk.api.longpoll.LongPollPayload;

import java.util.*;
import java.util.function.BiConsumer;

public class EventRegistry {

    private static class Entry {

        private final EventType type;

        private final List<BiConsumer<Bot, LongPollPayload>> handlers;

        public Entry(EventType type) {
            this.type = type;

            handlers = new ArrayList<>();
        }

        public EventType getType() {
            return type;
        }

        public List<BiConsumer<Bot, LongPollPayload>> getHandlers() {
            return handlers;
        }

    }

    private final Map<String, Entry> eventRegistry;

    public EventRegistry() {
        eventRegistry = new HashMap<>();

        for (EventType type : EventType.values())
            eventRegistry.put(type.getType(), new Entry(type));
    }

    public EventType fromName(String name) {
        Entry entry = eventRegistry.get(name);

        return entry != null ? entry.getType() : null;
    }

    public List<BiConsumer<Bot, LongPollPayload>> getHandlers(EventType type) {
        return Collections.unmodifiableList(eventRegistry.get(type.getType()).getHandlers());
    }

    public void addHandler(EventType type, BiConsumer<Bot, LongPollPayload> handler) {
        eventRegistry.get(type.getType()).getHandlers().add(handler);
    }

}
