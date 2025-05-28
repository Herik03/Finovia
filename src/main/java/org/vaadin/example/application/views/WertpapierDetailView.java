package org.vaadin.example.application.views;

import com.vaadin.flow.component.dialog.Dialog;
import org.vaadin.example.application.classes.Wertpapier;

public interface WertpapierDetailView {
    Dialog createDetailsDialog(Wertpapier wertpapier);
}
