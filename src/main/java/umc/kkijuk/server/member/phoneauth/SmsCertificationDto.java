package umc.kkijuk.server.member.phoneauth;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SmsCertificationDto {
    private String phoneNumber;
    private String randomNumber;

    @Builder
    public SmsCertificationDto(String phoneNumber, String randomNumber) {
        this.phoneNumber = phoneNumber;
        this.randomNumber = randomNumber;
    }
}
