package org.vaadin.example.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vaadin.example.application.classes.Nutzer;
import org.vaadin.example.application.repositories.NutzerRepository;

import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final NutzerRepository nutzerRepository;

    @Autowired
    public UserDetailsServiceImpl(NutzerRepository nutzerRepository) {
        this.nutzerRepository = nutzerRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        Nutzer nutzer = nutzerRepository.findByUsername(username);
        if (nutzer == null) {
            throw new UsernameNotFoundException("Benutzer nicht gefunden: " + username);
        }

        return new org.springframework.security.core.userdetails.User(
                nutzer.getUsername(),
                nutzer.getPasswort(),
                true, true, true, true,
                nutzer.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .collect(Collectors.toList())
        );
    }
}

