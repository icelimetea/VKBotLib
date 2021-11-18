package me.lemontea.vk.api.longpoll;

import com.google.gson.annotations.SerializedName;
import me.lemontea.vk.api.longpoll.events.Event;

import java.util.List;

public class LongPollResponse {

    @SerializedName("ts")
    private String timestamp;

    @SerializedName("updates")
    private List<Event> events;

    private LongPollResponse() {}

    public LongPollResponse(String timestamp, List<Event> events) {
        this.timestamp = timestamp;
        this.events = events;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public List<Event> getEvents() {
        return events;
    }

}
