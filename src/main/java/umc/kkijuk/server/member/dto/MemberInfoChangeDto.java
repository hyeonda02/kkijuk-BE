package umc.kkijuk.server.member.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import umc.kkijuk.server.member.domain.MarketingAgree;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class MemberInfoChangeDto {
    @NotNull
    private String phoneNumber;
    @NotNull
    private LocalDate birthDate;
    @NotNull
    private MarketingAgree marketingAgree;

    @Builder
    public MemberInfoChangeDto(String phoneNumber, LocalDate birthDate, MarketingAgree marketingAgree) {
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.marketingAgree = marketingAgree;
    }
}
