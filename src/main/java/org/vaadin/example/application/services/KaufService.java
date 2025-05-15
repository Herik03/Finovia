package org.vaadin.example.application.services;

import org.springframework.stereotype.Service;
import org.vaadin.example.application.classes.Kauf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class KaufService {

    private final List<Kauf> kauefe = new ArrayList<>();

    public void addKauf(Kauf kauf) {
        kauefe.add(kauf);
    }

    public List<Kauf> getAllKauefe() {
        return Collections.unmodifiableList(kauefe);
    }
}
