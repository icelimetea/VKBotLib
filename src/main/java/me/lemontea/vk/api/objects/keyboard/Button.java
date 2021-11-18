package me.lemontea.vk.api.objects.keyboard;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Button {

    public enum Color {

        @SerializedName("primary") PRIMARY(0x5181B8),
        @SerializedName("secondary") SECONDARY(0xFFFFFF),
        @SerializedName("negative") NEGATIVE(0xE64646),
        @SerializedName("positive") POSITIVE(0x4BB34B);

        private final int color;

        Color(int color) {
            this.color = color;
        }

        public int getColor() {
            return color;
        }

    }

    private Color color;

    private Action action;

    private Button() {}

    public Button(Color color, Action action) {
        this.color = color;
        this.action = action;
    }

    public Color getColor() {
        return color;
    }

    public Action getAction() {
        return action;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Button button = (Button) o;
        return color == button.color && Objects.equals(action, button.action);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, action);
    }

}
