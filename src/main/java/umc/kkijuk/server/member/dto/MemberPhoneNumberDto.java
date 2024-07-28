package umc.kkijuk.server.member.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberPhoneNumberDto {
    private String phoneNumber;

    @Builder
    public MemberPhoneNumberDto(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}