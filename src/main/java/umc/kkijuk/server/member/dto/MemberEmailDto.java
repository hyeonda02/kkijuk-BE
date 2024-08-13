package umc.kkijuk.server.member.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberEmailDto {
    private String email;

    @Builder
    public MemberEmailDto(String email) {
        this.email = email;
    }
}
