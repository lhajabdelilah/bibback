package com.Biblio.cours.dao;


import com.Biblio.cours.entities.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypeDAO extends JpaRepository<Type, Long> {

    // Méthode pour trouver un Type par son nom
    Optional<Type> findByName(String name);
}
