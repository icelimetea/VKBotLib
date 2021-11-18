package me.lemontea.vk.api.request.requests;

import me.lemontea.vk.api.request.Request;
import me.lemontea.vk.api.request.RequestInfo;
import me.lemontea.vk.api.response.responses.GroupLongPollServerResponse;
import me.lemontea.vk.api.util.query.ParameterName;

@RequestInfo(methodName = "groups.getLongPollServer")
public class GroupLongPollServerRequest extends Request<GroupLongPollServerResponse> {

    @ParameterName("group_id")
    private final long groupId;

    public GroupLongPollServerRequest(long groupId) {
        this.groupId = groupId;
    }

    public long getGroupId() {
        return groupId;
    }

}
