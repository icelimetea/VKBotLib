package me.lemontea.vk.api.response.responses;

import com.google.gson.annotations.SerializedName;
import me.lemontea.vk.api.response.ApiError;
import me.lemontea.vk.api.response.Response;

public class GroupLongPollServerResponse extends Response<GroupLongPollServerResponse.GroupLongPollServerPayload> {

    public static class GroupLongPollServerPayload {

        @SerializedName("key")
        private String longPollKey;

        @SerializedName("server")
        private String longPollServer;

        @SerializedName("ts")
        private String longPollTimestamp;

        private GroupLongPollServerPayload() {}

        public GroupLongPollServerPayload(String longPollKey, String longPollServer, String longPollTimestamp) {
            this.longPollKey = longPollKey;
            this.longPollServer = longPollServer;
            this.longPollTimestamp = longPollTimestamp;
        }

        public String getLongPollKey() {
            return longPollKey;
        }

        public String getLongPollServer() {
            return longPollServer;
        }

        public String getLongPollTimestamp() {
            return longPollTimestamp;
        }

    }

    private GroupLongPollServerResponse() {}

    public GroupLongPollServerResponse(ApiError error, GroupLongPollServerPayload response) {
        super(error, response);
    }

}
