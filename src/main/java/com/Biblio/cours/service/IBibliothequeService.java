package com.Biblio.cours.service;

import com.Biblio.cours.entities.Bibliotheque;

import java.util.List;
import java.util.Optional;

public interface IBibliothequeService {
    // Create or Update Bibliotheque
    Bibliotheque saveBibliotheque(Bibliotheque bibliotheque);

    // Get all Bibliotheques
    List<Bibliotheque> getAllBibliotheques();

    // Get Bibliotheque by ID
    Optional<Bibliotheque> getBibliothequeById(Long id);

    // Delete Bibliotheque by ID
    void deleteBibliotheque(Long id);
}
