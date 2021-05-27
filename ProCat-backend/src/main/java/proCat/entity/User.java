package proCat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "email")
    private String email;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Rent> rentSet;
}
