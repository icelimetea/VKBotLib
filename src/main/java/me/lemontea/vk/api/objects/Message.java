package me.lemontea.vk.api.objects;

import com.google.gson.annotations.SerializedName;
import me.lemontea.vk.api.objects.keyboard.Keyboard;
import me.lemontea.vk.api.objects.templates.Template;

import java.util.Objects;

public class Message {

    private int id;

    private int date;

    @SerializedName("peer_id")
    private int peerId;

    private String text;

    private String payload;

    @SerializedName("from_id")
    private int fromId;

    private Keyboard keyboard;
    private Template template;

    private Message() {}

    public Message(int id, int date, int peerId, String text, String payload, int fromId, Keyboard keyboard, Template template) {
        this.id = id;
        this.date = date;
        this.peerId = peerId;
        this.text = text;
        this.payload = payload;
        this.fromId = fromId;
        this.keyboard = keyboard;
        this.template = template;
    }

    public int getId() {
        return id;
    }

    public int getDate() {
        return date;
    }

    public int getPeerId() {
        return peerId;
    }

    public String getText() {
        return text;
    }

    public String getPayload() {
        return payload;
    }

    public int getFromId() {
        return fromId;
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }

    public Template getTemplate() {
        return template;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return id == message.id && date == message.date && peerId == message.peerId && fromId == message.fromId && Objects.equals(text, message.text) && Objects.equals(payload, message.payload) && Objects.equals(keyboard, message.keyboard) && Objects.equals(template, message.template);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, peerId, text, payload, fromId, keyboard, template);
    }

}
