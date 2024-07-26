package umc.kkijuk.server.member.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import umc.kkijuk.server.member.domain.MarketingAgree;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.member.domain.State;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class MemberJoinDto {

    private String email;
    private String name;
    private String phoneNumber;
    private LocalDate birthDate;
    private String password;
    private String passwordConfirm;
    private MarketingAgree marketingAgree;
    private State userState;

//    @Builder
//    public MemberJoinDto(String email, String name, String phoneNumber, LocalDate birthDate,
//                         String password, Boolean marketingAgree, State userState) {
//        this.email = email;
//        this.name = name;
//        this.phoneNumber = phoneNumber;
//        this.birthDate = birthDate;
//        this.password = password;
//        this.marketingAgree = marketingAgree;
//        this.userState = userState;
//    }

    @Builder
    public MemberJoinDto(String email, String name, String phoneNumber, LocalDate birthDate,
                         String password, String passwordConfirm, MarketingAgree marketingAgree, State userState) {
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.marketingAgree = marketingAgree;
        this.userState = userState;
    }

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .name(name)
                .phoneNumber(phoneNumber)
                .birthDate(birthDate)
                .password(password)
                .marketingAgree(marketingAgree)
                .userState(userState)
                .build();
    }

}
