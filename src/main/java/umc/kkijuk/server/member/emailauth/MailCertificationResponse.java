package umc.kkijuk.server.member.emailauth;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MailCertificationResponse {
    private String email;
    private String authNumber;

    @Builder
    public MailCertificationResponse(String email, String authNumber) {
        this.email = email;
        this.authNumber = authNumber;
    }
}
