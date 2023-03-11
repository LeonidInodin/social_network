package ru.inodinln.social_network.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.inodinln.social_network.security.Roles;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private LocalDate regDate;

    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Roles role;

    private Long countOfSubscribers;

    @ManyToMany(mappedBy = "members")
    private List<Conversation> conversations;


    @PrePersist
    private void prePersist() {
        regDate = LocalDate.now();
        countOfSubscribers = 0L;
        role = Roles.ROLE_USER;
    }

}
