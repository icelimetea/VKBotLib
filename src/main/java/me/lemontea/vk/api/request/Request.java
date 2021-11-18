package me.lemontea.vk.api.request;

import me.lemontea.vk.api.response.Response;

import java.lang.reflect.ParameterizedType;
import java.util.Objects;

public abstract class Request<R extends Response<?>> {

    private final Class<R> responseType;
    private final RequestInfo requestInfo;

    @SuppressWarnings("unchecked")
    public Request() {
        responseType = (Class<R>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        requestInfo = Objects.requireNonNull(
                getClass().getAnnotation(RequestInfo.class),
                "Can not create Request instance without RequestInfo specified."
        );
    }

    public Class<R> getResponseType() {
        return responseType;
    }

    public RequestInfo getRequestInfo() {
        return requestInfo;
    }

}
