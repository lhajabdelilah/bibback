package com.Biblio.cours.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
;


import java.io.File;
import java.util.List;

@Getter
@Setter
@Entity
public class Document {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dislike")
    private Integer dislike;

    @Column(name = "likes")  // Change "like" to "likes"
    private Integer likes; // Change the variable name to "likes"
    @Column(name = "filePath")
    private String filePath ;



    @Column(name = "description")
    private String description;

    @Column(name = "filier")
    private String filier;

    @Column(name = "niveaux")
    private String niveaux;

    @Column(name = "titre")
    private String titre;

    @ManyToOne
    @JoinColumn(name = "bibliotheque_id", nullable = false)
    private Bibliotheque bibliotheque;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private Type type;

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Commentaire> commentaires;

    // Constructors, Getters, and Setters
    public Document() {}

    public Document(String titre, String description, String filier, String niveaux, Bibliotheque bibliotheque, Type type) {
        this.titre = titre;
        this.description = description;
        this.filier = filier;
        this.niveaux = niveaux;
        this.bibliotheque = bibliotheque;
        this.type = type;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    // Getters and Setters
}

