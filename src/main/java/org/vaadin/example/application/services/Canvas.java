package org.vaadin.example.application.services;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;

/**
 * UI-Komponente für ein HTML5-Canvas-Element in Vaadin.
 *
 * Diese Klasse erstellt ein Canvas-Element mit einer angegebenen ID, Breite und Höhe
 * und setzt einen Rahmen um das Canvas.
 *
 * @author Batuhan Güvercin
 */
@Tag("canvas")
public class Canvas extends Component {
    /**
     * Konstruktor für die Canvas-Komponente.
     *
     * @param id     Die ID des Canvas-Elements
     * @param width  Die Breite des Canvas in Pixeln
     * @param height Die Höhe des Canvas in Pixeln
     */
    public Canvas(String id, int width, int height) {
        getElement().setAttribute("id", id);
        getElement().setAttribute("width", String.valueOf(width));
        getElement().setAttribute("height", String.valueOf(height));
        getElement().getStyle().set("border", "1px solid #ccc");
    }
}