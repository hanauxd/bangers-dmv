package lk.apiit.eirlss.dmv.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;
    private String username;
    @JsonIgnore
    private String password;
    @Column(name = "registration_number")
    private String registrationNumber;
    private String role;

    @PrePersist
    private void assignValues() {
        this.registrationNumber = UUID.randomUUID().toString();
        this.role = "ROLE_USER";
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
