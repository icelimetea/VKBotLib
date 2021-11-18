package me.lemontea.vk.api.response;

public abstract class Response<R> {

    private ApiError error;

    private R response;

    protected Response() {}

    public Response(ApiError error, R response) {
        this.error = error;
        this.response = response;
    }

    public ApiError getError() {
        return error;
    }

    public R getResponse() {
        return response;
    }

}
