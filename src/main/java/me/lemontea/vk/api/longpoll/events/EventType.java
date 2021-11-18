package me.lemontea.vk.api.longpoll.events;

import me.lemontea.vk.api.longpoll.LongPollPayload;
import me.lemontea.vk.api.longpoll.events.payloads.NewMessage;

public enum EventType {

    MESSAGE_NEW("message_new", NewMessage.class);

    private final String type;

    private final Class<? extends LongPollPayload> payloadClass;

    EventType(String type, Class<? extends LongPollPayload> payloadClass) {
        this.type = type;
        this.payloadClass = payloadClass;
    }

    public String getType() {
        return type;
    }

    public Class<? extends LongPollPayload> getPayloadClass() {
        return payloadClass;
    }

}
