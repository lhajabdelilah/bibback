package com.Biblio.cours.dao;



import com.Biblio.cours.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilisateurDAO extends JpaRepository<Utilisateur, Long> {

    // Méthode pour trouver un Utilisateur par son email
    Optional<Utilisateur> findByEmail(String email);

    // Méthode pour vérifier l'existence d'un utilisateur par son nom
    boolean existsByNom(String nom);
}
