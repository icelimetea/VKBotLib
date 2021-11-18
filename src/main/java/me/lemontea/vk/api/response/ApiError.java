package me.lemontea.vk.api.response;

import com.google.gson.annotations.SerializedName;

public class ApiError {

    @SerializedName("error_msg")
    String errorMessage;

    @SerializedName("error_code")
    int errorCode;

    private ApiError() {}

    public ApiError(String errorMessage, int errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

}
