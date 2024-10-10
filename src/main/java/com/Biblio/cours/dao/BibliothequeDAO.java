package com.Biblio.cours.dao;

import com.Biblio.cours.entities.Bibliotheque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BibliothequeDAO extends JpaRepository<Bibliotheque, Long> {
    // Additional queries if needed
    List<Bibliotheque> findByNomContaining(String nom);
}

