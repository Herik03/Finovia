package org.vaadin.example.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.example.application.classes.Ausschuettung;
import org.vaadin.example.application.repositories.AusschuettungRepository;

@Service
public class AusschuettungService {
	   private final AusschuettungRepository ausschuettungRepository;

	    @Autowired
	    public AusschuettungService(AusschuettungRepository ausschuettungRepository) {
	        this.ausschuettungRepository = ausschuettungRepository;
	    }

	    public void saveAusschuettung(Ausschuettung ausschuettung) {
	        ausschuettungRepository.save(ausschuettung);
	    }
}
