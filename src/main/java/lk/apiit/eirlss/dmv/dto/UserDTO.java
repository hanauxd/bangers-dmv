package lk.apiit.eirlss.dmv.dto;

import lk.apiit.eirlss.dmv.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserDTO {
    private String username;
    private String password;

    public User getUser() {
        return new User(this.username, this.password);
    }
}
