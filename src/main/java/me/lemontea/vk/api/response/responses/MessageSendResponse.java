package me.lemontea.vk.api.response.responses;

import me.lemontea.vk.api.response.ApiError;
import me.lemontea.vk.api.response.Response;

public class MessageSendResponse extends Response<Integer> {

    private MessageSendResponse() {}

    public MessageSendResponse(ApiError error, Integer response) {
        super(error, response);
    }

}
