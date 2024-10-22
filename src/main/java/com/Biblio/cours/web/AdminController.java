package com.Biblio.cours.web;


import com.Biblio.cours.dto.BibliothequeDTO;
import com.Biblio.cours.dto.UtilisateurDTO;
import com.Biblio.cours.entities.Bibliotheque;
import com.Biblio.cours.entities.Document;
import com.Biblio.cours.entities.Utilisateur;
import com.Biblio.cours.services.IBibliothequeService;
import com.Biblio.cours.services.IDocumentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.OPTIONS})
public class AdminController {
    @Autowired
    private com.Biblio.cours.services.IUtilisateurService utilisateurService;
    @Autowired
    private IBibliothequeService bibliothequeService;
    @Autowired
    private IDocumentService documentService;

    @GetMapping("/api/user/test")
    public ResponseEntity<String> testSerialization() throws JsonProcessingException {
        Utilisateur user = utilisateurService.getAllUtilisateurs().get(0);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @PostMapping("/api/user/create")
    public ResponseEntity<Utilisateur> saveUtilisateurs(@RequestBody Utilisateur utilisateur) {
        Utilisateur savedUtilisateur = utilisateurService.saveUtilisateur(utilisateur);
        return new ResponseEntity<>(savedUtilisateur, HttpStatus.CREATED);
    }

    @GetMapping("/api/admin/user/{id}")
    public ResponseEntity<Utilisateur> getUtilisateurById(@PathVariable Long id) {
        Optional<Utilisateur> utilisateur = utilisateurService.getUtilisateurById(id);
        return utilisateur.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/api/user/update/{id}")
    public ResponseEntity<Utilisateur> updateUtilisateur(@PathVariable Long id, @RequestParam(required = false) String name,
                                                         @RequestParam(required = false) String email,
                                                         @RequestParam(required = false) String password,
                                                         @RequestParam(required = false) String userType,
                                                         @RequestParam(required = false) MultipartFile image) {
        Optional<Utilisateur> optionalUtilisateur = utilisateurService.getUtilisateurById(id);
        if (!optionalUtilisateur.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Utilisateur utilisateur = optionalUtilisateur.get();
        if (name != null) utilisateur.setNom(name);
        if (email != null) utilisateur.setEmail(email);
        if (password != null) utilisateur.setPassword(password); // Ensure proper hashing
        if (userType != null) utilisateur.setType(userType);
        // Handle image upload logic here if necessary

        Utilisateur updatedUtilisateur = utilisateurService.saveUtilisateur(utilisateur);
        return new ResponseEntity<>(updatedUtilisateur, HttpStatus.OK);
    }


    @DeleteMapping("/api/admin/user/delete/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable Long id) {
        utilisateurService.deleteUtilisateur(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    // Create or Update Bibliotheque
    @PostMapping("/api/admin/bibliotique/save")
    public ResponseEntity<Bibliotheque> saveBibliotheque(@RequestBody Bibliotheque bibliotheque) {
        Bibliotheque savedBibliotheque = bibliothequeService.saveBibliotheque(bibliotheque);
        return new ResponseEntity<>(savedBibliotheque, HttpStatus.CREATED);
    }

    @GetMapping("/api/admin/bibliotique/all")
    public ResponseEntity<List<BibliothequeDTO>> getAllBibliotheques() {
        List<Bibliotheque> bibliotheques = bibliothequeService.getAllBibliotheques();
        List<BibliothequeDTO> dtos = bibliotheques.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/api/user/all")
    public ResponseEntity<List<UtilisateurDTO>> getAllUtilisateurs() {
        List<UtilisateurDTO> utilisateurs = utilisateurService.getAllUtilisateurs()
                .stream()
                .map(UtilisateurDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(utilisateurs, HttpStatus.OK);
    }

    private BibliothequeDTO convertToDTO(Bibliotheque bibliotheque) {
        BibliothequeDTO dto = new BibliothequeDTO();
        dto.setId(bibliotheque.getId());
        dto.setNom(bibliotheque.getNom());
        dto.setLocation(bibliotheque.getLocation());
        dto.setDocumentsCount(bibliotheque.getDocuments().size());
        return dto;
    }

    // Get Bibliotheque by ID
    @GetMapping("/api/admin/bibliotique/{id}")
    public ResponseEntity<Bibliotheque> getBibliothequeById(@PathVariable Long id) {
        Optional<Bibliotheque> bibliotheque = bibliothequeService.getBibliothequeById(id);
        return bibliotheque.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/api/admin/document/all")
    public ResponseEntity<List<Document>> getAllDocuments() {
        List<Document> documents = documentService.getAllDocuments();
        return new ResponseEntity<>(documents, HttpStatus.OK);
    }
    // Delete Bibliotheque by ID
    @DeleteMapping("/api/admin/bibliotique/delete/{id}")
    public ResponseEntity<Void> deleteBibliotheque(@PathVariable Long id) {
        bibliothequeService.deleteBibliotheque(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/api/admin/document/delete/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
