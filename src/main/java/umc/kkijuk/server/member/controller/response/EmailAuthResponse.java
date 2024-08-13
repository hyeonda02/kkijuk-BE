package umc.kkijuk.server.member.controller.response;

import lombok.Builder;
import lombok.Data;

@Data
public class EmailAuthResponse {
    private String email;
    private int authNumber;

    public EmailAuthResponse(){
    }
    @Builder
    public EmailAuthResponse(String email, int authNumber) {
        this.email = email;
        this.authNumber = authNumber;
    }
}
