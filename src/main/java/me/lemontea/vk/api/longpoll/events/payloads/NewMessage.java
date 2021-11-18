package me.lemontea.vk.api.longpoll.events.payloads;

import me.lemontea.vk.api.longpoll.LongPollPayload;
import me.lemontea.vk.api.objects.Message;

public class NewMessage implements LongPollPayload {

    private Message message;

    private NewMessage() {}

    public Message getMessage() {
        return message;
    }

}
