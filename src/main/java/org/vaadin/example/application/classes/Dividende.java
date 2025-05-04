package org.vaadin.example.application.classes;

import java.sql.Date;

public class Dividende extends Ausschuettung {

    public Dividende(double ausschuettung, double betrag, double steueren, Date datum)
    {
        super(ausschuettung,betrag,steueren,datum);
    }
}
