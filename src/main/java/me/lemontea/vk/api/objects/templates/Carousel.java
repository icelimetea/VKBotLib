package me.lemontea.vk.api.objects.templates;

import com.google.gson.annotations.SerializedName;
import me.lemontea.vk.api.objects.keyboard.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Carousel implements Template {

    public static class Element {

        private String title;

        private String description;

        @SerializedName("photo_id")
        private String photoId;

        private List<Button> buttons;

        private Element() {}

        public Element(String title, String description, String photoId) {
            this.title = title;
            this.description = description;
            this.photoId = photoId;

            buttons = new ArrayList<>();
        }

        public Element(String title, String description, String photoId, List<Button> buttons) {
            this.title = title;
            this.description = description;
            this.photoId = photoId;
            this.buttons = buttons;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getPhotoId() {
            return photoId;
        }

        public List<Button> getButtons() {
            return buttons;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Element element = (Element) o;
            return Objects.equals(title, element.title) && Objects.equals(description, element.description) && Objects.equals(photoId, element.photoId) && Objects.equals(buttons, element.buttons);
        }

        @Override
        public int hashCode() {
            return Objects.hash(title, description, photoId, buttons);
        }

    }

    private List<Element> elements;

    public Carousel() {
        elements = new ArrayList<>();
    }

    public Carousel(List<Element> elements) {
        this.elements = elements;
    }

    public List<Element> getElements() {
        return elements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carousel carousel = (Carousel) o;
        return Objects.equals(elements, carousel.elements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(elements);
    }

}
