package com.Biblio.cours.services;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.client.http.FileContent;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import java.util.Collections;
import java.util.List;

@Service
public class GoogleDriveService {

    private static final String APPLICATION_NAME = "BibliothiqueApp";
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE_FILE);

    private Drive getDriveService() throws IOException {
        InputStream credentialsStream = getClass().getResourceAsStream("/credentials.json");
        GoogleCredential credential = GoogleCredential.fromStream(credentialsStream).createScoped(SCOPES);
        return new Drive.Builder(credential.getTransport(), credential.getJsonFactory(), credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public String uploadFile(MultipartFile file) throws IOException {
        // Convert MultipartFile to a temporary File
        java.io.File tempFile = new java.io.File(System.getProperty("java.io.tmpdir"), file.getOriginalFilename());

        // Transfer the content of MultipartFile to the temporary file
        file.transferTo(tempFile);

        // Create Google Drive file metadata (Drive File)
        File fileMetadata = new File();
        fileMetadata.setName(file.getOriginalFilename());  // Set the name of the file for Google Drive

        // Create FileContent object using the temporary file
        FileContent mediaContent = new FileContent(file.getContentType(), tempFile);

        // Upload the file to Google Drive
        File uploadedFile = getDriveService().files().create(fileMetadata, mediaContent)
                .setFields("id, webContentLink, webViewLink")
                .execute();

        // Delete the temporary file after upload
        tempFile.delete();

        // Return the URL to access the uploaded file
        return uploadedFile.getWebViewLink();  // You can also return webContentLink or the file ID if needed
    }

    public void deleteFile(String fileId) throws IOException {
        getDriveService().files().delete(fileId).execute();
    }
}
