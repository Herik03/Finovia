package org.vaadin.example.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.example.application.classes.Kurs;
import org.vaadin.example.application.repositories.KursRepository;

import java.util.List;

@Service
public class KursService {

    private final KursRepository kursRepository;

    @Autowired
    public KursService(KursRepository kursRepository) {
        this.kursRepository = kursRepository;
    }

    /**
     * Retrieves all Kurs entries for a given Wertpapier symbol, ordered by date ascending.
     *
     * @param symbol The symbol of the Wertpapier.
     * @return A list of Kurs objects.
     */
    public List<Kurs> getKurseByWertpapierSymbol(String symbol) {
        return kursRepository.findByWertpapierSymbolOrderByDatumAsc(symbol);
    }


}