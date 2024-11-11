package com.Biblio.cours.services;



import com.Biblio.cours.dao.DocumentDAO;
import com.Biblio.cours.entities.Document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DocumentServiceImpl implements IDocumentService {

    @Autowired
    private DocumentDAO documentDao;

    @Value("${file.upload-dir}")
    private String uploadDirectory;
    @Autowired
    private GoogleDriveService googleDriveService;

    public Document saveDocument(Document document, MultipartFile file) {
        try {
            // Upload file to Google Drive and get the URL
            String googleDriveUrl = googleDriveService.uploadFile(file);

            // Set the Google Drive URL in the Document entity
            document.setFileUrl(googleDriveUrl); // Assuming you have a fileUrl field in Document

            // Save the document metadata in the database
            return documentDao.save(document);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file to Google Drive", e);
        }
    }
    @Override
    public List<Document> getAllDocuments() {
        List<Document> documents = documentDao.findAll();
        System.out.println("Documents retrieved from the database: " + documents);
        return documents;
    }


    @Override
    public Optional<Document> getDocumentById(Long id) {
        return documentDao.findById(id);
    }

    @Override
    public void deleteDocument(Long id) {
        Optional<Document> document = documentDao.findById(id);

        if (document.isPresent()) {
            Document doc = document.get();

            // Extract Google Drive file ID from the URL, assuming the URL is saved in fileUrl
            String fileUrl = doc.getFileUrl();
            String googleDriveFileId = extractFileIdFromUrl(fileUrl);

            // Delete the file from Google Drive
            try {
                googleDriveService.deleteFile(googleDriveFileId);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to delete file from Google Drive", e);
            }

            // Delete the document from the database
            documentDao.deleteById(id);
        }
    }

    private String extractFileIdFromUrl(String fileUrl) {
        // Assuming file URL follows format https://drive.google.com/file/d/FILE_ID/view
        String[] parts = fileUrl.split("/");
        return parts[parts.length - 2];
    }
}

