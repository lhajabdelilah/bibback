package com.Biblio.cours.dao;


import com.Biblio.cours.entities.Commentaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentaireDAO extends JpaRepository<Commentaire, Long> {

    // Méthodes supplémentaires si nécessaire
    List<Commentaire> findByDocumentId(Long documentId);

    List<Commentaire> findByUtilisateurId(Long utilisateurId);
}

