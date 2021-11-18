package me.lemontea.vk.api.objects.keyboard;

import com.google.gson.annotations.JsonAdapter;
import me.lemontea.vk.api.objects.keyboard.actions.Text;
import me.lemontea.vk.api.objects.keyboard.adapters.ActionJSONAdapter;

@JsonAdapter(ActionJSONAdapter.class)
public interface Action {

    enum ActionType {

        TEXT("text", Text.class);

        private final String actionName;
        private final Class<? extends Action> actionClass;

        ActionType(String actionName, Class<? extends Action> actionClass) {
            this.actionName = actionName;
            this.actionClass = actionClass;
        }

        public String getActionName() {
            return actionName;
        }

        public Class<? extends Action> getActionClass() {
            return actionClass;
        }

        public static ActionType fromName(String actionName) {
            for (ActionType actionType : ActionType.values())
                if (actionType.actionName.equals(actionName))
                    return actionType;

            return null;
        }

        public static ActionType fromClass(Class<? extends Action> actionClass) {
            for (ActionType actionType : ActionType.values())
                if (actionType.actionClass.equals(actionClass))
                    return actionType;

            return null;
        }

    }

}
