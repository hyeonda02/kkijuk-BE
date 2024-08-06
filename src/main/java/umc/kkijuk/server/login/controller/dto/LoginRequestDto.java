package umc.kkijuk.server.login.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class LoginRequestDto {
    private String email;
    private String password;
}
