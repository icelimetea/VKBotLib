package me.lemontea.vk.api.objects.keyboard;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Keyboard {

    @SerializedName("one_time")
    private boolean oneTime;

    private boolean inline;

    private List<List<Button>> buttons;

    private Keyboard() {}

    public Keyboard(boolean oneTime, boolean inline) {
        this.oneTime = oneTime;
        this.inline = inline;

        buttons = new ArrayList<>();
    }

    public Keyboard(boolean oneTime, boolean inline, List<List<Button>> buttons) {
        this.oneTime = oneTime;
        this.inline = inline;
        this.buttons = buttons;
    }

    public boolean isOneTime() {
        return oneTime;
    }

    public boolean isInline() {
        return inline;
    }

    public List<List<Button>> getButtons() {
        return buttons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Keyboard keyboard = (Keyboard) o;
        return oneTime == keyboard.oneTime && inline == keyboard.inline && Objects.equals(buttons, keyboard.buttons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(oneTime, inline, buttons);
    }

}
