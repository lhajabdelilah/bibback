package com.Biblio.cours.web;

import com.Biblio.cours.entities.Utilisateur;
import com.Biblio.cours.security.CustomUserDetailsService;
import com.Biblio.cours.security.JwtRequest;
import com.Biblio.cours.security.JwtResponse;
import com.Biblio.cours.security.JwtUtil;
import com.Biblio.cours.services.IUtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
public class AuthController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private IUtilisateurService utilisateurService;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Utilisateur utilisateur) {
        // Check if user already exists
        if (utilisateurService.getUtilisateurByEmail(utilisateur.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        // Create new user's account
        utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
        Utilisateur savedUtilisateur = utilisateurService.saveUtilisateur(utilisateur);

        return ResponseEntity.ok(savedUtilisateur);
    }
}