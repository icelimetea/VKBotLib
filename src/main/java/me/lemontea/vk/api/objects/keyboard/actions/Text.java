package me.lemontea.vk.api.objects.keyboard.actions;

import me.lemontea.vk.api.objects.keyboard.Action;

import java.util.Objects;

public class Text implements Action {

    private String label;

    private String payload;

    public Text() {}

    public Text(String label, String payload) {
        this.label = label;
        this.payload = payload;
    }

    public String getLabel() {
        return label;
    }

    public String getPayload() {
        return payload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Text text = (Text) o;
        return Objects.equals(label, text.label) && Objects.equals(payload, text.payload);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, payload);
    }

}
