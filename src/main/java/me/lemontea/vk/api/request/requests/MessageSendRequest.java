package me.lemontea.vk.api.request.requests;

import me.lemontea.vk.api.objects.keyboard.Keyboard;
import me.lemontea.vk.api.objects.templates.Template;
import me.lemontea.vk.api.request.Request;
import me.lemontea.vk.api.request.RequestInfo;
import me.lemontea.vk.api.response.responses.MessageSendResponse;
import me.lemontea.vk.api.util.query.ParameterName;

import java.util.concurrent.ThreadLocalRandom;

@RequestInfo(methodName = "messages.send")
public class MessageSendRequest extends Request<MessageSendResponse> {

    @ParameterName("random_id")
    private final int randomId;

    @ParameterName("peer_id")
    private final int peerId;

    private final String message;

    private final Keyboard keyboard;
    private final Template template;

    public MessageSendRequest(int peerId, String message, Keyboard keyboard, Template template) {
        randomId = ThreadLocalRandom.current().nextInt();

        this.peerId = peerId;
        this.message = message;
        this.keyboard = keyboard;
        this.template = template;
    }

    public int getRandomId() {
        return randomId;
    }

    public int getPeerId() {
        return peerId;
    }

    public String getMessage() {
        return message;
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }

    public Template getTemplate() {
        return template;
    }

}
