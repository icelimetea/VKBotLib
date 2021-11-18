package me.lemontea.vk.api.objects.templates;

import com.google.gson.annotations.JsonAdapter;

@JsonAdapter(TemplateJSONAdapter.class)
public interface Template {

    enum TemplateType {

        CAROUSEL("carousel", Carousel.class);

        private final String typeName;
        private final Class<? extends Template> templateClass;

        TemplateType(String typeName, Class<? extends Template> templateClass) {
            this.typeName = typeName;
            this.templateClass = templateClass;
        }

        public String getTypeName() {
            return typeName;
        }

        public Class<? extends Template> getTemplateClass() {
            return templateClass;
        }

        public static TemplateType fromName(String templateType) {
            for (TemplateType type : values())
                if (type.typeName.equals(templateType))
                    return type;

            return null;
        }

        public static TemplateType fromClass(Class<? extends Template> templateClass) {
            for (TemplateType type : values())
                if (type.templateClass.equals(templateClass))
                    return type;

            return null;
        }

    }

}
