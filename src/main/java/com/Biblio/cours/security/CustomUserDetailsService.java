package com.Biblio.cours.security;

import com.Biblio.cours.dao.UtilisateurDAO;
import com.Biblio.cours.entities.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UtilisateurDAO utilisateurDAO;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurDAO.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new org.springframework.security.core.userdetails.User(
                utilisateur.getEmail(),
                utilisateur.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + utilisateur.getType().toUpperCase()))
        );
    }
}