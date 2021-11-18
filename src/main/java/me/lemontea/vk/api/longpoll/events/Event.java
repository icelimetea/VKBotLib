package me.lemontea.vk.api.longpoll.events;

import me.lemontea.vk.api.longpoll.LongPollPayload;

public record Event(
        EventType type,
        long groupId,
        LongPollPayload object
) {}
