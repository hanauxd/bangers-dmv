package lk.apiit.eirlss.dmv.models;

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
    private String password;
    @Column(name = "registration_number")
    private String registrationNumber;

    @PrePersist
    private void assignRegistrationNumber() {
        this.registrationNumber = UUID.randomUUID().toString();
    }
}
