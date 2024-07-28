package umc.kkijuk.server.member.phoneauth;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SmsCertificationResponse {
    private String authPhoneNumber;
    private String authRandomNumber;

    @Builder
    public SmsCertificationResponse(String authPhoneNumber, String authRandomNumber) {
        this.authPhoneNumber = authPhoneNumber;
        this.authRandomNumber = authRandomNumber;
    }
}
