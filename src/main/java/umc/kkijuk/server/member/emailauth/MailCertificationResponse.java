package umc.kkijuk.server.member.emailauth;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MailCertificationResponse {
    private String email;

    @Builder
    public MailCertificationResponse(String email) {
        this.email = email;
    }
}
