package services;

import com.Biblio.cours.dao.UtilisateurDAO;
import com.Biblio.cours.entities.Utilisateur;
import com.Biblio.cours.services.UtilisateurServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UtilisateurDAO utilisateurDAO;

    @InjectMocks
    UtilisateurServiceImpl utilisateurService;

    @Test
    public void testGetAllUser() {
        // Arrange
        Utilisateur user1 = new Utilisateur("Alice", "alice@example.com", "Client", "password1", null);
        Utilisateur user2 = new Utilisateur("Bob", "bob@example.com", "Admin", "password2", null);
        List<Utilisateur> mockUserList = Arrays.asList(user1, user2);

        when(utilisateurDAO.findAll()).thenReturn(mockUserList);

        // Act
        List<Utilisateur> result = utilisateurService.getAllUtilisateurs();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Alice", result.get(0).getNom());
        assertEquals("Bob", result.get(1).getNom());

        verify(utilisateurDAO, times(1)).findAll();
    }
}


