package org.vaadin.example.application.services;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;

@Tag("canvas")
public class Canvas extends Component {
    public Canvas(String id, int width, int height) {
        getElement().setAttribute("id", id);
        getElement().setAttribute("width", String.valueOf(width));
        getElement().setAttribute("height", String.valueOf(height));
        getElement().getStyle().set("border", "1px solid #ccc");
    }
}
