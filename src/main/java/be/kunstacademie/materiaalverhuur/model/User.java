package be.kunstacademie.materiaalverhuur.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * User entiteit met BCrypt password hashing voor veilige authenticatie.
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Gebruikersnaam is verplicht")
    @Size(min = 3, max = 50)
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank(message = "Email is verplicht")
    @Email(message = "Ongeldig email adres")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Wachtwoord is verplicht")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "Voornaam is verplicht")
    private String firstName;

    @NotBlank(message = "Achternaam is verplicht")
    private String lastName;

    private String studentNumber;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> roles = new HashSet<>();

    @Column(nullable = false)
    private Boolean enabled = true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}