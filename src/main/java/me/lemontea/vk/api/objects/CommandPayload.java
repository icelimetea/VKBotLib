package me.lemontea.vk.api.objects;

import java.util.Objects;

public class CommandPayload {

    private String command;

    private CommandPayload() {}

    public CommandPayload(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommandPayload that = (CommandPayload) o;
        return Objects.equals(command, that.command);
    }

    @Override
    public int hashCode() {
        return Objects.hash(command);
    }

}
