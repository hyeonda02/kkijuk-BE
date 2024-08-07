package umc.kkijuk.server.login.controller.dto;

import lombok.Builder;
import lombok.Getter;
import umc.kkijuk.server.member.domain.Member;

@Getter
@Builder
public class LoginInfo {
    private Long memberId;

    public static LoginInfo from(Member member) {
        return LoginInfo.builder()
                .memberId(member.getId())
                .build();
    }
}
