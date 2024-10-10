package com.Biblio.cours.dao;



import com.Biblio.cours.entities.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentDAO extends JpaRepository<Document, Long> {
    Document findByTitre(String titre);
}

