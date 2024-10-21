package com.Biblio.cours.web;



import com.Biblio.cours.dao.BibliothequeDAO;
import com.Biblio.cours.dao.TypeDAO;
import com.Biblio.cours.dao.UtilisateurDAO;
import com.Biblio.cours.entities.*;
import com.Biblio.cours.services.IBibliothequeService;
import com.Biblio.cours.services.ICommentaireService;
import com.Biblio.cours.services.IDocumentService;
import com.Biblio.cours.services.IUtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@CrossOrigin("http://localhost:3000")
@RestController
public class AppController {

    @Autowired
    private IUtilisateurService utilisateurService;
    @Autowired
    private IBibliothequeService bibliothequeService;
    @Autowired
    private IDocumentService documentService;
    @Autowired
    private ICommentaireService commentaireService;
    @Autowired
    private BibliothequeDAO bibliothequeDAO;
    @Autowired
    private TypeDAO typeDAO;
    @Autowired
    private UtilisateurDAO utilisateurDAO;



    @PostMapping("/api/user/save")
    public ResponseEntity<Utilisateur> saveUtilisateur(@RequestBody Utilisateur utilisateur) {
        Utilisateur savedUtilisateur = utilisateurService.saveUtilisateur(utilisateur);
        return new ResponseEntity<>(savedUtilisateur, HttpStatus.CREATED);
    }

    // Create or Update Document
    @PostMapping("/api/document/save")
    public ResponseEntity<Document> saveDocument(
            @RequestParam("titre") String titre,
            @RequestParam("description") String description,
            @RequestParam("filier") String filier,
            @RequestParam("niveaux") String niveaux,
            @RequestParam("bibliothequeId") Long bibliothequeId,
            @RequestParam("typeId") Long typeId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("userid") Long userId
    ) {
        // Find related entities using their services
        Optional<Bibliotheque> bibliotheque = bibliothequeDAO.findById(bibliothequeId);
        Optional<Type> type = typeDAO.findById(typeId);
        Optional<Utilisateur> utilisateur = utilisateurDAO.findById(userId);


        // Create a new Document instance and set its properties
        Document document = new Document();
        document.setTitre(titre);
        document.setDescription(description);
        document.setFilier(filier);
        document.setNiveaux(niveaux);
        document.setBibliotheque(bibliotheque.get()); // Set the bibliotheque
        document.setType(type.get()); // Set the type
        document.setUtilisateur(utilisateur.get());

        // Save the document using the service
        Document savedDocument = documentService.saveDocument(document, file);

        return new ResponseEntity<>(savedDocument, HttpStatus.CREATED);
    }


    // Get all Documents
    @GetMapping("/api/document/all")
    public ResponseEntity<List<Document>> getAllDocuments() {
        List<Document> documents = documentService.getAllDocuments();
        return new ResponseEntity<>(documents, HttpStatus.OK);
    }

    // Get Document by ID
    @GetMapping("/api/document/{id}")
    public ResponseEntity<Document> getDocumentById(@PathVariable Long id) {
        Optional<Document> document = documentService.getDocumentById(id);
        return document.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Delete Document by ID
    @DeleteMapping("/api/document/delete/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }











    @PostMapping("/api/comentaire/create")
    public Commentaire createCommentaire(@RequestBody Commentaire commentaire) {
        return commentaireService.saveCommentaire(commentaire);
    }

    @GetMapping("/api/comentaire/{id}")
    public Commentaire getCommentaireById(@PathVariable Long id) {
        return commentaireService.getCommentaireById(id);
    }

    @GetMapping("/api/comentaire/all")
    public List<Commentaire> getAllCommentaires() {
        return commentaireService.getAllCommentaires();
    }

    @DeleteMapping("/api/comentaire/delete/{id}")
    public String deleteCommentaire(@PathVariable Long id) {
        commentaireService.deleteCommentaire(id);
        return "Commentaire supprimé avec succès!";
    }

    @PutMapping("/api/comentaire/update/{id}")
    public Commentaire updateCommentaire(@PathVariable Long id, @RequestBody Commentaire updatedCommentaire) {
        return commentaireService.updateCommentaire(id, updatedCommentaire);
    }




}

