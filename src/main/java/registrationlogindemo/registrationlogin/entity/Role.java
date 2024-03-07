package registrationlogindemo.registrationlogin.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "roleName", nullable = false, unique = true)
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    List<User> users = new ArrayList<>();

}
