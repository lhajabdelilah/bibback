package com.Biblio.cours.service;



import com.Biblio.cours.entities.Commentaire;
import java.util.List;

public interface ICommentaireService {
    Commentaire saveCommentaire(Commentaire commentaire);
    Commentaire getCommentaireById(Long id);
    List<Commentaire> getAllCommentaires();
    void deleteCommentaire(Long id);
    Commentaire updateCommentaire(Long id, Commentaire commentaire);
}
