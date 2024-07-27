package umc.kkijuk.server.member.dto;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import umc.kkijuk.server.member.domain.MarketingAgree;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class MemberInfoChangeDto {
    private Long id; //jwt토큰으로 받을땐 제거
    private String phoneNumber;
    private LocalDate birthDate;
    private MarketingAgree marketingAgree;

    @Builder
    public MemberInfoChangeDto(String phoneNumber, LocalDate birthDate, MarketingAgree marketingAgree) {
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.marketingAgree = marketingAgree;
    }
}
