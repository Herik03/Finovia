package org.vaadin.example.application.classes;

public class Zinszahlung  extends Ausschuettung {

    public Zinszahlung(double ausschuettung, double betrag, double steueren, double zinsen)
    {
        super(ausschuettung,betrag,steueren,null);
    }
}
