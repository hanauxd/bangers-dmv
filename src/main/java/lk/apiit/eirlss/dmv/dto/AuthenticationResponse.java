package lk.apiit.eirlss.dmv.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthenticationResponse {
    private final String username;
    private final String expiryTime;
    private final String jwt;
}
